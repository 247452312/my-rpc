package indi.uhyils.rpc.exchange.pojo.data;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.content.RpcResponseContentFactory;

/**
 * rpc正常响应
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 13时34分
 */
@RpcSpi(single = false)
public class NormalResponseRpcData extends AbstractResponseRpcData {


    @Override
    protected void initContent() throws RpcException {
        this.content = RpcResponseContentFactory.createByContentArray(this, this.contentArray);
    }


    @Override
    public Integer type() {
        return RpcTypeEnum.RESPONSE.getCode();
    }

}
