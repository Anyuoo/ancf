package com.anyu.postservice.resolver.query;


import com.anyu.common.model.CommonPage;
import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import com.anyu.postservice.entity.vo.CommentVo;
import com.anyu.postservice.service.CommentService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CmtQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private CommentService commentService;


    public CompletableFuture<Optional<Comment>> getComment(Long id) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(commentService.getById(id)));
    }

    /**
     * 获取帖子的评论
     *
     * @param postId 帖子id
     */
    public CompletableFuture<Connection<CommentVo>> getComments(int first, String after, Long postId) {
        return CompletableFuture.supplyAsync(() -> {
            List<Comment> comments =
                    commentService.listCommentsByEntity(first, CommonPage.decodeCursorWith(after), EntityType.POST, postId);
            return CommonPage.<CommentVo>build()
                    .newConnection(first, after, () -> comments.stream().map(comment -> {
                        var commentVo = CommentVo.build()
                                .setComment(comment)
                                .setCmtLikeNum(5)
                                .setCmtLikeStatus(false);
                        return new DefaultEdge<>(commentVo, CommonPage.createCursorWith(commentVo.getComment().getId()));
                    }).collect(Collectors.toUnmodifiableList()));
        });
    }


}
