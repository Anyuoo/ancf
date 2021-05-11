package com.anyu.common.model.entity;


import com.anyu.common.model.BaseEntity;
import com.anyu.common.model.enums.EntityType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (Comment)实体类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */

@EqualsAndHashCode(callSuper = true)
@TableName(value = "comment")
@Data
@Accessors(chain = true)
public class Comment extends BaseEntity {
    private static final long serialVersionUID = -80540297733715849L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 实体id
     */
    private Long entityId;
    /**
     * 0-帖子，1-评论
     */
    private EntityType entityType;
    /**
     * 目标ID
     */
    private Long targetId;

}