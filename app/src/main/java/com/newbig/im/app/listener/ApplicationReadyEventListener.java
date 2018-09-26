package com.newbig.im.app.listener;

import com.newbig.im.core.tcp.ChatTcpServer;
import com.newbig.im.core.websocket.ChatWebsocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    @Qualifier("chatTcpServer")
    private ChatTcpServer chatTcpServer;
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        try {
            chatTcpServer.start();
//            chatWebsocketServer.start();
        } catch (InterruptedException e) {
            log.error("chat server start failed, {}", ExceptionUtils.getStackTrace(e));
            Thread.currentThread().interrupt();
        }
    }

}
