package indi.uhyils.rpc.netty.core.handler;

import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import indi.uhyils.rpc.netty.spi.filter.invoker.LastProviderInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 10时58分
 */
public class RpcProviderHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 回调
     */
    private final RpcCallBack callback;


    public RpcProviderHandler(RpcCallBack callback) {
        this.callback = callback;

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        LastProviderInvoker invoker = new LastProviderInvoker(callback, msg);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildProviderAroundInvokerChain(invoker);
        FilterContext context = new FilterContext();
        rpcInvoker.invoke(context);
        Object result = context.get("result");
        send(ctx, (byte[]) result);
    }

    private void send(ChannelHandlerContext ctx, byte[] responseBytes) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(responseBytes);
        ctx.channel().writeAndFlush(buf);
    }

}
