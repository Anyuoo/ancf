package com.anyu.postservice.model.input;


import com.anyu.common.model.entity.Post;
import com.anyu.common.model.enums.PostType;

import javax.validation.constraints.NotBlank;

public class PostInput {

    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "帖子内容不能为空")
    private String content;

    public PostInput() {
    }

    public Post toEntity() {
        return new Post()
                .setTitle(title)
                .setContent(content);
    }

    public String getTitle() {
        return title;
    }

    public PostInput setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostInput setContent(String content) {
        this.content = content;
        return this;
    }
}
