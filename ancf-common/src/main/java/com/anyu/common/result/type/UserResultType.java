package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
 * some about user operation ,which may be produce result
 * the code begin with 20100 ,end with 20199
 *
 * @author Anyu
 * @since 2021/1/15 上午10:46
 */
public enum UserResultType implements IResultType {
    NOT_EXIST(20100, "该用户不存在"),
    EXISTED(20101, "该用户已存在"),
    NOT_ACTIVE(20103, "用户未激活"),
    REGISTER_ERROR(20104, "注册失败！"),
    SEND_ACTIVATION_EMAIL_ERROR(20105, "发送激活码失败"),
    EMAIL_EXISTED(20106, "该邮箱已经被绑定"),
    MOBILE_EXISTED(20107, "该手机号已经被注册"),
    ACCOUNT_EXISTED(20108, "该账号已经被注册"),

    PASSWORD_ERROR(20109, "密码错误");
    private final int code;
    private final String message;

    UserResultType(int code, String message) {
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
