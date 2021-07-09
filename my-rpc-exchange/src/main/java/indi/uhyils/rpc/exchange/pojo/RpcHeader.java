package indi.uhyils.rpc.exchange.pojo;

import java.io.Serializable;

/**
 * rpc内容中的header
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 10时21分
 */
public class RpcHeader implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
