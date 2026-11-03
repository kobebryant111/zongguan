package com.work.ppm.application.quartz.trigger.systemmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IImUserRoleRelJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 文员角色定时器
 */
@Slf4j
public class ImUserRoleRelJob implements IQuartzExecutorJob {

    /**
     *  用户角色接口
     */
    private IImUserRoleRelJobService imUserRoleRelJobService = (IImUserRoleRelJobService) SpringUtil.getBean(
            "imUserRoleRelService");

    @Override
    public void executeInternal() {
       try{
           log.warn(">>>>>>>>>>>>>>>>>>>>>>>>文员角色定时器 开始执行<<<<<<<<<<<<<<<<<<<<<<");
           imUserRoleRelJobService.clerkTimer();
           log.warn(">>>>>>>>>>>>>>>>>>>>>>>>文员角色定时器 执行结束<<<<<<<<<<<<<<<<<<<<<<");
       }catch (Exception e) {
           log.error("【文员角色定时器】定时器异常", e);
       }
    }


}
