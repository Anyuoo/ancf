package com.anyu.msgservcie.resolver;



import com.anyu.common.model.CommonResult;
import com.anyu.msgservcie.entity.MessageInput;
import com.anyu.msgservcie.service.MessageService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("msgMutationResolver")
public class MsgMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private MessageService messageService;

    CompletableFuture<CommonResult> sendMsg(MessageInput input) {
        return CompletableFuture.supplyAsync(() -> messageService.sendMsg(input));
    }
}
