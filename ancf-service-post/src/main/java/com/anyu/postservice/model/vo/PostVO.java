package com.anyu.postservice.model.vo;


import com.anyu.common.model.enums.PostType;

import java.time.LocalDateTime;


public class PostVO {
    //用户ID
    private Integer userId;
    //用户昵称
    private String nickname;
    //帖子ID
    private Integer id;
    //帖子标题
    private String title;
    //帖子内容
    private String content;
    //评论数量
    private Integer cmtNum;
    //帖子类型 0-普通，1-加精
    private PostType type;
    //帖子的点赞数量
    private Integer likeNum;
    //帖子的点赞状态
    private Boolean likeStatus;
    //帖子创建时间
    private LocalDateTime createTime;
    //帖子修改时间
    private LocalDateTime modifiedTime;

    public static PostVO getInstance() {
        return new PostVO();
    }

    public Integer getUserId() {
        return userId;
    }

    public PostVO setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public PostVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public PostVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PostVO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostVO setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getCmtNum() {
        return cmtNum;
    }

    public PostVO setCmtNum(Integer cmtNum) {
        this.cmtNum = cmtNum;
        return this;
    }

    public PostType getType() {
        return type;
    }

    public PostVO setType(PostType type) {
        this.type = type;
        return this;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public PostVO setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
        return this;
    }

    public Boolean getLikeStatus() {
        return likeStatus;
    }

    public PostVO setLikeStatus(Boolean likeStatus) {
        this.likeStatus = likeStatus;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public PostVO setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public PostVO setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}
