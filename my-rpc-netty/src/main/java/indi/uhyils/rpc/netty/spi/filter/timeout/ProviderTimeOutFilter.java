package indi.uhyils.rpc.netty.spi.filter.timeout;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactoryProducer;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.ProviderFilter;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcResult;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月22日 21时11分
 */
@RpcSpi(order = Integer.MAX_VALUE)
public class ProviderTimeOutFilter extends AbstractTimeOutFilter implements ProviderFilter {


    @Override
    public RpcResult invoke(RpcInvoker invoker, FilterContext invokerContext) throws RpcException, ClassNotFoundException, InterruptedException {
        return invoke0(invoker, invokerContext);
    }

    @Override
    protected RpcData invokeException(RpcData request, Long timeout) throws RpcException {
        return RpcFactoryProducer.build(RpcTypeEnum.RESPONSE).createTimeoutResponse(request, timeout);
    }

    @Override
    protected Long getTimeout() {
        return RpcConfigFactory.getInstance().getProvider().getTimeout();
    }
}
