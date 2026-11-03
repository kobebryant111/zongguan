package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Lists;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqRecordDto;
import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferDto;
import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmStaffTransfer;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.quartz.interfaces.IPpmStaffTransferJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqRecordService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmStaffTransferInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmStaffTransferService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.PostInfo;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
  * 人员移交表 接口实现类
  */
@Slf4j
@RequiredArgsConstructor
@Service("ppmStaffTransferService")
public class PpmStaffTransferService extends BaseDaoSupper
        implements IPpmStaffTransferService, IPpmStaffTransferJobService {
    /**
      * 实体类路径
      */
    private static String PPMSTAFFTRANSFER = "com.work.ppm.application.model.integratedmanager.PpmStaffTransfer";
    // 流程定义ID
    private static final String FLOW_ID = "ryyjsq";
    //流程名称
    private static final String FLOW_TITLE = "人员移交申请";
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private IPpmStaffTransferInfoService ppmStaffTransferInfoService;
    //花名册
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    @Autowired
    private IPpmKqRecordService ppmKqRecordService;

    @Override
    public int addPpmStaffTransfer(PpmStaffTransferDto ppmStaffTransferDto) {
        PpmStaffTransfer ppmStaffTransfer = JsonUtil.jsonToClass(ppmStaffTransferDto, PpmStaffTransfer.class);
        // 如果需要请set 主键id
        // ppmStaffTransfer.setId(getNextSequence(PpmStaffTransfer.class));
        return myBatisDao.executionPojo(PPMSTAFFTRANSFER, ppmStaffTransfer, IMyBatisDao.SQL.INSTER, "addPojo");
    }
    @Override
    public PpmStaffTransferDto queryDetail(PpmStaffTransferDto ppmStaffTransferDto) {
        PpmStaffTransfer ppmStaffTransfer = JsonUtil.jsonToClass(ppmStaffTransferDto, PpmStaffTransfer.class);
        PpmStaffTransfer data = myBatisDao.selectClass(PPMSTAFFTRANSFER, ppmStaffTransfer,"queryDetail");
        return JsonUtil.jsonToClass(data, PpmStaffTransferDto.class);
    }

    @Override
    public int updatePpmStaffTransfer(PpmStaffTransferDto ppmStaffTransferDto) {
        PpmStaffTransfer ppmStaffTransfer = JsonUtil.jsonToClass(ppmStaffTransferDto, PpmStaffTransfer.class);
        return myBatisDao.executionPojo(PPMSTAFFTRANSFER, ppmStaffTransfer, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePpmStaffTransfer(PpmStaffTransferDto ppmStaffTransferDto) {
        PpmStaffTransfer staffTransfer = JsonUtil.jsonToClass(ppmStaffTransferDto,PpmStaffTransfer.class);
        int count = myBatisDao.executionPojo(
                PPMSTAFFTRANSFER, staffTransfer, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");

        if (StrUtils.isNotBlank(staffTransfer.getInstanceid())) {
            // 清理关联业务数据
            ppmStaffTransferInfoService.deleteByInstanceId(staffTransfer.getInstanceid());
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(staffTransfer.getInstanceid());
            CamundaUtils.terminateProcess(staffTransfer.getInstanceid());
        }
        return count;
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public String managerCreated(PpmStaffTransferDto ppmStaffTransferDto) {
        BpsWorkFlowDto bps = ppmStaffTransferDto.getBps();
        List<PpmStaffTransferInfoDto> staffTransferDtos = ppmStaffTransferDto.getTransferInfoDtos();

        //数据校验
        vaildCommitData(ppmStaffTransferDto);

        if (ppmStaffTransferDto.getId() != null ) {
            this.updatePpmStaffTransfer(ppmStaffTransferDto);

            staffTransferDtos.forEach(ppmStaffTransferInfoService::updatePpmStaffTransferInfo);
            return null;
        }
        try {

            ProcessBean processBean;
            // 添加人员移交流程记录
            Long key = super.getNextSequence(PpmEarlyCourseApply.class);
            ppmStaffTransferDto.setId(key);
            ppmStaffTransferDto.setFlowId(FLOW_ID);
            ppmStaffTransferDto.setCreatedTime(new Date());
            ppmStaffTransferDto.setCreatedBy(bps.getUserId());
            ppmStaffTransferDto.setState("0");
            ppmStaffTransferDto.setStateName("未发起");

            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(ppmStaffTransferDto.getId()));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            ppmStaffTransferDto.setInstanceid(processBean.getProcessInstanceId());
            addPpmStaffTransfer(ppmStaffTransferDto);

            StringJoiner userName = new StringJoiner(BusinessUtil.REGEX_COMMA);
            staffTransferDtos.forEach(infoDto ->{
                userName.add(infoDto.getUserName());
                infoDto.setDeptIdBefore(ppmStaffTransferDto.getApplyerDeptId());
                infoDto.setDeptIdAfter(commEmployeeService.getDeptNo(infoDto.getPmIdAfter()));
                infoDto.setInstanceid(ppmStaffTransferDto.getInstanceid());
                infoDto.setCreatedBy(bps.getUserId());
                infoDto.setCreatedTime(new Date());
                infoDto.setPmIdBefore(ppmStaffTransferDto.getApplyerId());
            });
            ppmStaffTransferInfoService.addPpmStaffTransferInfo(staffTransferDtos);
            // 日志处理
            bps.setProjectNo(String.valueOf(ppmStaffTransferDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc("外包人员人员移交申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + userName + DateUtil.dateToNoBar(new Date()) + "】" + FLOW_TITLE);
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return "pass";
        } catch (Exception e) {
            throw new BizException(e);
        }
    }*/

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String managerInit(PpmStaffTransferDto staffTransferDto) {
        List<PpmStaffTransferInfoDto> transferInfoDtos = staffTransferDto.getTransferInfoDtos();
        BpsWorkFlowDto bps = staffTransferDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        //数据校验
        vaildCommitData(staffTransferDto);

        //流程逻辑
        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(staffTransferDto.getInstanceid())) {
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
                transferInfoDtos.stream().forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName(joiner + FLOW_TITLE);
                // 如果需要请set 主键id
                staffTransferDto.setId(getNextSequence(PpmStaffTransfer.class));
                staffTransferDto.setCreatedTime(new Date());

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(staffTransferDto.getId()));
                Map mPara = new HashMap();
                //获取下一步处理人 也就是对应的二级部经理
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(staffTransferDto.getApplyerDeptId())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(staffTransferDto.getApplyerDeptId());
                    // 二级部主持经理作为下一步处理人
                    currentId = employeeDto.getBadge();
                    currentName = employeeDto.getEmpname();
                    mPara.put("userId", currentId);
                    // 流程变量
                    processInfo.setmPara(mPara);
                }
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                // 人员移交申请
                staffTransferDto.setInstanceid(processBean.getProcessInstanceId());
                staffTransferDto.setState("1");
                staffTransferDto.setStateName("处理中");
                staffTransferDto.setCurrentId(currentId);
                staffTransferDto.setCurrentName(currentName);
                staffTransferDto.setCreatedBy(staffTransferDto.getApplyerId());
                staffTransferDto.setCreatedTime(new Date());
                addPpmStaffTransfer(staffTransferDto);

                // 人员移交申请
                transferInfoDtos.forEach(dto -> {
                    dto.setDeptIdBefore(staffTransferDto.getApplyerDeptId());
//                    dto.setDeptIdAfter(commEmployeeService.getDeptNo(dto.getPmIdAfter()));
                    dto.setPmIdBefore(staffTransferDto.getApplyerId());
                    dto.setInstanceid(staffTransferDto.getInstanceid());
                    dto.setCreatedBy(staffTransferDto.getApplyerId());
                    dto.setCreatedTime(staffTransferDto.getCreatedTime());
                    ppmStaffTransferInfoService.insertPpmStaffTransferInfo(dto);
                });
                //外包提前入场人员信息
//                ppmStaffTransferInfoService.addPpmStaffTransferInfo(transferInfoDtos);

                // 日志处理
                bps.setProjectNo(String.valueOf(staffTransferDto.getId()));
                bps.setConclusion("01");
                bps.setApplydesc("人员移交申请发起");
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
                if (StringUtil.isNotBlank(staffTransferDto.getApplyerDeptId())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(staffTransferDto.getApplyerDeptId());
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
                StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
                transferInfoDtos.stream().forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName(joiner + FLOW_TITLE);
                bps.setProcessInstId(staffTransferDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);

                staffTransferDto.setState("1");
                staffTransferDto.setStateName("处理中");
                staffTransferDto.setCurrentId(currentId);
                staffTransferDto.setCurrentName(currentName);
                staffTransferDto.setModifiedBy(bps.getUserId());
                staffTransferDto.setModifiedTime(new Date());
                updatePpmStaffTransfer(staffTransferDto);

                //在重新提交时，删除申请里删除的已提交数据
                ppmStaffTransferInfoService.deleteInJobId(transferInfoDtos,staffTransferDto.getInstanceid());

                transferInfoDtos.forEach(ai -> {
                    //移交状态应前端传值
                    ai.setTransferState("N");
                    ai.setDeptIdBefore(staffTransferDto.getApplyerDeptId());
                    ai.setPmIdBefore(staffTransferDto.getApplyerId());
                    ai.setInstanceid(staffTransferDto.getInstanceid());
                    ai.setModifiedBy(staffTransferDto.getModifiedBy());
                    ai.setModifiedTime(staffTransferDto.getModifiedTime());
                });
                ppmStaffTransferInfoService.addPpmStaffTransferInfo(transferInfoDtos);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(staffTransferDto.getInstanceid());
                CamundaUtils.addFlowLogCus(bps, taskBean);
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
    public String deptManagerApproval(PpmStaffTransferDto staffTransferDto) {
        BpsWorkFlowDto bps = staffTransferDto.getBps();

        List<PpmStaffTransferInfoDto> transferInfoDtos = staffTransferDto.getTransferInfoDtos();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            staffTransferDto.setState("0");
            staffTransferDto.setStateName("重新提交");
            staffTransferDto.setCurrentId(staffTransferDto.getApplyerId());
            staffTransferDto.setCurrentName(staffTransferDto.getApplyerName());
            String currentId = "";
            String currentName = "";

            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                String deptIdAfter = transferInfoDtos.get(0).getDeptIdAfter();
                if (StringUtil.isNotBlank(deptIdAfter)) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(deptIdAfter);
                    // 二级部主持经理作为下一步处理人
                    currentId = employeeDto.getBadge();
                    currentName = employeeDto.getEmpname();
                    mPara.put("userId", currentId);
                    // 流程变量
                    param.setmPara(mPara);
                }
                staffTransferDto.setState("2");
                staffTransferDto.setStateName("处理中");
                staffTransferDto.setCurrentId(currentId);
                staffTransferDto.setCurrentName(currentName);
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
            updatePpmStaffTransfer(staffTransferDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("二级部经理审批失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String deptManagerApprovalTransfer(PpmStaffTransferDto staffTransferDto) {
        BpsWorkFlowDto bps = staffTransferDto.getBps();

        List<PpmStaffTransferInfoDto> transferInfoDtos = staffTransferDto.getTransferInfoDtos();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            staffTransferDto.setState("0");
            staffTransferDto.setStateName("重新提交");
            staffTransferDto.setCurrentId(staffTransferDto.getApplyerId());
            staffTransferDto.setCurrentName(staffTransferDto.getApplyerName());
            // 通过状态
            StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            StringJoiner joinerTwo = new StringJoiner(BusinessUtil.REGEX_COMMA);
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");

                List<PostInfo> userList = new ArrayList<>();
                PostInfo postInfo;

                List<String> list = transferInfoDtos.stream().map(PpmStaffTransferInfoDto::getPmIdAfter)
                                .distinct().collect(Collectors.toList());
                for (String str : list) {
                    postInfo = new PostInfo();
                    postInfo.setType("user");
                    postInfo.setUserId(str);
                    joiner.add(str);
                    CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(str);
                    if(commEmployee != null){
                        joinerTwo.add(commEmployee.getEmpname());
                    }
                    userList.add(postInfo);
                }

                mPara.put("userList", userList);// 下一步审批人
                param.setmPara(mPara);// 流程变量
                staffTransferDto.setState("3");
                staffTransferDto.setStateName("处理中");
                staffTransferDto.setCurrentId(joiner.toString());
                staffTransferDto.setCurrentName(joinerTwo.toString());
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("移交后二级部经理处理");
            bps.setRelaData(mPara);

            //业务数据存储处理人
            staffTransferDto.setModifiedBy(param.getUserId());
            staffTransferDto.setModifiedTime(new Date());
            updatePpmStaffTransfer(staffTransferDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            List<String> msgList = Lists.newArrayList();
            if(taskBean != null){
                taskBean.getTaskList().forEach(map -> msgList.add(map.get("assignee").toString()));
            }
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgList.toArray(new String[msgList.size()]),bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgList.toArray(new String[msgList.size()]));
        } catch (Exception e) {
            log.error("移交后二级部经理审批失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String managerApprovalTransfer(PpmStaffTransferDto staffTransferDto) {
        BpsWorkFlowDto bps = staffTransferDto.getBps();

        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            staffTransferDto.setState("0");
            staffTransferDto.setStateName("重新提交");
            staffTransferDto.setCurrentId(staffTransferDto.getApplyerId());
            staffTransferDto.setCurrentName(staffTransferDto.getApplyerName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                staffTransferDto.setState("-1");
                staffTransferDto.setStateName("已结束");
                staffTransferDto.setCurrentId("");
                staffTransferDto.setCurrentName("");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("移交后项目经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());

            // 如果流程已完成成返回空
            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                saveOutSourcingBasic(staffTransferDto.getTransferInfoDtos());
            }

            staffTransferDto.setModifiedBy(bps.getUserId());
            staffTransferDto.setModifiedTime(new Date());
            updatePpmStaffTransfer(staffTransferDto);

            List<String> msgList = Lists.newArrayList();
            if(taskBean != null){
                taskBean.getTaskList().forEach(map -> msgList.add(map.get("assignee").toString()));
            }

            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,msgList.toArray(new String[0]),bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgList.toArray(new String[0]));
        } catch (Exception e) {
            log.error("移交后项目经理审批失败！");
            throw new BizException(e);
        }
    }

    /**
     * 对提交的数据做校验
     * @param staffTransferDto 对象
     */
    private void vaildCommitData(PpmStaffTransferDto staffTransferDto){
        List<PpmStaffTransferInfoDto> staffTransferInfoDtos = staffTransferDto.getTransferInfoDtos();

        //判断是否有申请人员
        if(staffTransferInfoDtos == null || staffTransferInfoDtos.isEmpty()){
            throw new BizException(ApiCode.FAIL, "无申请人员信息！");
        }

        //判断是否有已保存和提交的申请人员
        staffTransferInfoDtos.forEach(infoDto ->{
            PpmStaffTransferInfoDto dto = new PpmStaffTransferInfoDto();
            dto.setJobId(infoDto.getJobId());
            List<PpmStaffTransferInfoDto> staffTransferInfoDtoList = ppmStaffTransferInfoService.queryBaseList(dto);
            if(staffTransferInfoDtoList != null && !staffTransferInfoDtoList.isEmpty()){
                long num = staffTransferInfoDtoList.stream()
                        .filter(dto1 ->
                                dto1.getInstanceid() != null && !dto1.getInstanceid().equals(infoDto.getInstanceid()))
                        .filter(dto1 -> !"-1".equals(dto1.getState()))
                        .filter(dto1-> !dto1.getJobId().equals(infoDto.getJobId())).count();
                if(num > 0){
                    throw new BizException(ApiCode.FAIL,"所选人员存在已保存或已提交流程。");
                }
            }
        });

        staffTransferInfoDtos.forEach(ppmStaffTransferInfoDto -> {
            PpmKqRecordDto kqRecordDto = new PpmKqRecordDto();
            kqRecordDto.setWhetherHandle("1");
            kqRecordDto.setJobId(ppmStaffTransferInfoDto.getJobId());
            List<PpmKqRecordDto> recordDtos = ppmKqRecordService.queryBaseInfoList(kqRecordDto);
            if(recordDtos != null && !recordDtos.isEmpty()){
                throw new BizException(ApiCode.FAIL,"请先处理完异常流程");
            }
        });

        //不同部门人员请分开提交
        List<String> distList = staffTransferInfoDtos.stream()
                .map(PpmStaffTransferInfoDto::getDeptIdAfter).distinct().collect(Collectors.toList());
        //一条:  多条:
        if(distList.size() > 1){
            throw new BizException(ApiCode.FAIL, "不同部门人员请分开提交！");
        }

        //不同外包方式的人员请分开提交
        List<String> distTypeList = staffTransferInfoDtos.stream()
                .map(PpmStaffTransferInfoDto::getOutsourceType).distinct().collect(Collectors.toList());
        //一条:  多条:
        if(distTypeList.size() > 1){
            throw new BizException(ApiCode.FAIL, "不同外包方式的人员请分开提交！");
        }

        //判断提交的是否有重复的申请人员
        List<String> disList = staffTransferInfoDtos.stream()
                .map(PpmStaffTransferInfoDto::getJobId).distinct().collect(Collectors.toList());
        if(staffTransferInfoDtos.size() != disList.size()){
            throw new BizException(ApiCode.FAIL, "提交数据存在重复人员！");
        }
    }

    /**
     * 变更后的信息修改入花名册
     * @param staffTransferInfoDtos 对象集合
     */
    private void saveOutSourcingBasic(List<PpmStaffTransferInfoDto> staffTransferInfoDtos){
        staffTransferInfoDtos.forEach(dto ->{
            PpmOutsourcingBasicInfo basicDto = ppmOutsourcingBasicService.getBaseInfo(dto.getJobId());
            if(basicDto != null){
                Date now = DateUtil.dateToDate(new Date(),"yyyy-MM-dd");
                if(now.after(dto.getTransferTime()) || now.equals(dto.getTransferTime())){
                    PpmOutsourcingBasicInfo basicInfo = new PpmOutsourcingBasicInfo();
                    basicInfo.setId(basicDto.getId());
                    basicInfo.setDeptId(dto.getDeptIdAfter());
                    if("0".equals(dto.getOutsourceType())){
                        basicInfo.setPersonInCharge(dto.getPmIdAfter());
                        basicInfo.setProjectNumber(dto.getProjectId());
                        basicInfo.setProjectManager(dto.getPmIdAfter());
                    }else {
                        basicInfo.setPersonInCharge(dto.getPmIdAfter());
                        basicInfo.setSystemNo(dto.getSystemId());
                        basicInfo.setSystemPrincipal(dto.getPmIdAfter());
                    }

                    PpmOutsourcingBasic outsourcingBasic = JsonUtil.jsonToClass(basicInfo, PpmOutsourcingBasic.class);
                    ppmOutsourcingBasicService.updatePpmOutsourcingBasicTransfer(outsourcingBasic);

                    PpmStaffTransferInfoDto transferInfoDto = new PpmStaffTransferInfoDto();
                    transferInfoDto.setId(dto.getId());
                    transferInfoDto.setTransferState("Y");
                    ppmStaffTransferInfoService.updatePpmStaffTransferInfo(transferInfoDto);
                }
            }
        });
        log.info("人员移交完成。");

    }

    @Override
    public void handleTransferTime() {
        //首先查询transferState = N 的数据
        PpmStaffTransferInfoDto ppmStaffTransferInfoDto = new PpmStaffTransferInfoDto();
        ppmStaffTransferInfoDto.setTransferState("N");
        ppmStaffTransferInfoDto.setTransferTime(DateUtil.dateToDate(new Date(),"yyyy-MM-dd"));
        ppmStaffTransferInfoDto.setState("-1");
        List<PpmStaffTransferInfoDto> transferInfoDtos =
                ppmStaffTransferInfoService.queryDetailList(ppmStaffTransferInfoDto);

        if(transferInfoDtos != null && transferInfoDtos.size() != 0){
            saveOutSourcingBasic(transferInfoDtos);
        }

    }
}
