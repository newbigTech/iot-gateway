package com.newbig.im.core.consul;

import com.orbitz.consul.Consul;
import com.orbitz.consul.async.ConsulResponseCallback;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.net.ConnectException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * consul.exe agent -server -bootstrap -data-dir=data -bind=127.0.0.1 -client 0.0.0.0 -ui
 */
@Slf4j
public class ConsulManager {

    private static final int CONSUL_WATCH_WAIT_SECONDS = 20;
    public static Consul consul;

    public static void put(String key, String value) {
        consul.keyValueClient().putValue(key, value);
    }

    public static String getValueAsString(String key) {
        return consul.keyValueClient().getValueAsString(key).toString();
    }


    public static void watchSharding() {
        String fullKey = "imremoteconfig";
        int startRetryDelay = 10;
        ConsulResponseCallback<List<Value>> callback = new ConsulResponseCallback<List<Value>>() {
            AtomicReference<BigInteger> index = new AtomicReference<>(new BigInteger("0"));
            int currentRetryDelay = startRetryDelay;
            boolean previouslyDeleted = false;

            @Override
            public void onComplete(ConsulResponse<List<Value>> consulResponse) {
                // successful request, reset delay
                currentRetryDelay = startRetryDelay;
                if (index.get() != null && !index.get().equals(consulResponse.getIndex())) {
                    if (consulResponse.getResponse() != null && !consulResponse.getResponse().isEmpty()) {
                        for (Value v : consulResponse.getResponse()) {
                            Optional<String> valueOpt = v.getValueAsString();
                            String newKey = v.getKey();
                            if (valueOpt.isPresent()) {
                                log.info("Consul watch callback for key " + (newKey) +
                                        " invoked. " + "New value: " + valueOpt.get());
//                                configurationDispatcher.notifyChange(parseKeyNameFromConsul(newKey), valueOpt.get());
                                previouslyDeleted = false;
                            } else {
                                log.info("Consul watch callback for key " + (newKey) +
                                        " invoked. No value present, fallback to other configuration sources.");
//
                            }
                        }
                    } else if (!previouslyDeleted) {
                        log.info("Consul watch callback for key " + fullKey +
                                " invoked. No value present, fallback to other configuration sources.");
//                        ConfigurationUtil configurationUtil = ConfigurationUtil.getInstance();
//                        String fallbackConfig = configurationUtil.get(key).orElse(null);
//                        if (fallbackConfig != null) {
//                            configurationDispatcher.notifyChange(key, fallbackConfig);
//                        }
                        previouslyDeleted = true;
                    }
                }
                index.set(consulResponse.getIndex());
                watch();
            }

            void watch() {
                consul.keyValueClient().getValues(fullKey,
                        QueryOptions.blockSeconds(CONSUL_WATCH_WAIT_SECONDS, index.get()).build(),
                        this);
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (throwable instanceof ConnectException) {
                    try {
                        Thread.sleep(currentRetryDelay);
                    } catch (InterruptedException ignored) {
                    }
                    // exponential increase, limited by maxRetryDelay
                    currentRetryDelay *= 2;
                    int maxRetryDelay = 100;
                    if (currentRetryDelay > maxRetryDelay) {
                        currentRetryDelay = maxRetryDelay;
                    }
                } else {
                    log.error("Watch error: " + throwable.getLocalizedMessage());
                }
                watch();
            }
        };
        consul.keyValueClient().getValues(fullKey,
                QueryOptions.blockSeconds(CONSUL_WATCH_WAIT_SECONDS, new BigInteger("0")).build(),
                callback);

    }

}
