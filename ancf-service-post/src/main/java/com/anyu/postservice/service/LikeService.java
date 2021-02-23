package com.anyu.postservice.service;

/**
*
* @author Anyu
* @since 2021/1/28 下午5:15
*/
public interface LikeService {
    /**
     *
     */
    void doPostLike(Long userId,Long postId,Long postOwnerId);

    void doCommentLike(Long userId, Long cmtId,Long cmtOwnerId);

    long countPostLikeNum(Long postId);

    long countCommentLikeNum(Long cmtId);

    long countUserLikeNum(Long userId);

    boolean getPostLikeStatus(Long userId,Long postId);

    boolean getCmtLikeStatus(Long userId, Long cmtId);


}
