package com.newbig.im.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "10000"));

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ClientInitializer());
            startConnection(b);
    }

    private static void startConnection(Bootstrap b) {
        b.connect(HOST, PORT)
            .addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    //init registry
                    log.info("Client connected Gate Successed...");
                } else {
                    log.error("Client connected Gate Failed");
                }
            });
    }
}

