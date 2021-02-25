package com.anyu.postservice.entity.input;


import com.anyu.common.model.enums.EntityType;

public class CommentInput {
    private Integer userId;
    private String content;
    private EntityType entityType;
    private Integer entityId;
    private Integer targetId;

    public CommentInput() {
    }


    public static CommentInput build() {
        return new CommentInput();
    }

    public Integer getUserId() {
        return userId;
    }

    public CommentInput setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentInput setContent(String content) {
        this.content = content;
        return this;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public CommentInput setEntityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public CommentInput setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public CommentInput setTargetId(Integer targetId) {
        this.targetId = targetId;
        return this;
    }
}
