package space.laimengchao.constant;

public enum SubscribeTypeEnum {

    MAIL(1, "邮件"),
    PHONE(2, "手机短信");

    private int code;
    private String name;

    SubscribeTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
