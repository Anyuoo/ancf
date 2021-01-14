package com.anyu.common.exception;

import com.anyu.common.model.enums.ResultType;

public class GlobalException extends RuntimeException {
    /**
     * 结果枚举
     */
    private final ResultType resultType;


    private GlobalException(ResultType resultType) {
        super(resultType.getMessage());
        this.resultType = resultType;

    }

    /**
     * 通过{@link ResultType}构建异常
     *
     * @param result 结果枚举类
     */
    public static GlobalException causeBy(ResultType result) {
        return new GlobalException(result);
    }


    public ResultType getResultCode() {
        return resultType;
    }
}
