package com.anyu.common.result.type;

/**
*和缓存相关操作的结果
 * code: 起始为 20200 终止为 20299
 *
* @author Anyu
* @since 2021/1/29 上午11:20
*/
import com.anyu.common.result.IResultType;

/**
 *20400 - 20499
 * @author Anyu
 * @since 2021/1/27 上午11:08
 */
public enum CacheResultType implements IResultType {
    UPDATE_ERROR(false, 20200, "更新缓存失败"),
    DELETE_ERROT(false, 20201, "删除缓存失败"),
    SAVE_ERROR(false, 20202, "添加缓存失败");

    private final Boolean success;
    private final int code;
    private final String message;

    CacheResultType(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


    @Override
    public Boolean isSuccess() {
        return success;
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
