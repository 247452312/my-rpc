package indi.uhyils.rpc.registry.mode.nacos;

import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import indi.uhyils.rpc.cluster.pojo.NettyInfo;
import indi.uhyils.rpc.registry.mode.AbstractRegistryServiceListener;
import indi.uhyils.rpc.registry.mode.RegistryMode;
import indi.uhyils.rpc.registry.pojo.info.RegistryInfo;
import indi.uhyils.rpc.registry.pojo.info.RegistryProviderNecessaryInfo;
import indi.uhyils.rpc.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * nacos注册中心监听器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月26日 18时49分
 */
public class RegistryNacosServiceListener extends AbstractRegistryServiceListener implements EventListener, Listener {

    /**
     * 接口名称
     */
    private String interfaceName;


    /**
     * 验证用注册中心
     */
    private RegistryMode mode;


    public RegistryNacosServiceListener(RegistryMode mode, String interfaceName) {
        this.interfaceName = interfaceName;
        this.mode = mode;
    }

    @Override
    public void onEvent(Event event) {
        if (event == null) {
            return;
        }
        if (event instanceof NamingEvent) {
            LogUtil.info("name!!!!!!!!!!!!!!!!");
            try {
                doServiceEvent((NamingEvent) event);
            } catch (Exception e) {
                LogUtil.error(this, e);
            }
        } else {
            LogUtil.error("注册中心事件未响应" + event.getClass().getName());
        }
    }

    /**
     * service的event
     *
     * @param event
     */
    private void doServiceEvent(NamingEvent event) throws Exception {
        ArrayList<NettyInfo> nettyInfos = new ArrayList<>();
        List<Instance> instances = event.getInstances();
        if (instances.isEmpty()) {
            verificationService(nettyInfos);
            return;
        }
        for (int i = 0; i < instances.size(); i++) {
            Instance instance = instances.get(i);
            if (!instance.isEnabled() || !instance.isHealthy()) {
                continue;
            }
            NettyInfo nettyInfo = new NettyInfo();
            nettyInfo.setIndexInColony(i);
            nettyInfo.setHost(instance.getIp());
            nettyInfo.setPort(instance.getPort());
            nettyInfo.setWeight((int) instance.getWeight());
            nettyInfos.add(nettyInfo);
        }
        cluster.onServiceStatusChange(nettyInfos);
    }

    private void verificationService(ArrayList<NettyInfo> nettyInfos) {
        try {
            List<RegistryInfo> targetInterfaceInfo = mode.getTargetInterfaceInfo(interfaceName);
            for (int i = 0; i < targetInterfaceInfo.size(); i++) {
                RegistryInfo registryInfo = targetInterfaceInfo.get(i);
                NettyInfo nettyInfo = new NettyInfo();
                RegistryProviderNecessaryInfo necessaryInfo = (RegistryProviderNecessaryInfo) registryInfo.getNecessaryInfo();
                nettyInfo.setIndexInColony(i);
                nettyInfo.setHost(necessaryInfo.getHost());
                nettyInfo.setPort(necessaryInfo.getPort());
                double weight = necessaryInfo.getWeight();
                nettyInfo.setWeight((int) weight);
                nettyInfos.add(nettyInfo);
            }
            cluster.onServiceStatusChange(nettyInfos);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        LogUtil.info("configInfo!!!!!!!!!!!!!!!");
        LogUtil.info(configInfo);
    }
}
