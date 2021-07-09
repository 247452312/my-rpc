package indi.uhyils.rpc.exception;

/**
 * rpc的异常的公共父类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 15时06分
 */
public class RpcException extends Exception {
    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
