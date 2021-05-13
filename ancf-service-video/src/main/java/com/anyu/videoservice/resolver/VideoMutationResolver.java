package com.anyu.videoservice.resolver;

import com.anyu.ancf.service.OssService;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.videoservice.model.VideoInput;
import graphql.kickstart.tools.GraphQLMutationResolver;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@MutationResolver
public class VideoMutationResolver implements GraphQLMutationResolver {
    @Resource
    private OssService ossService;


}
