package indi.uhyils.rpc.cluster.provider.impl;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.cluster.enums.LoadBalanceEnum;
import indi.uhyils.rpc.cluster.pojo.NettyInfo;
import indi.uhyils.rpc.cluster.pojo.SendInfo;
import indi.uhyils.rpc.cluster.provider.AbstractProviderCluster;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.factory.RpcBeanFactory;
import indi.uhyils.rpc.netty.RpcNetty;
import indi.uhyils.rpc.netty.callback.RpcCallBackFactory;
import indi.uhyils.rpc.netty.enums.RpcNettyTypeEnum;
import indi.uhyils.rpc.netty.factory.RpcNettyFactory;
import indi.uhyils.rpc.netty.pojo.NettyInitDto;
import indi.uhyils.rpc.util.IpUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 15时36分
 */
@RpcSpi
public class ProviderDefaultCluster extends AbstractProviderCluster {

    /**
     * netty信息
     */
    private NettyInfo nettyInfo;

    /**
     * netty的本体
     */
    private RpcNetty netty;

    /**
     * 负载均衡策略
     */
    private LoadBalanceEnum loadBalanceType;


    @Override
    public void init(Object... params) throws Exception {

        Integer port = (Integer) params[0];
        Map<String, Object> beans = (Map<String, Object>) params[1];

        NettyInitDto nettyInit = new NettyInitDto();

        nettyInit.setCallback(RpcCallBackFactory.createRequestCallBack(RpcBeanFactory.getInstance(beans).getRpcBeans()));
        nettyInit.setHost(IpUtil.getIp());
        nettyInit.setPort(port);
        RpcNetty initNetty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.PROVIDER, nettyInit);
        NettyInfo initNettyInfo = new NettyInfo();
        initNettyInfo.setIndexInColony(1);

        this.nettyInfo = initNettyInfo;
        this.netty = initNetty;
        this.loadBalanceType = LoadBalanceEnum.RANDOM;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public LoadBalanceEnum getTypeOfLoadBalance() {
        return loadBalanceType;
    }

    @Override
    public Boolean isSingle() {
        return Boolean.TRUE;
    }

    @Override
    public Integer getNumOfColony() {
        return 1;
    }

    @Override
    public Map<NettyInfo, RpcNetty> getAllNetty() {
        Map<NettyInfo, RpcNetty> nettyInfoRpcNettyMap = new HashMap<>(1);
        nettyInfoRpcNettyMap.put(nettyInfo, netty);
        return nettyInfoRpcNettyMap;
    }

    @Override
    public Boolean shutdown() {
        return netty.shutdown();
    }

    @Override
    public RpcData sendMsg(RpcData rpcData, SendInfo info) throws InterruptedException, RpcException, ClassNotFoundException {
        return netty.sendMsg(rpcData);
    }


    @Override
    public Boolean onServiceStatusChange(List<NettyInfo> nettyInfos) {
        return Boolean.TRUE;
    }

    @Override
    public Integer getIndexInColony() {
        return 1;
    }

    @Override
    public Float getWeight() {
        return 0F;
    }
}
