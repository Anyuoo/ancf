package com.anyu.commentservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ReplyVO {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    private Long targetId;
    private String targetNickname;
    private Integer replyLikeNum;
    private Boolean replyLikeStatus;

}
