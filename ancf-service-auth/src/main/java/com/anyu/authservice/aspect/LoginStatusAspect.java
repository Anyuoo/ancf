package com.anyu.authservice.aspect;

import com.anyu.common.util.GlobalContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
//@Component
public class LoginStatusAspect {
    @Resource
    private GlobalContext globalContext;

    @Pointcut("execution(* com.anyu.*.*(..))")
    public void needCheckStatusPointcut() {
    }

    @Around("needCheckStatusPointcut()")
    public Object checkLoginStatus(ProceedingJoinPoint joinPoint) throws Throwable {
//        globalContext.saveCurrentUser();
        joinPoint.proceed();
//        globalContext.removeCurrentUser();
        return null;
    }
}
