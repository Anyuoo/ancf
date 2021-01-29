package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
*
 * 和消息相关操作的结果
 * code: 起始为 20500 终止为 20599
* @author Anyu
* @since 2021/1/29 上午11:38
*/
public enum MsgResultType implements IResultType {
    SEND_SUCCESS(true,20500,"消息发送成功"),
    SEND_ERROR(false,20501,"消息发送失败"),

    ;

    private final Boolean success;
    private final int code;
    private final String message;

    MsgResultType(Boolean success, int code, String message) {
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
