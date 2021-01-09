package com.anyu.postservice.resolver.query;


import com.anyu.common.model.entity.Post;
import com.anyu.postservice.entity.vo.PostVo;
import com.anyu.postservice.service.PostService;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 帖子信息查询
 *
 * @author Anyu
 * @since 2020/10/10
 */
@Service
public class PostQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    public CompletableFuture<Optional<Post>> getPost(@NonNull Long id) {
        return CompletableFuture.supplyAsync(() -> {
            return postService.getPostById(id);
        });
    }

    public PostVo postDetails(Long id) {
        Post post = postService.getById(id);
        return PostVo.build()
                .setPost(post)
                .setPublisher(userService.getById(post.getUserId()));
    }

}
