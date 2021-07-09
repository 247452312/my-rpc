package indi.uhyils.rpc.netty.callback;

import indi.uhyils.rpc.config.RpcConfigFactory;
import indi.uhyils.rpc.spi.RpcSpiManager;

import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月09日 08时44分
 */
public class RpcCallBackFactory {

    private static final String requestCallBackDefaultName = "default_request_call_back";
    private static final String responseCallBackDefaultName = "default_response_call_back";

    private static final String requestCallBackConfigName = "request_call_back";
    private static final String responseCallBackConfigName = "response_call_back";

    /**
     * 创建一个请求解析器(单例)
     *
     * @return
     */
    public static RpcCallBack createRequestCallBack(Map<String, Object> beans) throws Exception {
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(requestCallBackConfigName, requestCallBackDefaultName);
        return (RpcCallBack) RpcSpiManager.getExtensionByClass(RpcCallBack.class, registryName, beans);
    }

    /**
     * 创建一个回应解析器
     *
     * @return
     */
    public static RpcCallBack createResponseCallBack() {
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(responseCallBackConfigName, responseCallBackDefaultName);
        return (RpcCallBack) RpcSpiManager.getExtensionByClass(RpcCallBack.class, registryName);
    }
}
