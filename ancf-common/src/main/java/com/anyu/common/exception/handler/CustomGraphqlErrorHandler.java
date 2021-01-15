package com.anyu.common.exception.handler;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomGraphqlErrorHandler implements GraphQLErrorHandler {
    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        //just return error message
        ErrorClassification errorType = errors.get(0).getErrorType();
        return errors.stream()
                .map(e -> new GenericGraphQLError(e.getMessage().split(":")[1]))
                .collect(Collectors.toUnmodifiableList());
    }
}