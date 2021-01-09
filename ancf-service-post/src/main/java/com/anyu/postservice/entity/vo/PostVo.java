package com.anyu.postservice.entity.vo;


import com.anyu.common.model.entity.Post;
import com.anyu.common.model.entity.User;
import graphql.relay.Connection;


public class PostVo {
    //帖子
    private Post post;
    //发帖者
    private User publisher;
    //帖子的点赞数量
    private int PostLikeNum;
    //帖子的点赞状态
    private boolean postLikeStatus;
    //帖子的评论,分页显示
    private Connection<CommentVo> comments;

    public PostVo() {
    }

    public static PostVo build() {
        return new PostVo();
    }

    public Post getPost() {
        return post;
    }

    public PostVo setPost(Post post) {
        this.post = post;
        return this;
    }

    public User getPublisher() {
        return publisher;
    }

    public PostVo setPublisher(User publisher) {
        this.publisher = publisher;
        return this;
    }

    public int getPostLikeNum() {
        return PostLikeNum;
    }

    public PostVo setPostLikeNum(int postLikeNum) {
        PostLikeNum = postLikeNum;
        return this;
    }

    public boolean isPostLikeStatus() {
        return postLikeStatus;
    }

    public PostVo setPostLikeStatus(boolean postLikeStatus) {
        this.postLikeStatus = postLikeStatus;
        return this;
    }

    public Connection<CommentVo> getComments() {
        return comments;
    }

    public PostVo setComments(Connection<CommentVo> comments) {
        this.comments = comments;
        return this;
    }
}
