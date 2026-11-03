package com.work.ppm.application.quartz.trigger.suppliermanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmSupplierRiskEvalCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时创建供应商风险评估流程
 */
@Slf4j
public class PpmSupplierRiskEvalFlowJob implements IQuartzExecutorJob {
    /**
     * 定时创建供应商考核流程
     */
    private IPpmSupplierRiskEvalCalculateJobService ppmSupplierRiskEvalCalculateJobService =
            (IPpmSupplierRiskEvalCalculateJobService) SpringUtil.getBean("ppmSupplierRiskEvalService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时创建供应商风险评估流程 开始");
            ppmSupplierRiskEvalCalculateJobService.supplierRiskEvalFlow();
            log.warn("=================> 定时创建供应商风险评估流程 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时创建供应商风险评估流程 异常", e);
        }

    }

}
