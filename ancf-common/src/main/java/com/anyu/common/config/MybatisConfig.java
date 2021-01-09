package com.anyu.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus configuration
 *
 * @author Anyu
 * @since 2020/10/30
 */
@Configuration
@MapperScan("com.anyu.**.mapper")
public class MybatisConfig {
}
