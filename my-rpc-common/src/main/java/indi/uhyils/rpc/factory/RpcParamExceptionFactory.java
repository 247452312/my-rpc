package indi.uhyils.rpc.factory;

import indi.uhyils.rpc.exception.RpcRunTimeException;

import java.io.Serializable;


/**
 * rpc 参数错误问题
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月03日 09时12分
 */
public class RpcParamExceptionFactory implements Serializable {

    /**
     * 创建一个rpcRunTime错误
     *
     * @param throwable 错误
     * @param result    结果
     * @param objects   参数
     * @return
     */
    public static RpcRunTimeException newException(Throwable throwable, String result, Object... objects) {
        return new RpcRunTimeException(String.format(result, objects), throwable);
    }

    /**
     * 创建一个rpcRunTime错误
     *
     * @param result  结果
     * @param objects 参数
     * @return
     */
    public static RpcRunTimeException newException(String result, Object... objects) {
        return new RpcRunTimeException(String.format(result, objects));
    }
}
