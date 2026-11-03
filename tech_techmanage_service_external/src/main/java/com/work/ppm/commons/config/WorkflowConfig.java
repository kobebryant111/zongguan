package com.work.ppm.commons.config;

import com.tansun.workflow.service.WorkFlowHttpClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CAMUNDA工作流配置
 */
@Configuration
public class WorkflowConfig {

    // 配置文件需要添加
    @Value("${amla.address}")
    private String address;
    @Value("${amla.username}")
    private String username;
    @Value("${amla.password}")
    private String password;

    @Bean
    public WorkFlowHttpClientService workFlowHttpClientService() {
        WorkFlowHttpClientService workFlowHttpClientService = new WorkFlowHttpClientService();
        workFlowHttpClientService.setAddress(address);
        workFlowHttpClientService.setUsername(username);
        workFlowHttpClientService.setPassword(password);
//        workFlowHttpClientService.initToken();
        return workFlowHttpClientService;
    }
}
