package com.anyu.common.exception;

import com.anyu.common.result.IResultType;
import com.anyu.common.result.type.ResultType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * global application exception
 *
 * @author Anyu
 * @since 2021/1/15 上午10:59
 */
public class GlobalException extends RuntimeException {

    private final int code;

    private GlobalException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     *
     * @param resultType 结果枚举类
     */
    public static GlobalException causeBy(IResultType resultType) {
        return new GlobalException(resultType.getMessage(), resultType.getCode());
    }
    /**
     *
     * @param resultType 结果枚举类
     */
    public static GlobalException causeBy(IResultType resultType,String extMsg) {
        return new GlobalException(extMsg, resultType.getCode());
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return super.getMessage();
    }

}
