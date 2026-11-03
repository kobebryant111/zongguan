package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingChangeApplyJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 外包员工信息变更
 */
@Slf4j
public class PpmOutsourcingChangeApplyJob implements IQuartzExecutorJob {
    /**
     * 外包人员信息变更
     */
    private IPpmOutsourcingChangeApplyJobService ppmOutsourcingChangeApplyJobService = (IPpmOutsourcingChangeApplyJobService) SpringUtil.getBean("ppmOutsourcingChangeAppplyService");
    @Override
    public void executeInternal() {
        try{
            ppmOutsourcingChangeApplyJobService.outInfoChange();
        }catch (Exception e) {
            log.error("外包信息变更定时器异常", e);
        }

    }

}
