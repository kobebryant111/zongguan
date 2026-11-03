package com.work.ppm.application.services.vendormanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.vendorcontract.AttatchMUApplyDto;
import com.work.ppm.application.enums.LogicEnums;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.vendormanager.AttatchMUApply;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.vendormanager.IAttatchMUApplyService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 附件管理更新申请 服务实现类
 * </p>
 *
 * @since 2022-11-02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AttatchMUApplyService extends BaseDaoSupper implements IAttatchMUApplyService {

    /**
      * 实体类路径
      */
    private static String ATTATCHMUAPPLY= "com.work.ppm.application.model.vendormanager.AttatchMUApply";

    // 流程定义ID
    private static final String FLOW_ID = "fileManage";
    //流程名称
    private static final String FLOW_TITLE = "附件管理-人数-更新";

    private final ICommEmployeeService commEmployeeService;

    private final WorkFlowHttpClientService workFlowHttpClientService;
    // 流程日志
    private final IPpmFlowLogService ppmFlowLogService;
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public AttatchMUApply queryDetailsById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", id);
        return myBatisDao.selectClass(ATTATCHMUAPPLY, map, "queryDetailsById");
    }

    @Override
    public int queryNumberUsedById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", id);
        return myBatisDao.selectClass(ATTATCHMUAPPLY, map, "queryNumberUsedById");
    }

    @Override
    public boolean isApplied(AttatchMUApply attatchMUApply) {
        if (null == attatchMUApply) {
            return true;
        }
        AttatchMUApply obj= this.queryDetailsById(attatchMUApply.getRecordId());
        return !Objects.isNull(obj) ? true: false;
    }

    @Override
    public int addAttatchMUApply(AttatchMUApply attatchMUApply) {
       return myBatisDao.executionPojo(ATTATCHMUAPPLY, attatchMUApply, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer updateAttachMUApply(AttatchMUApply attatchMUApply) {
        return myBatisDao.executionPojo(ATTATCHMUAPPLY, attatchMUApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }


    @Override
    public int updateAttachByCalculate(AttatchMUApply attatchMUApply) {
        // 批准时，查询附件表已用人数
        int var01 = queryNumberUsedById(attatchMUApply.getRecordId());
        attatchMUApply.setNumberUsed(var01);
        // 可用人数：用于更新附件表
        attatchMUApply.setAvailableCount(attatchMUApply.getTotals() - attatchMUApply.getNumberUsed());
        return myBatisDao.executionPojo(ATTATCHMUAPPLY, attatchMUApply, IMyBatisDao.SQL.UPDATE, "updatePojoAttach");
    }

    @Override
    public Integer deleteFlowAttachApply(AttatchMUApply apply) {
        apply.setIsDel(LogicEnums.INVALID.getCode());
        int count = myBatisDao.executionPojo(ATTATCHMUAPPLY, apply, IMyBatisDao.SQL.UPDATE, "updatePojo");
        // 删除流程相关数据
        CamundaUtils.delProjectFlow(apply.getInstanceId());
        CamundaUtils.terminateProcess(apply.getInstanceId());
        return count;
    }

    @Override
    public List<AttatchMUApply> selectByMapBasic(HashMap<String, Object> map) {
        return myBatisDao.selectListT(ATTATCHMUAPPLY, map, "selectByMapBasic");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approvalCirculate(AttatchMUApplyDto applyDto, BpsWorkFlowDto bps) {
        // 首次发起
        if (null == applyDto.getInstanceId() && this.isApplied(applyDto)) {
            throw new BizException(ApiCode.FAIL, "已有在途更新申请！");
        }
        // 退回/撤回之后,重新提交申请
        if (null != applyDto.getInstanceId()) {
            PpmFlowLog ppmFlowLog = new PpmFlowLog();
            ppmFlowLog.setFlowcaseno(applyDto.getInstanceId());
            PpmFlowLog log= ppmFlowLogService.selectOneByInstanceId(applyDto.getInstanceId());
            if (WorkflowConclusionEnums.PASS.getCode().equalsIgnoreCase(log.getConclusion())){
                throw new BizException(ApiCode.FAIL, "已有在途更新申请！");
            }
        }
        ProcessBean processBean = null;
        try {
            //继续流转到主持工作的二级部经理
            String deptId = commEmployeeService.getDeptNo(bps.getUserId()).replaceAll("dep", "");
            CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(deptId);
            // 下一步审批人
            if (StringUtils.isBlank(workManager.getBadge())) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无二级部经理");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(applyDto.getInstanceId())) {
                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(applyDto.getRecordId()));

                applyDto.setFlowId(FLOW_ID);
                applyDto.setCreateUser(bps.getUserId());
                applyDto.setCreateDate(new Date());
                // 流程参数
                Map mPara = new HashMap();
                mPara.put("userId", workManager.getBadge());
                processInfo.setmPara(mPara);// 流程变量
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
                String instanceId= processBean.getProcessInstanceId();
                // 流程实例id
                applyDto.setInstanceId(instanceId);
                this.addAttatchMUApply(applyDto);

                // 任务实例
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(instanceId);
                // bps参数设置
                bps.setProjectNo(String.valueOf(applyDto.getRecordId())); // 业务主键
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("附件人数更新");
                bps.setSuggest("提交申请");
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "附件管理-人数-更新");
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见

                Map mPara = new HashMap();
                mPara.put("userId", workManager.getBadge());
                param.setmPara(mPara);// 流程变量
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                this.updateAttachMUApply(applyDto);
                // 日志处理
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                bps.setApplydesc("附件人数更新");
                bps.setProcessInstId(applyDto.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("附件更新流程发起失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String confirm(AttatchMUApplyDto applyDto, BpsWorkFlowDto bps) {
        try {
            // 回退&&通过 均设置无效
            applyDto.setUpdateUser(bps.getUserId());
            applyDto.setIsDel(LogicEnums.INVALID.getCode());
            AttatchMUApply muApply = JsonUtil.jsonToClass(applyDto, AttatchMUApply.class);

            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                param.setmPara(mPara);// 流程变量
                // 更新附件
                this.updateAttachByCalculate(muApply);
                // 删除更新申请记录
                this.updateAttachMUApply(muApply);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceId());
            bps.setApplydesc("二级部门经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceId());
            // 流程日志
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("二级部门经理审批失败：{}", e);
            throw new BizException(e);
        }
    }
}