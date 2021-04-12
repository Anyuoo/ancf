package com.anyu.postservice.resolver;

import com.anyu.postservice.entity.vo.PostVO;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLResolver;

import javax.annotation.Resource;

//@Component
public class PostVoResolver implements GraphQLResolver<PostVO> {
    @Resource
    private UserService userService;


}
