package indi.uhyils.rpc.enums;

/**
 * rpc 返回值结果
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月23日 19时29分
 */
public enum RpcResponseTypeEnum {
    /**
     * 无返回值
     */
    NULL_BACK(0),
    /**
     * 返回字符串
     */
    STRING_BACK(1),
    /**
     * 报错
     */
    EXCEPTION(2);

    /**
     * 代号
     */
    private Integer code;

    RpcResponseTypeEnum(Integer code) {
        this.code = code;
    }

    public static RpcResponseTypeEnum parse(Integer responseType) {
        for (RpcResponseTypeEnum value : RpcResponseTypeEnum.values()) {
            if (value.getCode().equals(responseType)) {
                return value;
            }
        }
        return EXCEPTION;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
