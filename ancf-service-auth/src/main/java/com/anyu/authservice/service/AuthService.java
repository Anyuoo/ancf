package com.anyu.authservice.service;

import com.anyu.authservice.model.AuthSubject;
import com.anyu.authservice.model.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

public interface AuthService {
    Optional<AuthSubject> getAuthSubjectWith(HttpServletRequest httpServletRequest);

    Optional<AuthSubject> getAuthSubjectWith(HandshakeRequest handshakeRequest);

    Optional<String> createJwt(String id, String nickname, Role role);

    void saveCurrentSubject(AuthSubject subject);

    /**
     * 得到当前用户主体信息
     */
    AuthSubject getCurrentSubject();

    /**
     * 移除当前用户主体信息
     */
    void removeCurrentSubject();

    /**
     * 得到当前用户id
     */
    int getCurrentUserId();

    /**
     * 是否具有当前用户权限
     */
    boolean hasCurrentUserPermission();


    Role getCurrentUserRole();
}
