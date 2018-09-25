package com.newbig.im.core.consul;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.async.ConsulResponseCallback;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;
import io.shardingsphere.core.event.ShardingEventBusInstance;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigurationListenerManager {
    private static KeyValueClient keyValueClient;
    private static HealthClient healthClient;
    private static AgentClient agentClient;

    static {
        Consul consul = Consul.builder()
                .withConnectTimeoutMillis(3000)
                .withPing(true)
                .withReadTimeoutMillis(2000)
                .withWriteTimeoutMillis(2000)
                .withHostAndPort(HostAndPort.fromParts("127.0.0.1", 8500)).build();
        keyValueClient = consul.keyValueClient();
        healthClient = consul.healthClient();
        agentClient = consul.agentClient();
    }
    public static void watchSharding() {
        final ConsulResponseCallback<Optional<Value>> callback = new ConsulResponseCallback<Optional<Value>>() {
            AtomicReference<BigInteger> index = new AtomicReference<>(null);
            @Override
            public void onComplete(ConsulResponse<Optional<Value>> consulResponse) {
                if (consulResponse.getResponse().isPresent()) {
                    Value v = consulResponse.getResponse().get();
                    String newconfig = new String(Base64.getDecoder().decode(v.getValue().get()));
//                    Map<String, DataSource> dataSourceMap = dataSourceService.getAvailableDataSources();
//                    ShardingConfigurationEventBusEvent shardingEvent = new ShardingConfigurationEventBusEvent(dataSourceMap,
//                            new ShardingRule(dataSourceService.getAvailableShardingRuleConfiguration(), dataSourceMap.keySet()), configService.loadShardingProperties());
                    ShardingEventBusInstance.getInstance().post(newconfig);
                }
                index.set(consulResponse.getIndex());
                watch();
            }

            void watch() {
                keyValueClient.getValue("student", QueryOptions.blockSeconds(5, index.get()).build(), this);
            }

            @Override
            public void onFailure(Throwable throwable) {
//                System.out.println("Error encountered");
//                watch();
            }
        };

        keyValueClient.getValue("/", QueryOptions.blockSeconds(5, new BigInteger("0")).build(), callback);
    }
}
