package com.work.ppm.application.services.workflowmanager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.tansun.workflow.model.TaskBean;

import java.util.List;

/**
 *  外部系统访问 工作流输出
 */
public interface IWorkflowOutputService {

    /**
     * 推送待办任务 - esb
     * @param userId 处理人
     * @param imgTxTtl 标题
     * @param imgTxDsc 详情
     * @param imgTxUrl url
     * @param taskId 任务编号
     */
    void pushTask(String userId,String imgTxTtl,String imgTxDsc,String imgTxUrl,String instId,Object taskId);

    /**
     * 推送待办任务 - 星云通用
     * @param userId 处理人
     * @param imgTxTtl 标题
     * @param imgTxDsc 详情
     * @param taskId 任务编号
     * @param url url
     */
    void pushTaskXy(String userId,String imgTxTtl,String imgTxDsc,String instId,Object taskId,String url);
    /**
     * 删除待办任务 - esb
     * @param workflowInfoDto 对象
     * @param imgTxUrl url
     */
    void deleteTask(PpmAppWorkflowInfoDto workflowInfoDto, String imgTxUrl);

    /**
     * 删除待办任务 - 星云
     * @param workflowInfoDto 对象
     */
    void deleteTaskXy(PpmAppWorkflowInfoDto workflowInfoDto);

    /**
     * 消息推送 - 星云
     * @param badges 工号
     * @param url 地址（包括域名）
     * @param pubId 公共号ID
     * @param messageType   消息类型
     * @param images 发布信息列表
     * @param text 消息内容
     */
    void sendMassageXy(JSONArray badges, String url, String pubId, String messageType, List images, String text);

    /**
     * 撤回任务 -> 删除待办任务
     * @param dto 对象
     * @param task 任务
     */
    void reBackTaskDelete(BpsWorkFlowDto dto,TaskBean task);
/**
     * 撤回任务 -> 推送待办任务
     * @param workFlowDto 流程对象
     */
    void reBackTaskPush(BpsWorkFlowDto workFlowDto);

    /**
     * 授权任务 -> 删除+推送待办任务
     * @param workFlowDto 流程对象
     */
    void accreditTask(BpsWorkFlowDto workFlowDto,String type);

    /**
     * 推送工作平台数据
     * @param body 对象
     * @return boolean
     */
    boolean handSupplierAssess(JSONObject body);

    /**
     * 推送工作平台数据
     * @param body 对象
     * @return boolean
     */
    boolean handWorkflowAccess(JSONObject body,String servicePath);

}
