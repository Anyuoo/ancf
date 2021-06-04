package com.anyu.videoservice.resolver;

import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.videoservice.model.VideoInput;
import com.anyu.videoservice.service.VideoService;
import graphql.kickstart.tools.GraphQLMutationResolver;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@MutationResolver
public class VideoMutationResolver implements GraphQLMutationResolver {
    @Resource
    private VideoService videoService;
    @Resource
    private AuthService authService;

    public Boolean publishVideo(@NotNull VideoInput input){
        return videoService.publishVideo(authService.getValidCUId(),input.toEntity());
    }




}
