package indi.uhyils.rpc.exception;

/**
 * rpc版本不正确
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 11时39分
 */
public class RpcVersionNotSupportedException extends RpcException {

    public RpcVersionNotSupportedException(Integer version, Integer maxVersion) {
        super(String.format("版本:%d   不兼容,最大支持版本为:%d", version, maxVersion));
    }
}
