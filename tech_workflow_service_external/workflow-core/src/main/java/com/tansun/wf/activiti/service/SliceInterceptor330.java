//package com.tansun.wf.activiti.service;
//
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.plugin.*;
//
//import java.sql.Connection;
//import java.util.Properties;
//
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
//public class SliceInterceptor330 implements Interceptor  {
//
//
//    private  SliceInterceptor sliceInterceptor = new SliceInterceptor();
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        return sliceInterceptor.intercept(invocation);
//    }
//
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//    }
//
//}
