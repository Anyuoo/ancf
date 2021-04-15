package com.anyu.postservice.service.impl;


import com.anyu.authservice.service.AuthService;
import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.EntityType;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.postservice.model.vo.CommentVO;
import com.anyu.postservice.model.vo.ReplyVO;
import com.anyu.postservice.mapper.CommentMapper;
import com.anyu.postservice.service.CommentService;
import com.anyu.postservice.service.LikeService;
import com.anyu.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Comment)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private SensitiveFilter sensitiveFilter;
    @Resource
    private UserService userService;
    @Resource
    private LikeService likeService;
    @Resource
    private AuthService authService;

    @Override
    public boolean createComment(Comment comment, int cmtUserId) {
        //將评论类容进行编码处理
        final var content = sensitiveFilter.filter(comment.getContent());
        comment.setUserId(cmtUserId)
                .setContent(content);
        return save(comment);
    }

    @Override
    public List<Comment> listCommentsByEntity(int first, Integer id, @NotNull EntityType entityType, @NotNull Integer entityId) {
        if (first < 1)
            first = PAGE_FIRST;
        return lambdaQuery().eq(Comment::getEntityType, entityType)
                .eq(Comment::getEntityId, entityId)
                .ge(id != null, Comment::getId, id)
                .orderByDesc(Comment::getCreateTime)
                .last(PAGE_SQL_LIMIT + first)
                .list();
    }

    @Override
    public List<CommentVO> listCommentVOsByPostId(int first, Integer id, @NotNull Integer entityId) {
        return listCommentsByEntity(first, id, EntityType.POST, entityId).stream()
                .map(this::convertCmtToCmtVo)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ReplyVO> listReplyVOsByTargetId(int first, @NotNull Integer id, @NotNull Integer targetId) {
        return listCommentsByEntity(first, id, EntityType.COMMENT, targetId)
                .stream()
                .map(this::convertCmtToReplyVO)
                .collect(Collectors.toUnmodifiableList());
    }


    private CommentVO convertCmtToCmtVo(@NotNull Comment comment) {

        var user = userService.getUserById(comment.getUserId()).orElse(new User());
        return CommentVO.getInstance()
                .setId(comment.getId())
                .setUserId(user.getId())
                .setNickname(user.getNickname())
                .setContent(comment.getContent())
                .setCmtLikeNum((int) likeService.countCommentLikeNum(comment.getId()))
                .setCmtLikeStatus(likeService.getCmtLikeStatus(authService.getCurrentUserId(), comment.getId()));
    }

    private ReplyVO convertCmtToReplyVO(@NotNull Comment comment) {
        var replier = userService.getUserById(comment.getUserId()).orElse(new User());
        return ReplyVO.getInstance()
                .setId(comment.getId())
                .setUserId(replier.getId())
                .setNickname(replier.getNickname())
                .setContent(comment.getContent())
                .setReplyLikeNum(likeService.countCommentLikeNum(comment.getId()))
                .setReplyLikeStatus(likeService.getCmtLikeStatus(authService.getCurrentUserId(), comment.getId()))
                .setTargetId(comment.getTargetId())
                .setTargetNickname(replier.getNickname());
    }
}