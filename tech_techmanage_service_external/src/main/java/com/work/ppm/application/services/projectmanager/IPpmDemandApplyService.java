package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmDemandApplyDto;

/**
 * 老的业务意向
 */
public interface IPpmDemandApplyService {


    /**
     * 添加
     * @param ppmDemandApplyDto 对象
     * @return int
     */
    int addPpmDemandApply(PpmDemandApplyDto ppmDemandApplyDto) ;

    /**
     * 根据projectno获取需求
     * @param projectno
     * @return PpmDemandApplyDto
     */
    PpmDemandApplyDto getdemandByProjectno(String projectno);

    /**
     * 根据demandno获取需求
     * @param demandno
     * @return PpmDemandApplyDto
     */
    PpmDemandApplyDto getdemandByDemandno(String demandno);
}
