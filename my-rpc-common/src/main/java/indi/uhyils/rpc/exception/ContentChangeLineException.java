package indi.uhyils.rpc.exception;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年05月29日 15时58分
 */
public class ContentChangeLineException extends RpcException {

    public ContentChangeLineException() {
        super("rpcContent没有初始化,content为null");
    }

    /**
     * 内容总行数,要插入的行数
     *
     * @param contentLength
     * @param line
     */
    public ContentChangeLineException(Integer contentLength, Integer line) {
        super(String.format("rpcContent 总行数: %d,要修改行数: %d", contentLength, line));
    }

}
