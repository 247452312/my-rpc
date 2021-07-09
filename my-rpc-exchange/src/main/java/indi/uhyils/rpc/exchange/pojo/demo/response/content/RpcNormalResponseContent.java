package indi.uhyils.rpc.exchange.pojo.demo.response.content;

import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.AbstractRpcContent;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.response.content.RpcResponseContent;

/**
 * rpc正常响应内容
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 11时03分
 */
public class RpcNormalResponseContent extends AbstractRpcContent implements RpcResponseContent {

    /**
     * 响应值类型
     */
    private Integer responseType;

    /**
     * 响应值体
     */
    private String responseContent;

    public RpcNormalResponseContent(RpcData rpcData, String[] contentArray) {
        super(contentArray);
        setRpcData(rpcData);
    }

    @Override
    public Integer type() {
        return RpcTypeEnum.REQUEST.getCode();
    }

    @Override
    public String contentString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type());
        sb.append("\n");
        sb.append(this.getResponseContent());
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Integer responseType() {
        return responseType;
    }

    @Override
    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public void setResponseType(Integer responseType) {
        this.responseType = responseType;
    }
}
