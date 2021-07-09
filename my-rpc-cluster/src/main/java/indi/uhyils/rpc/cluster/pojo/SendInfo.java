package indi.uhyils.rpc.cluster.pojo;

import java.io.Serializable;

/**
 * 这个类可能不只是ip,也有可能是别的东西
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 16时01分
 */
public class SendInfo implements Serializable {

    /**
     * ip
     */
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
