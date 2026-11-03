package com.work.ppm.application.quartz.trigger.safetymanager;

import com.alibaba.fastjson.JSON;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.safetymanager.ISafetyCommissionerEmployeeSyncService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * packageName com.work.ppm.application.quartz.trigger.safetyCommissioner
 *
 * @version JDK 8
 * @className SafetyCommissionerEmployeeSync
 * @date 2025/6/4
 * @description 人力资源系统同步安全专员人员信息
 */
@Slf4j
public class SafetyCommissionerEmployeeSync implements IQuartzExecutorJob {
    /**
     * 行员考勤信息计算
     */
    private ISafetyCommissionerEmployeeSyncService iSafetyCommissionerEmployeeSyncService =
            (ISafetyCommissionerEmployeeSyncService) SpringUtil.getBean("syncEmployeeMsgService");

    @Override
    public void executeInternal() throws JobExecutionException {
        log.info("总行人力资源信息同步到安全专员开始");
        try{
            log.info("=================> 总行人力资源信息同步到安全专员 开始");
            iSafetyCommissionerEmployeeSyncService.syncEmployeeMsg();
            log.info("=================> 总行人力资源信息同步到安全专员 结束");
        }catch (Exception e) {
            e.printStackTrace();
            log.error("总行人力资源信息同步到安全专员异常,异常信息为:{}", JSON.toJSONString(e));
        }
    }
}
