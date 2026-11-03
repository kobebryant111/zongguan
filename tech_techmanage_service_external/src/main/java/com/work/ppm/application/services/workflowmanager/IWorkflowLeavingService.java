package com.work.ppm.application.services.workflowmanager;

import com.work.ppm.application.dto.workflowmanager.CommonApproveProcessDto;

import java.util.Map;

/**
 *  外部系统访问 外包人员离场
 */
public interface IWorkflowLeavingService extends IWorkflowOutputService{

    /**
     * 通用审批接口
     * @param approveProcessDto 对象
     * @return map
     */
    Map<String,Object> commonApproveProcess(CommonApproveProcessDto approveProcessDto);

    /**
     * 门禁卡申请详情接口
     * @param instanceId 详情对象
     * @return map
     */
    Map<String,Object> queryLeavingDetail(String instanceId);

    /**
     * 推送待办任务 - 星云
     * @param userId 处理人
     * @param imgTxTtl 标题
     * @param imgTxDsc 详情
     * @param taskId 任务编号
     */
    void pushTaskCommon(String userId,String imgTxTtl,String imgTxDsc,String instId,Object taskId);

}
