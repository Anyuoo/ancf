package com.anyu.postservice.resolver.mutation;


import com.anyu.common.result.CommonResult;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public CommonResult publishPost(PostInput input) {
        if (postService.publishPost(input)) {
            return CommonResult.succeed("publish post successfully");
        }
        return CommonResult.failed();
    }

}
