package com.anyu.postservice.resolver.query;


import com.anyu.common.model.entity.Post;
import com.anyu.common.result.CommonPage;
import com.anyu.common.result.annotation.QueryResolver;
import com.anyu.postservice.entity.condition.PostPageCondition;
import com.anyu.postservice.entity.vo.PostVO;
import com.anyu.postservice.service.PostService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 帖子信息查询
 *
 * @author Anyu
 * @since 2020/10/10
 */
@QueryResolver
public class PostQueryResolver implements GraphQLQueryResolver {

    @Resource
    private PostService postService;


    public Optional<Post> getPost(@NonNull Integer id) {
        return postService.getPostById(id);
    }

    public PostVO postDetails(Long id) {
        return postService.convertPostToVO(postService.getById(id));
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
    public Connection<PostVO> posts(int first, String after, PostPageCondition condition) {
        var posts = postService.listPostVOAfter(first, CommonPage.decodeCursorWith(after), condition);
        return CommonPage.<PostVO>build()
                .newConnection(first, after, () -> posts.stream()
                        .map(postVo -> CommonPage.getDefaultEdge(postVo, postVo.getId()))
                        .collect(Collectors.toUnmodifiableList()));
    }

    ;

}

