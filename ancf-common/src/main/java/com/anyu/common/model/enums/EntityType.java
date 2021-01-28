package com.anyu.common.model.enums;

import com.anyu.common.model.IEnumsCode;
import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 实体类型枚举 0-帖子，1-评论
 *
 * @author Anyu
 * @since 2020/10/10
 */
public enum EntityType implements IEnumsCode {
    POST(0, "帖子"),
    COMMENT(1, "评论"),
    USER(2, "用户");

    @EnumValue
    final int code;

    final String desc;

    EntityType(int code, String desc) {
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
