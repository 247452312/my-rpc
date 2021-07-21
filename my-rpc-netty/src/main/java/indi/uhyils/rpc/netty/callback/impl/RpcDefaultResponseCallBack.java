package indi.uhyils.rpc.netty.callback.impl;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.pojo.content.RpcContent;
import indi.uhyils.rpc.exchange.pojo.data.RpcData;
import indi.uhyils.rpc.exchange.pojo.data.RpcFactory;
import indi.uhyils.rpc.exchange.pojo.data.RpcFactoryProducer;
import indi.uhyils.rpc.exchange.pojo.content.RpcResponseContent;
import indi.uhyils.rpc.netty.callback.RpcCallBack;
import indi.uhyils.rpc.netty.pojo.InvokeResult;

/**
 * 这个只是一个样例,具体如何执行要看下一级
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月23日 19时15分
 */
@RpcSpi
public class RpcDefaultResponseCallBack implements RpcCallBack {

    @Override
    public RpcData createRpcData(byte[] data) throws Exception {
        /*解析*/
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
        // 获取到的Request
        assert build != null;
        return build.createByBytes(data);
    }

    @Override
    public RpcContent getContent(byte[] data) throws Exception {
        RpcData request = createRpcData(data);
        return request.content();
    }

    @Override
    public InvokeResult invoke(RpcContent content) {
        if (content instanceof RpcResponseContent) {
            RpcResponseContent requestContent = (RpcResponseContent) content;
            String responseContent = requestContent.getResponseContent();
            return null;
        }
        return null;
    }

    @Override
    public RpcData assembly(Long unique, InvokeResult result) {
        return null;
    }


}
