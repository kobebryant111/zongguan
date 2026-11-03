package com.tansun.wf.activiti.timer;
import com.tansun.wf.activiti.service.SendMailService;
import com.tansun.wf.activiti.service.TimerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/** 
 * 定时器,启动项目时启动定时任务，多线程执行定时任务
 */  
//@Service
public class UrgedHandleMessageTimer {  
	//定时执行的间隔时间(s)
	public static final Integer SECONDS_INTERVAL = 10;
	//首次执行定时的延时时间(s)
	public static final Integer FIRST_SECONDS = 10;
	//定时配置信息状态，0正常，1失效
    private static String TIMERT_CONFIG_STATUS="0";
    //定时发送类型 邮件：email邮件
    private static String TIMERT_CONFIG_SEND_TYPE="email";
    
	@Autowired
	private TimerConfigService timerConfigService;
	@Autowired
	private SendMailService sendMailService;
	private Map<String,ScheduledFuture<?>> futureMap=new HashMap<String,ScheduledFuture<?>>();
//	private ScheduledFuture<?> future;
	/**
	 * 启动时触发定时器
	 */
//   @PostConstruct
//   @Scheduled(cron="0/10 * *  * * ? ")
   public void urgedHandleTimer() {  
	   final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		//初始化
		threadPoolTaskScheduler.initialize();
//        Runnable runnable = new Runnable() {
//        public void run() {
        	try {
        		//查询定时配置表
        		List<HashMap>  tcList = timerConfigService.findTimerConfig(TIMERT_CONFIG_STATUS);
            	if(null!=tcList&&!"".equals(tcList)&&tcList.size()>0){
            		for(Map m :tcList){
            			String id = (String) m.get("id");
            			String cron = (String) m.get("cronExpression");
            			String timeType = (String) m.get("timeType");
            			Long remindNum = (Long) m.get("remindNum");
            			Date beginTime = (Date) m.get("beginTime");
            			Date endTime = (Date) m.get("endTime");
            			String sendType = (String) m.get("sendType");         			
            			String procDefId = (String) m.get("procDefId");
            			String taskDefKey = (String) m.get("taskDefKey");
            			String procInstId = (String) m.get("procInstId");
            			String executionId = (String) m.get("executionId");
            			String tenantId = (String) m.get("tenantId");
            			String template = (String) m.get("template");
            			if(!"".equals(cron)&&null!=beginTime&&null!=endTime){
            				Long l = new Date().getTime();
            				if(l<endTime.getTime()&&l>beginTime.getTime()&&remindNum>0){
            					// 执行定时任务                       			
                    			trigerCron(threadPoolTaskScheduler,id,sendType,cron,procDefId,
                    					taskDefKey,procInstId,executionId,tenantId,template,remindNum);
            				}else if(l>endTime.getTime()||remindNum<1){
            					//删掉定时配置表种过时的定时任务
            					deleteOverTriger(id);
//                					timerConfigService.deleteTimerConfigByCondition(procDefId, procInstId, taskDefKey, executionId);
            				}
            			}
            		}
            	}
			} catch (Exception e) {
				e.printStackTrace();
			}
//        }
//    };  
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
//        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间，触发run方法执行定时任务
//        service.scheduleAtFixedRate(runnable, FIRST_SECONDS, SECONDS_INTERVAL, TimeUnit.SECONDS);  
   }   
     
 
   /**
    * 执行定时任务
    * @param sendType
    * @param cron
    * @param processDefinitionId
    * @param activityId
    * @param processInstanceId
    * @param executionId
    */
   public void trigerCron(final ThreadPoolTaskScheduler threadPoolTaskScheduler,final String id, final String sendType,
		   final String cron, final String processDefinitionId,final String activityId, final String processInstanceId, 
		   final String executionId,final String tenantId, final String template,final Long remindNum) {
	   ScheduledFuture<?> future = null;
	   if(!futureMap.containsKey(id)){
		future = threadPoolTaskScheduler.schedule(new Runnable() {
		@Override
		public void run() {
			try {
				//邮件
    			if(TIMERT_CONFIG_SEND_TYPE.equals(sendType)){
    				sendMailService.sendTrigerEmail(id,processDefinitionId,activityId,
    						processInstanceId,executionId,tenantId,template,remindNum);
    			}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}, new Trigger() {
		@Override
		public Date nextExecutionTime(TriggerContext triggerContext) {
			if ("".equals(cron) || cron == null)
				return null;
			CronTrigger trigger = new CronTrigger(cron);// 定时任务触发，可修改定时任务的执行周期
			Date nextExecDate = trigger.nextExecutionTime(triggerContext);
			return nextExecDate;
		}
		});	
		   futureMap.put(id, future);	   
	   }
	   if(future != null&&future.isDone()){
		   stopCron(future);
	   } 
	}
   //取消任务调度
	public void stopCron(ScheduledFuture<?> future) {
		if (future != null) {
			future.cancel(true);//取消任务调度
		}
	}
	//删除超过结束提醒时间的任务调度
	public void deleteOverTriger(String id){
		timerConfigService.deleteTimerConfig(id);
	}
	
}  