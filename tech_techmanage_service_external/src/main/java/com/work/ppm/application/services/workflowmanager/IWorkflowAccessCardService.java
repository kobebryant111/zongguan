package com.work.ppm.application.services.workflowmanager;

import com.work.ppm.application.dto.workflowmanager.CommonApproveParamsDto;

import java.util.Map;

/**
 *  外部系统访问 工作流输出
 */
public interface IWorkflowAccessCardService extends IWorkflowOutputService{

    /**
     * 通用审批接口
     * @param approveParamsDto 对象
     * @return map
     */
    Map<String,Object> commonApproveProcess(CommonApproveParamsDto approveParamsDto);

    /**
     * 门禁卡申请详情接口
     * @param instanceId 详情对象
     * @return map
     */
    Map<String,Object> queryAccessCardDetail(String instanceId);

    /**
     * 门禁卡归还详情接口
     * @param instanceId 详情对象
     * @return map
     */
    Map<String,Object> queryReturnCardDetail(String instanceId);

    /**
     * 推送待办任务 - 星云
     * @param userId 处理人
     * @param imgTxTtl 标题
     * @param imgTxDsc 详情
     * @param taskId 任务编号
     * @param type 任务编号
     */
    void pushTaskCommon(String userId,String imgTxTtl,String imgTxDsc,String instId,Object taskId,String type);



}
