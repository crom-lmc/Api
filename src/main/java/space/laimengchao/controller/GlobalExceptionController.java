package space.laimengchao.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import space.laimengchao.constant.ResultCodeEnum;
import space.laimengchao.vo.ResultMsg;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMsg exception(HttpServletRequest request, Exception ex) {
        String msg = ExceptionUtil.getMessage(ex);
        log.error(msg);
        return ResultMsg.fail(ResultCodeEnum.PARAM_ERROR.getCode(), msg);
    }

}
