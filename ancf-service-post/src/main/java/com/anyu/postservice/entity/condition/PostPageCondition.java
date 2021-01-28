package com.anyu.postservice.entity.condition;

import com.anyu.common.model.enums.PostType;

/**
 * 帖子查询条件
 *
 * @author Anyu
 * @since 2021/1/26 上午9:58
 */
public class PostPageCondition {
    private PostType type;
    private int userId;
    private String title;

    public PostType getType() {
        return type;
    }

    public PostPageCondition setType(PostType type) {
        this.type = type;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public PostPageCondition setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostPageCondition setTitle(String title) {
        this.title = title;
        return this;
    }
}
