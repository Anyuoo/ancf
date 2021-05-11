package com.anyu.commentservice.resolver;


import com.anyu.authservice.service.AuthService;
import com.anyu.commentservice.model.CommentInput;
import com.anyu.commentservice.service.CommentService;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.result.type.PostResultType;
import com.anyu.common.result.type.UserResultType;
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
        if (commentService.createComment(input.toEntity(), authService.getValidCUId())) {
            return CommonResult.with(PostResultType.COMMENT_SUCCESS);
        }
        return CommonResult.with(PostResultType.COMMENT_ERROR);
    }
}
