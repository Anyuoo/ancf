package com.anyu.userservice.mapper;


import com.anyu.common.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (U)表数据库访问层
 *
 * @author Anyu
 * @since 2020-10-07 09:56:08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}