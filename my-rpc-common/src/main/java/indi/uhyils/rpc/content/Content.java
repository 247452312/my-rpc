package indi.uhyils.rpc.content;

/**
 * 程序中用到的常量
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月10日 07时25分
 */
public interface Content {

    /**
     * 空字符串
     */
    String BLACK = "";

    /**
     * 作者
     */
    String PROJECT_AUTHOR = "uhyils";

    /**
     * 项目名称
     */
    String PROJECT_NAME = "my";


    /**
     * 未知
     */
    String UN_KNOW = "unknown";

    /**
     * 超级管理员的id
     */
    Long ADMIN_USER_ID = 0L;

    /**
     * 超级管理员的角色id
     */
    Long ADMIN_ROLE_ID = 0L;

    /**
     * 登录过期分钟
     */
    Integer LOGIN_TIME_OUT_MIN = 30;

    /**
     * email正则匹配
     */
    String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    /**
     * date正则匹配(yyyy-MM-dd)
     */
    String DATE_REGEX = "^\\d{4}(-)(1[0-2]|0?\\d)\1([0-2]\\d|\\d|30|31)$";

    /**
     * 数值正则匹配
     */
    String VALUE_REGEX = "^\\d+\\.\\d+$";

    /**
     * 英文正则匹配
     */
    String ENGLISH_REGEX = "^[a-zA-Z]+$";

    /**
     * 验证码验证接口名称
     */
    String VERIFICATION_CODE_INTERFACE = "VerificationService";

    /**
     * 验证码验证方法名称
     */
    String VERIFICATION_CODE_METHOD = "verification";

    /**
     * 获取验证码方法(此方法不计入爬虫)
     */
    String GET_VERIFICATION_CODE_METHOD = "getVerification";

    /**
     * 并发数数据字典的code
     */
    String CONCURRENT_NUM_DICT_CODE = "concurrent_num_dict_code";

    /**
     * 接口禁用redis中的hash-key
     */
    String SERVICE_USEABLE_SWITCH = "service_useable_switch";

    /**
     * service包前缀
     */
    String SERVICE_PACKAGE_PREFIX = "indi.uhyils.service.";

    /*id生产规则*/
    /*时间start*/

    /**
     * 时间位数
     */
    Long TIME_BIT = 43L;

    /**
     * 时间位移(最高位始终为0代表正数)
     */
    Long TIME_DISPLACEMENT = Long.bitCount(Long.MAX_VALUE) - TIME_BIT;

    /**
     * 时间掩码
     */
    Long TIME_MASK = (1L << TIME_BIT) - 1L;
    /*时间end*/

    /*序列位start*/

    /**
     * 序列位位数
     */
    Long SEQUENCE_BIT = 10L;

    /**
     * 序列位位移
     */
    Long SEQUENCE_DISPLACEMENT = TIME_DISPLACEMENT - SEQUENCE_BIT;

    /**
     * 序列位掩码
     */
    Long SEQUENCE_MASK = (1L << SEQUENCE_BIT) - 1L;
    /*序列位end*/

    /*分布式编码start*/

    /**
     * 分布式编码位数
     */
    Long DISTRIBUTED_BIT = 5L;

    /**
     * 分布式编码位移
     */
    Long DISTRIBUTED_DISPLACEMENT = SEQUENCE_DISPLACEMENT - DISTRIBUTED_BIT;

    /**
     * 分布式编码掩码
     */
    Long DISTRIBUTED_MASK = (1L << DISTRIBUTED_BIT) - 1L;
    /*分布式编码end*/


    /*预留5位其他业务,请自行添加*/


    /**
     * 返回值类型header
     */
    String HEADER_RETURN_TYPE = "returnType";

}
