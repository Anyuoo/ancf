package com.anyu.postservice.service;


import com.anyu.common.model.CommonResult;
import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import com.anyu.postservice.entity.input.CommentInput;
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
public interface CommentService extends IService<Comment> {

    List<Comment> listCommentsByEntity(int first, @Nullable Long id, @NotNull EntityType entityType, @NotNull Long entityId);

    CommonResult createComment(CommentInput input);
}