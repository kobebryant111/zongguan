package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.services.resourceplat.IResNoticeConfigService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 根据通知配置定时发送信息给通知配置用户
 */
@Slf4j
public class ResNoticeConfigJob implements IQuartzExecutorJob {
    /**
     * 通知配置
     */
    private IResNoticeConfigService resNoticeConfigService = (IResNoticeConfigService) SpringUtil.getBean(
            "ResNoticeConfigService");

    /**
     * 根据通知配置定时发送信息给通知配置用户定时器
     */
    @Override
    public void executeInternal() {
        try{
            log.info(">>>>>>>>>>>>>>>>> ResNoticeConfigJob executeInternal <<<<<<<<<<<<<<<<<<<<<");
            resNoticeConfigService.doNotice();
        }catch (Exception e) {
            log.error("根据通知配置定时发送信息给通知配置用户定时器异常", e);
        }
    }
}
