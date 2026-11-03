package com.work.ppm.application.quartz.core;

import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.model.basismanager.PpmQuartzManager;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.basismanager.IPpmQuartzManagerService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时器管理类
 */
@Slf4j
@Order(1)
@Component("quartzScheduleManager")
public class QuartzScheduleManager implements ApplicationRunner {
    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 定时器管理接口
     */
    @Autowired
    private IPpmQuartzManagerService ppmQuartzManagerService;
    /**
     * 初始启动所有开启的定时任务
     * @return int
     * @throws Exception Exception
     */
    private int initStartQuartz() throws Exception {
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统启动成功<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        // 匹配ip,匹配的启动定时任务
        String localIp = IpUtil.getLocalIp();
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>本地IP：{}", localIp);
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail("PPM_NEW_QUARTZ", "ip");
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>业务字典IP：{}", eos.getDictname());
        //如果有多个环境则放开以下代码指定单独ip
        if(!localIp.trim().equals(eos.getDictname().trim())) {
            return -1;
        }
        // 查询所有启动的定时器
        List<PpmQuartzManager> quartzManagers = ppmQuartzManagerService.queryEnableAll();
        log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>定时器任务：{}", JSONObject.toJSONString(quartzManagers));
        if(null == quartzManagers || quartzManagers.isEmpty()) {
            return -1;
        }
        for (PpmQuartzManager job : quartzManagers) {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getClassName(),job.getTriggerName());
            CronTrigger cron = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在 创建一个
            if(null == cron) {
                JobDetail detail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getClassName(), job.getTriggerName()).build();
                detail.getJobDataMap().put(job.getClassName(), job);
                // 调度器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getExpresstion());
                cron = TriggerBuilder.newTrigger().withIdentity(job.getClassName(), job.getTriggerName()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(detail, cron);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getExpresstion());
                cron = cron.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey,cron);

            }
        }
        return 1;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<定时器初始化开始 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        this.initStartQuartz();
        log.warn("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<定时器初始化结束 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 启动
     * @param job 任务
     * @param trigger 定时器
     * @throws SchedulerException SchedulerException
     */
    public void start(JobDetail job, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * 停止调度
     * @param key 对象
     * @return boolean
     * @throws SchedulerException SchedulerException
     */
    public boolean unscheduleJob(TriggerKey key ) throws SchedulerException {
        return scheduler.unscheduleJob(key);
    }

    /**
     * 创建任务详情
     * @param job 对象
     * @return  JobDetail
     * @throws SchedulerException SchedulerException
     */
    public JobDetail createJobDetail(ScheduleJob job) throws SchedulerException {
        JobDetail detail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(),
                job.getJobGroup()).build();
        detail.getJobDataMap().put(job.getJobName(), job);
        return detail;
    }

    /**
     * 创建正则调度器
     * @param job 对象
     * @return CronTrigger
     * @throws SchedulerException SchedulerException
     */
    public CronTrigger createCronTrigger(ScheduleJob job) throws SchedulerException {
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        return TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(builder).build();
    }

    /**
     * 获取任务key
     * @param job 对象
     * @return  TriggerKey
     * @throws SchedulerException SchedulerException
     */
    public TriggerKey getTriggerKey(ScheduleJob job) throws SchedulerException {
        return TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
    }

    /**
     * 立即执行job
     * @param job 任务对象
     * @throws SchedulerException
     */
    public void runJobNow(ScheduleJob job) throws SchedulerException {
        JobKey key = JobKey.jobKey(job.getJobName(), job.getJobGroup() + "NOW");
        boolean falg = scheduler.checkExists(key);
        if(!falg) {
            JobDetail detail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(job.getJobName(),
                    job.getJobGroup() + "NOW").storeDurably(true).build();
            detail.getJobDataMap().put(job.getJobName(), job);
            // 创建
            scheduler.addJob(detail, true);
        }
        scheduler.triggerJob(key);
        // 删除任务
//        scheduler.deleteJob(key);
    }
}
