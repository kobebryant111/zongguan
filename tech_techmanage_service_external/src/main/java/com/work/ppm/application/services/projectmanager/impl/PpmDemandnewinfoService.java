package com.work.ppm.application.services.projectmanager.impl;

import com.work.mercury.log.annotation.MercuryLog;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.projectmanager.DemandBackDto;
import com.work.ppm.application.dto.projectmanager.DemandFlowLogDto;
import com.work.ppm.application.dto.projectmanager.DemandInfoDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandApplyDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandQueueDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandnewcostsharesDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandnewinfoDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandnewinfoFjhDto;
import com.work.ppm.application.dto.projectmanager.PpmFhspbmpzDto;
import com.work.ppm.application.dto.systemmanager.AapSystemResponsibilityDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.model.projectmanager.PpmDemandnewinfo;
import com.work.ppm.application.model.projectmanager.ext.PpmDemandnewinfoInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.quartz.core.ExecutorManager;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import com.work.ppm.application.services.projectmanager.IPpmDemandApplyService;
import com.work.ppm.application.services.projectmanager.IPpmDemandQueueService;
import com.work.ppm.application.services.projectmanager.IPpmDemandnewcostsharesService;
import com.work.ppm.application.services.projectmanager.IPpmDemandnewinfoService;
import com.work.ppm.application.services.projectmanager.IPpmFhspbmpzService;
import com.work.ppm.application.services.systemmanager.IAapSystemResponsibilityService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.esb.EsbAction;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.FileUtil;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 奋进号业务意向实现类
 *
 */
@Slf4j
@MercuryLog
@Service("ppmDemandnewinfoService")
public class PpmDemandnewinfoService extends BaseDaoSupper implements IPpmDemandnewinfoService {
    /**
     * 实体类路径
     */
    private static String PPMDEMANDNEWINFO = "com.work.ppm.application.model.projectmanager.PpmDemandnewinfo";
    /**
     * 流程实例名称
     */
    private static final String FLOW_NAME = "com.work.ppm.bps.newFhywyxFjh";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 员工接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 业务字典接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 系统接口
     */
    @Autowired
    private IAapSystemService aapSystemService;
    /**
     * 机构接口
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;
    /**
     * 分摊接口
     */
    @Autowired
    private IPpmDemandnewcostsharesService ppmDemandnewcostsharesService;
    /**
     * 流程日志接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;
    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;
    /**
     * 主流程信息接口
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;
    /**
     * 流程列队
     */
    @Autowired
    private IPpmDemandQueueService ppmDemandQueueService;
    /**
     * 老的业务意向接口
     */
    @Autowired
    private IPpmDemandApplyService ppmDemandApplyService;
    /**
     * 分行审批部门配置接口
     */
    @Autowired
    private IPpmFhspbmpzService ppmFhspbmpzService;

    /**
     * 系统负责人接口
     */
    @Autowired
    private IAapSystemResponsibilityService aapSystemResponsibilityService;


    @Override
    public void queryPager(PpmDemandnewinfoDto ppmDemandnewinfoDto, Pager pager) {
        if (StringUtil.isNotBlank(ppmDemandnewinfoDto.getStatus())) {
            String[] ss = ppmDemandnewinfoDto.getStatus().split(",");
            StringJoiner joiner = new StringJoiner("','", "'", "'");
            for (int i = ss.length - 1; i >= 0; i--) {
                joiner.add(ss[i]);
            }
            ppmDemandnewinfoDto.setStatus(joiner.toString());
        }
        // 规模
        if (StringUtil.isNotBlank(ppmDemandnewinfoDto.getProscale())) {
            String[] ss = ppmDemandnewinfoDto.getProscale().split(",");
            StringJoiner joiner = new StringJoiner("','", "'", "'");
            for (int i = ss.length - 1; i >= 0; i--) {
                joiner.add(ss[i]);
            }
            ppmDemandnewinfoDto.setProscale(joiner.toString());
        }
        // 时间处理
        if (!StringUtil.isNotBlank(ppmDemandnewinfoDto.getStartTime())) {
            Date date = new Date();
            String dateFomat = "yyyy-MM-dd";
            String startTime = DateUtil.dateToStr(DateUtil.intervalMonth(date, -1), dateFomat);
            ppmDemandnewinfoDto.setStartTime(startTime);
            ppmDemandnewinfoDto.setEndTime(DateUtil.dateToStr(date, dateFomat));
        }
        Map<String, Object> map = JsonUtil.objectTOMap(ppmDemandnewinfoDto, PpmDemandnewinfoDto.class);
        myBatisDao.selectPager(PPMDEMANDNEWINFO, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmDemandnewinfo(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
        PpmDemandnewinfo ppmDemandnewinfo = JsonUtil.jsonToClass(ppmDemandnewinfoDto, PpmDemandnewinfo.class);
        Long id = getNextSequence(PpmDemandnewinfo.class);
        // 如果需要请set 主键id
        ppmDemandnewinfo.setId(id);
        myBatisDao.executionPojo(PPMDEMANDNEWINFO, ppmDemandnewinfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmDemandnewinfoDto queryDetail(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
        PpmDemandnewinfo ppmDemandnewinfo = JsonUtil.jsonToClass(ppmDemandnewinfoDto, PpmDemandnewinfo.class);
        PpmDemandnewinfoInfo data = myBatisDao.selectClass(PPMDEMANDNEWINFO, ppmDemandnewinfo,
                "queryDetail");
        if (null == data) {
            throw new BizException(ApiCode.FAIL, "查询数据异常");
        }
        PpmDemandnewinfoDto item = JsonUtil.jsonToClass(data, PpmDemandnewinfoDto
                .class);
        // 业务项目经理
        item.setBusiuserName(item.getBusiuserName() + "(" + item.getBusiuserid() + ")");
        // 意向发起人
        item.setDemandUserName(item.getDemandUserName() + "(" + item.getDemanduserid() + ")");
        // 产品经理
        item.setProdeuctManagerUserName(item.getProdeuctManagerUserName() + "(" + item.getProdeuctmanageruserid() +
                ")");
        // 发起人机构
        CommEmployee employee = commEmployeeService.getCommEmployeeInfo(item.getBusiuserid());
        item.setOrgId(employee.getCompid() + "");
        // 分摊
        List<PpmDemandnewcostsharesDto> sharess = ppmDemandnewcostsharesService.getList(item.getDemandno());
        item.setSharesList(sharess);
        // 附件
        List<PpmDocumentDto> documentDtos = ppmDocumentService.getList(item.getDemandno());

        item.setDocumentList(documentDtos);
        return item;
    }

    @Override
    public int updatePpmDemandnewinfo(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
        PpmDemandnewinfo ppmDemandnewinfo = JsonUtil.jsonToClass(ppmDemandnewinfoDto, PpmDemandnewinfo.class);
        return myBatisDao.executionPojo(PPMDEMANDNEWINFO, ppmDemandnewinfo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmDemandnewinfo(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
        PpmDemandnewinfo ppmDemandnewinfo = JsonUtil.jsonToClass(ppmDemandnewinfoDto, PpmDemandnewinfo.class);
        return myBatisDao.executionPojo(PPMDEMANDNEWINFO, ppmDemandnewinfo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    @Transactional("defaultTx")
    public String createDemandneFlow(DemandInfoDto demandInfoDto) {
        // 不等于3 说明不是分行业务意向
        if (!"3".equals(demandInfoDto.getRdMode())) {
            return null;
        }
        // 奋进号id是否重复
        PpmDemandnewinfo dem = new PpmDemandnewinfo();
        dem.setIssueid(demandInfoDto.getId() + "");
        dem.setIsdel("0");
        List<PpmDemandnewinfo> list = myBatisDao.selectListT(PPMDEMANDNEWINFO, dem, "getList");
        if (null != list && list.size() > 0) {
            throw new BizException(ApiCode.FAIL, "奋进号【issueId】重复");
        }
        demandInfoDto.setYwzjl(demandInfoDto.getBranchBankManager());
        // 生成分行意向编号
        demandInfoDto.setDemandno(CodeGenerater.generate("FHYX", 14, "yyyy"));
        // 分行研发二级部经理
        CommOrganizationDto orgitem = commOrganizationService.queryFHIdforUserId(demandInfoDto.getDemanduserid());

        if (null == orgitem) {
            throw new BizException(ApiCode.FAIL, "分行研发二级部经理未配置");
        }

        PpmFhspbmpzDto rditem = ppmFhspbmpzService.queryDetail(orgitem.getCompid() + "");
        Map<String, String> map = BpsDto.getInstance().setStatus("-1")
                .setUsers(rditem.getFhyfjl()).build();

        String xid = "";
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        try {
            bps.setUserId(demandInfoDto.getDemanduserid());
            bps.setFlowUrl("newFhywyxFjh");
            bps.setProcessDefName(FLOW_NAME);
            bps.setProcessInstDesc("分行业务意向发起");
            bps.setApplydesc(bps.getProcessInstDesc());
            bps.setConclusion("01");
            bps.setRelaData(map);
            bps.setProcessInstName(demandInfoDto.getDemandname());
            xid = BpsUtil.transactionBegin(bps.getUserId());
            String instanceId = BpsUtil.createProcessAndFinishFirst(bps, xid);
            demandInfoDto.setInstanceid(instanceId);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            log.warn("分行业务意向发起,流程创建完成，{}", bps);
            BpsUtil.flowLog(bps);
            // 保存信息
            saveDemandInfo(demandInfoDto);
        } catch (Exception e) {
            log.error("流程创建失败", e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程创建失败");
        }
        log.info("bps:{}", BpsUtil.queryProcessInfo(bps));
        return demandInfoDto.getDemandno();
    }

    @Override
    public ApiResult preliminaryReviewDemandne(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            // 业务数据先执行
            demandInfoDto.setInstanceid(bps.getProcessInstId());
            this.updatePpmDemandnewinfo(demandInfoDto);
            PpmDemandnewinfoDto detail = this.queryDetail(demandInfoDto);

            Map<String, String> map = BpsDto.getInstance().setStatus("-1")
                    .setUsers(detail.getYwzjl()).build();

            bps.setRelaData(map);
            bps.setApplydesc("研发二级部经理意向初审");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult busGeneralMsgApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 退回
            if ("02".equals(bps.getConclusion())) {
                this.backFlow(bps);
                result.setMessage("流程已结束");
                result.setStatus(ApiCode.SUCCESS);
                return result;
            }
            demandInfoDto.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto pd = this.queryDetail(demandInfoDto);
            // 涉及采购时验证
            if("02".equals(pd.getIssjwbcgxq())) {
                AssertUtil.notNull(demandInfoDto.getYwfghz(), "[Ywfghz]不能为空");
            }

            // 更新
            this.updatePpmDemandnewinfo(demandInfoDto);

            Map<String, String> map;
            // 判断是否涉及外包采购 02:涉及外包采购，01：则不涉及
            if ("02".equals(pd.getIssjwbcgxq())) {
                // 分行合规总经理审批
                CommOrganizationDto orgitem = commOrganizationService
                        .queryFHIdforUserId(pd.getBusiuserid());
                PpmFhspbmpzDto fhItem = ppmFhspbmpzService.queryDetail(orgitem.getCompid() + "");
                map = BpsDto.getInstance()
                        .setUsers(fhItem.getHgbzjl()).setStatus("-1").build();
            } else {
                demandInfoDto.setInstanceid(bps.getProcessInstId());
                PpmDemandnewinfoDto detail = this.queryDetail(demandInfoDto);
                // 01 不涉及外包采购
                // 科技总经理审批
                map = BpsDto.getInstance()
                        .setUsers(detail.getFhKjzjl()).setStatus("02").build();
            }
            bps.setApplydesc("业务总经理意向审批");
            bps.setRelaData(map);
            // 通过
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);

        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult complianceGeneralMsgApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            demandInfoDto.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto detail = this.queryDetail(demandInfoDto);
            // 提取工号
            String userId = detail.getBusiuserid();
            // 分行人力资源部总经理审批
            CommOrganizationDto orgitem = commOrganizationService
                    .queryFHIdforUserId(userId);
            PpmFhspbmpzDto fh = ppmFhspbmpzService.queryDetail(orgitem.getCompid() + "");
            Map<String, String> map = BpsDto.getInstance().setStatus("-1")
                    .setUsers(fh.getRlzybzjl()).build();
            bps.setRelaData(map);
            bps.setApplydesc("分行合规总经理审批");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult humanResourcesApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            // 科技总经理审批
            demandInfoDto.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto detail = this.queryDetail(demandInfoDto);
            Map<String, String> map = BpsDto.getInstance().setStatus("-1")
                    .setUsers(detail.getFhKjzjl()).build();
            bps.setRelaData(map);
            bps.setApplydesc("分行人力资源部总经理审批");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult techGMApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            // 业务数据先执行
            demandInfoDto.setInstanceid(bps.getProcessInstId());
//            this.updatePpmDemandnewinfo(demandInfoDto);
            PpmDemandnewinfoDto pditem = this.queryDetail(demandInfoDto);
            Map<String, String> map = null;
            // 判断是否涉及外包采购 02:涉及外包采购，01：则不涉及
            //  04: 小型，05：微型
            if ("02".equals(pditem.getIssjwbcgxq())) {
                // 分行分管行长审批
                map = BpsDto.getInstance()//YWFGHZ
                        .setUsers(pditem.getYwfghz()).setStatus("-1").build();
            } else if ("01".equals(pditem.getIssjwbcgxq())
                    && "04".equals(pditem.getProscale())) {
                // 不涉及外包采购并且规模小型
                // 01 不涉及外包采购
                // 分行管理部审核
                String deptid = eosDictEntryService.getTypeidForDetail("NEW_FH_BUS_YX", "1").getDictname();
                String badge = UsersUtil.getTwoWorkManager(deptid).getBadge();
                map = BpsDto.getInstance()
                        .setUsers(badge).setStatus("02").build();
            } else if ("01".equals(pditem.getIssjwbcgxq())
                    && "05".equals(pditem.getProscale())) {
                // 不涉及外包采购并且规模微型 架构部三新审核
                String deptids = eosDictEntryService.
                        getTypeidForDetail("JGBFHZZYF", "JGB").getDictname();
                map = BpsDto.getInstance()
                        .setUsers(deptids).setStatus("03").build();
            }
            bps.setRelaData(map);
            bps.setApplydesc("科技总经理审批");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult branchBankApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            demandInfoDto.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto pditem = this.queryDetail(demandInfoDto);
            Map<String, String> map = null;
            // 判断是否涉及外包采购 02:涉及外包采购，01：则不涉及
            //  04: 小型，05：微型
            if ("04".equals(pditem.getProscale())) {
                // 分行管理部审核
                String deptid = eosDictEntryService.getTypeidForDetail("NEW_FH_BUS_YX", "1").getDictname();
                String badge = UsersUtil.getTwoWorkManager(deptid).getBadge();
                // 规模小型
                map = BpsDto.getInstance()
                        .setUsers(badge).setStatus("-1").build();
            } else if ("02".equals(pditem.getIssjwbcgxq())
                    && "05".equals(pditem.getProscale())) {
                // 架构部三新审核
                String deptids = eosDictEntryService
                        .getTypeidForDetail("JGBFHZZYF", "JGB").getDictname();
                // 规模微型
                map = BpsDto.getInstance()
                        .setUsers(deptids).setStatus("02").build();
            }
            bps.setApplydesc("分行分管行长审批");
            bps.setRelaData(map);
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult branchMgtApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            // 架构部三新审核
            String deptids = eosDictEntryService
                    .getTypeidForDetail("JGBFHZZYF", "JGB").getDictname();
            Map<String, String> map;
            map = BpsDto.getInstance()
                    .setUsers(deptids).setStatus("-1").build();
            bps.setRelaData(map);
            bps.setApplydesc("分行管理部审核");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult frameworkDeptApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            PpmDemandnewinfoDto item = new PpmDemandnewinfoDto();
            item.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto ditem = this.queryDetail(item);

            Map<String, String> map;
            // 是否涉及三新
            // 1:涉及三新，0：不涉及
            if ("1".equals(demandInfoDto.getSfThreenew())) {
                // 架构部分管副总
                String userId = commEmployeeService.getAreDeptManager();
                map = BpsDto.getInstance()
                        .setUsers(userId).setStatus("-1").build();
                demandInfoDto.setInstanceid(bps.getProcessInstId());
            } else {
                // 如果涉及采购系统
                if ("02".equals(ditem.getIssjwbcgxq())) {
                    ExecutorManager.getExecutor().submit(()->{
                        try {
                            this.inputParamToCGSystem(ditem);
                        } catch (Exception e) {
                            log.error("【新应用】采购系统数据异常", e);
                        }
                    });
                }
                // 结束流程
                map = BpsDto.getInstance().setStatus("02").build();
                // 流程结束 进入队列
                this.fishQueue(ditem);
            }
            this.updatePpmDemandnewinfo(demandInfoDto);
            bps.setApplydesc("架构部三新审核");
            bps.setRelaData(map);
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult frameworkbrandeputyApprove(PpmDemandnewinfoDto demandInfoDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = demandInfoDto.getBps();
        String xid = "";
        try {
            // 默认通过
            if ("02".equals(bps.getConclusion())) {
                // 退回
                backFlow(bps);
                result.success("流程已结束");
                return result;
            }
            PpmDemandnewinfoDto item = new PpmDemandnewinfoDto();
            item.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto ditem = this.queryDetail(item);
            // 流程结束 进入队列
            this.fishQueue(ditem);
            // 如果涉及采购系统
            if ("02".equals(ditem.getIssjwbcgxq())) {
                ExecutorManager.getExecutor().submit(()->{
                    try {
                        this.inputParamToCGSystem(ditem);
                    } catch (Exception e) {
                        log.error("【新应用】采购系统数据异常", e);
                    }
                });
            }
            Map<String, String> map;
            map = BpsDto.getInstance().setStatus("-1").build();
            bps.setRelaData(map);
            bps.setApplydesc("架构部分管副总审批");
            xid = BpsUtil.transactionBegin(bps.getUserId());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            log.error("[新应用] 流程流转异常", e);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "流程流转异常");
        }
        String msg = BpsUtil.queryProcessInfo(bps);
        result.success(msg);
        return result;
    }

    /**
     * 传递信息给采购系统
     *
     * @param info
     * @throws Exception
     */
    public void inputParamToCGSystem(PpmDemandnewinfoDto info) throws Exception {
        String objno = info.getDemandno();
        String objname = info.getDemandname();
        String objjbr = info.getBusiuserid();

        String root = eosDictEntryService.getTypeidForDetail
                ("FileuploadRootpath", "rootPath").getDictname();
        CharSequence se = objno;
        PpmDocumentDto document = ppmDocumentService.getDetail(se);
        String fileName = document.getDocname();
        String filePath = document.getStockpath();
        String documentno = document.getDocumentno();
        String docdomain = document.getDocdomain();
        String fileP = root + "/ppmtest" + filePath + docdomain;
        String fileBase = FileUtil.encodeBase64File(fileP);
        //获取接口地址      业务字典获取
        String url = eosDictEntryService.getTypeidForDetail
                ("XQSPWCDJCGXT", "CGURL").getDictname();
        String ywurl = eosDictEntryService.getTypeidForDetail
                ("PPM_URL", "1").getDictname();
        String sendutl = ywurl + "/com.work.ppm.project.prepares.newdemand.loginForKG.flow?md5="
                + CryptoUtil.digestByMD5(objjbr) + "&userid=" + objjbr + "&documentno=" + documentno;

        objno = URLEncoder.encode(objno, "utf-8");
        objname = URLEncoder.encode(objname, "utf-8");
        objjbr = URLEncoder.encode(objjbr, "utf-8");
        fileName = URLEncoder.encode(fileName, "utf-8");
        filePath =  URLEncoder.encode(sendutl,"utf-8");

        log.info("============objno=============" + objno);
        log.info("============objname=============" + objname);
        log.info("============objjbr=============" + objjbr);
        log.info("============fileName=============" + fileName);
        log.info("============filePath=============" + filePath);
        log.info("============fileBase=============" + fileBase);

        log.info("============传参数给采购系统开始=============");
        Map<String, String> map = new HashMap<>();
        map.put("method", "importKgxmxx");
        map.put("xmbh", objno);
        map.put("xmmc", objname);
        map.put("xmjbr", objjbr);
        map.put("fileName", fileName);
        map.put("filePath", filePath);
        map.put("fileBase", fileBase);
        String msg = HttpClient.sendPost(url, map);
        log.info("============传参数给采购系统结束=============");
        log.info("科技外包采购申请结束对接采购系统:" + msg);
    }

    /**
     * 结束流程
     *
     * @param dto 对象
     */
    private void fishQueue(PpmDemandnewinfoDto dto) {
        // 异步通知奋进号
        fishFjh(dto, "ok");
        PpmDemandnewinfoDto item = new PpmDemandnewinfoDto();
        item.setDemandno(dto.getDemandno());
        PpmDemandQueueDto queueDto = new PpmDemandQueueDto();
        queueDto.setDemandno(dto.getDemandno());
        queueDto.setDemandname(dto.getDemandname());
        queueDto.setSysno(dto.getMSysno());
        queueDto.setBusideptno(dto.getDemanddeptno());
        queueDto.setDeptno(dto.getTectdeptno());
        queueDto.setStatus("01");
        queueDto.setIsresearched("0");
        queueDto.setCreatetime(new Date());
        queueDto.setCreators(dto.getDemanduserid());
        queueDto.setIsdel(0);
        ppmDemandQueueService.addPpmDemandQueue(queueDto);
        // 老项目业务意向表
        PpmDemandApplyDto applyDto = JsonUtil.jsonToClass(dto, PpmDemandApplyDto.class);

        ppmDemandApplyService.addPpmDemandApply(applyDto);
    }

    /**
     * 流程结束通知奋进号
     *
     * @param ppmDemandnewinfoDto 对象
     * @param state               状态
     */
    private void fishFjh(PpmDemandnewinfoDto ppmDemandnewinfoDto, String state) {
        try {
            PpmDemandnewinfoFjhDto fjh = new PpmDemandnewinfoFjhDto();
            fjh.setApprovalResult(state);
            fjh.setIssueId(ppmDemandnewinfoDto.getIssueid());

            AapSystemResponsibilityDto aap = aapSystemResponsibilityService.
                    queryDetail(ppmDemandnewinfoDto.getMSysno());
            if (null != aap) {
                fjh.setTechnologyManager(aap.getKeji());
            }
            BigDecimal tecworkload = ppmDemandnewinfoDto.getTecworkload();
            String tecworkloadUnit = ppmDemandnewinfoDto.getTworkunit();
            if ("人月".equals(tecworkloadUnit)) {
                tecworkload = tecworkload.multiply(BigDecimal.valueOf(21));
            }
            fjh.setTechnologyWorkload(tecworkload.doubleValue());
            fjh.setTechnologyBudget(ppmDemandnewinfoDto.getTbudget().doubleValue());
            fjh.setBusinessBudget(ppmDemandnewinfoDto.getBbudget().doubleValue());

            BigDecimal sumworkload = ppmDemandnewinfoDto.getSumworkload();
            String sumworkloadUnit = ppmDemandnewinfoDto.getSumworkunit();
            if ("人月".equals(sumworkloadUnit)) {
                sumworkload = sumworkload.multiply(BigDecimal.valueOf(21));
            }
            fjh.setSumWorkload(sumworkload.doubleValue());
            //项目规模 GLOBAL_PROJECT_SCALE
            if ("01".equals(ppmDemandnewinfoDto.getProscale())) {
                fjh.setProjectScale("特大型");
            } else if ("02".equals(ppmDemandnewinfoDto.getProscale())) {
                fjh.setProjectScale("大型");
            } else if ("03".equals(ppmDemandnewinfoDto.getProscale())) {
                fjh.setProjectScale("中型");
            } else if ("04".equals(ppmDemandnewinfoDto.getProscale())) {
                fjh.setProjectScale("小型");
            } else if ("05".equals(ppmDemandnewinfoDto.getProscale())) {
                fjh.setProjectScale("微型");
            } else {
                fjh.setProjectScale("");
            }
            //是否大中型 02是  01否
            if ("02".equals(ppmDemandnewinfoDto.getIslarge())) {
                fjh.setIsLarge("1");
            } else if ("01".equals(ppmDemandnewinfoDto.getIslarge())) {
                fjh.setIsLarge("0");
            } else {
                fjh.setIsLarge("");
            }
            // 获取业务字典 奋进号地址
            String url = eosDictEntryService.getTypeidForDetail("NEWDEMAND", "01").getDictname();
            String jsonData = JsonUtil.toJSONString(fjh);
            log.warn("============流程结束调用奋进号开始:{}=============", jsonData);

            String response = HttpClient.post(url, jsonData);
            log.warn("============流程结束调用奋进号结束:{}=============", response);
            if (!"ok".equals(response)) {
                this.sendSms(ppmDemandnewinfoDto.getIssueid());
            }
        } catch (Exception e) {
            log.error("回传奋进号异常:", e);
            this.sendSms(ppmDemandnewinfoDto.getIssueid());
        }
    }


    @Transactional("defaultTx")
    @Override
    public ApiResult removeDemand(DemandBackDto demandBackDto) {
        ApiResult result = new ApiResult();
        if (!"0".equals(demandBackDto.getDelType())) {
            result.setStatus(ApiCode.FLOW_WITHDRAW_FAIL);
            result.setMessage("警告：当前删除类型不是新科管意向审批");
            return result;
        }
        if (!StringUtil.isNotBlank(demandBackDto.getId())
                && !"0".equals(demandBackDto.getId())) {
            result.setStatus(ApiCode.PARAMETER_ERROR);
            result.setMessage("ID为空或者ID值不正确");
            return result;
        }
        PpmDemandnewinfo info = new PpmDemandnewinfo();
        info.setIssueid(demandBackDto.getId());
        PpmDemandnewinfo item = myBatisDao.selectClass(PPMDEMANDNEWINFO, info, "sfWithdraw");
        if (null == item) {
            result.setStatus(ApiCode.FLOW_WITHDRAW_FAIL);
            result.setMessage("该流程不可撤回");
            return result;
        }
        // 删除流程
        BpsUtil.terminateProcessInstance(item.getInstanceid(), demandBackDto.getUserid());
        item.setIsdel("1");
        item.setIssueid(demandBackDto.getId());
        // 更新信息为删除
        myBatisDao.executionPojo(PPMDEMANDNEWINFO, item, IMyBatisDao.SQL.UPDATE, "updatePojo");
        result.setStatus(ApiCode.SUCCESS);
        result.setMessage("流程删除成功");
        return result;
    }

    /**
     * 发送短信
     *
     * @param issueid 奋进号编号
     */
    private void sendSms(String issueid) {
        String content = "【" + issueid + "】意向审批信息变更失败，请知晓。";
        String users = eosDictEntryService
                .getTypeidForDetail("MOBILEOASUBMITUSERS", "02")
                .getDictname();
        EsbAction esb = new EsbAction();
        esb.sendSMSText(users, content);
    }

    /**
     * 流程退回
     *
     * @param bps 对象
     */
    @Transactional("defaultTx")
    private void backFlow(BpsWorkFlowDto bps) {
        try {
            // 删除流程
            BpsUtil.terminateProcessInstance(bps);
            PpmProjectFlow pfItem = ppmProjectFlowService.queryDetail(bps.getProcessInstId());
            pfItem.setLastmodtime(new Date());
            pfItem.setFlowstatus("已删除");
            pfItem.setClr("");
            ppmProjectFlowService.updatePpmProjectFlow(pfItem);
            PpmFlowLog log = new PpmFlowLog();
            log.setFlowcaseno(bps.getProcessInstId());
            log.setIsfalse("01");
            log.setIsdel(0);
            ppmFlowLogService.deletePpmFlowLog(log);
            PpmFlowLog log2 = new PpmFlowLog();
            log2.setCreatetime(new Date());
            log2.setUserid(bps.getUserId());
            log2.setCreators(bps.getUserId());
            log2.setConclusion("90");
            log2.setFlowcaseno(bps.getProcessInstId());
            log2.setIsdel(0);
            log2.setSuggest(bps.getSuggest());
            ppmFlowLogService.addPpmFlowLog(log2);
            PpmDemandnewinfoDto ppmDemandnewinfo = new PpmDemandnewinfoDto();
            ppmDemandnewinfo.setInstanceid(bps.getProcessInstId());
            PpmDemandnewinfoDto ditem = this.queryDetail(ppmDemandnewinfo);
            this.fishFjh(ditem, "0");
            ditem.setIsdel("1");
            // 删除流程
            this.updatePpmDemandnewinfo(ditem);
        } catch (Exception e) {
            log.error("【新应用】流程退回异常", e);
        }
    }

    /**
     * 保存业务意向数据
     *
     * @param demandInfoDto 对象
     */
    private void saveDemandInfo(DemandInfoDto demandInfoDto) {
        PpmDemandnewinfoDto info = JsonUtil.jsonToClass(demandInfoDto, PpmDemandnewinfoDto.class);
        info.setYwzjl(demandInfoDto.getYwzjl());    // 业务总经理
        info.setDemanddeptno(demandInfoDto.getDemanddeptno());  // 牵头业务部门
        info.setInstanceid(demandInfoDto.getInstanceid());      // 流程id
        info.setIssueid(demandInfoDto.getId() + "");    // 奋进号业务意向id
        info.setDemandno(demandInfoDto.getDemandno());  // 意向编号
        info.setDemandname(demandInfoDto.getDemandname());  // 意向名称
        info.setDemanduserid(demandInfoDto.getDemanduserid());  // 需求发起人
        info.setBringupdate(demandInfoDto.getCreatetime());     //提出时间
        info.setBusiuserid(demandInfoDto.getBusiuserid());      // 业务项目经理
        info.setProdeuctmanageruserid(demandInfoDto.getProdeuctmanageruserid());    //产品经理
        info.setApps(demandInfoDto.getApps());          //级联-具体应用
        info.setTypes(demandInfoDto.getTypes());        //意向分类
        info.setStatus(demandInfoDto.getStatus());      //状态
        info.setIsnew(demandInfoDto.getIsnew());//是否新系统
        //dmandnewinfo.setForusers(info.getForusers());//适用用户
        info.setIncometype(demandInfoDto.getIncometype());//收益类型
        info.setMainincomes(demandInfoDto.getMainincomes());//级联-具体收益
        info.setMajorprojectreason(demandInfoDto.getMajorprojectreason());//级联-紧急/重点原因
        info.setDemanddesc(demandInfoDto.getDemanddesc());//意向描述
        info.setBusiexpect(demandInfoDto.getSysbusiexpect());//系统期望上线日期
        info.setTecmanageruserid(demandInfoDto.getTecmanageruserid());//科技二级部经理
        info.setTecuserid(demandInfoDto.getTecuserid());//主系统研发负责人
        info.setMSysno(demandInfoDto.getSysno());//主系统编号
        info.setBusideptno(demandInfoDto.getSysbusdepno());//系统业务主管部门
        info.setTectdeptno(demandInfoDto.getSystecdepno());//系统科技主管部门
        info.setCbudget(demandInfoDto.getCbudget());//采购预算
        info.setYwfgzjl(demandInfoDto.getYwfgzjl());//业务 分管总经理
        info.setYwfghz(demandInfoDto.getYwfghz());//业务 分管行长
        info.setTecworkload(demandInfoDto.getTecworkload()); // 科技团队工作量
        info.setBusiworkload(demandInfoDto.getBusiworkload());  // //业务团队工作量
        info.setIsresearch("0");// 是否可行性分析
        if ("业务新功能".equals(demandInfoDto.getApptypes())) {
            info.setApptypes("01");//应用类型
        } else if ("业务优化".equals(demandInfoDto.getApptypes())) {
            info.setApptypes("02");//应用类型
        } else if ("科技驱动".equals(demandInfoDto.getApptypes())) {
            info.setApptypes("03");//应用类型
        } else {
            info.setApptypes("");
        }
        //优先类型  GLOBAL_PROJECT_3PRIORITY
        if ("战略".equals(info.getPriority())) {
            info.setPriority("01");
        } else if ("效益".equals(info.getPriority())) {
            info.setPriority("02");
        } else if ("监管".equals(info.getPriority())) {
            info.setPriority("03");
        } else if ("普通".equals(info.getPriority())) {
            info.setPriority("04");
        } else {
            info.setPriority("");
        }
        //是否重点项目
        if ("全行重点项目".equals(demandInfoDto.getMajorproject())) {
            info.setMajorproject("01");
        } else if ("条线（分行）重点项目".equals(demandInfoDto.getMajorproject())
                || "条线(分行)重点项目".equals(demandInfoDto.getMajorproject())) {
            info.setMajorproject("02");
        } else if ("普通项目".equals(demandInfoDto.getMajorproject())) {
            info.setMajorproject("03");
        } else {
            info.setMajorproject("");
        }
        //是否大中型 02是  01否
        info.setIslarge("01");

        //是否涉及包含电子印章
        if ("1".equals(demandInfoDto.getIssjbhdzyz())) {
            info.setIssjbhdzyz("02");
        } else if ("0".equals(demandInfoDto.getIssjbhdzyz())) {
            info.setIssjbhdzyz("01");
        } else {
            info.setIssjbhdzyz("");
        }
        //是否涉及外包采购需求
        if ("1".equals(demandInfoDto.getIssjwbcgxq())) {
            info.setIssjwbcgxq("02");
        } else if ("0".equals(demandInfoDto.getIssjwbcgxq())) {
            info.setIssjwbcgxq("01");
        } else {
            info.setIssjwbcgxq("");
        }
        // 需求来源
        String type = DemandInfoDto.DemandSourceEnum.getTypeVal(demandInfoDto.getDemandsource());
        info.setDemandsource(type);
        // 计算 预算信息的工作量
        info = budgetWorkload(demandInfoDto, info);
        info.setIsdel("0");
        info.setIsywkjfzjl(demandInfoDto.getIsywkjfzjl());
        info.setIsftywjl(demandInfoDto.getIsftywjl());
        info.setYwzjl(demandInfoDto.getYwzjl());
        this.addPpmDemandnewinfo(info);
        // 分摊
        List<PpmDemandnewcostsharesDto> dcsBatchs = new ArrayList<>();
        List<PpmDemandnewcostsharesDto> dcsList = demandInfoDto.getDemandCostShares();
        for (PpmDemandnewcostsharesDto ii : dcsList) {
            PpmDemandnewcostsharesDto cost = new PpmDemandnewcostsharesDto();
            cost.setDemandno(info.getDemandno());
            cost.setProportion(ii.getProportion());
            String deptno = commDepartmentService.getDepNameForId(ii.getDeptname());
            if (StringUtil.isNotBlank(deptno)) {
                cost.setDeptno(deptno);
                cost.setOrgdep("dep");
            } else {
                deptno = commOrganizationService.queryDateil(ii.getDeptname()).getCompid() + "";
                cost.setDeptno(deptno);
                cost.setOrgdep("org");
            }
            cost.setManageruser(ii.getManageruser());
            dcsBatchs.add(cost);
        }
        // 批量添加
        ppmDemandnewcostsharesService.addPpmDemandnewcostshares(dcsBatchs);
        List<PpmFlowLog> flows = new ArrayList<>();
        // 流程日志
        List<DemandFlowLogDto> dflList = demandInfoDto.getDemandFlowLogList();
        if (null != dflList && !dflList.isEmpty()) {
            PpmFlowLog log = null;
            for (DemandFlowLogDto ii : dflList) {
                log = new PpmFlowLog();
                log.setCreatetime(ii.getCreatetime());
                log.setUserid(ii.getUserid());
                log.setCreators(ii.getUserid());
                String deptId = commEmployeeService.getDeptNo(ii.getUserid());
                log.setDeptno(deptId);
                if ("0".equals(ii.getConclusion())) {
                    log.setConclusion("01");
                } else {
                    log.setConclusion("02");
                }
                log.setSuggest(ii.getSuggest());
                log.setFlowcaseno(demandInfoDto.getInstanceid());
                log.setExamine(demandInfoDto.getCreatetime());
                log.setApplydesc(ii.getActivename());
                //log.setFlowdraftno(flowdraftno);
                log.setIsdel(0);
                flows.add(log);
            }
            ppmFlowLogService.batchAddFlowLog(flows);
        }
        // 附件
        ppmDocumentService.addDocment(demandInfoDto.getDemandFileList(),
                demandInfoDto.getDemandno(), demandInfoDto.getDemanduserid());
    }

    /**
     * 计算预算信息的工作量
     *
     * @param demandInfoDto 对象
     * @return
     */
    @Override
    public PpmDemandnewinfoDto budgetWorkload(DemandInfoDto demandInfoDto, PpmDemandnewinfoDto info) {
        if (null == info) {
            info = JsonUtil.jsonToClass(demandInfoDto, PpmDemandnewinfoDto.class);
        }
        // 总工作量
        BigDecimal sumtotal = new BigDecimal(0);
        sumtotal = sumtotal.add(info.getTecworkload()).add(info.getBusiworkload());
        if (sumtotal.doubleValue() > 21) {
            sumtotal = sumtotal.divide(BigDecimal.valueOf(21.0), 2, BigDecimal.ROUND_HALF_UP);
            info.setSumworkunit("人月");
            info.setSumworkload(sumtotal);
            info.setSumbudget(sumtotal.multiply(BigDecimal.valueOf(2.5)));
            if (sumtotal.doubleValue() <= 1) {
                info.setProscale("05");
            } else if (sumtotal.doubleValue() > 1) {
                info.setProscale("04");
            }
        } else {
            info.setSumworkload(sumtotal);
            info.setSumworkunit("人天");
            info.setSumbudget(sumtotal.divide(BigDecimal.valueOf(21), 2,
                    BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(2.5)));
            info.setProscale("05");
        }
        if (info.getTecworkload() == null && info.getBusiworkload() == null) {
            info.setSumbudget(new BigDecimal(0));
        }
        //科技团队工作量
        BigDecimal tecworkload = info.getTecworkload();
        BigDecimal tbudget =
                tecworkload.divide(
                        BigDecimal.valueOf(21), 2,
                        BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(2.5)).setScale(2, BigDecimal.ROUND_HALF_UP);
        info.setTbudget(tbudget);//科技人力预算
        if (tecworkload.doubleValue() > 21) {
            info.setTworkunit("人月");
            info.setTecworkload(tecworkload.divide(BigDecimal.valueOf(21.0), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            info.setTecworkload(info.getTecworkload());
            info.setTworkunit("人天");
        }
        // 业务团队工作量
        BigDecimal busiworkload = info.getBusiworkload();//业务团队工作量
        BigDecimal bbudget = busiworkload.divide(BigDecimal.valueOf(21),
                2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(2.5)).setScale(2, BigDecimal.ROUND_HALF_UP);
        info.setBbudget(bbudget);//业务人力预算
        if (busiworkload.doubleValue() > 21) {
            info.setSworkunit("人月");
            info.setBusiworkload(
                    busiworkload.divide(BigDecimal.valueOf(21.0), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            info.setBusiworkload(info.getBusiworkload());
            info.setSworkunit("人天");
        }
        return info;
    }

    @Override
    public CommEmployeeDto getBranchGeneralManager(String depno) {
        CommDepartmentDto dep = commDepartmentService.queryParentDeptId(depno);
        CommDepartmentDto depart = commDepartmentService.queryFhKjbManager(dep.getCompid());
        return UsersUtil.getTwoWorkManager(depart.getDepid() + "");
    }

    @Override
    public PpmDemandnewinfoDto queryInstanceidByIssueId(String issueid) {
        PpmDemandnewinfo de = new PpmDemandnewinfo();
        de.setIssueid(issueid);
        PpmDemandnewinfo item = myBatisDao.selectClass(PPMDEMANDNEWINFO, de,"queryByIssueId");
        PpmDemandnewinfoDto dto = JsonUtil.jsonToClass(item, PpmDemandnewinfoDto.class);
        return dto;
    }

    @Override
    public PpmDemandnewinfoDto queryMobileDetail(PpmDemandnewinfoDto ppmDemandnewinfoDto) {
        PpmDemandnewinfoDto item = this.queryDetail(ppmDemandnewinfoDto);
        // 是否涉及采购
        if("02".equals(item.getIssjwbcgxq())) {
            // 业务分管行长
            CommDepartmentDto depItem = commDepartmentService.queryDeptIdForCompId(item.getOrgId());
            List<CommTreeDto> list = commDepartmentService.queryAuth(depItem.getDepid() + "", null,null);
            item.setTreeList(list);
        }
        // 手机端
        String mobilePath = ppmDemandnewinfoDto.getDir()
                + ppmDemandnewinfoDto.getInstanceid()
                + ppmDemandnewinfoDto.getSeparator()
                ;
        item.getDocumentList().stream().forEach(ii -> {
            String fileName = ii.getStockpath()
                    .substring(0, ii.getStockpath().lastIndexOf("/"));
            String fileName1 = StringUtil.isNotBlank(ii.getTag2()) ? ii.getTag2() : ii.getDocname();
            String sourceFilePath = eosDictEntryService
                    .getTypeidForDetail("NEW_MOBILE_FILE","fileurl").getDictname()
                    + "?stockPath=" + ii.getStockpath()
                    + "&docname=" + fileName1;
            ii.setSourceFilePath(sourceFilePath);
            ii.setMobilePath(mobilePath + fileName1);
        });
        return item;
    }

    @Override
    public PpmDemandnewinfoDto getdemandByProjectno(String projectno) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectno", projectno);
        PpmDemandnewinfo data = myBatisDao.selectClass(PPMDEMANDNEWINFO, map,"getdemandByProjectno");
        PpmDemandnewinfoDto item = JsonUtil.jsonToClass(data, PpmDemandnewinfoDto.class);
        return item;
    }

    @Override
    public PpmDemandnewinfoDto getdemandByDemandno(String demandno) {
        Map<String, Object> map = new HashMap<>();
        map.put("demandno", demandno);
        PpmDemandnewinfo data = myBatisDao.selectClass(PPMDEMANDNEWINFO, map,"getdemandByDemandno");
        PpmDemandnewinfoDto item = JsonUtil.jsonToClass(data, PpmDemandnewinfoDto.class);
        return item;
    }
}
