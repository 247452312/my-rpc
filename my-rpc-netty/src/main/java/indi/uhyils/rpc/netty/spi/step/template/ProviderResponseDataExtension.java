package indi.uhyils.rpc.netty.spi.step.template;

import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.netty.spi.step.base.RpcDataExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcProviderExtension;
import indi.uhyils.rpc.netty.spi.step.base.RpcResponseExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时22分
 */
public interface ProviderResponseDataExtension extends RpcProviderExtension, RpcResponseExtension, RpcDataExtension {

    @Override
    default RpcData doFilter(RpcData data) {
        return data;
    }
}
