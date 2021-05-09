package com.anyu.common.result.aspect;

import com.anyu.common.result.CommonResult;
import com.anyu.common.result.IResultType;
import com.anyu.common.result.type.ResultType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
//@Order
public class MutationResolverAdvise {
    private final static Logger logger = LoggerFactory.getLogger(MutationResolverAdvise.class);

//    @Pointcut("@within(com.anyu.common.result.annotation.MutationResolver)")
    public void required() {
    }

//
//    @AfterReturning(pointcut = "required()", returning = "result")
    public Object handle(JoinPoint joinPoint, Object result) {
        logger.debug("ResultAdvisor classname: {},methodName: {}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
        if (result instanceof IResultType) {
            return CommonResult.with((IResultType) result);
        }

        if (result instanceof CommonResult) {
            return result;
        }
        return CommonResult.with(ResultType.SYS_ERROR);
    }

}
