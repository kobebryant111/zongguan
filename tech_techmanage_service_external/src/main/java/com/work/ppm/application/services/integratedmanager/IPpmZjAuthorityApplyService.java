package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityApplyDto;
import com.work.ppm.commons.pager.Pager;

/**
 * 闸机权限申请表 接口描述
 *
 */
public interface IPpmZjAuthorityApplyService {

    /**
     * 分页
     *
     * @param authorityApplyDto 对象
     * @param pager             分页对象
     */
    void queryPager(PpmZjAuthorityApplyDto authorityApplyDto, Pager pager);

    /**
     * 添加
     *
     * @param authorityApplyDto 对象
     * @return int
     */
    int addPpmZjAuthorityApply(PpmZjAuthorityApplyDto authorityApplyDto);

    /**
     * 查询详情
     *
     * @param authorityApplyDto 对象
     * @return PpmZjAuthorityApplyDto
     */
    PpmZjAuthorityApplyDto queryDetail(PpmZjAuthorityApplyDto authorityApplyDto);

    /**
     * 更新
     *
     * @param authorityApplyDto 对象
     * @return int
     */
    int updatePpmZjAuthorityApply(PpmZjAuthorityApplyDto authorityApplyDto);

    /**
     * 删除
     *
     * @param authorityApplyDto 对象
     * @return int
     */
    int deletePpmZjAuthorityApply(PpmZjAuthorityApplyDto authorityApplyDto);


    /**
     * 存草稿
     *
     * @param applyDto 对象
     * @return String
     */
    PpmZjAuthorityApplyDto daftPpmZjAuthorityApply(PpmZjAuthorityApplyDto applyDto,
                                                   BpsWorkFlowDto bps);


    /**
     * 流转
     *
     * @param applyDto 对象
     * @return int
     */
    String apply(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps);

    /**
     * 二级部负责人审批
     * @param applyDto
     * @param bps
     * @return
     */
    String twoManagerAudit(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps);

    /**
     * 一级部负责人审批
     * @param applyDto
     * @param bps
     * @return
     */
    String topManagerAudit(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps);
}
