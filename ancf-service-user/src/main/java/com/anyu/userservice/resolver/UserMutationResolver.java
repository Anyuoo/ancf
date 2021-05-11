package com.anyu.userservice.resolver;


import com.anyu.ancf.service.OssService;
import com.anyu.authservice.annotation.UserRole;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.util.CommonUtils;
import com.anyu.userservice.model.input.UserInput;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@MutationResolver
public class UserMutationResolver implements GraphQLMutationResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserMutationResolver.class);

    @Resource
    private UserService userService;

    @Resource
    private OssService ossService;

    @Resource
    private AuthService authService;

    /**
     * 用户注册接口
     */
    public Boolean register(@NotBlank String email, @NotBlank String password, @NotBlank String code) {
        return userService.register(email, password, code);
    }

    /**
     * 发送注册验证码
     *
     * @param email 发送验证码
     */
    public Boolean sendVerifyCode(@NotBlank String email) {
        return userService.sendVerifyCodeByEmail(email);
    }

    public Boolean sendEmailUpdateCode(@NotBlank String email) {
        return userService.sendEmailUpdateCode(authService.getValidCUId(), email);
    }


    public Optional<String> login(@NotBlank String principal, @NotBlank String password) {
        return userService.login(principal, password);
    }

    //
    @UserRole
    public Boolean updateUserInfo(@NotNull UserInput input) {
        return userService.updateUserInfoById(authService.getValidCUId(), input);
    }

    public Boolean updatePassword(@NotBlank String password) {
        return userService.updatePassword(authService.getValidCUId(), password);
    }

    public Boolean updateEmail(@NotBlank String email, @NotBlank String code) {
        return userService.updateEmail(authService.getValidCUId(), email, code);
    }

    public Optional<String> uploadAvatar(@NotNull Part avatar) {
        var avatarUrl = ossService.uploadAvatar(avatar);
        return avatarUrl.isPresent() && userService.updateAvatar(authService.getValidCUId(), avatarUrl.get())
                ? avatarUrl
                : Optional.empty();
    }
}
