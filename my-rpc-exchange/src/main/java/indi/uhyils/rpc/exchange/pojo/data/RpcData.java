package indi.uhyils.rpc.exchange.pojo.data;

import indi.uhyils.rpc.exchange.pojo.content.RpcContent;
import indi.uhyils.rpc.exchange.pojo.head.RpcHeader;
import indi.uhyils.rpc.spi.RpcSpiExtension;

/**
 * rpc数据体
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时14分
 */
public interface RpcData extends RpcSpiExtension {

    /**
     * RPC版本
     *
     * @return
     */
    Integer rpcVersion();

    /**
     * RPC类型,0->请求 1->响应
     *
     * @return
     */
    Integer type();

    /**
     * 获取唯一标识
     *
     * @return
     */
    Long unique();

    /**
     * RPC内容的size,最大值{@link Integer#MAX_VALUE}
     *
     * @return
     */
    Integer size();

    /**
     * 获取RPC中的header
     *
     * @return
     */
    RpcHeader[] rpcHeaders();

    /**
     * 内容
     *
     * @return
     */
    RpcContent content();

    /**
     * 获取header和content的组合字符串
     *
     * @return
     */
    String headerAndContent();

    /**
     * 获取rpc全部
     *
     * @return
     */
    byte[] toBytes();
}
