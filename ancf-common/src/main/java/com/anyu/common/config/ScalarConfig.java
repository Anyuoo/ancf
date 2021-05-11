package com.anyu.common.config;

import com.anyu.common.config.scalar.DateTimeScalar;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarConfig {
    @Bean
    public GraphQLScalarType dateTimeScalarType() {
        return new DateTimeScalar();
    }

    @Bean
    public GraphQLScalarType objectScalarType() {
        return ExtendedScalars.Object;
    }

    @Bean
    public GraphQLScalarType uploadScalarType() {
        return ApolloScalars.Upload;
    }

    @Bean
    public GraphQLScalarType dateScalarType() {
        return ExtendedScalars.Date;
    }

}
