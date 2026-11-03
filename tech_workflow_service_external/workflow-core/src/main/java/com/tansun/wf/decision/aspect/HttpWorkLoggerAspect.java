package com.tansun.wf.decision.aspect;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.api.IWorkflowController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 计算IWorkflowController接口调用的时间并打印日志
 * @author lijiang
 * @create 2020-01-21 11:07
 */
@Component
@Aspect
public class HttpWorkLoggerAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpWorkLoggerAspect.class);

    @Pointcut("execution(public * com.tansun.workflow.api.IWorkflowController.createTaskEvent(..)) " +
            "|| execution(public * com.tansun.workflow.api.IWorkflowController.processCompletedTrans(..))" +
            "|| execution(public * com.tansun.workflow.api.IWorkflowController.afterVotingCompleted(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        try {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis() - startTime;
            // 大于3秒
            if(endTime >= 1000 * 3){
                logger.error("{}.{} execute completed: 耗时{}秒 params: {}",simpleName,methodName,endTime/1000,JSON.toJSONString(joinPoint.getArgs()[0]));
            }else{
                logger.info("{}.{} execute completed: 耗时{}毫秒 params: {}",simpleName,methodName,endTime,JSON.toJSONString(joinPoint.getArgs()[0]));
            }
            return result;
        } catch (Throwable te) {
            logger.error(te.getMessage(),te);
            throw new RuntimeException(te.getMessage());
        }
    }

}