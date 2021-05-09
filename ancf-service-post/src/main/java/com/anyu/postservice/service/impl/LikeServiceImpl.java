package com.anyu.postservice.service.impl;

import com.anyu.cacheservice.service.CacheService;
import com.anyu.common.model.enums.EntityType;
import com.anyu.postservice.service.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private CacheService cacheService;


    @Override
    public void doPostLike(Integer userId, Integer postId, Integer postOwnerId) {
        cacheService.like(String.valueOf(userId), EntityType.POST, String.valueOf(postId), String.valueOf(postOwnerId));
    }

    @Override
    public void doCommentLike(Integer userId, Integer cmtId, Integer cmtOwnerId) {
        cacheService.like(String.valueOf(userId), EntityType.COMMENT, String.valueOf(cmtId), String.valueOf(cmtOwnerId));
    }

    @Override
    public int countPostLikeNum(Integer postId) {
        return cacheService.countEntityLikeNum(EntityType.POST, String.valueOf(postId));
    }

    @Override
    public int countCommentLikeNum(Integer cmtId) {
        return cacheService.countEntityLikeNum(EntityType.COMMENT, String.valueOf(cmtId));
    }


    @Override
    public int countUserLikeNum(Integer userId) {
        return cacheService.countUserLikeNum(String.valueOf(userId));
    }

    @Override
    public boolean getPostLikeStatus(Integer userId, Integer postId) {
        return cacheService.getEntityLikeStatus(String.valueOf(userId), EntityType.POST, String.valueOf(postId));
    }

    @Override
    public boolean getCmtLikeStatus(Integer userId, Integer cmtId) {
        return cacheService.getEntityLikeStatus(String.valueOf(userId), EntityType.COMMENT, String.valueOf(cmtId));
    }
}
