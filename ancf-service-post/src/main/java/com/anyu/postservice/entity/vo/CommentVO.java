package com.anyu.postservice.entity.vo;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.entity.User;

import java.util.List;


public class CommentVO {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    //评论点赞数
    private Integer cmtLikeNum;
    //评论点赞状态
    private boolean cmtLikeStatus;

    public static CommentVO getInstance() {
        return new CommentVO();
    }

    public Long getId() {
        return id;
    }

    public CommentVO setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public CommentVO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public CommentVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentVO setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getCmtLikeNum() {
        return cmtLikeNum;
    }

    public CommentVO setCmtLikeNum(Integer cmtLikeNum) {
        this.cmtLikeNum = cmtLikeNum;
        return this;
    }

    public boolean isCmtLikeStatus() {
        return cmtLikeStatus;
    }

    public CommentVO setCmtLikeStatus(boolean cmtLikeStatus) {
        this.cmtLikeStatus = cmtLikeStatus;
        return this;
    }

}
