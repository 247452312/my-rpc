package indi.uhyils.rpc.cluster;

import indi.uhyils.rpc.cluster.enums.LoadBalanceEnum;
import indi.uhyils.rpc.cluster.pojo.NettyInfo;
import indi.uhyils.rpc.cluster.pojo.SendInfo;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.netty.RpcNetty;
import indi.uhyils.rpc.spi.RpcSpiExtension;

import java.util.List;
import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 09时29分
 */
public interface Cluster extends RpcSpiExtension {

    /**
     * 获取接口名称
     *
     * @return
     */
    String getInterfaceName();

    /**
     * 获取负载均衡方式
     *
     * @return
     */
    LoadBalanceEnum getTypeOfLoadBalance();

    /**
     * 获取cluster中是否是单例,例如 provider就一定是单例
     *
     * @return
     */
    Boolean isSingle();

    /**
     * 获取集群数量
     *
     * @return
     */
    Integer getNumOfColony();


    /**
     * 获取此cluster下所有的netty
     *
     * @return
     */
    Map<NettyInfo, RpcNetty> getAllNetty();


    /**
     * 关闭,不会立即关闭.会等待线程结束
     *
     * @return
     */
    Boolean shutdown();

    /**
     * 发送信息
     *
     * @param rpcData
     * @param info
     * @return
     * @throws InterruptedException
     */
    RpcData sendMsg(RpcData rpcData, SendInfo info) throws RpcException, ClassNotFoundException, InterruptedException;

    /**
     * 服务数量改变时->生产者不需要关心自己的上下线,所以只有消费者需要完成逻辑
     *
     * @param nettyInfos
     * @return
     */
    Boolean onServiceStatusChange(List<NettyInfo> nettyInfos);


}
