package indi.uhyils.rpc.netty.spi.filter.invoker;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 08时20分
 */
public interface RpcInvoker {

    /**
     * 执行
     *
     * @param context
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    RpcResult invoke(FilterContext context) throws RpcException, ClassNotFoundException, InterruptedException;
}
