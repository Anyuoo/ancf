package com.anyu.authservice.service.impl;

import com.anyu.authservice.entity.AuthSubject;
import com.anyu.authservice.jwt.JwtHelper;
import com.anyu.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private JwtHelper jwtHelper;
    

    @Override
    public Optional<AuthSubject> getAuthSubjectWith(HttpServletRequest httpServletRequest) {
        var token = jwtHelper.getTokenWith(httpServletRequest).orElse(null);
        if (token == null) {
            return Optional.empty();
        }
        var userId = jwtHelper.getUserId(token).orElse(null);
        if (userId == null) {
            return Optional.empty();
        }
        var username = jwtHelper.getUsername(token).orElse(null);
        if (username == null) {
            return Optional.empty();
        }
        AuthSubject authSubject = new AuthSubject();
        authSubject.setUserId(Integer.parseInt(userId));
        authSubject.setUsername(username);
        return  Optional.of(authSubject);
    }

    @Override
    public Optional<AuthSubject> getAuthSubjectWith(HandshakeRequest handshakeRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<String> createJwt(String id, String nickname, String role) {
        return jwtHelper.createJwt(id, nickname, role);
    }
}
