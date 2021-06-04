package com.anyu.common.model.entity;

import com.anyu.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("video")
public class Video extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String title;

    @TableField("`desc`")
    private String desc;

    private String videoUrl;

    private String coverUrl;
}
