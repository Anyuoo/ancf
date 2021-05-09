package com.anyu.postservice.model.condition;

import com.anyu.common.model.entity.Post;
import com.anyu.common.model.enums.PostType;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

/**
 * 帖子查询条件
 *
 * @author Anyu
 * @since 2021/1/26 上午9:58
 */
public class PostPageCondition {
    private PostType type;
    private Integer userId;
    private String title;

    public PostType getType() {
        return type;
    }

    public  LambdaQueryChainWrapper<Post> initWrapper(LambdaQueryChainWrapper<Post> wrapper) {
       return wrapper.eq(title != null, Post::getType, type)
                .eq(userId != null, Post::getUserId, userId)
                .eq(title != null, Post::getTitle, title);
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
