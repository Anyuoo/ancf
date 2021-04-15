package com.anyu.authservice.aspect;

import com.anyu.authservice.annotation.AdminRole;
import com.anyu.authservice.annotation.UserRole;
import com.anyu.authservice.model.enums.Role;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.type.AuthResultType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Anyu
 * @since 2021/2/26 下午4:07
 */
@Aspect
@Component
@Order(-1)
public class RoleValidInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(RoleValidInterceptor.class);

    @Resource
    private AuthService authService;

    @Pointcut("@annotation(com.anyu.authservice.annotation.AdminRole) || @annotation(com.anyu.authservice.annotation.UserRole)")
    public void validRole() {
    }

    @Around("validRole()")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("Enter RoleValidInterceptor");
        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();
        if (method == null) {
            return CommonResult.with(AuthResultType.NOT_PERMISSION);
        }
        //get current user role
        var currentUserRole = authService.getCurrentUserRole();
        var userRole = method.getAnnotation(UserRole.class);
        if (userRole != null) {
            //current user doesn't have user role or admin role, the target method isn't allowed to process
            if (Role.USER != currentUserRole && Role.ADMIN != currentUserRole) {
                return CommonResult.with(AuthResultType.NOT_PERMISSION);
            }
        }
        var adminRole = method.getAnnotation(AdminRole.class);
        if (adminRole != null) {
            //current user doesn't have  admin role, the target method isn't allowed to process
            if (Role.ADMIN != currentUserRole) {
                return CommonResult.with(AuthResultType.NOT_PERMISSION);
            }
        }
        //权限正确执行resolver 方法
        return joinPoint.proceed();
    }
}
