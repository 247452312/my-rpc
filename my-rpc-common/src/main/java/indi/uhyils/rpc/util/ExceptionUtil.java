package indi.uhyils.rpc.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年10月15日 09时32分
 */
public final class ExceptionUtil {

    private ExceptionUtil() {
    }

    /**
     * 解析错误
     *
     * @param e
     *
     * @return
     */
    public static String parseException(Throwable e) {
        String exceptionStr = null;
        if (e != null) {
            StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out, true));
            exceptionStr = out.toString();
        }
        return exceptionStr;
    }

}
