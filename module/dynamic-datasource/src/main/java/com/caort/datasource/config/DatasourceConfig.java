package com.caort.datasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Caort.
 * @date 2021/5/10 下午3:02
 */
@Configuration
public class DatasourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @DependsOn({"slaveDataSource", "masterDataSource"})
    DataSource dynamicDatasource(DataSource slaveDataSource, DataSource masterDataSource){
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceHolder.SLAVE, slaveDataSource);
        dataSourceMap.put(DataSourceHolder.MASTER, masterDataSource);

        DynamicRoutingDataSource dynamicDataSource = new DynamicRoutingDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }


}
