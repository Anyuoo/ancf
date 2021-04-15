package com.anyu.postservice.model.input;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import org.springframework.web.util.HtmlUtils;

public class CommentInput {
    private String content;
    private EntityType entityType;
    private Integer entityId;
    private Integer targetId;

    public CommentInput() {
    }


    public Comment toEntity() {
        return new Comment()
                .setContent(HtmlUtils.htmlEscape(content))
                .setEntityId(entityId)
                .setEntityType(entityType)
                .setTargetId(targetId);
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
