package com.tansun.wf.decision.service;


import com.alibaba.fastjson.JSON;
import com.tansun.wf.decision.model.ThirdPartyDataSourceConfigModel;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;


/**
 * 解析接口参数
 *
 */
public class InterfaceConfigParserService implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(InterfaceConfigParserService.class);

	private String defaultUrl = null;
	
	private List<ThirdPartyDataSourceConfigModel> interfaceConfigs = null;

    private ApplicationContext applicationContext;

    public List<ThirdPartyDataSourceConfigModel> getInterfaceConfigs(){
        if (null == interfaceConfigs) {
            try {
                ClassPathResource resource = new ClassPathResource("thirdparty-config.json");
                InputStream inputStream = resource.getInputStream();
                List<String> lines = IOUtils.readLines(inputStream, "utf-8");
                StringBuffer configStr = new StringBuffer();
                if (lines != null) {
                    for (String line : lines) {
                        configStr.append(line);
                    }
                }

                interfaceConfigs = JSON.parseArray(configStr.toString(), ThirdPartyDataSourceConfigModel.class);
                if (!StringUtils.isBlank(defaultUrl) && interfaceConfigs != null) {
                    for (ThirdPartyDataSourceConfigModel interfaceConfig : interfaceConfigs) {
                        interfaceConfig.setDefaultUrl(defaultUrl);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return interfaceConfigs;
    }

    public ThirdPartyDataSourceConfigModel getInterfaceConfig(String interfaceKey){
        if(StringUtils.isBlank(interfaceKey)){
            return null;
        }
        List<ThirdPartyDataSourceConfigModel> interfaceConfigs = getInterfaceConfigs();
        for (ThirdPartyDataSourceConfigModel interfaceConfig : interfaceConfigs) {
            if(interfaceKey.equals(interfaceConfig.getDataSource())){
                return interfaceConfig;
            }
        }
        return null;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
