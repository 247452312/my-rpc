package indi.uhyils.rpc.proxy.exception;

import indi.uhyils.rpc.exception.RpcException;
import java.lang.reflect.Method;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月08日 20时43分
 */
public class MethodAndArgsNoMatchException extends RpcException {

    public MethodAndArgsNoMatchException(int argsLength, Method method) {
        super(String.format("方法入参数量:%d 实际方法入参数量:%d 方法:%s", argsLength, method.getParameterTypes().length, method.getName()));
    }

    public MethodAndArgsNoMatchException(Object arg, Class<?> paramType, Method method, int index) {
        super(String.format("方法 %s第%d个入参,参数入参不匹配: %s to %s", method.getName(), index, arg.getClass().getName(), paramType.getName()));
    }
}
