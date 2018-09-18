//package com.newbig.im.core;
//
//import com.google.protobuf.ByteString;
//import com.newbig.im.core.proto.MessageBody;
//import com.newbig.im.core.proto.SERVER_CODE;
//import com.newbig.im.core.proto.SocketMessage;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelHandlerContext;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ReplyUtils {
//    /**
//     * 通知消息
//     *
//     * @param channel
//     * @param socketMessage
//     */
//    static void reply(Channel channel, SocketMessage socketMessage) {
//        ChannelFuture future = channel.writeAndFlush(socketMessage);
//
//        if (future.isSuccess()) {
//            // 成功
//        } else if (future.cause() != null) {
//            // 异常
//            log.error("{}", future.cause());
//        } else {
//            // 取消
//            //  throw new Exception("客户端取消执行");
//            log.error("Client cancel receive message.");
//        }
//    }
//
//    /**
//     * 回复消息到客户端
//     *
//     * @param channelHandlerContext
//     * @param socketMessage
//     */
//    public static void reply(ChannelHandlerContext channelHandlerContext, SocketMessage socketMessage) {
//        reply(channelHandlerContext.channel(), socketMessage);
//    }
//
//    /**
//     * 回复消息到客户端
//     *
//     * @param channelHandlerContext
//     * @param domain
//     * @param opcode
//     */
//    public static void reply(ChannelHandlerContext channelHandlerContext, int domain, int opcode) {
//        reply(channelHandlerContext, SocketMessage.newBuilder()
//                .setCode(SERVER_CODE.RESONSE_CODE)
//                .setDomain(domain)
//                .setOpcode(opcode)
//                .build());
//    }
//
//    /**
//     * 回复消息到客户端
//     *
//     * @param channelHandlerContext
//     * @param domain
//     * @param opcode
//     * @param message
//     */
//    public static void reply(ChannelHandlerContext channelHandlerContext, int domain, int opcode, ByteString message) {
//        reply(channelHandlerContext.channel(), SocketMessage.newBuilder()
//                .setCode(SERVER_CODE.RESONSE_CODE)
//                .setDomain(domain)
//                .setOpcode(opcode)
//                .setBody(MessageBody.newBuilder().setMessage(message))
//                .build());
//    }
//
//    /**
//     * 回复消息到客户端
//     *
//     * @param channelHandlerContext
//     * @param domain
//     * @param opcode
//     */
//    public static void reply(ChannelHandlerContext channelHandlerContext, int domain, int opcode, int error) {
//        reply(channelHandlerContext.channel(), SocketMessage.newBuilder()
//                .setCode(SERVER_CODE.RESONSE_CODE)
//                .setDomain(domain)
//                .setOpcode(opcode)
//                .setBody(MessageBody.newBuilder().setError(error))
//                .build());
//    }
//
//    /**
//     * 回复消息到客户端
//     *
//     * @param channelHandlerContext
//     * @param domain
//     * @param opcode
//     * @param message
//     */
//    public static void reply(ChannelHandlerContext channelHandlerContext, int domain, int opcode, int error, ByteString message) {
//        reply(channelHandlerContext.channel(), SocketMessage.newBuilder()
//                .setCode(SERVER_CODE.RESONSE_CODE)
//                .setDomain(domain)
//                .setOpcode(opcode)
//                .setBody(MessageBody.newBuilder().setError(error).setMessage(message))
//                .build());
//    }
//}
