package com.newbig.im.core.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WebsocketBeanConfig {
    @Autowired
    @Qualifier("websocketInitializer")
    private WebsocketInitializer websocketInitializer;
    @Bean(name = "chatWebsocketServer")
    private ChatWebsocketServer chatWebsocketServer(){
        return new ChatWebsocketServer();
    }

    @Bean(name = "websocketBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(websocketBossGroup(), websocketWorkerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(websocketInitializer);
        Map<ChannelOption<?>, Object> websocketChannelOptions = websocketChannelOptions();
        Set<ChannelOption<?>> keySet = websocketChannelOptions.keySet();
        for (@SuppressWarnings("rawtypes") ChannelOption option : keySet) {
            b.option(option, websocketChannelOptions.get(option));
        }
        return b;
    }

    @Bean(name = "websocketChannelOptions")
    public Map<ChannelOption<?>, Object> websocketChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<>();
//        options.put(ChannelOption.SO_KEEPALIVE, true);
        options.put(ChannelOption.SO_BACKLOG, 100);
        return options;
    }

    @Bean(name = "websocketBossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup websocketBossGroup() {
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WEB_BOSS_" + index.incrementAndGet());
            }
        });
    }

    @Bean(name = "websocketWorkerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup websocketWorkerGroup() {
        return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 5, new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "WEB_WORK_" + index.incrementAndGet());
            }
        });
    }
}
