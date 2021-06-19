package space.laimengchao.service.lottery;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import space.laimengchao.domain.LotteryResult;

public interface LotteryResultService {

    int insert(LotteryResult lotteryResult);

    Page<LotteryResult> getHistoryLotteryNumber(String lotteryNo, String lotteryId, Integer page, Integer limit);

    LotteryResult getLotteryResult(String lotteryNo, String lotteryId);

    int update(LotteryResult lotteryResult);

}
