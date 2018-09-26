package com.newbig.im.core.client.handler;

import com.alibaba.fastjson.JSON;
import com.newbig.im.model.proto.Command.CommandType;
import com.newbig.im.model.proto.LoginMsg;
import com.newbig.im.model.proto.Message.MessageBase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogicClientHandler extends SimpleChannelInboundHandler<MessageBase> {

    private final static String CLIENTID = "123456789";

    // 连接成功后，向server发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MessageBase.Builder authMsg = MessageBase.newBuilder();
        authMsg.setClientId(CLIENTID);
        authMsg.setCmd(CommandType.AUTH);
        LoginMsg loginMsg = new LoginMsg();
        loginMsg.setId(123L);
        loginMsg.setType(1);
        loginMsg.setToken("sss");
        authMsg.setData(JSON.toJSONString(loginMsg));

        ctx.writeAndFlush(authMsg.build());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("连接断开 ");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBase msg) throws Exception {
        if(msg.getCmd().equals(CommandType.AUTH_BACK)){
            log.debug("验证成功");
            ctx.writeAndFlush(
                    MessageBase.newBuilder()
                            .setClientId(CLIENTID)
                            .setCmd(CommandType.PUSH_DATA)
                            .setData("This is upload data")
                            .build()
            );

        }else if(msg.getCmd().equals(CommandType.PING)){
            //接收到server发送的ping指令
            log.info(msg.getData());

        }else if(msg.getCmd().equals(CommandType.PONG)){
            //接收到server发送的pong指令
            log.info(msg.getData());

        }else if(msg.getCmd().equals(CommandType.PUSH_DATA)){
            //接收到server推送数据
            log.info(msg.getData());
            Thread.sleep(2000);
            ctx.writeAndFlush(MessageBase.newBuilder()
                    .setClientId(CLIENTID)
                    .setCmd(CommandType.PUSH_DATA)
                    .setData("This is upload data")
                    .build());

        }else if(msg.getCmd().equals(CommandType.PUSH_DATA_BACK)){
            //接收到server返回数据
            log.info(msg.getData());

        }else{
            log.info(msg.getData());
        }
    }
}
