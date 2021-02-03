package com.anyu.common.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class AnCFGraphQLError implements GraphQLError {

    private final int code;
    private final String message;
    private final String handlePath;

    public AnCFGraphQLError(int code, String message, String handlePath) {
        this.code = code;
        this.message = message;
        this.handlePath = handlePath;
    }

    public String getHandlePath() {
        return handlePath;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
