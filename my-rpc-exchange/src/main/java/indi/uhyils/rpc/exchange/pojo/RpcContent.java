package indi.uhyils.rpc.exchange.pojo;

import indi.uhyils.rpc.exception.ContentChangeLineException;

/**
 * rpc内容抽象类,里面不包含rpc的version等信息, 只有正式内容,例如请求时的请求接口名称,接口版本等信息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时25分
 */
public interface RpcContent extends RpcDataObserver {
    /**
     * 获取类型
     *
     * @return
     */
    Integer type();

    /**
     * 获取第x行
     *
     * @param line
     * @return
     */
    String getLine(Integer line);

    /**
     * 修改行
     *
     * @param line    行
     * @param lineStr 行字符串
     * @return
     */
    void changeLine(Integer line, String lineStr) throws ContentChangeLineException;

    /**
     * 获取content部分的字符串
     *
     * @return
     */
    String contentString();


    /**
     * 获取rpc内容体的行
     *
     * @return
     */
    String[] contentArray();

}
