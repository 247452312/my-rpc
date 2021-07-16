package indi.uhyils.rpc.exchange.pojo.data;

import com.alibaba.fastjson.JSON;
import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcResponseTypeEnum;
import indi.uhyils.rpc.enums.RpcStatusEnum;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.content.MyRpcContent;
import indi.uhyils.rpc.exchange.pojo.content.RpcContent;
import indi.uhyils.rpc.exchange.pojo.content.RpcRequestContentFactory;
import indi.uhyils.rpc.exchange.pojo.content.RpcResponseContentFactory;
import indi.uhyils.rpc.exchange.pojo.head.RpcHeader;
import indi.uhyils.rpc.spi.RpcSpiManager;
import indi.uhyils.rpc.util.LogUtil;
import java.nio.charset.StandardCharsets;

/**
 * 组装请求工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时38分
 */
@RpcSpi
public class NormalRpcRequestFactory extends AbstractRpcFactory {

    private static final String RPC_REQUEST_DEFAULT_NAME = "RPC_REQUEST_DEFAULT_NAME";

    private static final String RPC_REQUEST_SPI_NAME = "RPC_REQUEST_SPI_NAME";


    @Override
    public RpcData createByBytes(byte[] data) throws Exception {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(RPC_REQUEST_SPI_NAME, RPC_REQUEST_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (RpcData) RpcSpiManager.getExtensionByClass(RpcData.class, registryName, data);
    }

    @Override
    public RpcData createByInfo(Long unique, Object[] others, RpcHeader[] rpcHeaders, String... contentArray) throws RpcException, ClassNotFoundException {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(RPC_REQUEST_SPI_NAME, RPC_REQUEST_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        NormalRequestRpcData rpcNormalRequest = (NormalRequestRpcData) RpcSpiManager.getExtensionByClass(RpcData.class, registryName);
        rpcNormalRequest.setType(RpcTypeEnum.REQUEST.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(rpcHeaders);
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.NULL.getCode());
        rpcNormalRequest.setUnique(unique);
        RpcContent content = RpcRequestContentFactory.createNormalByContentArray(rpcNormalRequest, contentArray);
        rpcNormalRequest.setContent(content);
        rpcNormalRequest.setSize(content.contentString().getBytes(StandardCharsets.UTF_8).length);
        return rpcNormalRequest;
    }

    /**
     * 客户端超时
     *
     * @param request 请求
     *
     * @return
     */
    @Override
    public RpcData createTimeoutResponse(RpcData request, Long timeout) throws RpcException {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(RPC_REQUEST_SPI_NAME, RPC_REQUEST_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        NormalRequestRpcData rpcNormalRequest = (NormalRequestRpcData) RpcSpiManager.getExtensionByClass(RpcData.class, registryName);
        rpcNormalRequest.setType(RpcTypeEnum.REQUEST.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(request.rpcHeaders());
        String[] contentArray = {String.valueOf(RpcResponseTypeEnum.EXCEPTION.getCode()), "消费者超时:" + timeout};
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.CONSUMER_TIMEOUT.getCode());
        rpcNormalRequest.setUnique(request.unique());
        RpcContent content = RpcResponseContentFactory.createByContentArray(rpcNormalRequest, contentArray);
        rpcNormalRequest.setContent(content);
        rpcNormalRequest.setSize(content.toString().getBytes(StandardCharsets.UTF_8).length);
        return rpcNormalRequest;
    }


    public RpcData createRetriesError(RpcData request, Throwable th) {
        NormalResponseRpcData rpcNormalRequest = NormalRpcResponseFactory.createNewNormalResponseRpcData();
        rpcNormalRequest.setType(RpcTypeEnum.REQUEST.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(request.rpcHeaders());
        String[] contentArray = {String.valueOf(RpcResponseTypeEnum.EXCEPTION.getCode()), "错误: " + th.getMessage()};
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.BAD_REQUEST.getCode());
        rpcNormalRequest.setUnique(request.unique());
        try {
            RpcContent content = RpcResponseContentFactory.createByContentArray(rpcNormalRequest, contentArray);
            rpcNormalRequest.setContent(content);
            rpcNormalRequest.setSize(content.toString().getBytes(StandardCharsets.UTF_8).length);
        } catch (RpcException e) {
            LogUtil.error(this, e);
        }
        return rpcNormalRequest;
    }

    public RpcData createFallback(RpcData request, Object response) {
        NormalResponseRpcData rpcNormalRequest = NormalRpcResponseFactory.createNewNormalResponseRpcData();
        rpcNormalRequest.setType(RpcTypeEnum.REQUEST.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(request.rpcHeaders());
        String[] contentArray = {String.valueOf(RpcResponseTypeEnum.STRING_BACK.getCode()), JSON.toJSONString(response)};
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.CONSUMER_FUSING.getCode());
        rpcNormalRequest.setUnique(request.unique());
        try {
            RpcContent content = RpcResponseContentFactory.createByContentArray(rpcNormalRequest, contentArray);
            rpcNormalRequest.setContent(content);
            rpcNormalRequest.setSize(content.toString().getBytes(StandardCharsets.UTF_8).length);
        } catch (RpcException e) {
            LogUtil.error(this, e);
        }
        return rpcNormalRequest;
    }

    @Override
    protected RpcTypeEnum getRpcType() {
        return RpcTypeEnum.REQUEST;
    }


}
