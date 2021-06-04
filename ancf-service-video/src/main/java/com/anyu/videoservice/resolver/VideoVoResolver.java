package com.anyu.videoservice.resolver;

import com.anyu.common.model.entity.User;
import com.anyu.common.result.annotation.QueryResolver;
import com.anyu.userservice.service.UserService;
import com.anyu.videoservice.model.VideoVo;
import graphql.kickstart.tools.GraphQLResolver;

import javax.annotation.Resource;
import java.util.Optional;

@QueryResolver
public class VideoVoResolver implements GraphQLResolver<VideoVo> {
    @Resource
    private UserService userService;

    public Optional<User> getPublisher(VideoVo videoVo) {
        return userService.getUserById(videoVo.getUserId());
    }
}
