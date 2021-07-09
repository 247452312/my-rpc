package indi.uhyils.rpc.netty.spi.step.base;

import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.netty.spi.step.RpcStep;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时07分
 */
public interface RpcDataExtension extends RpcStep {

    /**
     * 拦截
     *
     * @param data
     * @return
     */
    RpcData doFilter(RpcData data);
}
