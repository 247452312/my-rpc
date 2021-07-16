package indi.uhyils.rpc.cluster.load;

import indi.uhyils.rpc.cluster.pojo.NettyInfo;
import indi.uhyils.rpc.cluster.pojo.SendInfo;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.RpcNetty;
import indi.uhyils.rpc.util.LogUtil;
import java.util.Map;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 08时45分
 */
public abstract class AbstractLoadBalance implements LoadBalanceInterface {

    /**
     * 使用getIndex方法
     */
    static final Integer INDEX_TYPE = 1;

    /**
     * 使用getNettyInfo方法
     */
    static final Integer NETTY_INFO_TYPE = 2;

    @Override
    public RpcData send(RpcData rpcSendData, SendInfo info, Map<NettyInfo, RpcNetty> nettyMap) throws InterruptedException, RpcException, ClassNotFoundException {
        /*1.通过子类查询出要使用的netty 2.发送信息*/
        NettyInfo nettyInfo = null;
        RpcNetty rpcNetty = null;
        int type = getType();
        if (type == 1) {
            int index = getIndex(info, nettyMap.size());
            index = index % nettyMap.size();
            nettyInfo = nettyMap.keySet().toArray(new NettyInfo[0])[index];
            rpcNetty = nettyMap.get(nettyInfo);

        } else if (type == 2) {
            nettyInfo = getNettyInfo(info, nettyMap);
            rpcNetty = nettyMap.get(nettyInfo);
        }
        if (rpcNetty != null) {
            preprocessing(nettyInfo, rpcNetty);
            RpcData rpcData;
            try {
                rpcData = rpcNetty.sendMsg(rpcSendData);
                postProcessing(nettyInfo, rpcNetty, rpcData);
                return rpcData;
            } catch (Exception e) {
                LogUtil.error(this, e);
                exceptionHandle(nettyInfo, rpcNetty, e);
                throw e;
            }


        }
        throw new RpcException("netty没有找到");

    }


    /**
     * 通过指定算法计算出要获取的netty的下标
     *
     * @param info 发送时携带的信息
     * @param size netty的数量
     *
     * @return 一个指定算法计算出的数字
     */
    protected int getIndex(SendInfo info, int size) {
        // 默认实现随机算法,可以覆盖实现
        return RandomUtils.nextInt(0, size);
    }

    /**
     * 通过指定算法计算出要获取的netty的key
     *
     * @param info     发送时携带的信息
     * @param nettyMap netty本身
     *
     * @return 一个指定算法计算出的key
     */
    protected NettyInfo getNettyInfo(SendInfo info, Map<NettyInfo, RpcNetty> nettyMap) {
        return null;
    }

    /**
     * 获取此方法是什么类型的
     * 如果是1 请重写getIndex方法
     * 如果是2 请重写getNettyInfo方法
     *
     * @return 要实现的方式
     */
    protected int getType() {
        return INDEX_TYPE;
    }

    /**
     * 前置处理
     *
     * @return 要实现的方式
     */
    protected void preprocessing(NettyInfo nettyInfo, RpcNetty netty) {
    }

    /**
     * 后置处理
     */
    protected void postProcessing(NettyInfo nettyInfo, RpcNetty netty, RpcData rpcData) {
    }

    /**
     * 报错时处理
     *
     * @param rpcNetty
     * @param e
     */
    protected void exceptionHandle(NettyInfo nettyInfo, RpcNetty rpcNetty, Exception e) {

    }
}
