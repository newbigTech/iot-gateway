//package com.newbig.im.core.client;
//
//import com.google.protobuf.Message;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.util.concurrent.atomic.AtomicLong;
//
////import com.newbig.im.core.ReplyUtils;
////import com.newbig.im.core.proto.MessageBody;
////import com.newbig.im.core.proto.SERVER_CODE;
////import com.newbig.im.core.proto.SocketMessage;
//
//@Slf4j
//public class ClientHandler extends SimpleChannelInboundHandler<Message> {
//    public static ChannelHandlerContext gateClientConnection;
//    public static AtomicLong increased = new AtomicLong(1);
//    private static int count = 0;
//    String userId = "";
//    boolean verify = true;
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws IOException {
////        final SocketMessage msg = SocketMessage.newBuilder()
////                .setCode(SERVER_CODE.NOTIFY_CODE)
////
////                .setDomain(1)
////                .setOpcode(0)
////                .setBody(MessageBody.newBuilder().setMessage(ByteString.copyFrom("tttt", Charsets.UTF_8)))
////                .build();
////        ReplyUtils.reply(ctx, msg);
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message msg) throws Exception {
//        log.info("received message: {}", msg.toString());
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        //ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        // Close the connection when an exception is raised.
//        cause.printStackTrace();
//        ctx.close();
//    }
//}
