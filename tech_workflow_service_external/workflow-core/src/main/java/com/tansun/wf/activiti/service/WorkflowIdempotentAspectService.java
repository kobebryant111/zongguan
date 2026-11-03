package com.tansun.wf.activiti.service;

import java.lang.reflect.Method;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.annotation.WorkflowIdempotent;
import com.tansun.wf.activiti.model.WfIdempotentEntity;
import com.tansun.workflow.model.BaseBean;
import com.tansun.workflow.model.BaseInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.TaskBean;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 幂等操作类
 */
@Component
@Aspect
public class WorkflowIdempotentAspectService{

    private static Logger logger = LoggerFactory.getLogger(WorkflowIdempotentAspectService.class);

    @Value("${idempotent:false}")
    private String isIdempotent = "false";

    @Autowired
    private WorkflowIdempotentService workflowIdempotentService;

    @Pointcut("execution(* com.tansun.workflow.api.IWorkflowService.*(..))")
    private void operatePointCut() {
    }

    /**
     * 支持幂等操作的方法
     */
    private final List<String>  methodNames =Arrays.asList("startProcess", "startAndSubmitProcess", "terminateProcess", "suspendProcess", "activateProcess", "submitTask", "backTask", "backPreTask", "rebackTask", "getTask", "releaseTask", "transferTask",  "goFlowTask", "addGroupUser", "delGroupUser");

    @Around("operatePointCut()")
    private Object dealIdempotent(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        try {
            //1.校验交易是否需要幂等操作
            boolean isNeed = isNeedDoIdempotent(pjp);

            if (!isNeed){
                //无需做幂等操作
                return pjp.proceed();
            } else {
                //2.执行幂等逻辑
                WfIdempotentEntity record = getRecord(pjp);
                if(record != null){
                    //忽略成功的重试交易
                    logger.warn("ignore repeat request , transaction id is :" + getTransactionId(pjp));
                    return createResult(pjp,record);
                }else{
                    Date startTime = new Date();
                    result = pjp.proceed();
                    //3.业务执行成功后,记录交易
                    recordResult(pjp, startTime, result);
                }
            }

        } catch (Throwable throwable) {
            throw throwable;
        }finally {
            WorkflowThreadLocalUtils.removeAll();
        }
        return result;
    }


    private Object createResult(ProceedingJoinPoint pjp, WfIdempotentEntity record) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Class returnType = methodSignature.getReturnType();

        if(returnType == ProcessBean.class){
            ProcessBean processBean = JSON.parseObject(record.getResponseResult(), ProcessBean.class);
            processBean.setIsRepeatResult("true");
            return processBean;
        }else if(returnType == TaskBean.class){
            TaskBean taskBean = JSON.parseObject(record.getResponseResult(), TaskBean.class);
            taskBean.setIsRepeatResult("true");
            return taskBean;
        }
        return null;
    }


    private WfIdempotentEntity getRecord(ProceedingJoinPoint pjp) {
        return workflowIdempotentService.findByTransactionId( getTransactionId(pjp));
    }

    /**
     * 判断是否执行执行幂等操作
     * @param pjp
     * @return
     */
    private boolean isNeedDoIdempotent(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        WorkflowIdempotent annotation = method.getAnnotation(WorkflowIdempotent.class);
        boolean supportIdempotent = annotation == null?false:annotation.value();

        if (!"true".equals(isIdempotent) || !supportIdempotent) {
            return false;
        }

        if (pjp.getArgs() == null) {
            return false;
        }

        String transactionId = getTransactionId(pjp);

        if (StringUtils.isBlank(transactionId)) {
            return false;
        }
        return true;
    }

    /**
     * 获取交易id
     * @param pjp
     * @return
     */
    private String getTransactionId(ProceedingJoinPoint pjp) {

        String transactionId = null;
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof BaseInfo){
                String value = ((BaseInfo) arg).getTransactionId();
                transactionId = value == null?transactionId:value;
            } else if(arg instanceof BaseBean){
                String value = ((BaseBean) arg).getTransactionId();
                transactionId = value == null?transactionId:value;
            }
        }
        return transactionId;
    }

    /**
     * 保存记录
     * @param pjp
     * @param startTime
     * @param result
     */
    private void recordResult(ProceedingJoinPoint pjp, Date startTime, Object result) {
        WfIdempotentEntity record = new WfIdempotentEntity();
        record.setId(UUID.randomUUID().toString());
        record.setTransactionId(getTransactionId(pjp));
        String params = JSON.toJSONString(pjp.getArgs());

        record.setReqeustParams(params);
        record.setMethodName(pjp.getSignature().getName());
        String responseResult = result == null ? "" : JSON.toJSONString(result);

        record.setResponseResult(responseResult);
        record.setState("0");
        record.setStartTime(startTime);
        record.setEndTime(new Date());
        workflowIdempotentService.saveRecord(record);
    }

    public String getIsIdempotent() {
        return isIdempotent;
    }

    public void setIsIdempotent(String isIdempotent) {
        this.isIdempotent = isIdempotent;
    }

    public static void main(String[] args) {

    }
}
