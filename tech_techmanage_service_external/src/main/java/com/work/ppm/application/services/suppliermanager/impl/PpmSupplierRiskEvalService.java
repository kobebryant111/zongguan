package com.work.ppm.application.services.suppliermanager.impl;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.google.common.collect.Lists;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRiskEvalDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.suppliermanager.PpmSupplierRiskEval;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.interfaces.IPpmSupplierRiskEvalCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRelationService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRiskEvalService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRosterInfoService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BusinessUtil;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StreamUtils;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
  *  风险评估表 接口实现类
  */
@Slf4j
@Service("ppmSupplierRiskEvalService")
public class PpmSupplierRiskEvalService extends BaseDaoSupper
        implements IPpmSupplierRiskEvalService, IPpmSupplierRiskEvalCalculateJobService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERRISKEVAL = "com.work.ppm.application.model.suppliermanager.PpmSupplierRiskEval";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    // 流程定义ID
    private static final String FLOW_ID = "fxpgsq";
    //流程名称
    private static final String FLOW_TITLE = "风险评估申请";

    @Value("${file.assess.pg01-name}")
    private String pgFileName;

    //工作流服务
    @Autowired
    private WorkFlowHttpClientService workFlowHttpClientService;

    @Autowired
    private IPpmSupplierRelationService ppmSupplierRelationService;
    //员工
    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Autowired
    private IEosDictEntryService eosDictEntryService;

    @Autowired
    private IPpmSupplierRosterInfoService ppmSupplierRosterInfoService;

    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Override
    public void queryPager(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto, Pager pager, CommOperatorDoLoginDto user) {

        handleAuthCode(ppmSupplierRiskEvalDto,user);
        Map<String,Object> map = JsonUtil.objectTOMap(ppmSupplierRiskEvalDto,PpmSupplierRiskEvalDto.class);
        myBatisDao.selectPager(PPMSUPPLIERRISKEVAL, map,pager,"queryBaseInfoList");

        List<PpmSupplierRiskEvalDto> ppmSupplierRiskEvalDtos =
                JsonUtil.jsonToList(pager.getList(),PpmSupplierRiskEvalDto.class);
        ppmSupplierRiskEvalDtos.forEach(riskEvalDto -> {
            riskEvalDto.setAffectBusiTypeArray(
                    BusinessUtil.getArrayString(riskEvalDto.getAffectBusiType(),BusinessUtil.REGEX_LINE_UN));
            riskEvalDto.setDeptName(BusinessUtil.getDeptNameValue(riskEvalDto.getDeptName(),1));
        });
        pager.setList(ppmSupplierRiskEvalDtos);
    }

    @Override
    public int addPpmSupplierRiskEval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        PpmSupplierRiskEval ppmSupplierRiskEval =
                JsonUtil.jsonToClass(ppmSupplierRiskEvalDto, PpmSupplierRiskEval.class);
        // 如果需要请set 主键id
        // ppmSupplierRiskEval.setId(getNextSequence(PpmSupplierRiskEval.class));
        ppmSupplierRiskEval.setCreatedBy(ppmSupplierRiskEval.getPmId());
        ppmSupplierRiskEval.setCreatedTime(new Date());
        myBatisDao.executionPojo(PPMSUPPLIERRISKEVAL, ppmSupplierRiskEval, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSupplierRiskEvalDto queryDetail(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,String uniqueName) {
        PpmSupplierRiskEval ppmSupplierRiskEval =
                JsonUtil.jsonToClass(ppmSupplierRiskEvalDto, PpmSupplierRiskEval.class);
        PpmSupplierRiskEval data = myBatisDao.selectClass(PPMSUPPLIERRISKEVAL, ppmSupplierRiskEval,uniqueName);
        return JsonUtil.jsonToClass(data, PpmSupplierRiskEvalDto.class);
    }

    @Override
    public List<PpmSupplierRiskEvalDto> queryDetailList(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        PpmSupplierRiskEval ppmSupplierRiskEval =
                JsonUtil.jsonToClass(ppmSupplierRiskEvalDto, PpmSupplierRiskEval.class);
        List<PpmSupplierRiskEval> data = myBatisDao.selectListT(PPMSUPPLIERRISKEVAL, ppmSupplierRiskEval,"getList");
        return JsonUtil.jsonToList(data,PpmSupplierRiskEvalDto.class);
    }

    @Override
    public int updatePpmSupplierRiskEval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        PpmSupplierRiskEval ppmSupplierRiskEval =
                JsonUtil.jsonToClass(ppmSupplierRiskEvalDto, PpmSupplierRiskEval.class);
        return myBatisDao.executionPojo(PPMSUPPLIERRISKEVAL, ppmSupplierRiskEval, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmSupplierRiskEval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        PpmSupplierRiskEval supplierRiskEval = JsonUtil.jsonToClass(ppmSupplierRiskEvalDto,PpmSupplierRiskEval.class);
        int count = myBatisDao.executionPojo(
                PPMSUPPLIERRISKEVAL, supplierRiskEval, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");

        if (StrUtils.isNotBlank(supplierRiskEval.getInstanceid())) {
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(supplierRiskEval.getInstanceid());
            CamundaUtils.terminateProcess(supplierRiskEval.getInstanceid());
        }
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String managerCreated(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        BpsWorkFlowDto bps = ppmSupplierRiskEvalDto.getBps();

        //校验
        vaildCommitData(ppmSupplierRiskEvalDto);

        if (ppmSupplierRiskEvalDto.getId() != null ) {
            this.updatePpmSupplierRiskEval(ppmSupplierRiskEvalDto);
            return null;
        }

        try {
            ProcessBean processBean;
            // 添加供应商考核记录
            ppmSupplierRiskEvalDto.setId(getNextSequence(PpmSupplierRiskEval.class));
            ppmSupplierRiskEvalDto.setFlowId(FLOW_ID);
            ppmSupplierRiskEvalDto.setState("0");
            ppmSupplierRiskEvalDto.setStateName("未发起");
            ppmSupplierRiskEvalDto.setCurrentId(ppmSupplierRiskEvalDto.getPmId());
            ppmSupplierRiskEvalDto.setCurrentName(ppmSupplierRiskEvalDto.getPmName());
            // ■ □
            //插入供应商考核总分集合
            if(ppmSupplierRiskEvalDto.getAssessScoreMap() != null){
                ppmSupplierRiskEvalDto.setAssessScores(
                        BusinessUtil.mapRegexString(
                                ppmSupplierRelationService.queryDetailKey(ppmSupplierRiskEvalDto.getAssessType()),
                                ppmSupplierRiskEvalDto.getAssessScoreMap(),
                                new String[]{BusinessUtil.REGEX_COLON,BusinessUtil.REGEX_LINE}));
            }
            //插入影响业务内容
            if(ppmSupplierRiskEvalDto.getAffectBusiTypeArray() != null){
                ppmSupplierRiskEvalDto.setAffectBusiType(BusinessUtil.getStringArray(
                        ppmSupplierRiskEvalDto.getAffectBusiTypeArray(),BusinessUtil.REGEX_LINE));
            }
//            insertPpmEarlyCourseApply(ppmEarlyCourseApplyDto);

            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(ppmSupplierRiskEvalDto.getId()));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);

            ppmSupplierRiskEvalDto.setInstanceid(processBean.getProcessInstanceId());
            //插入评估结果集合
            addPpmSupplierRiskEval(ppmSupplierRiskEvalDto);

            // 日志处理
            bps.setProjectNo(String.valueOf(ppmSupplierRiskEvalDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc(FLOW_TITLE);
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());

            bps.setProcessInstName("【" + ppmSupplierRiskEvalDto.getSupplierName() + "】" +
                    ppmSupplierRiskEvalDto.getAssessYear() + "年" + FLOW_TITLE);
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return "pass";
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String managerInit(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        BpsWorkFlowDto bps = ppmSupplierRiskEvalDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        //校验
        vaildCommitData(ppmSupplierRiskEvalDto);

        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(ppmSupplierRiskEvalDto.getInstanceid())) {
                bps.setProcessDefName(FLOW_ID);

                // 如果需要请set 主键id
                ppmSupplierRiskEvalDto.setId(getNextSequence(PpmSupplierRiskEval.class));

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(ppmSupplierRiskEvalDto.getId()));
                Map mPara = new HashMap();
                //获取下一步处理人 也就是对应的二级部经理
                String currentId = "";
                String currentName = "";

                if (StringUtil.isNotBlank(ppmSupplierRiskEvalDto.getDeptId())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(ppmSupplierRiskEvalDto.getDeptId());
                    // 二级部主持经理作为下一步处理人
                    currentId = employeeDto.getBadge();
                    currentName = employeeDto.getEmpname();
                    mPara.put("userId", currentId);
                    // 流程变量
                    processInfo.setmPara(mPara);
                }
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);

                //供应商考核流程申请
                ppmSupplierRiskEvalDto.setInstanceid(processBean.getProcessInstanceId());
                ppmSupplierRiskEvalDto.setState("1");
                ppmSupplierRiskEvalDto.setStateName("处理中");
                ppmSupplierRiskEvalDto.setCurrentId(currentId);
                ppmSupplierRiskEvalDto.setCurrentName(currentName);
                //评估结果集集合
                ppmSupplierRiskEvalDto.setAssessScores(
                        BusinessUtil.mapRegexString(
                                ppmSupplierRelationService.queryDetailKey(ppmSupplierRiskEvalDto.getAssessType()),
                                ppmSupplierRiskEvalDto.getAssessScoreMap(),
                                new String[]{BusinessUtil.REGEX_COLON,BusinessUtil.REGEX_LINE}));
                ppmSupplierRiskEvalDto.setAffectBusiType(BusinessUtil.getStringArray(
                        ppmSupplierRiskEvalDto.getAffectBusiTypeArray(),BusinessUtil.REGEX_LINE));

                addPpmSupplierRiskEval(ppmSupplierRiskEvalDto);

                // 日志处理
                bps.setProjectNo(String.valueOf(ppmSupplierRiskEvalDto.getId()));
                bps.setConclusion("01");
                bps.setApplydesc(FLOW_TITLE + "发起");
                bps.setRelaData(mPara);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setProcessInstName("【" + ppmSupplierRiskEvalDto.getSupplierName() + "】" +
                        ppmSupplierRiskEvalDto.getAssessYear() + "年" + FLOW_TITLE);
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
                String nowUserId = bps.getUserId();
                param.setUserId(nowUserId);// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(ppmSupplierRiskEvalDto.getDeptId())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(ppmSupplierRiskEvalDto.getDeptId());
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
                bps.setProcessInstId(ppmSupplierRiskEvalDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);

                ppmSupplierRiskEvalDto.setState("1");
                ppmSupplierRiskEvalDto.setStateName("处理中");
                ppmSupplierRiskEvalDto.setCurrentId(currentId);
                ppmSupplierRiskEvalDto.setCurrentName(currentName);
                ppmSupplierRiskEvalDto.setModifiedBy(nowUserId);
                ppmSupplierRiskEvalDto.setModifiedTime(new Date());
                //插入供应商考核总分集合
                ppmSupplierRiskEvalDto.setAssessScores(
                        BusinessUtil.mapRegexString(
                                ppmSupplierRelationService.queryDetailKey(ppmSupplierRiskEvalDto.getAssessType()),
                                ppmSupplierRiskEvalDto.getAssessScoreMap(),
                                new String[]{BusinessUtil.REGEX_COLON,BusinessUtil.REGEX_LINE}));
                ppmSupplierRiskEvalDto.setAffectBusiType(BusinessUtil.getStringArray(
                        ppmSupplierRiskEvalDto.getAffectBusiTypeArray(),BusinessUtil.REGEX_LINE));

                updatePpmSupplierRiskEval(ppmSupplierRiskEvalDto);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(ppmSupplierRiskEvalDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            if(processBean != null){
                CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            }

            e.printStackTrace();
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(value = "defaultTx", rollbackFor = Exception.class)
    public String departmentHeadApproval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        BpsWorkFlowDto bps = ppmSupplierRiskEvalDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            String nowUserId = bps.getUserId();
            param.setUserId(nowUserId);// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmSupplierRiskEvalDto.setState("0");
            ppmSupplierRiskEvalDto.setStateName("重新提交");
            ppmSupplierRiskEvalDto.setCurrentId(ppmSupplierRiskEvalDto.getPmId());
            ppmSupplierRiskEvalDto.setCurrentName(ppmSupplierRiskEvalDto.getPmName());
            List<String> msgIds = Lists.newArrayList();
            List<String> msgNames;
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

                StringJoiner joinerName = new StringJoiner(",");
                msgNames.forEach(joinerName::add);
                //记录日志
                ppmSupplierRiskEvalDto.setState("2");
                ppmSupplierRiskEvalDto.setStateName("处理中");
                ppmSupplierRiskEvalDto.setCurrentId(joiner.toString());
                ppmSupplierRiskEvalDto.setCurrentName(joinerName.toString());

            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("二级部经理处理");
            bps.setRelaData(mPara);

            ppmSupplierRiskEvalDto.setModifiedBy(nowUserId);
            ppmSupplierRiskEvalDto.setModifiedTime(new Date());
            updatePpmSupplierRiskEval(ppmSupplierRiskEvalDto);

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
    public String generalManagerApproval(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto) {
        BpsWorkFlowDto bps = ppmSupplierRiskEvalDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmSupplierRiskEvalDto.setState("0");
            ppmSupplierRiskEvalDto.setStateName("重新提交");
            ppmSupplierRiskEvalDto.setCurrentId(ppmSupplierRiskEvalDto.getPmId());
            ppmSupplierRiskEvalDto.setCurrentName(ppmSupplierRiskEvalDto.getPmName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                ppmSupplierRiskEvalDto.setState("-1");
                ppmSupplierRiskEvalDto.setStateName("已结束");
                ppmSupplierRiskEvalDto.setCurrentId("");
                ppmSupplierRiskEvalDto.setCurrentName("");
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("一级部总经理室审批");
            bps.setRelaData(mPara);

            //记录日志
            ppmSupplierRiskEvalDto.setModifiedBy(bps.getUserId());
            ppmSupplierRiskEvalDto.setModifiedTime(new Date());
            updatePpmSupplierRiskEval(ppmSupplierRiskEvalDto);

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
     * @param ppmSupplierRiskEvalDto 对象
     */
    private void vaildCommitData(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto){

        //需要判断是否已提交过年份的数据
        PpmSupplierRiskEvalDto supplierRiskEvalDto = new PpmSupplierRiskEvalDto();
        supplierRiskEvalDto.setAssessType(ppmSupplierRiskEvalDto.getAssessType());
        supplierRiskEvalDto.setPmId(ppmSupplierRiskEvalDto.getPmId());
        supplierRiskEvalDto.setAssessYear(ppmSupplierRiskEvalDto.getAssessYear());
        supplierRiskEvalDto.setSupplierId(ppmSupplierRiskEvalDto.getSupplierId());
        supplierRiskEvalDto.setDeptId(ppmSupplierRiskEvalDto.getDeptId());
        List<PpmSupplierRiskEvalDto> supplierAssessDtos = queryDetailList(supplierRiskEvalDto);
        if(supplierAssessDtos != null && !supplierAssessDtos.isEmpty() &&
                !Objects.equals(supplierAssessDtos.get(0).getInstanceid(),ppmSupplierRiskEvalDto.getInstanceid())){
            throw new BizException(ApiCode.FAIL,"此供应商该年份风险评估已存在！");
        }

    }

    @Override
    public void exportPdf(PpmSupplierRiskEvalDto ppmSupplierRiskEvalDto,
                          HttpServletResponse response) throws Exception{

        PpmSupplierRiskEvalDto riskEvalDto = queryDetail(ppmSupplierRiskEvalDto,"queryBaseInfo");
        if(riskEvalDto == null){
            return;
        }

        File file = new File(pgFileName);
        Map<String,Object> assessScoreMap = BusinessUtil.stringRegexMap(
                riskEvalDto.getAssessScores(),new String[]{BusinessUtil.REGEX_LINE_UN,BusinessUtil.REGEX_COLON});
        riskEvalDto.setAssessScoreMap(assessScoreMap);

        Map<String,Object> params = new HashMap<>();

        params.put("assessYear",riskEvalDto.getAssessYear());
        params.put("busiName",riskEvalDto.getBusiName());

        //外包业务类型
        String busiType = riskEvalDto.getBusiType();
        List<EosDictEntry> eosDictEntryDtos =
                eosDictEntryService.getTypeidForList("BUSI_TYPE_DICT");
        StringBuffer stringBuffer = new StringBuffer();
        eosDictEntryDtos.forEach(eosDictEntryDto ->{
            if(eosDictEntryDto.getDictid().equals(busiType)){
                stringBuffer.append(BusinessUtil.REGEX_BLACK_GRID)
                        .append(eosDictEntryDto.getDictname()).append(" ");
            }else {
                stringBuffer.append(BusinessUtil.REGEX_WHITE_GRID)
                        .append(eosDictEntryDto.getDictname()).append(" ");
            }
        });
        params.put("busiType",stringBuffer.toString());

        params.put("supplierName",riskEvalDto.getSupplierName());
        params.put("impInfo",riskEvalDto.getImpInfo());
        //实施方式
        if("Y".equals(riskEvalDto.getExecutionMode())){
            params.put("executionMode_0",BusinessUtil.REGEX_BLACK_GRID);
            params.put("executionMode_1",BusinessUtil.REGEX_WHITE_GRID);
        }else {
            params.put("executionMode_0",BusinessUtil.REGEX_WHITE_GRID);
            params.put("executionMode_1",BusinessUtil.REGEX_BLACK_GRID);
        }

        //外包业务类型
        String affectBusiType = riskEvalDto.getAffectBusiType();
        List<EosDictEntry> affectBusiTypeDicts =
                eosDictEntryService.getTypeidForList("AFFECT_BUSI_TYPE_DICT");
        StringBuffer stringBuffer1 = new StringBuffer();
        affectBusiTypeDicts.forEach(affectBusiTypeDict ->{
            if(affectBusiType.contains(affectBusiTypeDict.getDictid())){
                stringBuffer1.append(BusinessUtil.REGEX_BLACK_GRID)
                        .append(affectBusiTypeDict.getDictname()).append(" ");
            }else {
                stringBuffer1.append(BusinessUtil.REGEX_WHITE_GRID)
                        .append(affectBusiTypeDict.getDictname()).append(" ");
            }
        });
        params.put("affectBusiType",stringBuffer1.toString());
        params.put("deptName",riskEvalDto.getDeptName());

        Set<String> keySet = assessScoreMap.keySet();

        keySet.forEach(str ->{
            if("Y".equals(assessScoreMap.get(str))){
                params.put(str + "_result_0",BusinessUtil.REGEX_BLACK_GRID);
                params.put(str + "_result_1",BusinessUtil.REGEX_WHITE_GRID);
            }else {
                params.put(str + "_result_0",BusinessUtil.REGEX_WHITE_GRID);
                params.put(str + "_result_1",BusinessUtil.REGEX_BLACK_GRID);
            }
        });

        params.put("assessResult",riskEvalDto.getAssessResult());
        params.put("pmName",riskEvalDto.getPmName());
        params.put("leaderPmName",riskEvalDto.getLeaderPmName());

        XWPFDocument xwpfDocument = WordExportUtil.exportWord07(file.getPath(),params);
        PdfOptions pdfOptions = PdfOptions.create();

        ServletOutputStream outputStream = response.getOutputStream();
        PdfConverter.getInstance().convert(xwpfDocument,outputStream,pdfOptions);

//        System.out.println(file.getPath());
//        String filename = "永赢租赁外包服务考核评分表.docx";
//        ServletOutputStream outputStream = response.getOutputStream();
//        response.setHeader("Content-Disposition","attachment;filename=" + new String(filename.getBytes(),"UTF-8"));
//        response.setContentType("appliction/vnd.openxmlformats-officedocument.wordprocessingml.document");
//        //输出word版本
//        xwpfDocument.write(outputStream);
    }

    @Override
    public void supplierRiskEvalFlow() {
        //先筛选合作满一年的供应商,然后筛对应的项目经理，然后依次发起评估任务
        List<PpmSupplierRosterInfoDto> list =
                ppmSupplierRosterInfoService.getFinishOneYears(new PpmSupplierRosterInfoDto());
        list.forEach(ppmSupplierRosterInfoDto -> {
            PpmOutsourcingBasicDto ppmOutsourcingBasicInfo = new PpmOutsourcingBasicDto();
            ppmOutsourcingBasicInfo.setDeptId("'"+ppmSupplierRosterInfoDto.getDeptId()+"'");
            ppmOutsourcingBasicInfo.setOutsourceNo(ppmSupplierRosterInfoDto.getSupplierCode());
            ppmOutsourcingBasicInfo.setState("0");
            //是否要找离职时间
            List<PpmOutsourcingBasicDto> basicDtos = ppmOutsourcingBasicService.queryOutList(ppmOutsourcingBasicInfo);
            basicDtos = basicDtos.stream().filter(StreamUtils.distinctByKey(PpmOutsourcingBasicDto::getPersonInCharge))
                    .collect(Collectors.toList());
            basicDtos.forEach(dto ->{
                PpmSupplierRiskEvalDto supplierRiskEvalDto = new PpmSupplierRiskEvalDto();
                supplierRiskEvalDto.setAssessType("PG01");
                supplierRiskEvalDto.setAssessYear((DateUtil.getYear()-1)+"");
                supplierRiskEvalDto.setSupplierId(ppmSupplierRosterInfoDto.getSupplierCode());
                supplierRiskEvalDto.setSupplierName(ppmSupplierRosterInfoDto.getSupplierName());
                supplierRiskEvalDto.setLeaderPmId(
                        commEmployeeService.getTwoDeptWorkManager(ppmSupplierRosterInfoDto.getDeptId()).getUserid());
                supplierRiskEvalDto.setPmId(dto.getPersonInCharge());
                supplierRiskEvalDto.setDeptId(ppmSupplierRosterInfoDto.getDeptId());
                BpsWorkFlowDto bps = new BpsWorkFlowDto();
                bps.setUserId(dto.getPersonInCharge());
                supplierRiskEvalDto.setBps(bps);

                try {
                    managerCreated(supplierRiskEvalDto);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });




    }

    /**
     * 处理权限
     * @param supplierRiskEvalDto supplierRiskEvalDto
     * @param user user
     */
    private void handleAuthCode(PpmSupplierRiskEvalDto supplierRiskEvalDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            supplierRiskEvalDto.setDeptIdVal(user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            supplierRiskEvalDto.setPmIdVal(user.getUserid());
            //系统管理员和一级部经理不做限制
        }
    }
}
