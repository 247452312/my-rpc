package indi.uhyils.rpc.exchange.pojo.response;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.AbstractRpcData;

/**
 * rpc响应体
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时23分
 */
public abstract class AbstractResponseRpcData extends AbstractRpcData {

    protected AbstractResponseRpcData() {
    }

    @Override
    public void init(Object... params) throws Exception {
        super.init(params);
        this.type = RpcTypeEnum.RESPONSE.getCode();
    }

}
