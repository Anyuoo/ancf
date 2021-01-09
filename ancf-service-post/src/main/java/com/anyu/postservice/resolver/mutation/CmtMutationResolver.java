package com.anyu.postservice.resolver.mutation;


import com.anyu.common.model.CommonResult;
import com.anyu.postservice.entity.input.CommentInput;
import com.anyu.postservice.service.CommentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CmtMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private CommentService commentService;

    CompletableFuture<CommonResult> createComment(CommentInput input) {
        return CompletableFuture.supplyAsync(() -> commentService.createComment(input));
    }
}
