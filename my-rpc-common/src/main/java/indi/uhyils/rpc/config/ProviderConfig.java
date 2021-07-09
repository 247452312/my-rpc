package indi.uhyils.rpc.config;

import java.io.Serializable;

/**
 * provider 配置
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月16日 10时27分
 */
public class ProviderConfig implements Serializable {

    private static final long serialVersionUID = -3653659017499763477L;
    /**
     * 是否开启生产者
     */
    private boolean enable = Boolean.FALSE;
    /**
     * rpc端口
     */
    private Integer port;

    /**
     * 超时时间
     */
    private Long timeout = 1000L;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
        enable = Boolean.TRUE;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
        enable = Boolean.TRUE;
    }

    public boolean isEnable() {
        return enable;
    }
}
