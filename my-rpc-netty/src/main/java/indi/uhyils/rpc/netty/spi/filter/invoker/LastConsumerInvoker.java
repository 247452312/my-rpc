package indi.uhyils.rpc.netty.spi.filter.invoker;

import indi.uhyils.rpc.enums.RpcStatusEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.NormalResponseRpcData;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.netty.core.RpcNettyNormalConsumer;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月21日 21时07分
 */
public class LastConsumerInvoker implements RpcInvoker {

    /**
     * netty
     */
    private RpcNettyNormalConsumer netty;

    public LastConsumerInvoker(RpcNettyNormalConsumer netty) {
        this.netty = netty;
    }

    @Override
    public RpcData invoke(FilterContext context) throws RpcException, ClassNotFoundException, InterruptedException {
        RpcData request = context.getRequestData();
        if (netty.sendMsg(request.toBytes())) {
            NormalResponseRpcData wait = (NormalResponseRpcData) netty.wait(request.unique());
            byte status = wait.getStatus();
            RpcStatusEnum parse = RpcStatusEnum.parse(status);
            if (parse.equals(RpcStatusEnum.PROVIDER_ERROR)) {
                String exception = wait.content().contentArray()[1];
                LogUtil.error(exception);
                throw new RuntimeException(exception);
            }
            return wait;
        }
        throw new RpcException("netty错误");
    }
}
