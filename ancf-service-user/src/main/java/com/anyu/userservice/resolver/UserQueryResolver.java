package com.anyu.userservice.resolver;


import com.anyu.common.model.entity.User;
import com.anyu.common.result.CommonPage;
import com.anyu.common.result.annotation.QueryResolver;
import com.anyu.userservice.entity.condition.UserPageCondition;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@QueryResolver
public class UserQueryResolver implements GraphQLQueryResolver {

    @Resource
    private UserService userService;


    /**
     * @param id user id
     * @return user
     */
    public Optional<User> getUser(@Nullable Integer id, @Nullable String email,
                                  @Nullable String mobile, @Nullable String account) {
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
        return Optional.empty();
    }


    /**
     * @param first 数据量
     * @param after 分页坐标
     * @return 分页对象
     */
    public Connection<User> getUsers(int first, @Nullable String after, UserPageCondition condition) {
        List<User> users = userService.listUserAfter(first, CommonPage.decodeCursorWith(after), condition);
        return CommonPage.<User>build()
                .newConnection(first, after, () -> users.stream()
                        .map(user -> CommonPage.getDefaultEdge(user,user.getId()))
                        .limit(first)
                        .collect(Collectors.toUnmodifiableList()));
    }
}
