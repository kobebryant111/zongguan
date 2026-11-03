package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;

/**
 * 公共流程删除接口
 */
public interface ICommonDeleteFlowService {
    /**
     * 删除记录
     */
    void doDelete(BpsWorkFlowDto bps) ;
}
