package indi.uhyils.rpc.util;


import com.alibaba.fastjson.JSON;
import indi.uhyils.rpc.enums.LogTypeEnum;
import indi.uhyils.rpc.util.pojo.ThreadLayerInfo;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 08时43分
 */
public class LogUtil {

    /**
     * 使用层是第几层
     */
    private static final Integer USR_LAYER = 3;

    /**
     * 日志文件缓存地
     */
    private static Map<String, Logger> loggerMap = new HashMap<>();

    public static boolean isDebugEnabled(Object obj) {
        if (obj == null) {
            return Boolean.FALSE;
        }
        return isDebugEnabled(obj.getClass());
    }

    public static boolean isDebugEnabled(Class<?> clazz) {
        if (clazz == null) {
            return Boolean.FALSE;
        }
        String simpleName = clazz.getName();
        Logger logger = MapUtil.putIfAbsent(loggerMap, simpleName, () -> LoggerFactory.getLogger(clazz));
        if (logger != null) {
            return logger.isDebugEnabled();
        }
        return Boolean.TRUE;
    }

    public static void info(Class<?> cls, String msg) {
        writeLog(cls.getName(), msg, null, LogTypeEnum.INFO);
    }

    public static void info(String msg) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER);
        writeLog(threadLayerInfo.getClassName(), msg, null, LogTypeEnum.INFO);
    }

    public static void info(Class<?> cls, Throwable e) {
        writeLog(cls.getName(), null, e, LogTypeEnum.INFO);
    }

    public static void info(Object obj, String msg) {
        info(obj.getClass(), msg);
    }

    public static void info(Object obj, Throwable e) {
        info(obj.getClass(), e);
    }


    public static void debug(Class<?> cls, String msg) {
        writeLog(cls.getName(), msg, null, LogTypeEnum.DEBUG);
    }

    public static void debug(Class<?> cls, String msg, String... param) {
        debug(cls, String.format(msg, param));
    }

    public static void debug(Class<?> cls, String msg, Object... param) {
        String[] paramStrs = new String[param.length];
        for (int i = 0; i < param.length; i++) {
            paramStrs[i] = JSON.toJSONString(param[i]);
        }
        debug(cls, msg, paramStrs);
    }

    public static void debug(String msg) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER);
        writeLog(threadLayerInfo.getClassName(), msg, null, LogTypeEnum.DEBUG);
    }

    public static void debug(Class<?> cls, Throwable e) {
        writeLog(cls.getName(), null, e, LogTypeEnum.DEBUG);
    }

    public static void debug(Object obj, String msg) {
        debug(obj.getClass(), msg);
    }

    public static void debug(Object obj, Throwable e) {
        debug(obj.getClass(), e);
    }


    public static void warn(Class<?> cls, String msg) {
        writeLog(cls.getName(), msg, null, LogTypeEnum.WARN);
    }

    public static void warn(String msg) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER);
        writeLog(threadLayerInfo.getClassName(), msg, null, LogTypeEnum.WARN);
    }

    public static void warn(Class<?> cls, Throwable e) {
        writeLog(cls.getName(), null, e, LogTypeEnum.WARN);
    }

    public static void warn(Object obj, String msg) {
        warn(obj.getClass(), msg);
    }

    public static void warn(Object obj, Throwable e) {
        warn(obj.getClass(), e);
    }


    public static void error(Class<?> cls, String msg) {
        writeLog(cls.getName(), msg, null, LogTypeEnum.ERROR);
    }

    public static void error(String msg) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER);
        writeLog(threadLayerInfo.getClassName(), msg, null, LogTypeEnum.ERROR);
    }

    /**
     * 给{@link indi.uhyils.rpc.util.RpcAssertUtil}调用的,请其他的地方不要使用
     *
     * @param msg
     */
    public static void assertError(String msg) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER + 1);
        writeLog(threadLayerInfo.getClassName(), msg, null, LogTypeEnum.ERROR);
    }

    public static void error(Throwable e) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER);
        writeLog(threadLayerInfo.getClassName(), null, e, LogTypeEnum.ERROR);
    }

    public static void error(Class<?> cls, Throwable e) {
        writeLog(cls.getName(), null, e, LogTypeEnum.ERROR);
    }

    public static void error(Class<?> cls, Throwable e, String msg) {
        writeLog(cls.getName(), msg, e, LogTypeEnum.ERROR);
    }

    public static void error(Object cls, Throwable e, String msg) {
        error(cls.getClass(), e, msg);
    }

    public static void error(Object obj, String msg) {
        error(obj.getClass(), msg);
    }

    public static void error(Throwable e, String msg) {
        ThreadLayerInfo threadLayerInfo = RpcAssertUtil.getThreadLayerInfo(USR_LAYER);
        writeLog(threadLayerInfo.getClassName(), msg, e, LogTypeEnum.ERROR);
    }

    public static void error(Object obj, Throwable e) {
        error(obj.getClass(), e);
    }


    /**
     * 根据类型输出不同级别的对应类的日志
     *
     * @param className   名称,simpleName
     * @param msg         信息
     * @param logTypeEnum 类型
     */
    private static void writeLog(String className, String msg, Throwable throwable, LogTypeEnum logTypeEnum) {
        if (loggerMap.containsKey(className)) {
            Logger logger = loggerMap.get(className);
            choiceLogType(msg, throwable, logTypeEnum, logger);
            return;
        }
        Logger logger = LoggerFactory.getLogger(className);
        loggerMap.put(className, logger);
        choiceLogType(msg, throwable, logTypeEnum, logger);
    }


    private static void choiceLogType(String msg, Throwable throwable, LogTypeEnum logTypeEnum, Logger logger) {
        switch (logTypeEnum) {
            case INFO:
                logger.info(msg, throwable);
                break;
            case WARN:
                logger.warn(msg, throwable);
                break;
            case DEBUG:
                logger.debug(msg, throwable);
                break;
            case ERROR:
                logger.error(msg, throwable);
                break;
            default:
                logger.error("no this LogType!");
                break;
        }
    }


}
