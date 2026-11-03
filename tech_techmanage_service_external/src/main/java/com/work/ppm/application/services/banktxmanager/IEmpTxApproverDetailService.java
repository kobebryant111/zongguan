package com.work.ppm.application.services.banktxmanager;


import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

/**
 * 行员调休信息表 接口描述
 *
 */
public interface IEmpTxApproverDetailService {
    /**
     * 分页
     *
     * @param integInfoDto 对象
     * @param pager        分页对象
     */
    void queryPagerForApprover(IntegInfoDto integInfoDto, Pager pager, CommOperatorDoLoginDto user);

}
