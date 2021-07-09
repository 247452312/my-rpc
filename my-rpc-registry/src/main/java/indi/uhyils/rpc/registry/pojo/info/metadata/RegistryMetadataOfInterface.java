package indi.uhyils.rpc.registry.pojo.info.metadata;

import java.io.Serializable;

/**
 * 元数据之接口信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 17时53分
 */
public class RegistryMetadataOfInterface implements Serializable {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 是否使用这个负载均衡
     */
    private Boolean useThisBalance;

    /**
     * 负载均衡
     */
    private Integer loadBalance;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Boolean getUseThisBalance() {
        return useThisBalance;
    }

    public void setUseThisBalance(Boolean useThisBalance) {
        this.useThisBalance = useThisBalance;
    }

    public Integer getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(Integer loadBalance) {
        this.loadBalance = loadBalance;
    }
}
