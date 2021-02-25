package com.anyu.authservice.service.impl;

import com.anyu.authservice.entity.AuthSubject;
import com.anyu.authservice.jwt.JwtHelper;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.memory.ILocalMemory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private JwtHelper jwtHelper;

    private final AuthInfoMemory authInfoMemory = new AuthInfoMemory();
    

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
        authSubject.setNickname(username);
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

    @Override
    public void saveCurrentSubject(AuthSubject subject) {
        authInfoMemory.saveCurrentAuthSubject(subject);
    }

    @Override
    public AuthSubject getCurrentSubject() {
        return authInfoMemory.getCurrentAuthSubject();
    }

    @Override
    public void removeCurrentSubject() {
        authInfoMemory.removeCurrentAuthSubject();
    }

    @Override
    public int getCurrentUserId() {
        return authInfoMemory.saveCurrentUserId();
    }

    @Override
    public boolean hasCurrentUserPermission() {
        return getCurrentSubject() != null ;
    }



    /**
    *存储认证的信息的内存
    * @author Anyu
    * @since 2021/2/25 下午9:22
    */
    static class AuthInfoMemory implements ILocalMemory {
        private static final ThreadLocal<AuthSubject> currentUser = new ThreadLocal<>();

        public void saveCurrentAuthSubject(AuthSubject subject) {
            currentUser.set(subject);
        }

        public AuthSubject getCurrentAuthSubject() {
            return currentUser.get();
        }

        public void removeCurrentAuthSubject() {
            currentUser.remove();
        }

        public int saveCurrentUserId() {
            return getCurrentAuthSubject() == null ? -1 : getCurrentAuthSubject().getUserId();
        }
    }
}
