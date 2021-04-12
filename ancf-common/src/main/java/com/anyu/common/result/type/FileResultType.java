package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
 * 和文件相关操作的结果
 * code: 起始为 20200 终止为 20299
 *
 * @author Anyu
 * @since 2021/1/15 下午1:27
 */
public enum FileResultType implements IResultType {
    UPLOAD_SUCCESS(true, 20200, "文件上传成功"),
    UPLOAD_ERROR(false, 20201, "文件上传错误"),
    UPLOAD_NAME_ERROR(false, 20202, "文件上传名错误"),
    UPLOAD_REQUEST_ERROR(false, 20203, "文件上传请求出错");

    private final Boolean success;
    private final int code;
    private final String message;

    FileResultType(Boolean success, int code, String message) {
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
