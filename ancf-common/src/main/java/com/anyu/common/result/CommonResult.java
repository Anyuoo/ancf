package com.anyu.common.result;

import com.anyu.common.result.type.SystemResultType;

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
     * 根据枚举结果得到同意返回对象
     */
    public static CommonResult with(IResultType resultType,Object data) {
        return new CommonResult(resultType.isSuccess(), resultType.getCode(), resultType.getMessage(), data);
    }

    public static CommonResult with(IResultType resultType) {
        return with(resultType, null);
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
