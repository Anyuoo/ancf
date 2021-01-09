package com.anyu.common.model.enums;

import com.anyu.common.model.IEnumsCode;
import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 用户性别枚举类
 *
 * @author Anyu
 * @since 2020/10/9
 */
public enum Gender implements IEnumsCode {
    /**
     * 0,1 mybatis-plus 解析自动映射成boolean 值
     */
    NUKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    /* {@EnumValue} mybatis-plus 枚举类字段处理*/
    @EnumValue
    private final int code;
    private final String desc;

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
