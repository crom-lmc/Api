package space.laimengchao.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("lottery_result")
@ApiModel(value = "开奖结果")
public class LotteryResult {

    @TableId("id")
    @ApiModelProperty(value = "id",dataType = "Long")
    private Long id;
    @ApiModelProperty(value = "彩票类型Id",dataType = "String")
    private String lotteryId;
    @ApiModelProperty(value = "彩票开奖结果",dataType = "String")
    private String lotteryRes;
    @ApiModelProperty(value = "彩票期号",dataType = "String")
    private String lotteryNo;
    @ApiModelProperty(value = "开奖日期",dataType = "Date")
    private Date lotteryDate;
    @ApiModelProperty(value = "兑奖截止日期",dataType = "Date")
    private Date lotteryExdate;
    @ApiModelProperty(value = "本期销量",dataType = "String")
    private String lotterySaleAmount;
    @ApiModelProperty(value = "奖池滚存",dataType = "String")
    private String lotteryPoolAmount;

}
