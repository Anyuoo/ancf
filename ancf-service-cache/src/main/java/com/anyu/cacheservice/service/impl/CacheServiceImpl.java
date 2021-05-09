package com.anyu.cacheservice.service.impl;

import com.anyu.cacheservice.service.CacheService;
import com.anyu.cacheservice.util.RedisKeyUtils;
import com.anyu.common.model.enums.EntityType;
import com.anyu.common.util.GlobalConstant;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    private final static Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        logger.info("CacheService init successfully");
    }

    @Override
    public Optional<String> getActivationCode(boolean isEmail, String key) {
        key = isEmail ? RedisKeyUtils.getActivationKeyByEmail(key)
                : RedisKeyUtils.getActivationKeyByMobile(key);
        return valueGet(key);
    }

    @Override
    public boolean setActivationCode(boolean isEmail, @NotBlank String key, @NotBlank String activeCode) {
        //将激活码缓存有效期两个小时
        final var activationKey = isEmail ? RedisKeyUtils.getActivationKeyByEmail(key)
                : RedisKeyUtils.getActivationKeyByMobile(key);
        valueSet(activationKey, activeCode);
        return setExpire(activationKey, GlobalConstant.CACHE_EXPIRED_2H);
    }

    @Override
    public boolean setVerifyCode(String email, String verifyCode) {
        final String verifyCodeKey = RedisKeyUtils.getVerifyCode(email);
        valueSet(verifyCodeKey,verifyCode);
        return setExpire(verifyCodeKey, GlobalConstant.CACHE_EXPIRED_2H);
    }

    @Override
    public Optional<String> getVerifyCode(String email) {
        return valueGet(RedisKeyUtils.getVerifyCode(email));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void like(String userId, EntityType entityType, String entityId, String entityUserId) {
        redisTemplate.execute(new SessionCallback<>() {
            @Override
            public Object execute(@NotNull RedisOperations operations) throws DataAccessException {
                //被点赞实体
                final var entityLikeKey = RedisKeyUtils.getLikeKeyByEntityType(entityType, entityId);
                //被点赞用户
                final var userLikeKey = RedisKeyUtils.getLikeKeyByEntityType(EntityType.USER, entityUserId);

                final var isMember = operations.opsForSet().isMember(entityLikeKey, userId);
                //事
                operations.multi();
                //为点赞
                if (isMember == null || !isMember) {
                    //实体点赞集合加入点赞人ID
                    operations.opsForSet().add(entityLikeKey, userId);
                    //被点赞人的点赞数加1
                    operations.opsForValue().increment(userLikeKey);
                } else {
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                }
                return operations.exec();
            }
        });
    }

    @Override
    public boolean getEntityLikeStatus(String userId, EntityType entityType, String entityId) {
        final var entityLikeKey = RedisKeyUtils.getLikeKeyByEntityType(entityType, entityId);
        final var isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
        return isMember != null && isMember;
    }

    @Override
    public int countUserLikeNum(String userId) {
        final var userLikeKey = RedisKeyUtils.getLikeKeyByEntityType(EntityType.USER, userId);
        Optional<Integer> userLikeNum = valueGet(userLikeKey);
        return userLikeNum.orElse(0);
    }

    @Override
    public int countEntityLikeNum(EntityType entityType, String entityId) {
        final var entityLikeKey = RedisKeyUtils.getLikeKeyByEntityType(entityType, entityId);
        final var entityLikeNum = redisTemplate.opsForSet().size(entityLikeKey);
        return entityLikeNum != null ? entityLikeNum.intValue() : 0;
    }

    @Override
    public void addPostLookNum(long id) {
        String postLookKey = RedisKeyUtils.getPostLookKey(id);
        redisTemplate.opsForValue().increment(postLookKey);
    }

    @Override
    public int getPostLookNum(long id) {
        String postLookKey = RedisKeyUtils.getPostLookKey(id);
        Integer num = (Integer)redisTemplate.opsForValue().get(postLookKey);
        return  num == null ? 0 : num;
    }

    /**
     * string类型
     */
    private void valueSet(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> valueGet(String key) {
        final var hasKey = redisTemplate.hasKey(key);
        if (hasKey == null || !hasKey) {
            return Optional.empty();
        }
        return Optional.ofNullable((T) redisTemplate.opsForValue().get(key));
    }

    /**
     * 设置过期时间
     */
    private boolean setExpire(String key, long seconds) {
        final var expire = redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return expire != null && expire;
    }
}
