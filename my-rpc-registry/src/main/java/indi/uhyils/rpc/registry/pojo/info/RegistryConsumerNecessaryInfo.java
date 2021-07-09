package indi.uhyils.rpc.registry.pojo.info;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 17时29分
 */
public class RegistryConsumerNecessaryInfo implements RegistryNecessaryInfo {

    /**
     * 消费者ip
     */
    private String host;

    /**
     * rpc版本
     */
    private String rpcVersion;

    /**
     * 消费者服务名称
     */
    private String serviceName;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRpcVersion() {
        return rpcVersion;
    }

    public void setRpcVersion(String rpcVersion) {
        this.rpcVersion = rpcVersion;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
