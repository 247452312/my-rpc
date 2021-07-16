package indi.uhyils.rpc.netty.spi.step.template;

import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.spi.step.base.RpcConsumerExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcObjectExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcResponseExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月14日 13时01分
 */
public interface ConsumerResponseObjectExtension extends RpcConsumerExtension, RpcResponseExtension, RpcObjectExtension {

    @Override
    default Object doFilter(Object obj, RpcData rpcData) {
        return obj;
    }
}
