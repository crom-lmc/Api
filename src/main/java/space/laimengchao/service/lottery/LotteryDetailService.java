package space.laimengchao.service.lottery;

import space.laimengchao.domain.LotteryDetail;

public interface LotteryDetailService {

    int insert(LotteryDetail lotteryDetail);

    LotteryDetail findLotteryDetail(String lotteryNo,String lotteryId);

}
