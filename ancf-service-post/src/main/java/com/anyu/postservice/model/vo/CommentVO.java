package com.anyu.postservice.model.vo;


public class CommentVO {
    private Integer id;
    private Integer userId;
    private String nickname;
    private String content;
    //评论点赞数
    private Integer cmtLikeNum;
    //评论点赞状态
    private boolean cmtLikeStatus;

    public static CommentVO getInstance() {
        return new CommentVO();
    }

    public Integer getId() {
        return id;
    }

    public CommentVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public CommentVO setUserId(Integer userId) {
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
