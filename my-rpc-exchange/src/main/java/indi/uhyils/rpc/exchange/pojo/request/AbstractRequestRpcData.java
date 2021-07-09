package indi.uhyils.rpc.exchange.pojo.request;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.AbstractRpcData;

/**
 * rpc请求体(rpc扩展点.继承此请求体可扩展rpc请求)
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时23分
 */
public abstract class AbstractRequestRpcData extends AbstractRpcData {


    protected AbstractRequestRpcData() {
    }

    @Override
    public void init(Object... params) throws Exception {
        super.init(params);
        this.type = RpcTypeEnum.REQUEST.getCode();
    }

}
