package com.anyu.postservice.resolver.mutation;


import com.anyu.common.model.CommonResult;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * 帖子信息变更操作
 *
 * @author Anyu
 * @since 2020/10/10
 */
@Service
public class PostMutationResolver implements GraphQLMutationResolver {

    @Autowired
    private PostService postService;

    public CompletableFuture<CommonResult> publishPost(PostInput input) {
        return CompletableFuture.supplyAsync(() -> postService.publishPost(input));
    }

}
