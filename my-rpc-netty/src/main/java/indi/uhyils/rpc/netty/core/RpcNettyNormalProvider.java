package indi.uhyils.rpc.netty.core;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.AbstractRpcNetty;
import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.core.handler.RpcProviderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * netty服务提供者
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 13时42分
 */
@RpcSpi
public class RpcNettyNormalProvider extends AbstractRpcNetty {

    /**
     * 回调
     */
    private RpcCallBack callback;

    /**
     * 主线程,单线程
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程,多线程
     */
    private EventLoopGroup workerGroup;


    public RpcNettyNormalProvider() {

    }

    @Override
    public void init(Object... params) throws Exception {
        super.init(params);
        this.callback = (RpcCallBack) params[1];
        String host = (String) params[2];
        Integer port = (Integer) params[3];
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
         .channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 100)
         .handler(new LoggingHandler(LogLevel.DEBUG))
         .childHandler(new ChannelInitializer<SocketChannel>() {

             @Override
             public void initChannel(SocketChannel ch) throws Exception {
                 ChannelPipeline p = ch.pipeline();
                 p.addLast("length-decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 3, 4, 9, 0));
                 p.addLast("byte-to-object", new RpcProviderHandler(callback));
             }
         });

        b.bind(port).sync();
        setBootstrap(b);
    }

    @Override
    public Boolean shutdown() {
        try {
            if (this.bossGroup != null) {
                this.bossGroup.shutdownGracefully();
            }
            if (this.workerGroup != null) {
                this.workerGroup.shutdownGracefully();
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public RpcData sendMsg(RpcData rpcData) {
        return null;
    }

}
