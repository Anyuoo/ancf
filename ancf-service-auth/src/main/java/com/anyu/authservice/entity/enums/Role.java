package com.anyu.authservice.entity.enums;


import com.anyu.common.model.IEnumsCode;

public enum Role implements IEnumsCode {
    ROLE(0,"用户角色"),
    ADMIN(1,"管理员角色"),
    VISITOR(2,"游客角色"),
    ;
    private final int code;
    private final String desc;

    Role(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}