package indi.uhyils.rpc.netty.enums;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月23日 15时39分
 */
public enum FilterContextTypeEnum {
    /**
     * filterContext中的key
     */
    REQUEST_RPC_DATA("requestRpcData"),
    RESULT("result");

    private String key;

    FilterContextTypeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
