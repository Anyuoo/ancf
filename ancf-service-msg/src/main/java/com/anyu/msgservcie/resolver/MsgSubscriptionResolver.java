package com.anyu.msgservcie.resolver;


import com.anyu.common.model.entity.Message;
import com.anyu.common.result.CommonPage;
import com.anyu.msgservcie.service.MessageService;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import graphql.relay.Connection;
import org.reactivestreams.Publisher;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

@Component
public class MsgSubscriptionResolver implements GraphQLSubscriptionResolver {

    @Resource
    private MessageService messageService;

    public Publisher<Integer> test() {
        return Flux.range(0, 100).delayElements(Duration.ofSeconds(1));
    }

    public Publisher<Connection<Message>> receiveMsg(int first, @Nullable String after, @NonNull String chartId) {
        List<Message> messages = messageService.listMsgAfter(CommonPage.decodeCursorWith(after), first, chartId);
        return null;
    }
}
