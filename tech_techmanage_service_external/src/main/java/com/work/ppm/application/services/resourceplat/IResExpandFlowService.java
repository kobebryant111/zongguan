package com.work.ppm.application.services.resourceplat;

import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.resourceplat.ResExpandDto;
import com.work.ppm.application.model.resourceplat.ResAdded;
/**
 * 资源扩容业务处理
 */
public interface IResExpandFlowService {
    /**
     * 根据流程实例ID，获取业务信息
     * @param processInstId
     *  @return ResAdded
     */
    ResAdded queryBusInfoByProcessInstId(String processInstId);

    /**
     * 提交工作项
     * @param flowDto
     *
     * @return true/false
     */
    boolean submitWorkItem(FlowDto flowDto);

    /**
     * 重新提交发起工作项
     * @param flowDto
     * @param resExpandDto
     * @return true/false
     */
    boolean reSubmitWorkFlow(FlowDto flowDto, ResExpandDto resExpandDto);
}
