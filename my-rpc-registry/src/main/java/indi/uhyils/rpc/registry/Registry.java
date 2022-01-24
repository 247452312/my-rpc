package indi.uhyils.rpc.registry;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.spi.RpcSpiExtension;

/**
 * 这里的T指service
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 15时30分
 */
public interface Registry<T> extends RpcSpiExtension {

    /**
     * 远程调用
     *
     * @param rpcData rpc
     *
     * @return 返回值的json串
     *
     * @throws RpcException         rpc错误
     * @throws InterruptedException 过程调用被打断错误(例如超时)
     */
    RpcData invoke(RpcData rpcData) throws RpcException, InterruptedException;


}
