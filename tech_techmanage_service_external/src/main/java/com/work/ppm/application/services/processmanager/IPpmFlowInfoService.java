package com.work.ppm.application.services.processmanager;

import com.work.ppm.application.dto.processmanager.PpmFlowInfoDto;
import com.work.ppm.application.model.processmanager.PpmFlowInfo;

import java.util.List;

/**
 * 流程信息
 */
public interface IPpmFlowInfoService {
    /**
     * 添加
     * @param ppmFlowInfo 对象
     * @return 分页对象
     */
     @Deprecated
    int addPpmFlowInfo(PpmFlowInfo ppmFlowInfo) ;

    /**
     * 查询详情
     * @param ppmFlowInfo 对象
     * @return PpmFlowInfo
     */
    PpmFlowInfo queryDetail(PpmFlowInfo ppmFlowInfo) ;

    /**
     * 更新
     * @param ppmFlowInfo 对象
     * @return Integer
     */
    Integer updatePpmFlowInfo(PpmFlowInfo ppmFlowInfo) ;

    /**
     * 删除
     * @param ppmFlowInfo 对象
     * @return Integer
     */
    Integer deletePpmFlowInfo(PpmFlowInfo ppmFlowInfo) ;

    /**
     * 查询列表
     * @param ppmFlowInfo 对象
     * @return List<PpmFlowInfoDto>
     */
    List<PpmFlowInfoDto> queryList(PpmFlowInfo ppmFlowInfo);

    /**
     * 查询详情
     * @param flownodeCode 流程节点名称
     * @param flowdraftno 流程实例名称
     * @return PpmFlowInfo
     */
    PpmFlowInfo queryDetail(String flownodeCode,String flowdraftno) ;
}
