package indi.uhyils.rpc.config;

import java.io.Serializable;

/**
 * 微服务信息配置
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月16日 10时27分
 */
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 5902293946002526409L;

    /**
     * 微服务名称
     */
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
