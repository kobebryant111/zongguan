package com.work.ppm.application.quartz.trigger.vendormanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.vendormanager.IOutsrcingAttrChgService;
import com.work.ppm.application.services.vendormanager.IOutsrcingPriceChgService;
import com.work.ppm.application.services.vendormanager.impl.OutsrcingAttrChgService;
import com.work.ppm.application.services.vendormanager.impl.OutsrcingPriceChgService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

@Slf4j
public class AttrPriceChgJob implements IQuartzExecutorJob {

    /**
     * 单价调整
     */
    private IOutsrcingPriceChgService outsrcingPriceChgService
            = (OutsrcingPriceChgService) SpringUtil.getBean("outsrcingPriceChgService");

    /**
     * 单价调整
     */
    private IOutsrcingAttrChgService outsrcingAttrChgService
            = (OutsrcingAttrChgService) SpringUtil.getBean("outsrcingAttrChgService");

    @Override
    public void executeInternal() throws JobExecutionException {
        try{
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>单价调整定时器执行开始<<<<<<<<<<<<<<<<<<<<<<<");
            outsrcingPriceChgService.priceCron();
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>单价调整定时器执行结束<<<<<<<<<<<<<<<<<<<<<<<");
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>人员属性变更定时器执行开始<<<<<<<<<<<<<<<<<<<<<<<");
            outsrcingAttrChgService.attrCron();
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>人员属性变更定时器执行结束<<<<<<<<<<<<<<<<<<<<<<<");
        }catch (Exception e) {
            log.error("定时器异常", e);
        }
    }
}
