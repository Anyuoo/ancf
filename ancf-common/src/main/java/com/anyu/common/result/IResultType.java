package com.anyu.common.result;

/**
 * 操作结果枚举顶层接口
 * 根据该接口所实现的结果枚举，可交由全局异常{@link com.anyu.common.exception.GlobalException}
 * 或者全局结果{@link CommonResult}，生成特定的异常或结果
 *
 * @author Anyu
 * @since 2021/1/15 上午10:26
 */
public interface IResultType {
    /**
     * 操作是否成功，三种状态[true:成功，false:失败，null: 该结果为状态]
     */
    Boolean isSuccess();

    int getCode();

    String getMessage();
}
