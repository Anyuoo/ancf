package com.anyu.userservice.resolver;


import com.anyu.common.model.CommonPage;
import com.anyu.common.model.entity.User;
import com.anyu.userservice.entity.input.condition.UserPageCondition;
import com.anyu.userservice.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultEdge;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserQueryResolver implements GraphQLQueryResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserQueryResolver.class);

    @Autowired
    private UserService userService;


    /**
     * @param id user id
     * @return user
     */
    public CompletableFuture<Optional<User>> getUser(@Nullable Long id, @Nullable String email,
                                                     @Nullable String mobile, @Nullable String account) {
        return CompletableFuture.supplyAsync(() -> {
                    if (id != null) {
                        return userService.getUserById(id);
                    }
                    if (StringUtils.isNotBlank(email)) {
                        return userService.getUserByEmail(email);
                    }
                    if (StringUtils.isNotBlank(mobile)) {
                        return userService.getUserByMobile(mobile);
                    }
                    if (StringUtils.isNotBlank(account)) {
                        return userService.getUserByAccount(account);
                    }
                    return Optional.<User>empty();
                }
        ).thenApply(user -> user);
    }


    /**
     * @param first 数据量
     * @param after 分页坐标
     * @return 分页对象
     */
    public CompletableFuture<Connection<User>> getUsers(int first, @Nullable String after, UserPageCondition condition) {
        List<User> users = userService.listUserAfter(first, CommonPage.decodeCursorWith(after), condition);
        return CompletableFuture.supplyAsync(() -> CommonPage.<User>build()
                .newConnection(first, after, () -> users.stream()
                        .map(user -> new DefaultEdge<>(user, CommonPage.createCursorWith(user.getId())))
                        .limit(first)
                        .collect(Collectors.toUnmodifiableList())));
    }
}
