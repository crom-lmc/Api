package space.laimengchao.exception;

import lombok.Data;
import space.laimengchao.constant.ResultCodeEnum;

@Data
public class ApiException extends Exception {

    private int errorCode;

    public ApiException(ResultCodeEnum errorCode, String message) {
        super(String.format("%s : %s", errorCode.getMsg(), message));
        this.errorCode = errorCode.getCode();
    }

    public ApiException(ResultCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode.getCode();
    }

}
