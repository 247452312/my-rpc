package indi.uhyils.rpc.registry.exception;

import indi.uhyils.rpc.exception.RpcException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 09时01分
 */
public class RegistryException extends RpcException {

    public RegistryException() {
    }

    public RegistryException(String message) {
        super(message);
    }

    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryException(Throwable cause) {
        super(cause);
    }

    public RegistryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
