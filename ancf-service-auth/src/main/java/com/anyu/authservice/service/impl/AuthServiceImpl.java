package com.anyu.authservice.service.impl;

import com.anyu.authservice.model.AuthSubject;
import com.anyu.authservice.model.enums.Role;
import com.anyu.authservice.jwt.JwtHelper;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.memory.ILocalMemory;
import com.anyu.common.result.type.ResultType;
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
        AuthSubject authSubject = new AuthSubject();
        jwtHelper.getUserId(token.get()).ifPresent(id-> authSubject.setUserId(Integer.parseInt(id)));
        jwtHelper.getAccount(token.get()).ifPresent(authSubject::setAccount);
        jwtHelper.getRole(token.get()).ifPresent(authSubject::setRole);
        return Optional.of(authSubject);
    }

    @Override
    public Optional<AuthSubject> getAuthSubjectWith(HandshakeRequest handshakeRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<String> createJwt(String id, String account, Role role) {
        return jwtHelper.createJwt(id, account, role);
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
        var subject = authInfoMemory.getCurrentAuthSubject();
        if (subject == null) {
            return -1;
        }
        return subject.getUserId();
    }

    @Override
    public Optional<String> getLoginUserAccount() {
        var authSubject = authInfoMemory.getCurrentAuthSubject();
        return Optional.ofNullable(authSubject.getAccount());
    }

    @Override
    public boolean hasCurrentUserPermission() {
        return getCurrentSubject() != null;
    }

    @Override
    public Role getCurrentUserRole() {
        if (getCurrentSubject() == null || getCurrentUserId() == -1) {
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

    }
}
