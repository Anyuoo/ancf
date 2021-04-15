package com.anyu.postservice.service;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import com.anyu.common.util.GlobalConstant;
import com.anyu.postservice.model.vo.CommentVO;
import com.anyu.postservice.model.vo.ReplyVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (Comment)表服务接口
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
public interface CommentService extends IService<Comment>, GlobalConstant {

    List<Comment> listCommentsByEntity(int first, @Nullable Integer id, @NotNull EntityType entityType, @NotNull Integer entityId);

    List<CommentVO> listCommentVOsByPostId(int first, @Nullable Integer id, @NotNull Integer entityId);

    List<ReplyVO> listReplyVOsByTargetId(int first, @NotNull Integer id, @NotNull Integer targetId);

    boolean createComment(Comment comment, int cmtUserId);
}