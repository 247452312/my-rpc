package indi.uhyils.rpc.netty;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.spi.RpcSpiExtension;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;

/**
 * rpc调用中的netty
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 13时42分
 */
public interface RpcNetty extends RpcSpiExtension {

    /**
     * 获取bootStrap
     *
     * @return
     */
    AbstractBootstrap<?, ? extends Channel> getBootstrap();

    /**
     * 设置bootstrap
     *
     * @param bootstrap
     */
    void setBootstrap(AbstractBootstrap<?, ? extends Channel> bootstrap);


    /**
     * 关闭,不会立即关闭.会等待线程结束
     *
     * @return
     */
    Boolean shutdown();

    /**
     * 发送信息 等待回应
     *
     * @param rpcData
     * @return
     */
    RpcData sendMsg(RpcData rpcData) throws RpcException, ClassNotFoundException, InterruptedException;
}
