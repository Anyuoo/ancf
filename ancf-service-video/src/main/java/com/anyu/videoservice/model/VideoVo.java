package com.anyu.videoservice.model;

import com.anyu.common.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class VideoVo {
    private Long id;

    private Long userId;

    private User publisher;

    private String title;

    private String desc;

    private String videoUrl;

    private String coverUrl;

    private LocalDateTime createTime;

}
