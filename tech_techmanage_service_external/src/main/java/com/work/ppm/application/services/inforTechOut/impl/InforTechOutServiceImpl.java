package com.work.ppm.application.services.inforTechOut.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dao.inforTechOut.InforTechOutMapper;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.inforTechOut.OssObjectPo;
import com.work.ppm.application.dto.inforTechOut.OssObjectVo;
import com.work.ppm.application.dto.inforTechOut.PhoneApproveDto;
import com.work.ppm.application.dto.inforTechOut.PpmOutsourcingXklx;
import com.work.ppm.application.dto.inforTechOut.PurchaseXklx;
import com.work.ppm.application.dto.inforTechOut.XJMultiSequenceRequestDTO;
import com.work.ppm.application.dto.inforTechOut.XKFileUploadDto;
import com.work.ppm.application.dto.rdmanager.RdContractDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.ext.PpmFlowLogInfo;
import com.work.ppm.application.model.inforTechOut.InforOutsourcFile;
import com.work.ppm.application.model.inforTechOut.XJMultiSequencePO;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.oss.utils.FileManagerOss;
import com.work.ppm.application.oss.ossServiceImpl.OssObjectConverter;
import com.work.ppm.application.oss.ossServiceImpl.OssObjectService;
import com.work.ppm.application.repository.IBaseDaoSuper;
import com.work.ppm.application.repository.IXJMultiSequenceReadModelRepository;
import com.work.ppm.application.services.external.CallEsbService;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import com.work.ppm.application.services.inforTechOut.InforTechOutService;
import com.work.ppm.application.services.workflowmanager.IInforTechService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.exception.BizRuntimeException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.socket.util.Utils;
import com.work.ppm.commons.utils.CamundaExtUtils;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.IpUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service("inforTechOutServiceImpl")
@RequiredArgsConstructor
public class InforTechOutServiceImpl extends ServiceImpl<InforTechOutMapper, InforOutsourcFile>
        implements InforTechOutService {
    @Autowired
    private IXJMultiSequenceReadModelRepository xjMultiSequenceReadModelRepository;

    @Autowired
    private IBaseDaoSuper baseDaoSuper;

    @Lazy
    @Autowired
    private IInforTechService InforTechService;

    @Autowired
    private IMyBatisDao myBatisDao;

    private final IPpmAppWorkflowInfoService ppmAppWorkflowInfoService;

    private static final String INFORTECHOUT = "com.work.ppm.application.dao.inforTechOut.InforTechOutMapper";
    // 流程定义ID
    private static final String FLOW_ID = "xxkjwblx";

    @Value("${esb.url}")
    private String url;

    /**
     * 采购桶
     */
    @Value("${mercuryoa.oss.endpoint-Bucket}")
    private String bucket;
    /**
     * 采购桶
     */
    @Value("${mercuryoa.oss.publicNginxXJHG}")
    private String publicNginxXJHG;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;

    private final CallEsbService esbService;

    @Autowired
    private OssObjectConverter ossObjectConverter;
    /**
     * ossObjectService
     */
    @Autowired
    private OssObjectService ossObjectService;

    @Override
    public OssObjectVo upload(XKFileUploadDto req, MultipartFile multipartFile) {
        OssObjectVo ossObject = new OssObjectVo(multipartFile, "");
        ossObject.setObjectId(FileManagerOss.createObjectId() + ossObject.getSuffix());
        if (!FileManagerOss.uploadobject(multipartFile, ossObject, bucket, publicNginxXJHG)) {
            throw new BizRuntimeException("上传失败");
        }
        ossObject.setDescription("");
        ossObject.setBizKey(req.getOutReqid());
        ossObject.setBizType("");
        ossObject.setCurtVersion("1");
        ossObject.setFlag("1");
        ossObject.setUploadOperator(req.getBadge());
        ossObject.setUploadOperatorName(req.getEmpname());
        ossObject.setId(StringUtil.UUID32());
        ossObject.setVersionRef(ossObject.getId());
        ossObject.setBucket("");
        //转换为PO对象
        OssObjectPo ossObjectPo = ossObjectConverter.convertToPo(ossObject);
        LambdaQueryWrapper<OssObjectPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OssObjectPo::getBizKey, ossObjectPo.getBizKey());
        queryWrapper.eq(OssObjectPo::getFlag, "1");
        List<OssObjectPo> ossObjectPos = this.ossObjectService.list(queryWrapper);
        if (!ossObjectPos.isEmpty()) {
            List<String> ids = ossObjectPos.stream().map(OssObjectPo::getId).collect(Collectors.toList());
            ossObjectService.removeByIds(ids);
        }
        ossObjectService.save(ossObjectPo);
        //保存PO对象

        return ossObject;
    }

    @Override
    @Transactional
    public String getNextSeqNo(XJMultiSequenceRequestDTO xJMultiSequenceRequestDTO) {
        StringBuffer seqprefix = new StringBuffer(xJMultiSequenceRequestDTO.getSeqprefix());
        QueryWrapper<XJMultiSequencePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SEQTYPE", xJMultiSequenceRequestDTO.getSeqtype());
        queryWrapper.eq("SEQPREFIX", xJMultiSequenceRequestDTO.getSeqprefix()).last("for update");
        XJMultiSequencePO xjMultiSequencePO = xjMultiSequenceReadModelRepository.getOne(queryWrapper);
        XJMultiSequencePO multiSequencePO = new XJMultiSequencePO();
        if (StringUtils.isEmpty(xjMultiSequencePO)) {
            multiSequencePO.setSeqtype(xJMultiSequenceRequestDTO.getSeqtype());
            multiSequencePO.setSeqid("1");
            multiSequencePO.setSeqprefix(xJMultiSequenceRequestDTO.getSeqprefix());
            xjMultiSequenceReadModelRepository.insertMultiSequence(multiSequencePO);
            String newString = Utils.frontCompWithZore(Integer.valueOf(multiSequencePO.getSeqid()),
                    Integer.valueOf(xJMultiSequenceRequestDTO.getFormatLength() == null ? "4" :
                            xJMultiSequenceRequestDTO.getFormatLength()));
            seqprefix.append(newString);
            return seqprefix.toString();
        } else {
            QueryWrapper<XJMultiSequencePO> updateWrapper = new QueryWrapper<>();
            updateWrapper.eq("SEQTYPE", xJMultiSequenceRequestDTO.getSeqtype());
            updateWrapper.eq("SEQPREFIX", xJMultiSequenceRequestDTO.getSeqprefix());
            int seqId = Integer.parseInt(xjMultiSequencePO.getSeqid()) + 1;
            xjMultiSequencePO.setSeqid(String.valueOf(seqId));
            xjMultiSequenceReadModelRepository.update(xjMultiSequencePO, updateWrapper);
            String newString = Utils.frontCompWithZore(Integer.valueOf(seqId), Integer.valueOf(
                    xJMultiSequenceRequestDTO.getFormatLength() == null ? "4" :
                            xJMultiSequenceRequestDTO.getFormatLength()));
            seqprefix.append(newString);
            return seqprefix.toString();
        }
    }

    @Override
    public List<InforOutsourcFile> queryInforOutsourcFile(String outReqid) {
        LambdaQueryWrapper<OssObjectPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OssObjectPo::getBizKey, outReqid);
        queryWrapper.eq(OssObjectPo::getFlag, "1");
        List<OssObjectPo> ossObjectPos = this.ossObjectService.list(queryWrapper);
        List<InforOutsourcFile> files = new ArrayList<>();
        for (OssObjectPo ossObjectPo : ossObjectPos) {
            InforOutsourcFile file = new InforOutsourcFile();
            file.setId(ossObjectPo.getId());
            file.setOutReqid(ossObjectPo.getBizKey());
            file.setFileName(ossObjectPo.getOriginName());
            file.setCreateBy(ossObjectPo.getUploadOperatorName());
            file.setCreateBadge(ossObjectPo.getUploadOperator());
            file.setCreateDate(ossObjectPo.getUploadTime());
            files.add(file);
        }
        return files;
    }

    @Override
    public void download(String id, HttpServletResponse response) throws Exception {
        OssObjectPo ossObjectPo = this.ossObjectService.getById(id);
        FileManagerOss.download(ossObjectPo.getObjectId(), ossObjectPo.getOriginName(), bucket, publicNginxXJHG, response);
    }

    @Override
    public void delete(String id) {
        LambdaUpdateWrapper<OssObjectPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OssObjectPo::getId, id);
        updateWrapper.set(OssObjectPo::getFlag, "0");
        updateWrapper.set(OssObjectPo::getCurtVersion, "0");
        this.ossObjectService.update(updateWrapper);
    }

    @Override
    public List<CommEmployee> getAplyDirector(String depid) {
        return this.getBaseMapper().queryList(depid);
    }

    @Override
    public List<CommEmployee> getOutDirector(String outDepid) {
        return this.getBaseMapper().queryList(outDepid);
    }

    @Override
    @Transactional("defaultTx")
    public PpmOutsourcingXklx saveInforTechApply(PpmOutsourcingXklx info, BpsWorkFlowDto bps) {
        // 更新数据
        if (com.work.ppm.commons.utils.StringUtil.isNotBlank(info.getInstanceid())) {
            info.setFlag(-1);
            this.getBaseMapper().updateAllPpmOutsourcingXklx(info);
            return info;
        }
        try {
            ProcessBean processBean;
            Long businessKey = baseDaoSuper.getNextSequence();
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(businessKey));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            info.setId(businessKey);
            info.setInstanceid(processBean.getProcessInstanceId());
            info.setCreatedate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
            info.setCreateid(bps.getUserId());
            info.setTitle("信息科技外包立项审批");
            info.setFlowid(FLOW_ID);
            info.setFlag(-1);
            this.getBaseMapper().addPpmOutsourcingXklx(info);
            // 日志处理
            bps.setProjectNo(String.valueOf(businessKey));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("添加");
            bps.setApplydesc("外包人员离场申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【信息科技外包立项审批】");
            bps.setProcessInstDesc("信息科技外包立项审批发起");
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return info;

        } catch (Exception e) {
            log.error("保存失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String outLeaveStart(PpmOutsourcingXklx infoDto, BpsWorkFlowDto bps) {
        // 发起申请
        ProcessBean processBean = null;
        try {
            if (!com.work.ppm.commons.utils.StringUtil.isNotBlank(infoDto.getInstanceid())) {
                Long businessKey = baseDaoSuper.getNextSequence();
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(businessKey));
                Map mPara = new HashMap();
                mPara.put("userId", infoDto.getAlctionDirector());
                processInfo.setmPara(mPara);// 流程变量
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
                infoDto.setId(businessKey);
                infoDto.setInstanceid(processBean.getProcessInstanceId());
                infoDto.setCreatedate(DateUtil.dateToStr(new Date(), "yyyy-MM-dd"));
                infoDto.setCreateid(bps.getUserId());
                infoDto.setTitle("信息科技外包立项审批");
                infoDto.setFlowid(FLOW_ID);
                infoDto.setFlag(1);
                this.getBaseMapper().addPpmOutsourcingXklx(infoDto);
                // 日志处理
                bps.setProjectNo(String.valueOf(businessKey));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("提交申请");
                bps.setApplydesc("信息科技外包立项审批申请");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstName("【信息科技外包立项审批】");
                bps.setProcessInstDesc("信息科技外包立项审批发起");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
                //推送待办
                String imgTxDsc = "(" + bps.getUserName()
                        + bps.getUserId() + ")，于"
                        + infoDto.getCreatedate()
                        + "，发起外包需求名称:“" + infoDto.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
                InforTechService.pushTaskCommon(infoDto.getAlctionDirector(), "申请人发起",
                        imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                infoDto.setFlag(1);
                this.getBaseMapper().updateAllPpmOutsourcingXklx(infoDto);
                // 执行任务
                TaskBean param = new TaskBean();
                param.setTenantId(BusinessConstant.TENANT_ID);// 租户
                param.setTaskId(bps.getTaskId());// 任务id
                param.setUserId(bps.getUserId());// 处理人
                param.setComment(bps.getSuggest());// 审批意见
                Map mPara = new HashMap();
                mPara.put("userId", infoDto.getAlctionDirector());
                param.setmPara(mPara);// 流程变量
                // 提交任务
                workFlowHttpClientService.submitTask(param);
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(infoDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setApplydesc("信息科技外包立项审批申请");
                bps.setConclusion("01");
                bps.setSuggest("提交申请");
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(infoDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
                //删除待办
                PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                        ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
                if (ppmAppWorkflowInfoDto != null) {
                    InforTechService.deleteTaskXy(ppmAppWorkflowInfoDto);
                }
                //推送待办
                String imgTxDsc = "(" + bps.getUserName()
                        + bps.getUserId() + ")，于"
                        + infoDto.getCreatedate()
                        + "，发起外包需求名称:“" + infoDto.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
                InforTechService.pushTaskCommon(infoDto.getAlctionDirector(), "申请人发起",
                        imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("添加并发起流程失败！", e);
            throw new BizException(e);
        }

    }

    @Override
    public PpmOutsourcingXklx getDb(PpmOutsourcingXklx info) {
        if (!Strings.isNullOrEmpty(info.getInstanceid())) {
            PpmOutsourcingXklx ppmOutsourcingXklx = this.getBaseMapper().getDb(info);
            if (Objects.nonNull(ppmOutsourcingXklx)) {
                ppmOutsourcingXklx.setFileList(this.queryInforOutsourcFile(ppmOutsourcingXklx.getOutReqNum()));
            }
            return ppmOutsourcingXklx;
        }
        return null;
    }

    @Override
    @Transactional("defaultTx")
    public String managerApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps) {
        try {
            // 申请部门负责人审批
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            String userId = "";
            String depEquals = "";
            if (ppmOutsourcingXklx.getAlctionDirector().equals(ppmOutsourcingXklx.getOutDirectorBadge())) {
                userId = UsersUtil.getDepApprove("JRKJ_HANDING").getBadge();
                depEquals = "equal";
            } else {
                userId = ppmOutsourcingXklx.getOutDirectorBadge();
                depEquals = "noEqual";
            }
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("userId", userId);
                mPara.put("status", depEquals);
            } else {
                mPara.put("status", "return");
                ppmOutsourcingXklx.setFlag(0);
                this.getBaseMapper().updatePpmOutsourcingXklx(ppmOutsourcingXklx);
            }
            param.setmPara(mPara);
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc(bps.getActivityName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                InforTechService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            String msg = CamundaUtils.findNextTaskInfo(taskBean, null);
            //推送待办
            String participantPart = msg.substring(msg.indexOf("参与者:") + "参与者:".length());

            // 3. 按 "-" 分割，取第一部分即为 ID
            String[] parts = participantPart.split("-");
            String userid = "";
            if (parts.length > 0) {
                userid = parts[0].trim(); // 去除可能存在的空格
            }
            String imgTxDsc = "(" + bps.getUserName()
                    + bps.getUserId() + ")，于"
                    + ppmOutsourcingXklx.getCreatedate()
                    + "，发起外包需求名称:“" + ppmOutsourcingXklx.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
            InforTechService.pushTaskCommon(userid, "申请部门负责人审批",
                    imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
            return msg;
        } catch (Exception e) {
            log.error("申请部门负责人审批！", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String hiringDepApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps) {
        // 用工部门负责人审批
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            // 获取金融科技部经办人
            CommEmployee commEmployee = UsersUtil.getDepApprove("JRKJ_HANDING");
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                mPara.put("userId", commEmployee.getBadge());

            } else {
                mPara.put("status", "return");
                ppmOutsourcingXklx.setFlag(0);
                this.getBaseMapper().updatePpmOutsourcingXklx(ppmOutsourcingXklx);
            }
            param.setmPara(mPara);
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc(bps.getActivityName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                InforTechService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            String msg = CamundaUtils.findNextTaskInfo(taskBean, null);
            //推送待办
            String participantPart = msg.substring(msg.indexOf("参与者:") + "参与者:".length());

            // 3. 按 "-" 分割，取第一部分即为 ID
            String[] parts = participantPart.split("-");
            String userid = "";
            if (parts.length > 0) {
                userid = parts[0].trim(); // 去除可能存在的空格
            }
            String imgTxDsc = "(" + bps.getUserName()
                    + bps.getUserId() + ")，于"
                    + ppmOutsourcingXklx.getCreatedate()
                    + "，发起外包需求名称:“" + ppmOutsourcingXklx.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
            InforTechService.pushTaskCommon(userid, "用工部门负责人审批",
                    imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
            return msg;

        } catch (Exception e) {
            log.error("用工部门负责人审批！", e);
            throw new BizException(e);
        }

    }

    @Override
    @Transactional("defaultTx")
    public String financeApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps) {
        // 金融科技部经办人审核
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            // 获取金融科技部经办人
            CommEmployee commEmployee = UsersUtil.getDepApprove("FXGL_HANDING");
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                mPara.put("userId", commEmployee.getBadge());

            } else {
                mPara.put("status", "return");
                ppmOutsourcingXklx.setFlag(0);
                this.getBaseMapper().updatePpmOutsourcingXklx(ppmOutsourcingXklx);
            }
            param.setmPara(mPara);
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc(bps.getActivityName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                InforTechService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            String msg = CamundaUtils.findNextTaskInfo(taskBean, null);
            //推送待办
            String participantPart = msg.substring(msg.indexOf("参与者:") + "参与者:".length());

            // 3. 按 "-" 分割，取第一部分即为 ID
            String[] parts = participantPart.split("-");
            String userid = "";
            if (parts.length > 0) {
                userid = parts[0].trim(); // 去除可能存在的空格
            }
            String imgTxDsc = "(" + bps.getUserName()
                    + bps.getUserId() + ")，于"
                    + ppmOutsourcingXklx.getCreatedate()
                    + "，发起外包需求名称:“" + ppmOutsourcingXklx.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
            InforTechService.pushTaskCommon(userid, "金融科技部经办人审核",
                    imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
            return msg;

        } catch (Exception e) {
            log.error("金融科技部经办人审核！", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String riskHandApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps) {
        // 风险管理部经办人审核

        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
            } else {
                mPara.put("status", "return");
                ppmOutsourcingXklx.setFlag(0);
                this.getBaseMapper().updatePpmOutsourcingXklx(ppmOutsourcingXklx);
            }
            param.setmPara(mPara);
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc(bps.getActivityName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                InforTechService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            String msg = CamundaUtils.findNextTaskInfo(taskBean, null);
            //推送待办
            String participantPart = msg.substring(msg.indexOf("参与者:") + "参与者:".length());

            // 3. 按 "-" 分割，取第一部分即为 ID
            String[] parts = participantPart.split("-");
            String userid = "";
            if (parts.length > 0) {
                userid = parts[0].trim(); // 去除可能存在的空格
            }
            String imgTxDsc = "(" + bps.getUserName()
                    + bps.getUserId() + ")，于"
                    + ppmOutsourcingXklx.getCreatedate()
                    + "，发起外包需求名称:“" + ppmOutsourcingXklx.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
            InforTechService.pushTaskCommon(userid, "风险管理部经办人审核",
                    imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
            return msg;

        } catch (Exception e) {
            log.error("风险管理部经办人审核！", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional("defaultTx")
    public String resPerApprove(PpmOutsourcingXklx ppmOutsourcingXklx, BpsWorkFlowDto bps) {
        // 人力资源部负责人审批
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                try {
                    this.getBaseMapper().saveInforTechApply(ppmOutsourcingXklx);
                } catch (Exception e) {
                    String jsonBody = JSONObject.toJSONString(ppmOutsourcingXklx);
                    log.error("该审批已保存:{}",jsonBody);
                }
                // 审批结束 推送采购系统
                sendTodoPurchaseXklx(ppmOutsourcingXklx);
                ppmOutsourcingXklx.setFlag(2);
            } else {
                ppmOutsourcingXklx.setFlag(0);
            }
            this.getBaseMapper().updatePpmOutsourcingXklx(ppmOutsourcingXklx);
            param.setmPara(mPara);
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc(bps.getActivityName());
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, null, bps);
            //删除待办
            PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto =
                    ppmAppWorkflowInfoService.queryDetailForTaskId(bps.getTaskId());
            if (ppmAppWorkflowInfoDto != null) {
                InforTechService.deleteTaskXy(ppmAppWorkflowInfoDto);
            }
            return CamundaUtils.findNextTaskInfo(taskBean, null);
//            //推送待办
//            String participantPart = msg.substring(msg.indexOf("参与者:") + "参与者:".length());
//
//            // 3. 按 "-" 分割，取第一部分即为 ID
//            String[] parts = participantPart.split("-");
//            String userid = "";
//            if (parts.length > 0) {
//                userid = parts[0].trim(); // 去除可能存在的空格
//            }
//            String imgTxDsc = "(" + bps.getUserName()
//                    + bps.getUserId() + ")，于"
//                    + ppmOutsourcingXklx.getCreatedate()
//                    + "，发起外包需求名称:“" + ppmOutsourcingXklx.getOutreqName() + "”的信息科技外包立项审批流程，请及时登录手机工作平台处理。";
//            InforTechService.pushTaskCommon(userid, "人力资源部负责人审批",
//                    imgTxDsc, bps.getProcessInstId(), taskBean.getTaskList().get(0).get("taskId"));
//            return msg;

        } catch (Exception e) {
            log.error("风险管理部经办人审核！", e);
            throw new BizException(e);
        }

    }

    public void sendTodoPurchaseXklx(PpmOutsourcingXklx info) {
        LambdaQueryWrapper<OssObjectPo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OssObjectPo::getBizKey, info.getOutReqNum());
        queryWrapper.eq(OssObjectPo::getFlag, "1");
        List<OssObjectPo> ossObjectPos = this.ossObjectService.list(queryWrapper);

        Date date = new Date();
        String IOBP_ = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(date) + "_" + (int) ((Math.random() * 9 + 1) * 100000);

        JSONObject addBody = new JSONObject();
        addBody.put("transCode", "xjzginitiateproject");
        addBody.put("serviceCode", "12002010064");
        addBody.put("serviceScene", "07");
        addBody.put("orgConsumerSeqNo", IOBP_);
        addBody.put("tranDate", DateUtil.dateToStr(date, "yyyy-MM-dd HH:MM:ss"));
        addBody.put("tranTime", DateUtil.dateToStr(date, "yyyy-MM-dd HH:MM:ss"));
        addBody.put("hDBnkInrSysNm", "cpms");
        addBody.put("hDBnkOutSysIp", IpUtil.getLocalIp());
        addBody.put("svcNm", "cpms-epsin-sidecar");
        addBody.put("url", "/cpms7end/esb/app/ananymous/1.0/xjzg-initiate-project?app_code=4761y7ty58qw65q1ee37te356q18e1q9");
        addBody.put("txnCd", "");
        addBody.put("sendTp", "POST");
        addBody.put("psThgFlg", "true");
        String s = "IOBP_" + new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date()) + "_" + (int) ((Math.random() * 9 + 1) * 100000);
        addBody.put("consumerSeqNo", s);
        addBody.put("consumerId", "cfit");
        addBody.put("xmbh", info.getOutReqNum());
        addBody.put("xmmc", info.getOutreqName());
        addBody.put("fileName", ossObjectPos.get(0).getOriginName());
        addBody.put("objId", ossObjectPos.get(0).getObjectId());
        addBody.put("xmjbr", info.getCreateid());
        String jsonBody = addBody.toJSONString();
        log.info("----------------链路过星云推送采购报文{}", jsonBody);
        try {
            String res = HttpUtil.createPost(url).body(jsonBody).execute().body();
            JSONObject resJson = JSON.parseObject(res);
            log.info("推送采购管理系统接口返回报文:{}", resJson);
            Object retCode = resJson.get("state") + "";
            if (Integer.parseInt(String.valueOf(retCode)) == 1) {
                log.info("推送成功");
            } else {
                throw new BizRuntimeException("调用失败");
            }

        } catch (HttpException e) {
            log.error("推送采购接口异常：{}", e);
            throw new RuntimeException(e);
        }


    }

    @Override
    public void queryList(PpmOutsourcingXklx info, Pager pager, CommOperatorDoLoginDto user) {
        Map<String, Object> map = com.work.ppm.commons.utils.StringUtil.objectTOMap(info, PpmOutsourcingXklx.class);
        // 机构数据控制
        if (getBoolean(user)) {
            List<CommDepartment> dep = this.getBaseMapper().getMechanism(user.getDeptId());
            map.put("deptId", dep);
        }
        if (!StringUtils.isEmpty(info.getAlctionDepid())) {
            List<CommDepartment> dep = this.getBaseMapper().getMechanism(info.getAlctionDepid());
            map.put("alctionDepid", dep);
        }
        List<PpmOutsourcingXklx> xklxes = myBatisDao.selectListT(INFORTECHOUT, map, "selectXklxPager");
        myBatisDao.selectPager(INFORTECHOUT, map, pager, "selectXklxPager");
        List<PpmOutsourcingXklx> Xklxs = new ArrayList<>();
        if (CollectionUtils.isEmpty(pager.getList())) {
            return;
        }
        for (Object o : pager.getList()) {
            PpmOutsourcingXklx ppmOutsourcingXklx = (PpmOutsourcingXklx) o;
            List<InforOutsourcFile> fileList = this.queryInforOutsourcFile(ppmOutsourcingXklx.getOutReqNum());
            ppmOutsourcingXklx.setFileList(fileList);
            Xklxs.add(ppmOutsourcingXklx);
        }
        pager.setList(Xklxs);
        pager.setTotal(xklxes.size());
    }

    public boolean getBoolean(CommOperatorDoLoginDto user) {
        List<String> roles = user.getRoles().stream().map(ImRoleDoLoginDto::getRoleId).collect(Collectors.toList());
        List<String> role = Arrays.asList("JRKJ_HANDING", "FXGL_HANDING");
        List<CommDepartment> deps = this.getBaseMapper().selectDirector(user.getUserid());
        return deps.isEmpty() && (roles.stream().noneMatch(role::contains));
    }

    @Override
    public List<PpmFlowLogDto> getSignDetail(PpmFlowLog info) {
        Map<String, Object> map = JsonUtil.objectTOMap(info, PpmFlowLog.class);
        List<PpmFlowLogInfo> list = null;
        if ("2".equals(info.getConclusion())) {
            list = this.getBaseMapper().getHistoryLogs(map);
        } else {
            list = this.getBaseMapper().getLogs(map);
        }
        List<PpmFlowLogDto> dtoList = JsonUtil.jsonToList(list, PpmFlowLogDto.class);
        return dtoList;
    }

    @Override
    public PpmOutsourcingXklx getPhoneDb(PpmOutsourcingXklx info) {
        if (!Strings.isNullOrEmpty(info.getInstanceid())) {
            PpmOutsourcingXklx ppmOutsourcingXklx = this.getBaseMapper().getPhoneDb(info);
            if (Objects.nonNull(ppmOutsourcingXklx)) {
                LambdaQueryWrapper<OssObjectPo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(OssObjectPo::getBizKey, ppmOutsourcingXklx.getOutReqNum());
                queryWrapper.eq(OssObjectPo::getFlag, "1");
                List<OssObjectPo> ossObjectPos = this.ossObjectService.list(queryWrapper);
                ppmOutsourcingXklx.setObjectPosList(ossObjectPos);
            }
            return ppmOutsourcingXklx;
        }
        return null;
    }

    @Override
    public String phoneApprove(PhoneApproveDto info, BpsWorkFlowDto bps) {
        PpmOutsourcingXklx xklx = new PpmOutsourcingXklx();
        xklx.setInstanceid(info.getInstanceid());
        PpmOutsourcingXklx infoDat = this.getDb(xklx);
        if ("申请人发起".equals(bps.getActivityName())) {
            // 发起申请
            return outLeaveStart(infoDat, bps);
        } else if ("申请部门负责人审批".equals(bps.getActivityName())) {
            // 申请部门负责人审批
            return managerApprove(infoDat, bps);
        } else if ("用工部门负责人审批".equals(bps.getActivityName())) {
            // 用工部门负责人审批
            return hiringDepApprove(infoDat, bps);
        } else if ("金融科技部经办人审核".equals(bps.getActivityName())) {
            // 金融科技部经办人审核
            return financeApprove(infoDat, bps);
        } else if ("风险管理部经办人审核".equals(bps.getActivityName())) {
            // 风险管理部经办人审核
            return riskHandApprove(infoDat, bps);
        } else if ("风险管理部负责人审批".equals(bps.getActivityName())) {
            // 风险管理部负责人审批
            return riskHandApprove(infoDat, bps);
        } else if ("人力资源部负责人审批".equals(bps.getActivityName())) {
            // 人力资源部负责人审批
            return resPerApprove(infoDat, bps);
        } else {
            throw new BizRuntimeException("节点名称不能为空");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePpmOutsourcingXklx(PpmOutsourcingXklx accessapply) {
        if (accessapply.getInstanceid() == null) {
            return 0;
        }
        int count = this.getBaseMapper().deleteByInstanceid(accessapply);
        if (StrUtils.isNotBlank(accessapply.getInstanceid())) {
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(accessapply.getInstanceid());
            CamundaUtils.terminateProcess(accessapply.getInstanceid());
        }
        return count;
    }


    // 对象所有字段不能为空
    private boolean checkAllFieldNotNull(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            Field[] files = obj.getClass().getDeclaredFields();
            for (Field file : files) {
                file.setAccessible(true);
                // 获取字段值
                Object value = file.get(obj);
                if (value == null) {
                    return false;
                }
                if (value instanceof String && ((String) value).trim().isEmpty()) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
