package com.anyu.userservice.resolver;


import com.anyu.ancf.service.OssService;
import com.anyu.authservice.annotation.UserRole;
import com.anyu.authservice.gql.AncfGqlHttpContext;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.MutationResolver;
import com.anyu.common.result.type.FileResultType;
import com.anyu.common.result.type.UserResultType;
import com.anyu.userservice.model.input.UserInput;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
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
    public Boolean register(@NotBlank String email,@NotBlank String password,@NotBlank String code) {
        return userService.register(email,password,code);
    }

    /**
     * 发送注册验证码
     * @param email 发送验证码
     */
    public Boolean sendVerifyCode(@NotBlank String email){
        return userService.sendVerifyCodeByEmail(email);
    }

    public Boolean sendEmailUpdateCode(@NotBlank String email) {
        return userService.sendEmailUpdateCode(authService.getCurrentUserId(),email);
    }


    public Optional<String> login(@NotBlank String principal, @NotBlank String password) {

        return userService.login(principal, password);
    }
//
//    @UserRole
//    public CommonResult activateUser(@NotBlank String activationKey,
//                                     @NotBlank String activationCode, boolean isEmail) {
//        return CommonResult.supplyByBool(userService.activateUser(activationKey, activationCode, isEmail));
//    }
//
//    @UserRole
//    public CommonResult removeUser(@NonNull Integer id) {
//        return CommonResult.supplyByBool(userService.removeUserById(id));
//
//    }
//
    @UserRole
    public Boolean updateUserInfo(@NotNull UserInput input) {
        return userService.updateUserInfoById(authService.getCurrentUserId(), input);
    }

    public Boolean updatePassword(@NotBlank String password) {
        return userService.updatePassword(authService.getCurrentUserId(), password);
    }

    public Boolean updateEmail(@NotBlank String email, @NotBlank String code) {
        return userService.updateEmail(authService.getCurrentUserId(), email, code);
    }

    @UserRole
    public Optional<String> uploadAvatar(DataFetchingEnvironment environment) {
        AncfGqlHttpContext context = environment.getContext();
        final var avatar = context.getFilePart("avatar");
        if (avatar == null) {
            return Optional.empty();
        }
        final var url = ossService.uploadAvatar(avatar);
        if (userService.updateAvatar(authService.getCurrentUserId(), url)) {
            logger.debug("filename: {}, size: {},url length {}", avatar.getName(), avatar.getSize(), url.length());
            return Optional.of(url);
        }
        return Optional.empty();
    }

}
