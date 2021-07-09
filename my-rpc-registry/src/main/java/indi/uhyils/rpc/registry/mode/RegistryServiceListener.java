package indi.uhyils.rpc.registry.mode;

import indi.uhyils.rpc.cluster.Cluster;

/**
 * 监听器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 18时45分
 */
public interface RegistryServiceListener {

    /**
     * 设置集群
     *
     * @param cluster
     */
    void setCluster(Cluster cluster);
}
