package com.anyu.common.model;

import com.anyu.common.model.enums.ResultType;

public class CommonResult {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    public CommonResult() {
    }

    public CommonResult(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功的处理结果
     *
     * @return CommonResult
     */
    public static CommonResult succeed() {
        return succeed(ResultType.SUCCESS.getMessage());
    }


    public static CommonResult succeed(String message) {
        return succeed(message, null);
    }


    public static CommonResult succeed(String message, Object data) {
        return new CommonResult(true, ResultType.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败的处理结果
     *
     * @return CommonResult
     */
    public static CommonResult failed() {
        return failed(ResultType.FAILED.getMessage());
    }

    /**
     * 失败的处理结果
     *
     * @return Result
     */
    public static CommonResult failed(String message) {
        return new CommonResult(false, ResultType.FAILED.getCode(), message, null);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public CommonResult setData(Object data) {
        this.data = data;
        return this;
    }
}
