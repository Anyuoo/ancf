package com.anyu.common.util;

import com.anyu.common.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class GlobalContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public void saveCurrentUser(User user) {
        currentUser.set(user);
    }

    public User getCurrentUser() {
        return currentUser.get();
    }

    public void removeCurrentUser() {
        currentUser.remove();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
