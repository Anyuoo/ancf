package com.anyu.postservice.resolver;


import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.EntityType;
import com.anyu.common.result.CommonPage;
import com.anyu.postservice.entity.vo.CommentVo;
import com.anyu.postservice.entity.vo.ReplyVo;
import com.anyu.postservice.service.CommentService;
import com.anyu.postservice.service.LikeService;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CmtVoResolver implements GraphQLResolver<CommentVo> {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;


    public CompletableFuture<Optional<User>> getObserver(CommentVo commentVo) {
        return CompletableFuture.supplyAsync(() -> userService.getUserById(commentVo.getComment().getUserId()));
    }

    /**
     * 评论的回复
     *
     * @param commentVo 评论对象
     * @param first     分页
     * @param after     起始id
     * @return 评论的回复分页对象
     */
    public CompletableFuture<Connection<ReplyVo>> getReplies(CommentVo commentVo, int first, String after) {
        return CompletableFuture.supplyAsync(() -> {
            var comment = commentVo.getComment();
            //评论的回复
            var replies =
                    commentService.listCommentsByEntity(first, CommonPage.decodeCursorWith(after), EntityType.COMMENT, comment.getId());
            return CommonPage.<ReplyVo>build()
                    .newConnection(first, after, () -> replies.stream().map(reply -> {
                        var replier = userService.getUserById(reply.getUserId());
                        var replyLikeNum  = likeService.countCommentLikeNum(String.valueOf(reply.getId()));
                        var target = reply.getTargetId() == null ? Optional.<User>empty()
                                : userService.getUserById(reply.getTargetId());
                        var replyVo = ReplyVo.build()
                                .setReplier(replier.orElse(User.build()))
                                .setReply(reply)
                                .setReplyLikeNum(replyLikeNum)
                                .setReplyLikeStatus()
                                .setTarget(target.orElse(null));
                        return new DefaultEdge<>(replyVo, CommonPage.createCursorWith(replyVo.getReply().getId()));
                    }).collect(Collectors.toUnmodifiableList()));
        });
    }
}
