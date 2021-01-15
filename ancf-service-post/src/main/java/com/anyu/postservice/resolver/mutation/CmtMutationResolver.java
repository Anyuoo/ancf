package com.anyu.postservice.resolver.mutation;


import com.anyu.common.result.CommonResult;
import com.anyu.postservice.entity.input.CommentInput;
import com.anyu.postservice.service.CommentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmtMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private CommentService commentService;

    public CommonResult createComment(CommentInput input) {
        if (commentService.createComment(input)) {
            return CommonResult.succeed("comment successfully");
        }
        return CommonResult.failed("comment unsuccessfully");
    }
}
