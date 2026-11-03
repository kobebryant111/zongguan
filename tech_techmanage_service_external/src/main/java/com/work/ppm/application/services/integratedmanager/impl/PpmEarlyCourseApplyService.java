package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Lists;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmEarlyCourseApply;
import com.work.ppm.application.services.integratedmanager.IPpmEarlyCourseApplyInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmEarlyCourseApplyService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 提前入场申请
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmEarlyCourseApplyService")
public class PpmEarlyCourseApplyService extends BaseDaoSupper implements IPpmEarlyCourseApplyService{
    //实体类路径
    private static final String PPMEARLYCOURSEAPPLY =
            "com.work.ppm.application.model.integratedmanager.PpmEarlyCourseApply";
    // 流程定义ID
    private static final String FLOW_ID = "tqrcsq";
    //流程名称
    private static final String FLOW_TITLE = "提前入场申请";
    //数据接口
    private final IMyBatisDao myBatisDao;
    //员工
    private final ICommEmployeeService commEmployeeService;
    //行员兼职
    private final ICommParttimejobService commParttimejobService;
    //提前入场申请接口
    private final IPpmEarlyCourseApplyInfoService ppmEarlyCourseApplyInfoService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;

    @Override
    public PpmEarlyCourseApplyDto queryDetail(PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto) {
        PpmEarlyCourseApply courseApply = JsonUtil.jsonToClass(ppmEarlyCourseApplyDto,PpmEarlyCourseApply.class);
        PpmEarlyCourseApply earlyCourseApply = myBatisDao.selectClass(PPMEARLYCOURSEAPPLY, courseApply, "queryDetail");
        return JsonUtil.jsonToClass(earlyCourseApply,PpmEarlyCourseApplyDto.class);
    }

    @Override
    public Integer insertPpmEarlyCourseApply(PpmEarlyCourseApplyDto courseApplyDto) {
        PpmEarlyCourseApply ppmEarlyCourseApply = JsonUtil.jsonToClass(courseApplyDto,PpmEarlyCourseApply.class);
        return myBatisDao.executionPojo(PPMEARLYCOURSEAPPLY, ppmEarlyCourseApply, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer updatePpmEarlyCourseApply(PpmEarlyCourseApplyDto courseApplyDto) {
        PpmEarlyCourseApply ppmEarlyCourseApply = JsonUtil.jsonToClass(courseApplyDto,PpmEarlyCourseApply.class);
        return myBatisDao.executionPojo(PPMEARLYCOURSEAPPLY, ppmEarlyCourseApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deletePpmEarlyCourseApply(PpmEarlyCourseApplyDto ppmEarlyCourseApply) {
        PpmEarlyCourseApply courseApply = JsonUtil.jsonToClass(ppmEarlyCourseApply,PpmEarlyCourseApply.class);
        int count = myBatisDao.executionPojo(
                PPMEARLYCOURSEAPPLY, courseApply, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");

        if (StrUtils.isNotBlank(ppmEarlyCourseApply.getInstanceid())) {
            // 清理关联业务数据
            ppmEarlyCourseApplyInfoService.deleteByInstanceId(ppmEarlyCourseApply.getInstanceid());
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(ppmEarlyCourseApply.getInstanceid());
            CamundaUtils.terminateProcess(ppmEarlyCourseApply.getInstanceid());
        }
        return count;
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String managerInit(PpmEarlyCourseApplyDto earlyCourseApplyDto) {
        List<PpmEarlyCourseApplyInfoDto> applyInfoDtos = earlyCourseApplyDto.getApplyInfoDtos();
        BpsWorkFlowDto bps = earlyCourseApplyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        //数据校验
        vaildCommitData(earlyCourseApplyDto);

        //流程逻辑
        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(earlyCourseApplyDto.getInstanceid())) {
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(",");
                applyInfoDtos.stream().forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName(joiner + "提前入场申请");
                // 如果需要请set 主键id
                earlyCourseApplyDto.setId(getNextSequence(PpmEarlyCourseApply.class));
                earlyCourseApplyDto.setCreatedTime(new Date());

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(earlyCourseApplyDto.getId()));
                Map mPara = new HashMap();
                //获取下一步处理人 也就是对应的二级部经理
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(earlyCourseApplyDto.getDeptNo())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(earlyCourseApplyDto.getDeptNo());
                    // 二级部主持经理作为下一步处理人
                    currentId = employeeDto.getBadge();
                    currentName = employeeDto.getEmpname();
                    mPara.put("userId", currentId);
                    // 流程变量
                    processInfo.setmPara(mPara);
                }
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                // 提前入场人员申请
                earlyCourseApplyDto.setInstanceid(processBean.getProcessInstanceId());
                earlyCourseApplyDto.setState("1");
                earlyCourseApplyDto.setStateName("处理中");
                earlyCourseApplyDto.setCurrentId(currentId);
                earlyCourseApplyDto.setCurrentName(currentName);
                earlyCourseApplyDto.setCreatedBy(earlyCourseApplyDto.getApplyer());
                earlyCourseApplyDto.setCreatedTime(new Date());
                insertPpmEarlyCourseApply(earlyCourseApplyDto);

                // 提前入场人员
                applyInfoDtos.stream().forEach(ai -> {
                    ai.setPid(earlyCourseApplyDto.getId());
                    ai.setInstanceid(earlyCourseApplyDto.getInstanceid());
                    ai.setCampaignTime(DateUtil.getPlusDays(new Date(),10L));
                    ai.setCreatedBy(earlyCourseApplyDto.getApplyer());
                    ai.setCreatedTime(new Date());
                });
                //外包提前入场人员信息
                ppmEarlyCourseApplyInfoService.addPpmEarlyCourseApplyInfo(applyInfoDtos);

                // 日志处理
                bps.setProjectNo(String.valueOf(earlyCourseApplyDto.getId()));
                bps.setConclusion("01");
                bps.setApplydesc("提前入场申请发起");
                bps.setRelaData(mPara);

                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                // 第一次流程后只做流转更新
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(earlyCourseApplyDto.getDeptNo())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(earlyCourseApplyDto.getDeptNo());
                    // 二级部主持经理作为下一步处理人
                    currentId = employeeDto.getBadge();
                    currentName = employeeDto.getEmpname();
                    mPara.put("userId", currentId);
                    // 流程变量
                    param.setmPara(mPara);
                }
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(earlyCourseApplyDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);

                earlyCourseApplyDto.setState("1");
                earlyCourseApplyDto.setStateName("处理中");
                earlyCourseApplyDto.setCurrentId(currentId);
                earlyCourseApplyDto.setCurrentName(currentName);
                earlyCourseApplyDto.setModifiedBy(earlyCourseApplyDto.getApplyer());
                earlyCourseApplyDto.setModifiedTime(new Date());
                updatePpmEarlyCourseApply(earlyCourseApplyDto);

                //在重新提交时，删除申请里删除的已提交数据
                ppmEarlyCourseApplyInfoService.deleteInCardNo(applyInfoDtos,earlyCourseApplyDto.getInstanceid());

                applyInfoDtos.stream().forEach(ai -> {
                    ai.setPid(earlyCourseApplyDto.getId());
                    ai.setInstanceid(earlyCourseApplyDto.getInstanceid());
                    ai.setCampaignTime(DateUtil.getPlusDays(new Date(),10L));
                    ai.setModifiedBy(earlyCourseApplyDto.getApplyer());
                    ai.setModifiedTime(new Date());
                });
                ppmEarlyCourseApplyInfoService.addPpmEarlyCourseApplyInfo(applyInfoDtos);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(earlyCourseApplyDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            assert processBean != null;
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            e.printStackTrace();
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String departmentHeadApproval(PpmEarlyCourseApplyDto infoDto) {
        BpsWorkFlowDto bps = infoDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            //记录流程状态
            infoDto.setState("0");
            infoDto.setStateName("重新提交");
            infoDto.setCurrentId(infoDto.getApplyer());
            infoDto.setCurrentName(infoDto.getApplyerName());
            //定义下一步处理人
            List<String> msgIds = Lists.newArrayList();
            List<String> msgNames;
            String currentId = "";
            String currentName = "";

            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
                //永赢租赁金融科技部-总经理室
                commEmployeeDto.setDepid(80000053);
                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
                        .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList());
                msgNames = commEmployeeService.getEmployeeLists(commEmployeeDto)
                        .stream().map(CommEmployeeDto::getEmpname).collect(Collectors.toList());

                StringJoiner joiner = new StringJoiner(",");
                msgIds.forEach(joiner::add);
                currentId = joiner.toString();
                StringJoiner joinerTwo = new StringJoiner(",");
                msgNames.forEach(joinerTwo::add);
                currentName = joinerTwo.toString();

                infoDto.setState("2");
                infoDto.setStateName("处理中");
                infoDto.setCurrentId(currentId);
                infoDto.setCurrentName(currentName);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("二级部经理处理");
            bps.setRelaData(mPara);

            //业务数据存储处理人
            infoDto.setModifiedBy(param.getUserId());
            infoDto.setModifiedTime(new Date());
            updatePpmEarlyCourseApply(infoDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgIds.toArray(new String[msgIds.size()]),bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]));
        } catch (Exception e) {
            log.error("二级部经理审批失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String generalManagerApproval(PpmEarlyCourseApplyDto courseApplyDto) {
        BpsWorkFlowDto bps = courseApplyDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            courseApplyDto.setState("0");
            courseApplyDto.setStateName("重新提交");
            courseApplyDto.setCurrentId(courseApplyDto.getApplyer());
            courseApplyDto.setCurrentName(courseApplyDto.getApplyerName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                courseApplyDto.setState("-1");
                courseApplyDto.setStateName("已结束");
                courseApplyDto.setCurrentId("");
                courseApplyDto.setCurrentName("");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("一级部总经理室审批");
            bps.setRelaData(mPara);

            courseApplyDto.setModifiedBy(param.getUserId());
            courseApplyDto.setModifiedTime(new Date());
            updatePpmEarlyCourseApply(courseApplyDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部总经理室审批失败！");
            e.printStackTrace();
            throw new BizException(e);
        }
    }

    /**
     * 对提交的数据做校验
     * @param applyDto 对象
     */
    private void vaildCommitData(PpmEarlyCourseApplyDto applyDto){
        List<PpmEarlyCourseApplyInfoDto> applyInfoDtos = applyDto.getApplyInfoDtos();

        //判断是否有申请人员
        if(applyInfoDtos == null || applyInfoDtos.isEmpty()){
            throw new BizException(ApiCode.FAIL, "无申请人员信息！");
        }

        //判断是否有已保存和提交的申请人员
        applyInfoDtos.forEach(infoDto ->{
            PpmEarlyCourseApplyInfoDto dto = ppmEarlyCourseApplyInfoService.queryDetailCert(infoDto.getCardNo());
            if(dto != null && !dto.getInstanceid().equals(infoDto.getInstanceid())){
                throw new BizException(ApiCode.FAIL, "所选申请人已经申请提前入场！");
            }
        });

        //判断提交的是否有重复的申请人员
        List<String> disList = applyInfoDtos.stream()
                .map(PpmEarlyCourseApplyInfoDto::getCardNo).distinct().collect(Collectors.toList());
        if(applyInfoDtos.size() != disList.size()){
            throw new BizException(ApiCode.FAIL, "提交数据存在重复人员！");
        }
    }


}
