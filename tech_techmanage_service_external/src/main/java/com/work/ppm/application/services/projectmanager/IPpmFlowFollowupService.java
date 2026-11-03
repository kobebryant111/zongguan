package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmFlowFollowupDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;

/**
 * 后督流程
 */
public interface IPpmFlowFollowupService {

    /**
     * 查询详情
     * @param ppmFlowFollowupDto 对象
     * @return PpmFlowFollowupDto
     */
    PpmFlowFollowupDto queryDetail(PpmFlowFollowupDto ppmFlowFollowupDto) ;

    /**
     * 发起流程
     * @param ppmFlowFollowupDto 对象
     */
    void initialeFlow(PpmFlowFollowupDto ppmFlowFollowupDto, CommOperatorDoLoginDto user);
}
