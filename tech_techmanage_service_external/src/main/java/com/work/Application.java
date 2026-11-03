package com.work;

import com.work.ppm.commons.constant.MyBatisProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 功能描述：启动项目
 *
 * @date: 2019/05/07 09:47
 * @version: V1.0
 */
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableSwagger2
@SpringBootApplication
@ComponentScan({"com.work"})
@MapperScan({"com.component.work.**.infrastructure.**","com.work.ppm.**.mapper.**"})
@EnableFeignClients("com.component.work.auth.sso")
 @EnableTransactionManagement
@EnableConfigurationProperties({MyBatisProperties.class })
public class Application  {
    /**
     * main
     *
     * @param args params
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 时区
     */
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
