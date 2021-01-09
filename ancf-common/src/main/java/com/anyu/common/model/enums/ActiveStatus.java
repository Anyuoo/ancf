package com.anyu.common.model.enums;

import com.anyu.common.model.IEnumsCode;
import com.baomidou.mybatisplus.annotation.EnumValue;

public enum ActiveStatus implements IEnumsCode {
    UNACTIVED(0, "未激活"),
    ACTIVED(1, "已激活");

    @EnumValue
    private int code;
    private String desc;

    ActiveStatus(int code, String desc) {
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
