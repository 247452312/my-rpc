package indi.uhyils.rpc.exchange.pojo.content;

import indi.uhyils.rpc.exception.ContentArrayQuantityMismatchException;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.pojo.content.impl.RpcRequestContentImpl;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;

/**
 * 请求内容装载工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 14时09分
 */
public class RpcRequestContentFactory {

    /**
     * 内容大小
     */
    private static final Integer CONTENT_MIN_SIZE = 5;

    public static RpcContent createNormalByContentArray(RpcData rpcData, String[] contentArray) throws ClassNotFoundException, RpcException {
        if (contentArray.length < CONTENT_MIN_SIZE) {
            throw new ContentArrayQuantityMismatchException(contentArray.length, CONTENT_MIN_SIZE);
        }

        return new RpcRequestContentImpl(rpcData, contentArray);
    }
}
