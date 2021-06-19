package space.laimengchao.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import space.laimengchao.domain.LotteryDetail;
import space.laimengchao.mapper.lottery.LotteryDetailMapper;
import space.laimengchao.service.lottery.LotteryDetailService;

import java.util.List;

@Service
@Transactional
public class LotteryDetailServiceImpl implements LotteryDetailService {

    @Autowired
    private LotteryDetailMapper lotteryDetailMapper;

    @Override
    public int insert(LotteryDetail lotteryDetail) {
        return lotteryDetailMapper.insert(lotteryDetail);
    }

    @Override
    public LotteryDetail findLotteryDetail(String lotteryNo, String lotteryId) {
        QueryWrapper<LotteryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lottery_no", lotteryNo);
        queryWrapper.eq("lottery_id", lotteryId);
        List<LotteryDetail> list = lotteryDetailMapper.selectList(queryWrapper);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

}
