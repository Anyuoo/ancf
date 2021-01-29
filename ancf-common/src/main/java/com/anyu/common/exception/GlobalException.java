package com.anyu.common.exception;

import com.anyu.common.result.IResultType;
import com.anyu.common.result.type.SystemResultType;
import org.jetbrains.annotations.NotNull;

/**
 * global application exception
 *
 * @author Anyu
 * @since 2021/1/15 上午10:59
 */
public class GlobalException extends RuntimeException {

    private final int code;
    private final String message;

    private GlobalException(@NotNull IResultType resultType) {
        super(resultType.getMessage());
        this.code = resultType.getCode();
        this.message = resultType.getMessage();
    }

    /**
     * 通过{@link SystemResultType}构建异常
     *
     * @param resultType 结果枚举类
     */
    public static GlobalException causeBy(IResultType resultType) {
        return new GlobalException(resultType);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
