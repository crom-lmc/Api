package space.laimengchao.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("lottery_result_subscribe")
@ApiModel(value = "结果订阅")
public class LotteryResultSubscribe implements Serializable {

    @TableId("id")
    @ApiModelProperty(value = "Id", dataType = "Long")
    private Long id;
    @ApiModelProperty(value = "彩票类型Id", dataType = "String")
    private String lotteryId;
    @ApiModelProperty(value = "订阅类型，邮件/收级", dataType = "int")
    private int subscribeType;
    @ApiModelProperty(value = "邮件/手机号", dataType = "String")
    private String subscribeAddress;
    @ApiModelProperty(value = "取消日期", dataType = "Date")
    private Date cancelDate;

}
