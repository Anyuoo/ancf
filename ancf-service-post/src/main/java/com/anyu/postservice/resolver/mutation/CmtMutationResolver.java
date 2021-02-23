package com.anyu.postservice.resolver.mutation;


import com.anyu.common.result.CommonResult;
import com.anyu.common.result.type.PostResultType;
import com.anyu.postservice.entity.input.CommentInput;
import com.anyu.postservice.service.CommentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CmtMutationResolver implements GraphQLMutationResolver {
    @Resource
    private CommentService commentService;

    public CommonResult createComment(CommentInput input) {
        if (commentService.createComment(input)) {
            return CommonResult.with(PostResultType.COMMENT_SUCCESS);
        }
        return CommonResult.with(PostResultType.COMMENT_ERROR);
    }
}
