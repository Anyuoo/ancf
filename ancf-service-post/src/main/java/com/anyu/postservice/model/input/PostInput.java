package com.anyu.postservice.model.input;


import com.anyu.common.model.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
public class PostInput {

    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "帖子内容不能为空")
    private String content;
    
    public Post toEntity() {
        return new Post()
                .setTitle(title)
                .setContent(content);
    }

}
