package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmDacenterDemandJobService;
import com.work.ppm.commons.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;

/**
 * 科管数据中心需求管理员角色员工信息同步
 */
@Slf4j
public class PpmDacenterDemandJob implements IQuartzExecutorJob {

    /**
     * 科管数据中心需求管理员角色员工信息同步接口
     */
    private IPpmDacenterDemandJobService ppmDacenterDemandService = (IPpmDacenterDemandJobService)
            SpringUtil.getBean("ppmDacenterDemandService");

    @Override
    public void executeInternal() throws JobExecutionException {
        try{
            log.info("=================> 中心需求管理员角色员工信息同步 开始");
            ppmDacenterDemandService.upDacenterAdminInfo();
            log.info("=================> 中心需求管理员角色员工信息同步 结束");
        }catch (Exception e) {
            log.error("中心需求管理员角色员工信息同步 异常", e);
        }
    }
}
