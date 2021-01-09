package com.anyu.authservice.entity;

public abstract class AuthSubject {
    private int id;
    private AuthUser authUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }
}
