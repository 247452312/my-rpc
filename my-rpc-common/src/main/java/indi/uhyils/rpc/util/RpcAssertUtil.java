package indi.uhyils.rpc.util;

import indi.uhyils.rpc.exception.RpcAssertException;
import indi.uhyils.rpc.util.pojo.ThreadLayerInfo;
import java.util.function.Supplier;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月15日 22时06分
 */
public class RpcAssertUtil {

    /**
     * 断言正确
     *
     * @param condition 验证
     * @param msg
     */
    public static void assertTrue(boolean condition, String msg) {
        assertTrue(condition, 3, msg);
    }

    /**
     * 断言正确
     *
     * @param condition 验证
     */
    public static void assertTrue(boolean condition) {
        assertTrue(condition, 3, "断言错误");
    }

    /**
     * 断言正确
     *
     * @param condition        验证
     * @param removeLayerCount 要删除的顶层堆栈的层数
     * @param msg
     */
    public static void assertTrue(boolean condition, int removeLayerCount, String msg) {
        if (!condition) {
            RpcAssertException rpcAssertException = new RpcAssertException("throw exception: " + msg);
            removeExceptionTrace(rpcAssertException, removeLayerCount);
            LogUtil.error(rpcAssertException);
            throw rpcAssertException;
        }
    }


    /**
     * 断言正确
     *
     * @param condition
     * @param msgFunction
     */
    public static void assertTrue(boolean condition, Supplier<String> msgFunction) {
        assertTrue(condition, 3, msgFunction);
    }

    /**
     * 断言正确
     *
     * @param condition
     * @param removeLayerCount
     * @param msgFunction
     */
    public static void assertTrue(boolean condition, int removeLayerCount, Supplier<String> msgFunction) {
        if (!condition) {
            String msg = msgFunction.get();
            RpcAssertException rpcAssertException = new RpcAssertException("throw exception: " + msg);
            removeExceptionTrace(rpcAssertException, removeLayerCount);
            throw rpcAssertException;
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


    /**
     * 删除异常的顶层堆栈信息
     *
     * @param rpcAssertException 异常
     * @param removeLayerCount   要删除异常的顶层堆栈信息数量
     */
    private static void removeExceptionTrace(RpcAssertException rpcAssertException, int removeLayerCount) {
        StackTraceElement[] stackTrace = rpcAssertException.getStackTrace();
        int newCount = stackTrace.length - removeLayerCount;
        StackTraceElement[] newStackTrace = new StackTraceElement[newCount];
        System.arraycopy(stackTrace, removeLayerCount - 1, newStackTrace, 0, newCount);
        rpcAssertException.setStackTrace(newStackTrace);
    }
}
