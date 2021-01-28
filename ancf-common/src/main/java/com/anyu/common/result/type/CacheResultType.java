package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
 * the code begin with 20200 ,end with 20210
 *
 * @author Anyu
 * @since 2021/1/27 上午11:08
 */
public enum CacheResultType implements IResultType {
    UPDATE_ERROR(20200, "更新缓存失败"),
    DELETE_ERROT(20201, "删除缓存失败"),
    SAVE_ERROR(20202, "添加缓存失败");
    private final int code;
    private final String message;

    CacheResultType(int code, String message) {
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
