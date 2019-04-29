package com.mochi.lockstep.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Description:
 * User: mochi.yang
 * Date: 2019-04-26
 * version: 1.0
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class CheckStringHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String originMessage = (String) msg;
        originMessage += "chekDone";
        msg = originMessage.replace("/[\\x00-\\x20]+/g", "");
        //传递给下一个handler
        super.channelRead(ctx, msg);
    }
}
