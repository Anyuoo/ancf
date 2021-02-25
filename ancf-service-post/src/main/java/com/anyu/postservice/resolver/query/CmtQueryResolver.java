package com.anyu.postservice.resolver.query;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.result.CommonPage;
import com.anyu.postservice.entity.vo.CommentVO;
import com.anyu.postservice.entity.vo.ReplyVO;
import com.anyu.postservice.service.CommentService;
import graphql.GraphQLContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CmtQueryResolver implements GraphQLQueryResolver {
    @Resource
    private CommentService commentService;

    public Optional<Comment> getComment(Long id) {
        return Optional.ofNullable(commentService.getById(id));
    }

    /**
     * 获取帖子的评论
     *
     * @param postId 帖子id
     */
    public Connection<CommentVO> getComments(int first, String after, Integer postId) {
        final var comments = commentService.listCommentVOsByPostId(first, CommonPage.decodeCursorWith(after), postId);
        return CommonPage.<CommentVO>build()
                .newConnection(first, after, () -> comments.stream()
                        .map(commentVO -> CommonPage.getDefaultEdge(commentVO,commentVO.getId()))
                        .collect(Collectors.toUnmodifiableList()));
    }

    public Connection<ReplyVO> getReplies(int first, String after, int targetId) {
        final var replies = commentService.listReplyVOsByTargetId(first, CommonPage.decodeCursorWith(after), targetId);
        return CommonPage.<ReplyVO>build()
                .newConnection(first, after, () -> replies.stream()
                        .map(replyVO -> CommonPage.getDefaultEdge(replyVO,replyVO.getId()))
                        .collect(Collectors.toUnmodifiableList()));
    }

}
