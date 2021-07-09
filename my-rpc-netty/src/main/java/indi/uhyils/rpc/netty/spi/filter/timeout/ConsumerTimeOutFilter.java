package indi.uhyils.rpc.netty.spi.filter.timeout;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.factory.RpcFactoryProducer;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.ConsumerFilter;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcResult;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月21日 20时59分
 */
@RpcSpi(order = Integer.MAX_VALUE)
public class ConsumerTimeOutFilter extends AbstractTimeOutFilter implements ConsumerFilter {


    @Override
    public RpcResult invoke(final RpcInvoker invoker, final FilterContext invokerContext) throws RpcException, ClassNotFoundException, InterruptedException {
        return invoke0(invoker, invokerContext);
    }

    @Override
    protected RpcData invokeException(RpcData request, Long timeout) throws RpcException {
        return RpcFactoryProducer.build(RpcTypeEnum.REQUEST).createTimeoutResponse(request, timeout);
    }

    @Override
    protected Long getTimeout() {
        return RpcConfigFactory.getInstance().getConsumer().getTimeout();
    }
}
