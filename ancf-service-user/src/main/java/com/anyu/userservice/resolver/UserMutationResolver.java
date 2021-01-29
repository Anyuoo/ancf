package com.anyu.userservice.resolver;


import com.anyu.ancf.service.OssService;
import com.anyu.authservice.gql.AncfGqlHttpContext;
import com.anyu.common.result.CommonResult;
import com.anyu.common.result.type.FileResultType;
import com.anyu.common.result.type.UserResultType;
import com.anyu.userservice.entity.input.UserInput;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserMutationResolver implements GraphQLMutationResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserMutationResolver.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OssService ossService;


    /**
     * 用户注册接口
     *
     * @param input 注册参数
     * @return 结果
     */
    public CommonResult register(@NotNull UserInput input) {
        if (userService.register(input)) {
            return CommonResult.with(UserResultType.REGISTER_SUCCESS);
        }
        return CommonResult.with(UserResultType.REGISTER_ERROR);
    }

    public CommonResult login(@NotBlank String principal, @NotBlank String password) {

        Optional<String> loginJtw = userService.login(principal, password);
        return loginJtw.map(jwt -> CommonResult.with(UserResultType.LOGIN_SUCCESS, jwt))
                .orElseGet(() -> CommonResult.with(UserResultType.LOGIN_ERROR));
    }

    public CommonResult activateUser(@NotBlank String activationKey,
                                     @NotBlank String activationCode, boolean isEmail) {
        if (userService.activateUser(activationKey, activationCode, isEmail)) {
            return CommonResult.with(UserResultType.ACTIVE_SUCCESS);
        }
        return CommonResult.with(UserResultType.ACTIVE_ERROR);
    }

    public CommonResult removeUser(@NonNull Long id) {
        if (userService.removeUserById(id)) {
            return CommonResult.with(UserResultType.REMOVE_SUCCESS);
        }
        return CommonResult.with(UserResultType.REMOVE_ERROR);

    }

    public CommonResult updateUserInfo(@NotNull Long id, @NotNull UserInput input) {
        if (userService.updateUserById(id, input)) {
            return CommonResult.with(UserResultType.UPDATE_INFO_SUCCESS);
        }
        return CommonResult.with(UserResultType.UPDATE_INFO_ERROR);
    }

    public CommonResult uploadAvatar(DataFetchingEnvironment environment) {
        AncfGqlHttpContext context = environment.getContext();
        Part avatar = context.getFilePart("avatar");
        if (avatar == null) {
            return CommonResult.with(FileResultType.UPLOAD_ERROR);
        }
        String url = ossService.uploadAvatar(avatar);
        logger.debug("filename: {}, size: {},url length {}", avatar.getName(), avatar.getSize(), url.length());
        return CommonResult.with(FileResultType.UPLOAD_SUCCESS, url);
    }

}
