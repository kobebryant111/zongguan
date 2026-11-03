package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmDacenterDemandJobService;
import com.work.ppm.application.quartz.interfaces.PpmPostGovernorJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * 后督管理流程  数据中心后督管理员角色人员同步接口
 */
@Slf4j
public class PpmPostGovernorJob implements IQuartzExecutorJob {

    /**
     * 数据中心后督管理员角色人员同步接口
     */
    private PpmPostGovernorJobService ppmPostGovernorJobService = (PpmPostGovernorJobService)
            SpringUtil.getBean("ppmPostGovernorService");

    @Override
    public void executeInternal() throws JobExecutionException {
        try{
            log.info("=================> 数据中心后督管理员角色人员同步接口 开始");
            ppmPostGovernorJobService.upPostGovernorAdminInfo();
            log.info("=================> 数据中心后督管理员角色人员同步接口 结束");
        }catch (Exception e) {
            log.error("数据中心后督管理员角色人员同步接口 异常", e);
        }
    }
}
