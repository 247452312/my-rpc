package indi.uhyils.rpc.exchange.pojo.data;

import java.beans.Transient;

/**
 * rpcData的观察者
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年05月29日 15时22分
 */
public interface RpcDataObserver {

    /**
     * 观察者模式,获取rpcData
     * 序列化时防止递归 -> @Transient
     *
     * @return
     */
    @Transient
    RpcData getRpcData();
}
