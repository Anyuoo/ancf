package com.anyu.authservice.service;

import com.anyu.authservice.entity.AuthSubject;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

public interface AuthService {
    Optional<AuthSubject> getAuthSubjectWith(HttpServletRequest httpServletRequest);

    Optional<AuthSubject> getAuthSubjectWith(HandshakeRequest handshakeRequest);

    Optional<String> createJwt(String id, String nickname, String role);

    void saveCurrentSubject(AuthSubject subject);

    AuthSubject getCurrentSubject();

    void removeCurrentSubject();

    int getCurrentUserId();

    boolean hasCurrentUserPermission();


}
