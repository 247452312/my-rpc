package indi.uhyils.rpc.proxy.handler;

import com.alibaba.fastjson.JSON;
import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.config.ConsumerConfig;
import indi.uhyils.rpc.config.RpcConfig;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.content.Content;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import indi.uhyils.rpc.exchange.pojo.demo.response.content.RpcNormalResponseContent;
import indi.uhyils.rpc.factory.RpcParamExceptionFactory;
import indi.uhyils.rpc.netty.spi.step.RpcStep;
import indi.uhyils.rpc.netty.spi.step.template.ConsumerResponseObjectExtension;
import indi.uhyils.rpc.proxy.exception.MethodAndArgsNoMatchException;
import indi.uhyils.rpc.registry.Registry;
import indi.uhyils.rpc.registry.RegistryFactory;
import indi.uhyils.rpc.spi.RpcSpiManager;
import indi.uhyils.rpc.util.IdUtil;
import indi.uhyils.rpc.util.LogUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 默认的rpc代理类,自己实现代理见{@link RpcProxyHandlerInterface}
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月28日 06时47分
 * @see RpcProxyHandlerInterface
 */
@RpcSpi(single = false)
public class RpcProxyDefaultHandler implements RpcProxyHandlerInterface {

    private static final String TO_STRING = "toString";


    /**
     * 这个handler代理的类
     */
    private Class<?> type;

    /**
     * 注册类
     */
    private Registry registry;

    /**
     * 消费者接收回复Object拦截器
     */
    private List<ConsumerResponseObjectExtension> consumerResponseObjectExtensions;

    /**
     * 唯一id生成器(雪花)
     */
    private IdUtil idUtil;

    /**
     * 创建时初始化
     *
     * @param clazz
     */
    public RpcProxyDefaultHandler(Class<?> clazz) {
        init(clazz);
    }

    /**
     * 创建时不初始化 兼容spi
     */
    public RpcProxyDefaultHandler() {
    }

    @Override
    public void init(Object... params) {
        if (params == null || !(params[0] instanceof Class)) {
            throw RpcParamExceptionFactory.newException("<RpcProxyDefaultHandler>参数不正确,应该为%s,实际为:%s", "java.lang.Class[0]", JSON.toJSONString(params));
        }
        Class<?> clazz = (Class<?>) params[0];
        this.type = clazz;
        this.idUtil = new IdUtil();
        // 如果懒加载,那么就不加载
        if (isCheck()) {
            initRegistry(clazz);
        }
        consumerResponseObjectExtensions = RpcSpiManager.getExtensionsByClass(RpcStep.class, ConsumerResponseObjectExtension.class);
    }

    private void initRegistry(Class<?> clazz) {
        try {
            this.registry = RegistryFactory.createConsumer(clazz);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }

    /**
     * 配置中是否使用了懒加载
     *
     * @return
     */
    private boolean isCheck() {
        RpcConfig instance = RpcConfigFactory.getInstance();
        ConsumerConfig consumer = instance.getConsumer();
        return consumer.getCheck();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (registry == null) {
            initRegistry(type);
        }

        // 防止proxy自动调用toString方法导致的报错
        if (TO_STRING.equals(method.getName())) {
            return "this is the interface,it`s name is " + proxy.getClass().getSimpleName();
        }
        // 验证method和arg是否有关系
        validateArgsWithMethodParams(args, method);
        // registry执行方法
        RpcData invoke = registry.invoke(idUtil.newId(), method.getName(), Arrays.stream(args).map(Object::getClass).toArray(Class[]::new), args);
        Class<?> returnType = null;
        Object result = null;
        for (RpcHeader rpcHeader : invoke.rpcHeaders()) {
            if (Content.HEADER_RETURN_TYPE.equals(rpcHeader.getName())) {
                returnType = Class.forName(rpcHeader.getValue());
            }
        }
        RpcNormalResponseContent content = (RpcNormalResponseContent) invoke.content();
        String contentString = content.getResponseContent();
        if (returnType == null) {
            result = contentString;
        } else {
            result = JSON.parseObject(contentString, returnType);
        }
        //后置自定义处理
        result = postProcessing(invoke, result);
        return result;
    }

    /**
     * 验证method的入参和args是否相同
     *
     * @param args   入参
     * @param method 方法本身
     */
    private void validateArgsWithMethodParams(Object[] args, Method method) throws MethodAndArgsNoMatchException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (args.length != parameterTypes.length) {
            throw new MethodAndArgsNoMatchException(args.length, method);
        }

        for (int i = 0; i < args.length; i++) {
            if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
                throw new MethodAndArgsNoMatchException(args[i], parameterTypes[i], method, i);
            }
        }

    }

    /**
     * spi加载的类进行后置处理
     *
     * @param rpcData
     * @param result
     *
     * @return
     */
    private Object postProcessing(RpcData rpcData, Object result) {
        for (ConsumerResponseObjectExtension consumerResponseObjectExtension : consumerResponseObjectExtensions) {
            result = consumerResponseObjectExtension.doFilter(result, rpcData);
        }
        return result;
    }
}
