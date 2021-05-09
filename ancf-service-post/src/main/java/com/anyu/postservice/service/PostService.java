package com.anyu.postservice.service;


import com.anyu.common.model.entity.Post;
import com.anyu.common.util.GlobalConstant;
import com.anyu.postservice.model.condition.PostPageCondition;
import com.anyu.postservice.model.vo.PostVO;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * (Post)表服务接口
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
public interface PostService extends IService<Post> {

    Optional<Post> getPostById(Integer id);

    boolean publishPost(@NonNull Post post, int publisherId);

    List<Post> listPostAfter(int first, Integer postId, PostPageCondition condition);

    List<PostVO> listPostVOAfter(int first, Integer postId, PostPageCondition condition);

    PostVO convertPostToVO(@NotNull Post post);
}