package com.anyu.common.model.entity;


import com.anyu.common.model.enums.PostType;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Post)实体类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */

@TableName(value = "post")
public class Post implements Serializable {
    private static final long serialVersionUID = -64017816087882713L;
    /**
     * 帖子ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 帖子类型 0-普通，1-加精
     */
    private PostType type;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 帖子标题
     */
    private String title;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 评论数量
     */
    private Integer cmtNum;

    private Float score;
    /**
     * 0-正常，1-删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * 帖子创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 帖子修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedTime;

    public Post() {
    }

    public static Post build() {
        return new Post();
    }

    public Long getId() {
        return id;
    }

    public Post setId(Long id) {
        this.id = id;
        return this;
    }

    public PostType getType() {
        return type;
    }

    public Post setType(PostType type) {
        this.type = type;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Post setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getCmtNum() {
        return cmtNum;
    }

    public Post setCmtNum(Integer cmtNum) {
        this.cmtNum = cmtNum;
        return this;
    }

    public Float getScore() {
        return score;
    }

    public Post setScore(Float score) {
        this.score = score;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Post setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Post setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public Post setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}