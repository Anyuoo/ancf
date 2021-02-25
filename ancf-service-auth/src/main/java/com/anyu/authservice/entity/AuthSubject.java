package com.anyu.authservice.entity;

public class AuthSubject {
    private Integer userId;
    private String account;
    private String nickname;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public AuthSubject setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public AuthSubject setAccount(String account) {
        this.account = account;
        return this;
    }
}
