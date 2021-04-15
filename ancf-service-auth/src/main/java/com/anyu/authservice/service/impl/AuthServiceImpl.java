package com.anyu.authservice.service.impl;

import com.anyu.authservice.model.AuthSubject;
import com.anyu.authservice.model.enums.Role;
import com.anyu.authservice.jwt.JwtHelper;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.memory.ILocalMemory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthInfoMemory authInfoMemory = new AuthInfoMemory();
    @Resource
    private JwtHelper jwtHelper;

    @Override
    public Optional<AuthSubject> getAuthSubjectWith(HttpServletRequest httpServletRequest) {
        var token = jwtHelper.getTokenWith(httpServletRequest);
        if (token.isEmpty()) {
            return Optional.empty();
        }
        var userId = jwtHelper.getUserId(token.get());
        var username = jwtHelper.getUsername(token.get());
        var role = jwtHelper.getRole(token.get());

        if (userId.isEmpty() || username.isEmpty() || role.isEmpty()) {
            return Optional.empty();
        }
        AuthSubject authSubject = new AuthSubject();
        authSubject.setUserId(Integer.parseInt(userId.get()));
        authSubject.setNickname(username.get());
        authSubject.setRole(role.get());
        return Optional.of(authSubject);
    }

    @Override
    public Optional<AuthSubject> getAuthSubjectWith(HandshakeRequest handshakeRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<String> createJwt(String id, String nickname, Role role) {
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
        return getCurrentSubject() != null;
    }

    @Override
    public Role getCurrentUserRole() {
        if (getCurrentSubject() == null) {
            return Role.VISITOR;
        }
        return getCurrentSubject().getRole();
    }

    /**
     * 存储认证的信息的内存
     *
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
