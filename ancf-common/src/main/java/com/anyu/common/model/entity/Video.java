package com.anyu.common.model.entity;

import com.anyu.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Video extends BaseEntity {
    private Long id;
    private Long userId;
    private String title;
    private String desc;
    private String srcUrl;
    private String coverUrl;
}
