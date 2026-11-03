package com.tansun.wf.extend.db;

import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



public class WfIdGeneratorService implements ApplicationContextAware {

    private final String DB_ID_GENERATOR = "db";

    private final String UUID_ID_GENERATOR = "uuid";

    private String type;

    private ApplicationContext applicationContext;

    public IdGenerator getIdGenerator() {
        if (type.equals(DB_ID_GENERATOR)) {

            return applicationContext.getBean(SpringProcessEngineConfiguration.class).getIdGenerator();
        } else if (type.equals(UUID_ID_GENERATOR)) {
            return applicationContext.getBean(StrongUuidGenerator.class);
        } else {
            return null;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
