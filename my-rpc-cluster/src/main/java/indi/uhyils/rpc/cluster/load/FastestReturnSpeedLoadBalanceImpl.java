package indi.uhyils.rpc.cluster.load;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.cluster.pojo.NettyInfo;
import indi.uhyils.rpc.cluster.pojo.SendInfo;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.netty.RpcNetty;

import java.util.HashMap;
import java.util.Map;

/**
 * 最快返回速度
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 09时14分
 */
@RpcSpi
public class FastestReturnSpeedLoadBalanceImpl extends AbstractLoadBalance {

    /**
     * 记录返回时间用
     */
    private ThreadLocal<Long> timeThreadLocal;

    private Map<NettyInfo, Long> lastFiveSendAvgTimeMap = new HashMap<>();

    @Override
    protected NettyInfo getNettyInfo(SendInfo info, Map<NettyInfo, RpcNetty> nettyMap) {
        NettyInfo fastNettyInfo = null;
        long minTime = 0;
        for (NettyInfo nettyInfo : nettyMap.keySet()) {
            Long lastFiveSendAvgTime = lastFiveSendAvgTimeMap.putIfAbsent(fastNettyInfo, null);
            // 如果一个netty一次都没有执行过,那么就选它
            if (lastFiveSendAvgTime == null) {
                fastNettyInfo = nettyInfo;
                break;
            }
            if (lastFiveSendAvgTime < minTime) {
                minTime = lastFiveSendAvgTime;
                fastNettyInfo = nettyInfo;
            }
        }
        return fastNettyInfo;
    }

    @Override
    protected int getType() {
        return NETTY_INFO_TYPE;
    }

    @Override
    protected void preprocessing(NettyInfo nettyInfo, RpcNetty netty) {
        long l = System.currentTimeMillis();
        timeThreadLocal = new ThreadLocal<>();
        timeThreadLocal.set(l);
    }

    @Override
    protected void postProcessing(NettyInfo nettyInfo, RpcNetty netty, RpcData rpcData) {
        long runTime = System.currentTimeMillis() - timeThreadLocal.get();
        timeThreadLocal.remove();
        Long lastFiveSendAvgTime = lastFiveSendAvgTimeMap.putIfAbsent(nettyInfo, runTime);
        if (lastFiveSendAvgTime == null) {
            lastFiveSendAvgTime = runTime;
        }
        long lastTime = (lastFiveSendAvgTime * 5 + runTime) / 6L;
        lastFiveSendAvgTimeMap.put(nettyInfo, lastTime);
    }

    @Override
    protected void exceptionHandle(NettyInfo nettyInfo, RpcNetty rpcNetty, Exception e) {
        timeThreadLocal.remove();
    }
}
