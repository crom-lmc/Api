package space.laimengchao.controller;

import cn.hutool.core.util.ReUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.laimengchao.constant.ResultCodeEnum;
import space.laimengchao.constant.SubscribeTypeEnum;
import space.laimengchao.domain.LotteryResultSubscribe;
import space.laimengchao.exception.ApiException;
import space.laimengchao.service.lottery.LotteryResultSubscribeService;
import space.laimengchao.vo.ResultMsg;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("subscribe")
@RestController
@Api(value = "subscribe", tags = "subscribe", description = "提供邮件，短信等订阅功能")
public class SubscribeController {

    @Autowired
    private LotteryResultSubscribeService lotteryResultSubscribeService;

    private final static String ACTIVE = "active";

    private final static String REGULAR_MAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

    @ApiOperation(value = "sendVerifyMail.json", notes = "发送订阅邮件")
    @PostMapping("sendVerifyMail.json")
    public ResultMsg sendVerifyMail(HttpServletRequest request, @ApiParam(value = "彩票类型Id") @RequestParam(value = "lotteryId") String lotteryId,
                                    @ApiParam(value = "邮箱") @RequestParam(value = "mail") String mail,
                                    @ApiParam(value = "类型(订阅/取消订阅)") @RequestParam(value = "type") String type) throws ApiException {
        boolean result = ReUtil.isMatch(REGULAR_MAIL, mail);
        if (result) {
            LotteryResultSubscribe lotteryResultSubscribe = lotteryResultSubscribeService.selectLotteryResultSubscribe(lotteryId, mail, SubscribeTypeEnum.MAIL.getCode());
            if (ACTIVE.equalsIgnoreCase(type)) {//激活
                if (lotteryResultSubscribe != null && lotteryResultSubscribe.getCancelDate() == null) {
                    return ResultMsg.fail(ResultCodeEnum.PARAM_ERROR.getCode(), "当前邮箱已经订阅,请勿重复订阅.");
                }
                lotteryResultSubscribeService.sendActivateSubscribeMail(request, mail, lotteryId);
            } else {//取消订阅
                if (lotteryResultSubscribe == null) {
                    return ResultMsg.fail(ResultCodeEnum.PARAM_ERROR.getCode(), "当前邮箱还未订阅.");
                }
                lotteryResultSubscribeService.sendCancelSubscribeMail(request, mail, lotteryId);
            }
            return ResultMsg.success(ResultCodeEnum.SUCCESS.getCode());
        } else {
            return ResultMsg.fail(ResultCodeEnum.PARAM_ERROR.getCode(), "不是一个有效的邮件地址.");
        }
    }

    @ApiOperation(value = "activateSubscribe.json", notes = "订阅激活")
    @GetMapping("activateSubscribe.json")
    public ResultMsg activateSubscribe(@ApiParam(value = "uuId") @RequestParam(value = "uuId") String uuId,
                                       @ApiParam(value = "彩票类型Id") @RequestParam(value = "lotteryId") String lotteryId,
                                       @ApiParam(value = "邮箱") @RequestParam(value = "mail") String mail) throws ApiException {
        lotteryResultSubscribeService.activateSubscribe(uuId, lotteryId, mail);
        return ResultMsg.success(ResultCodeEnum.SUCCESS.getCode());
    }

    @ApiOperation(value = "cancelSubscribe.json", notes = "取消订阅")
    @GetMapping("cancelSubscribe.json")
    public ResultMsg cancelSubscribe(@ApiParam(value = "uuId") @RequestParam(value = "uuId") String uuId,
                                     @ApiParam(value = "彩票类型Id") @RequestParam(value = "lotteryId") String lotteryId,
                                     @ApiParam(value = "邮箱") @RequestParam(value = "mail") String mail) throws ApiException {
        lotteryResultSubscribeService.cancelSubscribe(uuId, lotteryId, mail);
        return ResultMsg.success(ResultCodeEnum.SUCCESS.getCode());
    }

}
