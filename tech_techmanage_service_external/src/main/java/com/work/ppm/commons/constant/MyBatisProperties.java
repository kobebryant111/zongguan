package com.work.ppm.commons.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatis 属性
 */
@Data
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisProperties {
    /**
     * mapper
     */
    private String mapperLocations;
    /**
     * 别名包
     */
    private String typeAliasesPackage;
}
