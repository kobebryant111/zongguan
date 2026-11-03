package com.work.ppm.application.quartz.trigger.suppliermanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmSupplierCalculateJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时创建供应商考核人力类流程
 */
@Slf4j
public class PpmSupplierAssessFlowRlJob implements IQuartzExecutorJob {
    /**
     * 定时创建供应商考核流程
     */
    private IPpmSupplierCalculateJobService ppmSupplierCalculateJobService =
            (IPpmSupplierCalculateJobService) SpringUtil.getBean("ppmSupplierAssessService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时创建供应商考核人力类流程 开始");
            ppmSupplierCalculateJobService.supplierAssessFlowRl();
            log.warn("=================> 定时创建供应商考核人力类流程 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时创建供应商考核流程 异常", e);
        }

    }

}
