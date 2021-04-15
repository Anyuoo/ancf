package com.anyu.msgservcie.model;

public class MessageInput {
    /**
     * 创建者
     */
    private Long fromId;
    /**
     * 接收者
     */
    private Long toId;
    /**
     * 内容
     */
    private String content;

    public MessageInput() {
    }

    public static MessageInput build() {
        return new MessageInput();
    }

    public Long getFromId() {
        return fromId;
    }

    public MessageInput setFromId(Long fromId) {
        this.fromId = fromId;
        return this;
    }

    public Long getToId() {
        return toId;
    }

    public MessageInput setToId(Long toId) {
        this.toId = toId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageInput setContent(String content) {
        this.content = content;
        return this;
    }
}
