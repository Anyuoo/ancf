package com.anyu.common.model.entity;

import com.anyu.common.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (Message)实体类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName(value = "message")
public class Message extends BaseEntity {
    private static final long serialVersionUID = -62242889282154549L;
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建者
     */
    private Long fromId;
    /**
     * 接收者
     */
    private Long toId;
    /**
     * 私聊的对话ID
     */
    private String chartId;
    /**
     * 内容
     */
    private String content;

}