package com.work.ppm.application.services.workflowmanager;

import com.work.ppm.application.dto.workflowmanager.CommonApproveProcessDto;
import com.work.ppm.application.dto.workflowmanager.InterviewApproveProcessDto;

import java.util.Map;

/**
 *  外部系统访问 工作流输出
 */
public interface IWorkflowOutsourcingService extends IWorkflowOutputService {

    /**
     * 通用审批接口
     * @param approveProcessDto 对象
     * @return map
     */
    Map<String,Object> commonApproveProcess(CommonApproveProcessDto approveProcessDto);

    /**
     * 外包人员面试 审批接口
     * @param approveProcessDto 对象
     * @return map
     */
    Map<String,Object> interviewApproveProcess(InterviewApproveProcessDto approveProcessDto);

    /**
     * 人力外包需求详情接口
     * @param instanceId 详情对象
     * @return map
     */
    Map<String,Object> queryDemandDetail(String instanceId);

    /**
     * 外包人员面试详情接口
     * @param instanceId 详情对象
     * @return map
     */
    Map<String,Object> queryInterviewDetail(String instanceId);

    /**
     * 外包人员准入详情接口
     * @param instanceId 详情对象
     * @return map
     */
    Map<String,Object> queryAccessDetail(String instanceId);


}
