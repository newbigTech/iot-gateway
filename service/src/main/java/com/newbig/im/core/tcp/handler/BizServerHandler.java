package com.newbig.im.core.tcp.handler;

import com.newbig.im.core.NettyServerContext;
import com.newbig.im.model.proto.Command.CommandType;
import com.newbig.im.model.proto.Message.MessageBase;
import com.newbig.im.service.RedisService;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("bizServerHandler")
@ChannelHandler.Sharable
public class BizServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;
    /**
     * 当客户端发送数据到服务器会触发此函数
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageBase msgBase = (MessageBase)msg;
        log.info(msgBase.getData());

        log.info("......{}",NettyServerContext.INSTANCE.getLoginUserId(ctx.channel().id()));
        ChannelFuture cf = ctx.writeAndFlush(
                MessageBase.newBuilder()
                        .setClientId(msgBase.getClientId())
                        .setCmd(CommandType.UPLOAD_DATA_BACK)
                        .setData("This is upload data back msg")
                        .build()
        );
        /* 上一条消息发送成功后，立马推送一条消息 */
        cf.addListener(future -> {
            if (future.isSuccess()){
                ctx.writeAndFlush(
                        MessageBase.newBuilder()
                                .setClientId(msgBase.getClientId())
                                .setCmd(CommandType.PUSH_DATA)
                                .setData("This is a push msg")
                                .build()
                );
            }
        });
        ReferenceCountUtil.release(msg);
    }

    // 调用异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.error("{}", cause);
    }


    /**
     * 当客户端断开连接的时候触发函数
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final Long logoutUserId = NettyServerContext.INSTANCE.channelInactive(ctx.channel());
        if(null != logoutUserId) {
            redisService.deleteUserHost(logoutUserId);
        }
        if (log.isDebugEnabled()) {
            log.debug("client:{}, logout userId:{}", ctx.channel().id(), logoutUserId);
        }
    }
}
