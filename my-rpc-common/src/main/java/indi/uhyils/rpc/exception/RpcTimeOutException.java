package indi.uhyils.rpc.exception;

/**
 * rpc超时异常
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年12月31日 09时12分
 */
public class RpcTimeOutException extends RuntimeException {

    public RpcTimeOutException() {
        super();
    }

    public RpcTimeOutException(String message) {
        super(message);
    }

    public RpcTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcTimeOutException(Throwable cause) {
        super(cause);
    }

    protected RpcTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    

}
