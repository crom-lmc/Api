package space.laimengchao.controller.lottery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.laimengchao.constant.ResultCodeEnum;
import space.laimengchao.constant.lottery.LotteryTypeEnum;
import space.laimengchao.domain.LotteryResult;
import space.laimengchao.exception.ApiException;
import space.laimengchao.service.lottery.LotteryDetailService;
import space.laimengchao.service.lottery.LotteryResultService;
import space.laimengchao.util.RandomLotteryUtils;
import space.laimengchao.vo.lottery.LotteryResultVO;
import space.laimengchao.vo.ResultMsg;

@RestController
@RequestMapping("/lottery")
@Api(value = "lottery", tags = "lottery", description = "提供查询彩票开奖结果功能")
public class LotteryController {

    @Autowired
    private LotteryResultService lotteryResultService;
    @Autowired
    private LotteryDetailService lotteryDetailService;

    @ApiOperation(value = "getLotteryResult.json", notes = "根据彩票类型和期号获取彩票开奖结果")
    @GetMapping("getLotteryResult.json")
    public ResultMsg<LotteryResultVO> getLotteryResult(@ApiParam(value = "彩票类型Id") @RequestParam(value = "lotteryId") String lotteryId,
                                                       @ApiParam(value = "彩票期号") @RequestParam(value = "lotteryNo", required = false) String lotteryNo) throws Exception {
        LotteryResultVO lotteryResultVO = new LotteryResultVO();
        LotteryResult lotteryResult = lotteryResultService.getLotteryResult(lotteryNo, lotteryId);
        if (lotteryResult == null) {
            throw new ApiException(ResultCodeEnum.NO_DATA_FOUND);
        }
        BeanUtils.copyProperties(lotteryResult, lotteryResultVO);
        lotteryResultVO.setLotteryDetail(lotteryDetailService.findLotteryDetail(lotteryResult.getLotteryNo(), lotteryResult.getLotteryId()));
        return ResultMsg.success(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), lotteryResultVO);
    }

    @ApiOperation(value = "getHistoryLotteryNumber.json", notes = "根据彩票类型获取彩票历史开奖结果")
    @GetMapping("getHistoryLotteryNumber.json")
    public ResultMsg<LotteryResultVO> getHistoryLotteryNumber(@ApiParam(value = "彩票类型Id") @RequestParam(value = "lotteryId") String lotteryId,
                                                              @ApiParam(value = "页码", example = "1") @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @ApiParam(value = "每页记录数", example = "50") @RequestParam(value = "limit", defaultValue = "50") Integer limit,
                                                              @ApiParam(value = "彩票期号") @RequestParam(value = "lotteryNo", required = false) String lotteryNo) {
        return ResultMsg.success(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), lotteryResultService.getHistoryLotteryNumber(lotteryNo, lotteryId, page, limit));
    }

    @ApiOperation(value = "getRandomNumber.json", notes = "随机获取号码")
    @GetMapping("getRandomNumber.json")
    public ResultMsg getRandomNumber(@ApiParam(value = "彩票类型Id") @RequestParam(value = "lotteryId") String lotteryId,
                                     @ApiParam(value = "组数", example = "1") @RequestParam(value = "groupQty", defaultValue = "1") Integer groupQty) throws ApiException {
        if (LotteryTypeEnum.SSQ.getCode().equals(lotteryId)) {
            return ResultMsg.success(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), RandomLotteryUtils.randomTwoColorBallVo(groupQty));
        }
        throw new ApiException(ResultCodeEnum.LOTTERY_TYPE_ERROR);
    }

}
