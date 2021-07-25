package indi.uhyils.rpc.netty.core.handler;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.exchange.pojo.data.RpcFactory;
import indi.uhyils.rpc.exchange.pojo.data.RpcFactoryProducer;
import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import indi.uhyils.rpc.netty.spi.filter.invoker.LastProviderInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.netty.spi.step.RpcStep;
import indi.uhyils.rpc.netty.spi.step.template.ProviderRequestByteExtension;
import indi.uhyils.rpc.spi.RpcSpiManager;
import indi.uhyils.rpc.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 10时58分
 */
public class RpcProviderHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 回调
     */
    private final RpcCallBack callback;

    /**
     * 生产者接收请求byte拦截器
     */
    private List<ProviderRequestByteExtension> providerRequestByteFilters;

    public RpcProviderHandler(RpcCallBack callback) {
        this.callback = callback;
        providerRequestByteFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ProviderRequestByteExtension.class);

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = receiveByte(msg);
        // ProviderRequestByteFilter
        for (ProviderRequestByteExtension filter : providerRequestByteFilters) {
            bytes = filter.doFilter(bytes);
        }

        /*解析*/
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        // 获取到的Request
        assert build != null;

        RpcData requestRpcData = build.createByBytes(bytes);

        LastProviderInvoker invoker = new LastProviderInvoker(callback);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildProviderAroundInvokerChain(invoker);
        FilterContext context = new FilterContext(requestRpcData);
        RpcData invoke = rpcInvoker.invoke(context);
        send(ctx, invoke.toBytes());
    }

    private void send(ChannelHandlerContext ctx, byte[] responseBytes) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(responseBytes);
        ctx.channel().writeAndFlush(buf);
    }

    private byte[] receiveByte(ByteBuf msg) {
        /*接收并释放byteBuf*/
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        return bytes;
    }

}
