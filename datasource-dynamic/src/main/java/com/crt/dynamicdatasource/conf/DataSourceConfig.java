package com.crt.dynamicdatasource.conf;

import com.crt.dynamicdatasource.dynamic.DynamicDataSource;
import com.crt.dynamicdatasource.dynamic.DynamicDataSourceHolder;
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
 * @Author reed
 * @Date 2020/7/17 9:06 上午
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave01")
    public DataSource slave01Datasource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    @DependsOn({"masterDatasource", "slave01Datasource"})
    public DataSource dynamicDatasource(){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceHolder.MASTER, masterDatasource());
        targetDataSources.put(DynamicDataSourceHolder.SLAVE01, slave01Datasource());

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }
}
