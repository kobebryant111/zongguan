package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessApplyDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssessApply;

import java.util.List;

/**
 * 人员考核 接口描述
 *
 * @since 2023-01-03
 */
public interface IOutsrcEmpAssessApplyService {

    /**
     * 分页查询
     * @param outsrcAssessApply 对象
     * @param pager 分页
     * @return list
     */
    List<OutsrcEmpAssessApplyDto> queryPager(OutsrcEmpAssessApplyDto outsrcAssessApply, Pager pager);

    /**
     * 集合查询
     * @param outsrcAssessApply 对象
     * @return list
     */
    List<OutsrcEmpAssessApply> queryList(OutsrcEmpAssessApply outsrcAssessApply);

    /**
     * 根据id查询
     * @param id 编号
     * @return OutsrcEmpAssessApplyDto
     */
    OutsrcEmpAssessApplyDto queryDetailsById(Long id);

    /**
     * 根据流程id查询
     * @param instanceId 流程编号
     * @return OutsrcEmpAssessApplyDto
     */
    OutsrcEmpAssessApplyDto queryDetailsByInsId(String instanceId);

    /**
     * 新增
     * @param outsrcAssessApply 对象
     * @return int
     */
     int addOutsrcAssessApply(OutsrcEmpAssessApplyDto outsrcAssessApply);

    /**
     *  新增
     * @param outsrcAssessApply 对象
     * @return int
     */
     int addOutsrcAssessApplyCus(OutsrcEmpAssessApplyDto outsrcAssessApply);

    /**
     * 修改
     * @param outsrcAssessApply 对象
     * @return int
     */
     int updateOutsrcEmpAssessApply(OutsrcEmpAssessApply outsrcAssessApply);

    /**
     * 流程删除
     * @param outsrcAssessApply 对象
     * @return int
     */
     int removeFlow(OutsrcEmpAssessApply outsrcAssessApply);

    /**
     * 流程发起
     * @param apply 对象
     * @param bps 流程对象
     * @return string
     */
     String approvalCirculate(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps);

    /**
     * 二级部经理审批
     * @param apply 对象
     * @param bps 流程对象
     * @return string
     */
    String approvedByMgrL2(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps);

    /**
     * 存草稿
     * @param apply 申请信息
     * @param bps 流程信息
     * @return OutsrcEmpAssessApplyDto
     */
    OutsrcEmpAssessApplyDto draft(OutsrcEmpAssessApplyDto apply, BpsWorkFlowDto bps);

    /**
     * 调度执行年度考评
     */
    void cronYearAssess();
    /**
     * 调度执行季度考评
     */
    void cronAnnualAssess();

}