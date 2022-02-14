package indi.uhyils.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.cluster.Cluster;
import indi.uhyils.rpc.cluster.ClusterFactory;
import indi.uhyils.rpc.cluster.pojo.SendInfo;
import indi.uhyils.rpc.enums.RpcResponseTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.content.impl.RpcResponseContentImpl;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.callback.RpcCallBackFactory;
import indi.uhyils.rpc.netty.enums.RpcNettyTypeEnum;
import indi.uhyils.rpc.netty.factory.NettyInitDtoFactory;
import indi.uhyils.rpc.netty.pojo.NettyInitDto;
import indi.uhyils.rpc.registry.mode.RegistryMode;
import indi.uhyils.rpc.registry.mode.RegistryModeFactory;
import indi.uhyils.rpc.registry.pojo.info.RegistryInfo;
import indi.uhyils.rpc.registry.pojo.info.RegistryProviderNecessaryInfo;
import indi.uhyils.rpc.util.IpUtil;
import indi.uhyils.rpc.util.LogUtil;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 15时43分
 */
@RpcSpi(single = false)
public class ConsumerRegistry<T> extends AbstractRegistry<T> {


    /**
     * 发送者的ip
     */
    private String selfIp;

    @Override
    protected void doRegistryInit(Object... objects) {

        RegistryMode mode = RegistryModeFactory.create();
        mode.setType(RpcNettyTypeEnum.CONSUMER);
        this.mode = mode;

        try {
            this.cluster = createCluster();
            this.selfIp = IpUtil.getIp();
            // 创建监听器.监听注册中心
            mode.createListener(this.serviceClass.getName(), cluster);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }

    /**
     * 创建一个消费者的cluster,包含了目标的cluster
     *
     * @return
     *
     * @throws NacosException
     */
    private Cluster createCluster() throws NacosException {

        /*构建netty初始化需要的信息*/
        List<RegistryInfo> remoteInfo = this.mode.getTargetInterfaceInfo(this.serviceClass.getName());
        // 获取目标接口的信息
        NettyInitDto[] nettyInits = new NettyInitDto[remoteInfo.size()];
        for (int i = 0; i < remoteInfo.size(); i++) {
            RegistryInfo registryInfo = remoteInfo.get(i);
            //查询到目标class注册到注册中心的信息
            RegistryProviderNecessaryInfo necessaryInfo = (RegistryProviderNecessaryInfo) registryInfo.getNecessaryInfo();
            nettyInits[i] = NettyInitDtoFactory.createNettyInitDto(necessaryInfo.getHost(), necessaryInfo.getPort(), necessaryInfo.getWeight().intValue(), RpcCallBackFactory.createResponseCallBack());
        }

        try {
            return ClusterFactory.createDefaultConsumerCluster(this.serviceClass, nettyInits);
        } catch (Exception e) {
            LogUtil.error(this, e);
            return null;
        }
    }


    @Override
    public RpcData invoke(RpcData rpcData) throws RpcException, InterruptedException {

        SendInfo info = new SendInfo();
        info.setIp(selfIp);

        RpcData rpcResponseData;
        try {
            rpcResponseData = cluster.sendMsg(rpcData, info);
        } catch (ClassNotFoundException e) {
            throw new RpcException(e);
        }
        if (rpcResponseData == null) {
            throw new RpcException("返回值为空");
        }

        //处理rpc请求
        dealWithErrorRpcData(rpcResponseData);
        return rpcResponseData;
    }

    /**
     * 处理rpc请求
     *
     * @param rpcResponseData
     */
    private void dealWithErrorRpcData(RpcData rpcResponseData) {
        RpcResponseContentImpl content = (RpcResponseContentImpl) rpcResponseData.content();
        Integer responseType = content.responseType();
        RpcResponseTypeEnum type = RpcResponseTypeEnum.parse(responseType);
        String responseContent = content.getResponseContent();

        if (type == RpcResponseTypeEnum.EXCEPTION) {
            throw new RpcException("请求出错:" + responseContent);
        }
    }

}
