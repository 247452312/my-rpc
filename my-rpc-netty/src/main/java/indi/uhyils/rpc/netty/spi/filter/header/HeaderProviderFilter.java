package indi.uhyils.rpc.netty.spi.filter.header;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.content.HeaderContext;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.exchange.pojo.head.RpcHeader;
import indi.uhyils.rpc.netty.spi.filter.FilterContext;
import indi.uhyils.rpc.netty.spi.filter.filter.ProviderFilter;
import indi.uhyils.rpc.netty.spi.filter.invoker.RpcInvoker;

/**
 * 动态代码header 获取filter
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月11日 19时13分
 */
@RpcSpi
public class HeaderProviderFilter implements ProviderFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws RpcException, ClassNotFoundException, InterruptedException {
        RpcData requestData = invokerContext.getRequestData();
        RpcHeader[] rpcHeaders = requestData.rpcHeaders();
        for (RpcHeader rpcHeader : rpcHeaders) {
            HeaderContext.addHeader(rpcHeader.getName(), rpcHeader.getValue());
        }
        try {
            return invoker.invoke(invokerContext);
        } finally {
            HeaderContext.remove();
        }
    }
}
