package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmStaffTransferJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时移交人员移交数据
 */
@Slf4j
public class PpmStaffTransferTimeJob implements IQuartzExecutorJob {
    /**
     * 定时移交人员移交数据
     */
    private IPpmStaffTransferJobService ppmStaffTransferJobService =
            (IPpmStaffTransferJobService) SpringUtil.getBean("ppmStaffTransferService");

    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 定时移交人员移交数据 开始");
            ppmStaffTransferJobService.handleTransferTime();
            log.warn("=================> 定时移交人员移交数据 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("定时移交人员移交数据 异常", e);
        }

    }

}
