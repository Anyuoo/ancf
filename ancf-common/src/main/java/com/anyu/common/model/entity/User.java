package com.anyu.common.model.entity;

import com.anyu.common.model.enums.ActiveStatus;
import com.anyu.common.model.enums.Gender;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author Anyu
 * @since 2020-10-07 09:56:07
 */
@TableName(value = "user")
public class User implements Serializable {
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
    /*
     *年龄
     */
    private Integer age;
    /*
     *生日
     */
    private LocalDateTime birthday;
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
     * 0-未激活，1-已激活
     */
    private ActiveStatus activation;
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

    public User() {
    }

    public static User build() {
        return new User();
    }


    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public User setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public User setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public ActiveStatus getActivation() {
        return activation;
    }

    public void setActivation(ActiveStatus activation) {
        this.activation = activation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}