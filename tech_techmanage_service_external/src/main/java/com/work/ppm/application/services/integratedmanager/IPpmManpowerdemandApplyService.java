package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.ManpowerdemandsInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyOrgDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmManpowerdemandApplyInfoInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApply;
import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;

import java.util.List;

/**
 * 人力需求主表
 */
public interface IPpmManpowerdemandApplyService {

    /**
     * 分页
     * @param ppmManpowerdemandApply 对象
     * @param pager 对象
     */
    void queryPager(PpmManpowerdemandApply ppmManpowerdemandApply, Pager pager) ;

    /**
     * 添加
     * @param applyDto 对象
     * @return int
     */
    int addPpmManpowerdemandApply(PpmManpowerdemandApplyDto applyDto) ;

    /**
     * 查询详情
     * @param apply 对象
     * @return PpmManpowerdemandApply
     */
    @Deprecated
    PpmManpowerdemandApply queryDetail(PpmManpowerdemandApply apply) ;

    /**
     * 更新
     * @param apply 对象
     * @return Integer
     */
    Integer updatePpmManpowerdemandApply(PpmManpowerdemandApply apply) ;

    /**
     * 删除,包括子表相关数据
     * @param apply 对象
     * @return Integer
     */
    Integer deletePpmManpowerdemandApply(PpmManpowerdemandApply apply) ;

    /**
     * 保存人力需求变更
     * @param applyDto 对象
     * @param infos 集合
     * @param user session
     * @return PpmManpowerdemandApplyDto
     */
    PpmManpowerdemandApplyDto save(PpmManpowerdemandApplyDto applyDto,
                                   List<PpmManpowerdemandApplyInfo> infos,CommOperatorDoLoginDto user) ;
    /**
     * 发起人力需求变更
     * @param applyDto 对象
     * @param bps 对象
     * @return String
     */
    String liuzhuan(PpmManpowerdemandApplyDto applyDto, BpsWorkFlowDto bps)  ;

    /**
     * 二级部经理审批
     * @param applyDto 对象
     * @param bps 对象
     * @return String
     */
    String twoManagerAudit(PpmManpowerdemandApplyDto applyDto, BpsWorkFlowDto bps) ;

    /**
     * 一级部总经理室审批
     * @param applyDto 对象
     * @param bps 对象
     * @return String
     */
    String confirm(PpmManpowerdemandApplyDto applyDto, BpsWorkFlowDto bps) ;

    /**
     * 保存人力需求流程数据
     * @param apply 对象
     * @return int
     */
    int savePpmManpowerDemandApply(PpmManpowerdemandApply apply);

    /**
     * 根据权限查询人力需求数据分页
     * @param infoDto 对象
     * @param pager 分页对象
     * @param user session
     * @return List<PpmManpowerdemandApplyInfoInfoDto>
     */
    List<PpmManpowerdemandApplyInfoInfoDto> queryPagerList(ManpowerdemandsInfoDto infoDto,
                                                           Pager pager, CommOperatorDoLoginDto user);

    /**
     * 根据流程ID查询流程信息
     * @param apply 对象
     * @return PpmManpowerdemandApplyOrgDto
     */
    PpmManpowerdemandApplyOrgDto queryDetails(PpmManpowerdemandApply apply);


    /**
     * 更新人力需求状态
     * @param applyInfo 对象
     * @return Integer
     */
    Integer updateStatus(PpmManpowerdemandApplyInfo applyInfo);

    /**
     * 面试调用更新人力需求方法updateStatus
     * @param id 编号
     * @return int
     */
    int updateFlowStatus(long id);
}
