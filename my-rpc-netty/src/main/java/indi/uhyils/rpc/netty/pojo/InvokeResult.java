package indi.uhyils.rpc.netty.pojo;

import java.io.Serializable;
import java.lang.reflect.Type;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月09日 08时21分
 */
public class InvokeResult implements Serializable {

    /**
     * 结果json
     */
    private String resultJson;

    /**
     * 结果的类型
     */
    private Class<?> resultClass;

    /**
     * 结果的type
     */
    private Type resultType;

    public static InvokeResult build(String resultJson, Class<?> resultClass, Type resultType) {
        InvokeResult build = new InvokeResult();
        build.resultJson = resultJson;
        build.resultClass = resultClass;
        build.resultType = resultType;
        return build;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public Class<?> getResultClass() {
        return resultClass;
    }

    public void setResultClass(Class<?> resultClass) {
        this.resultClass = resultClass;
    }

    public Type getResultType() {
        return resultType;
    }

    public void setResultType(Type resultType) {
        this.resultType = resultType;
    }
}
