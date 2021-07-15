package indi.uhyils.rpc.util;

import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月15日 23时15分
 */
class RpcAssertUtilTest {

    @Test
    void anyMethodName() {
        try {
            RpcAssertUtil.assertTrue(false, "测试断言时吞掉前N个错误日志堆栈");
        } catch (Throwable e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            RpcAssertUtil.assertTrue(stackTraceElement.getMethodName().equals(stackTrace[1].getMethodName()), "这里不可能错误的吧");
            return;
        }
        RpcAssertUtil.assertTrue(false, "测试断言时吞掉前N个错误日志堆栈 ------ 失败");
    }
}
