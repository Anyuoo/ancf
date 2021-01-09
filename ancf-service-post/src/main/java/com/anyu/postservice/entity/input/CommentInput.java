package com.anyu.postservice.entity.input;


import com.anyu.common.model.enums.EntityType;

public class CommentInput {
    private Long userId;
    private String content;
    private EntityType entityType;
    private Long entityId;
    private Long targetId;

    public CommentInput() {
    }


    public static CommentInput build() {
        return new CommentInput();
    }

    public Long getUserId() {
        return userId;
    }

    public CommentInput setUserId(Long userId) {
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

    public Long getEntityId() {
        return entityId;
    }

    public CommentInput setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public CommentInput setTargetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }
}
