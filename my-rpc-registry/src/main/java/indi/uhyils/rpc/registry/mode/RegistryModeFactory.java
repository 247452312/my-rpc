package indi.uhyils.rpc.registry.mode;

import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.spi.RpcSpiManager;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月08日 08时50分
 */
public class RegistryModeFactory {

    /**
     * 默认的注册中心
     */
    private static final String DEFAULT_MODE_REGISTRY = "default_mode_registry";
    /**
     * 配置中注册中心的名称
     */
    private static final String REGISTRY_MODE_SPI_NAME = "registryModeSpi";

    private RegistryModeFactory() {
    }

    public static RegistryMode create() {
        String registryModelName = (String) RpcConfigFactory.getCustomOrDefault(REGISTRY_MODE_SPI_NAME, DEFAULT_MODE_REGISTRY);
        return (RegistryMode) RpcSpiManager.getExtensionByClass(RegistryMode.class, registryModelName);
    }
}
