package com.anyu.common.util;

/**
 * redis 键生成工具类
 *
 * @author Anyu
 * @since 2020/10/31
 */
public class RedisKeyUtils {
    private final static String SPLIT = ":";
    private final static String ACTIVATION = "activation";
    private final static String EMAIL = "email";
    private final static String MOBILE = "mobile";

    /**
     * 根据邮箱获取激活码
     *
     * @param email 邮箱
     * @return "activation:email:xxx@qq.com"
     */
    public static String getActivationKeyByEmail(String email) {
        return ACTIVATION + SPLIT + EMAIL + SPLIT + email;
    }

    /**
     * 根据邮箱获取激活码
     *
     * @param mobile 手机号
     * @return "activation:mobile:18502861999"
     */
    public static String getActivationKeyByMobile(String mobile) {
        return ACTIVATION + SPLIT + MOBILE + SPLIT + MOBILE;
    }
}


