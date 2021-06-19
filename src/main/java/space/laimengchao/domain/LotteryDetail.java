package space.laimengchao.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("lottery_detail")
@ApiModel(value = "开奖结果明细")
public class LotteryDetail {

    @TableId("id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;
    @ApiModelProperty(value = "彩票类型Id", dataType = "String")
    private String lotteryId;
    @ApiModelProperty(value = "期号", dataType = "String")
    private String lotteryNo;
    @ApiModelProperty(value = "一等奖数量", dataType = "Long")
    private Long prizeNum1;
    @ApiModelProperty(value = "一等奖奖金", dataType = "String")
    private String prizeAmount1;
    @ApiModelProperty(value = "二等奖数量", dataType = "Long")
    private Long prizeNum2;
    @ApiModelProperty(value = "二等奖奖金", dataType = "String")
    private String prizeAmount2;
    @ApiModelProperty(value = "三等奖数量", dataType = "Long")
    private Long prizeNum3;
    @ApiModelProperty(value = "三等奖奖金", dataType = "String")
    private String prizeAmount3;
    @ApiModelProperty(value = "四等奖数量", dataType = "Long")
    private Long prizeNum4;
    @ApiModelProperty(value = "四等奖奖金", dataType = "String")
    private String prizeAmount4;
    @ApiModelProperty(value = "五等奖数量", dataType = "Long")
    private Long prizeNum5;
    @ApiModelProperty(value = "五等奖奖金", dataType = "String")
    private String prizeAmount5;
    @ApiModelProperty(value = "六等奖数量", dataType = "Long")
    private Long prizeNum6;
    @ApiModelProperty(value = "六等奖奖金", dataType = "String")
    private String prizeAmount6;
    @ApiModelProperty(value = "七等奖数量", dataType = "Long")
    private Long prizeNum7;
    @ApiModelProperty(value = "七等奖奖金", dataType = "String")
    private String prizeAmount7;

}
