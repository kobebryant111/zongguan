package com.work.ppm.application.services.integratedmanager.impl;

import com.google.common.collect.Lists;
import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmZjAuthorityApply;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityApplyService;
import com.work.ppm.application.services.integratedmanager.IPpmZjAuthorityService;
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
import com.work.ppm.commons.utils.UsersUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 闸机权限申请表 接口实现类
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmZjAuthorityApplyService")
public class PpmZjAuthorityApplyService extends BaseDaoSupper implements IPpmZjAuthorityApplyService {
    //实体类路径
    private static String PPMZJAUTHORITYAPPLY = "com.work.ppm.application.model.integratedmanager.PpmZjAuthorityApply";  // 流程定义ID
    // 流程编码
    private static final String FLOW_ID = "zjqxlc";
    //流程名称
    private static final String FLOW_TITLE = "闸机权限申请";
    //数据接口
    private final IMyBatisDao myBatisDao;
    //行员服务
    private final ICommEmployeeService commEmployeeService;
    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    //闸机权限服务
    private final IPpmZjAuthorityService ppmZjAuthorityService;

    private final IEosDictEntryService eosDictEntryService;

    @Override
    public void queryPager(PpmZjAuthorityApplyDto authorityApplyDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(authorityApplyDto, PpmZjAuthorityApplyDto.class);
        myBatisDao.selectPager(PPMZJAUTHORITYAPPLY, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmZjAuthorityApply(PpmZjAuthorityApplyDto authorityApplyDto) {
        PpmZjAuthorityApply ppmZjAuthorityApply = JsonUtil.jsonToClass(authorityApplyDto, PpmZjAuthorityApply.class);
        myBatisDao.executionPojo(PPMZJAUTHORITYAPPLY, ppmZjAuthorityApply, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmZjAuthorityApplyDto queryDetail(PpmZjAuthorityApplyDto authorityApplyDto) {
        PpmZjAuthorityApply ppmZjAuthorityApply = JsonUtil.jsonToClass(authorityApplyDto, PpmZjAuthorityApply.class);
        PpmZjAuthorityApply data = myBatisDao.selectClass(PPMZJAUTHORITYAPPLY, ppmZjAuthorityApply, "queryDetail");
        PpmZjAuthorityApplyDto item = JsonUtil.jsonToClass(data, PpmZjAuthorityApplyDto.class);
        if (!ObjectUtils.isEmpty(item)) {
            PpmZjAuthorityDto ppmZjAuthorityDto = new PpmZjAuthorityDto();
            ppmZjAuthorityDto.setInstanceid(authorityApplyDto.getInstanceid());
            item.setAuthorities(ppmZjAuthorityService.queryListOther(ppmZjAuthorityDto));
            String ppmZjId = item.getPpmZjId();
            List<EosDictEntry> entries = eosDictEntryService.getTypeidForList("FLOOR_PERMINSSIONS");
            List<String> arrays = new ArrayList<>();
            entries.forEach(obj ->{
                if (ppmZjId.contains(obj.getDictname())){
                    arrays.add(obj.getDictname());
                }
            });
            item.setPpmZjIdArrays(arrays);
        }
        return item;
    }

    @Override
    public int updatePpmZjAuthorityApply(PpmZjAuthorityApplyDto authorityApplyDto) {
        PpmZjAuthorityApply ppmZjAuthorityApply = JsonUtil.jsonToClass(authorityApplyDto, PpmZjAuthorityApply.class);
        return myBatisDao.executionPojo(PPMZJAUTHORITYAPPLY, ppmZjAuthorityApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePpmZjAuthorityApply(PpmZjAuthorityApplyDto authorityApplyDto) {
        // 删除申请信息
        PpmZjAuthorityApply ppmZjAuthorityApply = JsonUtil.jsonToClass(authorityApplyDto, PpmZjAuthorityApply.class);
        int count = myBatisDao.executionPojo(PPMZJAUTHORITYAPPLY, ppmZjAuthorityApply, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
        //清理权限数据
        PpmZjAuthorityDto authorityDto = new PpmZjAuthorityDto();
        authorityDto.setInstanceid(authorityApplyDto.getInstanceid());
        ppmZjAuthorityService.deletePpmZjAuthority(authorityDto);
        // 删除流程
        if (StrUtils.isNotBlank(authorityApplyDto.getInstanceid())) {
            // 删除流程相关数据
            CamundaUtils.delProjectFlow(authorityApplyDto.getInstanceid());
            CamundaUtils.terminateProcess(authorityApplyDto.getInstanceid());
        }
        return count;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PpmZjAuthorityApplyDto daftPpmZjAuthorityApply(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps) {
        if (null != applyDto.getId()) {
            this.updatePpmZjAuthorityApply(applyDto);
            ppmZjAuthorityService.addPpmZjAuthorityInfo(applyDto);
            return applyDto;
        }
        try {
            ProcessBean processBean;
            // 添加外包准入流程记录
            Long key = super.getNextSequence(PpmZjAuthorityApply.class);
            applyDto.setTitle(bps.getProcessInstName());
            applyDto.setId(key);
            applyDto.setFlowid(FLOW_ID);
            applyDto.setCreateid(bps.getUserId());
            applyDto.setCreatedate(new Date());
            applyDto.setFlowflag(0);
            this.addPpmZjAuthorityApply(applyDto);

            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(applyDto.getId()));
            // 开始并提交流程
            processBean = workFlowHttpClientService.startProcess(processInfo);
            // 回填工作流实例ID
            applyDto.setInstanceid(processBean.getProcessInstanceId());
            this.updatePpmZjAuthorityApply(applyDto);
            // 添加闸机权限
            ppmZjAuthorityService.addPpmZjAuthorityInfo(applyDto);
            // 日志处理
            bps.setProjectNo(String.valueOf(applyDto.getId()));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setApplydesc("闸机权限申请发起");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "闸机权限申请");
            bps.setProcessInstDesc(FLOW_TITLE);
            bps.setSuggest("提交申请");
            bps.setToDB(false);
            CamundaUtils.addFlowLog(bps, null);
            return applyDto;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String apply(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps) {
        ProcessBean processBean = null;
        try {
            //继续流转到主持工作的二级部经理
            CommEmployeeDto workManager = UsersUtil.getTwoWorkManager(bps.getDepId());
            // 下一步审批人
            if (StringUtils.isBlank(workManager.getBadge())) {
                throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无二级部经理");
            }
            // 判断流程实例是否存在，不存在说明是初始提交
            if (StringUtils.isBlank(applyDto.getInstanceid())) {
                // 添加外包准入流程记录
                Long key = super.getNextSequence(PpmZjAuthorityApply.class);
                applyDto.setTitle(bps.getProcessInstName());
                applyDto.setId(key);
                applyDto.setFlowid(FLOW_ID);
                applyDto.setCreateid(bps.getUserId());
                applyDto.setCreatedate(new Date());
                applyDto.setFlowflag(0);

//                //按照“,”分割，循环插入申请机房的权限
//                String ppmZjIdBackup = applyDto.getPpmZjId();//创建备份PpmZjId
//                String[] ppmZjIds = applyDto.getPpmZjId().split(",");
//                for (int i = 0; i < ppmZjIds.length; i++) {
//                    applyDto.setPpmZjId(ppmZjIds[i]);
//                    this.addPpmZjAuthorityApply(applyDto);
//                }
//                applyDto.setPpmZjId(ppmZjIdBackup);//备份恢复

                this.addPpmZjAuthorityApply(applyDto);

                // 流程实例参数设置
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
                processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
                processInfo.setUserId(bps.getUserId());// 提交人id
                processInfo.setStartUserId(bps.getUserId());// 流程发起人id
                processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
                processInfo.setBusinessKey(String.valueOf(applyDto.getId()));
                // 流程参数
                Map mPara = new HashMap();
                mPara.put("userId", workManager.getBadge());
                processInfo.setmPara(mPara);// 流程变量
                // 开始并提交流程
                processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
                // 回填流程实例编码
                applyDto.setInstanceid(processBean.getProcessInstanceId());
                this.updatePpmZjAuthorityApply(applyDto);
                // 添加闸机权限
                //ppmZjAuthorityService.addPpmZjAuthorityInfo(applyDto);//原方法

                //将ppmZjId拆分，循环添加闸机权限   现方法
//                String ppmZjIdBackup = applyDto.getPpmZjId();//创建备份PpmZjId
                String[] ppmZjIds = applyDto.getPpmZjId().split(",");
                for (int i = 0; i < ppmZjIds.length; i++) {
                    applyDto.setPpmZjId(ppmZjIds[i]);
                    ppmZjAuthorityService.addPpmZjAuthorityInfo(applyDto);
                }

                // 日志处理
                bps.setProjectNo(String.valueOf(applyDto.getId()));
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setApplydesc("闸机权限申请发起");
                bps.setRelaData(mPara);
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(processBean.getProcessInstanceId());
                bps.setActivityInstId(processBean.getCurrentActivityId());
                bps.setProcessInstName("【" + bps.getUserName() + DateUtil.dateToNoBar(new Date()) + "】" + "闸机权限申请");
                bps.setProcessInstDesc(FLOW_TITLE);
                bps.setSuggest("提交申请");
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean, null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            } else {
                this.updatePpmZjAuthorityApply(applyDto);
                // 添加闸机权限  原方法
                //ppmZjAuthorityService.addPpmZjAuthorityInfo(applyDto);

                //将ppmZjId拆分，循环添加闸机权限   现方法
//                String ppmZjIdBackup = applyDto.getPpmZjId();//创建备份PpmZjId
                String[] ppmZjIds = applyDto.getPpmZjId().split(",");
                PpmZjAuthorityDto authorityDto = new PpmZjAuthorityDto();
                authorityDto.setInstanceid(applyDto.getInstanceid());
                ppmZjAuthorityService.deletePpmZjAuthority(authorityDto);
                for (int i = 0; i < ppmZjIds.length; i++) {
                    applyDto.setPpmZjId(ppmZjIds[i]);
                    ppmZjAuthorityService.addPpmZjAuthorityInfo(applyDto);
                }

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
                // 日志处理
                bps.setProcessDefName(FLOW_ID);
                bps.setProcessInstId(applyDto.getInstanceid());
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(mPara);
                bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
                bps.setSuggest("重新提交");
                bps.setApplydesc("闸机权限申请发起");
                bps.setProcessInstDesc(FLOW_TITLE);
                // 获取流程信息
                TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceid());
                CamundaUtils.addFlowLog(bps, taskBean);
                CamundaExtUtils.saveNextTaskInfo(taskBean, null,bps);
                // 返回下一节点信息
                return CamundaUtils.findNextTaskInfo(taskBean, null);
            }
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("闸机权限申请发起流程失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String twoManagerAudit(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            List<String> msgIds = Lists.newArrayList();
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                CommEmployeeDto commEmployeeDto = new CommEmployeeDto();
                //永赢租赁金融科技部-总经理室
                commEmployeeDto.setDepid(80000053);
                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
                        .stream().map(CommEmployeeDto::getBadge).collect(Collectors.toList());

                StringJoiner joiner = new StringJoiner(BusinessUtil.REGEX_COMMA);
                msgIds.forEach(joiner::add);

                //继续流转到主持工作的一级部经理
//                CommEmployeeDto workManager = UsersUtil.getTopWorkManager(bps.getDepId());
//                // 下一步审批人
//                if (StringUtils.isBlank(workManager.getBadge())) {
//                    throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无一级部经理");
//                }
//                // 查询一部下当前存在的行员
//                CommEmployeeDto commEmployeeDto = CommEmployeeDto.builder()
//                        .depid(workManager.getDepid()).build();
//                msgIds = commEmployeeService.getEmployeeLists(commEmployeeDto)
//                        .stream().map(item -> item.getBadge()).collect(Collectors.toList());
//                List<PostInfo> userList = new ArrayList<>();
//                PostInfo postInfo;
//                for (String userId : msgIds) {
//                    postInfo = new PostInfo();
//                    postInfo.setType("user");
//                    postInfo.setUserId(userId);
//                    userList.add(postInfo);
//                }
//                mPara.put("userList", userList);// 下一步审批人
//                param.setmPara(mPara);// 流程变量
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceid());
            bps.setApplydesc("二级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceid());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]),bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, msgIds.toArray(new String[msgIds.size()]));
        } catch (Exception e) {
            log.error("二级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String topManagerAudit(PpmZjAuthorityApplyDto applyDto, BpsWorkFlowDto bps) {
        try {
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
                // 调整闸机权限申请数据
                applyDto.setFlowflag(1);
                applyDto.setCompletedate(new Date());
                this.updatePpmZjAuthorityApply(applyDto);
                //调整闸机权限
                PpmZjAuthorityDto ppmZjAuthorityDto = new PpmZjAuthorityDto();
                ppmZjAuthorityDto.setInstanceid(applyDto.getInstanceid());
                List<PpmZjAuthorityDto> authorities = ppmZjAuthorityService.queryList(ppmZjAuthorityDto);

                // 同步处理闸机人员权限
                for (PpmZjAuthorityDto authority : authorities) {
                    authority.setFlag(1);
                    ppmZjAuthorityService.updatePpmZjAuthority(authority);
                }

                //获取权限列表信息
                List powerlist = new ArrayList();
                for (int i = 0; i < authorities.size(); i++) {
                    powerlist.add(authorities.get(i).getPpmZjId());
                }
                Set powerset = new HashSet(powerlist);//去重后的权限列表信息
                List powerlistNew = new ArrayList(powerset);
                dealInfo(powerlistNew,authorities);
//                for (int i = 0; i < powerlistNew.size(); i++) {
//                    List<PpmZjAuthorityDto> authoritiesBackup = new ArrayList<PpmZjAuthorityDto>();
//                    for (PpmZjAuthorityDto authority : authorities) {
//                        if (authority.getPpmZjId().equals(powerlistNew.get(i).toString())){
//                            authoritiesBackup.add(authority);
//                        }
//                    }
//                    // 同步权限数据到闸机系统
//                    ppmZjAuthorityService.addAuthToZjServer(authoritiesBackup);
//                    authoritiesBackup.clear();
//                }
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(applyDto.getInstanceid());
            bps.setApplydesc("一级部经理审批");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(applyDto.getInstanceid());
            CamundaUtils.addFlowLog(bps, taskBean);
            CamundaExtUtils.saveNextTaskInfo(taskBean, null,bps);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("一级部经理审批失败：{}", e);
            throw new BizException(e);
        }
    }

    public void dealInfo(List powerlistNew,List<PpmZjAuthorityDto> authorities){
        for (int i = 0; i < powerlistNew.size(); i++) {
            List<PpmZjAuthorityDto> authoritiesBackup = new ArrayList<PpmZjAuthorityDto>();
            for (PpmZjAuthorityDto authority : authorities) {
                if (authority.getPpmZjId().equals(powerlistNew.get(i).toString())){
                    authoritiesBackup.add(authority);
                }
            }
            // 同步权限数据到闸机系统
            ppmZjAuthorityService.addAuthToZjServer(authoritiesBackup);
            authoritiesBackup.clear();
        }
    }
}
