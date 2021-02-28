package com.anyu.msgservcie.resolver;


import com.anyu.common.result.CommonResult;
import com.anyu.common.result.annotation.QueryResolver;
import com.anyu.common.result.type.MsgResultType;
import com.anyu.msgservcie.entity.MessageInput;
import com.anyu.msgservcie.service.MessageService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@QueryResolver("msgMutationResolver")
public class MsgMutationResolver implements GraphQLMutationResolver {

    @Resource
    private MessageService messageService;

    public CommonResult sendMsg(MessageInput input) {
        if (messageService.sendMsg(input)) {
            return CommonResult.with(MsgResultType.SEND_SUCCESS);
        }
        return CommonResult.with(MsgResultType.SEND_ERROR);
    }
}
