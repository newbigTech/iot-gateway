package com.newbig.im.core.tcp.handler;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.rpc.common.utils.NetUtils;
import com.newbig.im.core.NettyServerContext;
import com.newbig.im.model.proto.Command.CommandType;
import com.newbig.im.model.proto.LoginMsg;
import com.newbig.im.model.proto.Message.MessageBase;
import com.newbig.im.service.RedisService;
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
@Qualifier("authServerHandler")
@ChannelHandler.Sharable
public class AuthServerHandler extends ChannelInboundHandlerAdapter {
    private static String localIpV4;
    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;
    static {
        localIpV4 = NetUtils.getLocalIpv4();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        MessageBase msgBase = (MessageBase)msg;
        String clientId = msgBase.getClientId();
        /*认证处理*/
        if(msgBase.getCmd().equals(CommandType.AUTH)){
            log.info("我是验证处理逻辑");
            LoginMsg login = JSON.parseObject(msgBase.getData(), LoginMsg.class);
            NettyServerContext.INSTANCE.userJoin(ctx.channel(), login.getId());
            redisService.addUserHost(login.getId(), localIpV4);
            ctx.writeAndFlush(createData(clientId, CommandType.AUTH_BACK, "This is AUTH_BACK data").build());
        }else if(msgBase.getCmd().equals(CommandType.PING)){
            //处理ping消息
            ctx.writeAndFlush(createData(clientId, CommandType.PONG, "This is pong data").build());

        }else{
            if(ctx.channel().isOpen()){
                //触发下一个handler
                ctx.fireChannelRead(msg);
                log.info("我进业务入处理逻辑");
            }
        }
        ReferenceCountUtil.release(msg);
    }
    private MessageBase.Builder createData(String clientId, CommandType cmd,String data){
        MessageBase.Builder msg = MessageBase.newBuilder();
        msg.setClientId(clientId);
        msg.setCmd(cmd);
        msg.setData(data);
        return msg;
    }
}

