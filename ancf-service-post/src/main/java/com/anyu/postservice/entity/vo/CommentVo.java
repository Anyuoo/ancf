package com.anyu.postservice.entity.vo;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.entity.User;

import java.util.List;


public class CommentVo {
    //评论
    private Comment comment;
    //评论者
    private User observer;
    //评论点赞数
    private long cmtLikeNum;
    //评论点赞状态
    private boolean cmtLikeStatus;
    //评论的评论
    private List<ReplyVo> replies;

    public CommentVo() {
    }

    public static CommentVo build() {
        return new CommentVo();
    }

    public Comment getComment() {
        return comment;
    }

    public CommentVo setComment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public User getObserver() {
        return observer;
    }

    public CommentVo setObserver(User observer) {
        this.observer = observer;
        return this;
    }

    public long getCmtLikeNum() {
        return cmtLikeNum;
    }

    public CommentVo setCmtLikeNum(long cmtLikeNum) {
        this.cmtLikeNum = cmtLikeNum;
        return this;
    }

    public boolean isCmtLikeStatus() {
        return cmtLikeStatus;
    }

    public CommentVo setCmtLikeStatus(boolean cmtLikeStatus) {
        this.cmtLikeStatus = cmtLikeStatus;
        return this;
    }

    public List<ReplyVo> getReplies() {
        return replies;
    }

    public CommentVo setReplies(List<ReplyVo> replies) {
        this.replies = replies;
        return this;
    }
}
