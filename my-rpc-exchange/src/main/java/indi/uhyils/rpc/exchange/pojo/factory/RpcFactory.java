package indi.uhyils.rpc.exchange.pojo.factory;

import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.content.MyRpcContent;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import indi.uhyils.rpc.spi.RpcSpiExtension;
import io.netty.buffer.ByteBuf;

/**
 * rpc工厂抽象接口
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时39分
 */
public interface RpcFactory extends RpcSpiExtension {

    /**
     * 使用数据流新建一个rpc体
     *
     * @param data 数据流
     * @return 创建之后的pojo
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    RpcData createByBytes(byte[] data) throws Exception;

    /**
     * 根据数据流新建一个rpc体
     *
     * @param data
     * @return
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    RpcData createByByteBuf(ByteBuf data) throws Exception;

    /**
     * 根据一些必要的信息创建RPC体
     *
     * @param rpcHeaders   rpc头
     * @param unique       唯一标示
     * @param others       其他
     * @param contentArray rpc内容体以及其他内容
     * @return
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    RpcData createByInfo(Long unique, Object[] others, RpcHeader[] rpcHeaders, String... contentArray) throws RpcException, ClassNotFoundException;

    /**
     * 创建一个超时的rpc返回体
     *
     * @param request 请求
     * @param timeout 超时时间
     * @return
     */
    RpcData createTimeoutResponse(RpcData request, Long timeout) throws RpcException;

    /**
     * 获取RPC心跳包请求
     *
     * @return
     * @throws RpcException
     * @throws ClassNotFoundException
     */
    default RpcData getHealth() {
        return MyRpcContent.RPC_HEALTH_DATA;
    }
}
