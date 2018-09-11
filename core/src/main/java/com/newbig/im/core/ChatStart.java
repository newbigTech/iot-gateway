package com.newbig.im.core;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ChatStart {

    public static void start(Integer port) {
        final ChatServer server = new ChatServer(port);
        server.init();
        server.start();
        // 注册进程钩子，在JVM进程关闭前释放资源
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.shutdown();
                log.warn(">>>>>>>>>> jvm shutdown");
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        start(10000);
    }
}
