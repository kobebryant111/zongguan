package com.tansun.wf.activiti.service;

import com.tansun.workflow.model.TaskBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 工作流取回切面服务类
 *
 */
@Component
@Aspect
public class WorkflowReBackTaskAspectService {
	private static final ThreadLocal<String> reBackNodeId = new ThreadLocal<String>(){
		protected String initialValue(){
			return null;
		}
	};

	@Pointcut("execution(* com.tansun.wf.activiti.service.IWorkflowEngineService.rebackTask(..))")
	private void operatePointCut(){}

	@Around("operatePointCut()")
    private Object setOperateInThreadLocal(ProceedingJoinPoint pjp){
		Object result = null;
		try {
			//设置取回操作
			TaskBean taskBean = (TaskBean) pjp.getArgs()[0];
			String nodeId = taskBean.getTaskId();
			reBackNodeId.set(nodeId);

			result = pjp.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			reBackNodeId.set(null);
		}
		return result;
    }

    public static String getReBackNodeId(){
        return reBackNodeId.get();
    }

    public static void setNull(){
    	reBackNodeId.set(null);
    }
}


