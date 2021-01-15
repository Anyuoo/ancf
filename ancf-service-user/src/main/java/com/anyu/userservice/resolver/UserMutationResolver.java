package com.anyu.userservice.resolver;


import com.anyu.ancf.service.OssService;
import com.anyu.authservice.gql.AncfGqlHttpContext;
import com.anyu.common.result.CommonResult;
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
            return CommonResult.succeed("user register successful!");
        }
        return CommonResult.failed("user register failed");
    }

    public CommonResult login(@NotBlank String principal, @NotBlank String password) {

        Optional<String> loginJtw = userService.login(principal, password);
        return loginJtw.map(jwt -> CommonResult.succeed("login successfully", jwt))
                .orElseGet(() -> CommonResult.failed("login unsuccessfully"));
    }

    public CommonResult activateUser(@NotBlank String activationKey,
                                     @NotBlank String activationCode, boolean isEmail) {
        if (userService.activateUser(activationKey, activationCode, isEmail)) {
            return CommonResult.succeed("user active successful");
        }
        return CommonResult.failed("user active unsuccessfully");
    }

    public CommonResult removeUser(@NonNull Long id) {
        if (userService.removeUserById(id)) {
            return CommonResult.succeed("remove user successfully");
        }
        return CommonResult.failed("remove user unsuccessfully");

    }

    public CommonResult updateUserInfo(@NotNull Long id, @NotNull UserInput input) {
        if (userService.updateUserById(id, input)) {
            return CommonResult.succeed("user information has been updated");
        }
        return CommonResult.failed("user information updated unsuccessfully");
    }

    public CommonResult uploadAvatar(DataFetchingEnvironment environment) {
        AncfGqlHttpContext context = environment.getContext();
        Part avatar = context.getFilePart("avatar");
        if (avatar == null) {
            return CommonResult.failed("上传失败");
        }
        String url = ossService.uploadAvatar(avatar);
        logger.debug("filename: {}, size: {},url length {}", avatar.getName(), avatar.getSize(), url.length());
        return CommonResult.succeed("upload successfully", url);
    }

}
