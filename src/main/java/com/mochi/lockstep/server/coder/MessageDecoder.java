package com.mochi.lockstep.server.coder;

import com.mochi.lockstep.communicate.message.Operation;
import com.mochi.lockstep.communicate.message.OperationMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */
@ChannelHandler.Sharable
public class MessageDecoder extends MessageToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        String stringMessage = (String) o;
        Operation operation = new Operation(System.currentTimeMillis(), stringMessage);
        OperationMessage operationMessage = new OperationMessage("defRoom", channelHandlerContext.channel().remoteAddress().toString());
        operationMessage.getOperations().add(operation);
        list.add(operationMessage);
    }
}
