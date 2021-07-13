package indi.uhyils.rpc.spi.param.impl;

import com.alibaba.fastjson.JSON;
import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.spi.param.ParamTransExtension;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


/**
 * 默认转换类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月12日 21时04分
 */
@RpcSpi
public class ParamTransExtensionImpl implements ParamTransExtension {

    @Override
    public Object changeObjRequestParadigm(Object arg, Class interfaceClass, Method method, Integer methodTypeIndex, Object bean) {
        String requestJson = JSON.toJSONString(arg);
        Type genericParameterType = method.getGenericParameterTypes()[methodTypeIndex];
        return JSON.parseObject(requestJson, genericParameterType);
    }
}
