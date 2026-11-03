//package com.tansun.wf.extend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
// camunda-TODO
//@Configuration
//public class WorkflowConfiguration {
//
//    /**
//     * 是指默认的事务监听器
//     * @param transactionManager
//     * @return
//     */
//    @Lazy
//    @Bean
//    public TransactionManagementConfigurer transactionManagementConfigurer(final PlatformTransactionManager transactionManager) {
//        TransactionManagementConfigurer transactionManagementConfigurer = new TransactionManagementConfigurer() {
//            @Override
//            public PlatformTransactionManager annotationDrivenTransactionManager() {
//                return transactionManager;
//            }
//        };
//        return transactionManagementConfigurer;
//
//    }
//}
//
//
