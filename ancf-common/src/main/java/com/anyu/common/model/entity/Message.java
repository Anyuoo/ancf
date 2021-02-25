package com.anyu.common.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Message)实体类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:56
 */
@TableName(value = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = -62242889282154549L;
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 创建者
     */
    private Integer fromId;
    /**
     * 接收者
     */
    private Integer toId;
    /**
     * 私聊的对话ID
     */
    private String chartId;
    /**
     * 内容
     */
    private String content;
    /**
     * 0-正常，2-删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifiedTime;

    public Message() {
    }

    public static Message build() {
        return new Message();
    }

    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFromId() {
        return fromId;
    }

    public Message setFromId(Integer fromId) {
        this.fromId = fromId;
        return this;
    }

    public Integer getToId() {
        return toId;
    }

    public Message setToId(Integer toId) {
        this.toId = toId;
        return this;
    }

    public String getChartId() {
        return chartId;
    }

    public Message setChartId(String chartId) {
        this.chartId = chartId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Message setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Message setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public Message setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }
}