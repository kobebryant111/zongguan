//package com.tansun.wf.mocktest.service;
//
//import org.h2.jdbcx.JdbcConnectionPool;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.io.DefaultResourceLoader;
//import org.springframework.core.io.Resource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//
//import javax.sql.DataSource;
//
//public class WfTestDataSourceFactory {
//
//    private String dataPath;
//    private String user;
//    private DataSource dataSource;
//
//    private static Logger logger = LoggerFactory.getLogger(WfTestDataSourceFactory.class);
//
//    public synchronized DataSource getH2DataSource() {
//        if (dataSource != null) {
//            return dataSource;
//        } else {
//            //内存模式 jdbc:h2:mem:test ,文件模式 jdbc:h2:./mocktest
//            String url = "jdbc:h2:" + dataPath;
//            JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(url, user, "");
//            dataSource = jdbcConnectionPool;
//
//            //初始化sql
//            DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
//            Resource script = defaultResourceLoader.getResource("classpath:mocktest/initsql/h2-schema.sql");
//
//            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//            databasePopulator.addScript(script);
//
//            try {
//                DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//                dataSourceInitializer.setDataSource(dataSource);
//                dataSourceInitializer.setDatabasePopulator(databasePopulator);
//                dataSourceInitializer.afterPropertiesSet();
//            } catch (Exception e){
//                logger.error(e.getMessage(),e);
//            }
//
//
//            return dataSource;
//        }
//
//    }
//
//    public String getDataPath() {
//        return dataPath;
//    }
//
//    public void setDataPath(String dataPath) {
//        this.dataPath = dataPath;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }
//
//}
