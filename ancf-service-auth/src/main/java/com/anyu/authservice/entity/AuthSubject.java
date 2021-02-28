package com.anyu.authservice.entity;

import com.anyu.authservice.entity.enums.Role;
import com.anyu.common.util.GlobalConstant;

public class AuthSubject{
    private Integer userId;
    private String account;
    private Role role;
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

    public Role getRole() {
        return role;
    }

    public AuthSubject setRole(Role role) {
        this.role = role;
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
