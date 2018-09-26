package com.newbig.im.core.tcp.handler;

import com.newbig.im.core.NettyServerContext;
import com.newbig.im.service.RedisService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("idleServerHandler")
@ChannelHandler.Sharable
public class IdleServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;

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
                    // 发现连接是闲置状态就关闭它
//                final Long logoutUserId = NettyServerContext.INSTANCE.channelInactive(ctx.channel());
//                if(null != logoutUserId) {
//                    redisService.deleteUserHost(logoutUserId);
//                }
//                log.info("Idle client:{}, logout userId:{}", ctx.channel().id(), logoutUserId);
//                ctx.close();
            } else {
                super.userEventTriggered(ctx, evt);
            }
            log.debug(ctx.channel().remoteAddress()+"超时类型：" + type);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
