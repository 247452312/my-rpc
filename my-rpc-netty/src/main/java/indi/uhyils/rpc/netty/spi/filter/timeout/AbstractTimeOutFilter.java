package indi.uhyils.rpc.netty.spi.filter.timeout;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.util.LogUtil;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月22日 21时14分
 */
public abstract class AbstractTimeOutFilter {

    private static ExecutorService es = new ThreadPoolExecutor(5, 100, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

    protected RpcData invoke0(RpcInvoker invoker, FilterContext invokerContext) throws RpcException, InterruptedException {
        RpcData requestData = invokerContext.getRequestData();
        final Long timeOut = getTimeout();
        final Future<RpcData> submit = es.submit(() -> {
            try {
                return invoker.invoke(invokerContext);
            } catch (RpcException | ClassNotFoundException | InterruptedException e) {
                LogUtil.error(e);
                throw new RuntimeException(e);
            }
        });
        try {
            return submit.get(timeOut, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            LogUtil.error(e);
            submit.cancel(Boolean.TRUE);
            return invokeException(requestData, timeOut);
        } catch (ExecutionException e) {
            LogUtil.error(e.getCause());
            submit.cancel(Boolean.TRUE);
            return invokeException(requestData, e);
        } catch (InterruptedException e) {
            throw e;
        }
    }

    /**
     * 超时时要做的事情
     *
     * @param request
     * @param timeout
     *
     * @return
     *
     * @throws RpcException
     */
    protected abstract RpcData invokeException(RpcData request, Long timeout) throws RpcException;

    /**
     * 异常时要做的事
     *
     * @param request
     * @param e
     *
     * @return
     */
    protected abstract RpcData invokeException(RpcData request, ExecutionException e);

    /**
     * 获取超时时间
     *
     * @return
     */
    protected abstract Long getTimeout();
}
