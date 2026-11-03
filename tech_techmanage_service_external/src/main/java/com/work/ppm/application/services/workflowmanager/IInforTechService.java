package com.work.ppm.application.services.workflowmanager;

import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;

public interface IInforTechService {
    /**
     * 推送待办任务 - 星云
     * @param userId 处理人
     * @param imgTxTtl 标题
     * @param imgTxDsc 详情
     * @param taskId 任务编号
     */
    void pushTaskCommon(String userId,String imgTxTtl,String imgTxDsc,String instId,Object taskId);
    /**
     * 删除待办任务 - 星云
     * @param ppmAppWorkflowInfoDto 对象
     */
    void deleteTaskXy(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto);
}
