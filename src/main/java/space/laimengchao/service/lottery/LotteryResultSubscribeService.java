package space.laimengchao.service.lottery;

import space.laimengchao.domain.LotteryResultSubscribe;
import space.laimengchao.exception.ApiException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LotteryResultSubscribeService {

    int delete(Long id);

    int insert(LotteryResultSubscribe lotteryResultSubscribe);

    void sendActivateSubscribeMail(HttpServletRequest request, String mail, String lotteryId) throws ApiException;

    void sendCancelSubscribeMail(HttpServletRequest request, String mail, String lotteryId) throws ApiException;

    void activateSubscribe(String uuId, String lotteryId, String mail) throws ApiException;

    void cancelSubscribe(String uuId, String lotteryId, String mail) throws ApiException;

    LotteryResultSubscribe selectLotteryResultSubscribe(String lotteryId, String mail, int subscribeType);

    List<LotteryResultSubscribe> findLotteryResultSubscribeBySubscribeType(int subscribeType);

}
