package com.anyu.userservice.service;


import com.anyu.common.model.CommonResult;
import com.anyu.common.model.entity.User;
import com.anyu.userservice.entity.input.UserInput;
import com.anyu.userservice.entity.input.condition.UserPageCondition;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

/**
 * (Subject)表服务接口
 *
 * @author Anyu
 * @since 2020-10-07 09:56:09
 */
public interface UserService extends IService<User> {

    List<User> listUserAfter(int first, Long id, UserPageCondition condition);

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByAccount(@NotBlank String account);

    Optional<User> getUserByEmail(@NotBlank String email);

    Optional<User> getUserByMobile(@NotBlank String mobile);

    CommonResult register(UserInput input);

    CommonResult updateUserById(@NonNull Long id, UserInput input);

    CommonResult removeUserById(@NonNull Long id);

    CommonResult activateUser(@NotBlank String activationKey, @NotBlank String activationCode, boolean isEmail);

    CommonResult login(String principal, String password);

}