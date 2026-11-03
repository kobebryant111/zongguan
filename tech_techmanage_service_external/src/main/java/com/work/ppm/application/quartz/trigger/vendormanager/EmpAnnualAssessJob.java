package com.work.ppm.application.quartz.trigger.vendormanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.vendormanager.IOutsrcEmpAssessApplyService;
import com.work.ppm.application.services.vendormanager.impl.OutsrcEmpAssessApplyService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

@Slf4j
public class EmpAnnualAssessJob implements IQuartzExecutorJob {

    /**
     * 人员季度考核
     */
    private IOutsrcEmpAssessApplyService outsrcEmpAssessApplyService
            = (OutsrcEmpAssessApplyService) SpringUtil.getBean("outsrcEmpAssessApplyService");

    @Override
    public void executeInternal() throws JobExecutionException {
        try{
            log.warn(">>>>>>>>>>>>>>>>>>>>>>> 人员季度考核定时器执行开始<<<<<<<<<<<<<<<<<<<<<<<");
            outsrcEmpAssessApplyService.cronAnnualAssess();
            log.warn(">>>>>>>>>>>>>>>>>>>>>>> 人员季度考核定时器执行结束<<<<<<<<<<<<<<<<<<<<<<<");

        }catch (Exception e) {
            log.error("人员季度考核-定时器异常：", e);
        }
    }
}
