package indi.uhyils.rpc.netty.spi.step.template;

import indi.uhyils.rpc.netty.spi.step.base.RpcByteExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcConsumerExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcResponseExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时23分
 */
public interface ConsumerResponseByteExtension extends RpcConsumerExtension, RpcResponseExtension, RpcByteExtension {

    @Override
    default byte[] doFilter(byte[] bytes) {
        return bytes;
    }
}
