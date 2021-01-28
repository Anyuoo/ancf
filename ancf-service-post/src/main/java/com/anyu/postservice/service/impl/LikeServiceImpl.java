package com.anyu.postservice.service.impl;

import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.enums.EntityType;
import com.anyu.postservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private CacheService cacheService;


    @Override
    public void doPostLike(String userId, String postId, String postOwnerId) {
        cacheService.like(userId,EntityType.POST,postId,postOwnerId);
    }

    @Override
    public void doCommentLike(String userId, String cmtId, String cmtOwnerId) {
        cacheService.like(userId, EntityType.COMMENT, cmtId, cmtOwnerId);
    }

    @Override
    public long countPostLikeNum(String postId) {
        return cacheService.countEntityLikeNum(EntityType.POST,postId);
    }

    @Override
    public long countCommentLikeNum(String cmtId) {
        return cacheService.countEntityLikeNum(EntityType.COMMENT,cmtId);
    }



    @Override
    public long countUserLikeNum(String userId) {
        return cacheService.countUserLikeNum(userId);
    }

    @Override
    public boolean getPostLikeStatus(String userId, String postId) {
        return cacheService.getEntityLikeStatus(userId,EntityType.POST,postId);
    }

    @Override
    public boolean getCmtLikeStatus(String userId, String cmtId) {
        return cacheService.getEntityLikeStatus(userId, EntityType.COMMENT, cmtId);
    }
}
