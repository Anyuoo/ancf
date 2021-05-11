package com.anyu.commentservice.mapper;


import com.anyu.common.model.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Comment)表数据库访问层
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}