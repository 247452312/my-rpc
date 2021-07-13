package indi.uhyils.rpc.util;

import com.alibaba.fastjson.JSON;
import indi.uhyils.rpc.spi.RpcSpiManager;
import indi.uhyils.rpc.spi.param.ParamTransExtension;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月16日 13时10分
 */
public class RpcObjectTransUtil {

    /**
     * 泛型T
     */
    private static final String PARADIGM_STRING = "T";

    /**
     * 泛型左括号
     */
    private static final String GENERIC_LEFT_BRACKET = "<";

    /**
     * 泛型右括号
     */
    private static final String GENERIC_RIGHT_BRACKET = ">";

    /**
     * 参数转换扩展
     */
    private static List<ParamTransExtension> paramTransExtensions;

    /**
     * 转换为指定接口的指定方法的类型
     *
     * @param arg             请求参数
     * @param interfaceClass  接口class
     * @param method          方法
     * @param methodTypeIndex 参数在方法中的下标
     * @param bean            rpcBean
     *
     * @return
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public static Object changeObjRequestParadigm(Object arg, Class interfaceClass, Method method, Integer methodTypeIndex, Object bean) throws ClassNotFoundException, NoSuchMethodException {
        if (paramTransExtensions == null) {
            paramTransExtensions = RpcSpiManager.getExtensionsByClass(ParamTransExtension.class, ParamTransExtension.class);
        }
        for (ParamTransExtension paramTransExtension : paramTransExtensions) {
            arg = paramTransExtension.changeObjRequestParadigm(arg, interfaceClass, method, methodTypeIndex, bean);
        }
        return arg;
    }


    /**
     * 转换为指定接口的指定方法的类型
     *
     * @param arg            请求参数们
     * @param interfaceClass 接口class
     * @param method         方法
     *
     * @return
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public static Object[] changeObjRequestParadigm(Object[] arg, Class interfaceClass, Method method, Object bean) throws ClassNotFoundException, NoSuchMethodException {
        // 判断参数是否合理
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        if (genericParameterTypes.length != arg.length) {
            throw new RuntimeException(String.format("入参实际参数和出参参数数量不一致,入参个数应为:%d个 传入%d个", genericParameterTypes.length, arg.length));
        }

        Object[] result = new Object[arg.length];
        for (int i = 0; i < arg.length; i++) {
            if (arg[i] instanceof JSON) {
                result[i] = changeObjRequestParadigm(arg[i], interfaceClass, method, i, bean);
            } else {
                result[i] = arg[i];
            }
        }
        return result;
    }
}
