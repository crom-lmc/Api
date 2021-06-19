package space.laimengchao.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultMsg <T> implements Serializable {

    /**
     * 返回状态(成功:true 失败:false)
     */
    private boolean success;

    /**
     * 状态码(成功:200 失败:500)
     */
    private Integer code;

    /**
     * 异常提示消息
     */
    private String msg;

    /**
     * 返回参数
     */
    private T data;

    public ResultMsg() {
    }

    public ResultMsg(boolean success) {
        this.success = success;
    }

    public ResultMsg(boolean success, Integer code) {
        this.success = success;
        this.code = code;
    }

    public ResultMsg(boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public ResultMsg(boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultMsg(boolean success, Integer code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    public static ResultMsg success() {
        return new ResultMsg(true);
    }

    public static ResultMsg success(Integer code) {
        return new ResultMsg(true, code);
    }

    public static ResultMsg success(Integer code, String msg) {
        return new ResultMsg(true, code, msg);
    }

    public static ResultMsg success(Integer code, String msg, Object data) {
        return new ResultMsg(true, code, msg, data);
    }

    public static ResultMsg success(Integer code, Object data) {
        return new ResultMsg(true);
    }

    public static ResultMsg fail() {
        return new ResultMsg(false);
    }

    public static ResultMsg fail(Integer code, String msg) {
        return new ResultMsg(false, code, msg);
    }

}
