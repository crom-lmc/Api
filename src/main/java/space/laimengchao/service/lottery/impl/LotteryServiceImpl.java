package space.laimengchao.service.lottery.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import space.laimengchao.constant.lottery.LotteryConstant;
import space.laimengchao.constant.ResultCodeEnum;
import space.laimengchao.domain.LotteryResult;
import space.laimengchao.exception.ApiException;
import space.laimengchao.mapper.lottery.LotteryResultMapper;
import space.laimengchao.service.lottery.LotteryService;
import space.laimengchao.util.RandomLotteryUtils;
import space.laimengchao.vo.lottery.LotteryTypeVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LotteryServiceImpl implements LotteryService {

    @Value("${polymerization.lottery.key}")
    private String key;

    @Autowired
    private LotteryResultMapper lotteryResultMapper;

    @Override
    public List<String> randomSSQ(int groupQty) throws Exception {
        if (groupQty < 0) {
            throw new ApiException(ResultCodeEnum.PARAM_ERROR,"组数不能小于0");
        } else if (groupQty > 10) {
            throw new ApiException(ResultCodeEnum.PARAM_ERROR,"组数不能大于10");
        }
        return RandomLotteryUtils.randomTwoColorBallVo(groupQty);
    }

    @Override
    public String getLotteryResult(String lotteryId, String no) throws Exception {
        JSONObject jsonObject = getLotteryDetail(lotteryId, no);
        if (jsonObject != null) {
            return jsonObject.getString("lottery_res");
        }
        throw new ApiException(ResultCodeEnum.REMOTE_PROCEDURE_CALL_FAIL);
    }

    @Override
    public JSONObject getLotteryDetail(String lotteryId, String no) throws Exception {
        String url = LotteryConstant.URL.ROOT_PATH + LotteryConstant.URL.QUERY + "?key=" + key;
        url = url + "&lottery_id=" + lotteryId;
        if (StringUtils.isNotBlank(no)) {
            url = url + "&lottery_no=" + no;
        }
        HttpRequest httpRequest = HttpUtil.createGet(url);
        HttpResponse httpResponse = httpRequest.execute();
        if (httpResponse.isOk()) {
            JSONObject result = JSON.parseObject(httpResponse.body());
            if (result.getIntValue("error_code") == LotteryConstant.SUCCESS_CODE) {
                JSONObject jsonObject = result.getJSONObject("result");
                return jsonObject;
            } else {
                throw new Exception(result.getString("reason"));
            }
        }
        throw new ApiException(ResultCodeEnum.REMOTE_PROCEDURE_CALL_FAIL);
    }

    @Override
    public void importLotteryHistoryResult(String lotteryId, int pageSize, int page) throws Exception {
        for (int x = 1; x < 119; x++) {
            List<LotteryResult> list = getLotteryHistoryResult(lotteryId, pageSize, x);
            Optional.ofNullable(list).orElse(new ArrayList<>()).stream().forEach(item -> {
                QueryWrapper<LotteryResult> queryWrapper = new QueryWrapper();
                queryWrapper.eq("lottery_Id", lotteryId);
                queryWrapper.eq("lottery_no", item.getLotteryNo());
                LotteryResult lotteryResult = lotteryResultMapper.selectOne(queryWrapper);
                if (lotteryResult == null) {
                    lotteryResultMapper.insert(item);
                }
            });
        }
    }

    @Override
    public List<LotteryResult> getLotteryHistoryResult(String lotteryId, int pageSize, int page) throws Exception {
        String url = LotteryConstant.URL.ROOT_PATH + LotteryConstant.URL.HISTORY + "?key=" + key;
        if (StringUtils.isNotBlank(lotteryId)) {
            url = url + "&lottery_id=" + lotteryId;
        }
        if (pageSize > 0) {
            url = url + "&page_size=" + pageSize;
        }
        if (page > 0) {
            url = url + "&page=" + page;
        }
        HttpRequest httpRequest = HttpUtil.createGet(url);
        HttpResponse httpResponse = httpRequest.execute();
        if (httpResponse.isOk()) {
            JSONObject result = JSON.parseObject(httpResponse.body());
            if (result.getIntValue("error_code") == LotteryConstant.SUCCESS_CODE) {
                JSONObject jsonObject = result.getJSONObject("result");
                String lotteryResList = jsonObject.getString("lotteryResList");
                return JSON.parseArray(lotteryResList, LotteryResult.class);
            } else {
                throw new Exception(result.getString("reason"));
            }
        }
        throw new ApiException(ResultCodeEnum.REMOTE_PROCEDURE_CALL_FAIL);
    }

    @Override
    public List<LotteryTypeVO> types() throws Exception {
        HttpRequest httpRequest = HttpUtil.createGet(LotteryConstant.URL.ROOT_PATH + LotteryConstant.URL.TYPE + "?key=" + key);
        HttpResponse httpResponse = httpRequest.execute();
        if (httpResponse.isOk()) {
            JSONObject result = JSON.parseObject(httpResponse.body());
            if (result.getIntValue("error_code") == LotteryConstant.SUCCESS_CODE) {
                List<LotteryTypeVO> list = new ArrayList<>();
                JSONArray jsonArray = result.getJSONArray("result");
                for (int x = 0, size = jsonArray.size(); x < size; x++) {
                    JSONObject jo = jsonArray.getJSONObject(x);
                    LotteryTypeVO lotteryTypeVO = new LotteryTypeVO(jo.getString("lottery_id"), jo.getString("lottery_name"),
                            jo.getString("lottery_type_id"), jo.getString("remarks"));
                    list.add(lotteryTypeVO);
                }
                return list;
            } else {
                throw new Exception(result.getString("reason"));
            }
        }
        throw new ApiException(ResultCodeEnum.REMOTE_PROCEDURE_CALL_FAIL);
    }

}
