package com.anyu.postservice.entity.vo;


public class ReplyVO {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    private Long targetId;
    private String targetNickname;
    private long replyLikeNum;
    private boolean replyLikeStatus;

    public static ReplyVO getInstance() {
        return new ReplyVO();
    }
    public Long getId() {
        return id;
    }

    public ReplyVO setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public ReplyVO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public ReplyVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReplyVO setContent(String content) {
        this.content = content;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public ReplyVO setTargetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public String getTargetNickname() {
        return targetNickname;
    }

    public ReplyVO setTargetNickname(String targetNickname) {
        this.targetNickname = targetNickname;
        return this;
    }

    public long getReplyLikeNum() {
        return replyLikeNum;
    }

    public ReplyVO setReplyLikeNum(long replyLikeNum) {
        this.replyLikeNum = replyLikeNum;
        return this;
    }

    public boolean isReplyLikeStatus() {
        return replyLikeStatus;
    }

    public ReplyVO setReplyLikeStatus(boolean replyLikeStatus) {
        this.replyLikeStatus = replyLikeStatus;
        return this;
    }
}
