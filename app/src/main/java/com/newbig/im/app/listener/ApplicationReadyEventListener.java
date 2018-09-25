package com.newbig.im.app.listener;

import com.newbig.im.core.tcp.ChatTcpServer;
import com.newbig.im.core.websocket.ChatWebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Value("${server.tcp.port}")
    private Integer tcpPort;
    @Value("${server.websocket.port}")
    private Integer websocketPort;
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        applicationReadyEvent.getApplicationContext();
        final ChatTcpServer chatTcpServer = new ChatTcpServer(tcpPort);
        final ChatWebsocketServer chatWebsocketServer = new ChatWebsocketServer(websocketPort);
        try {
            chatTcpServer.init();
            chatTcpServer.start();
            chatWebsocketServer.init();
            chatWebsocketServer.start();
            log.info("chat server start success, tcp port: {}, websocket port: {}", tcpPort, websocketPort);
        } catch (InterruptedException e) {
            log.error("chat server start failed, {}", ExceptionUtils.getStackTrace(e));
            Thread.currentThread().interrupt();
        }
        // 注册进程钩子，在JVM进程关闭前释放资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            chatTcpServer.shutdown();
            chatWebsocketServer.shutdown();
            log.warn(">>>>>>>>>> chat server shutdown");
            System.exit(0);
        }));
    }
}
