package indi.uhyils.rpc.config;

import java.io.Serializable;

/**
 * consumer 配置
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月16日 10时28分
 */
public class ConsumerConfig implements Serializable {

    private static final long serialVersionUID = 9134042554661832941L;
    /**
     * 是否需要检查(默认需要)
     */
    private Boolean check = Boolean.TRUE;

    /**
     * 超时 默认10秒
     */
    private Long timeout = 1000L;

    /**
     * 重试次数
     */
    private Integer retries = 3;

    /**
     * 集群是否内联,如果在自己项目中发现了有bean,则是否使用bean去加载
     */
    private Boolean inConnection = Boolean.FALSE;


    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public Boolean getInConnection() {
        return inConnection;
    }

    public void setInConnection(Boolean inConnection) {
        this.inConnection = inConnection;
    }
}
