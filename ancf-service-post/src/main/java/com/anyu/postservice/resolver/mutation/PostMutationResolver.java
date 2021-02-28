package com.anyu.postservice.resolver.mutation;


import com.anyu.authservice.annotation.UserRole;
import com.anyu.authservice.entity.AuthSubject;
import com.anyu.authservice.gql.AncfGqlHttpContext;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.result.type.PostResultType;
import com.anyu.common.result.type.UserResultType;
import com.anyu.postservice.entity.input.PostInput;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

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
        if (postService.publishPost(input,authService.getCurrentUserId())) {
            return CommonResult.with(PostResultType.PULISH_SUCCESS);
        }
        return CommonResult.with(PostResultType.PUBLISH_ERROR);
    }

}
