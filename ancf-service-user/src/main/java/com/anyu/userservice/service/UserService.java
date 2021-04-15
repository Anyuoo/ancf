package com.anyu.userservice.service;


import com.anyu.common.model.entity.User;
import com.anyu.common.util.GlobalConstant;
import com.anyu.userservice.model.condition.UserPageCondition;
import com.anyu.userservice.model.input.UserInput;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

/**
 * (User)表服务接口
 *
 * @author Anyu
 * @since 2020-10-07 09:56:09
 */
public interface UserService extends IService<User>, GlobalConstant {

    List<User> listUserAfter(int first, Integer id, UserPageCondition condition);

    Optional<User> getUserById(int userId);

    Optional<User> getUserByAccount(@NotBlank String account);

    Optional<User> getUserByEmail(@NotBlank String email);

    Optional<User> getUserByMobile(@NotBlank String mobile);

    boolean register(UserInput input);

    boolean updateUserById(@NonNull Integer id, UserInput input);

    boolean removeUserById(@NonNull Integer id);

    boolean activateUser(@NotBlank String activationKey, @NotBlank String activationCode, boolean isEmail);

    Optional<String> login(String principal, String password);

    boolean updateAvatar(int userId, String url);
}