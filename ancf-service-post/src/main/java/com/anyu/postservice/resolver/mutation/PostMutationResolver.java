package com.anyu.postservice.resolver.mutation;


import com.anyu.common.result.CommonResult;
import com.anyu.common.result.type.PostResultType;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 帖子信息变更操作
 *
 * @author Anyu
 * @since 2020/10/10
 */
@Service
public class PostMutationResolver implements GraphQLMutationResolver {

    @Resource
    private PostService postService;

    public CommonResult publishPost(PostInput input) {
        if (postService.publishPost(input)) {
            return CommonResult.with(PostResultType.PULISH_SUCCESS);
        }
        return CommonResult.with(PostResultType.PUBLISH_ERROR);
    }

}
