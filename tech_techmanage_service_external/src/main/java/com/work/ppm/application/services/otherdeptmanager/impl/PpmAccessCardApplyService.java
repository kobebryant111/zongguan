package com.work.ppm.application.services.otherdeptmanager.impl;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoExportDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardLedgerDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.enums.XmlIdEnums;
import com.work.ppm.application.model.otherdeptmanager.PpmAccessCardApply;
import com.work.ppm.application.model.otherdeptmanager.ext.PpmAccessCardApplyExt;
import com.work.ppm.application.model.othermanager.PpmDocument;
import com.work.ppm.application.model.othermanager.PpmDocumentCatalog;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyInfoService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardApplyService;
import com.work.ppm.application.services.otherdeptmanager.IPpmAccessCardLedgerService;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.workflowmanager.IWorkflowAccessCardService;
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
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
  *  监保部台账领用申请表 接口实现类
  */
@Slf4j
@Service("ppmAccessCardApplyService")
public class PpmAccessCardApplyService extends BaseDaoSupper implements IPpmAccessCardApplyService {
    /**
      * 实体类路径
      */
    private static String PPMACCESSCARDAPPLY = "com.work.ppm.application.model.otherdeptmanager.PpmAccessCardApply";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    private String FLOW_ID ;
    private String FLOW_TITLE;
    // 流程定义ID
    private static final String FLOW_ID_RV = "lymjsq";
    //流程名称
    private static final String FLOW_TITLE_RV = "领用门禁申请";
    // 流程定义ID
    private static final String FLOW_ID_RT = "ghmjsq";
    //流程名称
    private static final String FLOW_TITLE_RT = "归还门禁申请";
    @Autowired
    private ICommEmployeeService commEmployeeService;

    //工作流服务
    @Autowired
    private WorkFlowHttpClientService workFlowHttpClientService;

    //工作流服务
    @Autowired
    private IPpmAccessCardApplyInfoService accessCardApplyInfoService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmAccessCardLedgerService ppmAccessCardLedgerService;

    @Autowired
    private IPpmDocumentCatalogService ppmDocumentCatalogService;

    @Autowired
    private IPpmDocumentService ppmDocumentService;

    @Value("${file.upload-path}")
    private String uploadPath;
    @Value("${file.assessCard.ar01-docx}")
    private String arFileDocx;
    @Value("${file.assessCard.ar01-xlsx}")
    private String arFileXlsx;
    @Value("${appUrl.accessCard}")
    private String accessCardUrlValue;

    @Lazy
    @Autowired
    private IWorkflowAccessCardService workflowAccessCardService;

    @Autowired
    private IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    @Override
    public int addPpmAccessCardApply(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        PpmAccessCardApply ppmAccessCardApply = JsonUtil.jsonToClass(ppmAccessCardApplyDto, PpmAccessCardApply.class);
        // 如果需要请set 主键id
//        ppmAccessCardApply.setId(DateUtil.getDateRandom());
        ppmAccessCardApply.setIsDel("0");
        myBatisDao.executionPojo(
            PPMACCESSCARDAPPLY, ppmAccessCardApply, IMyBatisDao.SQL.INSTER, XmlIdEnums.ADD.getCode());
        return 1;
    }
    @Override
    public PpmAccessCardApplyDto queryDetail(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        PpmAccessCardApply ppmAccessCardApply = JsonUtil.jsonToClass(ppmAccessCardApplyDto, PpmAccessCardApply.class);
        PpmAccessCardApply data =
            myBatisDao.selectClass(PPMACCESSCARDAPPLY, ppmAccessCardApply,XmlIdEnums.QUERY.getCode());
        return JsonUtil.jsonToClass(data, PpmAccessCardApplyDto.class);
    }

    @Override
    public PpmAccessCardApplyDto queryDetailForInstanceId(String instanceId) {
        PpmAccessCardApply ppmAccessCardApply = new PpmAccessCardApply();
        ppmAccessCardApply.setInstanceId(instanceId);
        PpmAccessCardApplyExt data =
                myBatisDao.selectClass(PPMACCESSCARDAPPLY, ppmAccessCardApply,"queryDetailForInstanceId");
        return JsonUtil.jsonToClass(data, PpmAccessCardApplyDto.class);
    }

    @Override
    public int updatePpmAccessCardApply(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        PpmAccessCardApply ppmAccessCardApply = JsonUtil.jsonToClass(ppmAccessCardApplyDto, PpmAccessCardApply.class);
        return myBatisDao.executionPojo(
            PPMACCESSCARDAPPLY, ppmAccessCardApply, IMyBatisDao.SQL.UPDATE,XmlIdEnums.UPDATE.getCode());
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public int deletePpmAccessCardApply(PpmAccessCardApplyDto ppmAccessCardApplyDto) {

        ppmAccessCardApplyDto.setIsDel("1");
        int count = updatePpmAccessCardApply(ppmAccessCardApplyDto);

        if (StrUtils.isNotBlank(ppmAccessCardApplyDto.getInstanceId())) {
            List<PpmAccessCardApplyInfoDto> list =
                    accessCardApplyInfoService.queryDetailList(PpmAccessCardApplyInfoDto.builder()
                                    .instanceId(ppmAccessCardApplyDto.getInstanceId()).build(),
                            "getListForInstanceId");
            list.forEach(obj ->{
                //删除或修改现台账
                PpmAccessCardLedgerDto ppmAccessCardLedgerDto = ppmAccessCardLedgerService.queryDetail(
                        PpmAccessCardLedgerDto.builder().id(obj.getBizObjectId()).build());

                if ("Receive".equals(ppmAccessCardApplyDto.getType())){
                    ppmAccessCardLedgerDto.setIfMakeCard("N");
                    if (StringUtils.isNotEmpty(ppmAccessCardLedgerDto.getPid())){
                        ppmAccessCardLedgerDto.setIsDel("1");
                    }
                }else if ("Return".equals(ppmAccessCardApplyDto.getType())){
                    ppmAccessCardLedgerDto.setIfCreateReturn("N");
                    ppmAccessCardLedgerDto.setIfReturnCard("N");
                }
                ppmAccessCardLedgerService.updatePpmAccessCardLedger(ppmAccessCardLedgerDto);

                if (StringUtils.isNotEmpty(ppmAccessCardLedgerDto.getPid())){
                    //修改原台账
                    ppmAccessCardLedgerService.updatePpmAccessCardLedger(PpmAccessCardLedgerDto.builder()
                            .id(ppmAccessCardLedgerDto.getPid()).state("NEW").build());
                }
            });
            // 清理关联业务数据
            accessCardApplyInfoService.updatePpmAccessCardApplyInfo(
                    PpmAccessCardApplyInfoDto.builder()
                            .instanceId(ppmAccessCardApplyDto.getInstanceId())
                            .isDel("1").build());

            // 删除流程相关数据
            CamundaUtils.delProjectFlow(ppmAccessCardApplyDto.getInstanceId());
            CamundaUtils.terminateProcess(ppmAccessCardApplyDto.getInstanceId());
        }
        return count;
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String flowInit(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        BpsWorkFlowDto bps = ppmAccessCardApplyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        //数据校验
        vaildCommitData(ppmAccessCardApplyDto);

        //外包人员信息
        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoList = ppmAccessCardApplyDto.getAccessCardApplyInfoList();
        //流程逻辑
        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(ppmAccessCardApplyDto.getInstanceId())) {
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
                accessCardApplyInfoList.forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName(joiner + FLOW_TITLE);
                // 如果需要请set 主键id
                ppmAccessCardApplyDto.setId(DateUtil.getDateRandom());
                ppmAccessCardApplyDto.setCreatedTime(new Date());

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(ppmAccessCardApplyDto.getId());
                Map mPara = new HashMap();

                // *** 获取下一步处理人
                //从字典内获取到下一个节点处理人
                EosDictEntry eosDictEntry =
                        eosDictEntryService.getTypeidForDetail("ACCESS_CARD_FLOW_CURRENT","gmApprove");
                CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(eosDictEntry.getDictname());
                String currentName = "";
                if (commEmployee != null){
                    currentName = commEmployee.getEmpname();
                }

                mPara.put("userId",eosDictEntry.getDictname());// 下一步审批人
                processInfo.setmPara(mPara);

                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                // 领用门禁详情
                ppmAccessCardApplyDto.setInstanceId(processBean.getProcessInstanceId());
                ppmAccessCardApplyDto.setState("1");
                ppmAccessCardApplyDto.setStateName("处理中");
                ppmAccessCardApplyDto.setCurrentId(eosDictEntry.getDictname());
                ppmAccessCardApplyDto.setCurrentName(currentName);
                ppmAccessCardApplyDto.setCreatedBy(ppmAccessCardApplyDto.getApplyerId());
                ppmAccessCardApplyDto.setCreatedTime(new Date());
                addPpmAccessCardApply(ppmAccessCardApplyDto);

                // 领用门禁详情
                accessCardApplyInfoList.forEach(infoDto -> {
                    PpmAccessCardLedgerDto accessCardLedgerDto = ppmAccessCardLedgerService.queryDetail(
                            PpmAccessCardLedgerDto.builder().id(infoDto.getId()).build());

                    if (accessCardLedgerDto != null){
                        if ("E".equals(accessCardLedgerDto.getIfReturnCard())){
                            String id = DateUtil.getDateRandom();
                            PpmAccessCardLedgerDto accessCardLedgerDto1 = PpmAccessCardLedgerDto.builder().build();
                            accessCardLedgerDto1.setId(id);
                            accessCardLedgerDto1.setPid(accessCardLedgerDto.getId());
                            accessCardLedgerDto1.setJobId(infoDto.getJobId());
                            accessCardLedgerDto1.setIfMakeCard("W");
                            accessCardLedgerDto1.setVirtualMachineId(infoDto.getVirtualMachineId());
                            accessCardLedgerDto1.setAccountNumber(accessCardLedgerDto.getAccountNumber());
                            accessCardLedgerDto1.setPlaceOfAccount(accessCardLedgerDto.getPlaceOfAccount());
                            accessCardLedgerDto1.setCreditCardNumbers(accessCardLedgerDto.getCreditCardNumbers());
                            accessCardLedgerDto1.setOutBankName(accessCardLedgerDto.getOutBankName());
                            accessCardLedgerDto1.setCreatedTime(new Date());
                            accessCardLedgerDto1.setCreatedBy(processInfo.getUserId());
                            ppmAccessCardLedgerService.addPpmAccessCardLedger(accessCardLedgerDto1);

                            accessCardLedgerDto.setState("OLD");
                            ppmAccessCardLedgerService.updatePpmAccessCardLedger(accessCardLedgerDto);

                            accessCardApplyInfoService.addPpmAccessCardApplyInfo(PpmAccessCardApplyInfoDto.builder()
                                    .instanceId(ppmAccessCardApplyDto.getInstanceId())
                                    .bizObjectId(id)
                                    .jobId(infoDto.getJobId())
                                    .virtualMachineId(infoDto.getVirtualMachineId())
                                    .createdBy(ppmAccessCardApplyDto.getApplyerId())
                                    .createdTime(ppmAccessCardApplyDto.getCreatedTime())
                                    .build());
                        }else {
                            accessCardLedgerDto.setIfMakeCard("W");
                            accessCardLedgerDto.setVirtualMachineId(infoDto.getVirtualMachineId());
                            accessCardLedgerDto.setModifiedTime(new Date());
                            accessCardLedgerDto.setModifiedBy(processInfo.getUserId());
                            ppmAccessCardLedgerService.updatePpmAccessCardLedger(accessCardLedgerDto);

                            PpmAccessCardApplyInfoDto accessCardApplyInfoDto = PpmAccessCardApplyInfoDto.builder()
                                    .instanceId(ppmAccessCardApplyDto.getInstanceId())
                                    .bizObjectId(accessCardLedgerDto.getId())
                                    .jobId(infoDto.getJobId())
                                    .virtualMachineId(infoDto.getVirtualMachineId())
                                    .createdBy(ppmAccessCardApplyDto.getApplyerId())
                                    .createdTime(ppmAccessCardApplyDto.getCreatedTime())
                                    .build();
                            accessCardApplyInfoService.addPpmAccessCardApplyInfo(accessCardApplyInfoDto);
                        }
                    }
                });

                // 日志处理
                bps.setProjectNo(ppmAccessCardApplyDto.getId());
                bps.setConclusion("01");
                bps.setApplydesc("门禁卡申请发起");
                bps.setRelaData(mPara);

                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                //推送待办
                String imgTxDsc = "金融科技部(" + ppmAccessCardApplyDto.getApplyerName()
                        + ppmAccessCardApplyDto.getApplyerId() + ")，于"
                        + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")
                        + "，发起金融科技部外包人员门禁卡申请流程，请及时登录手机工作平台处理。";
                workflowAccessCardService.pushTaskCommon(ppmAccessCardApplyDto.getCurrentId(),"门禁卡领用申请科技部负责人审批",
                        imgTxDsc,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"),"1");
                //
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                // 第一次流程后只做流转更新
                return flowInitExt(ppmAccessCardApplyDto,bps,accessCardApplyInfoList);
            }
        } catch (Exception e) {
            assert processBean != null;
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            e.printStackTrace();
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }

    /**
     * 退回处理
     * @param ppmAccessCardApplyDto 对象
     * @param bps 流程对象
     * @param accessCardApplyInfoList 集合
     * @return string
     * @throws Exception 异常
     */
    public String flowInitExt (PpmAccessCardApplyDto ppmAccessCardApplyDto,BpsWorkFlowDto bps,
                                List<PpmAccessCardApplyInfoDto> accessCardApplyInfoList) throws Exception {

        // 执行任务
        TaskBean param = new TaskBean();
        param.setTenantId(BusinessConstant.TENANT_ID);// 租户
        param.setTaskId(bps.getTaskId());// 任务id
        param.setUserId(bps.getUserId());// 处理人
        param.setComment(bps.getSuggest());// 审批意见
        Map mPara = new HashMap();
        //下一步处理人
        //从字典内获取到下一个节点处理人
        EosDictEntry eosDictEntry =
                eosDictEntryService.getTypeidForDetail("ACCESS_CARD_FLOW_CURRENT","gmApprove");
        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(eosDictEntry.getDictname());
        String currentName = "";
        if (commEmployee != null){
            currentName = commEmployee.getEmpname();
        }
        mPara.put("userId",eosDictEntry.getDictname());// 下一步审批人

        // 流程变量
        param.setmPara(mPara);
        //存储节点日志
        ppmAccessCardApplyDto.setState("1");
        ppmAccessCardApplyDto.setStateName("处理中");
        ppmAccessCardApplyDto.setCurrentId(eosDictEntry.getDictname());
        ppmAccessCardApplyDto.setCurrentName(currentName);
        // 提交任务
        workFlowHttpClientService.submitTask(param);
        // 日志处理
        bps.setProcessDefName(FLOW_ID);
        StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
        accessCardApplyInfoList.forEach(ai -> joiner.add(ai.getUserName()));
        bps.setProcessInstName(joiner + FLOW_TITLE);
        bps.setProcessInstId(ppmAccessCardApplyDto.getInstanceId());
        bps.setApplydesc(bps.getProcessInstDesc());
        bps.setRelaData(mPara);
        bps.setConclusion("01");
        bps.setSuggest("重新提交");
        bps.setApplydesc(FLOW_TITLE);

        ppmAccessCardApplyDto.setModifiedBy(bps.getUserId());
        ppmAccessCardApplyDto.setModifiedTime(new Date());
        updatePpmAccessCardApply(ppmAccessCardApplyDto);

        // 获取流程信息
        TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(ppmAccessCardApplyDto.getInstanceId());

        //推送待办
        String imgTxDsc = "金融科技部(" + ppmAccessCardApplyDto.getApplyerName()
                + ppmAccessCardApplyDto.getApplyerId() + ")，于"
                + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")
                + "，发起金融科技部外包人员门禁卡申请流程，请及时登录手机工作平台处理。";
        workflowAccessCardService.pushTaskCommon(ppmAccessCardApplyDto.getCurrentId(),"门禁卡领用申请科技部负责人审批",
                imgTxDsc,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"),"1");
        //日志记录
        CamundaUtils.addFlowLog(bps, taskBean);
        CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
        // 返回下一节点信息
        return CamundaUtils.findNextTaskInfo(taskBean, null);
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String returnFlowInit(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        BpsWorkFlowDto bps = ppmAccessCardApplyDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        //数据校验
        vaildCommitData(ppmAccessCardApplyDto);

        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoList = ppmAccessCardApplyDto.getAccessCardApplyInfoList();
        //流程逻辑
        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(ppmAccessCardApplyDto.getInstanceId())) {
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
                accessCardApplyInfoList.forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName(joiner + FLOW_TITLE);
                // 如果需要请set 主键id
                ppmAccessCardApplyDto.setId(DateUtil.getDateRandom());
                ppmAccessCardApplyDto.setCreatedTime(new Date());

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(ppmAccessCardApplyDto.getId());
                Map mPara = new HashMap();
                //获取下一步处理人
                StringJoiner joinerOne = new StringJoiner(BusinessUtil.REGEX_COMMA);
                StringJoiner joinerTwo = new StringJoiner(BusinessUtil.REGEX_COMMA);
                //下一步处理人
                //从字典内获取到下一个节点处理人
                String [] userIds = commEmployeeService.queryUserIdByRole("JBB_AFFIRM").split(BusinessUtil.REGEX_COMMA);
                for (String str : userIds) {
                    joinerOne.add(str);
                    CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(str);
                    if(commEmployee != null){
                        joinerTwo.add(commEmployee.getEmpname());
                    }
                }
                processInfo.setmPara(mPara);
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                // 人员移交申请
                ppmAccessCardApplyDto.setInstanceId(processBean.getProcessInstanceId());
                ppmAccessCardApplyDto.setState("1");
                ppmAccessCardApplyDto.setStateName("处理中");
                ppmAccessCardApplyDto.setCurrentId(joinerOne.toString());
                ppmAccessCardApplyDto.setCurrentName(joinerTwo.toString());
                ppmAccessCardApplyDto.setCreatedBy(ppmAccessCardApplyDto.getApplyerId());
                ppmAccessCardApplyDto.setCreatedTime(new Date());
                addPpmAccessCardApply(ppmAccessCardApplyDto);

                // 归还门禁详情
                accessCardApplyInfoList.forEach(infoDto -> {
                    PpmAccessCardLedgerDto accessCardLedgerDto = PpmAccessCardLedgerDto.builder().build();
                    accessCardLedgerDto.setId(infoDto.getId());
                    accessCardLedgerDto.setIfCreateReturn("Y");
                    accessCardLedgerDto.setModifiedBy(processInfo.getUserId());
                    accessCardLedgerDto.setModifiedTime(new Date());
                    ppmAccessCardLedgerService.updatePpmAccessCardLedger(accessCardLedgerDto);

                    infoDto.setIfReturnCard("");
                    infoDto.setBizObjectId(infoDto.getId());
                    infoDto.setInstanceId(ppmAccessCardApplyDto.getInstanceId());
                    infoDto.setCreatedBy(ppmAccessCardApplyDto.getApplyerId());
                    infoDto.setCreatedTime(ppmAccessCardApplyDto.getCreatedTime());
                    accessCardApplyInfoService.addPpmAccessCardApplyInfo(infoDto);
                });

                // 日志处理
                bps.setProjectNo(ppmAccessCardApplyDto.getId());
                bps.setConclusion("01");
                bps.setApplydesc("门禁卡归还发起");
                bps.setRelaData(mPara);

                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                String imgTxDsc = "金融科技部(" + ppmAccessCardApplyDto.getApplyerName()
                        + ppmAccessCardApplyDto.getApplyerId() + ")，于"
                        + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")
                        + "，发起金融科技部外包人员门禁卡归还流程，请及时登录手机工作平台处理。";
                workflowAccessCardService.pushTaskCommon(ppmAccessCardApplyDto.getCurrentId(),"门禁卡归还申请监保部确认",
                        imgTxDsc,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"),"2");
                //日志记录
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,userIds,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, userIds);
            } else {
                // 第一次流程后只做流转更新
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                StringJoiner joinerOne = new StringJoiner(BusinessUtil.REGEX_COMMA);
                StringJoiner joinerTwo = new StringJoiner(BusinessUtil.REGEX_COMMA);
                //下一步处理人
                String [] userIds = commEmployeeService.queryUserIdByRole("JBB_AFFIRM").split(BusinessUtil.REGEX_COMMA);
                for (String str : userIds) {
                    joinerOne.add(str);
                    CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(str);
                    if(commEmployee != null){
                        joinerTwo.add(commEmployee.getEmpname());
                    }
                }
                // 流程变量
                param.setmPara(mPara);

                ppmAccessCardApplyDto.setState("1");
                ppmAccessCardApplyDto.setStateName("处理中");
                ppmAccessCardApplyDto.setCurrentId(joinerOne.toString());
                ppmAccessCardApplyDto.setCurrentName(joinerTwo.toString());

                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
                accessCardApplyInfoList.forEach(ai -> joiner.add(ai.getUserName()));
                bps.setProcessInstName(joiner + FLOW_TITLE);
                bps.setProcessInstId(ppmAccessCardApplyDto.getInstanceId());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);

                ppmAccessCardApplyDto.setModifiedBy(bps.getUserId());
                ppmAccessCardApplyDto.setModifiedTime(new Date());
                updatePpmAccessCardApply(ppmAccessCardApplyDto);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(ppmAccessCardApplyDto.getInstanceId());

                //推送待办
                String imgTxDsc = "金融科技部(" + ppmAccessCardApplyDto.getApplyerName()
                        + ppmAccessCardApplyDto.getApplyerId() + ")，于"
                        + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss")
                        + "发起金融科技部外包人员门禁卡归还流程，请及时登录手机工作平台处理。";
                workflowAccessCardService.pushTaskCommon(ppmAccessCardApplyDto.getCurrentId(),"门禁卡归还申请监保部确认",
                        imgTxDsc,bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"),"2");

                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,userIds,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, userIds);
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
    public String flowGmApproval(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        BpsWorkFlowDto bps = ppmAccessCardApplyDto.getBps();

//        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoList = ppmAccessCardApplyDto.getAccessCardApplyInfoList();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmAccessCardApplyDto.setModifiedBy(bps.getUserId());
            ppmAccessCardApplyDto.setModifiedTime(new Date());
            ppmAccessCardApplyDto.setState("0");
            ppmAccessCardApplyDto.setStateName("重新提交");
            ppmAccessCardApplyDto.setCurrentId(ppmAccessCardApplyDto.getApplyerId());
            ppmAccessCardApplyDto.setCurrentName(ppmAccessCardApplyDto.getApplyerName());
            boolean flag = false;
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                flag = true;
                //从字典内获取到下一个节点处理人
                EosDictEntry eosDictEntry =
                        eosDictEntryService.getTypeidForDetail("ACCESS_CARD_FLOW_CURRENT","monitorApprove");

                mPara.put("userId", eosDictEntry.getDictname());// 下一步审批人

                CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(eosDictEntry.getDictname());
                ppmAccessCardApplyDto.setState("2");
                ppmAccessCardApplyDto.setStateName("处理中");
                ppmAccessCardApplyDto.setCurrentId(commEmployee.getBadge());
                ppmAccessCardApplyDto.setCurrentName(commEmployee.getEmpname());
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("科技部负责人审批");
            bps.setRelaData(mPara);

            updatePpmAccessCardApply(ppmAccessCardApplyDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowAccessCardService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                //推送待办
                workflowAccessCardService.pushTaskCommon(ppmAccessCardApplyDto.getCurrentId(),"门禁卡领用申请监保部审批",
                        ppmAppWorkflowInfoDto.getPushContent(),
                        bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"),"1");
            }

            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String flowMonitorApproval(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        this.FLOW_ID = FLOW_ID_RV;
        this.FLOW_TITLE = FLOW_TITLE_RV;

        BpsWorkFlowDto bps = ppmAccessCardApplyDto.getBps();

//        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoList = ppmAccessCardApplyDto.getAccessCardApplyInfoList();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmAccessCardApplyDto.setModifiedBy(bps.getUserId());
            ppmAccessCardApplyDto.setModifiedTime(new Date());
            ppmAccessCardApplyDto.setState("0");
            ppmAccessCardApplyDto.setStateName("重新提交");
            ppmAccessCardApplyDto.setCurrentId(ppmAccessCardApplyDto.getApplyerId());
            ppmAccessCardApplyDto.setCurrentName(ppmAccessCardApplyDto.getApplyerName());
            // 通过状态
            StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
            StringJoiner joinerTwo = new StringJoiner(BusinessUtil.REGEX_COMMA);

            String[] userIds = null;
            boolean flag = false;
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {

                mPara.put("status", "pass");
                flag = true;
                userIds = commEmployeeService.queryUserIdByRole("JBB_AFFIRM").split(BusinessUtil.REGEX_COMMA);
                for (String str : userIds) {
                    joiner.add(str);
                    CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(str);
                    if(commEmployee != null){
                        joinerTwo.add(commEmployee.getEmpname());
                    }
                }

                ppmAccessCardApplyDto.setState("3");
                ppmAccessCardApplyDto.setStateName("处理中");
                ppmAccessCardApplyDto.setCurrentId(joiner.toString());
                ppmAccessCardApplyDto.setCurrentName(joinerTwo.toString());

                ppmAccessCardApplyDto.setFileNo(saveFlowCaseDocx(ppmAccessCardApplyDto)
                        + BusinessUtil.REGEX_COMMA
                        + saveFlowCaseXlsx(ppmAccessCardApplyDto));
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("监保部审批");
            bps.setRelaData(mPara);
            updatePpmAccessCardApply(ppmAccessCardApplyDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowAccessCardService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }else {
                ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForPushState("pushError",bps.getProcessInstId());
            }

            if (flag && ppmAppWorkflowInfoDto != null){
                //推送待办
                workflowAccessCardService.pushTaskCommon(ppmAccessCardApplyDto.getCurrentId(),"门禁卡领用申请监保部确认",
                        ppmAppWorkflowInfoDto.getPushContent(),
                        bps.getProcessInstId(),taskBean.getTaskList().get(0).get("taskId"),"1");
            }

            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,userIds,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, userIds);
        } catch (Exception e) {
            log.error("监保部审批审批失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String flowFinish(PpmAccessCardApplyDto ppmAccessCardApplyDto) {
        BpsWorkFlowDto bps = ppmAccessCardApplyDto.getBps();

        vaildCommitData(ppmAccessCardApplyDto);

        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmAccessCardApplyDto.setModifiedBy(bps.getUserId());
            ppmAccessCardApplyDto.setModifiedTime(new Date());
            ppmAccessCardApplyDto.setState("0");
            ppmAccessCardApplyDto.setStateName("重新提交");
            ppmAccessCardApplyDto.setCurrentId(ppmAccessCardApplyDto.getApplyerId());
            ppmAccessCardApplyDto.setCurrentName(ppmAccessCardApplyDto.getApplyerName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                ppmAccessCardApplyDto.setState("-1");
                ppmAccessCardApplyDto.setStateName("已结束");
                ppmAccessCardApplyDto.setCurrentId("");
                ppmAccessCardApplyDto.setCurrentName("");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("监保部确认");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());

            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                workflowAccessCardService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }

            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);

            updatePpmAccessCardApply(ppmAccessCardApplyDto);
            // 如果流程已完成成返回空
            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                saveAccessCardInfo(ppmAccessCardApplyDto,bps);
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("监保部确认审批失败！");
            throw new BizException(e);
        }
    }

    /**
     * 对提交的数据做校验
     * @param accessCardApplyDto 对象
     */
    private void vaildCommitData(PpmAccessCardApplyDto accessCardApplyDto){
        String accessCardType = accessCardApplyDto.getType();
        if(StringUtils.isEmpty(accessCardType)){
            throw new BizException(ApiCode.FAIL,"类型为空");
        }else {
            if("Receive".equals(accessCardType)){
                this.FLOW_ID = FLOW_ID_RV;
                this.FLOW_TITLE = FLOW_TITLE_RV;
            }else if("Return".equals(accessCardType)){
                this.FLOW_ID = FLOW_ID_RT;
                this.FLOW_TITLE = FLOW_TITLE_RT;
            }else {
                throw new BizException(ApiCode.FAIL,"类型不合规");
            }
        }
    }

    /**
     * 修改门禁卡信息
     * @param accessCardApplyDto 对象集合
     * @param bps 对象集合
     */
    private void saveAccessCardInfo(PpmAccessCardApplyDto accessCardApplyDto,BpsWorkFlowDto bps){
        List<PpmAccessCardApplyInfoDto> accessCardApplyInfoDtoList = accessCardApplyDto.getAccessCardApplyInfoList();
        if("Receive".equals(accessCardApplyDto.getType())){
            accessCardApplyInfoDtoList.forEach(dto ->{
                accessCardApplyInfoService.updatePpmAccessCardApplyInfo(dto);

                PpmAccessCardLedgerDto ppmAccessCardLedgerDto = PpmAccessCardLedgerDto.builder().build();
                ppmAccessCardLedgerDto.setId(dto.getBizObjectId());
                if ("Y".equals(dto.getIfReceiveCard())){
                    ppmAccessCardLedgerDto.setIfMakeCard("Y");
                }
                ppmAccessCardLedgerDto.setIfReturnCard("N");//未归还
                ppmAccessCardLedgerDto.setAccessCardNumber(dto.getAccessCardNumber());
                ppmAccessCardLedgerDto.setIfReceiveCard(dto.getIfReceiveCard());
                ppmAccessCardLedgerDto.setReceiveTime(dto.getReceiveTime());
                ppmAccessCardLedgerDto.setRemarkJbb(dto.getRemark());
                ppmAccessCardLedgerDto.setModifiedBy(bps.getUserId());
                ppmAccessCardLedgerDto.setModifiedTime(new Date());
                ppmAccessCardLedgerService.updatePpmAccessCardLedger(ppmAccessCardLedgerDto);
            });
        }else if ("Return".equals(accessCardApplyDto.getType())){
            accessCardApplyInfoDtoList.forEach(dto ->{
                accessCardApplyInfoService.updatePpmAccessCardApplyInfo(dto);

                PpmAccessCardLedgerDto ppmAccessCardLedgerDto = PpmAccessCardLedgerDto.builder().build();
                ppmAccessCardLedgerDto.setId(dto.getBizObjectId());
                ppmAccessCardLedgerDto.setIfReturnCard(dto.getIfReturnCard());//未归还
                ppmAccessCardLedgerDto.setAccessCardNumber(dto.getAccessCardNumber());
                ppmAccessCardLedgerDto.setReturnTime(dto.getReturnTime());
                ppmAccessCardLedgerDto.setRemarkJbb(dto.getRemark());
                ppmAccessCardLedgerDto.setModifiedBy(bps.getUserId());
                ppmAccessCardLedgerDto.setModifiedTime(new Date());
                ppmAccessCardLedgerService.updatePpmAccessCardLedger(ppmAccessCardLedgerDto);
            });
        }
        log.info("修改门禁卡信息完成。");
    }

    /**
     * 自动生成门禁权限申请表
     * @param accessCardApplyDto 对象
     * @return 文件编号
     */
    @Override
    public String saveFlowCaseDocx(PpmAccessCardApplyDto accessCardApplyDto){

        Map<String,Object> params = new HashMap<>();
        params.put("applyUserName",accessCardApplyDto.getApplyerName());
        List<PpmAccessCardApplyInfoDto> list = accessCardApplyDto.getAccessCardApplyInfoList();
        List<Map<String,Object>> mapList = new ArrayList<>();


        //整合字典
        List<EosDictEntry> eosDictEntryList =
                eosDictEntryService.getTypeidForList(new String[]{"BUSI_OUTSOURCE_GENFER","PPM_SYSTEM_FILE_NAME"});
        Map<String,String> dictMap = new HashMap<>();
        for (EosDictEntry eosDictEntry : eosDictEntryList){
            dictMap.put(eosDictEntry.getDictid(),eosDictEntry.getDictname());
        }

        int i = 1;
        for(PpmAccessCardApplyInfoDto applyInfoDto : list){
            Map<String,Object> map = new HashMap<>();
            map.put("index",i++);
            map.put("userName",applyInfoDto.getUserName());
            map.put("jobId",applyInfoDto.getVirtualMachineId());
            map.put("sex", dictMap.get(applyInfoDto.getSex()));
            Date applyTime = accessCardApplyDto.getCreatedTime();
            map.put("info","□长期（聘期内）；☑临时，时限： "
                    + DateUtil.dateToStr(applyTime,"yyyy年MM月dd日")
                    + BusinessUtil.REGEX_ROD
                    + DateUtil.dateToStr(DateUtil.intervalYear(applyTime,1),"yyyy年MM月dd日"));
            map.put("telephone",applyInfoDto.getTelephone());
            map.put("remark","");
            mapList.add(map);
        }
        params.put("mapList",mapList);

        String aliasName = DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss") + BusinessUtil.SUFFIX_DOCX;
        // 获取上传路径
        PpmDocumentCatalog catalog = ppmDocumentCatalogService.queryDetail("CATALOG2023071300001");
        // 添加记录
        Date date = new Date();
        PpmDocument document = new PpmDocument();
        document.setCatalogno("CATALOG2023071300001");
        document.setObjectno(null);
        document.setObjecttype("AI");
        document.setStockpath(catalog.getStockpath());
        document.setDocname(dictMap.get("ACCESSCARD01") + BusinessUtil.SUFFIX_DOCX);
        document.setTag2(aliasName);  // 真实上传文件名称
        document.setCreateorid("sysadmin");
        document.setCreators("sysadmin");
        document.setCreatetime(date);
        document.setLastmodtime(date);
        String docNo = ppmDocumentService.getDocmentnoSeq();
        document.setDocumentno(docNo);
        document.setIparea("");
        document.setLastmoduser(null);
        ppmDocumentService.addDocment(document);

        //上传路径
        String uploadPathAll = uploadPath + "ppmtest" + catalog.getStockpath() + aliasName ;
        File newFile = new File(uploadPathAll);
        try {
            XWPFDocument xwpfDocument = WordExportUtil.exportWord07(arFileDocx,params);

            FileOutputStream fos = new FileOutputStream(newFile);
            xwpfDocument.write(fos);
            xwpfDocument.close();
        }catch (Exception e){
            throw new BizException(ApiCode.FAIL, "门禁权限申请表附件生成异常");
        }
        return docNo;
    }

    /**
     * 自动生成外包单位人员信息登记表
     * @param accessCardApplyDto 对象
     * @return 文件编号
     */
    @Override
    public String saveFlowCaseXlsx(PpmAccessCardApplyDto accessCardApplyDto){

        List<PpmAccessCardApplyInfoDto> list = accessCardApplyDto.getAccessCardApplyInfoList();
        List<Map<String,Object>> mapList = new ArrayList<>();

        //整合字典
        List<EosDictEntry> eosDictEntryList =
                eosDictEntryService.getTypeidForList("PPM_SYSTEM_FILE_NAME");
        Map<String,String> dictMap = new HashMap<>();
        for (EosDictEntry eosDictEntry : eosDictEntryList){
            dictMap.put(eosDictEntry.getDictid(),eosDictEntry.getDictname());
        }

        int i = 1;
        for(PpmAccessCardApplyInfoDto applyInfoDto : list){
            Map<String,Object> map = new HashMap<>();
            map.put("index",i++);
            map.put("userName",applyInfoDto.getUserName());
            map.put("idCardNo",applyInfoDto.getIdCardNo());
            map.put("telephone",applyInfoDto.getTelephone());
            map.put("outsourceName",applyInfoDto.getOutsourceName());
            Date applyTime = accessCardApplyDto.getCreatedTime();
            map.put("betweenTime",DateUtil.dateToStr(applyTime,"yyyy年MM月dd日")
                    + BusinessUtil.REGEX_ROD
                    + DateUtil.dateToStr(DateUtil.intervalYear(applyTime,1),"yyyy年MM月dd日"));
            map.put("deptName",applyInfoDto.getDeptName());
            map.put("ifCard","是");
            map.put("ifCxCard","是");
            mapList.add(map);
        }

        String aliasName = DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss") + BusinessUtil.SUFFIX_XLSX;
        // 获取上传路径
        PpmDocumentCatalog catalog = ppmDocumentCatalogService.queryDetail("CATALOG2023071300001");

        // 添加记录
        Date date = new Date();
        PpmDocument document = new PpmDocument();
        document.setCatalogno("CATALOG2023071300001");
        document.setObjectno(null);
        document.setObjecttype("AI");
        document.setStockpath(catalog.getStockpath());
        document.setDocname(dictMap.get("ACCESSCARD02") + BusinessUtil.SUFFIX_XLSX);
        document.setTag2(aliasName);  // 真实上传文件名称
        document.setCreateorid("sysadmin");
        document.setCreators("sysadmin");
        document.setCreatetime(date);
        document.setLastmodtime(date);
        String docNo = ppmDocumentService.getDocmentnoSeq();
        document.setDocumentno(docNo);
        document.setIparea("");
        document.setLastmoduser(null);
        ppmDocumentService.addDocment(document);
        //上传路径
        String uploadPathAll = uploadPath + "ppmtest" + catalog.getStockpath() + aliasName ;
        ExcelWriter excelWriter = EasyExcel.write(uploadPathAll).withTemplate(arFileXlsx).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig =
                FillConfig.builder().direction(WriteDirectionEnum.VERTICAL).forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(new FillWrapper("data",mapList),fillConfig,writeSheet);
        excelWriter.finish();

        return docNo;
    }

    @Override
    public List<PpmAccessCardApplyInfoExportDto> exportExcel(PpmAccessCardApplyDto accessCardApplyDto, CommOperatorDoLoginDto user) {
        return JsonUtil.jsonToList(accessCardApplyDto.getAccessCardApplyInfoList(),PpmAccessCardApplyInfoExportDto.class);
    }

}
