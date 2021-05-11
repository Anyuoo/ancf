package com.anyu.common.model.entity;


import com.anyu.common.model.BaseEntity;
import com.anyu.common.model.enums.PostType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (Post)实体类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:46
 */

@EqualsAndHashCode(callSuper = true)
@TableName(value = "post")
@Data
@Accessors(chain = true)
public class Post extends BaseEntity {
    private static final long serialVersionUID = -64017816087882713L;
    /**
     * 帖子ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 帖子类型 0-普通，1-加精
     */
    private PostType type;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 帖子标题
     */
    private String title;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 评论数量
     */
    private Integer cmtNum;

    private Float score;


}