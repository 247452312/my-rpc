package indi.uhyils.rpc.util;

import indi.uhyils.rpc.exception.RpcAssertException;
import indi.uhyils.rpc.util.pojo.ThreadLayerInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月15日 22时06分
 */
public class RpcAssertUtil {

    /**
     * 断言正确
     *
     * @param condition
     * @param msg
     */
    public static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            LogUtil.assertError(msg);
            throw new RpcAssertException("throw exception: " + msg);
        }
    }


    /**
     * 获取日志错误
     *
     * @param up
     *
     * @return
     */
    public static ThreadLayerInfo getThreadLayerInfo(int up) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        assertTrue(up < stackTrace.length && up >= 0, String.format("获取堆栈传入信息不符,此时的堆栈共%d层,您要访问%d层", stackTrace.length, up));
        StackTraceElement stackTraceElement = stackTrace[up];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        String fileName = stackTraceElement.getFileName();
        int lineNumber = stackTraceElement.getLineNumber();
        return ThreadLayerInfo.build(className, methodName, fileName, lineNumber);
    }

    public static ThreadLayerInfo getThreadLayerInfo() {
        return getThreadLayerInfo(3);
    }
}
