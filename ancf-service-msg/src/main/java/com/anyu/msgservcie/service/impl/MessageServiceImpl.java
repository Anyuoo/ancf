package com.anyu.msgservcie.service.impl;


import com.anyu.common.model.entity.Message;
import com.anyu.common.util.CommonUtils;
import com.anyu.msgservcie.entity.MessageInput;
import com.anyu.msgservcie.mapper.MessageMapper;
import com.anyu.msgservcie.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (Message)表服务实现类
 *
 * @author Anyu
 * @since 2020-10-10 12:51:56
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public boolean sendMsg(MessageInput input) {
        //进行转码
        input.setContent(HtmlUtils.htmlEscape(input.getContent()));
        final var chartId = CommonUtils.createChartId(input.getFromId(), input.getToId());
        final var message = new Message();
        BeanUtils.copyProperties(input, message);
        message.setChartId(chartId);
        return this.save(message);
    }

    @Override
    public List<Message> listMsgAfter(Long id, int first, @NotNull String chartId) {
        final var chainWrapper = this.lambdaQuery()
                .eq(Message::getChartId, chartId)
                .orderByAsc(Message::getCreateTime);
        if (id == null) {
            chainWrapper.ge(Message::getId, id);
        }
        return chainWrapper.last("limit " + first).list();
    }

}