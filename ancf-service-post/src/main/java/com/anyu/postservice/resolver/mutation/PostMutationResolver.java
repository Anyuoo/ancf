package com.anyu.postservice.resolver.mutation;


import com.anyu.authservice.annotation.UserRole;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.result.type.PostResultType;
import com.anyu.postservice.model.input.PostInput;
import com.anyu.postservice.service.LikeService;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 帖子信息变更操作
 *
 * @author Anyu
 * @since 2020/10/10
 */
@MutationResolver
public class PostMutationResolver implements GraphQLMutationResolver {

    @Resource
    private PostService postService;
    @Resource
    private AuthService authService;
    @Resource
    private LikeService likeService;

    @UserRole
    public Boolean publishPost(@Validated PostInput input) {
        return postService.publishPost(input.toEntity(), authService.getCurrentUserId());
    }

    /**
     * 帖子点赞
     * @param postId 帖子id
     * @return 点赞状态
     */
    public Boolean postLike(Integer postId) {
        int currentUserId = authService.getCurrentUserId();
        postService.getPostById(postId).ifPresent(post -> {
            likeService.doPostLike(currentUserId,postId,post.getUserId());
        });
        return likeService.getPostLikeStatus(currentUserId, postId);
    }

}
