package indi.uhyils.rpc.exchange.pojo.demo.request.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import indi.uhyils.rpc.enums.RpcTypeEnum;
import indi.uhyils.rpc.exchange.enum_.RpcRequestContentEnum;
import indi.uhyils.rpc.exchange.pojo.AbstractRpcContent;
import indi.uhyils.rpc.exchange.pojo.RpcData;
import indi.uhyils.rpc.exchange.pojo.request.content.RpcRequestContent;
import indi.uhyils.rpc.util.LogUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求体内容
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 11时03分
 */
public class RpcNormalRequestContent extends AbstractRpcContent implements RpcRequestContent {
    /**
     * 接口名称
     */
    private String serviceName;
    /**
     * 接口版本
     */
    private String serviceVersion;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法参数类型
     */
    private String[] methodParamterTypes;
    /**
     * 参数
     */
    private Object[] args;
    /**
     * 其他预留字段
     */
    private Object[] others;

    public RpcNormalRequestContent(RpcData rpcData, String[] contentArray) {
        super(contentArray);
        init();
        setRpcData(rpcData);
    }

    /**
     * 初始化属性
     *
     */
    private void init() {
        this.setServiceName(this.getLine(RpcRequestContentEnum.SERVICE_NAME.getLine()));
        this.setServiceVersion(this.getLine(RpcRequestContentEnum.SERVICE_VERSION.getLine()));
        this.setMethodName(this.getLine(RpcRequestContentEnum.METHOD_NAME.getLine()));
        String[] methodParamterTypes = this.getLine(RpcRequestContentEnum.METHOD_PARAM_TYPE.getLine()).split(";");
        this.setMethodParamterTypes(methodParamterTypes);
        JSONArray argsMap = JSON.parseArray(this.getLine(RpcRequestContentEnum.ARG_MAP.getLine()));

        this.setArgs(argsMap.toArray());

        List<Object> others = new ArrayList<>();
        for (int i = 5; i < this.contentArray().length; i++) {
            String s = this.getLine(i);
            Object parse = JSON.parse(s);
            others.add(parse);
        }
        this.setOthers(others.toArray());
    }


    @Override
    public Integer type() {
        return RpcTypeEnum.REQUEST.getCode();
    }


    @Override
    public String contentString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getServiceName());
        sb.append("\n");
        sb.append(this.getServiceVersion());
        sb.append("\n");
        sb.append(this.getMethodName());
        sb.append("\n");
        String[] methodParameterTypes = this.getMethodParameterTypes();
        for (String methodParameterType : methodParameterTypes) {
            sb.append(methodParameterType);
            sb.append(";");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append("\n");
        sb.append(JSONArray.toJSONString(this.getArgs()));
        sb.append("\n");
        Object[] others = this.getOthers();
        for (Object other : others) {
            sb.append(JSON.toJSONString(other));
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String[] getMethodParameterTypes() {
        return methodParamterTypes;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public Object[] getOthers() {
        return others;
    }

    public void setOthers(Object[] others) {
        this.others = others;
    }

    public void setMethodParamterTypes(String[] methodParamterTypes) {
        this.methodParamterTypes = methodParamterTypes;
    }
}
