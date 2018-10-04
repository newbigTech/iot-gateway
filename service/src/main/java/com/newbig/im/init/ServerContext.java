package com.newbig.im.init;

import com.orbitz.consul.Consul;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ServerContext {
    public static ApplicationContext context;
    public static List<String> onlineServers;
    public static Consul consul;

}
