package indi.uhyils.rpc.exchange.pojo.factory;

import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import org.apache.commons.lang3.StringUtils;

/**
 * rpc内容工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时00分
 */
public class RpcHeaderFactory {

    /**
     * header
     */
    private static final char HEADER_SPLIT = ':';

    public static RpcHeader newHeader(String data) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        RpcHeader rpcHeader = new RpcHeader();
        int colonIndex = data.indexOf(HEADER_SPLIT);
        String name = data.substring(0, colonIndex);
        String value = data.substring(colonIndex + 1);
        rpcHeader.setName(name);
        rpcHeader.setValue(value);
        return rpcHeader;
    }
}
