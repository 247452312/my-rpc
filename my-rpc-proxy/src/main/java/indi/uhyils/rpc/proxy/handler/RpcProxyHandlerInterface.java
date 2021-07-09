package indi.uhyils.rpc.proxy.handler;

import indi.uhyils.rpc.spi.RpcSpiExtension;

import java.lang.reflect.InvocationHandler;

/**
 * rpc的interface扩展点
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月01日 08时23分
 */
public interface RpcProxyHandlerInterface extends InvocationHandler, RpcSpiExtension {


}
