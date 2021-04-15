package com.anyu.userservice.model.input;


import com.anyu.common.model.enums.Gender;

import java.time.LocalDateTime;

public class UserInput {

    private String account;
    /**
     * 用户昵称
     */
    private String nickname;
    /*
     *年龄
     */
    private Integer age;
    /*
     *生日
     */
    private LocalDateTime birthday;
    /**
     * 用户性别
     */
    private Gender gender;
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

    public UserInput() {
    }

    public static UserInput build() {
        return new UserInput();
    }

    public String getAccount() {
        return account;
    }

    public UserInput setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInput setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserInput setAge(Integer age) {
        this.age = age;
        return this;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public UserInput setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public UserInput setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public UserInput setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public UserInput setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getEmail() {
        return email;

    }

    public UserInput setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public UserInput setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInput setPassword(String password) {
        this.password = password;
        return this;
    }
}
