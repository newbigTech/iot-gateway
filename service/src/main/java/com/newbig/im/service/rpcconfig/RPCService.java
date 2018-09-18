package com.newbig.im.service.rpcconfig;

import com.alipay.sofa.rpc.config.*;
import com.google.common.collect.Maps;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.service.HelloService;
import com.newbig.im.service.impl.HelloServiceImpl;

import java.util.Map;

import static com.alipay.sofa.rpc.registry.zk.ZookeeperRegistry.PARAM_CREATE_EPHEMERAL;

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
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("consul")
                .setAddress("120.55.45.175:8501");
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setProtocol("bolt") // 指定协议
                .setRegistry(registryConfig)
                .setConnectTimeout(10 * 1000);
        CONSUMER_CONFIG_MAP.put(HelloServiceName,consumerConfig );
    }
    public static void publishService(){
        RegistryConfig registryConfig = new RegistryConfig()
                .setProtocol("consul")
                .setAddress("120.55.45.175:8501")
                .setParameter(PARAM_CREATE_EPHEMERAL,"true");
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
