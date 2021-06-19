package space.laimengchao.vo.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotteryTypeVO implements Serializable {

    @ApiModelProperty(value = "id", dataType = "String")
    private String lotteryId;
    @ApiModelProperty(value = "彩票名称", dataType = "String")
    private String lotteryName;
    @ApiModelProperty(value = "彩票类型Id", dataType = "String")
    private String lotteryTypeId;
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}
