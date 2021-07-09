package indi.uhyils.rpc.cluster.enums;

/**
 * 负载均衡策咯
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 11时05分
 */
public enum LoadBalanceEnum {
    /**
     * 根据ip
     */
    IP_HASH(0, "ip_hash_load_balance"),
    /**
     * 手动分配权重
     */
    MANUAL_ASSIGNMENT(1, "manual_assignment_load_balance"),
    /**
     * 随机
     */
    RANDOM(2, "random_load_balance"),
    /**
     * 轮询
     */
    POLLING(3, "polling_load_balance"),
    /**
     * 最少活跃
     */
    LEAST_ACTIVE(4, "无"),
    /**
     * 最快返回速度
     */
    FASTEST_RETURN_SPEED(5, "fastest_return_speed_load_balance");


    /**
     * 负责均衡对应的code
     */
    private Integer code;

    /**
     * spi中对应的名称
     */
    private String spiName;


    LoadBalanceEnum(Integer code, String spiName) {
        this.code = code;
        this.spiName = spiName;
    }

    public Integer getCode() {
        return code;
    }


    public String getSpiName() {
        return spiName;
    }
}
