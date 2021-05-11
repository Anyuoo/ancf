package com.anyu.ancf.aspect;

import com.anyu.ancf.model.annotation.FileUploadLogger;
import com.anyu.ancf.service.UploadFileService;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.model.entity.UploadFile;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
/**
*文件上传记录切面
* @author Anyu
* @since 2021/5/11
*/
@Aspect
@Component
public class FileUploadAspect {

    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private AuthService authService;

    @Pointcut("@annotation(com.anyu.ancf.model.annotation.FileUploadLogger)")
    public void savePointcut() {
    }

    @AfterReturning(pointcut = "savePointcut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
       //获取对象,存储数据库
        getUploadFile(joinPoint, jsonResult).ifPresent(uf-> uploadFileService.save(uf));
    }

    /**
     * 根据切面生成 UploadFile对象
     */
    public Optional<UploadFile> getUploadFile(JoinPoint joinPoint, Object jsonResult) {
        final var uploadLogger = getAnnotationLog(joinPoint);
        @SuppressWarnings("unchecked")
        Optional<String> result = (Optional<String>) jsonResult;
        if (uploadLogger == null || result.isEmpty())
            return Optional.empty();
        //文件上传信息
        final UploadFile uploadFile = new UploadFile()
                .setUserId(authService.getValidCUId())
                .setUrl(result.get())
                .setType(uploadLogger.type());

        return Optional.of(uploadFile);
    }
    /**
     * 是否存在注解，如果存在就获取
     */
    private FileUploadLogger getAnnotationLog(JoinPoint joinPoint) {
        final var methodSignature = (MethodSignature) joinPoint.getSignature();
        final var method = methodSignature.getMethod();
        return method != null
                ? method.getAnnotation(FileUploadLogger.class)
                : null;
    }
}
