package space.laimengchao.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.laimengchao.domain.LotteryResult;
import space.laimengchao.mapper.lottery.LotteryResultMapper;
import space.laimengchao.service.lottery.LotteryResultService;

import java.util.List;

@Service
@Transactional
public class LotteryResultServiceImpl implements LotteryResultService {

    @Autowired
    private LotteryResultMapper lotteryResultMapper;

    @Override
    public int insert(LotteryResult lotteryResult) {
        return lotteryResultMapper.insert(lotteryResult);
    }

    @Override
    public Page<LotteryResult> getHistoryLotteryNumber(String lotteryNo, String lotteryId, Integer page, Integer limit) {
        Page<LotteryResult> pageBean = new Page<>();
        pageBean.setCurrent(page);
        pageBean.setSize(limit);
        QueryWrapper<LotteryResult> queryWrapper = new QueryWrapper();
        queryWrapper.eq("lottery_id", lotteryId);
        if (StringUtils.isNotBlank(lotteryNo)) {
            queryWrapper.eq("lottery_no", lotteryNo);
        }
        queryWrapper.orderByDesc("lottery_date");
        return lotteryResultMapper.selectPage(pageBean, queryWrapper);
    }

    @Override
    public LotteryResult getLotteryResult(String lotteryNo, String lotteryId) {
        QueryWrapper<LotteryResult> queryWrapper = new QueryWrapper();
        queryWrapper.eq("lottery_id", lotteryId);
        if (StringUtils.isNotEmpty(lotteryNo)) {
            queryWrapper.eq("lottery_no", lotteryNo);
        } else {
            queryWrapper.orderByDesc("lottery_date");
        }
        List<LotteryResult> list = lotteryResultMapper.selectList(queryWrapper);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int update(LotteryResult lotteryResult) {
        return lotteryResultMapper.updateById(lotteryResult);
    }

}
