package com.anyu.postservice.resolver;

import com.anyu.common.model.entity.User;
import com.anyu.postservice.entity.vo.PostVo;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class PostVoResolver implements GraphQLResolver<PostVo> {
    @Autowired
    private UserService userService;

    /**
     * 解析postVo publisher字段
     */
    public CompletableFuture<User> getPublisher(PostVo postVo) {
        return CompletableFuture
                .supplyAsync(() -> userService.getById(postVo.getPost().getUserId()))
                .thenApply(user -> user);
    }

}
