package com.newbig.im.core.websocket;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.rpc.common.utils.ExceptionUtils;
import com.alipay.sofa.rpc.common.utils.NetUtils;
import com.newbig.im.common.utils.JwtUtil;
import com.newbig.im.core.NettyServerContext;
import com.newbig.im.model.proto.AuthResultMsg;
import com.newbig.im.model.proto.Command.CommandType;
import com.newbig.im.model.proto.IMMessage;
import com.newbig.im.model.proto.LoginMsg;
import com.newbig.im.model.proto.Message.MessageBase;
import com.newbig.im.service.RedisService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static String localIpV4;
    @Autowired
    @Qualifier("redisService")
    private RedisService redisService;
    static {
        localIpV4 = NetUtils.getLocalIpv4();
    }
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg){
        System.out.println("收到消息：" + msg.text());
        IMMessage msgBase = JSON.parseObject(msg.text(),IMMessage.class);
        /*认证处理*/
        if(msgBase.getCmd().equals(CommandType.AUTH.getNumber())){
            log.info("我是验证处理逻辑");
            LoginMsg login = JSON.parseObject(msgBase.getData(), LoginMsg.class);

            Long userId = JwtUtil.getUserUuid(login.getToken());
            AuthResultMsg authResultMsg = new AuthResultMsg();
            if(userId != null) {
                NettyServerContext.INSTANCE.userJoin(ctx.channel(), userId);
                redisService.addUserHost(login.getId(), localIpV4);
                authResultMsg.setSuccess(true);
                ctx.writeAndFlush(new TextWebSocketFrame(IMMessage.buildAuthMsg(authResultMsg)));
            }else{
                authResultMsg.setSuccess(false);
                authResultMsg.setMsg("token validate failed");
                ctx.writeAndFlush(new TextWebSocketFrame(IMMessage.buildAuthMsg(authResultMsg)));
                ctx.close();
            }
        }else if(msgBase.getCmd().equals(CommandType.PING.getNumber())){
            //处理ping消息
            ctx.writeAndFlush(new TextWebSocketFrame(IMMessage.buildPong()));

        }else{
            if(ctx.channel().isOpen()){
                //触发下一个handler
                ctx.fireChannelRead(msg);
                log.info("我进业务入处理逻辑");
            }
        }
        /**
         * writeAndFlush接收的参数类型是Object类型，但是一般我们都是要传入管道中传输数据的类型，比如我们当前的demo
         * 传输的就是TextWebSocketFrame类型的数据
         */
//        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务时间：" + LocalDateTime.now()));
    }

    //每个channel都有一个唯一的id值
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        //打印出channel唯一值，asLongText方法是channel的id的全名
        System.out.println("handlerAdded：" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handlerRemoved：" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(ExceptionUtils.toString(cause));
        ctx.close();
    }
}
