package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.vendorcontract.AttatchMUApplyDto;
import com.work.ppm.application.model.vendormanager.AttatchMUApply;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 附件管理更新申请 接口描述
 * </p>
 *
 * @since 2022-11-02
 */


public interface IAttatchMUApplyService {

    /**
     * 详情
     * @param id /
     */
    AttatchMUApply queryDetailsById(Long id);
    /**
     * 查询附件管理已用人数
     * @param id /
     */
    int queryNumberUsedById(Long id);

    /**
     * 申请是否发起
     * @param attatchMUApply /
     */
    boolean isApplied(AttatchMUApply attatchMUApply);

    /**
     * 新增
     * @param attatchMUApply 实例
     */
    int addAttatchMUApply(AttatchMUApply attatchMUApply);

    /**
     * 编辑
     * @param attatchMUApply 实例
     */
    Integer updateAttachMUApply(AttatchMUApply attatchMUApply);

    /**
     * 附件更新
     * @param attatchMUApply 实例
     */
    int updateAttachByCalculate(AttatchMUApply attatchMUApply);

    /**
     * 流程删除
     * @param attatchMUApply 实例
     */
    Integer deleteFlowAttachApply(AttatchMUApply attatchMUApply);

    /**
     * selectByMapBasic
     * @param map /
     */
    List<AttatchMUApply> selectByMapBasic(HashMap<String, Object>  map);

    /**
     * 流程确认
     * @param dto /
     * @param bps /
     */
    String confirm(AttatchMUApplyDto dto, BpsWorkFlowDto bps);
    /**
     * 流转
     * @param dto /
     * @param bps /
     */
    String approvalCirculate(AttatchMUApplyDto dto, BpsWorkFlowDto bps);

}