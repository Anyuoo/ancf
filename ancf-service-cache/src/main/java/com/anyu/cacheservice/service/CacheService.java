package com.anyu.cacheservice.service;

import com.anyu.common.model.enums.EntityType;

import java.util.Optional;

public interface CacheService {
    /**
     * 获取用户注册激活码
     *
     * @param isEmail 是否为邮箱
     * @param key     键值
     */
    Optional<String> getActivationCode(boolean isEmail, String key);

    /**
     * 存储激活码
     *
     * @param isEmail    是否为有邮箱
     * @param key        键值
     * @param activeCode 激活
     */
    boolean setActivationCode(boolean isEmail, String key, String activeCode);


    /**
     * 点赞
     *
     * @param userId       点赞人
     * @param entityType   被点赞实体
     * @param entityId     被点赞实体ID
     * @param entityUserId 被点赞的用户
     */
    void like(String userId, EntityType entityType, String entityId, String entityUserId);

    /**
     * 获取实体点赞状态
     *
     * @param userId     用户
     * @param entityType 实体类型
     * @param entityId   实体ID
     * @return 点赞状态
     */
    boolean getEntityLikeStatus(String userId, EntityType entityType, String entityId);

    /**
     * 计算用户点赞数
     *
     * @param userId 用户id
     * @return 点赞数
     */
    int countUserLikeNum(String userId);

    int countEntityLikeNum(EntityType entityType, String entityId);

    /**
     * 获取邮箱验证码
     *
     * @param email 邮箱
     */
    Optional<String> getVerifyCode(String email);

    /**
     * 存储邮箱验证码
     *
     * @param email      邮箱
     * @param verifyCode 验证码
     */
    boolean setVerifyCode(String email, String verifyCode);

    /**
     * 增加帖子浏览
     */
    void addPostLookNum(long id);

    /**
     * 获取帖子浏览数
     *
     * @param id 帖子id
     */
    int getPostLookNum(long id);
}
