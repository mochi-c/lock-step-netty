package com.mochi.lockstep.server.coder;

import com.mochi.lockstep.communicate.message.OperationMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-29
 * version: 1.0
 */

//public class MessageEncoder extends MessageToMessageEncoder<OperationMessage> {
//
//    @Override
//    protected void encode(ChannelHandlerContext channelHandlerContext, OperationMessage operationMessage, List<Object> list) throws Exception {
//        list.add(operationMessage.getOperations());
//    }
//}

@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        list.add(o.toString());
    }
}
