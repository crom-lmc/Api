package space.laimengchao.constant.lottery;

import space.laimengchao.constant.ResultCodeEnum;
import space.laimengchao.exception.ApiException;

public enum LotteryTypeEnum {

    SSQ("ssq", "双色球"),
    DLT("dlt", "大乐透"),
    QLC("qlc", "七乐彩"),
    FCSD("fcsd", "福彩3D"),
    QXC("qxc", "七星彩"),
    PLS("pls", "排列3"),
    PLW("plw", "plw");

    private String code;
    private String name;

    LotteryTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String findName(String code) throws ApiException {
        for (LotteryTypeEnum lotteryTypeEnum : values()) {
            if (lotteryTypeEnum.getCode().equalsIgnoreCase(code)) {
                return lotteryTypeEnum.getName();
            }
        }
        throw new ApiException(ResultCodeEnum.PARAM_ERROR, "无效的彩票类型.");
    }

}
