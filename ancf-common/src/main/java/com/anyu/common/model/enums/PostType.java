package com.anyu.common.model.enums;


import com.anyu.common.model.IEnumsCode;
import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Anyu
 * @since 2020/10/10
 */
public enum PostType implements IEnumsCode {
    NORMAL(0, "正常"),
    QUALITY(1, "精品");

    @EnumValue
    final int code;

    final String desc;

    PostType(int code, String desc) {
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
