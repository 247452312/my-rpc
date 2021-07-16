package indi.uhyils.rpc.exchange.pojo.content;

/**
 * rpc请求内容抽象类,包含请求接口名称,接口版本等信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时31分
 */
public interface RpcRequestContent extends RpcContent {

    /**
     * 获取接口名称
     *
     * @return
     */
    String getServiceName();

    /**
     * 获取接口版本
     *
     * @return
     */
    String getServiceVersion();

    /**
     * 获取方法名称
     *
     * @return
     */
    String getMethodName();

    /**
     * 获取方法参数类型
     *
     * @return
     */
    String[] getMethodParameterTypes();

    /**
     * 获取内容
     *
     * @return
     */
    Object[] getArgs();

    /**
     * 获取其他
     *
     * @return
     */
    Object[] getOthers();

}
