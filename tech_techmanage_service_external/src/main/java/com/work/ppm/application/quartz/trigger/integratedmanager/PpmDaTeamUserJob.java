package com.work.ppm.application.quartz.trigger.integratedmanager;

import com.work.ppm.commons.utils.SpringUtil;
import com.work.ppm.application.quartz.core.IQuartzExecutorJob;
import com.work.ppm.application.quartz.interfaces.IPpmDaTeamUserJobService;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步老应用外包数据
 */
@Slf4j
public class PpmDaTeamUserJob implements IQuartzExecutorJob {
    /**
     * 团队小组接口
     */
    private IPpmDaTeamUserJobService ppmDaTeamUserJobService = (IPpmDaTeamUserJobService)
            SpringUtil.getBean("ppmDaTeamUserService");
    @Override
    public void executeInternal() {
        try{
            log.warn("=================> 组织树同步新应用外包岗位和团队信息 开始");
            ppmDaTeamUserJobService.updateBasicInfo();
            log.warn("=================> 组织树同步新应用外包岗位和团队信息 结束");
        }catch (Exception e) {
            log.error("组织树同步新应用外包岗位和团队信息 异常", e);
        }

    }

}
