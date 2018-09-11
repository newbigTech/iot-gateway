package com.newbig.im.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ChatServer{
    private ScheduledExecutorService executorService;
    private int port;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;
    private ChannelFuture cf;
    private ServerBootstrap b;

    public ChatServer(int port) {
        this.port = port;
        executorService = Executors.newScheduledThreadPool(2);
    }

    public void init() {
        bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "BOSS_" + index.incrementAndGet());
            }
        });
        workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 10, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WORK_" + index.incrementAndGet());
            }
        });

        b = new ServerBootstrap();
    }

    public void start() {
        b.group(bossGroup, workGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .localAddress(new InetSocketAddress(port))
            .childHandler(new ServerInitializer());

        try {
            cf = b.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) cf.channel().localAddress();
            log.info("Server start success, port is:{}", addr.getPort());
        } catch (InterruptedException e) {
            log.error("Server start failed, {}", ExceptionUtils.getStackTrace(e));
        }
    }


    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
        }
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
