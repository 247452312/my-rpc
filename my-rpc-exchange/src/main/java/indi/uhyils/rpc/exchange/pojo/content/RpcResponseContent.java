package indi.uhyils.rpc.exchange.pojo.content;

/**
 * rpc请求内容抽象类,包含回应值类型,回应值信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时45分
 */
public interface RpcResponseContent extends RpcContent {

    /**
     * 获取响应值的类型
     *
     * @return
     */
    Integer responseType();

    /**
     * 获取返回值的内容
     *
     * @return
     */
    String getResponseContent();

}
