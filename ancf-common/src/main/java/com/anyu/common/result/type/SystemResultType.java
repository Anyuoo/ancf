package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
 * 方法处理得到的系统级结果
 * code: 起始为 20000 终止为 20099
 */
public enum SystemResultType implements IResultType {
    SUCCESS(true,2000, "操作成功"),
    FAILED(false,2001, "操作失败"),
    SYS_ERROR(false,2003, "系统错误"),
    UNKNOWN_ERROR(false,2004, "未知错误"),
    PARAM_ERROR(false,2005, "参数错误");


    private final Boolean success;
    private final int code;
    private final String message;

    SystemResultType(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


    @Override
    public Boolean isSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
