package com.anyu.common.model.enums;

import com.anyu.common.model.IEnumsCode;

/**
 * 查询用户的排序规则枚举类
 *
 * @author Anyu
 * @since 2020/11/2
 */
public enum UserOrderType implements IEnumsCode {
    DEFAULT(0, "默认排序规则"),
    DESC_CREATE_TIME(1, "按用户创建时间降序"),
    DESC_MODIFIED_TIME(2, "按用户修改时间降序"),
    DESC_AGE(3, "按用户年龄时间降序"),
    ASC_CREATE_TIME(4, "按用户创建时间升序"),
    ASC_MODIFIED_TIME(5, "按用户修改时间升序"),
    ASC_AGE(6, "按用户年龄升序");

    private int code;
    private String desc;

    UserOrderType(int code, String desc) {
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
