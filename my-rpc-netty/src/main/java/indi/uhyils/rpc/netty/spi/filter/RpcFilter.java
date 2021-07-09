package indi.uhyils.rpc.netty.spi.filter;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcResult;
import indi.uhyils.rpc.spi.RpcSpiExtension;

/**
 * handler中执行类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 07时01分
 */
public interface RpcFilter extends RpcSpiExtension {


    /**
     * 执行netty传输过来的逻辑
     *
     * @param invoker
     * @param invokerContext
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    RpcResult invoke(RpcInvoker invoker, FilterContext invokerContext) throws RpcException, ClassNotFoundException, InterruptedException;
}
