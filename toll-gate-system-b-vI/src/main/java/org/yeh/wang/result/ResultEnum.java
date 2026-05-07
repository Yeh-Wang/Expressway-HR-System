package org.yeh.wang.result;

import lombok.Setter;

/**
 * 状态码枚举
 */
@Setter
public class ResultEnum implements IResult {

    /**
     * 成功
     */
    public static final ResultEnum SUCCESS = new ResultEnum(200, "成功");
    /**
     * 失败
     */
    public static final ResultEnum ERROR = new ResultEnum(500, "失败");
    /**
     * 参数错误
     */
    public static final ResultEnum PARAM_ERROR = new ResultEnum(400, "参数错误");
    /**
     * 未登录
     */
    public static final ResultEnum NOT_LOGIN = new ResultEnum(401, "会话已失效，请登录");
    /**
     * 无权限
     */
    public static final ResultEnum NO_PERMISSION = new ResultEnum(403, "无权限");

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
