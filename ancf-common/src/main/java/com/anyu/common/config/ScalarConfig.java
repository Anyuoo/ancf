package com.anyu.common.config;

import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConfig {
    @Bean("dateTimeScalarType")
    public GraphQLScalarType getDateTimeScalarType() {
        return ExtendedScalars.DateTime;
    }

    @Bean("objectScalarType")
    public GraphQLScalarType getObjectScalarType() {
        return ExtendedScalars.Object;
    }

    @Bean
    GraphQLScalarType uploadScalarType() {
        return ApolloScalars.Upload;
    }

}
