package com.tansun.wf.activiti.aspect.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class WorkflowTaskBackAspect {
	private static final ThreadLocal<String> backNodeId = new ThreadLocal<String>(){
		protected String initialValue(){
			return null;
		}
	};
	
	private static final ThreadLocal<String> backTaskId = new ThreadLocal<String>(){
		protected String initialValue(){
			return null;
		}
	};

	@Pointcut("execution(* com.tansun.wf.activiti.service.IWorkflowEngineService.rollBackTask(..))")
	private void operatePointCut(){}

	@Around("operatePointCut()")
    private Object setOperateInThreadLocal(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            //驳回节点ID
            String nodeId = (String) pjp.getArgs()[2];
            backNodeId.set(nodeId);

            //当前节点任务id
            String taskId = (String) pjp.getArgs()[0];
            backTaskId.set(taskId);

            result = pjp.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            backNodeId.set(null);
            backTaskId.set(null);
        }
        return result;
    }

    public static String getBackNodeId(){
        return backNodeId.get();
    }
    
    public static String getBackTaskId(){
        return backTaskId.get();
    }
    
    public static void setNull(){
    }
}
