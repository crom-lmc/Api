package space.laimengchao.constant;

/**
 * Redis key 常量
 */
public class RedisKeyConstant {

    /**
     * 邮件激活订阅key
     */
    public final static String LOTTERY_ACTIVE_SUBSCRIBE_MAIL = "LOTTERY:MAIL_ACTIVE_SUBSCRIBE:";

    /**
     * 邮件取消订阅key
     */
    public final static String LOTTERY_CANCEL_SUBSCRIBE_MAIL = "LOTTERY:MAIL_CANCEL_SUBSCRIBE:";

    /**
     * 接口访问限制
     */
    public final static String LOTTERY_ACTIVE_SUBSCRIBE_ACCESS_LIMIT = "LOTTERY:ACTIVE_SUBSCRIBE_ACCESS_LIMIT:";

    /**
     * 接口访问限制
     */
    public final static String LOTTERY_CANCEL_ACCESS_LIMIT = "LOTTERY:CANCEL_ACCESS_LIMIT:";

}
