package indi.uhyils.rpc.netty.spi.filter.invoker;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.demo.factory.NormalRpcResponseFactory;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactoryProducer;
import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.pojo.InvokeResult;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.step.RpcStep;
import indi.uhyils.rpc.netty.spi.step.template.ProviderRequestByteExtension;
import indi.uhyils.rpc.netty.spi.step.template.ProviderRequestDataExtension;
import indi.uhyils.rpc.netty.spi.step.template.ProviderResponseByteExtension;
import indi.uhyils.rpc.netty.spi.step.template.ProviderResponseDataExtension;
import indi.uhyils.rpc.spi.RpcSpiManager;
import indi.uhyils.rpc.util.LogUtil;
import io.netty.buffer.ByteBuf;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 07时27分
 */
public class LastProviderInvoker implements RpcInvoker {

    /**
     * 回调
     */
    private final RpcCallBack callback;

    /**
     * 生产者接收请求byte拦截器
     */
    private List<ProviderRequestByteExtension> providerRequestByteFilters;

    /**
     * 生产者接收请求data拦截器
     */
    private List<ProviderRequestDataExtension> providerRequestDataFilters;

    /**
     * 生产者接收请求处理完成后的data拦截器
     */
    private List<ProviderResponseDataExtension> providerResponseDataFilters;

    /**
     * 生产者接收请求处理完成后byte拦截器
     */
    private List<ProviderResponseByteExtension> providerResponseByteFilters;

    private ByteBuf msg;


    public LastProviderInvoker(RpcCallBack callback, ByteBuf msg) {
        this.callback = callback;
        this.msg = msg;
        providerRequestByteFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ProviderRequestByteExtension.class);
        providerRequestDataFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ProviderRequestDataExtension.class);
        providerResponseDataFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ProviderResponseDataExtension.class);
        providerResponseByteFilters = RpcSpiManager.getExtensionsByClass(RpcStep.class, ProviderResponseByteExtension.class);
    }

    @Override
    public RpcResult invoke(FilterContext context) throws RpcException, ClassNotFoundException {
        RpcResult rpcResult = context.getRpcResult();

        byte[] bytes = receiveByte(msg);
        // ProviderRequestByteFilter
        for (ProviderRequestByteExtension filter : providerRequestByteFilters) {
            bytes = filter.doFilter(bytes);
        }
        RpcData rpcData = null;
        try {
            rpcData = callback.getRpcData(bytes);

            // ProviderRequestDataFilter
            for (ProviderRequestDataExtension filter : providerRequestDataFilters) {
                rpcData = filter.doFilter(rpcData);
            }
            RpcData assembly = doInvoke(rpcData);
            // ProviderResponseDataFilter
            for (ProviderResponseDataExtension filter : providerResponseDataFilters) {
                assembly = filter.doFilter(assembly);
            }
            byte[] result = assembly.toBytes();
            for (ProviderResponseByteExtension providerResponseByteFilter : providerResponseByteFilters) {
                result = providerResponseByteFilter.doFilter(result);
            }
            context.put("result", result);
            RpcData byBytes = RpcFactoryProducer.build(RpcTypeEnum.RESPONSE).createByBytes(result);
            rpcResult.set(byBytes);
        } catch (Throwable e) {
            LogUtil.error(this, e);
            if (rpcData != null) {
                RpcData assembly = new NormalRpcResponseFactory().createErrorResponse(rpcData.unique(), e, null);
                context.put("result", assembly.toBytes());
                rpcResult.set(assembly);
            }
        }
        return rpcResult;
    }

    /**
     * 执行业务
     *
     * @param rpcData
     *
     * @return
     *
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    private RpcData doInvoke(RpcData rpcData) throws RpcException, ClassNotFoundException {
        InvokeResult invoke = callback.invoke(rpcData.content());
        RpcData assembly = callback.assembly(rpcData.unique(), invoke);
        return assembly;
    }

    private byte[] receiveByte(ByteBuf msg) {
        /*接收并释放byteBuf*/
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        return bytes;
    }

}
