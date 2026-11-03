package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.commons.pager.Pager;
import com.work.workflow.client.soentity.BPSWorkItem;

import java.util.List;

/**
 * 公共流程接口
 */
public interface ICommonFlowService {
    /**
     * 我的待办
     * @param dto 对象
     * @return List<BPSWorkItem>
     */
    List<BPSWorkItem> getdbWorkItem(BpsWorkFlowDto dto) ;

    /**
     * 我的已办
     * @param dto 对象
     * @return List<BPSWorkItem>
     */
    List<BPSWorkItem> getybWorkItem(BpsWorkFlowDto dto) ;

    /**
     * 我的已办
     * @param dto 对象
     */
    void getYbWorkItem(PpmProjectFlowDto dto, Pager pager) ;

    /**
     * 我的归档
     * @param dto 对象
     */
    void getGdWorkItem(PpmProjectFlowDto dto, Pager pager) ;

    /**
     * 授权列表
     * @param dto 对象
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> getAuthList(PpmProjectFlowDto dto) ;

    /**
     * 执行授权
     * @param dto 对象
     * @return String
     */
    String doAuth(BpsWorkFlowDto dto) ;

    /**
     * 删除流程
     * @param bps 对象
     * @return String
     */
    String doDel(BpsWorkFlowDto bps) ;

    /**
     * 撤回流程
     * @param bps 对象
     * @return String
     */
    String drawbackWorkItem(BpsWorkFlowDto bps) ;

    /**
     * 判断是否可以撤回工作项
     * @param bps 对象
     * @return BpsWorkFlowDto
     */
    BpsWorkFlowDto doIsDrawbackEnable(BpsWorkFlowDto bps);

    /**
     * 查询已完成但未结束的流程信息
     * @param bps 对象
     * @return BPSWorkItem
     */
    BPSWorkItem doFinishedLastWorkitem(BpsWorkFlowDto bps) ;

    /**
     * 获取待办工作项信息详情
     * @param bps 对象
     * @return BPSWorkItem
     */
    BPSWorkItem queryWorkItem(BpsWorkFlowDto bps);

    /**
     * 获取归档详情
     * @param instanceid
     * @return BPSWorkItem
     */
    PpmProjectFlowDto queryGdDetail(String instanceid);

    /**
     * 查询流程状态
     * @param bps 对象
     * @return true：结束，false：未结束
     */
    boolean getProcessInstState(BpsWorkFlowDto bps);

    /**
     * 删除oa通知待办
     * @param bps 对象
     */
    void delOaNotice(BpsWorkFlowDto bps);
}
