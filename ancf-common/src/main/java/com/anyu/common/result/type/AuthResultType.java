package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
*和安全权限相关操作的结果
 * code: 起始为 20400 终止为 20499
* @author Anyu
* @since 2021/1/29 上午11:20
*/
public enum AuthResultType implements IResultType {
    SUCCESS(true, 20400, "认证成功"),
    ERROR(false,20401, "认证失败"),
    TOKEN_SIGNATURE_ERROR(false,20402, "Token签名错误"),
    TOKEN_PARSE_ERROR(false,20403, "Token解析错误");

    private final Boolean success;
    private final int code;
    private final String message;

    AuthResultType(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public Boolean isSuccess() {
        return null;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
