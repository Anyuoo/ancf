package com.anyu.msgservcie.resolver;


import com.anyu.common.model.CommonPage;
import com.anyu.common.model.entity.Message;
import com.anyu.msgservcie.service.MessageService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 消息解析器
 *
 * @author Anyu
 * @since 2020/10/10
 */
@Component("msgQueryResolver")
public class MsgQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private MessageService messageService;

    public CompletableFuture<Message> getMessage(@NonNull Long id) {
        return CompletableFuture.supplyAsync(() -> messageService.getById(id));
    }

    public CompletableFuture<Connection<Message>> getMessages(int first, @Nullable String after, @NonNull String chartId) {
        List<Message> messages = messageService.listMsgAfter(CommonPage.decodeCursorWith(after), first, chartId);
        return CompletableFuture.supplyAsync(() -> CommonPage.<Message>build()
                .newConnection(first, after, () -> messages.stream()
                        .map(message -> new DefaultEdge<>(message, CommonPage.createCursorWith(message.getId())))
                        .collect(Collectors.toUnmodifiableList())));
    }

}
