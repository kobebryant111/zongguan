package com.work.ppm.application.services.flowmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;

import java.util.List;

/**
 * 流程对象信息
 */
public interface IPpmProjectFlowService {

    /**
     * 分页
     * @param ppmProjectFlow 对象
     * @param pager 分页对象
     */

    void queryPager(PpmProjectFlowDto ppmProjectFlow, Pager pager) ;

    /**
     * 分页按日期区间
     * @param ppmProjectFlow 对象
     * @param pager 分页对象
     */

    void queryPagerDateBetween(PpmProjectFlowDto ppmProjectFlow, Pager pager,String dateBegin,String dateEnd) ;

    /**
     * 查询集合数据
     * @param ppmProjectFlow 对象
     * @return List<PpmProjectFlowDto>
     */

    List<PpmProjectFlowDto> queryList(PpmProjectFlowDto ppmProjectFlow) ;

    /**
     * 添加
     * @param ppmProjectFlow 对象
     * @return int
     */
    int addPpmProjectFlow(PpmProjectFlow ppmProjectFlow) ;

    /**
     * 查询详情
     * @param ppmProjectFlow 对象
     * @return PpmProjectFlow
     */
    PpmProjectFlow queryDetail(PpmProjectFlow ppmProjectFlow) ;

    /**
     * 更新
     * @param ppmProjectFlow 对象
     * @return Integer
     */
    Integer updatePpmProjectFlow(PpmProjectFlow ppmProjectFlow) ;

    /**
     * 删除
     * @param ppmProjectFlow 对象
     * @return Integer
     */
    Integer deletePpmProjectFlow(PpmProjectFlow ppmProjectFlow) ;

    /**
     * 根据条件查询集合
     * @param entity 对象
     * @return List<PpmProjectFlow>
     */
    List<PpmProjectFlow> queryList(PpmProjectFlow entity) ;

    /**
     * 查询详情
     * @param instanceid 流程id
     * @return  PpmProjectFlow
     */
    PpmProjectFlow queryDetail(String instanceid) ;

    /**
     * 查询详情
     * @param projectno 对象编号
     * @return PpmProjectFlowDto
     */
    PpmProjectFlowDto queryDetailForProjectno(String projectno);

    /**
     * 根据流程instanceid更新流程状态
     * @param ppmProjectFlowDto
     * @return int
     */
    int updateProjectFlowByInstanceId(PpmProjectFlowDto ppmProjectFlowDto);
}
