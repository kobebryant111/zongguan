package com.work.ppm.commons.utils;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowGrantDto;
import com.work.ppm.application.model.flowmanager.PpmFlowGrant;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.core.ExecutorManager;
import com.work.ppm.application.services.flowmanager.IPpmFlowGrantService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.esb.EsbAction;
import com.work.ppm.commons.lang.notice.MessageNotice;
import com.work.workflow.client.soentity.BPSWorkItem;
import com.work.workflow.client.soentity.BpsResultInfo;
import com.work.workflow.client.soentity.WSPageCond;
import com.work.workflow.service.spi.ActivityManagerService;
import com.work.workflow.service.spi.GlobalTransactionManagerService;
import com.work.workflow.service.spi.RelativeDataManagerService;
import com.work.workflow.service.spi.WorkItemQueryManagerService;
import com.work.workflow.util.WorkflowUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * bps 工作流 工具类
 *
 */
@Slf4j
public class BpsUtil extends AbsBpsUtil {

    /**
     * 创建并发起流程
     *
     * @param bps 流程参数对象，以下必填
     *            processDefName， processInstName，processInstDesc，relaData，userId
     * @return String
     */
    @Deprecated
    public static String createProcessAndFinishFirst(BpsWorkFlowDto bps) {
        String processInstID = "";
        try {
            AssertUtil.notNull(bps.getProcessDefName(), "[processDefName]不能为空");
            AssertUtil.notNull(bps.getProcessInstName(), "[processInstName]不能为空");
            AssertUtil.notNull(bps.getProcessInstDesc(), "[processInstDesc]不能为空");
            AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");

            setMapUserAndRoles(bps.getRelaData());
            processInstID = WorkflowUtil.createProcessAndFinishFirst(bps.getProcessDefName(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), (HashMap<String, String>) bps.getRelaData(), bps.getUserId());
            bps.setProcessInstId(processInstID);
            saveProjectFlow(bps.getProjectNo(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), bps.getProcessInstId(), bps.getProcessDefName(), bps.getUserId(), bps.getFlowUrl());
            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 创建并发起流程异常", e);
            deleteProcessInstance(bps.getProcessInstId(), bps.getUserId());
            throw new BizException(e);
        }
        return processInstID;
    }

    /**
     * 创建并发起流程
     *
     * @param bps 流程参数对象，以下必填
     *            processDefName， processInstName，processInstDesc，relaData，userId
     * @param xid 事务id
     * @return String
     */
    public static String createProcessAndFinishFirst(BpsWorkFlowDto bps, String xid) {
        String processInstID = "";
        try {
            AssertUtil.notNull(bps.getProcessDefName(), "[processDefName]不能为空");
            AssertUtil.notNull(bps.getProcessInstName(), "[processInstName]不能为空");
            AssertUtil.notNull(bps.getProcessInstDesc(), "[processInstDesc]不能为空");
            AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");

            setMapUserAndRoles(bps.getRelaData());
            processInstID = WorkflowUtil.createProcessAndFinishFirst(bps.getProcessDefName(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), (HashMap<String, String>) bps.getRelaData(), bps.getUserId(), xid);
            bps.setProcessInstId(processInstID);
            saveProjectFlow(bps.getProjectNo(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), bps.getProcessInstId(), bps.getProcessDefName(), bps.getUserId(), bps.getFlowUrl());
//            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 创建并发起流程异常", e);
//            deleteProcessInstance(bps.getProcessInstId(), bps.getUserId());
            throw new BizException(e);
        }
        return processInstID;
    }

    /**
     * 创建并启动流程
     *
     * @param bps 流程参数对象
     * @return String
     */
    public static String createProcessAndStart(BpsWorkFlowDto bps) {
        String processInstId = "";
        String xid = "";
        GlobalTransactionManagerService transactionManagerService = new GlobalTransactionManagerService();
        try {
            AssertUtil.notNull(bps.getProcessDefName(), "[processDefName]不能为空");
            AssertUtil.notNull(bps.getProcessInstName(), "[processInstName]不能为空");
            AssertUtil.notNull(bps.getProcessInstDesc(), "[processInstDesc]不能为空");
            AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");

            xid = transactionManagerService.begin(bps.getUserId(), null);
            BpsResultInfo bpsResultInfo = processInstManagerService.createProcessInstance(bps.getProcessDefName(),
                    bps.getProcessInstName(), bps.getProcessInstDesc(), false, null, null, bps.getUserId(), null, xid);
            processInstId = bpsResultInfo.getResult();
            // 如果为空 则鬼回滚事务
            if (!StringUtil.isNotBlank(processInstId)) {
                transactionManagerService.rollback(bps.getUserId(), null, xid);
                return "-1";
            }
            bps.setProcessInstId(processInstId);
            //

            setMapUserAndRoles(bps.getRelaData());
            RelativeDataManagerService relativeDataManagerService = new RelativeDataManagerService();
            relativeDataManagerService.setRelativeData(new Long(processInstId).longValue(), bps.getRelaData(), bps.getUserId(), null, xid);
            processInstManagerService.startProcessInstAndFinishFirstWorkItem(Long.parseLong(processInstId), false,
                    bps.getUserId(), null, xid, null);
            transactionManagerService.commit(bps.getUserId(), null, xid);
            // 保存流程信息
            saveProjectFlow(bps.getProjectNo(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), bps.getProcessInstId(), bps.getProcessDefName(), bps.getUserId(), bps.getFlowUrl());
//            // 流程日志
//            ppmFlowLogService.saveFlowLog(bps);
//            // 通知
//            BpsUtil.sendMessage(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 创建并发起流程异常", e);
            try {
                transactionManagerService.rollback(bps.getUserId(), null, xid);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (StringUtil.isNotBlank(bps.getProcessInstId())) {
                deleteProcessInstance(bps.getProcessInstId(), bps.getUserId());
            }
            throw new BizException(e);
        }
        return processInstId;
    }

    /**
     * 创建并启动流程
     *
     * @param bps 流程参数对象
     * @param xid 事务id
     * @return String
     */
    public static String createProcessAndStart(BpsWorkFlowDto bps, String xid) {
        String processInstId = "";
        GlobalTransactionManagerService transactionManagerService = new GlobalTransactionManagerService();
        try {
            AssertUtil.notNull(bps.getProcessDefName(), "[processDefName]不能为空");
            AssertUtil.notNull(bps.getProcessInstName(), "[processInstName]不能为空");
            AssertUtil.notNull(bps.getProcessInstDesc(), "[processInstDesc]不能为空");
            AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");

//            xid = transactionManagerService.begin(bps.getUserId(), null);
            BpsResultInfo bpsResultInfo = processInstManagerService.createProcessInstance(bps.getProcessDefName(),
                    bps.getProcessInstName(), bps.getProcessInstDesc(), false, null, null, bps.getUserId(), null, xid);
            processInstId = bpsResultInfo.getResult();
            // 如果为空 则鬼回滚事务
            if (!StringUtil.isNotBlank(processInstId)) {
                transactionManagerService.rollback(bps.getUserId(), null, xid);
                return "-1";
            }
            bps.setProcessInstId(processInstId);
            //

            setMapUserAndRoles(bps.getRelaData());
            RelativeDataManagerService relativeDataManagerService = new RelativeDataManagerService();
            relativeDataManagerService.setRelativeData(new Long(processInstId).longValue(), bps.getRelaData(), bps.getUserId(), null, xid);
            processInstManagerService.startProcessInstAndFinishFirstWorkItem(Long.parseLong(processInstId), false,
                    bps.getUserId(), null, xid, null);
            transactionManagerService.commit(bps.getUserId(), null, xid);
            // 保存流程信息
            saveProjectFlow(bps.getProjectNo(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), bps.getProcessInstId(), bps.getProcessDefName(), bps.getUserId(), bps.getFlowUrl());
//            // 流程日志
//            ppmFlowLogService.saveFlowLog(bps);
//            // 通知
//            BpsUtil.sendMessage(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 创建并发起流程异常", e);
            throw new BizException(e);
        }
        return processInstId;
    }

    /**
     * 创建流程实例
     *
     * @param bps bps.processDefName
     *            bps.processInstName
     *            bps.processInstDesc
     *            bps.relaData
     *            bps.userId
     * @return string
     */
    public static String createProcessInstance(BpsWorkFlowDto bps) {
        AssertUtil.notNull(bps.getProcessDefName(), "[processDefName]不能为空");
        AssertUtil.notNull(bps.getProcessInstName(), "[processInstName]不能为空");
        AssertUtil.notNull(bps.getProcessInstDesc(), "[processInstDesc]不能为空");
        AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
        AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");
        String processInstId = "";
        try {
            processInstId = WorkflowUtil.createProcessAndStart(bps.getProcessDefName(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), (HashMap<String, String>) bps.getRelaData(), bps.getUserId());
            BpsUtil.saveProjectFlow(bps.getProjectNo(), bps.getProcessInstName(), bps.getProcessInstDesc(),
                    processInstId, bps.getProcessDefName(), bps.getUserId(), bps.getFlowUrl());
            bps.setProcessInstId(processInstId);
            // 流程日志
            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 创建流程异常", e);
            throw new BizException(e);
        }
        return processInstId;
    }


    /**
     * 创建流程实例 - 事务
     *
     * @param bps bps.processDefName
     *            bps.processInstName
     *            bps.processInstDesc
     *            bps.relaData
     *            bps.userId
     * @param xid 事务id
     * @return string
     */
    public static String createProcessInstance(BpsWorkFlowDto bps, String xid) {
        String processInstId = "";
        try {
            processInstId = WorkflowUtil.createProcessAndStart(bps.getProcessDefName(), bps.getProcessInstName(),
                    bps.getProcessInstDesc(), (HashMap<String, String>) bps.getRelaData(), bps.getUserId(), xid);
            BpsUtil.saveProjectFlow(bps.getProjectNo(), bps.getProcessInstName(), bps.getProcessInstDesc(),
                    processInstId, bps.getProcessDefName(), bps.getUserId(), bps.getFlowUrl());
            bps.setProcessInstId(processInstId);
            // 流程日志
//            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 创建流程异常", e);
            throw new BizException(e);
        }
        return processInstId;
    }

    /**
     * 获取工作流实例的状态
     *
     * @param processInstId 流程id
     * @param userId        用户id
     * @return string
     *             1  			未启动
     * 	 * 		   2			运行
     * 	 * 		   3			挂起
     * 	 * 		   7			结束
     * 	 * 		   8   			终止
     */
    public static String getProcessInstState(String processInstId, String userId) {
        String resultInfo;
        try {
            resultInfo = WorkflowUtil.getProcessInstState(processInstId, userId);
        } catch (Exception e) {
            log.error("BpsUtil - 获取工作流实例的状态异常", e);
            throw new BizException(e);
        }
        return resultInfo;
    }

    /**
     * 完成工作项
     *
     * @param bps 流程参数对象
     *            必填   bps.processInstId，
     *            bps.workItemId，
     *            bps.relaData，
     *            bps.userId,
     *            日志参数
     *            bps.applydesc
     *            bps.conclusion
     */
    @Deprecated
    public static void finshWorkItem(BpsWorkFlowDto bps) {
        try {
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId]不能为空");
            AssertUtil.notNull(bps.getWorkItemId(), "[workItemId]不能为空");
            AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");
            AssertUtil.notNull(bps.getApplydesc(), "[applydesc]不能为空");
            AssertUtil.notNull(bps.getConclusion(), "[conclusion]不能为空");
            String message = BpsUtil.queryProcessInfo(bps);
            if (message.indexOf("已结束") != -1) {
                throw new BizException(ApiCode.SUCCESS, "流程已审批");
            }
            setMapUserAndRoles(bps.getRelaData());
            WorkflowUtil.finshWorkItem(bps.getProcessInstId(), bps.getWorkItemId(), bps.getDestActDefId(),
                    (HashMap<String, String>) bps.getRelaData(), bps.getUserId());
            String processInstState = getProcessInstState(bps.getProcessInstId(), bps.getUserId());
            updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            if ("7".equals(processInstState) || "8".equals(processInstState)) {
                updateProjectFlow(bps.getProcessInstId());
            } else {
                updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            }
            BpsUtil.preAuthor(bps);
            ppmFlowLogService.saveFlowLog(bps);
            BpsUtil.sendMessage(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 完成工作项异常", e);
            throw new BizException(e);
        }
    }

    /**
     * 完成工作项
     *
     * @param bps 流程参数对象
     *            必填   bps.processInstId，
     *            bps.workItemId，
     *            bps.relaData，
     *            bps.userId,
     *            日志参数
     *            bps.applydesc
     *            bps.conclusion
     * @param xid 流程事务id
     */
    public static void finshWorkItem(BpsWorkFlowDto bps, String xid) {
        try {
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId]不能为空");
            AssertUtil.notNull(bps.getWorkItemId(), "[workItemId]不能为空");
            AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");
            AssertUtil.notNull(bps.getApplydesc(), "[applydesc]不能为空");
            AssertUtil.notNull(bps.getConclusion(), "[conclusion]不能为空");
            String message = BpsUtil.queryProcessInfo(bps);
            log.warn("===========> 流程流转信息bps:{}", JsonUtil.toJSONString(bps));
            if (message.indexOf("已结束") != -1) {
                throw new BizException(ApiCode.SUCCESS, "流程已审批");
            }
            setMapUserAndRoles(bps.getRelaData());
            WorkflowUtil.finshWorkItem(bps.getProcessInstId(), bps.getWorkItemId(), bps.getDestActDefId(),
                    (HashMap<String, String>) bps.getRelaData(), bps.getUserId(), xid);
//            String processInstState = getProcessInstState(bps.getProcessInstId(), bps.getUserId());
//            updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
//            if ("7".equals(processInstState) || "8".equals(processInstState)) {
//                updateProjectFlow(bps.getProcessInstId());
//            } else {
//                updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
//            }

        } catch (Exception e) {
            log.error("BpsUtil - 完成工作项异常", e);
            throw new BizException(e);
        }
    }

    /**
     * 开启事务
     * @param userId 用户id
     * @return xid 事务id
     */
    public static String transactionBegin(String userId) {
        try{
            return WorkflowUtil.transactionBegin(userId);
        }catch (Exception  e) {
            throw new BizException(e);
        }

    }

    /**
     * 事务回滚
     * @param userId  用户id
     * @param xid 事务id
     */
    public static void transactionRollback(String userId, String xid) {
        try{
            WorkflowUtil.transactionRollback(userId,  xid);
        }catch (Exception e) {
            throw new BizException(e);
        }
    }

    /**
     * 事务提交
     * @param userId 用户id
     * @param xid 事务id
     */
    public static void transactionCommit(String userId, String xid){
        try{
            WorkflowUtil.transactionCommit(userId, xid);
        }catch (Exception e) {
            throw new BizException(e);
        }
    }

    /**
     * 完成活动项，使流程进入下一个节点时
     *
     * @param bps *  必填   bps.processInstId，
     *            *            bps.activityInstId 活动项id，
     *            *            bps.relaData，
     *            *            bps.userId,
     *            * 日志参数
     *            *            bps.applydesc
     *            *            bps.conclusion
     *            *
     */
    public static void finishActivityInstance(BpsWorkFlowDto bps) {
        AssertUtil.notNull(bps.getProcessInstId(), "[processInstId]不能为空");
        AssertUtil.notNull(bps.getActivityInstId(), "[activityInstId]不能为空");
        AssertUtil.notNull(bps.getRelaData(), "[relaData]不能为空");
        AssertUtil.notNull(bps.getUserId(), "[userId]不能为空");
        AssertUtil.notNull(bps.getApplydesc(), "[applydesc]不能为空");
        AssertUtil.notNull(bps.getConclusion(), "[conclusion]不能为空");

        String xid = "";
        ActivityManagerService activityManagerService = new ActivityManagerService();
        RelativeDataManagerService relativeDataManagerService = new RelativeDataManagerService();
        // activityInstID, String userID, String password, String xid
        try {
            xid = transactionBegin(bps.getUserId());
            if (isMapNotEmpty(bps.getRelaData())) {
                setMapUserAndRoles(bps.getRelaData());
                relativeDataManagerService.setRelativeData(new Long(bps.getProcessInstId()),
                        bps.getRelaData(), bps.getUserId(), null, xid);
            }
            BpsResultInfo resultInfo = activityManagerService.finishActivityInstance(
                    Long.parseLong(bps.getActivityInstId()),
                    bps.getUserId(), null, xid);
            transactionCommit(bps.getUserId(), xid);
            String processInstState = getProcessInstState(bps.getProcessInstId(), bps.getUserId());
            log.warn("resultInfo", resultInfo.getResult());
            if ("7".equals(processInstState) || "8".equals(processInstState)) {
                updateProjectFlow(bps.getProcessInstId());
            } else {
                updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            }
            BpsUtil.preAuthor(bps);
            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil - 完成工作项异常", e);
            try {
                transactionRollback(bps.getUserId(), xid);
            } catch (Exception e1) {
                log.error("BpsUtil - 完成工作项异常,回滚异常", e1);
            }
            throw new BizException(e);
        }

    }

    /**
     * 我的待办
     *
     * @param bps         流程参数
     *                    必填项 pbs.userId
     * @param bpsWorkItem 流程查询条件
     * @return List<BPSWorkItem>
     */
    public static List<BPSWorkItem> getTodoWorkItem(BpsWorkFlowDto bps, BPSWorkItem bpsWorkItem) {
        List<BPSWorkItem> bpsworkitems = null;
        try {
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            WSPageCond cond = new WSPageCond();
            cond.setBegin(0);
            cond.setLength(10);
            cond.setIsCount(true);
            bpsworkitems = WorkflowUtil.queryPersonWorkItems(bps.getUserId(), bpsWorkItem, cond);
            log.debug("cond:{}", cond.getCount());
        } catch (Exception e) {
            log.error("BpsUtil - 我的待办查询异常", e);
            throw new BizException(e);
        }
        return bpsworkitems;
    }

    /**
     * 我的已办
     *
     * @param bps   工作流相关参数
     *              必填       pbs.userId
     * @param param 工作项对象
     * @return List<BPSWorkItem>
     */
    public static List<BPSWorkItem> getHandleWorkItem(BpsWorkFlowDto bps, BPSWorkItem param) {
        List<BPSWorkItem> workItems = null;
        try {
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            WSPageCond cond = new WSPageCond();
            cond.setBegin(1);
            cond.setLength(1);
            WorkItemQueryManagerService workItemQueryManagerService = new WorkItemQueryManagerService();
            workItems = workItemQueryManagerService.queryWorkItemInApprovedAndActivityProcessInst(bps.getUserId(),
                    param, cond, bps.getUserId(), null);
        } catch (Exception e) {
            log.error("BpsUtil- 我的已办查询异常", e);
            throw new BizException(e);
        }
        return workItems;
    }

    /**
     * 删除流程实例
     *
     * @param processInstId 流程实例id
     * @param userId        工号
     */
    public static void deleteProcessInstance(String processInstId, String userId) {
        String xid = "";
        try {
            xid = transactionBegin(userId);
            BpsResultInfo resultInfo = processInstManagerService.deleteProcessInstance(Long.parseLong(processInstId), userId, null, xid);
            log.info("删除流程实例{}", JsonUtil.toJSONString(resultInfo));
            transactionCommit(userId,xid);

        } catch (Exception e) {
            try {
                transactionRollback(userId, xid);
            } catch (Exception e1) {
                log.error("BpsUtil- 删除流程实例异常", e1);
            }
            throw new BizException(e);
        }
    }

    /**
     * 流程工作项信息
     * 只知道流程ID时，根据流程ID查询工作项ID
     *
     * @param bps processInstId
     * @return List<BPSWorkItem>
     */
    public static List<BPSWorkItem> getWorkItem(BpsWorkFlowDto bps) {
        AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
        WorkItemQueryManagerService workItemQueryManagerService = new WorkItemQueryManagerService();
        BPSWorkItem item = new BPSWorkItem();
        item.setProcessInstID(new Long(bps.getProcessInstId()).longValue());
        List<BPSWorkItem> list = null;
        try {
            list = workItemQueryManagerService.queryPersonWorkItems("processAdmin",
                    item, "ALL", null, null, null);
        } catch (Exception e) {
            log.error("BpsUtil-工作流查询异常", e);
            throw new BizException(e);
        }
        return list;
    }

    /**
     * 查询流程工作项信息
     *
     * @param item   对象
     * @param userId 工号
     * @return List<BPSWorkItem>
     */
    public static List<BPSWorkItem> getWorkItem(BPSWorkItem item, String userId) {
        WorkItemQueryManagerService workItemQueryManagerService = new WorkItemQueryManagerService();
        List<BPSWorkItem> list = null;
        try {
            list = workItemQueryManagerService.queryPersonWorkItems(userId,
                    item, "ALL", null, userId, null);
        } catch (Exception e) {
            log.error("BpsUtil-工作流查询异常", e);
            throw new BizException(e);
        }
        return list;
    }

    /**
     * 流程信息
     *
     * @param processInstId 流程id
     * @return String
     */
    public static String queryProcessInfo(String processInstId) {
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        bps.setProcessInstId(processInstId);
        return queryProcessInfo(bps);
    }

    /**
     * 流程信息
     *
     * @param bps 流程参数对象
     *            processInstId
     * @return String
     */
    public static String queryProcessInfo(BpsWorkFlowDto bps) {
        String processInfo = "";
        try {
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
            processInfo = WorkflowUtil.queryProcessInfo(bps.getProcessInstId(), null);
        } catch (Exception e) {
            log.error("BpsUtil-查询流程信息异常", e);
            throw new BizException(e);
        }
        return processInfo;
    }

    /**
     * 流程撤回
     *
     * @param bps 流程参数对象
     *            workItemId,userId
     */
    public static void drawbackWorkItem(BpsWorkFlowDto bps) {
        try {
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
            AssertUtil.notNull(bps.getWorkItemId(), "[workItemId] 不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            AssertUtil.notNull(bps.getConclusion(), "[conclusion] 不能为空");
            AssertUtil.notNull(bps.getApplydesc(), "[applydesc] 不能为空");
//            AssertUtil.notNull(bps.getSuggest(),"[suggest] 不能为空");
            WorkflowUtil.drawbackEnable(bps.getWorkItemId(), bps.getUserId());
            ppmFlowLogService.saveFlowLog(bps);
            updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
        } catch (Exception e) {
            log.error("BpsUtil-流程撤回异常", e);
            throw new BizException(e);
        }
    }

    /**
     * 是否可以撤回
     *
     * @param bps bps.workItemId,
     *            bps.userId
     * @return boolean
     */
    public static boolean isDrawbackEnable(BpsWorkFlowDto bps) {
        boolean falg = false;
        try {
            AssertUtil.notNull(bps.getWorkItemId(), "[workItemId] 不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            falg = WorkflowUtil.isDrawbackEnable(bps.getWorkItemId(), bps.getUserId());
        } catch (Exception e) {
            log.error("BpsUtil-是否可以流程撤回异常", e);
            throw new BizException(e);
        }
        return falg;
    }

    /**
     * 流程退回到任意阶段
     *
     * @param bps bps.currentActInstId
     *            bps.destActDefId
     *            bps.userId
     *            bps.processInstId
     *            日志参数
     *            bps.conclusion
     *            bps.suggest
     *            bps.applydesc
     * @return 下个节点信息
     */
    public static String backActivity(BpsWorkFlowDto bps) {
        try {
            AssertUtil.notNull(bps.getActivityInstId(), "[activityInstId] 不能为空");
            AssertUtil.notNull(bps.getDestActDefId(), "[destActDefId] 不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
            AssertUtil.notNull(bps.getConclusion(), "[conclusion] 不能为空");
            AssertUtil.notNull(bps.getSuggest(), "[suggest] 不能为空");
            AssertUtil.notNull(bps.getApplydesc(), "[applydesc] 不能为空");
            WorkflowUtil.backActivity(Long.parseLong(bps.getActivityInstId()), bps.getDestActDefId(), "path", bps.getUserId());
            BpsUtil.updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            String state = BpsUtil.getProcessInstState(bps.getProcessInstId(), bps.getUserId());
            if ("7".equals(state) || "8".equals(state)) {
                BpsUtil.updateProjectFlow(bps.getProcessInstId());
            }
            // 日志
            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil-流程退回异常", e);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps.getProcessInstId());
    }

    /**
     * 授权
     *
     * @param bps bps.personId
     *            bps.personName
     *            bps.workItemId
     *            bps.userId
     *            bps.processInstId
     *            bps.processInstName
     *            bps.processDefName
     */
    public static void reassignWorkItem(BpsWorkFlowDto bps) {
        try {
            AssertUtil.notNull(bps.getPersonId(), "[personId] 不能为空");
            AssertUtil.notNull(bps.getPersonName(), "[personName] 不能为空");
            AssertUtil.notNull(bps.getWorkItemId(), "[workItemId] 不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
            AssertUtil.notNull(bps.getProcessInstName(), "[processInstName] 不能为空");
            AssertUtil.notNull(bps.getProcessDefName(), "[processDefName] 不能为空");
            WorkflowUtil.reassignWorkItem(bps.getPersonId(), bps.getPersonName(), "person", Long.parseLong(bps.getWorkItemId()), bps.getUserId());
            savePpmFlowShouquanLog(bps);
            // 预授权
            BpsUtil.preAuthor(bps);
            BpsUtil.updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            // 日志
            bps.setConclusion("03");
            ppmFlowLogService.saveFlowLog(bps);
        } catch (Exception e) {
            log.error("BpsUtil-流程授权异常", e);
            throw new BizException(e);
        }
    }

    /**
     * 预授权
     *
     * @param bps bps.processInstId
     *            bps.processDefName
     *            bps.workItemId
     *            bps.processInstName
     *            bps.userId
     *            bps.personId
     * @return string 流程信息
     */
    public static String preAuthor(BpsWorkFlowDto bps) {
        String info = BpsUtil.queryProcessInfo(bps.getProcessInstId());
        try {
            EosDictEntry switchs=eosDictEntryService.getTypeidForDetail("ISSENDOADB","01");
            if("1".equals(switchs.getDictname())){
                //先执行OA通知
                ExecutorManager.getExecutor().submit(()->{
                    // OA 通知
                    EsbAction action = new EsbAction();
                    action.sendRemoveOA(bps);
                    action.sendCreateOA(bps);
                });
            }



            String[] infos = info.split(",");
            if (infos.length <= 2) {
                return null;
            }
            String jdid = infos[2].split(":")[1].trim();
            bps.setActivityInstId(jdid);
            List<BPSWorkItem> bpsList = BpsUtil.getWorkItem(bps);
            if (null != bpsList && !bpsList.isEmpty()) {
                String tempuserId = "";
                String userIds = "";
                String userNmaes = "";
                for (BPSWorkItem item : bpsList) {
                    if (!StringUtil.isNotBlank(bps.getProcessDefName())) {
                        bps.setProcessDefName(item.getProcessDefName());
                    }
                    String[] participant = item.getParticipant().split("\\|");
                    int length = participant.length;
                    for (int i = 0; i < length; i++) {
                        String userId = savePpmFlowShouquanLog(participant[i], participant[i], bps);
                        if (!participant[i].equals(userId)) {
                            tempuserId = userId;
                            userIds += userId + "@";
                            String userinfo = BpsUtil.getUser(userId);
                            userNmaes += userinfo.split("@")[1] + "@";
                        } else {
                            userIds += participant[i] + "@";
                            String userinfo = BpsUtil.getUser(participant[i]);
                            if (StringUtil.isNotBlank(userinfo)) {
                                userNmaes += userinfo.split("@")[1] + "@";
                            }
                        }
                    }
                    // 授权
                    if (StringUtil.isNotBlank(tempuserId)) {
                        WorkflowUtil.reassignWorkItem(userIds, userNmaes, "person", item.getWorkItemID(), tempuserId);
                    }
                }
            }

        } catch (Exception e) {
            log.error("BpsUtil- 预授权异常", e);
            throw new BizException(e);
        }
        return info;
    }

    /**
     * 查询已办
     *
     * @param item   对象
     * @param userId 工号
     * @return List<BPSWorkItem>
     */
    public static List<BPSWorkItem> queryWorkItemInApprovedAndActivityProcessInst(BPSWorkItem item, String userId) {
        List<BPSWorkItem> list = null;
        try {
            WorkItemQueryManagerService workItemQueryManagerService = new WorkItemQueryManagerService();
            WSPageCond page = new WSPageCond();
            page.setIsPage(false);
            page.setIsCount(true);
            list = workItemQueryManagerService.queryWorkItemInApprovedAndActivityProcessInst(userId, item, page, userId, null);
        } catch (Exception e) {
            log.error("BpsUtil- 查询已办异常", e);
            list = null;
        }
        return list;
    }

    /**
     * 保存授权日志
     *
     * @param userId1 授权的工号
     * @param userId2 被授权的工号
     * @param bps     对象
     * @return userId1
     */
    public static String savePpmFlowShouquanLog(String userId1, String userId2, BpsWorkFlowDto bps) {
        IPpmFlowGrantService ppmFlowGrantService = (IPpmFlowGrantService) SpringUtil.getBean("ppmFlowGrantService");
//        Date date = DateUtil.strToDate()),"yyyy-MM-dd");
        PpmFlowGrantDto grant = new PpmFlowGrantDto();
        grant.setDate(DateUtil.dateToIsoDateFormat(new Date()));
        grant.setFlowdraftno(bps.getProcessDefName());
        grant.setSourceuserid(userId1);
        grant.setIsdel(0);
        grant.setIsqy(1);
        PpmFlowGrant grantItem = ppmFlowGrantService.queryDetail(grant);
        if (null == grantItem) {
            return userId1;
        }
        String beishouquanren = grantItem.getTargetuserid();
        if (null != userId2 && userId2.indexOf(beishouquanren) == -1) {
            bps.setPersonId(beishouquanren);
            BpsUtil.savePpmFlowShouquanLog(bps);
            userId2 = userId2 + "," + beishouquanren;
            userId1 = BpsUtil.savePpmFlowShouquanLog(beishouquanren, userId2, bps);
        }
        return userId1;
    }

    /**
     * 终止流程
     *
     * @param bps bps.processInstId
     *            bps.userId
     */
    public static void terminateProcessInstance(BpsWorkFlowDto bps) {
        try {
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            WorkflowUtil.terminateProcessInstance(bps.getProcessInstId(), bps.getUserId());
            PpmProjectFlow flow = new PpmProjectFlow();
            flow.setInstanceid(bps.getProcessInstId());
            PpmProjectFlow flowItem = ppmProjectFlowService.queryDetail(flow);
            // 更改状态
            if (null != flowItem) {
                flow.setLastmodtime(new Date());
                flow.setFlowstatus("已删除");
                flow.setClr("");
                ppmProjectFlowService.updatePpmProjectFlow(flow);
            }
            // 添加日志
            PpmFlowLog flowLog = new PpmFlowLog();
            flowLog.setFlowcaseno(bps.getProcessInstId());
            flowLog.setIsdel(0);
            flowLog.setIsfalse("01");
            flowLog.setConclusion("07");
            flowLog.setCreatetime(new Date());
            flowLog.setUserid(bps.getUserId());
            ppmFlowLogService.updatePpmFlowLog(flowLog);
        } catch (Exception e) {
            log.error("BpsUtil- 终止流程异常", e);
            throw new BizException(e);
        }
    }

    /**
     * 终止流程
     *
     * @param processInstId 流程id
     * @param userId 用户id
     */
    public static void terminateProcessInstance(String processInstId, String userId) {
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        bps.setProcessInstId(processInstId);
        bps.setUserId(userId);
        terminateProcessInstance(bps);
    }

    /**
     * 查询已完成但未结束的流程
     *
     * @param bps bps.processInstId
     *            bps.userId
     * @return BPSWorkItem
     */
    public static BPSWorkItem queryFinishedWorkItemInActivityProcessInst(BpsWorkFlowDto bps) {
        try {
            AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
            AssertUtil.notNull(bps.getUserId(), "[userId] 不能为空");
            WorkItemQueryManagerService workItemQueryManagerService = new WorkItemQueryManagerService();
            BPSWorkItem workItemParam = new BPSWorkItem();
            workItemParam.setProcessInstID(new Long(bps.getProcessInstId()).longValue());
            workItemParam.setSortField("createTime");
            workItemParam.setSortOrder("desc");
            List<BPSWorkItem> list = workItemQueryManagerService.queryFinishedWorkItemInActivityProcessInst(bps.getUserId(), workItemParam, null, bps.getUserId(), null);
            if (null != list && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
            log.error("BpsUtil- 查询已完成但未结束的流程异常", e);
            throw new BizException(e);
        }
        return null;
    }

    /**
     * 更新流程对象表
     *
     * @param processInstId 流程实例id
     * @param userId        工号
     * @return int
     */
    public static int updateProjectFlow(String processInstId, String userId) {
        try {
            PpmProjectFlow entity = new PpmProjectFlow();
            entity.setInstanceid(processInstId);
            List<PpmProjectFlow> list = ppmProjectFlowService.queryList(entity);
            if (null == list || list.isEmpty()) {
                return -1;
            }
            Clr clrItem = getClrAndClrId(processInstId);
            PpmProjectFlow flow = list.get(0);
            flow.setLastmodtime(new Date());
            flow.setClr(clrItem.getClr());
            flow.setClrid(clrItem.getClrId());
            flow.setLastmoduser(flow.getLastmoduser() + "," + userId);
            flow.setActivityname(clrItem.getNodeName());
            flow.setActivityid(clrItem.getActivityId());
            ppmProjectFlowService.updatePpmProjectFlow(flow);
        } catch (Exception e) {
            log.error("BpsUtil-更新流程对象异常", e);
            throw new BizException(e);
        }
        return 1;
    }

    /**
     * 流程 通知
     *
     * @param bps 对象
     */
    public static void sendMessage(BpsWorkFlowDto bps) {
        AssertUtil.notNull(bps.getProcessInstId(), "[processInstId] 不能为空");
        BPSWorkItem item = BpsUtil.queryFinishedWorkItemInActivityProcessInst(bps);
        // 说明流程已经结束，不需要在去通知
        if(null == item) {
            return;
        }
        bps.setProcessDefName(item.getProcessDefName());

        // 1. 查询预授权
        String info = preAuthor(bps);
        if(!StringUtil.isNotBlank(info)) {
            return;
        }
        String[] str = info.split(",");
        String id = str[2].split(":")[1].trim();

        List<BPSWorkItem> list = BpsUtil.getWorkItem(bps);
        String workItems;
        String partiName = "";
        WeiXin weiXin = null;
        if (null != list && !list.isEmpty()) {
            weiXin = addWeiXin(list);
        }
        if (!",".equals(weiXin.getPartiName().substring(weiXin.getPartiName().length() - 1))) {
            partiName += "|";
        }
        partiName = weiXin.getPartiName().substring(0, weiXin.getPartiName().length() - 1);
        workItems = weiXin.getWorkItems().substring(0, weiXin.getWorkItems().length() - 1);
        // 流程名称带有"TEST-" 一律不发送通知
        PpmProjectFlow ppf = ppmProjectFlowService.queryDetail(bps.getProcessInstId());
        if (null != ppf && "TEST-".indexOf(ppf.getFlowtitle()) > -1) {
            log.warn(" 流程名称带有【TEST-】 一律不发送通知>>>>>>>>{}", ppf.getFlowtitle());
        } else {
            // 发送通知
            MessageNotice notice = new MessageNotice(partiName, bps.getUserId(), bps.getProcessDefName(),
                    id,
                    weiXin.getProcessTitle(), bps.getProcessInstId(), workItems);
            notice.sendFlowNotice();
        }
    }

    /**
     * 查询指定活动节点的工作项
     *
     * @param activityInstId 活动定义id
     * @param userId         用户id
     * @return List<BPSWorkItem>
     */
    public static List<BPSWorkItem> queryWorkItemsByActivityInstID(long activityInstId, String userId) {
        AssertUtil.notNull(activityInstId, "[activityInstId] 不能为空");
        AssertUtil.notNull(userId, "[userId] 不能为空");
        WorkItemQueryManagerService workItemQueryManagerService = new WorkItemQueryManagerService();
        try {
           List<BPSWorkItem> list =
                   workItemQueryManagerService.queryWorkItemsByActivityInstID(activityInstId,userId,
                    null);
         return list;
        } catch (Exception e) {
            log.error("BpsUtil-查询指定活动节点的工作项异常", e);
        }
        return null;
    }

    /**
     * 流程日誌以及通知,注意：请在事务提交后在写此方法
     * @param bps 對象
     */
    public static void flowLog(BpsWorkFlowDto bps) {
        try{
            String processInstState = getProcessInstState(bps.getProcessInstId(), bps.getUserId());
            updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            if ("7".equals(processInstState) || "8".equals(processInstState)) {
                updateProjectFlow(bps.getProcessInstId());
            } else {
                updateProjectFlow(bps.getProcessInstId(), bps.getUserId());
            }
            BpsUtil.preAuthor(bps);
            ppmFlowLogService.saveFlowLog(bps);
            BpsUtil.sendMessage(bps);
        }catch (Exception e){
            log.error("流程日志以及通知异常：", e);
            throw new BizException(e);
        }
    }

}
