package indi.uhyils.rpc.exchange.enums;

/**
 * RpcData响应中的RpcContent结构 (例: "返回类型\n返回内容...") 中间拿\n隔开
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月06日 10时20分
 */
public enum RpcResponseContentEnum {
    /**
     * 类型
     */
    TYPE(0),
    /**
     * 返回体
     */
    RESPONSE_CONTENT(1);

    private Integer line;

    RpcResponseContentEnum(Integer line) {
        this.line = line;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
