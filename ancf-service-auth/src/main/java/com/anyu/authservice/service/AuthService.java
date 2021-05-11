package com.anyu.authservice.service;

import com.anyu.authservice.jwt.JwtHelper;
import com.anyu.authservice.model.AuthSubject;
import com.anyu.authservice.model.enums.Role;
import com.anyu.common.exception.GlobalException;
import com.anyu.common.memory.ILocalMemory;
import com.anyu.common.result.type.ResultType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;
import java.util.Objects;
import java.util.Optional;

/**
 * 认证安全服务
 *
 * @author Anyu
 * @since 2021/5/9
 */
@Service
public class AuthService {
    private final AuthInfoMemory authInfoMemory = new AuthInfoMemory();
    @Resource
    private JwtHelper jwtHelper;


    /**
     * 解析请求获取当前认证主体
     *
     * @param httpServletRequest http请求
     */
    public Optional<AuthSubject> getAuthSubjectWith(HttpServletRequest httpServletRequest) {
        var token = jwtHelper.getTokenWith(httpServletRequest);
        if (token.isEmpty()) {
            return Optional.empty();
        }
        AuthSubject authSubject = new AuthSubject();
        jwtHelper.getUserId(token.get()).ifPresent(id -> authSubject.setUserId(Long.parseLong(id)));
        jwtHelper.getAccount(token.get()).ifPresent(authSubject::setAccount);
        jwtHelper.getRole(token.get()).ifPresent(authSubject::setRole);
        return Optional.of(authSubject);
    }

    /**
     * 解析请求获取当前认证主体
     *
     * @param handshakeRequest handshake请求
     */
    public Optional<AuthSubject> getAuthSubjectWith(HandshakeRequest handshakeRequest) {
        return Optional.empty();
    }

    /**
     * 创建jwt
     *
     * @param id      用户id
     * @param account 账号
     * @param role    角色
     * @return token
     */
    public Optional<String> createJwt(String id, String account, Role role) {
        return jwtHelper.createJwt(id, account, role);
    }

    /**
     * 存储当前认证信息
     *
     * @param subject 认证信息
     */
    public void saveCurrentSubject(AuthSubject subject) {
        authInfoMemory.saveCurrentAuthSubject(subject);
    }

    /**
     * 获取当前认证主体
     */
    public AuthSubject getCurrentSubject() {
        return authInfoMemory.getCurrentAuthSubject();
    }

    /**
     * 移除当前认证主体
     */
    public void removeCurrentSubject() {
        authInfoMemory.removeCurrentAuthSubject();
    }

    /**
     * 获取当前用户id
     */
    public Optional<Long> getCUId() {
        var subject = authInfoMemory.getCurrentAuthSubject();
        return Objects.isNull(subject) ? Optional.empty() : Optional.ofNullable(subject.getUserId());
    }

    /**
     * 获取当前用户,经过验证
     *
     * @return 当前用户id
     * @throws GlobalException 未授权
     */
    public long getValidCUId() {
        return getCUId().orElseThrow(() -> GlobalException.causeBy(ResultType.FORBIDDEN));
    }

    public boolean hasCurrentUserPermission() {
        return getCurrentSubject() != null;
    }


    public Role getCurrentUserRole() {
        if (getCurrentSubject() == null || getCUId().isEmpty()) {
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
