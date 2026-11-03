package com.work.ppm.application.quartz.trigger.systemmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IImRoleJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 行员自动添加到普通行员角色定时器
 */
@Slf4j
public class CommEmployeeJob implements IQuartzExecutorJob {
    /**
     * 用户角色
     */
    private IImRoleJobService imRoleJobService = (IImRoleJobService) SpringUtil.getBean("imRoleService");
    @Override
    public void executeInternal() {
        try{
            imRoleJobService.updateEmployForRole();
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>科技部行员角色、业务部门员工定时器执行结束<<<<<<<<<<<<<<<<<<<<<<<");
        }catch (Exception e) {
            log.error("定时器异常", e);
        }

    }

}
