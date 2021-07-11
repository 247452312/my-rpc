package indi.uhyils.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.cluster.ClusterFactory;
import indi.uhyils.rpc.config.RpcConfig;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.exception.MyRpcException;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.registry.mode.RegistryModeBuilder;
import indi.uhyils.rpc.registry.mode.RegistryModeFactory;
import indi.uhyils.rpc.registry.pojo.info.RegistryInfo;
import indi.uhyils.rpc.util.LogUtil;
import java.util.HashMap;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 15时43分
 */
@RpcSpi(single = false)
public class ProviderRegistry<T> extends AbstractRegistry<T> {

    @Override
    protected void doRegistryInit(Object[] objects) throws NacosException, MyRpcException {
        Object bean = objects[1];

        RpcConfig config = RpcConfigFactory.getInstance();
        Integer port = config.getProvider().getPort();

        // 初始化集群
        initCluster(bean, port);

        this.mode = RegistryModeFactory.create();
        RegistryInfo info = RegistryModeBuilder.initRegistryInfo(this.serviceClass);
        try {
            this.mode.registry(info);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }

    /**
     * 初始化集群
     *
     * @param bean 生产者的项目中的bean或实例
     * @param port 端口
     */
    private void initCluster(Object bean, Integer port) {
        HashMap<String, Object> beans = new HashMap<>(1);
        beans.put(this.serviceClass.getName(), bean);
        try {
            this.cluster = ClusterFactory.createDefaultProviderCluster(port, beans);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }


    @Override
    public RpcData invoke(Long unique, String methodName, Class[] paramType, Object[] args) throws RpcException, ClassNotFoundException, InterruptedException {
        return null;
    }
}
