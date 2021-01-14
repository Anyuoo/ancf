package com.anyu.userservice.resolver;


import com.anyu.ancf.service.OssService;
import com.anyu.authservice.gql.AncfGqlHttpContext;
import com.anyu.authservice.service.AuthService;
import com.anyu.common.model.CommonResult;
import com.anyu.common.model.entity.User;
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
import java.util.concurrent.CompletableFuture;

@Service
public class UserMutationResolver implements GraphQLMutationResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserMutationResolver.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OssService ossService;

    @Autowired
    private AuthService authService;


    /**
     * 用户注册接口
     *
     * @param input 注册参数
     * @return 结果
     */
    public CommonResult register(@NotNull UserInput input) {
        return userService.register(input);
    }

    public CompletableFuture<CommonResult> login(@NotBlank String principal, @NotBlank String password) {
        return CompletableFuture.supplyAsync(() -> userService.login(principal, password))
                .thenApply(result -> {
                    //success == true 登录成功
                    if (result.getSuccess()) {
                        User user = (User) result.getData();
                        Optional<String> token = authService.createJwt(String.valueOf(user.getId()), user.getNickname(), "admin");
                        return CommonResult.succeed(result.getMessage(), token);
                    }
                    return CommonResult.failed(result.getMessage());
                });
    }

    public CompletableFuture<CommonResult> activateUser(@NotBlank String activationKey,
                                                        @NotBlank String activationCode, boolean isEmail) {
        return CompletableFuture.supplyAsync(() -> userService.activateUser(activationKey, activationCode, isEmail));
    }

    public CompletableFuture<CommonResult> removeUser(@NonNull Long id) {
        return CompletableFuture.supplyAsync(() -> userService.removeUserById(id));
    }

    public CompletableFuture<CommonResult> updateUserInfo(@NotNull Long id, @NotNull UserInput input) {
        return CompletableFuture.supplyAsync(() -> userService.updateUserById(id, input));
    }

    public CompletableFuture<CommonResult> uploadAvatar(DataFetchingEnvironment environment) {
        return CompletableFuture.supplyAsync(() -> {
            AncfGqlHttpContext context = environment.getContext();
            Part avatar = context.getFilePart("avatar");
            if (avatar == null) {
                return CommonResult.failed("上传失败");
            }
            String url = ossService.uploadAvatar(avatar);
            logger.info("filename: {}, size: {},url length {}", avatar.getName(), avatar.getSize(), url.length());
            return CommonResult.succeed(url);
        });
    }

}
