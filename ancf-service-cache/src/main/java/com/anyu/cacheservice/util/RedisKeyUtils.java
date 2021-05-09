package com.anyu.cacheservice.util;

import com.anyu.common.model.enums.EntityType;

/**
 * redis 键生成工具类
 *
 * @author Anyu
 * @since 2020/10/31
 */
public class RedisKeyUtils {
    //分割符
    private final static String SPLIT = ":";
    //激活码
    private final static String ACTIVATION = "activation";
    private final static String EMAIL = "email";
    private final static String MOBILE = "mobile";
    //帖子
    private final static String POST = "post";
    //点赞
    private final static String LIKE = "like";
    //浏览
    private final static String LOOK = "look";
    //验证码
    private final static String VERIFY = "verify-code";

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

    /**
     * 获取帖子点赞key 储存用户ID
     * "like:user:1222
     */
    public static String getLikeKeyByEntityType(EntityType entityType, String entityId) {
        return POST + LIKE + SPLIT + entityType.name() + SPLIT + entityId;
    }

    /**
     * 获取验证码
     * @param email 邮箱
     */
    public static String getVerifyCode(String email) {
        return VERIFY+SPLIT+EMAIL+email;
    }

    /**
     * 获取帖子浏览数
     * @param postId 帖子id
     */
    public static String getPostLookKey(long postId) {
        return POST + SPLIT + LOOK + SPLIT + postId;
    }
}


