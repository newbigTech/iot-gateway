package com.newbig.im.init;

import com.alipay.sofa.rpc.client.ProviderGroup;
import com.alipay.sofa.rpc.config.*;
import com.alipay.sofa.rpc.registry.RegistryFactory;
import com.alipay.sofa.rpc.registry.consul.ConsulRegistry;
import com.alipay.sofa.rpc.server.Server;
import com.alipay.sofa.rpc.server.ServerFactory;
import com.alipay.sofa.rpc.server.bolt.BoltServer;
import com.google.common.collect.Maps;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.service.ChatRpcService;
import com.newbig.im.service.impl.ChatRpcServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class RPCConfig {
    //key: InterfaceId_ip value: ConsumerConfig
    private static final Map<String,ConsumerConfig> CONSUMER_CONFIG_MAP = Maps.newConcurrentMap();
    private static final String HelloServiceName= ChatRpcService.class.getName();
    private static RegistryConfig registryConfig;
    public static ChatRpcService getChatRpcService(Object host){
        ConsumerConfig consumerConfig = CONSUMER_CONFIG_MAP.get(HelloServiceName);
        consumerConfig.setDirectUrl(StringUtil.concat("bolt://",host,":12200"));
        return (ChatRpcService)consumerConfig.refer();

    }
    public static void registConsumer(String consulAddress){
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("consul")
                .setAddress(consulAddress);
        ConsumerConfig<ChatRpcService> consumerConfig = new ConsumerConfig<ChatRpcService>()
                .setInterfaceId(ChatRpcService.class.getName()) // 指定接口
                .setProtocol("bolt") // 指定协议
                .setUniqueId(ChatRpcService.class.getName())
                .setApplication(new ApplicationConfig().setAppName("iot-gateway"))
                .setRegistry(registryConfig)
                .setConnectTimeout(10 * 1000);
        CONSUMER_CONFIG_MAP.put(HelloServiceName,consumerConfig );


    }
    public static void publishService(String consulAddress){
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("consul")
                .setAddress(consulAddress);
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // 设置一个协议，默认bolt
                .setPort(12200) // 设置一个端口，默认12200
                .setDaemon(false); // 非守护线程
        ProviderConfig<ChatRpcService> providerConfig = new ProviderConfig<ChatRpcService>()
                .setInterfaceId(ChatRpcService.class.getName()) // 指定接口
                .setRef(new ChatRpcServiceImpl()) // 指定实现
                .setRegistry(registryConfig)
                .setServer(serverConfig); // 指定服务端

        providerConfig.export(); // 发布服务
    }

    public static void initRPC(String host,Integer port){
        String consulAddress = StringUtil.concat(host,":",port);
        publishService(consulAddress);
        registConsumer(consulAddress);
    }
    public static void getOnlineServer(){

    }

}
