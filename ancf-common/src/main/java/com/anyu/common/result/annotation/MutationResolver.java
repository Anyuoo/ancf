package com.anyu.common.result.annotation;

import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
/**
*
 * 标识为实现graphql {@link graphql.kickstart.tools.GraphQLMutationResolver} 接口的类，并将其标识为Component 可注入IOC
* @author Anyu
* @since 2021/2/27 下午2:05
*/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MutationResolver {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
