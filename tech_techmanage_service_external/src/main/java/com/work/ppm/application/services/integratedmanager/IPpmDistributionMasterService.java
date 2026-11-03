package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmDistributionMasterDto;

/**
  * 入职部门分配主表
  */
public interface IPpmDistributionMasterService {


    /**
     * 添加
     * @param ppmDistributionMasterDto 对象
     * @return int
     */
     @Deprecated
    int addPpmDistributionMaster(PpmDistributionMasterDto ppmDistributionMasterDto) ;

    /**
     * 查询详情
     * @param ppmDistributionMasterDto 对象
     * @return PpmDistributionMasterDto
     */
    PpmDistributionMasterDto queryDetail(PpmDistributionMasterDto ppmDistributionMasterDto) ;

    /**
     * 更新
     * @param ppmDistributionMasterDto  对象
     * @return int
     */

    int updatePpmDistributionMaster(PpmDistributionMasterDto ppmDistributionMasterDto) ;

    /**
     * 流程流转
     * @param ppmDistributionMasterDto 对象
     * @return ApiResult
     */
    String liuzhuan(PpmDistributionMasterDto ppmDistributionMasterDto);

    /**
     * 处理人审批
     * @param ppmDistributionMasterDto 对象
     * @return String 流转后的节点信息
     */
    String processApproval(PpmDistributionMasterDto ppmDistributionMasterDto);
}
