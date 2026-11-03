package com.work.ppm.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 事务配置
 */
@Slf4j
@Configuration
public class TransactionConfig {
    /**
     * 事务
     * @param dataSource
     * @return  DataSourceTransactionManager
     */
    @Bean(name = "defaultTx")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }
}
