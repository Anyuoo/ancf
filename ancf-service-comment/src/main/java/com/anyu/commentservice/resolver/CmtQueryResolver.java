package com.anyu.commentservice.resolver;


import com.anyu.commentservice.model.CommentVO;
import com.anyu.commentservice.model.ReplyVO;
import com.anyu.commentservice.service.CommentService;
import com.anyu.common.model.entity.Comment;
import com.anyu.common.result.CommonPage;
import com.anyu.common.result.annotation.QueryResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.stream.Collectors;

@QueryResolver
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
    public Connection<CommentVO> getComments(int first, String after, Long postId) {
        final var comments = commentService.listCommentVOsByPostId(first, CommonPage.decodeCursorWith(after), postId);
        return CommonPage.<CommentVO>build()
                .newConnection(first, after, () -> comments.stream()
                        .map(commentVO -> CommonPage.getDefaultEdge(commentVO, commentVO.getId()))
                        .collect(Collectors.toUnmodifiableList()));
    }

    public Connection<ReplyVO> getReplies(int first, String after, long targetId) {
        final var replies = commentService.listReplyVOsByTargetId(first, CommonPage.decodeCursorWith(after), targetId);
        return CommonPage.<ReplyVO>build()
                .newConnection(first, after, () -> replies.stream()
                        .map(replyVO -> CommonPage.getDefaultEdge(replyVO, replyVO.getId()))
                        .collect(Collectors.toUnmodifiableList()));
    }

}
