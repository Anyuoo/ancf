package com.anyu.postservice.service.impl;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import com.anyu.postservice.entity.input.CommentInput;
import com.anyu.postservice.mapper.CommentMapper;
import com.anyu.postservice.service.CommentService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public boolean createComment(CommentInput input) {
        //將评论类容进行编码处理
        input.setContent(HtmlUtils.htmlEscape(input.getContent()));
        Comment comment = Comment.build();
        BeanUtils.copyProperties(input, comment);
        return this.save(comment);
    }

    @Override
    public List<Comment> listCommentsByEntity(int first, Long id, @NotNull EntityType entityType, @NotNull Long entityId) {
        LambdaQueryChainWrapper<Comment> chainWrapper = this.lambdaQuery().eq(Comment::getEntityType, entityType)
                .eq(Comment::getEntityId, entityId);
        if (id != null) {
            chainWrapper.ge(Comment::getId, id);
        }
        return chainWrapper.orderByDesc(Comment::getCreateTime)
                .last("limit " + first)
                .list();
    }

}