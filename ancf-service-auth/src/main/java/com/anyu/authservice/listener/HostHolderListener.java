package com.anyu.authservice.listener;

import com.anyu.authservice.service.AuthService;
import graphql.kickstart.servlet.core.GraphQLServletListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移除存储当前request 的用户信息
 *
 * @author Anyu
 * @since 2021/2/25 下午5:56
 */
@Component
public class HostHolderListener implements GraphQLServletListener {
    private final static Logger logger = LoggerFactory.getLogger(HostHolderListener.class);
    @Resource
    private AuthService authService;

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {

        return new RequestCallback() {
            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                authService.removeCurrentSubject();
                logger.debug("移除当前认证用户信息");
            }
        };
    }

}
