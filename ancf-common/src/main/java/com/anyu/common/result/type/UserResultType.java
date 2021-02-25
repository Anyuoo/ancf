package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
 * 和用户相关操作的结果
 * code: 起始为 20100 终止为 20199
 *
 * @author Anyu
 * @since 2021/1/15 上午10:46
 */
public enum UserResultType implements IResultType {
    NOT_EXIST(false,20100, "该用户不存在"),
    EXISTED(null,20101, "该用户已存在"),
    REGISTER_SUCCESS(true,20102, "用户注册成功！"),
    NOT_ACTIVE(null,20103, "用户未激活"),
    REGISTER_ERROR(false,20104, "用户注册失败！"),
    SEND_ACTIVATION_EMAIL_ERROR(false,20105, "发送激活码失败"),
    EMAIL_EXISTED(null,20106, "该邮箱已经被绑定"),
    MOBILE_EXISTED(null,20107, "该手机号已经被注册"),
    ACCOUNT_EXISTED(null,20108, "该账号已经被注册"),
    PASSWORD_ERROR(false,20109, "密码错误"),
    LOGIN_SUCCESS(true,20110,"用户登录成功"),
    LOGIN_ERROR(false,20111, "用户登录失败"),
    ACTIVE_SUCCESS(true,20112,"用户账号激活成功"),
    ACTIVE_ERROR(false,20113,"用户账号激活失败"),
    REMOVE_SUCCESS(true,20114,"用户注销成功"),
    REMOVE_ERROR(false, 20115, "用户注销失败"),
    UPDATE_INFO_SUCCESS(true,20116,"用户信息更新成功"),
    UPDATE_INFO_ERROR(false,20117,"用户信息更新失败"),
    NOT_LOGIN(false,20118,"用户未登录"),
    ;

    private final Boolean success;
    private final int code;
    private final String message;

    UserResultType(Boolean success,int code, String message) {
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
