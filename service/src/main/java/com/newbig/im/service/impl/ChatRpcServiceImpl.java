package com.newbig.im.service.impl;

import com.newbig.im.core.NettyServerContext;
import com.newbig.im.model.proto.ChatMsg;
import com.newbig.im.model.proto.IMMessage;
import com.newbig.im.service.ChatRpcService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatRpcServiceImpl implements ChatRpcService {

    @Override
    public Boolean sendChatMsg(ChatMsg chatMsg) {
        IMMessage imMessage = IMMessage.buildChatMsg(chatMsg);
        Channel channel = NettyServerContext.INSTANCE.getUserChannel(chatMsg.getTo());
        ChannelFuture future =channel.writeAndFlush(imMessage);
        if (future.isSuccess()) {
            return true;
            // 成功
        } else if (future.cause() != null) {
            // 异常
            log.error("{}", future.cause());
        } else {
            // 取消
            log.error("NettyClient cancel receive message.");
        }
        return false;

    }
}