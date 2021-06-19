package space.laimengchao.util;

import org.apache.commons.lang.StringUtils;
import space.laimengchao.constant.lottery.TwoColorBallRuleConstant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 随机彩票工具类
 */
public class RandomLotteryUtils {

    public static void main(String[] args) {
        List<String> result = randomTwoColorBallVo(10);
        result.forEach(item -> System.out.println(item.toString()));
    }

    /**
     * 随机产生x组双色球
     *
     * @param groupQty 组数
     * @return
     */
    public static List<String> randomTwoColorBallVo(int groupQty) {
        List<String> result = new ArrayList<>();
        for (int x = 0; x < groupQty; x++) {
            // 生成红色号码
            List<Integer> redBallList = randomNumbers(TwoColorBallRuleConstant.RED_BALL_QTY, TwoColorBallRuleConstant.MAX_RED_BALL);
            // 生成蓝色号码
            List<Integer> blueBallList = randomNumbers(TwoColorBallRuleConstant.BLUE_BALL_QTY, TwoColorBallRuleConstant.MAX_BLUE_BALL);
            // 排序
            redBallList.sort(Comparator.comparingInt(o -> o));
            blueBallList.sort(Comparator.comparingInt(o -> o));
            String redBallStr = StringUtils.join(redBallList, ",");
            String blueBallStr = StringUtils.join(blueBallList, ",");
            result.add(redBallStr + "," + blueBallStr);
        }
        return result;
    }

    /**
     * 随机数列
     *
     * @param qty   数量
     * @param range 范围
     * @return
     */
    public static List<Integer> randomNumbers(int qty, int range) {
        List<Integer> list = new ArrayList<>();
        while (list.size() < qty) {
            int redBall = randomNumber(range);
            if (!list.contains(redBall)) {
                list.add(redBall);
            }
        }
        return list;
    }

    /**
     * 指定范围内随机产生一个数字
     *
     * @param range 范围
     * @return 随机数
     */
    public static int randomNumber(int range) {
        int result = (int) (Math.random() * range) + 1;
        return result;
    }

}
