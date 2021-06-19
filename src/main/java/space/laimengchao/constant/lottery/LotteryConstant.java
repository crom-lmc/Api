package space.laimengchao.constant.lottery;

/**
 * 彩票远程服务链接
 */
public class LotteryConstant {

    public static int SUCCESS_CODE = 0;

    public static class URL{

        /**
         * 根路径
         */
        public static String ROOT_PATH = "http://apis.juhe.cn/lottery";

        /**
         * 支持彩种列表
         */
        public static String HISTORY = "/history";

        /**
         * 历史开奖结果查询
         */
        public static String TYPE = "/types";

        /**
         * 开奖结果查询
         */
        public static String QUERY = "/query";

    }

    public static class TYPES{

        /**
         * 双色球
         */
        public static String SSQ = "ssq";

        /**
         * 大乐透
         */
        public static String DLT = "dlt";

        /**
         * 七乐彩
         */
        public static String QLC = "qlc";

        /**
         * 福彩3D
         */
        public static String FCSD = "fcsd";

        /**
         * 七星彩
         */
        public static String QXC = "qxc";

        /**
         * 排列3
         */
        public static String PLS = "pls";

        /**
         * 排列五
         */
        public static String PLW = "plw";

    }

}
