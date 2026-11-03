package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IAqzyDepartmantJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *定时解析安全总部部门同步文件任务
 *
 */
@Slf4j
public class PpmAqzyDepartmentJob  implements IQuartzExecutorJob {
    /**
     * 同步数据服务接口
     */
    private IAqzyDepartmantJobService aqzyDepartmantJobService = (IAqzyDepartmantJobService) SpringUtil.getBean("AqzyDepartmantJobService");

    @Override
    public void executeInternal()  {
        try {
            log.info("安全总部部门同步文件任务开始");
            aqzyDepartmantJobService.AqzyDepartmantJob();
            log.info("安全总部部门同步文件任务结束");
        } catch (Exception e) {
            log.error("安全总部部门同步文件任务异常", e);
        }
    }
}
