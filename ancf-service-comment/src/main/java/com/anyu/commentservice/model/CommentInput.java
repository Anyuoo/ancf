package com.anyu.commentservice.model;


import com.anyu.common.model.entity.Comment;
import com.anyu.common.model.enums.EntityType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.util.HtmlUtils;

@Getter
@Setter
@Accessors(chain = true)
public class CommentInput {
    private String content;
    private EntityType entityType;
    private Long entityId;
    private Long targetId;

    public CommentInput() {
    }


    public Comment toEntity() {
        return new Comment()
                .setContent(HtmlUtils.htmlEscape(content))
                .setEntityId(entityId)
                .setEntityType(entityType)
                .setTargetId(targetId);
    }
}
