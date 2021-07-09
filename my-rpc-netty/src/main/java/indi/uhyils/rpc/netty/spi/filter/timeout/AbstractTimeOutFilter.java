package indi.uhyils.rpc.netty.spi.filter.timeout;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcResult;

import java.util.concurrent.*;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月22日 21时14分
 */
public abstract class AbstractTimeOutFilter {

    private static ExecutorService es = new ThreadPoolExecutor(5, 100, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

    protected RpcResult invoke0(RpcInvoker invoker, FilterContext invokerContext) throws RpcException, InterruptedException {
        RpcResult rpcResult = invokerContext.getRpcResult();
        RpcData rpcData = rpcResult.get();
        final Long timeOut = getTimeout();
        final Future<RpcResult> submit = es.submit(() -> invoker.invoke(invokerContext));
        try {
            return submit.get(timeOut, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | TimeoutException e) {
            submit.cancel(Boolean.TRUE);
            RpcData response = invokeException(rpcData, timeOut);
            invokerContext.getRpcResult().set(response);
            return invokerContext.getRpcResult();
        } catch (InterruptedException e) {
            throw e;
        }
    }

    /**
     * 超时时要做的事情
     *
     * @param request
     * @param timeout
     * @return
     * @throws RpcException
     */
    protected abstract RpcData invokeException(RpcData request, Long timeout) throws RpcException;

    /**
     * 获取超时时间
     *
     * @return
     */
    protected abstract Long getTimeout();
}
