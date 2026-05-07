package org.yeh.wang.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回数据结构
 *
 * @author YehWang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer status;
    private String message;
    private T data;

    /**
     * 成功
     * @param data 数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功
     * @param message 消息
     * @param data 数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败
     */
    public static Result<?> failed() {
        return new Result<>(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage(), null);
    }

    /**
     * 失败
     * @param message 消息
     */
    public static Result<?> failed(String message) {
        return new Result<>(ResultEnum.ERROR.getCode(), message, null);
    }

    /**
     * 失败
     * @param errorResult 错误结果
     */
    public static Result<?> failed(IResult errorResult) {
        return new Result<>(errorResult.getCode(), errorResult.getMessage(), null);
    }

    /**
     * 失败
     * @param code 状态码
     * @param message 消息
     */
    public static Result<?> failed(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 实例
     * @param code 状态码
     * @param message 消息
     * @param data 数据
     */
    public static <T> Result<T> instance(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setStatus(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
