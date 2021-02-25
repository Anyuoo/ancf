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
    void doPostLike(Integer userId,Integer postId,Integer postOwnerId);

    void doCommentLike(Integer userId, Integer cmtId,Integer cmtOwnerId);

    long countPostLikeNum(Integer postId);

    long countCommentLikeNum(Integer cmtId);

    long countUserLikeNum(Integer userId);

    boolean getPostLikeStatus(Integer userId,Integer postId);

    boolean getCmtLikeStatus(Integer userId, Integer cmtId);


}
