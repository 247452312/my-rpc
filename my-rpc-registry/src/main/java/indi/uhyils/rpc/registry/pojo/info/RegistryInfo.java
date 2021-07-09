package indi.uhyils.rpc.registry.pojo.info;

import indi.uhyils.rpc.registry.pojo.info.metadata.RegistryMetadata;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 17时21分
 */
public class RegistryInfo implements Serializable {

    /**
     * 注册时必要数据
     */
    private RegistryNecessaryInfo necessaryInfo;

    /**
     * 元数据
     */
    private RegistryMetadata metadata;


    public RegistryNecessaryInfo getNecessaryInfo() {
        return necessaryInfo;
    }

    public void setNecessaryInfo(RegistryNecessaryInfo necessaryInfo) {
        this.necessaryInfo = necessaryInfo;
    }

    public RegistryMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RegistryMetadata metadata) {
        this.metadata = metadata;
    }
}
