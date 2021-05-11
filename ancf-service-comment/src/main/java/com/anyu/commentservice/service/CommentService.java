package com.anyu.commentservice.service;


import com.anyu.authservice.service.AuthService;
import com.anyu.commentservice.mapper.CommentMapper;
import com.anyu.commentservice.model.CommentVO;
import com.anyu.commentservice.model.ReplyVO;
import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.EntityType;
import com.anyu.common.util.GlobalConstant;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.likeservice.service.LikeService;
import com.anyu.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Comment)表服务接口
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> implements IService<Comment> {


    @Resource
    private SensitiveFilter sensitiveFilter;
    @Resource
    private UserService userService;
    @Resource
    private LikeService likeService;
    @Resource
    private AuthService authService;


    public boolean createComment(Comment comment, long cmtUserId) {
        //將评论类容进行编码处理
        final var content = sensitiveFilter.filter(comment.getContent());
        comment.setUserId(cmtUserId)
                .setContent(content);
        return save(comment);
    }


    public List<Comment> listCommentsByEntity(int first, Long id, @NotNull EntityType entityType, @NotNull Long entityId) {
        if (first < 1)
            first = GlobalConstant.PAGE_FIRST;
        return lambdaQuery().eq(Comment::getEntityType, entityType)
                .eq(Comment::getEntityId, entityId)
                .ge(id != null, Comment::getId, id)
                .orderByDesc(Comment::getCreateTime)
                .last(GlobalConstant.PAGE_SQL_LIMIT + first)
                .list();
    }


    public List<CommentVO> listCommentVOsByPostId(int first, Long id, @NotNull Long entityId) {
        return listCommentsByEntity(first, id, EntityType.POST, entityId).stream()
                .map(this::convertCmtToCmtVo)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<ReplyVO> listReplyVOsByTargetId(int first, @NotNull Long id, @NotNull Long targetId) {
        return listCommentsByEntity(first, id, EntityType.COMMENT, targetId)
                .stream()
                .map(this::convertCmtToReplyVO)
                .collect(Collectors.toUnmodifiableList());
    }


    private CommentVO convertCmtToCmtVo(@NotNull Comment comment) {

        var user = userService.getUserById(comment.getUserId()).orElse(new User());
        return new CommentVO()
                .setId(comment.getId())
                .setUserId(user.getId())
                .setNickname(user.getNickname())
                .setContent(comment.getContent())
                .setCmtLikeNum(likeService.countCommentLikeNum(comment.getId()))
                .setCmtLikeStatus(likeService.getCmtLikeStatus(authService.getValidCUId(), comment.getId()));
    }

    private ReplyVO convertCmtToReplyVO(@NotNull Comment comment) {
        var replier = userService.getUserById(comment.getUserId()).orElse(new User());
        return new ReplyVO()
                .setId(comment.getId())
                .setUserId(replier.getId())
                .setNickname(replier.getNickname())
                .setContent(comment.getContent())
                .setReplyLikeNum(likeService.countCommentLikeNum(comment.getId()))
                .setReplyLikeStatus(likeService.getCmtLikeStatus(authService.getValidCUId(), comment.getId()))
                .setTargetId(comment.getTargetId())
                .setTargetNickname(replier.getNickname());
    }
}