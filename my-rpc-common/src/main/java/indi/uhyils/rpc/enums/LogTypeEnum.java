package indi.uhyils.rpc.enums;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 09时04分
 */
public enum LogTypeEnum {

    /**
     * info
     */
    INFO(0),
    /**
     * debug
     */
    DEBUG(1),
    /**
     * warn
     */
    WARN(2),
    /**
     * error
     */
    ERROR(3);

    private Integer type;

    LogTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
