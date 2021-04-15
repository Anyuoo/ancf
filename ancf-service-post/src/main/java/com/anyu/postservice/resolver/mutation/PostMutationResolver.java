package com.anyu.postservice.resolver.mutation;


import com.anyu.authservice.annotation.UserRole;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.result.type.PostResultType;
import com.anyu.postservice.model.input.PostInput;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;

import javax.annotation.Resource;

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

    @UserRole
    public CommonResult publishPost(PostInput input) {
        if (postService.publishPost(input.toEntity(), authService.getCurrentUserId())) {
            return CommonResult.with(PostResultType.PULISH_SUCCESS);
        }
        return CommonResult.with(PostResultType.PUBLISH_ERROR);
    }

}
