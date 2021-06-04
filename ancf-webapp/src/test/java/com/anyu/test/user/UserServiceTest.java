package com.anyu.test.user;


import com.anyu.test.BaseTest;
import com.anyu.userservice.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户服务测试
 *
 * @author Anyu
 * @since 2021/1/28 下午3:43
 */
public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Before
    public void init() {
        System.out.println("测试开始=========");
    }

    @After
    public void destroy() {
        System.out.println("测试结束=========");
    }

    @Test
    public void register() {
//        UserInput input = new UserInput();
//        input.setAccount("anyuoo")
//
//
////                .setGender(Gender.MALE)
//                .setRealName("anyu");
////        boolean register = userService.register(input.toEntity());
        System.out.println(false);
    }
}
