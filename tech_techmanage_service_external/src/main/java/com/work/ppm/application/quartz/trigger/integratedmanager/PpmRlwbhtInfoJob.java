package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingEtxRLJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 外包人员扩展表更新人力合同
 */
@Slf4j
public class PpmRlwbhtInfoJob implements IQuartzExecutorJob {
    /**
     * 外包人员扩展表更新人力合同
     */
    private IPpmOutsourcingEtxRLJobService ppmRlwbhtInfoJobService = (IPpmOutsourcingEtxRLJobService) SpringUtil.getBean("ppmRlwbhtInfoService");

    @Override
    public void executeInternal() {
         try{
             log.warn("=================> 更新人力合同 开始");
             ppmRlwbhtInfoJobService.createRlHt();
             log.warn("=================> 更新人力合同 结束");
         }catch(Exception e) {
            log.error("更新人力合同异常", e);
         }
    }
}
