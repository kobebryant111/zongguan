package com.work.ppm.application.quartz.trigger.vendormanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.vendormanager.IPaymentConfirmService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

@Slf4j
public class PaymentConfirmJob implements IQuartzExecutorJob {

    /**
     * 用户角色
     */
    private IPaymentConfirmService iPaymentConfirmService
            = (IPaymentConfirmService) SpringUtil.getBean("paymentConfirmService");

    @Override
    public void executeInternal() throws JobExecutionException {
        try{
            iPaymentConfirmService.cronJob();
            log.warn(">>>>>>>>>>>>>>>>>>>>>>>季度支付确认定时器执行结束<<<<<<<<<<<<<<<<<<<<<<<");
        }catch (Exception e) {
            log.error("定时器异常", e);
        }
    }
}
