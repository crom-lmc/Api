package space.laimengchao.constant;

public enum ResultCodeEnum {

    SUCCESS(100, "成功"),
    SYSTEM_ERROR(201, "系统错误"),
    PARAM_ERROR(202, "参数错误"),
    NO_DATA_FOUND(203, "没有获取到数据"),
    REMOTE_PROCEDURE_CALL_FAIL(204, "远程调用失败"),
    URL_EXPIRE(205, "链接已过期"),
    ACCESS_LIMIT(206, "接口访问限制,请不要频繁访问,稍候再试."),
    LOTTERY_TYPE_ERROR(207, "不支持的类型.");

    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
