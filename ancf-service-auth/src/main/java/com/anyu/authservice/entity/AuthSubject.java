package com.anyu.authservice.entity;

public class AuthSubject {
    private Integer userId;

    private String username;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public AuthSubject setUsername(String username) {
        this.username = username;
        return this;
    }
}
