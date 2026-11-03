package com.tansun.wf.activiti.service;

import com.alibaba.fastjson.JSON;
import com.tansun.workflow.model.TaskBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 工作流操作切面服务类
 *
 */
@Component
@Aspect
public class WorkflowBackTaskAspectService {
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

	@Pointcut("execution(* com.tansun.wf.activiti.service.IWorkflowEngineService.backTask(..))")
	private void operatePointCut(){}

	@Around("operatePointCut()")
    private Object setOperateInThreadLocal(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            //驳回节点ID
            TaskBean taskBean = (TaskBean) pjp.getArgs()[0];
            backNodeId.set(JSON.toJSONString(taskBean.getNextActivityIds()));

            //当前节点任务id
            String taskId = taskBean.getTaskId();
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


