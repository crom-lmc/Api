package space.laimengchao.service.lottery;

import com.alibaba.fastjson.JSONObject;
import space.laimengchao.domain.LotteryResult;
import space.laimengchao.vo.lottery.LotteryTypeVO;

import java.util.List;

public interface LotteryService {

    /**
     * 获取历史开奖结果
     * @param lotteryId 彩票类型Id
     * @param pageSize
     * @param page
     * @return
     */
    List<LotteryResult> getLotteryHistoryResult(String lotteryId,int pageSize,int page) throws Exception;

    /**
     * 查询支持彩种列表
     * @return
     */
    List<LotteryTypeVO> types() throws Exception;

    /**
     * 随机输出groupQty组双色球
     * @param groupQty 组数
     * @return
     * @throws Exception
     */
    List<String> randomSSQ(int groupQty) throws Exception;

    /**
     * 获取开奖结果
     * @param lotteryId 彩票类型
     * @param no 期号(为空则默认最新一期)
     * @return
     */
    String getLotteryResult(String lotteryId,String no) throws Exception;

    /**
     * 获取开奖结果明细
     * @param lotteryId 彩票类型
     * @param no 期号(为空则默认最新一期)
     * @return
     */
    JSONObject getLotteryDetail(String lotteryId, String no) throws Exception;

    void importLotteryHistoryResult(String lotteryId, int pageSize, int page) throws Exception;

}
