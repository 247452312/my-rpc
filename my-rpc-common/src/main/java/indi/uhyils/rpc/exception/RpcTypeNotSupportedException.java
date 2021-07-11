package indi.uhyils.rpc.exception;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import java.util.Objects;

/**
 * rpc 请求响应类型不正确异常
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月22日 10时19分
 */
public class RpcTypeNotSupportedException extends RpcException {

    public RpcTypeNotSupportedException(Integer type, Integer shouldType) {
        super("rpc类型不符合,应该是: " + (Objects.equals(type, RpcTypeEnum.RESPONSE.getCode()) ? "响应" : "请求") + "实际是: " + (Objects.equals(shouldType, RpcTypeEnum.RESPONSE.getCode()) ? "响应" : "请求"));
    }
}
