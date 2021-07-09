package indi.uhyils.rpc.netty.pojo;

import indi.uhyils.rpc.netty.callback.RpcCallBack;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 09时04分
 */
public class NettyInitDto implements Serializable {

    private static final long serialVersionUID = 8849579140716709610L;
    /**
     * 端口
     */
    private Integer port;

    /**
     * 地址
     */
    private String host;

    /**
     * 收到消息的回调
     */
    private transient RpcCallBack callback;

    /**
     * 权重
     */
    private Integer weight;

    public static NettyInitDto build(Integer port, String host, RpcCallBack callback, Integer weight) {
        NettyInitDto build = new NettyInitDto();
        build.port = port;
        build.host = host;
        build.callback = callback;
        build.weight = weight;
        return build;
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RpcCallBack getCallback() {
        return callback;
    }

    public void setCallback(RpcCallBack callback) {
        this.callback = callback;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
