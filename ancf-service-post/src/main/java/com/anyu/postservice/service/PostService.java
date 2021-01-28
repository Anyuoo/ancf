package com.anyu.postservice.service;


import com.anyu.common.model.entity.Post;
import com.anyu.common.util.GlobalConstant;
import com.anyu.postservice.entity.condition.PostPageCondition;
import com.anyu.postservice.entity.input.PostInput;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * (Post)表服务接口
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
public interface PostService extends IService<Post>, GlobalConstant {

    Optional<Post> getPostById(Long id);

    /**
     * 创建帖子
     *
     * @param input 输入参数
     * @return 结果
     */
    boolean publishPost(@NonNull PostInput input);

    List<Post> listPostAfter(int first, Long postId, PostPageCondition condition);

}