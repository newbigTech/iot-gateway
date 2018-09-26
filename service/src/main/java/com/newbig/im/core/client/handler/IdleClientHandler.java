package com.newbig.im.core.client.handler;

import com.google.protobuf.Message;
import com.newbig.im.core.client.NettyClient;
import com.newbig.im.model.proto.Command.CommandType;
import com.newbig.im.model.proto.Message.MessageBase;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class IdleClientHandler extends SimpleChannelInboundHandler<Message> {

    private NettyClient nettyClient;
    private int heartbeatCount = 0;
    private final static String CLIENTID = "123456789";

    /**
     * @param nettyClient
     */
    public IdleClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle";
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
            }
            log.debug(ctx.channel().remoteAddress() + "超时类型：" + type);
            sendPingMsg(ctx);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 发送ping消息
     * @param context
     */
    protected void sendPingMsg(ChannelHandlerContext context) {
        context.writeAndFlush(
                         MessageBase.newBuilder()
                        .setClientId(CLIENTID)
                        .setCmd(CommandType.PING)
                        .setData("This is a ping msg")
                        .build()
        );
        heartbeatCount++;
        log.info("NettyClient sent ping msg to " + context.channel().remoteAddress() + ", count: " + heartbeatCount);
    }

    /**
     * 处理断开重连
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> nettyClient.doConnect(new Bootstrap(), eventLoop), 10L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

    }
}

