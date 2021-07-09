package indi.uhyils.rpc.registry.pojo.info;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 17时32分
 */
public class RegistryProviderNecessaryInfo implements RegistryNecessaryInfo {


    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 此服务的ip
     */
    private String host;

    /**
     * 此服务的端口
     */
    private Integer port;

    /**
     * rpc版本
     */
    private Integer rpcVersion;

    /**
     * 是否需要健康检查
     */
    private Boolean health;

    /**
     * 所在集群名称
     */
    private String clusterName;

    /**
     * 权重
     */
    private Double weight = 20D;

    public static RegistryProviderNecessaryInfo build(String interfaceName, String host, Integer port, Integer rpcVersion, Boolean health, String clusterName, Double weight) {
        RegistryProviderNecessaryInfo build = new RegistryProviderNecessaryInfo();
        build.interfaceName = interfaceName;
        build.host = host;
        build.port = port;
        build.rpcVersion = rpcVersion;
        build.health = health;
        build.clusterName = clusterName;
        build.weight = weight;
        return build;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getRpcVersion() {
        return rpcVersion;
    }

    public void setRpcVersion(Integer rpcVersion) {
        this.rpcVersion = rpcVersion;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Boolean getHealth() {
        return health;
    }

    public void setHealth(Boolean health) {
        this.health = health;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
