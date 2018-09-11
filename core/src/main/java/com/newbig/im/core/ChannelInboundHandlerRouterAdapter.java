package com.newbig.im.core;


import com.newbig.im.core.proto.SYSTEM_FORWARD_CODE;
import com.newbig.im.core.proto.SocketASK;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelInboundHandlerRouterAdapter extends ChannelInboundHandlerAdapter {
    // 调用异常的处理
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ReplyUtils.reply(ctx, ProtoBuffStatic.LOGIN_SUCCESS);
    }
    /**
     * 当客户端发送数据到服务器会触发此函数
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info(msg.toString());
        final SocketASK ask = (SocketASK) msg;
        if (ask.getForward() >= SYSTEM_FORWARD_CODE.BOUNDARY_VALUE) {
//            onRequest(ctx, ask);
            return;
        }

        if (ask.getForward() == SYSTEM_FORWARD_CODE.HEARTBEAT_VALUE) {
            // forward = 0 表示心跳
            if (log.isDebugEnabled()) {
                log.debug("client id:{}, heartbeat ", ctx.channel().id());
            }
            ctx.writeAndFlush(ProtoBuffStatic.HEARTBEAT);
            ctx.fireChannelRead(ask);
            return;
        }
        NettyServerContext.INSTANCE.userJoin(ctx.channel(),111L);
    }

    // 调用异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.error("{}", cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.ALL_IDLE) {
                // 发现连接是闲置状态就关闭它
                final long logoutUserId = NettyServerContext.INSTANCE.channelInactive(ctx.channel());
                log.info("Idle client:{}, logout userId:{}", ctx.channel().id(), logoutUserId);
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    /**
     * 当客户端断开连接的时候触发函数
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final long logoutUserId = NettyServerContext.INSTANCE.channelInactive(ctx.channel());
        if (log.isDebugEnabled()) {
            log.debug("client:{}, logout userId:{}", ctx.channel().id(), logoutUserId);
        }
    }
}
