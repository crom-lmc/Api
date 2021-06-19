package space.laimengchao.service.lottery.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.laimengchao.cache.RedisUtils;
import space.laimengchao.constant.*;
import space.laimengchao.constant.lottery.LotteryTypeEnum;
import space.laimengchao.domain.LotteryResultSubscribe;
import space.laimengchao.exception.ApiException;
import space.laimengchao.mapper.lottery.LotteryResultSubscribeMapper;
import space.laimengchao.service.lottery.LotteryResultSubscribeService;
import space.laimengchao.service.MailService;
import space.laimengchao.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class LotteryResultSubscribeServiceImpl implements LotteryResultSubscribeService {

    @Autowired
    private LotteryResultSubscribeMapper lotteryResultSubscribeMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private RedisUtils redisUtils;
    private final static long CACHE_EXPIRE_TIME = 300L;
    private final static long CACHE_ACCESS_LIMIT_EXPIRE_TIME = 60L;
    @Value("${server.context-path}")
    private String contextPath;

    @Override
    public int delete(Long id) {
        return lotteryResultSubscribeMapper.deleteById(id);
    }

    @Override
    public int insert(LotteryResultSubscribe lotteryResultSubscribe) {
        return lotteryResultSubscribeMapper.insert(lotteryResultSubscribe);
    }

    @Override
    public void sendActivateSubscribeMail(HttpServletRequest request, String mail, String lotteryId) throws ApiException {
        // 检查是否可以再次访问,这个接口不要频繁被访问
        String encodeIp = Base64.encode(IPUtils.getIpAddress(request));
        String key = RedisKeyConstant.LOTTERY_ACTIVE_SUBSCRIBE_ACCESS_LIMIT + encodeIp;
        if (null != redisUtils.getValue(key)) {
            throw new ApiException(ResultCodeEnum.ACCESS_LIMIT);
        }
        String uuid = UUID.randomUUID().toString();
        redisUtils.set(RedisKeyConstant.LOTTERY_ACTIVE_SUBSCRIBE_MAIL + ":" + mail, uuid, CACHE_EXPIRE_TIME);
        String url = getRootPath() + "/subscribe/activateSubscribe.json?uuId=" + uuid + "&lotteryId=" + Base64.encode(lotteryId) + "&mail=" + Base64.encode(mail);
        String lotteryName = LotteryTypeEnum.findName(lotteryId);
        mailService.sendHtmlMail(new String[]{mail}, null, null, "订阅" + lotteryName + "开奖结果激活", "点击下方链接\r\n" + url + "\r\n激活" + lotteryName + "开奖结果订阅,链接" + CACHE_EXPIRE_TIME + "秒内有效");
        // 设置接口访问限制key
        redisUtils.set(RedisKeyConstant.LOTTERY_ACTIVE_SUBSCRIBE_ACCESS_LIMIT + encodeIp, encodeIp, CACHE_ACCESS_LIMIT_EXPIRE_TIME);
    }

    @Override
    public void sendCancelSubscribeMail(HttpServletRequest request, String mail, String lotteryId) throws ApiException {
        // 检查是否可以再次访问,这个接口不要频繁被访问
        String encodeIp = Base64.encode(IPUtils.getIpAddress(request));
        String key = RedisKeyConstant.LOTTERY_CANCEL_ACCESS_LIMIT + encodeIp;
        if (null != redisUtils.getValue(key)) {
            throw new ApiException(ResultCodeEnum.ACCESS_LIMIT);
        }
        String uuid = UUID.randomUUID().toString();
        redisUtils.set(RedisKeyConstant.LOTTERY_CANCEL_SUBSCRIBE_MAIL + ":" + mail, uuid, CACHE_EXPIRE_TIME);
        String url = getRootPath() + "/subscribe/cancelSubscribe.json?uuId=" + uuid + "&lotteryId=" + Base64.encode(lotteryId) + "&mail=" + Base64.encode(mail);
        String lotteryName = LotteryTypeEnum.findName(lotteryId);
        mailService.sendHtmlMail(new String[]{mail}, null, null, "取消订阅" + lotteryName + "开奖结果", "点击下方链接\r\n" + url + "\r\n取消订阅" + lotteryName + "开奖结果,链接" + CACHE_EXPIRE_TIME + "秒内有效");
        // 设置接口访问限制key
        redisUtils.set(RedisKeyConstant.LOTTERY_CANCEL_ACCESS_LIMIT + encodeIp, encodeIp, CACHE_ACCESS_LIMIT_EXPIRE_TIME);
    }

    @Override
    public void activateSubscribe(String uuId, String lotteryId, String mail) throws ApiException {
        String decodeLotteryId = new String(Base64.decode(lotteryId.getBytes(StandardCharsets.UTF_8)));
        String decodeMail = new String(Base64.decode(mail.getBytes(StandardCharsets.UTF_8)));
        String key = RedisKeyConstant.LOTTERY_ACTIVE_SUBSCRIBE_MAIL + ":" + decodeMail;
        String uuid = redisUtils.getValue(key);
        LotteryResultSubscribe lotteryResultSubscribe = new LotteryResultSubscribe();
        lotteryResultSubscribe.setLotteryId(decodeLotteryId);
        lotteryResultSubscribe.setSubscribeAddress(decodeMail);
        lotteryResultSubscribe.setSubscribeType(SubscribeTypeEnum.MAIL.getCode());
        if (StringUtils.isEmpty(uuid)) {
            throw new ApiException(ResultCodeEnum.URL_EXPIRE);
        }
        LotteryResultSubscribe existsLotteryResultSubscribe = selectLotteryResultSubscribe(decodeLotteryId, decodeMail, SubscribeTypeEnum.MAIL.getCode());
        if (existsLotteryResultSubscribe == null) {
            insert(lotteryResultSubscribe);
        } else {
            UpdateWrapper<LotteryResultSubscribe> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("cancel_date", null);
            updateWrapper.eq("id", existsLotteryResultSubscribe.getId());
            lotteryResultSubscribeMapper.update(existsLotteryResultSubscribe, updateWrapper);
        }
        redisUtils.deleteKey(key);
    }

    @Override
    public void cancelSubscribe(String uuId, String lotteryId, String mail) throws ApiException {
        String decodeLotteryId = new String(Base64.decode(lotteryId.getBytes(StandardCharsets.UTF_8)));
        String decodeMail = new String(Base64.decode(mail.getBytes(StandardCharsets.UTF_8)));
        String key = RedisKeyConstant.LOTTERY_CANCEL_SUBSCRIBE_MAIL + ":" + decodeMail;
        String uuid = redisUtils.getValue(key);
        if (StringUtils.isEmpty(uuid)) {
            throw new ApiException(ResultCodeEnum.URL_EXPIRE);
        }
        LotteryResultSubscribe lotteryResultSubscribe = selectLotteryResultSubscribe(decodeLotteryId, decodeMail, SubscribeTypeEnum.MAIL.getCode());
        if (lotteryResultSubscribe != null) {
            lotteryResultSubscribe.setCancelDate(new Date());
            lotteryResultSubscribeMapper.updateById(lotteryResultSubscribe);
        }
        redisUtils.deleteKey(key);
    }

    @Override
    public LotteryResultSubscribe selectLotteryResultSubscribe(String lotteryId, String mail, int subscribeType) {
        QueryWrapper<LotteryResultSubscribe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lottery_id", lotteryId);
        queryWrapper.eq("subscribe_type", subscribeType);
        queryWrapper.eq("subscribe_address", mail);
        return lotteryResultSubscribeMapper.selectOne(queryWrapper);
    }

    @Override
    public List<LotteryResultSubscribe> findLotteryResultSubscribeBySubscribeType(int subscribeType) {
        QueryWrapper <LotteryResultSubscribe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subscribe_type",subscribeType);
        return lotteryResultSubscribeMapper.selectList(queryWrapper);
    }

    private String getRootPath() {
        if (ProfileEnum.DEV.getProfileName().equalsIgnoreCase(context.getEnvironment().getActiveProfiles()[0])) {
            return "http://www.laimengchao.space" + contextPath;
        } else {
            return "http://localhost:8080" + contextPath;
        }
    }

}
