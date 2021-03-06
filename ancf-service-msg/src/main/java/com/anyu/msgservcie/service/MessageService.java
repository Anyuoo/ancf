package com.anyu.msgservcie.service;


import com.anyu.common.model.entity.Message;
import com.anyu.msgservcie.entity.MessageInput;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author Anyu
 * @since 2020-10-10 12:51:56
 */
public interface MessageService extends IService<Message> {
    boolean sendMsg(MessageInput input);

    List<Message> listMsgAfter(@Nullable Integer id, int first, @NotNull String chartId);
}