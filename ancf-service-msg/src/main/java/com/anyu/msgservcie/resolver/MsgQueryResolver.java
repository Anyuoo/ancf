package com.anyu.msgservcie.resolver;


import com.anyu.common.model.entity.Message;
import com.anyu.common.result.CommonPage;
import com.anyu.common.result.annotation.QueryResolver;
import com.anyu.msgservcie.service.MessageService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultEdge;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * 消息解析器
 *
 * @author Anyu
 * @since 2020/10/10
 */
@QueryResolver("msgQueryResolver")
public class MsgQueryResolver implements GraphQLQueryResolver {
    @Resource
    private MessageService messageService;

    public Message getMessage(@NonNull Long id) {
        return messageService.getById(id);
    }

    public Connection<Message> getMessages(int first, @Nullable String after, @NonNull String chartId) {
        final var messages = messageService.listMsgAfter(CommonPage.decodeCursorWith(after), first, chartId);
        return CommonPage.<Message>build()
                .newConnection(first, after, () -> messages.stream()
                        .map(message -> new DefaultEdge<>(message, CommonPage.createCursorWith(message.getId())))
                        .collect(Collectors.toUnmodifiableList()));
    }

}
