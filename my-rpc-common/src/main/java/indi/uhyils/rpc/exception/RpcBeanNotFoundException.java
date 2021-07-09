package indi.uhyils.rpc.exception;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月29日 08时34分
 */
public class RpcBeanNotFoundException extends RpcException {

    public RpcBeanNotFoundException(Class<?> wellInvokeClassBean) {
        super("rpc 未找到可执行bean:" + wellInvokeClassBean.getName());
    }
}
