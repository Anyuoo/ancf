package com.anyu.ancf.aspect;

import com.anyu.ancf.model.FileFolder;
import com.anyu.ancf.model.OssFile;
import com.anyu.ancf.model.annotation.FileUploadLogger;
import com.anyu.ancf.service.UploadFileService;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.model.entity.UploadFile;
import com.anyu.common.model.enums.FileType;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        handlePointResult(joinPoint, jsonResult);
    }

    /**
     * 根据切面生成 UploadFile对象
     */
    public void handlePointResult(JoinPoint joinPoint, Object jsonResult) {
        final var uploadLogger = getAnnotationLog(joinPoint);
        if (uploadLogger == null)
            return;
//        文件类型
        var fileType = uploadLogger.type();
//        当前用户id
        long cUId = authService.getValidCUId();
        //上传多个资源
        if (uploadLogger.more()) {
            @SuppressWarnings("unchecked")
            var ossFiles = (List<OssFile>) jsonResult;
            if (ossFiles== null || ossFiles.isEmpty())
                return;
            //文件上传信息
            var uploadFiles = ossFiles.stream()
                    .filter(ossFile -> StringUtils.isNotBlank(ossFile.getUrl()))
                    .map(ossFile -> buildUploadFile(cUId,fileType,ossFile))
                    .collect(Collectors.toUnmodifiableList());
            uploadFileService.saveBatch(uploadFiles);
        }else {
//            单个文件资源
            var ossFile = (OssFile) jsonResult;
            if (StringUtils.isNotBlank(ossFile.getUrl())) {
                uploadFileService.save(buildUploadFile(cUId, fileType, ossFile));
            }
        }
    }

    /**
     * 保存上传文件记录
     * @param ossFile 上传文件信息
     * @param cUId 上传用户id
     * @param fileType 文件类型
     */
    private UploadFile buildUploadFile(long cUId,FileType fileType,OssFile ossFile) {
        //文件上传信息
        return new UploadFile()
                .setUserId(cUId)
                .setName(ossFile.getName())
                .setFolder(ossFile.getFileFolder().getPath())
                .setUrl(ossFile.getUrl())
                .setType(fileType);
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
