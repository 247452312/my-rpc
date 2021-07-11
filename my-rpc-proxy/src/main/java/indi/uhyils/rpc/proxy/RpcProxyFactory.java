package indi.uhyils.rpc.proxy;

import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.proxy.generic.GenericService;
import indi.uhyils.rpc.proxy.handler.RpcProxyHandlerInterface;
import indi.uhyils.rpc.registry.exception.RegistryException;
import indi.uhyils.rpc.spi.RpcSpiManager;
import indi.uhyils.rpc.util.LogUtil;
import java.lang.reflect.Proxy;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月28日 06时45分
 */
public class RpcProxyFactory {

    /**
     * rpc spi 默认名称
     */
    private static final String RPC_SPI_DEFAULT_NAME = "default_rpc_spi_proxy";

    /**
     * 配置中的名称
     */
    private static final String RPC_SPI_CONFIG_PROXY_NAME = "proxy";

    private RpcProxyFactory() {

    }

    /**
     * 将一个正常的class加载为一个class类型的consumer
     *
     * @param clazz
     * @param <T>
     *
     * @return
     *
     * @throws RegistryException
     */
    public static <T> T newProxy(Class<T> clazz) throws RegistryException {
        if (!clazz.isInterface()) {
            throw new RegistryException("必须使用接口,您使用的是: " + clazz.getName());
        }
        // 从spi管理处获取一个指定的扩展点名称
        String spiClassName = RpcConfigFactory.getCustomOrDefault(RPC_SPI_CONFIG_PROXY_NAME, RPC_SPI_DEFAULT_NAME).toString();
        RpcProxyHandlerInterface extensionByClass = null;
        try {
            extensionByClass = (RpcProxyHandlerInterface) RpcSpiManager.getExtensionByClass(RpcProxyHandlerInterface.class, spiClassName, clazz);
        } catch (Exception e) {
            LogUtil.error(clazz, e);
        }

        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, extensionByClass);
        return (T) o;
    }

    /**
     * 获取泛化接口
     *
     * @param clazz   对应的类
     * @param generic 是否泛化接口
     *
     * @return
     *
     * @throws RegistryException
     */
    public static Object newProxy(Class<?> clazz, boolean generic) throws Exception {
        Object service = newProxy(clazz);
        return generic ? new GenericService<>(service) : service;
    }
}
