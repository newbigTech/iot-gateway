package com.newbig.im.app.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.net.HostAndPort;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.init.RPCConfig;
import com.newbig.im.init.ServerContext;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class ConsulConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        String serviceTag=environment.getProperty("service.tag");
        Preconditions.checkArgument(StringUtil.isNotBlank(appName),"spring.application.name不能为空");
        Preconditions.checkArgument(StringUtil.isNotBlank(serviceTag),"service.tag 不能为空");
        log.info("******** appName: {} , serviceTag : {} **************",appName,serviceTag);
        if(StringUtil.equals(serviceTag,"local")){
            return;
        }
        String host =environment.getProperty("consul.host");
        String port =environment.getProperty("consul.port");
        String consulShardingKey =environment.getProperty("consul.sharding.key");
        Preconditions.checkArgument(StringUtil.isNotBlank(host),"consul.host must be set");
        Preconditions.checkArgument(StringUtil.isNumeric(port),"consul.port must be number");
        Preconditions.checkArgument(StringUtil.isNotBlank(consulShardingKey),"consul.sharding.key can not be blank");
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(createPropertySource(host,Integer.valueOf(port),consulShardingKey));
        //初始化sofaRPC
        RPCConfig.initRPC(host,Integer.valueOf(port));
    }

    /**
     * 从consul获取最新配置
     * @param host
     * @param port
     * @param consulKey
     * @return
     */
    private PropertySource createPropertySource(String host,Integer port,String consulKey) {
        Map<String,Object> source = Maps.newHashMap();
        Optional<String> value = buildKeyValueClient(host,port).getValueAsString(consulKey);
        if(value.isPresent()) {
            Properties properties = new Properties();
            try {
                properties.load(new StringReader(value.get()));
            } catch (IOException e) {
                log.error(ExceptionUtils.getStackTrace(e));
                System.exit(0);
            }
            Enumeration enum1 = properties.propertyNames();
            while (enum1.hasMoreElements()) {
                String strKey = (String) enum1.nextElement();
                String strValue = properties.getProperty(strKey);
                source.put(strKey, strValue);
            }
            return new MapPropertySource("ConsulConfig", source);
        }else{
            log.error("get consul config {} failed",consulKey);
            System.exit(0);
        }
        return null;
    }

    /**
     * 这里用到consul的时候spring还没有初始化完成,不能使用autowire
     * @param host
     * @param port
     * @return
     */
    private KeyValueClient buildKeyValueClient(String host, Integer port){
        Consul consul = Consul.builder()
                .withConnectTimeoutMillis(3000)
                .withPing(false)
                .withReadTimeoutMillis(2000)
                .withWriteTimeoutMillis(2000)
                .withHostAndPort(HostAndPort.fromParts(host, port)).build();
        ServerContext.consul = consul;
        return consul.keyValueClient();
    }
}