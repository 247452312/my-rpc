package indi.uhyils.rpc.exchange.pojo.data;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.content.RpcContent;
import indi.uhyils.rpc.exchange.pojo.content.RpcRequestContentFactory;
import indi.uhyils.rpc.exchange.pojo.head.RpcHeader;

/**
 * rpc正常请求体(默认扩展实现,如果有其他实现,请自行处理)
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时52分
 */
@RpcSpi(single = false)
public class NormalRequestRpcData extends AbstractRequestRpcData {


    public NormalRequestRpcData() {
    }

    @Override
    protected void initContent() throws RpcException, ClassNotFoundException {
        this.content = RpcRequestContentFactory.createNormalByContentArray(this, this.contentArray);
    }

    @Override
    public Integer type() {
        return RpcTypeEnum.REQUEST.getCode();
    }


}
