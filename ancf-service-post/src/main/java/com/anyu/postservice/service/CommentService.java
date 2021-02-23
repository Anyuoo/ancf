package com.anyu.postservice.service;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import com.anyu.common.util.GlobalConstant;
import com.anyu.postservice.entity.input.CommentInput;
import com.anyu.postservice.entity.vo.CommentVO;
import com.anyu.postservice.entity.vo.ReplyVO;
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

    List<Comment> listCommentsByEntity(int first, @Nullable Long id, @NotNull EntityType entityType, @NotNull Long entityId);

    List<CommentVO> listCommentVOsByPostId(int first, @Nullable Long id, @NotNull Long entityId);

    List<ReplyVO> listReplyVOsByTargetId(int first,@NotNull Long id,@NotNull Long targetId);

    boolean createComment(CommentInput input);
}