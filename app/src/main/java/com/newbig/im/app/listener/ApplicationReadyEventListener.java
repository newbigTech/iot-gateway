package com.newbig.im.app.listener;

import com.newbig.im.core.tcp.ChatTcpServer;
import com.newbig.im.core.websocket.ChatWebsocketServer;
import com.newbig.im.init.RPCConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Resource(name = "chatTcpServer")
    private ChatTcpServer chatTcpServer;
    @Resource(name = "chatWebsocketServer")
    private ChatWebsocketServer chatWebsocketServer;
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        RPCConfig.getOnlineServer();
            new Thread(()-> {
                try {
                    chatTcpServer.start();
                } catch (InterruptedException e) {
                    log.error("chat server start failed, {}", ExceptionUtils.getStackTrace(e));
                    Thread.currentThread().interrupt();
                }
            }).start();
            new Thread(()-> {
                try {
                    chatWebsocketServer.start();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
    }

}
