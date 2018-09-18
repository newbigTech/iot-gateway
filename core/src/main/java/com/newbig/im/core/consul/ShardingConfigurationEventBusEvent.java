package com.newbig.im.core.consul;

import io.shardingsphere.core.rule.ShardingRule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
@Getter
public final class ShardingConfigurationEventBusEvent {

    private final Map<String, DataSource> dataSourceMap;

    private final ShardingRule shardingRule;

    private final Properties props;
}
