package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.services.integratedmanager.IPpmInterviewApplyService;
import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步新老科管面试数据
 */
@Slf4j
public class PpmInterviewApplyJob implements IQuartzExecutorJob {
    /**
     * 外包面试
     */
    private IPpmInterviewApplyService ppmInterviewApplyService = (IPpmInterviewApplyService) SpringUtil.getBean(
            "ppmInterviewApplyService");
    @Override
    public void executeInternal() {
        try{
            ppmInterviewApplyService.newAndOldDataSysnc();
        }catch (Exception e) {
            log.error("同步新老科管面试数据定时器异常", e);
        }
    }
}
