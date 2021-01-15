package com.anyu.userservice.service;


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

    boolean register(UserInput input);

    boolean updateUserById(@NonNull Long id, UserInput input);

    boolean removeUserById(@NonNull Long id);

    boolean activateUser(@NotBlank String activationKey, @NotBlank String activationCode, boolean isEmail);

    Optional<String> login(String principal, String password);

}