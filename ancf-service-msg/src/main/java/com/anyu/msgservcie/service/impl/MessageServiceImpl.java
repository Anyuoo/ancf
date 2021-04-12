package com.anyu.msgservcie.service.impl;


import com.anyu.common.model.entity.Message;
import com.anyu.common.util.CommonUtils;
import com.anyu.common.util.GlobalConstant;
import com.anyu.common.util.SensitiveFilter;
import com.anyu.msgservcie.entity.MessageInput;
import com.anyu.msgservcie.mapper.MessageMapper;
import com.anyu.msgservcie.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
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
    @Resource
    private SensitiveFilter sensitiveFilter;

    @Override
    public boolean sendMsg(MessageInput input) {
        final var content = sensitiveFilter.filter(input.getContent());
        //进行转码
        input.setContent(HtmlUtils.htmlEscape(content));
        final var chartId = CommonUtils.createChartId(input.getFromId(), input.getToId());
        final var message = new Message();
        BeanUtils.copyProperties(input, message);
        message.setChartId(chartId);
        return save(message);
    }

    @Override
    public List<Message> listMsgAfter(Integer id, int first, @NotNull String chartId) {
        final var chainWrapper = lambdaQuery()
                .eq(Message::getChartId, chartId)
                .orderByAsc(Message::getCreateTime);
        if (id == null) {
            chainWrapper.ge(Message::getId, id);
        }
        return chainWrapper.last(GlobalConstant.PAGE_SQL_LIMIT + first).list();
    }

}