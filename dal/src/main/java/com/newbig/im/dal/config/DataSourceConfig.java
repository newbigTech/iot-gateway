package com.newbig.im.dal.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.newbig.im.dal.config.DataSourceNames.IMCONFIG_DS;
import static com.newbig.im.dal.config.DataSourceNames.SHARDING_DS;

@Configuration
public class DataSourceConfig {

    //ShardingSphere 的数据源
    @Resource(name = "shardingDataSource")
    private DataSource shardingDataSource;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource imDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDS")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(imDataSource());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put(IMCONFIG_DS, imDataSource());
        dsMap.put(SHARDING_DS, shardingDataSource);
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource());
        return factoryBean.getObject();
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }
}

