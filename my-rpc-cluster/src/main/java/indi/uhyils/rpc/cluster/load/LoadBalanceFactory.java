package indi.uhyils.rpc.cluster.load;

import indi.uhyils.rpc.cluster.enums.LoadBalanceEnum;
import indi.uhyils.rpc.cluster.pojo.NettyInfo;
import indi.uhyils.rpc.netty.RpcNetty;
import indi.uhyils.rpc.spi.RpcSpiManager;

import java.util.Map;

/**
 * 负载均衡器工厂
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 09时52分
 */
public class LoadBalanceFactory {
    private LoadBalanceFactory() {
    }

    /**
     * 根据负载均衡枚举获取loadBalance
     *
     * @param loadBalanceEnum 负载均衡枚举
     * @param nettyMap        nettymap
     * @return
     * @throws Exception
     */
    public static LoadBalanceInterface createByLoadBalanceEnum(LoadBalanceEnum loadBalanceEnum, Map<NettyInfo, RpcNetty> nettyMap) throws Exception {
        // 返回一个构造完成的消费者
        return (LoadBalanceInterface) RpcSpiManager.getExtensionByClass(LoadBalanceInterface.class, loadBalanceEnum.getSpiName(), nettyMap);

    }

}
