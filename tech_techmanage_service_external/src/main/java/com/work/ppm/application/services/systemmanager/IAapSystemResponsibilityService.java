package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.AapSystemResponsibilityDto;

import java.util.List;

/**
 * 系统负责人
 */
public interface IAapSystemResponsibilityService {

    /**
     * 查询科技部经理
     * @param sysNo 系统编号
     * @return AapSystemResponsibilityDto
     */
    AapSystemResponsibilityDto queryDetail(String sysNo) ;
    /**
     * 查询详情
     * @param aapSystemResponsibilityDto 对象
     * @return AapSystemResponsibilityDto
     */
    AapSystemResponsibilityDto queryDetail(AapSystemResponsibilityDto aapSystemResponsibilityDto) ;

    /**
     * 查询系统负责人AB岗信息
     * @param sysNo 系统编号
     * @param instanceid 流程id
     * @return AapSystemResponsibilityDto
     */
    List<AapSystemResponsibilityDto> querySystemHeadAb(String sysNo,String instanceid) ;

    /**
     * 查询系统负责人AB岗信息
     * @param sysNo 系统编号
     * @return AapSystemResponsibilityDto
     */
    List<AapSystemResponsibilityDto> querySystemHeadAb(String sysNo) ;

    /**
     * 获取系统科技分管经理
     * @param aapSystemResDto
     * @return AapSystemResponsibilityDto
     */
    AapSystemResponsibilityDto getSysKeji(AapSystemResponsibilityDto aapSystemResDto);
}
