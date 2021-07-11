package indi.uhyils.rpc.registry.pojo.info.metadata;

import java.io.Serializable;

/**
 * 方法信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 17时52分
 */
public class RegistryMetadataOfMethod implements Serializable {

    /**
     * 方法名称
     */
    private String name;


    /**
     * 方法参数类型
     */
    private String[] methodParamTypes;

    /**
     * 方法返回类型
     */
    private String returnType;

    /**
     * 是否使用这个负载均衡
     */
    private Boolean useThisBalance;

    /**
     * 负载均衡
     */
    private Integer loadBalance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMethodParamTypes() {
        return methodParamTypes;
    }

    public void setMethodParamTypes(String[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
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
