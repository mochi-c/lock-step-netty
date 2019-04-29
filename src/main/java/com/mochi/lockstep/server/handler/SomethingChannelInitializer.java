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

import com.mochi.lockstep.server.coder.MessageDecoder;
import com.mochi.lockstep.server.coder.MessageEncoder;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Channel Initializer
 *
 * @author heks
 */
@Component
@Qualifier("somethingChannelInitializer")
public class SomethingChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();
    private static final MessageDecoder MESSAGE_DECODER = new MessageDecoder();
    private static final MessageEncoder MESSAGE_ENCODER = new MessageEncoder();

    @Autowired
    @Qualifier("handleMsgHandler")
    private ChannelInboundHandlerAdapter handleMsgHandler;

    @Autowired
    private CheckStringHandler checkStringHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // Add the text line codec combination first,
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024*1024, Delimiters.lineDelimiter()));

        // the encoder and decoder are static as these are sharable
        pipeline.addLast(DECODER);
        pipeline.addLast(MESSAGE_DECODER);
        pipeline.addFirst(MESSAGE_ENCODER);
        pipeline.addFirst(ENCODER);


        // service handler
        //pipeline.addLast(checkStringHandler);
        pipeline.addLast(handleMsgHandler);
    }
}
