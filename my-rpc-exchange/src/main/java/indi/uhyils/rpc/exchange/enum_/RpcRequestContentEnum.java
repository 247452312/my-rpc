package indi.uhyils.rpc.exchange.enum_;

/**
 * RpcData请求中的RpcContent结构 (例: "类名称\n类版本...") 中间拿\n隔开
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月06日 10时20分
 */
public enum RpcRequestContentEnum {
    /**
     * 类名称
     */
    SERVICE_NAME(0),
    /**
     * 类版本
     */
    SERVICE_VERSION(1),
    /**
     * 方法名称
     */
    METHOD_NAME(2),
    /**
     * 方法类型
     */
    METHOD_PARAM_TYPE(3),
    /**
     * 参数jsonArray
     */
    ARG_MAP(4);

    private Integer line;

    RpcRequestContentEnum(Integer line) {
        this.line = line;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
