package com.anyu.postservice.service;


import com.anyu.common.model.entity.Post;
import com.anyu.common.util.GlobalConstant;
import com.anyu.postservice.entity.condition.PostPageCondition;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.entity.vo.PostVO;
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
public interface PostService extends IService<Post>, GlobalConstant {

    Optional<Post> getPostById(Long id);

    boolean publishPost(@NonNull PostInput input);

    List<Post> listPostAfter(int first, Long postId, PostPageCondition condition);

    List<PostVO> listPostVOAfter(int first, Long postId, PostPageCondition condition);

    PostVO convertPostToVO(@NotNull Post post);
}