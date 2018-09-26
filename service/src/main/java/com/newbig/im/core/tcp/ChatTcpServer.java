package com.newbig.im.core.tcp;

import io.netty.bootstrap.ServerBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatTcpServer {
    @Value("${server.tcp.port}")
    private Integer tcpPort;
    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    public void start() throws InterruptedException {
        serverBootstrap
            .bind(tcpPort).sync().channel().closeFuture().sync().channel();
        log.info("chat server start successfully, tcp port: {}", tcpPort);
    }




}
