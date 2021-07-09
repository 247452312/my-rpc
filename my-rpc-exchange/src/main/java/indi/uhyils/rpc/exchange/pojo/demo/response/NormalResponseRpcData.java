package indi.uhyils.rpc.exchange.pojo.demo.response;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.RpcContent;
import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import indi.uhyils.rpc.exchange.pojo.response.AbstractResponseRpcData;
import indi.uhyils.rpc.exchange.pojo.response.content.RpcResponseContentFactory;

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
    public Integer rpcVersion() {
        return this.version;
    }

    @Override
    public Integer type() {
        return RpcTypeEnum.RESPONSE.getCode();
    }

    @Override
    public Integer size() {
        return this.size;
    }

    @Override
    public RpcHeader[] rpcHeaders() {
        return this.headers;
    }

    @Override
    public RpcContent content() {
        return this.content;
    }

}
