package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IDirectConnectionJob;
import com.work.ppm.application.quartz.interfaces.ISyncDataJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class PpmDirectConnectionJob implements IQuartzExecutorJob {
    /**
     * 同步采购系统合同信息接口
     */
    private IDirectConnectionJob iDirectConnectionJob =
            (IDirectConnectionJob) SpringUtil.getBean("DirectConnectionJobService");

    @Override
    @Transactional
    public void executeInternal() throws JobExecutionException {
        try {
            log.warn("=================> 同步采购系统合同信息 开始");
            iDirectConnectionJob.purchaseDiret();
            log.warn("=================> 同步采购系统合同信息 结束");
        } catch (Exception e) {
            log.error("同步采购系统合同信息 异常", e);
        }
    }
}
