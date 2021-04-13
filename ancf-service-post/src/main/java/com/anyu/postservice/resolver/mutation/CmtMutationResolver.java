package com.anyu.postservice.resolver.mutation;


import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.result.type.PostResultType;
import com.anyu.common.result.type.UserResultType;
import com.anyu.postservice.entity.input.CommentInput;
import com.anyu.postservice.service.CommentService;
import graphql.kickstart.tools.GraphQLMutationResolver;

import javax.annotation.Resource;

@MutationResolver
public class CmtMutationResolver implements GraphQLMutationResolver {
    @Resource
    private CommentService commentService;
    @Resource
    private AuthService authService;

    public CommonResult createComment(CommentInput input) {
        if (!authService.hasCurrentUserPermission()) {
            return CommonResult.with(UserResultType.NOT_LOGIN);
        }
        if (commentService.createComment(input.toEntity(), authService.getCurrentUserId())) {
            return CommonResult.with(PostResultType.COMMENT_SUCCESS);
        }
        return CommonResult.with(PostResultType.COMMENT_ERROR);
    }
}
