package com.anyu.postservice.service;

import com.anyu.common.model.enums.EntityType;

/**
*
* @author Anyu
* @since 2021/1/28 下午5:15
*/
public interface LikeService {
    /**
     *
     */
    void doPostLike(String userId,String postId,String postOwnerId);

    void doCommentLike(String userId, String cmtId,String cmtOwnerId);

    long countPostLikeNum(String postId);

    long countCommentLikeNum(String cmtId);

    long countUserLikeNum(String userId);

    boolean getPostLikeStatus(String userId,String postId);

    boolean getCmtLikeStatus(String userId, String cmtId);


}
