package com.newbig.im.app.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.core.tcp.ChatTcpServer;
import com.newbig.im.core.websocket.ChatWebsocketServer;
import com.newbig.im.init.ServerContext;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.cache.KVCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.model.kv.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Resource(name = "chatTcpServer")
    private ChatTcpServer chatTcpServer;
    @Resource(name = "chatWebsocketServer")
    private ChatWebsocketServer chatWebsocketServer;
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        Thread tcpServerThread = new Thread(()-> {
            try {
                chatTcpServer.start();
            } catch (InterruptedException e) {
                log.error("chat tcp server start failed, {}", ExceptionUtils.getStackTrace(e));
                Thread.currentThread().interrupt();
            }
        });
        Thread websocketServer =new Thread(()-> {
            try {
                chatWebsocketServer.start();
            } catch (InterruptedException e) {
                log.error("chat websocket server start failed, {}", ExceptionUtils.getStackTrace(e));
                Thread.currentThread().interrupt();
            }
        });
        Thread serviceListThread = new Thread(() ->{
            HealthClient healthClient = ServerContext.consul.healthClient();
            String serviceName = "consul__DEFAULT";
            ServiceHealthCache svHealth = ServiceHealthCache.newCache(healthClient, serviceName);
            svHealth.addListener((Map<ServiceHealthKey, ServiceHealth> newValues) -> {
                Set<String> newServers = Sets.newHashSet();
                Set<ServiceHealthKey> ss = newValues.keySet();
                for(ServiceHealthKey s: ss) {
                    log.info(s.getServiceId());
                    String host= s.getServiceId().split(":")[0];
                    newServers.add(host);
                }
                ServerContext.onlineServers = Lists.newArrayList(newServers);
            });
            svHealth.start();
        });
        Thread remotingConfigThread = new Thread(() ->{
            KeyValueClient keyValueClient = ServerContext.consul.keyValueClient();
            String key = "imremoteconfig";
            KVCache svHealth = KVCache.newCache(keyValueClient, key);
            svHealth.addListener(newValues -> { // Key changed
                for (Map.Entry<String, Value> entry : newValues.entrySet()) {
                    System.out.println(entry.getKey() + ":====:" + entry.getValue().getValueAsString().get());
                }
                //用 guava event 异步处理复杂逻辑
            });
            svHealth.start();
        });
        tcpServerThread.setName("tcpServerThread");
        tcpServerThread.start();
        websocketServer.setName("websocketServer");
        websocketServer.start();
        serviceListThread.setName("serviceListThread");
        serviceListThread.start();
        remotingConfigThread.setName("remotingConfigThread");
        remotingConfigThread.start();
    }

}
