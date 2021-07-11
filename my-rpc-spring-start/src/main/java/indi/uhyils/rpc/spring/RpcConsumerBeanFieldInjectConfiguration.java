package indi.uhyils.rpc.spring;

import indi.uhyils.rpc.annotation.RpcReference;
import indi.uhyils.rpc.config.RpcConfig;
import indi.uhyils.rpc.proxy.RpcProxyFactory;
import indi.uhyils.rpc.registry.exception.RegistryException;
import indi.uhyils.rpc.spring.util.SpringUtil;
import indi.uhyils.rpc.util.LogUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * 注册类实例化后在的地方,包含了带有rpc注解的类的实例,类中如果带着RpcReference,那么动态注入rpc相关的类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月17日 09时55分
 */
public class RpcConsumerBeanFieldInjectConfiguration implements InstantiationAwareBeanPostProcessor {

    /**
     * 注册类的缓存
     */
    private volatile static Map<String, Object> consumerRegistryCache = new ConcurrentHashMap<>();

    @Autowired
    private RpcConfig config;

    public Object getRegistryOnCache(Class<?> clazz) throws RegistryException {
        try {
            return getRegistryOnCache(clazz.getName());
        } catch (ClassNotFoundException e) {
            LogUtil.error(this, e);
        }
        return null;
    }

    public Object getRegistryOnCache(String clazzName) throws ClassNotFoundException, RegistryException {
        if (consumerRegistryCache.containsKey(clazzName)) {
            return consumerRegistryCache.get(clazzName);
        }
        initAndGetConsumerByType(clazzName, config.getConsumer().getInConnection());
        return getRegistryOnCache(clazzName);
    }

    /**
     * 注入bean中的带有{@link RpcReference}属性或者方法
     *
     * @param bean
     * @param beanName
     *
     * @return
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) {
        fieldInject(bean);
        methodInject(bean);
        return Boolean.TRUE;
    }

    /**
     * 通过方法注入, 方法入参要同时为远程方法
     *
     * @param bean 类实例
     */
    private void methodInject(Object bean) {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(Boolean.TRUE);
            Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();
            RpcReference haveInitProxy = isHaveInitProxy(declaredAnnotations);
            if (haveInitProxy != null) {
                try {
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                    for (Class<?> type : parameterTypes) {
                        initAndGetConsumerByType(type, haveInitProxy.inConnection());
                    }
                    Object[] params = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        params[i] = consumerRegistryCache.get(parameterTypes[i].getName());
                    }
                    declaredMethod.invoke(bean, params);
                } catch (RegistryException | IllegalAccessException | InvocationTargetException e) {
                    LogUtil.error(this, e);
                }
            }
        }
    }

    /**
     * 通过属性进行注入
     *
     * @param bean 类实例
     */
    private void fieldInject(Object bean) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(Boolean.TRUE);
            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
            RpcReference reference = isHaveInitProxy(declaredAnnotations);
            if (reference != null) {
                try {
                    Class<?> type = declaredField.getType();
                    initAndGetConsumerByType(type, reference.inConnection());
                    declaredField.set(bean, consumerRegistryCache.get(type.getName()));

                } catch (RegistryException | IllegalAccessException e) {
                    LogUtil.error(this, e);
                }
            }
        }
    }

    private RpcReference isHaveInitProxy(Annotation[] declaredAnnotations) {
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation instanceof RpcReference) {
                return (RpcReference) declaredAnnotation;
            }
        }
        return null;
    }

    private void initAndGetConsumerByType(Class<?> type, boolean inConnection) throws RegistryException {
        if (consumerRegistryCache.get(type.getName()) == null) {
            synchronized (consumerRegistryCache) {
                if (consumerRegistryCache.get(type.getName()) == null) {
                    Object consumer;
                    // 如果在本项目内发现了bean,那么使用项目内部的bean
                    if (inConnection && SpringUtil.containsBean(type)) {
                        consumer = SpringUtil.getBean(type);
                    } else {
                        consumer = RpcProxyFactory.newProxy(type);
                    }
                    consumerRegistryCache.put(type.getName(), consumer);
                }
            }
        }
    }

    private void initAndGetConsumerByType(String typeName, boolean inConnection) throws RegistryException, ClassNotFoundException {
        initAndGetConsumerByType(Class.forName(typeName), inConnection);
    }

}
