package com.newbig.im.core.tcp;

import com.newbig.im.core.tcp.handler.AuthServerHandler;
import com.newbig.im.core.tcp.handler.BizServerHandler;
import com.newbig.im.core.tcp.handler.IdleServerHandler;
import com.newbig.im.model.proto.Message.MessageBase;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Qualifier("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final static int READER_IDLE_TIME_SECONDS = 20;//读操作空闲20秒
    private final static int WRITER_IDLE_TIME_SECONDS = 20;//写操作空闲20秒
    private final static int ALL_IDLE_TIME_SECONDS = 40;//读写全部空闲40秒

    @Autowired
    @Qualifier("authServerHandler")
    private AuthServerHandler authServerHandler;

    @Autowired
    @Qualifier("bizServerHandler")
    private BizServerHandler bizServerHandler;

    @Autowired
    @Qualifier("idleServerHandler")
    private IdleServerHandler idleServerHandler;

    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        p.addLast("idleStateHandler", new IdleStateHandler(READER_IDLE_TIME_SECONDS
                , WRITER_IDLE_TIME_SECONDS, ALL_IDLE_TIME_SECONDS, TimeUnit.SECONDS));
        p.addLast("idleTimeoutHandler", idleServerHandler);
        p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
        p.addLast("decoder", new ProtobufDecoder(MessageBase.getDefaultInstance()));
        p.addLast("fieldPrepender", new ProtobufVarint32LengthFieldPrepender());
        p.addLast("encoder", new ProtobufEncoder());
        p.addLast("authServerHandler", authServerHandler);
        p.addLast("bizServerHandler", bizServerHandler);
    }
}
