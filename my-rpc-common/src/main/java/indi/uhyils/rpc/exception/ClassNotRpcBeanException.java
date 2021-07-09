package indi.uhyils.rpc.exception;

/**
 * 要找的类不是rpc的类异常
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月23日 08时49分
 */
public class ClassNotRpcBeanException extends RpcException {

    public ClassNotRpcBeanException(String message) {
        super("此类不是rpc的bean: " + message);
    }

    public ClassNotRpcBeanException(Class<?> clazz) {
        super("此类不是rpc的bean: " + clazz.getName());
    }
}
