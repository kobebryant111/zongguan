package com.tansun.workflowmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
@ComponentScan({"com.jeedev.msdp","com.tansun"})
public class WorkflowMicroServiceApplication {

//	@Value("${use.cloud.restTemplate:Y}")
//	private String useCloudRestTemplate;
//
//	@Bean
//	@LoadBalanced
//	@ConditionalOnMissingBean(value = RestTemplate.class ,name = "restTemplate")
//	public RestTemplate restTemplate(){
//		RestTemplate restTemplate=new RestTemplate();
//		if ("Y".equals(useCloudRestTemplate)){
//			RestTemplateUtils.replaceWithCloudRestTemplate(restTemplate);
//		}
//
//		return  restTemplate;
//	}


	public static void main(String[] args) {
		SpringApplication.run(WorkflowMicroServiceApplication.class, args);
	}

}
