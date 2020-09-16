package com.graduation.bbs.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description druid连接配置类
 * 默认没有实现druid，需要注册实现
 * @Author
 * @Date 2020-09-13 14:26
 * @Version 1.0
 **/
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid",ignoreInvalidFields = true)
    public DruidDataSource dataSource(
            DataSourceProperties properties) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.determineDriverClassName());
        dataSource.setUrl(properties.determineUrl());
        dataSource.setUsername(properties.determineUsername());
        dataSource.setPassword(properties.determinePassword());
        DatabaseDriver databaseDriver = DatabaseDriver
                .fromJdbcUrl(properties.determineUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }
        return dataSource;
    }
}
