package com.anyu.common.result.type;

import com.anyu.common.result.IResultType;

/**
*和帖子相关操作的结果
 * code: 起始为 20300 终止为 20399
* @author Anyu
* @since 2021/1/29 上午10:52
*/
public enum PostResultType implements IResultType {
    PULISH_SUCCESS(true,20300,"帖子发布成功"),
    PUBLISH_ERROR(false,20301,"帖子发布失败"),
    COMMENT_SUCCESS(true,20302,"评论成功"),
    COMMENT_ERROR(false,20303,"发送评论失败"),
    ;

    private final Boolean success;
    private final int code;
    private final String message;

    PostResultType(Boolean success, int code, String message) {
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
