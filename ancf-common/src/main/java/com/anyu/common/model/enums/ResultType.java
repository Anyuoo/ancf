package com.anyu.common.model.enums;

import com.anyu.common.model.Result;

public enum ResultType implements Result {
    SUCCESS(2000, "操作成功"),
    FAILED(2001, "操作失败"),
    AUTH_ERROR(401, "认证失败"),
    SYS_ERROR(500, "系统错误"),
    PARAM_ERROR(400, "参数错误"),
    UNKNOWN_ERROR(499, "未知错误"),
    FILE_UPLOAD_ERROR(2004, "文件上传错误"),
    FILE_UPLOAD_NAME_ERROR(2005, "文件上传名错误"),
    FILE_UPLOAD_REQUEST_ERROR(2006, "文件上传请求出错"),

    TOKEN_SIGNATURE_ERROR(20010, "Token签名错误"),
    TOKEN_PARSE_ERROR(20011, "Token解析错误");


    private int code;
    private String message;

    ResultType(int code, String message) {
        this.code = code;
        this.message = message;
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
