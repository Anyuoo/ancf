package com.anyu.commentservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CommentVO {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    //评论点赞数
    private Integer cmtLikeNum;
    //评论点赞状态
    private boolean cmtLikeStatus;


}
