package indi.uhyils.rpc.exchange.pojo.content;

import indi.uhyils.rpc.exception.ContentArrayQuantityMismatchException;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.enum_.RpcResponseContentEnum;
import indi.uhyils.rpc.exchange.pojo.content.impl.RpcResponseContentImpl;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;

/**
 * rpc响应内容工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 14时09分
 */
public class RpcResponseContentFactory {

    /**
     * 内容大小
     */
    private static final Integer CONTENT_SIZE = 2;

    public static RpcContent createByContentArray(RpcData rpcData, String[] contentArray) throws RpcException {
        if (contentArray.length != CONTENT_SIZE) {
            throw new ContentArrayQuantityMismatchException(contentArray.length, CONTENT_SIZE);
        }
        RpcResponseContentImpl content = new RpcResponseContentImpl(rpcData, contentArray);
        int type = Integer.parseInt(contentArray[RpcResponseContentEnum.TYPE.getLine()]);
        content.setResponseType(type);
        content.setResponseContent(contentArray[RpcResponseContentEnum.RESPONSE_CONTENT.getLine()]);
        return content;
    }
}
