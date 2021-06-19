package space.laimengchao.mapper.lottery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import space.laimengchao.domain.LotteryResult;

public interface LotteryResultMapper extends BaseMapper<LotteryResult> {

    @Select("SELECT * from lottery_result where lottery_id = #{lotteryId} ORDER BY lottery_date DESC LIMIT 0,1")
    LotteryResult getLatestResult(String lotteryId);

}
