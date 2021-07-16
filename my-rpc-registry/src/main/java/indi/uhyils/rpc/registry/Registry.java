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
     * @param unique     此次调用的唯一标识
     * @param methodName 方法名称
     * @param paramType  方法参数类型
     * @param args       参数
     *
     * @return 返回值的json串
     *
     * @throws RpcException           rpc错误
     * @throws ClassNotFoundException 类没有找到错误
     * @throws InterruptedException   过程调用被打断错误(例如超时)
     */
    RpcData invoke(Long unique, String methodName, Class<T>[] paramType, Object[] args) throws RpcException, ClassNotFoundException, InterruptedException;


}
