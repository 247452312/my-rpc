package indi.uhyils.rpc.netty.core;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.AbstractRpcNetty;
import indi.uhyils.rpc.netty.core.handler.RpcConsumerHandler;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import indi.uhyils.rpc.netty.spi.filter.invoker.LastConsumerInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.netty.util.FixedLengthQueue;
import indi.uhyils.rpc.util.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 15时06分
 */
@RpcSpi(single = false)
public class RpcNettyNormalConsumer extends AbstractRpcNetty {

    /**
     * 一个公平的可重入锁
     */
    private final ReentrantLock REENTRANT_LOCK = new ReentrantLock(Boolean.TRUE);

    /**
     * 客户端
     */
    private EventLoopGroup group;

    /**
     * 客户端channel
     */
    private ChannelFuture channelFuture;

    /*如果返回值来了,先判断是不是超时了, 如果超时了, 就直接释放内存, 如果没有超时,就先把值放入rpcResponse,然后唤醒*/

    /**
     * 存储返回值->如果返回值来了 但是还没有wait,就把返回值存在这里
     */
    private Map<Long, RpcData> rpcResponseMap = new ConcurrentHashMap<>();

    /**
     * 记录等待中的请求 -> 想获取的时候 还没有返回
     */
    private Map<Long, Condition> waitLock = new ConcurrentHashMap<>();

    /**
     * 超时的记录在这里,防止返回值的内存溢出
     */
    private FixedLengthQueue<Long> timeOutUnique = new FixedLengthQueue<>(200, Long.class);


    @Override
    public void init(Object... params) throws Exception {
        super.init(params);

        String host = (String) params[2];
        Integer port = (Integer) params[3];

        Bootstrap client = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        this.group = eventLoopGroup;
        setBootstrap(client);
        client.group(eventLoopGroup)
              .channel(NioSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.DEBUG))
              .handler(new ChannelInitializer<NioSocketChannel>() {

                  @Override
                  protected void initChannel(NioSocketChannel ch) throws Exception {
                      ChannelPipeline p = ch.pipeline();
                      p.addLast("length-decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 3, 4, 9, 0));
                      p.addLast("byte-to-object", new RpcConsumerHandler(getRpcCallBack(), RpcNettyNormalConsumer.this));
                  }
              });

        //连接服务器
        this.channelFuture = client.connect(host, port);
    }

    @Override
    public Boolean shutdown() {
        try {
            if (group != null) {
                this.group.shutdownGracefully();
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            LogUtil.error(e, "报错啦.夭寿啊");
            return Boolean.FALSE;
        }
    }

    @Override
    public RpcData sendMsg(RpcData rpcData) throws RpcException, ClassNotFoundException, InterruptedException {
        LastConsumerInvoker lastConsumerInvoker = new LastConsumerInvoker(this);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildConsumerSendInvokerChain(lastConsumerInvoker);
        FilterContext context = new FilterContext(rpcData);
        return rpcInvoker.invoke(context);
    }

    public boolean sendMsg(byte[] bytes) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(bytes);
        channelFuture.channel().writeAndFlush(buf);
        return Boolean.TRUE;
    }

    public RpcData wait(Long unique) {
        // 请求第一次
        if (rpcResponseMap.containsKey(unique)) {
            RpcData rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            return rpcData;
        }
        Condition condition = REENTRANT_LOCK.newCondition();
        // 等待第一次
        waitLock.put(unique, condition);
        REENTRANT_LOCK.lock();
        try {
            //阻塞
            condition.await(getTimeOut(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
        } finally {
            REENTRANT_LOCK.unlock();
        }

        //查询第二次是否存在
        if (rpcResponseMap.containsKey(unique)) {
            RpcData rpcData = rpcResponseMap.get(unique);
            rpcResponseMap.remove(unique);
            return rpcData;
        }
        timeOutUnique.add(unique);
        return null;
    }

    private void awaken(Long unique) {
        if (waitLock.containsKey(unique)) {
            Condition condition = waitLock.get(unique);
            waitLock.remove(unique);
            REENTRANT_LOCK.lock();
            try {
                //唤醒
                condition.signal();
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }
    }

    /**
     * 请求来了就执行这个
     *
     * @param rpcData
     */
    public void put(RpcData rpcData) {
        Long unique = rpcData.unique();
        // 先判断是否曾经执行过并且超时了
        Boolean contain = timeOutUnique.contain(unique);
        if (contain) {
            return;
        }
        rpcResponseMap.put(unique, rpcData);
        // 尝试唤醒
        awaken(unique);
    }
}
