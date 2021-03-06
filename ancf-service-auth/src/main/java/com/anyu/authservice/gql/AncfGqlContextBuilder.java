package com.anyu.authservice.gql;


import com.anyu.authservice.entity.AuthSubject;
import com.anyu.authservice.service.AuthService;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;

/**
 * GraphQLServletContextBuilder 创建 整合身份认证
 *
 * @author Anyu
 * @since 2020/11/2
 */
@Component
public class AncfGqlContextBuilder implements GraphQLServletContextBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AncfGqlContextBuilder.class);

    private final AuthService authService;


    public AncfGqlContextBuilder(AuthService authService) {
        this.authService = authService;
        logger.info("AncfGqlContextBuilder 初始化完成");
    }

    /**
     * 得到当前请求用户的subject
     *
     * @return subject
     */
    private Optional<AuthSubject> getAuthSubject(HttpServletRequest httpServletRequest) {
        return authService.getAuthSubjectWith(httpServletRequest);
    }

    /**
     * 创建自定义 GraphQLContext
     */
    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //不存在就为null
        final var authSubject = getAuthSubject(httpServletRequest).orElse(null);
        if (authSubject != null) {
            authService.saveCurrentSubject(authSubject);
            logger.debug("保存当前用户信息 ，userId:{},username{}", authSubject.getUserId(), authSubject.getNickname());
        }
        return AncfGqlHttpContext.builder()
                .setHttpServletRequest(httpServletRequest)
                .setHttpServletResponse(httpServletResponse)
                .setAncfSubject(authSubject)
                .setDataLoaderRegistry(new DataLoaderRegistry())
                .build();
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        //不存在就为null
        final var authSubject = getAuthSubject(handshakeRequest).orElse(null);
        if (authSubject != null) {
            authService.saveCurrentSubject(authSubject);
            logger.debug("保存当前用户信息 ，userId:{},username{}", authSubject.getUserId(), authSubject.getNickname());
        }
        return AncfGqlSocketContext.builder()
                .setSession(session)
                .setHandshakeRequest(handshakeRequest)
                .setAncfSubject(authSubject)
                .setDataLoaderRegistry(new DataLoaderRegistry())
                .build();
    }


    private Optional<AuthSubject> getAuthSubject(HandshakeRequest handshakeRequest) {
        return authService.getAuthSubjectWith(handshakeRequest);
    }

    @Override
    public GraphQLContext build() {
        return AncfGqlHttpContext.builder()
                .setDataLoaderRegistry(new DataLoaderRegistry())
                .build();
    }

}
