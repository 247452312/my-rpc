package indi.uhyils.rpc.netty.spi.filter.invoker;

import indi.uhyils.rpc.exchange.pojo.RpcData;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 11时16分
 */
public class RpcResultImpl implements RpcResult {

    RpcData data;

    @Override
    public RpcData get() {
        return data;
    }

    @Override
    public RpcData set(RpcData obj) {
        RpcData result = data;
        data = obj;
        return result;
    }
}
