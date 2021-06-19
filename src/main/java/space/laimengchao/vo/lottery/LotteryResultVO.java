package space.laimengchao.vo.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.laimengchao.domain.LotteryDetail;
import space.laimengchao.domain.LotteryResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotteryResultVO extends LotteryResult {

    @ApiModelProperty(value = "开奖结果明细", dataType = "LotteryDetail")
    private LotteryDetail lotteryDetail;

}
