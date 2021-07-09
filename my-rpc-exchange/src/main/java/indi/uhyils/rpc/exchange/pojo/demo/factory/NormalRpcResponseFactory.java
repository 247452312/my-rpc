package indi.uhyils.rpc.exchange.pojo.demo.factory;

import indi.uhyils.rpc.annotation.RpcSpi;
import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.enums.RpcResponseTypeEnum;
import indi.uhyils.rpc.enums.RpcStatusEnum;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exception.RpcException;
import indi.uhyils.rpc.exchange.content.MyRpcContent;
import indi.uhyils.rpc.exchange.pojo.RpcContent;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.RpcHeader;
import indi.uhyils.rpc.exchange.pojo.demo.response.NormalResponseRpcData;
import indi.uhyils.rpc.exchange.pojo.factory.AbstractRpcFactory;
import indi.uhyils.rpc.exchange.pojo.response.content.RpcResponseContentFactory;
import indi.uhyils.rpc.spi.RpcSpiManager;

import java.nio.charset.StandardCharsets;

/**
 * rpc响应体工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 12时47分
 */
@RpcSpi
public class NormalRpcResponseFactory extends AbstractRpcFactory {

    private static final String RPC_RESPONSE_DEFAULT_NAME = "RPC_RESPONSE_DEFAULT_NAME";
    private static final String RPC_RESPONSE_SPI_NAME = "RPC_RESPONSE_SPI_NAME";

    public static NormalResponseRpcData createNewNormalResponseRpcData() {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(RPC_RESPONSE_SPI_NAME, RPC_RESPONSE_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (NormalResponseRpcData) RpcSpiManager.getExtensionByClass(RpcData.class, registryName);
    }

    @Override
    public RpcData createByBytes(byte[] data) throws Exception {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(RPC_RESPONSE_SPI_NAME, RPC_RESPONSE_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (RpcData) RpcSpiManager.getExtensionByClass(RpcData.class, registryName, data);
    }

    @Override
    public RpcData createByInfo(Long unique, Object[] others, RpcHeader[] rpcHeaders, String... contentArray) throws RpcException {
        // spi 获取消费者的注册者信息
        NormalResponseRpcData rpcNormalRequest = createNewNormalResponseRpcData();

        rpcNormalRequest.setType(RpcTypeEnum.RESPONSE.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(rpcHeaders);
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus((Byte) others[0]);
        rpcNormalRequest.setUnique(unique);

        RpcContent content = RpcResponseContentFactory.createByContentArray(rpcNormalRequest, contentArray);
        rpcNormalRequest.setContent(content);
        rpcNormalRequest.setSize(content.toString().getBytes(StandardCharsets.UTF_8).length);
        return rpcNormalRequest;
    }

    @Override
    public RpcData createTimeoutResponse(RpcData request, Long timeout) throws RpcException {
        // spi 获取消费者的注册者信息
        NormalResponseRpcData rpcNormalRequest = createNewNormalResponseRpcData();

        rpcNormalRequest.setType(RpcTypeEnum.REQUEST.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(request.rpcHeaders());
        String[] contentArray = {String.valueOf(RpcResponseTypeEnum.EXCEPTION.getCode()), "生产者超时:" + timeout};
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.PROVIDER_TIMEOUT.getCode());
        rpcNormalRequest.setUnique(request.unique());
        RpcContent content = RpcResponseContentFactory.createByContentArray(rpcNormalRequest, contentArray);
        rpcNormalRequest.setContent(content);
        rpcNormalRequest.setSize(content.toString().getBytes(StandardCharsets.UTF_8).length);
        return rpcNormalRequest;
    }

    public RpcData createErrorResponse(Long unique, Throwable e, RpcHeader[] rpcHeaders) throws RpcException {
        NormalResponseRpcData rpcNormalRequest = createNewNormalResponseRpcData();

        rpcNormalRequest.setType(RpcTypeEnum.RESPONSE.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(rpcHeaders);
        String[] contentArray = new String[]{e == null ? RpcStatusEnum.PROVIDER_ERROR.getName() : e.getMessage()};
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.PROVIDER_ERROR.getCode());
        rpcNormalRequest.setUnique(unique);

        RpcContent content = RpcResponseContentFactory.createByContentArray(rpcNormalRequest, contentArray);
        rpcNormalRequest.setContent(content);
        rpcNormalRequest.setSize(content.toString().getBytes(StandardCharsets.UTF_8).length);
        return rpcNormalRequest;

    }

    @Override
    protected RpcTypeEnum getRpcType() {
        return RpcTypeEnum.RESPONSE;
    }
}
