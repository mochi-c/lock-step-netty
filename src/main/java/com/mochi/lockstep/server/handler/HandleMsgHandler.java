/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mochi.lockstep.server.handler;

import com.mochi.lockstep.communicate.CommunicateRepository;
import com.mochi.lockstep.communicate.Room;
import com.mochi.lockstep.communicate.RoomRepository;
import com.mochi.lockstep.communicate.message.OperationMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * event handler to process receiving messages
 *
 * @author heks
 */
@Component
@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class HandleMsgHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Assert.notNull(this.roomRepository, "[Assertion failed] - RoomRepository is required; it must not be null");
        super.channelActive(ctx);

        //demo-默认先加到defRoom里
        Room room = roomRepository.getWithCreate("defRoom");

        if (log.isDebugEnabled()) {
            log.debug(ctx.channel().remoteAddress() + "");
        }
        String name = ctx.channel().remoteAddress().toString();
        room.getCommunicateRepository().put(name, ctx.channel());

        ctx.writeAndFlush("Your channel name is " + name + "\r\n");

        if (log.isDebugEnabled()) {
            log.debug("Binded Channel Count is {}", room.getCommunicateRepository().size());
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        OperationMessage operationMessage = (OperationMessage) msg;
        if (log.isDebugEnabled()) {
            log.debug(operationMessage.getName() + ":" + operationMessage.getOperations());
        }
        Room room = roomRepository.getRoom(operationMessage.getRoom());
        room.getCommunicateRepository().getCommunicate(operationMessage.getName()).putMsg(operationMessage.getOperations());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Assert.notNull(this.roomRepository, "[Assertion failed] - RoomRepository is required; it must not be null");
        Assert.notNull(ctx, "[Assertion failed] - ChannelHandlerContext is required; it must not be null");

        //demo-默认先加到defRoom里
        Room room = roomRepository.getRoom("defRoom");
        String channelKey = ctx.channel().remoteAddress().toString();
        room.getCommunicateRepository().remove(channelKey);
        if (log.isDebugEnabled()) {
            log.debug("Binded Channel Count is " + room.getCommunicateRepository().size());
        }
    }
}
