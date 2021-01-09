package com.anyu.authservice.service.impl;

import com.anyu.authservice.entity.AuthSubject;
import com.anyu.authservice.jwt.JwtHelper;
import com.anyu.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Optional<AuthSubject> getAuthSubjectWith(HttpServletRequest httpServletRequest) {
        Optional<String> token = jwtHelper.getTokenWith(httpServletRequest);
        if (token.isPresent()) {

        }
        return Optional.empty();
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
