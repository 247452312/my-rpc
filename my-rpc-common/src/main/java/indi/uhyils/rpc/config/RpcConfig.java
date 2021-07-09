package indi.uhyils.rpc.config;

import java.io.Serializable;

/**
 * rpc总配置
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月16日 10时27分
 */
public class RpcConfig implements Serializable {
    private static final long serialVersionUID = -5258855332613054596L;
    /**
     * 服务配置
     */
    private ApplicationConfig application = new ApplicationConfig();

    /**
     * 注册中心配置
     */
    private RegistryConfig registry = new RegistryConfig();

    /**
     * 远程连接配置
     */
    private ProtocolConfig protocol = new ProtocolConfig();

    /**
     * 服务提供者配置
     */
    private ProviderConfig provider = new ProviderConfig();

    /**
     * 消费者配置
     */
    private ConsumerConfig consumer = new ConsumerConfig();

    /**
     * 自定义扩展
     */
    private CustomConfig custom = new CustomConfig();

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

    public ProtocolConfig getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }

    public CustomConfig getCustom() {
        return custom;
    }

    public void setCustom(CustomConfig custom) {
        this.custom = custom;
    }
}
