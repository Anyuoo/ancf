package com.anyu.likeservice.service;

import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.enums.EntityType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Anyu
 * @since 2021/1/28 下午5:15
 */
@Service
public class LikeService {

    @Resource
    private CacheService cacheService;


    public void doPostLike(long userId, long postId, long postOwnerId) {
        cacheService.like(String.valueOf(userId), EntityType.POST, String.valueOf(postId), String.valueOf(postOwnerId));
    }

    public void doCommentLike(long userId, long cmtId, long cmtOwnerId) {
        cacheService.like(String.valueOf(userId), EntityType.COMMENT, String.valueOf(cmtId), String.valueOf(cmtOwnerId));
    }


    public int countPostLikeNum(long postId) {
        return cacheService.countEntityLikeNum(EntityType.POST, String.valueOf(postId));
    }


    public int countCommentLikeNum(long cmtId) {
        return cacheService.countEntityLikeNum(EntityType.COMMENT, String.valueOf(cmtId));
    }


    public int countUserLikeNum(long userId) {
        return cacheService.countUserLikeNum(String.valueOf(userId));
    }


    public boolean getPostLikeStatus(long userId, long postId) {
        return cacheService.getEntityLikeStatus(String.valueOf(userId), EntityType.POST, String.valueOf(postId));
    }


    public boolean getCmtLikeStatus(long userId, long cmtId) {
        return cacheService.getEntityLikeStatus(String.valueOf(userId), EntityType.COMMENT, String.valueOf(cmtId));
    }


}
