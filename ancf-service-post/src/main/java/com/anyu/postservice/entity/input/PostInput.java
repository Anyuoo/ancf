package com.anyu.postservice.entity.input;


import com.anyu.common.model.enums.PostType;

public class PostInput {

    private String title;
    private String content;
    private PostType type;

    public PostInput() {
    }

    public static PostInput build() {
        return new PostInput();
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

    public PostType getType() {
        return type;
    }

    public PostInput setType(PostType type) {
        this.type = type;
        return this;
    }
}
