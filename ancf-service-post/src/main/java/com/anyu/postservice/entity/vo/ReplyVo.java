package com.anyu.postservice.entity.vo;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.entity.User;

public class ReplyVo {
    private Comment reply;
    private User replier;
    private User target;
    private long replyLikeNum;
    private boolean replyLikeStatus;

    public ReplyVo() {
    }

    public static ReplyVo build() {
        return new ReplyVo();
    }

    public Comment getReply() {
        return reply;
    }

    public ReplyVo setReply(Comment reply) {
        this.reply = reply;
        return this;
    }

    public User getReplier() {
        return replier;
    }

    public ReplyVo setReplier(User replier) {
        this.replier = replier;
        return this;
    }

    public User getTarget() {
        return target;
    }

    public ReplyVo setTarget(User target) {
        this.target = target;
        return this;
    }

    public long getReplyLikeNum() {
        return replyLikeNum;
    }

    public ReplyVo setReplyLikeNum(long replyLikeNum) {
        this.replyLikeNum = replyLikeNum;
        return this;
    }

    public boolean isReplyLikeStatus() {
        return replyLikeStatus;
    }

    public ReplyVo setReplyLikeStatus(boolean replyLikeStatus) {
        this.replyLikeStatus = replyLikeStatus;
        return this;
    }
}
