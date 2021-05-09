package com.anyu.common.model.entity;

import com.anyu.common.model.enums.ActiveStatus;
import com.anyu.common.model.enums.Gender;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author Anyu
 * @since 2020-10-07 09:56:07
 */
@TableName(value = "user")
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = 294426455105480535L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账户
     */
    private String account;

    /**
     * 用户性别
     */
    private Gender gender;
    /**
     *年龄
     */
    private Integer age;
    /**
     *生日
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

    /**
     * 0-正常，1-已删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * 用户信息创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 用户信息修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedTime;

}