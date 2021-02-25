package com.anyu.common.model.entity;


import com.anyu.common.model.enums.EntityType;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Comment)实体类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */

@TableName(value = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = -80540297733715849L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 实体id
     */
    private Integer entityId;
    /**
     * 0-帖子，1-评论
     */
    private EntityType entityType;
    /**
     * 目标ID
     */
    private Integer targetId;
    /**
     * 0-正常，1-已删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedTime;

    public Comment() {
    }

    public static Comment build() {
        return new Comment();
    }


    public Integer getId() {
        return id;
    }

    public Comment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Comment setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public Comment setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Comment setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public Comment setTargetId(Integer targetId) {
        this.targetId = targetId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Comment setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Comment setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public Comment setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}