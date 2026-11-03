package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmFlowSlainfoDto;
import com.work.ppm.commons.pager.Pager;

/**
  * sla 流程信息
  */
public interface IPpmFlowSlainfoService {

    /**
     * 分页
     * @param ppmFlowSlainfoDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmFlowSlainfoDto ppmFlowSlainfoDto, Pager pager) ;

    /**
     * 添加
     * @param bpsInfo 节点信息
     * @param ppmFlowSlainfoDto 对象
     * @return int
     */
    int addPpmFlowSlainfo(String bpsInfo, PpmFlowSlainfoDto ppmFlowSlainfoDto) ;

    /**
     * 查询详情
     * @param ppmFlowSlainfoDto 对象
     * @return PpmFlowSlainfoDto
     */
    PpmFlowSlainfoDto queryDetail(PpmFlowSlainfoDto ppmFlowSlainfoDto) ;

    /**
     * 查询详情
     * @param instanceId 流程id
     * @param activityId 活动id
     * @return PpmFlowSlainfoDto
     */
    PpmFlowSlainfoDto queryDetail(String instanceId, String activityId) ;

    /**
     * 查询详情
     * @param instanceId 流程id
     * @return PpmFlowSlainfoDto
     */
    PpmFlowSlainfoDto queryDetail(String instanceId) ;


    /**
     * 更新
     * @param bpsInfo 节点信息
     * @param ppmFlowSlainfoDto  对象
     * @return int
     */
    int updatePpmFlowSlainfo(String bpsInfo,PpmFlowSlainfoDto ppmFlowSlainfoDto) ;

    /**
     * 更新
     * @param instanceId 流程id
     * @return int
     */
    int updatePpmFlowSlainfo(String instanceId) ;

    /**
     * 删除
     * @param ppmFlowSlainfoDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmFlowSlainfo(PpmFlowSlainfoDto ppmFlowSlainfoDto) ;

    /**
     *  获取预计完成时间
     * @param instanceId 流程id
     * @return PpmFlowSlainfoDto
     */
    PpmFlowSlainfoDto queryExpectedCompletionTime(String instanceId);
}
