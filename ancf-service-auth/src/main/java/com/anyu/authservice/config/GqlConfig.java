package com.anyu.authservice.config;

import com.anyu.authservice.gql.AncfGqlContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GqlConfig {
    private static final Logger logger = LoggerFactory.getLogger(AncfGqlContextBuilder.class);


    @Bean("ancfGqlContextBuilder")
    public AncfGqlContextBuilder initAncfGqlContextBuilder() {
        AncfGqlContextBuilder gqlContextBuilder = new AncfGqlContextBuilder();
        logger.info("AncfGqlContextBuilder 初始化完成");
        return gqlContextBuilder;
    }

}
