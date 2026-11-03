package com.work.ppm.commons.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.work.ppm.commons.constant.MyBatisProperties;
import com.work.ppm.commons.mybatis.plugin.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis配置类
 */
@Slf4j
@Configuration
public class MyBatisConfiguration {
    /**
     * 数据源
     */
    @Autowired
    private DataSourceProperties dataSourceProperties;
    /**
     * 属性
     */
    @Autowired
    private MyBatisProperties myBatisProperties;

    /**
     * dataSource
     * @return DataSource
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return  DruidDataSourceBuilder.create().build();
    }

    /**
     * writeDataSource
     * @return DataSource
     */
    @Bean
    @Qualifier("writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        return this.dataSource();
    }

    /**
     * sqlSessionFactory
     * @return  SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(myBatisProperties.getMapperLocations()));
        sqlSessionFactoryBean.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());

        // 插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{ new PageHelper()});
        return sqlSessionFactoryBean.getObject();
    }

}
