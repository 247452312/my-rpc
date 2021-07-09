package indi.uhyils.rpc.exchange.pojo;

import indi.uhyils.rpc.exception.ContentChangeLineException;

/**
 * 抽象rpc内容体,rpc内容中包含了rpc体的指针,在特殊场合中可以用到
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 21时24分
 */
public abstract class AbstractRpcContent implements RpcContent {

    /**
     * rpcData
     */
    private RpcData rpcData;

    /**
     * 内容数组
     */
    private String[] contentArray;

    protected AbstractRpcContent(String[] contentArray) {
        this.contentArray = contentArray;
    }


    @Override
    public String getLine(Integer line) {
        return contentArray[line];
    }

    @Override
    public RpcData getRpcData() {
        return rpcData;
    }

    protected void setRpcData(RpcData rpcData) {
        this.rpcData = rpcData;
    }

    @Override
    public String toString() {
        return contentString();
    }


    @Override
    public String[] contentArray() {
        return contentArray;
    }

    @Override
    public void changeLine(Integer line, String lineStr) throws ContentChangeLineException {
        if (contentArray == null) {
            throw new ContentChangeLineException();
        } else if (contentArray.length <= line) {
            throw new ContentChangeLineException(contentArray.length, line);
        } else {
            contentArray[line] = lineStr;
        }
    }
}
