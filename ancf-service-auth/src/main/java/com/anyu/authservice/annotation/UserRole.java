package com.anyu.authservice.annotation;

import java.lang.annotation.*;

/**
*
* @author Anyu
* @since 2021/2/26 下午8:23
*/
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserRole {
}
