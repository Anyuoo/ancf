package com.anyu.postservice.service.impl;

import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.enums.EntityType;
import com.anyu.postservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private CacheService cacheService;


    @Override
    public void doPostLike(Long userId, Long postId, Long postOwnerId) {
        cacheService.like(String.valueOf(userId),EntityType.POST,String.valueOf(postId),String.valueOf(postOwnerId));
    }

    @Override
    public void doCommentLike(Long userId, Long cmtId, Long cmtOwnerId) {
        cacheService.like(String.valueOf(userId), EntityType.COMMENT, String.valueOf(cmtId), String.valueOf(cmtOwnerId));
    }

    @Override
    public long countPostLikeNum(Long postId) {
        return cacheService.countEntityLikeNum(EntityType.POST,String.valueOf(postId));
    }

    @Override
    public long countCommentLikeNum(Long cmtId) {
        return cacheService.countEntityLikeNum(EntityType.COMMENT,String.valueOf(cmtId));
    }



    @Override
    public long countUserLikeNum(Long userId) {
        return cacheService.countUserLikeNum(String.valueOf(userId));
    }

    @Override
    public boolean getPostLikeStatus(Long userId, Long postId) {
        return cacheService.getEntityLikeStatus(String.valueOf(userId),EntityType.POST,String.valueOf(postId));
    }

    @Override
    public boolean getCmtLikeStatus(Long userId, Long cmtId) {
        return cacheService.getEntityLikeStatus(String.valueOf(userId), EntityType.COMMENT, String.valueOf(cmtId));
    }
}
