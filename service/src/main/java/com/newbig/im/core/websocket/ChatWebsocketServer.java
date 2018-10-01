package com.newbig.im.core.websocket;

import io.netty.bootstrap.ServerBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ChatWebsocketServer {
    @Value("${server.websocket.port}")
    private Integer websocketPort;
    @Autowired
    @Qualifier("websocketBootstrap")
    private ServerBootstrap websocketBootstrap;

    public void start() throws InterruptedException {
        log.info("begin start chat websocket server, websocket port: {}", websocketPort);
        websocketBootstrap
                .bind(websocketPort).sync().channel().closeFuture().sync().channel();
    }
}
