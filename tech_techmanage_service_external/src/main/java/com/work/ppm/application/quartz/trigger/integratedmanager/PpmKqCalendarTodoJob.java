package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarTodoDto;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarTodoService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时发起考勤日历数据导入任务
 *
 */
@Slf4j
public class PpmKqCalendarTodoJob implements IQuartzExecutorJob {
    /**
     * 外包人员离职
     */
    private IPpmKqCalendarTodoService ppmKqCalendarTodoService = (IPpmKqCalendarTodoService) SpringUtil.getBean("ppmKqCalendarTodoService");

    @Override
    public void executeInternal() {
        try {

            log.info("考勤日历数据导入定时器开始");
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            bps.setUserId("sysadmin");
            bps.setUserName("系统管理员");
            ppmKqCalendarTodoService.start(new PpmKqCalendarTodoDto(), bps);
            log.info("考勤日历数据导入定时器结束");
        } catch (Exception e) {
            log.error("考勤日历数据导入定时器异常", e);
        }

    }

}
