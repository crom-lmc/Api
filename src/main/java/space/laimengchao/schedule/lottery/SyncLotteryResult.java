package space.laimengchao.schedule.lottery;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import space.laimengchao.constant.lottery.LotteryConstant;
import space.laimengchao.constant.SubscribeTypeEnum;
import space.laimengchao.domain.LotteryDetail;
import space.laimengchao.domain.LotteryResult;
import space.laimengchao.domain.LotteryResultSubscribe;
import space.laimengchao.service.*;
import space.laimengchao.service.lottery.LotteryDetailService;
import space.laimengchao.service.lottery.LotteryResultService;
import space.laimengchao.service.lottery.LotteryResultSubscribeService;
import space.laimengchao.service.lottery.LotteryService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Component
public class SyncLotteryResult {

    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private LotteryResultService lotteryResultService;
    @Autowired
    private LotteryDetailService lotteryDetailService;
    @Autowired
    private MailService mailService;
    @Autowired
    private LotteryResultSubscribeService lotteryResultSubscribeService;
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 每周二四七将双色球开奖结果同步到本地
     */
    @Scheduled(cron = "0 0 22 * * 2,4,7")
    public void syncTwoColorBallResult() {
        log.info("syncTwoColorBallResult start...");
        JSONObject jsonObject = getLotteryDetail();
        if (jsonObject != null) {
            LotteryResult lotteryResult = buildLotteryResult(jsonObject);
            lotteryResultService.insert(lotteryResult);
            List<LotteryResultSubscribe> list = lotteryResultSubscribeService.findLotteryResultSubscribeBySubscribeType(SubscribeTypeEnum.MAIL.getCode());
            if (!CollectionUtils.isEmpty(list)) {
                String lotteryNo = lotteryResult.getLotteryNo();
                String content = "本期开奖结果是:" + lotteryResult.getLotteryRes();
                for (LotteryResultSubscribe lotteryResultSubscribe : list) {
                    String subject = "第" + lotteryNo + "期双色球开奖结果";
                    mailService.sendHtmlMail(new String[]{lotteryResultSubscribe.getSubscribeAddress()}, null, null, subject, content);
                }
            }
        } else {
            log.error(String.format("获取%s开奖结果失败", LotteryConstant.TYPES.SSQ));
        }
    }

    /**
     * 每周一三五将开奖结果明细同步到本地
     */
    @Scheduled(cron = "0 30 23 * * 2,4,7")
    public void syncTwoColorBallDetail() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("syncTwoColorBallDetail start...");
        JSONObject jsonObject = getLotteryDetail();
        if (jsonObject != null) {
            JSONArray lotteryPrizes = jsonObject.getJSONArray("lottery_prize");
            if (lotteryPrizes != null) {
                LotteryDetail lotteryDetail = new LotteryDetail();
                lotteryDetail.setLotteryId(LotteryConstant.TYPES.SSQ);
                String lotteryNo = jsonObject.getString("lottery_no");
                LotteryResult lotteryResult = lotteryResultService.getLotteryResult(lotteryNo, LotteryConstant.TYPES.SSQ);
                if (lotteryResult == null) {
                    lotteryResult = buildLotteryResult(jsonObject);
                    lotteryResultService.insert(lotteryResult);
                } else {
                    lotteryResult.setLotteryPoolAmount(jsonObject.getString("lottery_pool_amount"));
                    lotteryResult.setLotterySaleAmount(jsonObject.getString("lottery_sale_amount"));
                    lotteryResultService.update(lotteryResult);
                }
                lotteryDetail.setLotteryNo(jsonObject.getString("lottery_no"));
                for (int x = 0, size = lotteryPrizes.size(); x < size; x++) {
                    JSONObject lotteryPrize = lotteryPrizes.getJSONObject(x);
                    Class clazz = lotteryDetail.getClass();
                    Method setPrizeNumMethod = clazz.getMethod("setPrizeNum" + (x + 1), Long.class);
                    setPrizeNumMethod.invoke(lotteryDetail, Long.valueOf(lotteryPrize.getString("prize_num")));
                    Method setPrizeAmountMethod = clazz.getMethod("setPrizeAmount" + (x + 1), String.class);
                    setPrizeAmountMethod.invoke(lotteryDetail, lotteryPrize.getString("prize_amount"));
                }
                lotteryDetailService.insert(lotteryDetail);
            } else {
                log.error(String.format("获取%s开奖结果明细失败", LotteryConstant.TYPES.SSQ));
            }
        } else {
            log.error(String.format("获取%s开奖结果明细失败", LotteryConstant.TYPES.SSQ));
        }
    }

    public JSONObject getLotteryDetail() {
        try {
            return lotteryService.getLotteryDetail(LotteryConstant.TYPES.SSQ, null);
        } catch (Exception e) {
            log.error(String.format("获取%s开奖结果失败:%s", LotteryConstant.TYPES.SSQ, ExceptionUtil.getMessage(e)));
        }
        return null;
    }

    public LotteryResult buildLotteryResult(JSONObject jsonObject) {
        LotteryResult lotteryResult = new LotteryResult();
        lotteryResult.setLotteryId(LotteryConstant.TYPES.SSQ);
        lotteryResult.setLotteryNo(jsonObject.getString("lottery_no"));
        lotteryResult.setLotteryRes(jsonObject.getString("lottery_res"));
        String lotteryDate = jsonObject.getString("lottery_date");
        String lotteryExdate = jsonObject.getString("lottery_exdate");
        lotteryResult.setLotteryDate(DateUtil.parse(lotteryDate, DATE_FORMAT));
        lotteryResult.setLotteryExdate(DateUtil.parse(lotteryExdate, DATE_FORMAT));
        lotteryResult.setLotteryPoolAmount(jsonObject.getString("lottery_pool_amount"));
        lotteryResult.setLotterySaleAmount(jsonObject.getString("lottery_sale_amount"));
        return lotteryResult;
    }

}
