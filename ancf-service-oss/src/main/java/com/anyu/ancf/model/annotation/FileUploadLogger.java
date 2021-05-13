package com.anyu.ancf.model.annotation;

import com.anyu.ancf.model.FileFolder;
import com.anyu.common.model.enums.FileType;

import java.lang.annotation.*;
/**
*文件上传日志
* @author Anyu
* @since 2021/5/11
*/
@Target({ElementType.METHOD})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FileUploadLogger {
    /**
     * 文件类型
     */
    FileType type();
    /**
     * 是否多个资源
     */
    boolean more() default false;



}
