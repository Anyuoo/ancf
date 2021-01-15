package com.anyu.msgservcie.resolver;


import com.anyu.common.result.CommonResult;
import com.anyu.msgservcie.entity.MessageInput;
import com.anyu.msgservcie.service.MessageService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("msgMutationResolver")
public class MsgMutationResolver implements GraphQLMutationResolver {
    @Autowired
    private MessageService messageService;

    public CommonResult sendMsg(MessageInput input) {
        if (messageService.sendMsg(input)) {
            return CommonResult.succeed("message succeed ");
        }
        return CommonResult.failed("message failed to send ");
    }
}
