package indi.uhyils.rpc.registry.mode.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.cluster.Cluster;
import indi.uhyils.rpc.config.RegistryConfig;
import indi.uhyils.rpc.config.RpcConfig;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.netty.enums.RpcNettyTypeEnum;
import indi.uhyils.rpc.registry.content.RegistryContent;
import indi.uhyils.rpc.registry.exception.RegistryException;
import indi.uhyils.rpc.registry.exception.RegistryTypeException;
import indi.uhyils.rpc.registry.mode.RegistryMode;
import indi.uhyils.rpc.registry.mode.RegistryServiceListener;
import indi.uhyils.rpc.registry.pojo.info.RegistryConsumerNecessaryInfo;
import indi.uhyils.rpc.registry.pojo.info.RegistryInfo;
import indi.uhyils.rpc.registry.pojo.info.RegistryNecessaryInfo;
import indi.uhyils.rpc.registry.pojo.info.RegistryProviderNecessaryInfo;
import indi.uhyils.rpc.registry.pojo.info.metadata.RegistryMetadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认的注册中心,如果有其他的.请自行实现
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 18时44分
 */
@RpcSpi(single = false)
public class RegistryNacosMode implements RegistryMode {

    /**
     * 注册元数据时的key
     */
    private static final String METADATA = "metadata";
    private static final Long TIME_OUT = 3000L;
    /**
     * nacos的Config
     */
    private final ConfigService nacosConfig;
    /**
     * nacos的naming
     */
    private final NamingService nacosNaming;

    /**
     * 此nacos连接的类型
     */
    private RpcNettyTypeEnum type;

    /**
     * 服务地址
     */
    private String serverAddr;


    /**
     * @throws NacosException
     */
    public RegistryNacosMode() throws NacosException {
        RpcConfig rpcConfig = RpcConfigFactory.getInstance();
        RegistryConfig registry = rpcConfig.getRegistry();
        this.serverAddr = registry.getHost() + ":" + registry.getPort();
        nacosConfig = ConfigFactory.createConfigService(serverAddr);
        nacosNaming = NamingFactory.createNamingService(serverAddr);
    }

    @Override
    public String getConfig(String interfaceName) throws NacosException {
        return nacosConfig.getConfig(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, TIME_OUT);
    }

    @Override
    public Boolean publishConfig(String interfaceName, String content) throws NacosException {
        return nacosConfig.publishConfig(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, content);
    }

    @Override
    public Boolean removeConfig(String interfaceName) throws NacosException {
        return nacosConfig.removeConfig(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME);
    }

    @Override
    public void addConfigListener(String interfaceName, final RegistryServiceListener listener) throws NacosException {
        nacosConfig.addListener(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, (RegistryNacosServiceListener) listener);
    }

    @Override
    public void removeConfigListener(String interfaceName, RegistryServiceListener listener) {
        nacosConfig.removeListener(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, (RegistryNacosServiceListener) listener);
    }

    @Override
    public List<RegistryInfo> getTargetInterfaceInfo(String interfaceName) throws NacosException {
        List<Instance> allInstances = nacosNaming.getAllInstances(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME);
        List<RegistryInfo> result = new ArrayList<>(allInstances.size());
        for (Instance instance : allInstances) {
            RegistryProviderNecessaryInfo providerNecessaryInfo = RegistryProviderNecessaryInfo.build(instance.getServiceName(), instance.getIp(), instance.getPort(), null, instance.isHealthy(), instance.getClusterName(), instance.getWeight());
            Map<String, String> metadata = instance.getMetadata();
            RegistryMetadata registryMetadata = JSON.parseObject(metadata.get(METADATA), RegistryMetadata.class);

            RegistryInfo registryInfo = new RegistryInfo();
            registryInfo.setNecessaryInfo(providerNecessaryInfo);
            registryInfo.setMetadata(registryMetadata);
            result.add(registryInfo);
        }

        return result;
    }

    @Override
    public Boolean registry(RegistryInfo info) throws NacosException, RegistryException {

        Instance instance = new Instance();
        RegistryNecessaryInfo necessaryInfo = info.getNecessaryInfo();
        if (necessaryInfo instanceof RegistryConsumerNecessaryInfo) {
            throw new RegistryTypeException();
        }
        RegistryProviderNecessaryInfo providerNecessaryInfo = (RegistryProviderNecessaryInfo) necessaryInfo;
        instance.setIp(providerNecessaryInfo.getHost());
        instance.setPort(providerNecessaryInfo.getPort());
        instance.setHealthy(providerNecessaryInfo.getHealth());
        instance.setWeight(providerNecessaryInfo.getWeight());
        instance.setServiceName(providerNecessaryInfo.getInterfaceName());
        instance.setClusterName(providerNecessaryInfo.getClusterName());

        Map<String, String> instanceMeta = new HashMap<>(2);
        RegistryMetadata metadata = info.getMetadata();

        instanceMeta.put(METADATA, JSON.toJSONString(metadata));
        instance.setMetadata(instanceMeta);


        nacosNaming.registerInstance(providerNecessaryInfo.getInterfaceName(), RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, instance);
        return Boolean.TRUE;
    }

    @Override
    public void removeInstance(String interfaceName, String ip, int port) throws NacosException {
        nacosNaming.deregisterInstance(interfaceName, ip, port);
    }

    @Override
    public void addServiceListener(String interfaceName, String groupName, RegistryServiceListener listener) throws NacosException {
        nacosNaming.subscribe(interfaceName, groupName, (RegistryNacosServiceListener) listener);
    }

    @Override
    public void removeServiceListener(String interfaceName, RegistryServiceListener listener) throws NacosException {
        nacosNaming.unsubscribe(interfaceName, (RegistryNacosServiceListener) listener);
    }

    @Override
    public void setType(RpcNettyTypeEnum type) {
        this.type = type;
    }

    @Override
    public void createListener(String interfaceName, Cluster cluster) throws NacosException {
        RegistryServiceListener listener = new RegistryNacosServiceListener(this, interfaceName);
        listener.setCluster(cluster);
        this.addServiceListener(interfaceName, RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, listener);
    }
}
