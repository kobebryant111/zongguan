package com.tansun.wf.extend.config;

import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.impl.db.sql.DbSqlSessionFactory;
import org.camunda.bpm.engine.impl.util.ReflectUtil;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

public class MySpringProcessEngineConfiguration extends SpringProcessEngineConfiguration  {

    @Override
	protected void initDataSource() {
    	if (this.dataSource == null) {
    		this.dataSource = new TransactionAwareDataSourceProxy(super.applicationContext.getBean(DataSource.class));
    	}
		super.initDataSource();
	}

	@Override
    protected InputStream getMyBatisXmlConfigurationSteam() {
        InputStream resourceAsStream = ReflectUtil.getResourceAsStream("spring/activiti-mappings.xml");
        String text = null;
        try {
            text = IOUtils.toString(resourceAsStream, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return IOUtils.toInputStream(text);
    }

    @Override
    protected void initSqlSessionFactory() {
        //替换mysql 分页写法
        DbSqlSessionFactory.databaseSpecificLimitAfterStatements.put("mysql", "LIMIT #{firstResult} , #{maxResults} ");
        //宁波反洗钱项目启动死锁问题
        DbSqlSessionFactory.dbSpecificConstants.get("oracle").put("constant_for_update","");
        super.initSqlSessionFactory();
    }

    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    
   
}
