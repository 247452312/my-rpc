package indi.uhyils.rpc.config;


/**
 * rpc配置工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月21日 20时03分
 */
public class RpcConfigFactory {

    /**
     * 唯一实例
     */
    private static RpcConfig config;

    /**
     * 此方法只在测试时使用,请勿在其他时候使用
     *
     * @return
     */
    @Deprecated
    public static RpcConfig newDefault() {
        RpcConfig rpcConfig = new RpcConfig();
        rpcConfig.getApplication().setName("rpc");
        ProviderConfig provider = rpcConfig.getProvider();
        provider.setPort(8080);
        RegistryConfig registry = rpcConfig.getRegistry();
        registry.setHost("192.168.1.101");
        registry.setPort(8848);
        return rpcConfig;
    }

    public static RpcConfig getInstance() {
        return config;
    }

    /**
     * 获取rpc自定义扩展配置
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getCustomOrDefault(String key, Object defaultValue) {
        return getInstance().getCustom().getCustom().getOrDefault(key, defaultValue);
    }

    public static void setRpcConfig(RpcConfig config) {
        RpcConfigFactory.config = config;
    }

}
