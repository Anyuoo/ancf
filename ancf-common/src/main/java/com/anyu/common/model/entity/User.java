package com.anyu.common.model.entity;

import com.anyu.common.model.BaseEntity;
import com.anyu.common.model.enums.Gender;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author Anyu
 * @since 2020-10-07 09:56:07
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
@Data
@Accessors(chain = true)
public class User extends BaseEntity {
    private static final long serialVersionUID = 294426455105480535L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户账户
     */
    private String account;

    /**
     * 用户性别
     */
    private Gender gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户真实名
     */
    private String realName;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;


}