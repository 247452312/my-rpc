package indi.uhyils.rpc.spi.param;

import indi.uhyils.rpc.spi.RpcSpiExtension;
import java.lang.reflect.Method;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月12日 20时53分
 */
public interface ParamTransExtension extends RpcSpiExtension {

    /**
     * 转换为指定接口的指定方法的类型
     *
     * @param arg             上一层的入参
     * @param interfaceClass  接口class
     * @param method          方法
     * @param methodTypeIndex 方法入参index
     * @param bean            实际执行实例
     *
     * @return
     */
    Object changeObjRequestParadigm(Object arg, Class interfaceClass, Method method, Integer methodTypeIndex, Object bean) throws ClassNotFoundException;
}
