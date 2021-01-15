package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
 * file operation may be produce result
 * the code begin with 2050 ,end with 2099
 *
 * @author Anyu
 * @since 2021/1/15 下午1:27
 */
public enum FileResultType implements IResultType {
    UPLOAD_ERROR(2050, "文件上传错误"),
    UPLOAD_NAME_ERROR(2051, "文件上传名错误"),
    UPLOAD_REQUEST_ERROR(2052, "文件上传请求出错");

    private final int code;
    private final String message;

    FileResultType(int code, String message) {
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
