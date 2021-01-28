package com.anyu.postservice.resolver.query;


import com.anyu.common.model.entity.Post;
import com.anyu.common.result.CommonPage;
import com.anyu.postservice.entity.condition.PostPageCondition;
import com.anyu.postservice.entity.vo.PostVo;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public Optional<Post> getPost(@NonNull Long id) {
        return postService.getPostById(id);
    }

    public PostVo postDetails(Long id) {
        Post post = postService.getById(id);
        return PostVo.build()
                .setPost(post);
    }

    /**
     * 帖子分页展示
     *
     * @param first     数量
     * @param after     起始ID
     * @param condition 查询条件
     * @return 分页对象
     * TODO post like number and status
     */
    public Connection<PostVo> posts(int first, String after, PostPageCondition condition) {
        List<Post> posts = postService.listPostAfter(first, CommonPage.decodeCursorWith(after), condition);
        return CommonPage.<PostVo>build()
                .newConnection(first, after, () -> posts.stream()
                        .map(post -> {
                            PostVo postVo = PostVo.build()
                                    .setPost(post)
                                    .setPostLikeNum(10)
                                    .setPostLikeStatus(false);
                            return new DefaultEdge<>(postVo, CommonPage.createCursorWith(post.getId()));
                        }).collect(Collectors.toUnmodifiableList()));
    }

    ;

}

