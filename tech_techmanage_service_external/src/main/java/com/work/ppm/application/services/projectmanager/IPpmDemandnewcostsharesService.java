package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.PpmDemandnewcostsharesDto;

import java.util.List;

/**
 * 分摊
 *
 */
public interface IPpmDemandnewcostsharesService {
    /**
     * 添加
     *
     * @param ppmDemandnewcostsharesDto 对象
     * @return int
     */
    int addPpmDemandnewcostshares(List<PpmDemandnewcostsharesDto> ppmDemandnewcostsharesDto);

    /**
     * 根据意向编号查询集合
     * @param demandno 意向编号
     * @return list
     */
    List<PpmDemandnewcostsharesDto> getList(String demandno);


}
