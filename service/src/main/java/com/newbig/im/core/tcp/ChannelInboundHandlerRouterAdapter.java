package com.newbig.im.core.tcp;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.rpc.common.utils.NetUtils;
import com.newbig.im.core.NettyServerContext;
import com.newbig.im.init.SpringContext;
import com.newbig.im.model.proto.ChatMsg;
import com.newbig.im.model.proto.IMMessage;
import com.newbig.im.model.proto.LoginMsg;
import com.newbig.im.service.ChatRpcService;
import com.newbig.im.init.RPCConfig;
import com.newbig.im.service.RedisService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelInboundHandlerRouterAdapter extends ChannelInboundHandlerAdapter {
    private static RedisService redisService = SpringContext.context.getBean(RedisService.class);
    private static String localIpV4;
    static {
        localIpV4 = NetUtils.getLocalIpv4();
    }
    // 调用异常的处理
    @Override
    public void channelActive(ChannelHandlerContext ctx){
//        ReplyUtils.reply(ctx, ProtoBuffStatic.LOGIN_SUCCESS);
    }
    /**
     * 当客户端发送数据到服务器会触发此函数
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        final IMMessage immsg = (IMMessage) msg;
        switch (immsg.getType()){
            case 0:{
                //心跳消息
                ctx.writeAndFlush(null);
                ctx.fireChannelRead(msg);
                break;
            }
            case 1:{
                //登录
                try {
                    LoginMsg login = JSON.parseObject(immsg.getBody(), LoginMsg.class);
                    NettyServerContext.INSTANCE.userJoin(ctx.channel(), login.getId());
                    redisService.addUserHost(login.getId(), localIpV4);
                    //注册到 redis
                }catch (Exception e){
                    log.error(e.getMessage());
                    ctx.close();
                }
                break;
            }
            case 2:{
                //聊天消息
                try {
                    ChatMsg chatMsg = JSON.parseObject(immsg.getBody(), ChatMsg.class);
                    //如果连接的是同一台机器
                    Channel channel = NettyServerContext.INSTANCE.getUserChannel(chatMsg.getTo());
                    if(null != channel){
                        IMMessage imMessage = IMMessage.buildChatMsg(chatMsg);
                        ChannelFuture future =channel.writeAndFlush(imMessage);
                        if (future.isSuccess()) {
                            // 成功
                        } else if (future.cause() != null) {
                            // 异常
                            log.error("{}", future.cause());
                        } else {
                            // 取消
                            //  throw new Exception("客户端取消执行");
                            log.error("Client cancel receive message.");
                        }
                    }else{
                        //去redis里查找to user 在哪台机器上
                        Object host = redisService.getUserHost(chatMsg.getTo());
                        if(null != host) {
                            ChatRpcService chatRpcService = RPCConfig.getChatRpcService(host);
                            chatRpcService.sendChatMsg(chatMsg);
                        }
                        //TODO 用户不在线
                    }
                }catch (Exception e){
                    log.error(e.getMessage());
                    ctx.close();
                }
                break;
            }
            case 3:{
                //
                break;
            }
            default:
                break;
        }
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
                final Long logoutUserId = NettyServerContext.INSTANCE.channelInactive(ctx.channel());
                if(null != logoutUserId) {
                    redisService.deleteUserHost(logoutUserId);
                }
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
