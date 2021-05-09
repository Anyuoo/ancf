package com.anyu.postservice.resolver;

import com.anyu.common.model.entity.User;
import com.anyu.postservice.model.vo.PostVO;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class PostVoResolver implements GraphQLResolver<PostVO> {
    @Resource
    private UserService userService;

    public CompletableFuture<Optional<User>> getPublisher(PostVO postVO) {
        return CompletableFuture.supplyAsync(()->userService.getUserById(postVO.getUserId()));
    }
}
