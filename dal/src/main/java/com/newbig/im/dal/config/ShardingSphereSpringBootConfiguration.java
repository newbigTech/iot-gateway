package com.newbig.im.dal.config;

import com.google.common.base.Preconditions;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.exception.ShardingException;
import io.shardingsphere.core.util.DataSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Spring boot sharding and master-slave configuration.
 *
 * @author caohao
 */
@Configuration
@EnableConfigurationProperties({SpringBootShardingRuleConfigurationProperties.class})
public class ShardingSphereSpringBootConfiguration implements EnvironmentAware {

    @Autowired
    private SpringBootShardingRuleConfigurationProperties shardingProperties;


    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    /**
     * Get data source bean.
     *
     * @return data source bean
     * @throws SQLException SQL exception
     */
    @Bean(name = "shardingDataSource")
    public DataSource dataSource() throws SQLException {
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingProperties.getShardingRuleConfiguration(), shardingProperties.getConfigMap(), shardingProperties.getProps());
    }

    @Override
    public final void setEnvironment(final Environment environment) {
        setDataSourceMap(environment);
    }

    @SuppressWarnings("unchecked")
    private void setDataSourceMap(final Environment environment) {
        String prefix = "sharding.jdbc.datasource.";
        String dataSources = environment.getProperty(prefix + "names");
        for (String each : dataSources.split(",")) {
            try {
                Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + each, Map.class);
                Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
                DataSource dataSource = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
                dataSourceMap.put(each, dataSource);
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingException("Can't find datasource type!", ex);
            }
        }
    }
}