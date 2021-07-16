package indi.uhyils.rpc.netty.spi.step.template;

import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.spi.step.base.RpcConsumerExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcDataExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcRequestExtension;

/**
 * 消费者,请求,RpcData类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时17分
 */
public interface ConsumerRequestDataExtension extends RpcConsumerExtension, RpcDataExtension, RpcRequestExtension {


    @Override
    default RpcData doFilter(RpcData data) {
        return data;
    }
}
