package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.constants.WorkflowConstants;
import com.tansun.wf.activiti.util.SliceServiceUtils;
import com.tansun.wf.platform.mapper.ActHiTaskinstMapper;
import com.tansun.workflow.model.BaseBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;

import org.camunda.bpm.engine.ManagementService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工作流操作切面服务类
 * 1.防止重复调用方法，并打印方法的执行时间
 * 2.设置分片参数省号
 *
 */
@Component
@Aspect
public class WorkflowServiceAspectService {
    private ConcurrentHashMap<String,Long> executeLimit = new ConcurrentHashMap();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final ThreadLocal<String> methodName = new ThreadLocal<String>(){
        protected String initialValue(){
            return null;
        }
    };

    @Pointcut("execution(* com.tansun.wf.activiti.service.WorkflowService.* (..))")
    private void operatePointCut(){}

    @Autowired
    private ManagementService managementService;

    @Autowired
    private ActHiTaskinstMapper actHiTaskinstMapper;


    @Around("operatePointCut()")
    private Object setOperateInThreadLocal(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        long startTime = System.currentTimeMillis();
        //限制标识一般为taskId或processInstanceId或businessKey, 参数对方法是一次性使用的
        Map<String, String> limitContent = getLimitContent(pjp);
        String limitFlagValue = limitContent.get("limitFlagValue");
        Long firstExecuteTime = null;
        Object result;
        try {
            WorkflowServiceAspectService.methodName.set(methodName);
            //判断方法是否为重复执行，如果是直接返回
            boolean isProceed = true;
            if (limitFlagValue != null) {
                firstExecuteTime = executeLimit.get(limitFlagValue);
                if (firstExecuteTime != null){
                    isProceed = false;
                } else {
                    firstExecuteTime = startTime;
                    executeLimit.put(limitFlagValue, firstExecuteTime);
                }
            }

            if (isProceed){
                this.setSliceContent(pjp);
                result = pjp.proceed();
            }else{
                result = createResultObj(pjp,firstExecuteTime);
            }

        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            //移除分片条件线程变量
            SliceServiceUtils.removeLocalSliceValue();

            //不管异常返回还是正常返回都移除限制标志
            if(limitFlagValue != null && startTime == firstExecuteTime){
                executeLimit.remove(limitFlagValue);
            }
            //记录方法执行时间,方法执行超过3s打印warn日志，没有超过3s打印info日志
            long endTime = System.currentTimeMillis();
            long l = endTime - startTime;
            String limitMethodInfo = "";
            if(limitFlagValue != null){
                String limitFlag = limitContent.get("limitFlag");
                limitMethodInfo = ", detail :" + limitFlag + ", " + limitFlagValue;
            }
            if (l >= 3000){
                logger.warn( "long execution time , method is : " + methodName + ", time is :"+  l + limitMethodInfo);
            }else{
               // logger.info( "short execution time , method is  : " + methodName + ", time is :"+  l +  limitMethodInfo);
            }
            WorkflowServiceAspectService.methodName.set(null);
        }
        return result;
    }

    /**
     * 创建返回对象
     * @param pjp
     * @param firstExecuteTime
     * @return
     */
    private Object createResultObj(ProceedingJoinPoint pjp, Long firstExecuteTime) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Signature signature = pjp.getSignature();
        String className = signature.toLongString().split(" ")[1];
        Class onwClass = Class.forName(className);
        BaseBean result = (BaseBean) onwClass.newInstance();
        result.setRetCode( WorkflowConstants.FAILED);
        Map<String, String> limitContent = getLimitContent(pjp);
        String limitFlag = limitContent.get("limitFlag");
        String limitFlagValue = limitContent.get("limitFlagValue");
        result.setRetMsg("动作["+signature.getName()+"]正在执行，执行时间 :" + new Date(firstExecuteTime) + "，限制标识("+limitFlag+") ：" + limitFlagValue );
        return result;
    }

    /**
     * 获取限制标识
     * @param pjp
     * @return
     */
    private Map<String, String> getLimitContent(ProceedingJoinPoint pjp) {
        Map<String, String> result = new HashMap<>();
        List<String> limitMethodsForTaskBean = Arrays.asList( "backToFirstUserTask", "submitTask", "backTask", "backPreTask","rebackTask");
        List<String> limitMethodsForTaskInfo = Arrays.asList(  "temporarySaveTaskExpandData", "updateCurrentTaskExpandData");
        List<String> limitMethodsForProcessInfo = Arrays.asList("startProcess", "startAndSubmitProcess", "terminateProcess",  "updateBusinessKey",  "deleteProcessInstance");
//        List<String> limitMethods = Arrays.asList("startProcess", "startAndSubmitProcess", "terminateProcess", "backToFirstUserTask", "submitTask", "backTask", "backPreTask", "updateBusinessKey", "rebackTask", "temporarySaveTaskExpandData", "updateCurrentTaskExpandData", "deleteProcessInstance");

        String methodName = pjp.getSignature().getName();
        if (limitMethodsForTaskBean.contains(methodName)){
            TaskBean taskBean = (TaskBean) pjp.getArgs()[0];
            if (taskBean != null && StringUtils.isNotBlank(taskBean.getTaskId())){
                result.put("limitFlag", "taskId");
                result.put("limitFlagValue",taskBean.getTaskId());
                return  result;
            }else{
                return result;
            }
        }
        if (limitMethodsForTaskInfo.contains(methodName)){
            TaskInfo taskInfo = (TaskInfo) pjp.getArgs()[0];
            if (taskInfo != null && StringUtils.isNotBlank(taskInfo.getTaskId())){
                result.put("limitFlag", "taskId");
                result.put("limitFlagValue",taskInfo.getTaskId());
                return  result;
            }else{
                return result;
            }
        }

        if (limitMethodsForProcessInfo.contains(methodName)){
            ProcessInfo processInfo = (ProcessInfo) pjp.getArgs()[0];
            if ("startProcess".equals(methodName) ||  "startAndSubmitProcess".equals(methodName)){

                if (processInfo != null && StringUtils.isNotBlank(processInfo.getBusinessKey())){
                    result.put("limitFlag", "businessKey");
                    result.put("limitFlagValue",processInfo.getBusinessKey());
                    return  result;
                }else{
                    return result;
                }
            } else{
                if (processInfo != null && StringUtils.isNotBlank(processInfo.getProcessInstanceId())){
                    result.put("limitFlag", "processInstanceId");
                    result.put("limitFlagValue",processInfo.getProcessInstanceId());
                    return  result;
                }else{
                    return result;
                }
            }
        }

        return result;
    }




    private void setSliceContent(ProceedingJoinPoint pjp) throws Exception {
        if (!SliceServiceUtils.isOpen()) {
            return;
        }

        Object[] objs = pjp.getArgs();
        if(objs.length == 0){
            return;
        }
        Object obj = objs[0];
        if (obj == null) {
            return;
        }

        String methodName = pjp.getSignature().getName();

        if(!SliceServiceUtils.isCheckMethod(methodName)){
            //只涉及全局表的方法不需要设置省号
            return;
        }

        String sliceValue = null;
        if (obj instanceof List ) {
            List list = (List) obj;
            for (Object o : list) {
                String tmp = SliceServiceUtils.getSliceValue(o);
                if (sliceValue != null && !sliceValue.equals(tmp)){
                    throw new RuntimeException("批量执行的方法的参数里面sliceValue 的值必须一致!");
                }else{
                    sliceValue = tmp;
                }
            }
            return;
        }else {
            sliceValue = SliceServiceUtils.getSliceValue(obj);
        }

        if(StringUtils.isBlank(sliceValue)){
            throw new RuntimeException("sliceValue 不能为空！");
        }

        Method getTaskId = ReflectionUtils.findMethod(obj.getClass(), "getTaskId");
        String taskId = getTaskId == null ? null : (String)ReflectionUtils.invokeMethod(getTaskId, obj);

        Method getProcessInstanceId = ReflectionUtils.findMethod(obj.getClass(), "getProcessInstanceId");
        String processInstanceId = getProcessInstanceId == null ? null : (String)ReflectionUtils.invokeMethod(getProcessInstanceId, obj);

        boolean isFindTask = methodName.equals("findTaskList");

        // 三种情况：1.全库查询，2.用切分条件查询，3.用发起流程的所在的数据节点作为切分条件
        if (SliceServiceUtils.isParent(sliceValue)) {
            if (isFindTask && (StringUtils.isBlank(processInstanceId) || StringUtils.isBlank(taskId))) {
                //如果是总机构查询待办已办，且没有流程实例id或任务id 不能设置切片条件，全部分库扫描
                return;

            } else {
                //如果是总机构审批流程，使用发起流程的所在的数据节点作为切分条件
                if (StringUtils.isBlank(processInstanceId) && StringUtils.isBlank(taskId)) {
                    //两个值都为空，无法找到发起流程所在数据节点
                    throw new RuntimeException("taskId or processInstanceId 为空，无法找到发起流程所在数据节点 !");
                }
                Map params = new HashMap();
                params.put("taskId", taskId);
                params.put("processInstanceId", processInstanceId);

                String firstTaskSliceValue = querySliceValue(taskId, processInstanceId);
                if (StringUtils.isBlank(firstTaskSliceValue)) {
                    throw new RuntimeException("总机构审批，发起流程的切分条件找不到 !");
                }
                sliceValue = firstTaskSliceValue;
            }

        } else {
            // 2.用切分条件查询
        }

        SliceServiceUtils.setLocalSliceValue(sliceValue);
    }

    /**
     * 查询发起流程使用的分片号
     * @param taskId
     * @param processInstanceId
     * @return
     */
    private String querySliceValue(String taskId, String processInstanceId) {
        // camunda-TODO
        return actHiTaskinstMapper.querySliceValue(taskId, processInstanceId, SliceServiceUtils.getDbSliceColumnName());
//        return   managementService.executeCustomSql(new AbstractCustomSqlExecution<ActHiTaskinstMapper,  String>(ActHiTaskinstMapper.class) {
//            @Override
//            public String execute(ActHiTaskinstMapper actHiTaskinstMapper) {
//               return actHiTaskinstMapper.querySliceValue(taskId, processInstanceId, SliceServiceUtils.getDbSliceColumnName());
//            }
//        });
    }

    public static String getExecuteMethodName(){
        return methodName.get();
    }

}
