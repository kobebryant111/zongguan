package com.work.ppm.application.services.suppliermanager.impl;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierAssessGradeDto;
import com.work.ppm.application.dto.suppliermanager.PpmSupplierRosterInfoDto;
import com.work.ppm.application.dto.suppliermanager.PpmSuppliersSyncDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.PpmProjectInfoDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.suppliermanager.PpmSupplierAssess;
import com.work.ppm.application.model.suppliermanager.PpmSupplierAssessGrade;
import com.work.ppm.application.model.suppliermanager.ext.PpmSupplierAssessExt;
import com.work.ppm.application.model.systemmanager.PpmProjectInfo;
import com.work.ppm.application.quartz.interfaces.IPpmSupplierCalculateJobService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.othermanager.IPpmOutsourcingInfoService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierAssessGradeService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierAssessService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRelationService;
import com.work.ppm.application.services.suppliermanager.IPpmSupplierRosterInfoService;
import com.work.ppm.application.services.suppliermanager.IPpmSuppliersSyncService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IPpmProjectInfoService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.sdk.OpenSdk;
import com.work.ppm.commons.utils.BusinessUtil;
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
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
  *  供应商考核信息表 接口实现类
  */
@Slf4j
@Service("ppmSupplierAssessService")
public class PpmSupplierAssessService extends BaseDaoSupper
        implements IPpmSupplierAssessService, IPpmSupplierCalculateJobService {
    /**
      * 实体类路径
      */
    private static String PPMSUPPLIERASSESS = "com.work.ppm.application.model.suppliermanager.PpmSupplierAssess";

    private String FLOW_ID ;
    private String FLOW_TITLE;
    // 流程定义ID
    private static final String FLOW_ID_RL = "gyskhrl";
    //流程名称
    private static final String FLOW_TITLE_RL = "供应商考核人力类";
    // 流程定义ID
    private static final String FLOW_ID_XM = "gyskhxm";
    //流程名称
    private static final String FLOW_TITLE_XM = "供应商考核项目类";

    //员工
    @Autowired
    private ICommEmployeeService commEmployeeService;

    //工作流服务
    @Autowired
    private WorkFlowHttpClientService workFlowHttpClientService;
    /**
     * 外包人员信息服务
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 外包人员信息服务
     */
    @Autowired
    private IPpmSupplierRelationService ppmSupplierRelationService;

    @Autowired
    private IPpmSuppliersSyncService ppmSuppliersSyncService;

    @Autowired
    private IPpmProjectInfoService ppmProjectInfoService;

    @Autowired
    private IPpmOutsourcingInfoService ppmOutsourcingInfoService;

    @Autowired
    private IPpmSupplierRosterInfoService ppmSupplierRosterInfoService;

    @Autowired
    private IPpmSupplierAssessGradeService supplierAssessGradeService;

    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Value("${kfBank.productId}")
    private String productId;

    @Value("${kfBank.cgplat-serverId}")
    private String serverId;

    @Value("${file.assess.rl01-name}")
    private String rlFileName;

    @Value("${file.assess.xm01-name}")
    private String xmFileName;

    @Override
    public void queryPager(PpmSupplierAssessDto ppmSupplierAssessDto, Pager pager,CommOperatorDoLoginDto user) {

        if(StringUtils.isNotEmpty(ppmSupplierAssessDto.getDeptId())){
            ppmSupplierAssessDto.setDeptId(ppmSupplierAssessDto.getDeptId().replace("dep",""));
        }

        handleAuthCode(ppmSupplierAssessDto,user);

        Map<String,Object> map = JsonUtil.objectTOMap(ppmSupplierAssessDto,PpmSupplierAssessDto.class);

        myBatisDao.selectPager(PPMSUPPLIERASSESS, map,pager,"queryBaseInfoList");
        List<PpmSupplierAssessDto> list = JsonUtil.jsonToList(pager.getList(),PpmSupplierAssessDto.class);
        list.forEach(obj ->{
            Map<String,Object> relation = BusinessUtil.stringRegexMap(obj.getAssessScores(),
                            new String[]{BusinessUtil.REGEX_LINE,BusinessUtil.REGEX_COLON});
            obj.setAssessScoreMap(relation);
            obj.setDeptName(BusinessUtil.getDeptNameValue(obj.getDeptName(),1));
            if(obj.getAssessType().contains("XM")){
                obj.setPmName(obj.getPmName() + BusinessUtil.REGEX_ROD + obj.getPmTypeName());
            }
        });

        pager.setList(list);
    }

    @Override
    public int addPpmSupplierAssess(PpmSupplierAssessDto ppmSupplierAssessDto) {
        PpmSupplierAssess ppmSupplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto, PpmSupplierAssess.class);
        // 如果需要请set 主键id
        // ppmSupplierAssess.setId(getNextSequence(PpmSupplierAssess.class));
        myBatisDao.executionPojo(PPMSUPPLIERASSESS, ppmSupplierAssess, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmSupplierAssessDto queryDetail(PpmSupplierAssessDto ppmSupplierAssessDto,String uniqueName) {
        PpmSupplierAssessExt ppmSupplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto, PpmSupplierAssessExt.class);
        PpmSupplierAssessExt data = myBatisDao.selectClass(PPMSUPPLIERASSESS, ppmSupplierAssess,uniqueName);
        return  JsonUtil.jsonToClass(data, PpmSupplierAssessDto.class);
    }

    @Override
    public List<PpmSupplierAssessDto> queryDetailList(PpmSupplierAssessDto ppmSupplierAssessDto) {
        PpmSupplierAssess ppmSupplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto, PpmSupplierAssess.class);
        List<PpmSupplierAssess> data = myBatisDao.selectListT(PPMSUPPLIERASSESS, ppmSupplierAssess,"getList");
        return JsonUtil.jsonToList(data,PpmSupplierAssessDto.class);
    }

    @Override
    public List<PpmSupplierAssessDto> queryBaseList(PpmSupplierAssessDto ppmSupplierAssessDto) {
        PpmSupplierAssessExt ppmSupplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto, PpmSupplierAssessExt.class);
        List<PpmSupplierAssessExt> data =
                myBatisDao.selectListT(PPMSUPPLIERASSESS, ppmSupplierAssess,"queryBaseInfoList");
        return JsonUtil.jsonToList(data,PpmSupplierAssessDto.class);
    }

    @Override
    public List<PpmSupplierAssessDto> countSupplierPmRL(PpmSupplierAssessDto ppmSupplierAssessDto) {
        PpmSupplierAssessExt ppmSupplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto, PpmSupplierAssessExt.class);
        List<PpmSupplierAssessExt> data =
                myBatisDao.selectListT(PPMSUPPLIERASSESS, ppmSupplierAssess,"countSupplierPmRL");
        return JsonUtil.jsonToList(data,PpmSupplierAssessDto.class);
    }

    @Override
    public int updatePpmSupplierAssess(PpmSupplierAssessDto ppmSupplierAssessDto) {
        PpmSupplierAssess ppmSupplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto, PpmSupplierAssess.class);
        return myBatisDao.executionPojo(PPMSUPPLIERASSESS, ppmSupplierAssess, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmSupplierAssess(PpmSupplierAssessDto ppmSupplierAssessDto) {
        PpmSupplierAssess supplierAssess = JsonUtil.jsonToClass(ppmSupplierAssessDto,PpmSupplierAssess.class);
        int count = myBatisDao.executionPojo(
                PPMSUPPLIERASSESS, supplierAssess, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");

        if (StrUtils.isNotBlank(supplierAssess.getInstanceid())) {
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(supplierAssess.getInstanceid());
            CamundaUtils.terminateProcess(supplierAssess.getInstanceid());
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String managerCreated(PpmSupplierAssessDto ppmSupplierAssessDto) {
        BpsWorkFlowDto bps = ppmSupplierAssessDto.getBps();

        //校验
        vaildCommitData(ppmSupplierAssessDto);

        if (ppmSupplierAssessDto.getId() != null ) {
            this.updatePpmSupplierAssess(ppmSupplierAssessDto);
            return null;
        }

        try {
            ProcessBean processBean;
            // 添加供应商考核记录
            Long key = super.getNextSequence(PpmSupplierAssess.class);
            ppmSupplierAssessDto.setId(key);
            ppmSupplierAssessDto.setFlowId(FLOW_ID);
            ppmSupplierAssessDto.setCreatedBy(bps.getUserId());
            ppmSupplierAssessDto.setCreatedTime(new Date());
            ppmSupplierAssessDto.setState("0");
            ppmSupplierAssessDto.setStateName("未发起");
            ppmSupplierAssessDto.setCurrentId(ppmSupplierAssessDto.getPmId());
            ppmSupplierAssessDto.setCurrentName(ppmSupplierAssessDto.getPmName());

            //插入供应商考核总分集合
            if(ppmSupplierAssessDto.getAssessScoreMap() != null){
                ppmSupplierAssessDto.setAssessScores(
                        BusinessUtil.mapRegexString(
                                ppmSupplierRelationService.queryDetailKey(ppmSupplierAssessDto.getAssessType()),
                                ppmSupplierAssessDto.getAssessScoreMap(),
                                new String[]{BusinessUtil.REGEX_COLON,BusinessUtil.REGEX_LINE}));
//            insertPpmEarlyCourseApply(ppmEarlyCourseApplyDto);
            }

            if(ppmSupplierAssessDto.getAssessType().contains("XM")){
                PpmProjectInfoDto ppmProjectInfoDto =
                        ppmProjectInfoService.queryProjectDetail(null,
                                Long.parseLong(ppmSupplierAssessDto.getDeptId()),
                                ppmSupplierAssessDto.getProjectNumber());
                if(ppmProjectInfoDto != null){
                    if(ppmSupplierAssessDto.getPmId().equals(ppmProjectInfoDto.getBusiBadge())){
                        ppmSupplierAssessDto.setPmType("YW");
                    }

                    if(ppmSupplierAssessDto.getPmId().equals(ppmProjectInfoDto.getBadge())){
                        ppmSupplierAssessDto.setPmType("KJ");
                    }
                }
            }

            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(ppmSupplierAssessDto.getId()));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            ppmSupplierAssessDto.setInstanceid(processBean.getProcessInstanceId());
            addPpmSupplierAssess(ppmSupplierAssessDto);

            // 日志处理
            bps.setProjectNo(String.valueOf(ppmSupplierAssessDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc(FLOW_TITLE + "申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());

            bps.setProcessInstName("【" + ppmSupplierAssessDto.getSupplierName() + "】" +
                    ppmSupplierAssessDto.getAssessYear() +  "年" + FLOW_TITLE);
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
    public String managerInit(PpmSupplierAssessDto ppmSupplierAssessDto) {
        BpsWorkFlowDto bps = ppmSupplierAssessDto.getBps();
        if (ObjectUtils.isEmpty(bps)) {
            bps = new BpsWorkFlowDto();
        }

        //校验
        vaildCommitData(ppmSupplierAssessDto);

        ProcessBean processBean = null;
        try {
            // 第一次流程发起
            if (!StringUtil.isNotBlank(ppmSupplierAssessDto.getInstanceid())) {
                bps.setProcessDefName(FLOW_ID);

                // 如果需要请set 主键id
                ppmSupplierAssessDto.setId(getNextSequence(PpmSupplierAssess.class));
                ppmSupplierAssessDto.setCreatedTime(new Date());
                ppmSupplierAssessDto.setCreatedBy(bps.getUserId());

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(ppmSupplierAssessDto.getId()));
                Map mPara = new HashMap();
                //获取下一步处理人 也就是对应的二级部经理
                String currentId = "";
                String currentName = "";
                if (StringUtil.isNotBlank(ppmSupplierAssessDto.getDeptId())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(ppmSupplierAssessDto.getDeptId());
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
                ppmSupplierAssessDto.setInstanceid(processBean.getProcessInstanceId());
                ppmSupplierAssessDto.setState("1");
                ppmSupplierAssessDto.setStateName("处理中");
                ppmSupplierAssessDto.setCurrentId(currentId);
                ppmSupplierAssessDto.setCurrentName(currentName);
                ppmSupplierAssessDto.setCreatedBy(bps.getUserId());
                ppmSupplierAssessDto.setCreatedTime(new Date());
                //插入供应商考核总分集合
                ppmSupplierAssessDto.setAssessScores(
                        BusinessUtil.mapRegexString(
                                ppmSupplierRelationService.queryDetailKey(ppmSupplierAssessDto.getAssessType()),
                                ppmSupplierAssessDto.getAssessScoreMap(),
                                new String[]{BusinessUtil.REGEX_COLON,BusinessUtil.REGEX_LINE}));

                if(ppmSupplierAssessDto.getAssessType().contains("XM")){
                    PpmProjectInfoDto ppmProjectInfoDto =
                            ppmProjectInfoService.queryProjectDetail(null,
                                    Long.parseLong(ppmSupplierAssessDto.getDeptId()),
                                    ppmSupplierAssessDto.getProjectNumber());
                    if(ppmProjectInfoDto != null &&
                            Objects.equals(ppmSupplierAssessDto.getPmId(),ppmProjectInfoDto.getBusiBadge())){
                        ppmSupplierAssessDto.setPmType("YW");
                    }

                    if(ppmProjectInfoDto != null &&
                            Objects.equals(ppmSupplierAssessDto.getPmId(),ppmProjectInfoDto.getBadge())){
                        ppmSupplierAssessDto.setPmType("KJ");
                    }
                }


                addPpmSupplierAssess(ppmSupplierAssessDto);

                // 日志处理
                bps.setProjectNo(String.valueOf(ppmSupplierAssessDto.getId()));
                bps.setConclusion("01");
                bps.setApplydesc(FLOW_TITLE + "发起");
                bps.setRelaData(mPara);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setProcessInstName("【" + ppmSupplierAssessDto.getSupplierName() + "】" +
                        ppmSupplierAssessDto.getAssessYear() +  "年" + FLOW_TITLE);
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
                if (StringUtil.isNotBlank(ppmSupplierAssessDto.getDeptId())) {
                    CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(ppmSupplierAssessDto.getDeptId());
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
                bps.setProcessInstId(ppmSupplierAssessDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion("01");
                bps.setSuggest("重新提交");
                bps.setApplydesc(FLOW_TITLE);

                ppmSupplierAssessDto.setState("1");
                ppmSupplierAssessDto.setStateName("处理中");
                ppmSupplierAssessDto.setCurrentId(currentId);
                ppmSupplierAssessDto.setCurrentName(currentName);
                ppmSupplierAssessDto.setModifiedBy(nowUserId);
                ppmSupplierAssessDto.setModifiedTime(new Date());
                //插入供应商考核总分集合
                ppmSupplierAssessDto.setAssessScores(
                        BusinessUtil.mapRegexString(
                                ppmSupplierRelationService.queryDetailKey(ppmSupplierAssessDto.getAssessType()),
                                ppmSupplierAssessDto.getAssessScoreMap(),
                                new String[]{BusinessUtil.REGEX_COLON,BusinessUtil.REGEX_LINE}));

                updatePpmSupplierAssess(ppmSupplierAssessDto);

                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(ppmSupplierAssessDto.getInstanceid());
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
    public String departmentHeadApproval(PpmSupplierAssessDto ppmSupplierAssessDto) {
        BpsWorkFlowDto bps = ppmSupplierAssessDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            String nowUserId = bps.getUserId();
            param.setUserId(nowUserId);// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmSupplierAssessDto.setState("0");
            ppmSupplierAssessDto.setStateName("重新提交");
            ppmSupplierAssessDto.setCurrentId(ppmSupplierAssessDto.getPmId());
            ppmSupplierAssessDto.setCurrentName(ppmSupplierAssessDto.getPmName());
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
                ppmSupplierAssessDto.setState("2");
                ppmSupplierAssessDto.setStateName("处理中");
                ppmSupplierAssessDto.setCurrentId(joiner.toString());
                ppmSupplierAssessDto.setCurrentName(joinerName.toString());

            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("二级部经理处理");
            bps.setRelaData(mPara);

            ppmSupplierAssessDto.setModifiedBy(nowUserId);
            ppmSupplierAssessDto.setModifiedTime(new Date());
            updatePpmSupplierAssess(ppmSupplierAssessDto);

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
    public String generalManagerApproval(PpmSupplierAssessDto ppmSupplierAssessDto) {
        BpsWorkFlowDto bps = ppmSupplierAssessDto.getBps();
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            ppmSupplierAssessDto.setState("0");
            ppmSupplierAssessDto.setStateName("重新提交");
            ppmSupplierAssessDto.setCurrentId(ppmSupplierAssessDto.getPmId());
            ppmSupplierAssessDto.setCurrentName(ppmSupplierAssessDto.getPmName());
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                ppmSupplierAssessDto.setState("-1");
                ppmSupplierAssessDto.setStateName("已结束");
                ppmSupplierAssessDto.setCurrentId("");
                ppmSupplierAssessDto.setCurrentName("");
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
            ppmSupplierAssessDto.setModifiedBy(bps.getUserId());
            ppmSupplierAssessDto.setModifiedTime(new Date());
            updatePpmSupplierAssess(ppmSupplierAssessDto);

            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean,null,bps);
            if (ObjectUtils.isEmpty(taskBean) || "Y".equals(taskBean.getIsFinished())) {
                //判断
                handleAssessInfo(ppmSupplierAssessDto);
            }
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部总经理室审批失败！");
            e.printStackTrace();
            throw new BizException(e);
        }
    }

    /**
     * 处理权限
     * @param supplierAssessDto ppmKqInfoDto
     * @param user user
     */
    private void handleAuthCode(PpmSupplierAssessDto supplierAssessDto, CommOperatorDoLoginDto user){
        // 权限控制
        String authCode = ppmOutsourcingBasicService.getPermissionByUserid(user);
        // 二级部经理需要加入部门限制
        if ("managers".equals(authCode)) {
            supplierAssessDto.setDeptId(user.getDeptId());
            // 项目经理需要加入人员限制
        } else if ("common".equals(authCode)) {
            supplierAssessDto.setPmId(user.getUserid());
            //系统管理员和研发管理部，一级部经理,外包管理岗不做限制
        }
    }

    /**
     * 对提交的数据做校验
     * @param ppmSupplierAssessDto 对象
     */
    private void vaildCommitData(PpmSupplierAssessDto ppmSupplierAssessDto){
        String assessType = ppmSupplierAssessDto.getAssessType();
        if(StringUtils.isEmpty(assessType)){
            throw new BizException(ApiCode.FAIL,"考核类型为空");
        }else {
            if(assessType.contains("RL")){
                this.FLOW_ID = FLOW_ID_RL;
                this.FLOW_TITLE = FLOW_TITLE_RL;
            }else if(assessType.contains("XM")){
                this.FLOW_ID = FLOW_ID_XM;
                this.FLOW_TITLE = FLOW_TITLE_XM;
            }else {
                throw new BizException(ApiCode.FAIL,"考核类型不合规");
            }
        }

        //判断是否已提交过年份的数据
        PpmSupplierAssessDto supplierAssessDto = new PpmSupplierAssessDto();
        supplierAssessDto.setAssessType(ppmSupplierAssessDto.getAssessType());
        supplierAssessDto.setPmId(ppmSupplierAssessDto.getPmId());
        supplierAssessDto.setAssessYear(ppmSupplierAssessDto.getAssessYear());
        supplierAssessDto.setSupplierId(ppmSupplierAssessDto.getSupplierId());
        if(ppmSupplierAssessDto.getAssessType().contains("XM")){
            supplierAssessDto.setProjectNumber(ppmSupplierAssessDto.getProjectNumber());
        }
        List<PpmSupplierAssessDto> supplierAssessDtos = queryDetailList(supplierAssessDto);
        if(!CollectionUtils.isEmpty(supplierAssessDtos)
                && !Objects.equals(supplierAssessDtos.get(0).getInstanceid(),ppmSupplierAssessDto.getInstanceid())){
            log.info("{}{}年份{}考核已存在！",ppmSupplierAssessDto.getSupplierName(),
                    ppmSupplierAssessDto.getAssessYear(),
                    ppmSupplierAssessDto.getAssessType().contains("RL")?"人力":"项目");
            throw new BizException(ApiCode.FAIL,"此供应商该年份考核已存在！");
        }

        //外包花名册如果没有这个项目经理+供应商+项目的人员则不予提交
        //项目和人力分开校验
        PpmOutsourcingBasicDto ppmOutsourcingBasicDto = new PpmOutsourcingBasicDto();
        if(ppmSupplierAssessDto.getAssessType().contains("RL")){
            ppmOutsourcingBasicDto.setOutsourceType("1");
            ppmOutsourcingBasicDto.setPersonInCharge(ppmSupplierAssessDto.getPmId());
            ppmOutsourcingBasicDto.setOutsourceNo(ppmSupplierAssessDto.getSupplierId());
            List<PpmOutsourcingBasicDto> basicDtos = ppmOutsourcingBasicService.queryOutList(ppmOutsourcingBasicDto);
            if (basicDtos == null || basicDtos.isEmpty()){
                throw new BizException(ApiCode.FAIL,"未在花名册中查询到您负责的该供应商人员，无法发起！");
            }
        }else {
            PpmProjectInfoDto ppmProjectInfoDto = new PpmProjectInfoDto();
            ppmProjectInfoDto.setId(ppmSupplierAssessDto.getProjectId());
            ppmProjectInfoDto = ppmProjectInfoService.queryDetail(ppmProjectInfoDto);

            if(ppmProjectInfoDto == null){
                throw new BizException(ApiCode.FAIL,"无此项目！");
            }

            if(!Objects.equals(ppmSupplierAssessDto.getPmId(),ppmProjectInfoDto.getBadge()) &&
                    !Objects.equals(ppmSupplierAssessDto.getPmId(),ppmProjectInfoDto.getBusiBadge())){
                throw new BizException(ApiCode.FAIL,"您不是该项目的相关项目经理，请确认后重试！");
            }
        }

    }

    @Override
    public void exportPdf(PpmSupplierAssessDto ppmSupplierAssessDto,
                          HttpServletResponse response) throws Exception{
        PpmSupplierAssessDto assessDto = queryDetail(ppmSupplierAssessDto,"queryBaseInfo");
        if(assessDto == null){
            return;
        }

        File file = new File(getFileNameValue(assessDto.getAssessType()));
        Map<String,Object> assessScoreMap = BusinessUtil.stringRegexMap(
                assessDto.getAssessScores(),new String[]{BusinessUtil.REGEX_LINE_UN,BusinessUtil.REGEX_COLON});
        assessDto.setAssessScoreMap(assessScoreMap);

        Map<String,Object> params = new HashMap<>();
        //评分集合存储数据
        params.put("assessYear",assessDto.getAssessYear());
        params.put("supplierName",assessDto.getSupplierName());
        params.put("assessScoreSum",assessDto.getAssessScoreSum());
        params.put("assessLevel",assessDto.getAssessLevel());
        params.put("pmName",assessDto.getPmName());
        params.put("projectName",assessDto.getProjectName());
        params.put("existQuestion",assessDto.getExistQuestion());

        CommEmployeeDto employeeDto = UsersUtil.getTwoWorkManager(assessDto.getDeptId());
        params.put("deptLeaderName",employeeDto.getEmpname());

//        List<Map> mapList = new ArrayList<>();
        Set<String> keySet = assessScoreMap.keySet();

        keySet.forEach(str -> params.put(str + "_result",assessScoreMap.get(str)));

        XWPFDocument xwpfDocument = WordExportUtil.exportWord07(file.getPath(),params);
        PdfOptions pdfOptions = PdfOptions.create();

        ServletOutputStream outputStream = response.getOutputStream();
        PdfConverter.getInstance().convert(xwpfDocument,outputStream,pdfOptions);

    }

    /**
     * 获取供应商考核导出文件名称
     * @param assessType 考核类型
     * @return fileName
     */
    private String getFileNameValue(String assessType){
        String fileName = "";
        if(assessType.contains("RL")){
            fileName = rlFileName;
        }else if(assessType.contains("XM")){
            fileName = xmFileName;
        }
        return fileName;
    }


    /**
     * 处理请求报文数据方法
     * @param ppmSupplierAssessDto 对象
     */
    private void handleAssessInfo(PpmSupplierAssessDto ppmSupplierAssessDto){
        //默认人力
        PpmSupplierAssessDto supplierAssessDto = new PpmSupplierAssessDto();
        supplierAssessDto.setStartTime(ppmSupplierAssessDto.getAssessYear()+"0101");
        supplierAssessDto.setEndTime(ppmSupplierAssessDto.getAssessYear()+"1231");
        supplierAssessDto.setSupplierId(ppmSupplierAssessDto.getSupplierId());
        List<PpmSupplierAssessDto> countList = countSupplierPmRL(supplierAssessDto);

        Long count = (long) countList.size();
        if(ppmSupplierAssessDto.getAssessType().contains("RL")){
            Long sum = 0L;
            for(PpmSupplierAssessDto countObj : countList){
                sum += countObj.getPersonCount();
            }

            Long finalSum = sum;
            countList.forEach(countObj -> countObj.setPercent((double) (countObj.getPersonCount() / finalSum)));
        }else if(ppmSupplierAssessDto.getAssessType().contains("XM")){
            count = 2L;
        }else {
            return;
        }

        PpmSupplierAssessDto supplierAssessDto1 = new PpmSupplierAssessDto();
        supplierAssessDto1.setAssessType(ppmSupplierAssessDto.getAssessType());
        supplierAssessDto1.setAssessYear(ppmSupplierAssessDto.getAssessYear());
        supplierAssessDto1.setSupplierId(ppmSupplierAssessDto.getSupplierId());
        supplierAssessDto1.setState("-1");
        if(ppmSupplierAssessDto.getAssessType().contains("XM")){
            supplierAssessDto1.setProjectNumber(ppmSupplierAssessDto.getProjectNumber());
        }
        List<PpmSupplierAssessDto> supplierAssessDtos = queryBaseList(supplierAssessDto1);
        if(supplierAssessDtos.size() == count){
            //可以进行下发
            JSONObject body = new JSONObject();
            if(ppmSupplierAssessDto.getAssessType().contains("XM")){
                PpmSuppliersSyncDto ppmSuppliersSyncDto = new PpmSuppliersSyncDto();
                ppmSuppliersSyncDto.setYzbm(ppmSupplierAssessDto.getProjectNumber());
                ppmSuppliersSyncDto = ppmSuppliersSyncService.queryDetail(ppmSuppliersSyncDto);
                body.put("xmbh",ppmSuppliersSyncDto.getXmbh());
                body.put("htid",ppmSuppliersSyncDto.getHtid());
                body.put("htlb","42");//合同类别 项目
            }else{
                body.put("xmbh",null);
                body.put("htid",null);
                body.put("htlb","41");//合同类别 人力
            }

            PpmOutsourcingInfo ppmOutsourcingInfo = new PpmOutsourcingInfo();
            ppmOutsourcingInfo.setOrgcode(ppmSupplierAssessDto.getSupplierId());
            ppmOutsourcingInfo = ppmOutsourcingInfoService.queryDetail(ppmOutsourcingInfo);
            JSONArray bpjrList = new JSONArray();
            JSONObject bpjrObj = new JSONObject();
            if(ppmOutsourcingInfo != null){
                bpjrObj.put("gysbh",ppmOutsourcingInfo.getCgSupplierId());//供应商编号
            }

            if(ppmSupplierAssessDto.getAssessType().contains("RL")){
                bpjrObj.put("pjzf",calRlSupplierScores(supplierAssessDtos,countList));
            }else {
                bpjrObj.put("pjzf",calXmSupplierScores(supplierAssessDtos));
            }
            JSONArray pjList = new JSONArray();
            supplierAssessDtos.forEach(obj ->{
                JSONObject pjObj = new JSONObject();
                pjObj.put("pjr",obj.getPmId());
                pjObj.put("pjrmc",obj.getPmName());
                pjObj.put("pjdf",obj.getAssessScoreSum()+"");
                pjObj.put("pjsj",DateUtil.dateToStr(obj.getCreatedTime(),"yyyy-MM-dd HH:mm:ss"));
                pjList.add(pjObj);
            });
            bpjrObj.put("pjList",pjList);
            bpjrList.add(bpjrObj);
            body.put("bpjrList",bpjrList);
            log.info("{}{}{}供应商考核评价推送报文：{}",ppmSupplierAssessDto.getAssessYear(),
                    ppmSupplierAssessDto.getSupplierName(),
                    ppmSupplierAssessDto.getAssessType().contains("RL")?"人力":"项目",body);
            if(handSupplierAssess(body)){
                boolean flag = ppmSupplierAssessDto.getAssessType().contains("RL");

                String pjzf = bpjrObj.getString("pjzf");
                //供应商考核评分表
                saveSupplierAssessGrade(ppmSupplierAssessDto,flag,Double.parseDouble(pjzf));

                //将总分存入到供应商花名册
                saveSupplierRosterInfo(ppmSupplierAssessDto,flag,pjzf);
            }
        }else{
            //反之不能下发
            log.info("{}{}{}供应商考核评价暂时无法下发",ppmSupplierAssessDto.getAssessYear(),
                    ppmSupplierAssessDto.getSupplierName(),
                    ppmSupplierAssessDto.getAssessType().contains("RL")?"人力":"项目");
        }
    }

    /**
     * 调用同步科管评价信息接口 开发
     * @param body 对象
     * @return boolean
     */
    public boolean handSupplierAssess(JSONObject body) {

        log.info("同步科管评价信息接口提交报文:{}", body);
        String res;
        try {
            res = OpenSdk.apiSdk(body.toString(),productId,serverId);
            log.info("同步科管评价信息接口返回报文:{}", res);
            JSONObject resJson = JSON.parseObject(res);

            JSONObject json = JSON.parseObject(resJson.get("Data").toString());
            if(json == null){
                json = resJson;
            }
            log.info("同步科管评价信息接口data返回报文:{}", json);
            Object errorCode = json.get("errorCode");
            if (!"0000".equals(errorCode)) {
                log.info("同步科管评价信息接口 调用失败：{}",json.get("retMsg"));
                return false;
            } else {
                log.info("下发成功");
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("同步科管评价信息接口异常：{}",e.getMessage());
            return false;
        }

    }

    /**
     * 考核评分存储
     * @param ppmSupplierAssessDto 对象
     * @param flag true : 人力 false : 项目
     * @param pjzf 评价总分
     */
    private void saveSupplierAssessGrade(PpmSupplierAssessDto ppmSupplierAssessDto,Boolean flag,Double pjzf){
        //查询是否有此考评记录
        PpmSupplierAssessGradeDto supplierAssessGradeDto = new PpmSupplierAssessGradeDto();
        supplierAssessGradeDto.setDeptId(ppmSupplierAssessDto.getDeptId());
        supplierAssessGradeDto.setAssessYear(ppmSupplierAssessDto.getAssessYear());
        supplierAssessGradeDto.setSupplierCode(ppmSupplierAssessDto.getSupplierId());
        supplierAssessGradeDto.setOutsourceType(flag?"1":"0");
        if(!flag){
            //如果是项目将项目编号放入对象，进行查询
            supplierAssessGradeDto.setProjectId(ppmSupplierAssessDto.getProjectId()+"");
        }

        PpmSupplierAssessGradeDto assessGradeDto =
                supplierAssessGradeService.queryDetail(supplierAssessGradeDto,"queryDetailQt");
        if(assessGradeDto != null && assessGradeDto.getId() != null){
            PpmSupplierAssessGradeDto gradeDto = new PpmSupplierAssessGradeDto();
            gradeDto.setId(assessGradeDto.getId());
            gradeDto.setAssessResult(pjzf);
            supplierAssessGradeService.updatePpmSupplierAssessGrade(gradeDto);
        }else{
            assessGradeDto = supplierAssessGradeDto;
            assessGradeDto.setId(getNextSequence(PpmSupplierAssessGrade.class));
            assessGradeDto.setAssessType(ppmSupplierAssessDto.getAssessType());
            assessGradeDto.setAssessResult(pjzf);
            supplierAssessGradeService.addPpmSupplierAssessGrade(assessGradeDto);
        }
        log.info("{}{}年考核评分已存储至供应商考核评价表。",ppmSupplierAssessDto.getSupplierName(),
                ppmSupplierAssessDto.getAssessYear());
    }

    /**
     * 考核评分存储存至供应商
     * @param ppmSupplierAssessDto 对象
     * @param flag true : 人力 false : 项目
     * @param pjzf 评价总分
     */
    private void saveSupplierRosterInfo(PpmSupplierAssessDto ppmSupplierAssessDto,Boolean flag,String pjzf){
        //将总分存入到供应商花名册
        PpmSupplierRosterInfoDto supplierRosterInfoDto = new PpmSupplierRosterInfoDto();
        supplierRosterInfoDto.setSupplierCode(ppmSupplierAssessDto.getSupplierId());
        //通过
        List<PpmSupplierRosterInfoDto> rosterInfoDtos =
                ppmSupplierRosterInfoService.queryDetailList(supplierRosterInfoDto);
        rosterInfoDtos.forEach(rosterInfoDto ->{
            PpmSupplierRosterInfoDto rosterInfoDto1 = new PpmSupplierRosterInfoDto();
            rosterInfoDto1.setId(rosterInfoDto.getId());
            ppmSupplierRosterInfoService.updatePpmSupplierRosterInfo(rosterInfoDto1);
        });
        log.info("{}{}年考核评分已存储至供应商扩展表。",ppmSupplierAssessDto.getSupplierName(),
                ppmSupplierAssessDto.getAssessYear());
    }

    /**
     * 计算供应商考核项目类总分方法
     * @param list list
     * @return String
     */
    private String calRlSupplierScores(List<PpmSupplierAssessDto> list,List<PpmSupplierAssessDto> countList){
        Double result = 0.00;
        Double sum = countList.stream().mapToDouble(PpmSupplierAssessDto::getPersonCount).sum();

        for(PpmSupplierAssessDto ppmSupplierAssessDto : list){
            List<PpmSupplierAssessDto> sums =
                    countList.stream().filter(countObj -> ppmSupplierAssessDto.getPmId().equals(countObj.getPmId()))
                            .collect(Collectors.toList());

            Double value = Double.valueOf(sums.get(0).getPersonCount());
            ppmSupplierAssessDto.setPercent(value / sum);
            result += ppmSupplierAssessDto.getAssessScoreSum() * ppmSupplierAssessDto.getPercent();
        }

        return String.format("%.2f",result);
    }

    /**
     * 计算供应商考核项目类总分方法
     * @param list list
     * @return String
     */
    private String calXmSupplierScores(List<PpmSupplierAssessDto> list){
        Double result = 0.00;
        for(PpmSupplierAssessDto ppmSupplierAssessDto : list){
            if("KJ".equals(ppmSupplierAssessDto.getPmType())){
                result += ppmSupplierAssessDto.getAssessScoreSum() * 0.7;
            }else {
                result += ppmSupplierAssessDto.getAssessScoreSum() * 0.3;
            }
        }

        return result.toString();
    }

    @Override
    public void supplierAssessFlowRl() {
        //取上一年 第一天 和 最后一天
        int year =  DateUtil.getYear() - 1;
        Date first = DateUtil.getFirstOfYear(year);
        Date end = DateUtil.getLastOfYear(year);
//        LocalDate.now().
        //获取人力类的供应商有
        PpmOutsourcingBasicDto tempOutsourceNo = new PpmOutsourcingBasicDto();
        tempOutsourceNo.setOutsourceType("1");
        List<PpmOutsourcingBasicDto> outsourceNoList = ppmOutsourcingBasicService.queryOutList(tempOutsourceNo);
        //入场时间 <= 最后一天 && (离场时间 == null || 离场时间 > 第一天)
        outsourceNoList = outsourceNoList.stream().filter(outsourceNo -> end.after(outsourceNo.getEntryTime())
                        && (outsourceNo.getLeavingTime() == null || first.before(outsourceNo.getLeavingTime()) ))
                .collect(Collectors.toList());
        //获取有多少供应商
        List<String> outsourceNos = outsourceNoList.stream()
                .map(PpmOutsourcingBasicDto::getOutsourceNo).distinct().collect(Collectors.toList());

        //
        List<PpmOutsourcingBasicDto> finalOutsourceNoList = outsourceNoList;
        //遍历供应商
        outsourceNos.forEach(outsourceNo -> {
            //筛选对应供应商
            List<PpmOutsourcingBasicDto> pmList = finalOutsourceNoList.stream().filter(obj ->
                    outsourceNo.equals(obj.getOutsourceNo())).collect(Collectors.toList());
            //获取项目经理集合
            List<String> pmIdList = pmList.stream()
                    .map(PpmOutsourcingBasicDto::getPersonInCharge).distinct().collect(Collectors.toList());
            //遍历项目经理
            pmIdList.forEach(pmId ->{
                PpmSupplierAssessDto ppmSupplierAssessDto = new PpmSupplierAssessDto();
                ppmSupplierAssessDto.setPmId(pmId);
                ppmSupplierAssessDto.setDeptId(commEmployeeService.getDeptNo(pmId));
                BpsWorkFlowDto bps = new BpsWorkFlowDto();
                bps.setUserId(pmId);
                bps.setUserName(commEmployeeService.getBadgeAndName(pmId));
                ppmSupplierAssessDto.setBps(bps);
                ppmSupplierAssessDto.setAssessYear(year+"");
                ppmSupplierAssessDto.setSupplierId(outsourceNo);
                ppmSupplierAssessDto.setSupplierName(ppmOutsourcingInfoService.queryNameByOrgCode(outsourceNo));
                ppmSupplierAssessDto.setPmType("KJ");
                ppmSupplierAssessDto.setAssessType("RL01");
                //流程发起
                try {
                    managerCreated(ppmSupplierAssessDto);
                }catch (BizException e){
                    log.info(e.getMessage());
                    return;
                }
                log.info("供应商考核人力类流程:供应商:{},项目经理{}",outsourceNo,pmId);
            });
        });
    }

    @Override
    public void supplierAssessFlowXm() {
        //取当前时间
        PpmProjectInfoDto ppmProjectInfoDto = new PpmProjectInfoDto();
        ppmProjectInfoDto.setProjectEndTimeVal(DateUtil.addOrSubDateToString(new Date(),-1,"yyyyMMdd"));
        List<PpmProjectInfo> ppmProjectInfos = ppmProjectInfoService.queryProject(ppmProjectInfoDto);
        List<PpmProjectInfoDto> ppmProjectInfoDtos = JsonUtil.jsonToList(ppmProjectInfos,PpmProjectInfoDto.class);
        ppmProjectInfoDtos = ppmProjectInfoDtos.stream().filter(
                info -> StringUtils.isNotEmpty(info.getProjectNumber())).collect(Collectors.toList());

        ppmProjectInfoDtos.forEach(ppmProjectInfo -> {
            //项目类型/项目id
            PpmSuppliersSyncDto ppmSuppliersSyncDto = new PpmSuppliersSyncDto();
            ppmSuppliersSyncDto.setYzbm(ppmProjectInfo.getProjectNumber());
            ppmSuppliersSyncDto = ppmSuppliersSyncService.queryDetail(ppmSuppliersSyncDto);
            if(ppmSuppliersSyncDto == null){
                return;
            }

            PpmOutsourcingInfo ppmOutsourcingInfo = new PpmOutsourcingInfo();
            ppmOutsourcingInfo.setCgSupplierId(ppmSuppliersSyncDto.getGysbh());
            ppmOutsourcingInfo = ppmOutsourcingInfoService.queryDetail(ppmOutsourcingInfo);
            if(ppmOutsourcingInfo == null){
                return;
            }

            //科技项目经理发起
            String kjPmId = ppmProjectInfo.getBadge();
            if(StringUtils.isNotEmpty(kjPmId)){
                managerCreatedBefore(ppmProjectInfo,ppmOutsourcingInfo.getOrgcode(),kjPmId,"KJ");
            }

            //业务项目经理发起
            String ywPmId = ppmProjectInfo.getBusiBadge();
            if(StringUtils.isNotEmpty(ywPmId)){
                managerCreatedBefore(ppmProjectInfo,ppmOutsourcingInfo.getOrgcode(),ywPmId,"YW");
            }
            log.info("项目名称：{}的供应商考核项目类流程已自动发起",ppmProjectInfo.getName());
        });

    }

    /**
     * 保存流程前置方法
     * @param ppmProjectInfo 项目对象
     * @param outsourceNo 供应商编号
     * @param pmId 项目经理编号
     * @param pmType 项目经理类型
     */
    public void managerCreatedBefore(PpmProjectInfoDto ppmProjectInfo,String outsourceNo,String pmId,String pmType){
        PpmSupplierAssessDto ppmSupplierAssessDto = new PpmSupplierAssessDto();
        ppmSupplierAssessDto.setPmId(pmId);
        ppmSupplierAssessDto.setDeptId(ppmProjectInfo.getDepId()+"");
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        bps.setUserId(pmId);
        bps.setUserName(commEmployeeService.getBadgeAndName(pmId));
        ppmSupplierAssessDto.setBps(bps);
        ppmSupplierAssessDto.setAssessYear(DateUtil.getYear()+"");
        ppmSupplierAssessDto.setSupplierId(outsourceNo);
        ppmSupplierAssessDto.setSupplierName(ppmOutsourcingInfoService.queryNameByOrgCode(outsourceNo));
        ppmSupplierAssessDto.setPmType(pmType);
        ppmSupplierAssessDto.setAssessType("XM01");
        ppmSupplierAssessDto.setProjectId(ppmProjectInfo.getId());
        ppmSupplierAssessDto.setProjectNumber(ppmProjectInfo.getProjectNumber());
        //流程发起
        try {
            managerCreated(ppmSupplierAssessDto);
            log.info("供应商考核项目类流程:供应商:{},项目经理{}",outsourceNo,pmId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
