package indi.uhyils.rpc.netty.spi.filter.retry;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.config.RpcConfig;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.NormalRpcRequestFactory;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.exchange.pojo.data.RpcFactoryProducer;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.ConsumerFilter;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月23日 09时10分
 */
@RpcSpi(order = -50)
public class RetryFilter implements ConsumerFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        RpcConfig instance = RpcConfigFactory.getInstance();
        RpcData request = invokerContext.getRequestData();
        Integer retries = instance.getConsumer().getRetries();
        Throwable th = null;
        // 如果出错 重试
        for (int i = 0; i < retries; i++) {
            try {
                return invoker.invoke(invokerContext);
            } catch (RpcException e) {
                LogUtil.error(this, e);
                th = e;
            } catch (ClassNotFoundException e) {
                LogUtil.error(this, e);
                return null;
            }
        }
        NormalRpcRequestFactory build = (NormalRpcRequestFactory) RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        assert build != null;

        return build.createRetriesError(request, th);
    }
}
