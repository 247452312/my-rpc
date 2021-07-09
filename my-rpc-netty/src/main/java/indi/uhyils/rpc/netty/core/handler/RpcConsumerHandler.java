package indi.uhyils.rpc.netty.core.handler;

import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.core.RpcNettyNormalConsumer;
import indi.uhyils.rpc.netty.spi.step.RpcStep;
import indi.uhyils.rpc.netty.spi.step.template.ConsumerResponseByteExtension;
import indi.uhyils.rpc.netty.spi.step.template.ConsumerResponseDataExtension;
import indi.uhyils.rpc.spi.RpcSpiManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 10时58分
 */
public class RpcConsumerHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 回调
     */
    private RpcCallBack callBack;
    /**
     * 观察者模式
     */
    private RpcNettyNormalConsumer netty;

    /**
     * 消费者接收回复byte拦截器
     */
    private List<ConsumerResponseByteExtension> consumerResponseByteFilters;
    /**
     * 消费者接收回复data拦截器
     */
    private List<ConsumerResponseDataExtension> consumerResponseDataFilters;

    public RpcConsumerHandler(RpcCallBack callBack, RpcNettyNormalConsumer netty) {
        this.callBack = callBack;
        this.netty = netty;
        consumerResponseByteFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ConsumerResponseByteExtension.class);
        consumerResponseDataFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ConsumerResponseDataExtension.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        // ConsumerResponseByteFilter
        for (ConsumerResponseByteExtension filter : consumerResponseByteFilters) {
            bytes = filter.doFilter(bytes);
        }
        RpcData rpcData = callBack.getRpcData(bytes);
        // ConsumerResponseDataFilter
        for (ConsumerResponseDataExtension filter : consumerResponseDataFilters) {
            rpcData = filter.doFilter(rpcData);
        }
        netty.put(rpcData);
    }
}
