package com.newbig.im.service.rpcconfig;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.google.common.collect.Maps;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.service.HelloService;
import com.newbig.im.service.impl.HelloServiceImpl;

import java.util.Map;

public class RPCService {
    //key: InterfaceId_ip value: ConsumerConfig
    private static final Map<String,ConsumerConfig> CONSUMER_CONFIG_MAP = Maps.newConcurrentMap();
    private static final String HelloServiceName=HelloService.class.getName();

    public static HelloService getHelloService(String host){
        ConsumerConfig consumerConfig = CONSUMER_CONFIG_MAP.get(HelloServiceName);
        consumerConfig.setDirectUrl(StringUtil.concat("bolt://"+host+":12200"));
        HelloService helloService = (HelloService)consumerConfig.refer();
        return helloService;

    }
    public static void registConsumer(){
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setProtocol("bolt") // 指定协议
                .setConnectTimeout(10 * 1000);
        CONSUMER_CONFIG_MAP.put(HelloServiceName,consumerConfig );
    }
    public static void publishService(){
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("zookeeper")
                .setAddress("192.168.10.103:2181");
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // 设置一个协议，默认bolt
                .setPort(12200) // 设置一个端口，默认12200
                .setDaemon(false); // 非守护线程

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setRef(new HelloServiceImpl()) // 指定实现
                .setRegistry(registryConfig)
                .setServer(serverConfig); // 指定服务端

        providerConfig.export(); // 发布服务
    }

    public static void initRPC(){
        publishService();
        registConsumer();
    }

}
