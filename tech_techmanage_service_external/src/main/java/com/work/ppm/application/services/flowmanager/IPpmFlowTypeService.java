package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmFlowTypeDto;
import com.work.ppm.application.model.flowmanager.PpmFlowType;

import java.util.List;

/**
 * 流程类型
 */
public interface IPpmFlowTypeService {

    /**
     * 流程树
     * @param ppmFlowType 对象
     * @return List<PpmFlowTypeDto>
     */
    List<PpmFlowTypeDto> queryFlowTree(PpmFlowType ppmFlowType) ;

    /**
     * 查询详情
     * @param ppmFlowType 对象
     * @return PpmFlowType
     */
    PpmFlowType queryDetail(PpmFlowType ppmFlowType) ;

    /**
     * 更新
     * @param ppmFlowType 对象
     * @return Integer
     */
    Integer updatePpmFlowType(PpmFlowType ppmFlowType) ;

    /**
     * 删除
     * @param ppmFlowType 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmFlowType(PpmFlowType ppmFlowType) ;

    /**
     * 查询所有流程类型
     * @return List<PpmFlowTypeDto>
     */
    List<PpmFlowTypeDto> getListAll();

    /**
     * 两级流程树
     * @param ppmFlowType 对象
     * @return  List<PpmFlowTypeDto>
     */
    List<PpmFlowTypeDto> queryTwoLevelFlowTree(PpmFlowType ppmFlowType) ;

    /**
     * 查询详情
     * @param flowdraftno 流程定义id
     * @return PpmFlowType
     */
    PpmFlowType queryDetail(String flowdraftno) ;

}
