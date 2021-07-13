package indi.uhyils.rpc.netty.spi.filter.invoker;

import indi.uhyils.rpc.enums.RpcStatusEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.demo.response.NormalResponseRpcData;
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
    public RpcResult invoke(FilterContext context) throws RpcException, ClassNotFoundException, InterruptedException {
        RpcResult rpcResult = context.getRpcResult();
        RpcData request = rpcResult.get();
        if (netty.sendMsg(request.toBytes())) {
            NormalResponseRpcData wait = (NormalResponseRpcData) netty.wait(request.unique());
            byte status = wait.getStatus();
            RpcStatusEnum parse = RpcStatusEnum.parse(status);
            if (parse.equals(RpcStatusEnum.CONSUMER_ERROR)) {
                String exception = wait.content().contentArray()[0];
                LogUtil.error(exception);
                throw new RuntimeException(exception);
            }
            rpcResult.set(wait);
        }
        return rpcResult;
    }
}
