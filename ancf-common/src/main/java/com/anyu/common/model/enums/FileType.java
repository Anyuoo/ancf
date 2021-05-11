package com.anyu.common.model.enums;

import com.anyu.common.model.IEnumsCode;
import com.baomidou.mybatisplus.annotation.EnumValue;
/**
*文件类型枚举
* @author Anyu
* @since 2021/5/11
*/
public enum FileType implements IEnumsCode {
    VIDEO(0,"视频"),
    PICTURE(1,"图片")
    ;
    /* {@EnumValue} mybatis-plus 枚举类字段处理*/
    @EnumValue
    private final int code;
    private final String desc;

    FileType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getDesc() {
        return null;
    }
}
