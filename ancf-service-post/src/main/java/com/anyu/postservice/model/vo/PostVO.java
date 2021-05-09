package com.anyu.postservice.model.vo;


import com.anyu.common.model.entity.User;
import com.anyu.common.model.enums.PostType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Getter
@Setter
@Accessors(chain = true)
public class PostVO {
    private User publisher;
    //帖子ID
    private Integer id;

    private Integer userId;
    //帖子标题
    private String title;
    //帖子内容
    private String content;
    //评论数量
    private Integer cmtNum;
    //浏览数
    private Integer lookNum;

    private Boolean cmtStatus;
    //帖子类型 0-普通，1-加精
    private PostType type;
    //帖子的点赞数量
    private Integer likeNum;
    //帖子的点赞状态
    private Boolean likeStatus;
    //帖子创建时间
    private LocalDateTime createTime;
    //帖子修改时间
    private LocalDateTime modifiedTime;
}
