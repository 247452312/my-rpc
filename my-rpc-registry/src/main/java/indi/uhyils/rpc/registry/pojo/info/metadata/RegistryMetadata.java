package indi.uhyils.rpc.registry.pojo.info.metadata;

import java.io.Serializable;
import java.util.List;

/**
 * 注册时元数据
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 17时23分
 */
public class RegistryMetadata implements Serializable {

    /**
     * 方法信息
     */
    private List<RegistryMetadataOfMethod> methodInfos;

    /**
     * 接口信息
     */
    private RegistryMetadataOfInterface serviceInfo;

    public List<RegistryMetadataOfMethod> getMethodInfos() {
        return methodInfos;
    }

    public void setMethodInfos(List<RegistryMetadataOfMethod> methodInfos) {
        this.methodInfos = methodInfos;
    }

    public RegistryMetadataOfInterface getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(RegistryMetadataOfInterface serviceInfo) {
        this.serviceInfo = serviceInfo;
    }
}
