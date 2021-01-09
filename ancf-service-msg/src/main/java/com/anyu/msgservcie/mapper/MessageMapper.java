package com.anyu.msgservcie.mapper;


import com.anyu.common.model.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Message)表数据库访问层
 *
 * @author Anyu
 * @since 2020-10-10 12:51:56
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}