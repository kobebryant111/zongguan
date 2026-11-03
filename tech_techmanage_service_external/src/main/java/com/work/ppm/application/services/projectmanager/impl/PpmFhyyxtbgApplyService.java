package com.work.ppm.application.services.projectmanager.impl;

import com.isprint.am.xmlrpc.clientcore.AuthResultTO;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiConfigureDto;
import com.work.ppm.application.dto.datamanager.PpmPwdShebeiDto;
import com.work.ppm.application.dto.flowmanager.BpsDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineOrgareaDto;
import com.work.ppm.application.dto.onlinemanager.PpmProductionDayDto;
import com.work.ppm.application.dto.onlinemanager.PpmProductionReviewDto;
import com.work.ppm.application.dto.othermanager.PpmDocumentDto;
import com.work.ppm.application.dto.projectmanager.AapSystemDto;
import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgApplyDto;
import com.work.ppm.application.dto.projectmanager.PpmFhyyxtbgSystemDto;
import com.work.ppm.application.dto.projectmanager.PpmPwdApplyDto;
import com.work.ppm.application.dto.projectmanager.PpmPwdHqmodelDto;
import com.work.ppm.application.dto.projectmanager.SyncPatchObjectDto;
import com.work.ppm.application.dto.systemmanager.AapSystemResponsibilityDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.model.projectmanager.PpmFhyyxtbgApply;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.core.ExecutorManager;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiConfigureService;
import com.work.ppm.application.services.datamanager.IPpmPwdShebeiService;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.onlinemanager.IPpmOnlineOrgareaService;
import com.work.ppm.application.services.onlinemanager.IPpmProductionDayService;
import com.work.ppm.application.services.onlinemanager.IPpmProductionReviewService;
import com.work.ppm.application.services.othermanager.IPpmDocumentService;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import com.work.ppm.application.services.projectmanager.IPpmFhyyxtbgApplyService;
import com.work.ppm.application.services.projectmanager.IPpmFhyyxtbgSystemService;
import com.work.ppm.application.services.projectmanager.IPpmPwdApplyService;
import com.work.ppm.application.services.projectmanager.IPpmPwdHqmodelService;
import com.work.ppm.application.services.systemmanager.IAapSystemResponsibilityService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.lang.other.UCMGetPasswordClient;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.HttpClient;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
  * 分行项目变更
  */
@Slf4j
@Service("ppmFhyyxtbgApplyService")
public class PpmFhyyxtbgApplyService extends BaseDaoSupper implements IPpmFhyyxtbgApplyService,
        ICommonDeleteFlowService {
    /**
      * 实体类路径
      */
    private static String PPMFHYYXTBGAPPLY = "com.work.ppm.application.model.projectmanager.PpmFhyyxtbgApply";
    /**
     * 流程实例名称
     */
    private static String FLOW_NAME="com.work.ppm.bps.newFhyyxtbg";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 业务字典接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 结构
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;
    /**
     * 系统接口
     */
    @Autowired
    private IAapSystemService aapSystemService;
    /**
     * 文档接口
     */
    @Autowired
    private IPpmDocumentService ppmDocumentService;
    /**
     * 密管设备接口
     */
    @Autowired
    private IPpmPwdShebeiService ppmPwdShebeiService;
    /**
     * 系统负责人信息接口
     */
    @Autowired
    private IAapSystemResponsibilityService aapSystemResponsibilityService;
    /**
     * 获取密码流程接口
     */
    @Autowired
    private IPpmPwdApplyService ppmPwdApplyService;
    /**
     * 项目流程接口
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;
    /**
     * 密码获取模式
     */
    @Autowired
    private IPpmPwdHqmodelService ppmPwdHqmodelService;
    /**
     * 投产窗口接口
     */
    @Autowired
    private IPpmProductionDayService ppmProductionDayService;
    /**
     * 流程日志接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;
    /**
     * 密码设备信息
     */
    @Autowired
    private IPpmPwdShebeiConfigureService ppmPwdShebeiConfigureService;
    /**
     * 分行系统
     */
    @Autowired
    private IPpmFhyyxtbgSystemService ppmFhyyxtbgSystemService;
    /**
     * 上线验证接口
     */
    @Autowired
    private IPpmProductionReviewService ppmProductionReviewService;
    /**
     * 上线相关
     */
    @Autowired
    private IPpmOnlineOrgareaService ppmOnlineOrgareaService;

    @Override
    public void queryPager(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmFhyyxtbgApplyDto,PpmFhyyxtbgApplyDto.class);
        myBatisDao.selectPager(PPMFHYYXTBGAPPLY, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmFhyyxtbgApply(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        PpmFhyyxtbgApply ppmFhyyxtbgApply = JsonUtil.jsonToClass(ppmFhyyxtbgApplyDto, PpmFhyyxtbgApply.class);
        Long id = getNextSequence(PpmFhyyxtbgApply.class);
        // 如果需要请set 主键id
        ppmFhyyxtbgApply.setId(id);
        myBatisDao.executionPojo(PPMFHYYXTBGAPPLY, ppmFhyyxtbgApply, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmFhyyxtbgApplyDto queryDetail(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        PpmFhyyxtbgApply ppmFhyyxtbgApply = JsonUtil.jsonToClass(ppmFhyyxtbgApplyDto, PpmFhyyxtbgApply.class);
        PpmFhyyxtbgApply data = myBatisDao.selectClass(PPMFHYYXTBGAPPLY, ppmFhyyxtbgApply,
        "queryDetail");
        PpmFhyyxtbgApplyDto item = JsonUtil.jsonToClass(data, PpmFhyyxtbgApplyDto
        .class);
        if(!StringUtil.isNotEmpty(item.getUsestarttime())) {
            item.setUsestarttime(new Date());
        }
        // 密码是否已到期
        if(StringUtil.isNotEmpty(item.getUseendtime())
                && new Date().after(item.getUseendtime())) {
            item.setIsoverdue(true);
        } else {
            item.setIsoverdue(false);
        }
        // 查询文档
        List<PpmDocumentDto> list = ppmDocumentService.getList(ppmFhyyxtbgApply.getInstanceid());
        item.setDocList(list);
        // 项目更新
        if("01".equals(item.getChangetype())) {
            List<PpmOnlineOrgareaDto> orgs = ppmOnlineOrgareaService.querySysNoForOnlineno(item.getOnlineno());
            StringJoiner joiner = new StringJoiner(",");
            for (int i = orgs.size() - 1; i >= 0; i--) {
                joiner.add(orgs.get(i).getSysNo());
            }
            List<PpmFhyyxtbgSystemDto> sys = ppmFhyyxtbgSystemService.queryDetail(data.getInstanceid());
            List<PpmFhyyxtbgSystemDto> systems = new ArrayList<>();
            systems.add(sys.get(0));
            item.setSystemList(sys);
            item.setProjectno(orgs.get(0).getProjectname());
        } else {
            // 关联系统
            if(StringUtil.isNotBlank(item.getmSysno())) {
                List<PpmFhyyxtbgSystemDto> sys = ppmFhyyxtbgSystemService.queryDetail(data.getInstanceid());
                if(null != sys && !sys.isEmpty()) {
                    List<PpmFhyyxtbgSystemDto> systems = new ArrayList<>();
                    systems.add(sys.get(0));
                    item.setSystemList(systems);
                    item.setmSysnoText(sys.get(0).getSysName());
                } else {
                    AapSystemDto aapSystemDto = new AapSystemDto();
                    aapSystemDto.setSystemId(item.getmSysno());
                    AapSystemDto systemDto = aapSystemService.queryDetail(aapSystemDto);
                    item.setmSysnoText(systemDto.getSysName());
                }
            }
        }


        // 密管
        List<PpmPwdShebeiDto> sbs = ppmPwdShebeiService.getList(item.getInstanceid());
        item.setPwdList(sbs);
        return item;
    }

    @Override
    public int updatePpmFhyyxtbgApply(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        PpmFhyyxtbgApply ppmFhyyxtbgApply = JsonUtil.jsonToClass(ppmFhyyxtbgApplyDto, PpmFhyyxtbgApply.class);
        return myBatisDao.executionPojo(PPMFHYYXTBGAPPLY, ppmFhyyxtbgApply, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmFhyyxtbgApply(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        PpmFhyyxtbgApply ppmFhyyxtbgApply = JsonUtil.jsonToClass(ppmFhyyxtbgApplyDto, PpmFhyyxtbgApply.class);
        return myBatisDao.executionPojo(PPMFHYYXTBGAPPLY, ppmFhyyxtbgApply, IMyBatisDao.SQL.DELETE,"deleteById");
    }
    @Transactional("defaultTx")
    @Override
    public ApiResult save(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmFhyyxtbgApplyDto.getBps();
        if(null == bps) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(ppmFhyyxtbgApplyDto.getApplyuser());
        String xid = "";
        try{
            // 添加
            if(!StringUtil.isNotBlank(bps.getProcessInstId())) {
                bps.setApplydesc("分行自研项目应用变更");
                // 项目更新
                if("01".equals(ppmFhyyxtbgApplyDto.getChangetype())) {
                    bps.setProcessInstName("项目更新_" +ppmFhyyxtbgApplyDto.getPatchtitle());
                    // 补丁更新
                } else if("02".equals(ppmFhyyxtbgApplyDto.getChangetype())) {
                    bps.setProcessInstName("补丁更新_" +ppmFhyyxtbgApplyDto.getPatchtitle());
                } else {        // 03 数据维护
                    bps.setProcessInstName("数据维护_" +ppmFhyyxtbgApplyDto.getPatchtitle());
                }
                bps.setProcessDefName(FLOW_NAME);
                bps.setFlowUrl("newFhyyxtbg");
                bps.setProcessInstDesc("分行自研项目应用变更");
                // 提取工号
                String userId = ppmFhyyxtbgApplyDto.getFhitmanager();
                userId = userId.substring(userId.indexOf("(") + 1, userId.lastIndexOf(")"));
                Map<String,String> map = BpsDto.getInstance().setStatus("01")
                        .setUsers(userId).build();
                bps.setRelaData(map);
                BpsUtil.transactionBegin(bps.getUserId());
                String instanceid = BpsUtil.createProcessInstance(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                ppmFhyyxtbgApplyDto.setInstanceid(instanceid);
                bps.setProcessInstId(instanceid);
                Date date = new Date();
                ppmFhyyxtbgApplyDto.setCreatedate(date);
                ppmFhyyxtbgApplyDto.setUpdatedate(date);
                String name = user.getOperatorname() + "(" + user.getUserid() + ")";
                ppmFhyyxtbgApplyDto.setUpdateuser(name);
                ppmFhyyxtbgApplyDto.setCreateuser(name);
                String applyCode = CodeGenerater.generate("FHYYXTBG",20,"yyyyMMdd");
                ppmFhyyxtbgApplyDto.setApplycode(applyCode);
                String projejctNo = CodeGenerater.generate("FHLX",12,"yyyy");
                ppmFhyyxtbgApplyDto.setProjectno(projejctNo);
                ppmFhyyxtbgApplyDto.setApplyuser(user.getOperatorname() + "(" + user.getUserid() + ")");
                ppmFhyyxtbgApplyDto.setApplyChangeTitle(bps.getProcessInstName());
                this.addPpmFhyyxtbgApply(ppmFhyyxtbgApplyDto);
                BpsUtil.flowLog(bps);
            } else { // 更新
                ppmFhyyxtbgApplyDto.setApplyuser(null);
                this.updatePpmFhyyxtbgApply(ppmFhyyxtbgApplyDto);
            }
            // 更新分行关联系统
            List<AapSystemResponsibilityDto> sysList = ppmFhyyxtbgApplyDto.getSysList();
            if(null != sysList && !sysList.isEmpty()) {
                BpsWorkFlowDto finalBps = bps;
                List<PpmFhyyxtbgSystemDto> systemList = new ArrayList<>();
                sysList.stream().forEach(item ->{
                    PpmFhyyxtbgSystemDto systemDto = new PpmFhyyxtbgSystemDto();
                    systemDto.setRefinstanceid(finalBps.getProcessInstId());
                    systemDto.setSubprojectno(ppmFhyyxtbgApplyDto.getApplycode());
                    systemDto.setSysno(item.getSystemId());
                    systemDto.setSysname(item.getSysName());
                    systemDto.setTecuserid(item.getPrincipala());
                    systemDto.setTecusername(item.getPrincipalaName() + "(" +item.getPrincipala() + ")");
                    systemList.add(systemDto);

                });
                ppmFhyyxtbgSystemService.addPpmFhyyxtbgSystem(systemList);
            }
            // 判断是否获取密码
            if("是".equals(ppmFhyyxtbgApplyDto.getIstopwd())) {
                if(null != ppmFhyyxtbgApplyDto.getPwdList()) {
                    ppmFhyyxtbgApplyDto.getPwdList().stream().forEach(ii -> {
                        ii.setApplyer(user.getOperatorname() + "(" + user.getUserid() + ")");

                    });
                    ppmPwdShebeiService.addSheBei(ppmFhyyxtbgApplyDto.getPwdList(), bps.getProcessInstId());
                }
            } else {
                // 删除 shebei
                ppmPwdShebeiService.deleteShebei(bps.getProcessInstId());
            }
            // 根据 文档编号更新文档信息
            if(StringUtil.isNotBlank(ppmFhyyxtbgApplyDto.getFiles())) {
                PpmDocumentDto doc = new PpmDocumentDto();
                doc.setObjectno(bps.getProcessInstId());
                doc.setDocumentnos(ppmFhyyxtbgApplyDto.getFiles());
                ppmDocumentService.updatePpmDocument(doc);
            }

            result.success(BpsUtil.queryProcessInfo(bps));
        }catch (Exception e){
            log.error("分行项目变更-草稿异常：", e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("流转失败");
            return result;
        }
        return result;
    }
    @Transactional("defaultTx")
    @Override
    public ApiResult liuzhuan(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user) {
        String xid = "";
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmFhyyxtbgApplyDto.getBps();
        if(null == bps) {
            bps = new BpsWorkFlowDto();
        }
        bps.setUserId(ppmFhyyxtbgApplyDto.getApplyuser());
        // 提取工号
        // 经理
        String userId = ppmFhyyxtbgApplyDto.getFhitmanager();
        userId = userId.substring(userId.indexOf("(") + 1, userId.lastIndexOf(")"));
        Map<String,String> map = BpsDto.getInstance().setStatus("01")
                .setUsers(userId).build();
        bps.setRelaData(map);
        bps.setApplydesc("分行自研项目应用变更");
        bps.setConclusion("01");
        try{
            // 添加并发起流程
            if(!StringUtil.isNotBlank(bps.getProcessInstId())) {
                // 项目更新
                if("01".equals(ppmFhyyxtbgApplyDto.getChangetype())) {
                    bps.setProcessInstName("项目更新_" +ppmFhyyxtbgApplyDto.getPatchtitle());
                    // 补丁更新
                } else if("02".equals(ppmFhyyxtbgApplyDto.getChangetype())) {
                    bps.setProcessInstName("补丁更新_" +ppmFhyyxtbgApplyDto.getPatchtitle());
                } else {        // 03 数据维护
                    bps.setProcessInstName("数据维护_" +ppmFhyyxtbgApplyDto.getPatchtitle());
                }

                bps.setProcessDefName(FLOW_NAME);
                bps.setFlowUrl("newFhyyxtbg");
                bps.setProcessInstDesc("分行自研项目应用变更");

                xid = BpsUtil.transactionBegin(bps.getUserId());
                String instanceid = BpsUtil.createProcessAndStart(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                ppmFhyyxtbgApplyDto.setInstanceid(instanceid);
                Date date = new Date();
                ppmFhyyxtbgApplyDto.setCreatedate(date);
                ppmFhyyxtbgApplyDto.setUpdatedate(date);
                String name = user.getOperatorname() + "(" + user.getUserid() + ")";
                ppmFhyyxtbgApplyDto.setUpdateuser(name);
                ppmFhyyxtbgApplyDto.setCreateuser(name);
                String applyCode = CodeGenerater.generate("FHYYXTBG",20,"yyyyMMdd");
                ppmFhyyxtbgApplyDto.setApplycode(applyCode);
                String projejctNo = CodeGenerater.generate("FHLX",12,"yyyy");
                ppmFhyyxtbgApplyDto.setProjectno(projejctNo);
                ppmFhyyxtbgApplyDto.setApplyuser(user.getOperatorname() + "(" + user.getUserid() + ")");
                ppmFhyyxtbgApplyDto.setApplyChangeTitle(bps.getProcessInstName());
                this.addPpmFhyyxtbgApply(ppmFhyyxtbgApplyDto);

            } else {    // 更新并发起流程
                ppmFhyyxtbgApplyDto.setProjectno("");
                ppmFhyyxtbgApplyDto.setApplyuser(null);
                this.updatePpmFhyyxtbgApply(ppmFhyyxtbgApplyDto);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            }
            BpsUtil.flowLog(bps);
            // 更新分行关联系统
            List<AapSystemResponsibilityDto> sysList = ppmFhyyxtbgApplyDto.getSysList();
            if(null != sysList && !sysList.isEmpty()) {
                BpsWorkFlowDto finalBps = bps;
                List<PpmFhyyxtbgSystemDto> systemList = new ArrayList<>();
                sysList.stream().forEach(item ->{
                    PpmFhyyxtbgSystemDto systemDto = new PpmFhyyxtbgSystemDto();
                    systemDto.setRefinstanceid(finalBps.getProcessInstId());
                    systemDto.setSubprojectno(ppmFhyyxtbgApplyDto.getApplycode());
                    systemDto.setSysno(item.getSystemId());
                    systemDto.setSysname(item.getSysName());
                    systemDto.setTecuserid(item.getPrincipala());
                    systemDto.setTecusername(item.getPrincipalaName() + "(" +item.getPrincipala() + ")");
                    systemList.add(systemDto);

                });
                ppmFhyyxtbgSystemService.addPpmFhyyxtbgSystem(systemList);
            }
            // 判断是否获取密码
            if("是".equals(ppmFhyyxtbgApplyDto.getIstopwd())) {
                if(null != ppmFhyyxtbgApplyDto.getPwdList()) {
                    ppmFhyyxtbgApplyDto.getPwdList().stream().forEach(ii -> {
                        ii.setApplyer(user.getOperatorname() + "(" + user.getUserid() + ")");
                    });
                    ppmPwdShebeiService.addSheBei(ppmFhyyxtbgApplyDto.getPwdList(), bps.getProcessInstId());
                }
            } else {
                // 删除 shebei
                ppmPwdShebeiService.deleteShebei(bps.getProcessInstId());
            }
            // 根据 文档编号更新文档信息
            if(StringUtil.isNotBlank(ppmFhyyxtbgApplyDto.getFiles())) {
                PpmDocumentDto doc = new PpmDocumentDto();
                doc.setObjectno(bps.getProcessInstId());
                doc.setDocumentnos(ppmFhyyxtbgApplyDto.getFiles());
                ppmDocumentService.updatePpmDocument(doc);
            }
            result.success(BpsUtil.queryProcessInfo(bps));
        }catch (Exception e) {
            log.error("分行项目变更-流转异常：", e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("流转失败");
            return result;
        }
        return result;
    }

    @Transactional("defaultTx")
    @Override
    public ApiResult liuzhuan2(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmFhyyxtbgApplyDto.getBps();
        String xid = "";
        try{
            bps.setUserId(ppmFhyyxtbgApplyDto.getApplyuser());
            bps.setApplydesc("分行研发推广部经理");
            // 退回
            if("02".equals(bps.getConclusion())) {
                Map<String,String> map = BpsDto.getInstance()
                        .setStatus("-1").build();
                bps.setRelaData(map);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            } else {
                ppmFhyyxtbgApplyDto.setInstanceid(bps.getProcessInstId());
                PpmFhyyxtbgApplyDto item = this.queryDetail(ppmFhyyxtbgApplyDto);
                // 提取工号
                String userId = item.getFhyybmanager();
                userId = userId.substring(userId.indexOf("(") + 1, userId.lastIndexOf(")"));
                Map<String,String> map = BpsDto.getInstance()
                        .setUsers(userId).setStatus("01")
                        .build();
                bps.setRelaData(map);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            }
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            log.error("分行项目变更-分行研发推广部经理：", e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("流转失败");
            return result;
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    public ApiResult liuzhuan3(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmFhyyxtbgApplyDto.getBps();
        String xid = "";
        try{
            bps.setUserId(ppmFhyyxtbgApplyDto.getApplyuser());
            bps.setApplydesc("分行金融科技部总经理");
            // 退回
            if("02".equals(bps.getConclusion())) {
                Map<String,String> map = BpsDto.getInstance()
                        .setStatus("-1").build();
                bps.setRelaData(map);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            } else {
                ppmFhyyxtbgApplyDto.setInstanceid(bps.getProcessInstId());
                PpmFhyyxtbgApplyDto item = this.queryDetail(ppmFhyyxtbgApplyDto);
                // 判断直接结束还是继续流转
                Map<String,String> map = null;
                if("03".equals(item.getChangetype())) {  // 结束
                    map = BpsDto.getInstance().setStatus("00")
                            .build();
                } else {    // 继续流转 主办人员办理
                    // 提取工号
                    String userId = item.getApplyuser();
                    userId = userId.substring(userId.indexOf("(") +1, userId.lastIndexOf(")"));
                    map = BpsDto.getInstance()
                            .setUsers(userId).setStatus("01")
                            .build();
                }

                bps.setRelaData(map);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            }
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            log.error("分行项目变更-分行金融科技部总经理：", e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("流转失败");
            return result;
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    public ApiResult liuzhuan4(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        ApiResult result = new ApiResult();
        BpsWorkFlowDto bps = ppmFhyyxtbgApplyDto.getBps();
        String xid = "";
        try{
            bps.setUserId(ppmFhyyxtbgApplyDto.getApplyuser());
            bps.setApplydesc("主办人员办理");
            List<AapSystemResponsibilityDto> sritem = ppmFhyyxtbgApplyDto.getSysList();
            // 退回
            if("02".equals(bps.getConclusion())) {
                Map<String,String> map = BpsDto.getInstance()
                        .setStatus("11").build();
                bps.setRelaData(map);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            } else {
                ppmFhyyxtbgApplyDto.setInstanceid(bps.getProcessInstId());
                PpmFhyyxtbgApplyDto applyDto = this.queryDetail(ppmFhyyxtbgApplyDto);

                AtomicBoolean falg = new AtomicBoolean(true);
                // 更新关联系统信息
                sritem.stream().forEach(item ->{
                    PpmFhyyxtbgSystemDto sysDto = new PpmFhyyxtbgSystemDto();
                    sysDto.setRefinstanceid(bps.getProcessInstId());
                    sysDto.setSysno(item.getSystemId());
                    sysDto.setSysname(item.getSysName());
                    sysDto.setTecuserid(item.getAdmina());
                    sysDto.setTecusername(item.getAdminaName());
                    sysDto.setChangestatus(item.getIschange());
                    ppmFhyyxtbgSystemService.updateFhyyxtbgSystem(sysDto);
                    if(!"1".equals(item.getIschange())) {
                        falg.set(false);
                    }
                });
                // 判断 是否有其他流程并且已经完成
                // 如果完成 则继续，否则等待
                // 是否发起上线验证，全部通过可发起，反之
                if("01".equals(applyDto.getChangetype()) && falg.get()) {
                    PpmProductionReviewDto review = new PpmProductionReviewDto();
                    review.setIsqbsx("1");
                    review.setOnlineno(applyDto.getOnlineno());
                    review.setProjectname(applyDto.getApplyChangeTitle());
                    review.setOnlineno(applyDto.getOnlineno());
                    review.setProductionno(applyDto.getApplycode());
                    ppmProductionReviewService.addPpmProductionReviewFlow(review,bps.getUserId());
                }

                Map<String,String> map = BpsDto.getInstance()
                        .setStatus("-1").build();
                bps.setRelaData(map);
                xid = BpsUtil.transactionBegin(bps.getUserId());
                BpsUtil.finshWorkItem(bps,xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
            }
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            log.error("分行项目变更-主办人员办理：", e);
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            result.failure("流转失败");
            return result;
        }
        result.success(BpsUtil.queryProcessInfo(bps));
        return result;
    }

    @Override
    public PpmFhyyxtbgApplyDto getProjectChangeInfo(String userid,String name, String deptId) {
        PpmFhyyxtbgApplyDto item = new PpmFhyyxtbgApplyDto();
        String orgs = eosDictEntryService.getTypeidForDetail("FHORG","org").getDictname();
        // 获取部门分管总经理/主持经理/部门经理层(二级部经理)
        CommDepartmentDto cdDto = commDepartmentService.queryManagerByDepid(deptId);
        item.setFhitmanager(cdDto.getEmpname() + "(" + cdDto.getBadge() + ")");
        item.setFhyybmanager(cdDto.getUsername() + "(" + cdDto.getUserid() + ")");

        CommDepartmentDto depitem = commDepartmentService.queryParentDeptId(deptId);
        CommOrganizationDto orgItem = commOrganizationService.queryDateil(depitem.getCompid());

        // 是否是外包管理员
        String[] orgsArray = orgs.split(",");
        for (int i = 0; i < orgsArray.length; i++) {
            if(orgsArray[i].equals(orgItem.getCompcode())) {
                item.setFlag(true);
            }
        }
        item.setCompcode(orgItem.getCompcode());
        item.setApplytime(new Date());
        item.setApplyuser(name + "(" + userid + ")");
        item.setDeptId(depitem.getDepid() + "");
        return item;
    }

    @Override
    public List<SyncPatchObjectDto> queryBugsList(String userId) {
        String sysIds=aapSystemService.getFZRSysno(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("sysNo", sysIds);
        map.put("offset", 0);   // //从0开始
        map.put("limit", 50);   // 取50条
        String url = eosDictEntryService.getTypeidForDetail(
                "FJHBUGURL","01").getDictname();
        String params = JsonUtil.toJSONString(map);
        log.info("============查询奋进号生产缺陷开始:{}=============",params);
        String data = HttpClient.post(url,params);
        log.info("====查询奋进号生产缺陷结束:{}==",data);
        Map<String,Object> maps =  JsonUtil.jsonToMap(data);
        if(Integer.valueOf(maps.get("total") + "") <= 0) {
            return null;
        }
        List<SyncPatchObjectDto> list = JsonUtil.jsonToList(maps.get("data"), SyncPatchObjectDto.class);
        // 过滤 不需要数据
        list =
                list.stream().filter(ii -> !"03".equals(ii.getSeverity()))
                        .filter(ii ->"3234".equals(ii.getStatus())).collect(Collectors.toList());
        // 业务字典 奋进号缺陷状态 FJHBUGSTATUS
        List<EosDictEntry> eosList = eosDictEntryService.getTypeidForList("FJHBUGSTATUS");
        // 严重程度
        List<EosDictEntry> eosList2 = eosDictEntryService.getTypeidForList("PPM_BUG_SERIOUS");
        // 剔除已经进入流程
        StringJoiner joiner = new StringJoiner("','","'","'");
        list.stream().forEach(ii -> {
            joiner.add(ii.getIssueId() + "");
            eosList.stream().forEach(tt -> {
                if(tt.getDictid().equals(ii.getStatus())) {
                    ii.setStatus(tt.getDictname());
                }
            });
            eosList2.stream().forEach(tt -> {
                if(tt.getDictid().equals(ii.getSeverity())) {
                    ii.setSeverity(tt.getDictname());
                }
            });

        });
        map.put("issueId", joiner.toString());
        List<PpmFhyyxtbgApply> fhList = myBatisDao.selectListT(PPMFHYYXTBGAPPLY,map, "getList");
        List<SyncPatchObjectDto> sps = new ArrayList<>();
        list.stream().forEach(ii->{
            Map<String,SyncPatchObjectDto> smap = new HashMap<>();
            //
            if(null != fhList && !fhList.isEmpty()) {
                for (int i = fhList.size() - 1; i >= 0; i--) {
                    if(ii.getIssueId().longValue() != Long.valueOf(fhList.get(i).getIssueId())) {
                        smap.put(ii.getIssueId()+"", ii);
                    }
                }
            } else {
                smap.put(ii.getIssueId()+"", ii);
            }

            smap.forEach((key,val)->{
                sps.add(val);
            });
        });
        return sps;
    }

    @Override
    public int getIsDayTime(Date date) {
        int type = DateUtil.isDaytime(date);
        return type;
    }

    @Override
    public ApiResult queryPasswordIs(String instanceid) {
        ApiResult result = new ApiResult();
        PpmFhyyxtbgApplyDto apply = new PpmFhyyxtbgApplyDto();
        apply.setInstanceid(instanceid);
        PpmFhyyxtbgApplyDto item = this.queryDetail(apply);
        // 当前时间
        long newDate = new Date().getTime();
        // 查询
        PpmPwdApplyDto applyDto = new PpmPwdApplyDto();
        applyDto.setRelateinstanceid(instanceid);
        PpmPwdApplyDto pwd = ppmPwdApplyService.queryDetail(applyDto);
        // 03 04 均可获取密码
        if(null != pwd) {
            result.setResults("03");
        } else {
            result.setResults("04");
        }
        // 如果结束不为空，直接不能获取
        if(null != item.getUseendtime() && item.getIsoverdue()) {
            result.setResults("01");
            result.success("查询成功");
            return result;
        }
        // 计算是否在获取密码范围内
        int changehourInt ;
        double changehour = Double.valueOf(item.getChangehour());
        changehourInt = (int) Math.ceil(changehour);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getPlanstarttime());//维护时间
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        long timestr1 = calendar.getTime().getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(item.getPlanstarttime());
        calendar1.add(Calendar.HOUR_OF_DAY, changehourInt);
        calendar1.add(Calendar.HOUR_OF_DAY, 1);
        long timestr2 =calendar1.getTime().getTime();
        //维护时间 -1h 到   维护时间+预计变更时长+1小时 时间范围内,直接获取密码
        if(newDate >= timestr1 && newDate <= timestr2){
            result.setResults("02");    // 需要进一步验证
        }else{
            result.setResults("01");    // 不能获取
        }

        result.success("查询成功");
        return result;
    }

    @Override
    public ApiResult queryPwdModel(String projectno) {
        ApiResult<Integer> result = new ApiResult();
        result.success("查询成功");
        PpmProjectFlowDto flow = ppmProjectFlowService.queryDetailForProjectno(projectno);
        if(null == flow) {
            result.setResults(1);
            return result;
        }
        PpmPwdHqmodelDto hq = new PpmPwdHqmodelDto();
        hq.setFlowid(flow.getFlowid());
        PpmPwdHqmodelDto hqItem = ppmPwdHqmodelService.queryDetail(hq);
        if(null == hqItem) {
            result.setResults(1);
            return result;
        }
        String spmodel = hqItem.getSpmodel();
        String gxrissp = hqItem.getGxrissp();
        if("1".equals(spmodel)) {
            result.setResults(1);
        } else if("2".equals(spmodel) && "否".equals(gxrissp)) {
            Date nowdate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowdate);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min =calendar.get(Calendar.MINUTE);
            if(hour<8){
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }else if(hour==8&&min<30){
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            Date changedate = calendar.getTime();
            String date = DateUtil.dateToStr(changedate, "yyyy-M-d");
            PpmProductionDayDto pditem = ppmProductionDayService.queryChangeDate(date);
            if(null != pditem) {
                result.setResults(1);
            } else {
                result.setResults(2);
            }
        } else {
            result.setResults(2);
        }
        if(2 == result.getResults().intValue()) {
            // 有其他流程牵扯，目前新应用暂时不写 2021-10-27 k6991
            log.info("dddd");
        }
        return result;
    }

    @Transactional(value = "defaultTx")
    @Override
    public ApiResult querySavePasswd(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        String userId = user.getOperatorname() + "(" + user.getUserid() + ")";
        AssertUtil.notNull(ppmFhyyxtbgApplyDto.getInstanceid(), "[Instanceid] 不能为空");
        PpmFhyyxtbgApplyDto item = this.queryDetail(ppmFhyyxtbgApplyDto);
        if(null == item) {
            result.failure("获取密码失败");
            return result;
        }
        Date date = new Date();
        // 第一次获取密码时 需要执行
        if(!StringUtil.isNotEmpty(item.getUseendtime())) {
            item.setUsehour(ppmFhyyxtbgApplyDto.getUsehour());
            item.setUsestarttime(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(ppmFhyyxtbgApplyDto.getUsehour()));
            item.setUseendtime(calendar.getTime());
            item.setPwdnote(ppmFhyyxtbgApplyDto.getPwdnote());
            // 更新
            item.setApplyuser(null);
            item.setProjectno("");
            this.updatePpmFhyyxtbgApply(item);
            // 页面获取的设备列表
            List<PpmPwdShebeiDto> sbs = ppmFhyyxtbgApplyDto.getPwdList();
            if(null != sbs && !sbs.isEmpty()) {
                sbs.stream().forEach(ii -> {
                    PpmPwdShebeiDto pwd = ppmPwdShebeiService.getSheBei(ii);
                    // 说明是新增加的
                    if(null == pwd) {
                        ii.setInstanceid(ppmFhyyxtbgApplyDto.getInstanceid());
                        ppmPwdShebeiService.addSheBei(ii);
                    }
                });
            }
        }

        // 从密管获取密码
        String spuser = "";
        String usehour = "";
        PpmFlowLogDto logDto = ppmFlowLogService.getPwdFlow(ppmFhyyxtbgApplyDto.getInstanceid());
        if(null != logDto) {
            CommEmployee employee = UsersUtil.getCommEmployeeInfo(logDto.getCreators());
            spuser = employee.getEmpname() + "(" + employee.getBadge() + ")";
        }
        PpmPwdApplyDto applyDto = new PpmPwdApplyDto();
        applyDto.setRelateinstanceid(ppmFhyyxtbgApplyDto.getInstanceid());
        PpmPwdApplyDto ppmPwdApplyDto = ppmPwdApplyService.queryDetail(applyDto);
        if(null != ppmPwdApplyDto) {
            if(StringUtil.isNotBlank(ppmFhyyxtbgApplyDto.getUsehour())) {
                usehour = ppmPwdApplyDto.getUsehour();
            }
            // 更新密码流程的结束时间
            Calendar calendarPwd = Calendar.getInstance();
            calendarPwd.setTime(date);
            calendarPwd.add(Calendar.MINUTE, Integer.valueOf(usehour)*60);
            Date endtime = calendarPwd.getTime();
            if(!StringUtil.isNotEmpty(ppmPwdApplyDto.getEndtime())) {
                ppmPwdApplyDto.setRealstarttime(date);
                ppmPwdApplyDto.setEndtime(endtime);
                ppmPwdApplyService.updatePpmPwdApply(ppmPwdApplyDto);
            }
        } else {
            usehour = ppmFhyyxtbgApplyDto.getUsehour();
        }

        List<PpmPwdShebeiDto> pwdShebeiDtos = ppmPwdShebeiService.getList(ppmFhyyxtbgApplyDto.getInstanceid());

        if(null == pwdShebeiDtos) {
            result.failure("没有设备，获取密码失败");
            return result;
        }
        // 当需要获取密码的设备大于10条时，一半在方法中获取，一半在线程中获取
        int num = pwdShebeiDtos.size() - pwdShebeiDtos.size() / 3;
        if(pwdShebeiDtos.size() > 10) {
            // 多线程
            String finalSpuser = spuser;
            String finalUsehour = usehour;
            ExecutorManager.getExecutor().submit(()->{
                int n = pwdShebeiDtos.size() / 3;
                for (int i = n; i > 0; i--) {
                    pwdShebeiDtos.get(i).setUserid(userId);
                    pwdShebeiDtos.get(i).setUsehour(finalUsehour);
                    saveSheBeiPassword(pwdShebeiDtos.get(i), finalSpuser);
                }
            });
            for (int i = 0; i > num; i++) {
                pwdShebeiDtos.get(i).setUsehour(usehour);
                pwdShebeiDtos.get(i).setUserid(userId);
                saveSheBeiPassword(pwdShebeiDtos.get(i), spuser);
            }
        } else {
            for (int i = pwdShebeiDtos.size() - 1; i >= 0; i--) {
                pwdShebeiDtos.get(i).setUsehour(usehour);
                pwdShebeiDtos.get(i).setUserid(userId);
                saveSheBeiPassword(pwdShebeiDtos.get(i), spuser);
            }
        }
        result.success("密码获取成功");
        return result;
    }

    @Override
    public ApiResult getFlowinfoForPwd(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto, CommOperatorDoLoginDto user) {
        ApiResult result = new ApiResult();
        AssertUtil.notNull(ppmFhyyxtbgApplyDto.getInstanceid(),"[Instanceid]不能为空");
        Map<String,Object> map = JsonUtil.objectTOMap(ppmFhyyxtbgApplyDto, PpmFhyyxtbgApplyDto.class);
        PpmFhyyxtbgApply item = myBatisDao.selectClass(PPMFHYYXTBGAPPLY, map, "getFlowinfoForPwd");
        if(null == item) {
            result.failure("生成运维审计工单失败");
            return result;
        }
        PpmFhyyxtbgApplyDto dto = JsonUtil.jsonToClass(item,PpmFhyyxtbgApplyDto.class);

        List<PpmPwdShebeiDto> shebeiDtos = ppmFhyyxtbgApplyDto.getPwdList();
        Map<String,Object> params = new HashMap<>();
        params.put("name", dto.getApplyChangeTitle());
        params.put("type", "分行自研项目应用变更");
        params.put("tid", dto.getProjectno());
        params.put("validfrom", dto.getUsestarttime().getTime() );
        params.put("validto", dto.getUseendtime().getTime() );
        List<String> logins = new ArrayList<>();
        logins.add(user.getUserid());
//        logins.add("182402");       // 写死的工号
        params.put("users", logins);
        params.put("override",1);
        List<Map<String, String>> userlist = new ArrayList<>();
        PpmPwdShebeiDto shebeiDto = new PpmPwdShebeiDto();
        for (int i = shebeiDtos.size() - 1; i >= 0; i--) {
            Map<String, String> ppmmap = new HashMap<>();
            String usertype= shebeiDtos.get(i).getUsertype();
            String ip= shebeiDtos.get(i).getIp();
            String username =  shebeiDtos.get(i).getUsername();
            shebeiDto.setIp(ip);
            shebeiDto.setUserid(username);
            shebeiDto.setUsertype(usertype);
            shebeiDto.setStatus("0");
            shebeiDto.setSystemid(shebeiDtos.get(i).getSystemname());
            PpmPwdShebeiConfigureDto configureDto = ppmPwdShebeiConfigureService.getSheBei(shebeiDto);
            String serverName = configureDto.getServername();
            if(null != configureDto) {
                ppmmap.put("name", serverName +"-工单");
                ppmmap.put("protocol",configureDto.getNetworkprotocol());
                ppmmap.put("loginattr", configureDto.getLogintype());
            }
            ppmmap.put("ip", ip);
            ppmmap.put("username", username);
            ppmmap.put("password", shebeiDtos.get(i).getPwd());
            String userType = shebeiDtos.get(i).getUsertype();
            if("数据库用户".equals(userType) || "数据库查询用户".equals(userType)) {
                String[] ipstr = ip.split("-");
                String serviceName="";
                String oracleIp ="";
                for (int k = 0; k < ipstr.length; k++) {
                    if(k == 0) {
                        oracleIp = ipstr[k];
                    } else if(k>0 && k < ipstr.length - 1){
                        oracleIp = oracleIp+"-"+ ipstr[k];
                    }else{
                        serviceName = ipstr[k];
                    }
                }
                if (ipstr.length >= 2) {
                    ppmmap.put("ip", oracleIp);
                    ppmmap.put("servicename", serviceName);
                }
            }
            userlist.add(ppmmap);
        }
        params.put("assets", userlist);
        String json = JsonUtil.toJSONString(params);
        log.info("开始上传工单{}", json);
        String path = eosDictEntryService
                .getTypeidForDetail("PWD_YF_URL","url")
                .getDictname() + "/tickets";
        Map<String,String> header = new HashMap<>();
        String token = eosDictEntryService
                .getTypeidForDetail("PWD_YF_URL", "token").getDictname();
        header.put("AccessToken",token);
        String resData = HttpClient.post(path, json, header);
        log.info("运维审计返回报文:{}", resData);
        Map<String,Object> resMap = JsonUtil.jsonToMap(resData);
        if("ok".equals(resMap.get("msg"))) {
            result.success("上传运维审计系统成功");
        }
        return result;
    }

    @Override
    public PpmFhyyxtbgApplyDto getGeneralPassworkKey(CommOperatorDoLoginDto user,String clientIp) {
        StringBuilder sb = new StringBuilder();
        PpmFhyyxtbgApplyDto applyDto = new PpmFhyyxtbgApplyDto();
        sb.append("PPM").append(user.getUserid());
        sb.append(DateUtil.dateToStr(new Date(), "yyyyMMdd"));
        try {
            String md5 = CryptoUtil.encoderByMd5(sb.toString());
            applyDto.setToken(md5);
            applyDto.setApplyuser(user.getUserid());
            String url = eosDictEntryService.getDomainName(clientIp);
            applyDto.setTyUrl(url);
        } catch (Exception e) {
            log.error("获取通用密码密钥异常", e);
        }
        return applyDto;
    }

    @Override
    public ApiResult ifHiddenButton(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        ApiResult<PpmFhyyxtbgApplyDto> result = new ApiResult<>();
        boolean daochu ;//导出
        boolean update = false;//新增/删除设备
        AssertUtil.notNull(ppmFhyyxtbgApplyDto.getInstanceid(), "【Instanceid】不能为空");
        PpmFhyyxtbgApplyDto applyDto1 =  this.queryDetail(ppmFhyyxtbgApplyDto);
        Date date = new Date();
        //当前时间大于计划结束时间 等于 过期
        if(null != applyDto1 && date.after(applyDto1.getUseendtime())) {
            daochu = false;
        } else {
            daochu = true;
            update = true;
        }
        ppmFhyyxtbgApplyDto.setDaochu(daochu);
        ppmFhyyxtbgApplyDto.setUpdate(update);
        result.setResults(ppmFhyyxtbgApplyDto);
        result.success("查询成功");
        return result;
    }

    /**
     * 保存设备密码
     * @param ppmPwdShebeiDto 对象
     * @param spuser 使用时长
     */
    private void saveSheBeiPassword(PpmPwdShebeiDto ppmPwdShebeiDto, String spuser) {
        Date date = new Date();
        String key = "";    // 获取密码的key
        String groupName = "";
        log.info("{}开始获取密码状态", ppmPwdShebeiDto.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, Integer.valueOf(ppmPwdShebeiDto.getUsehour())*60);
        Date endtime = calendar.getTime();
        UCMGetPasswordClient uCMGetPasswordClient =
                new UCMGetPasswordClient();
        if(StringUtil.isNotEmpty(ppmPwdShebeiDto.getIp())) {
            key = ppmPwdShebeiDto.getUsername() + "@" + ppmPwdShebeiDto.getIp();
        } else {
            key = ppmPwdShebeiDto.getUsername();
        }
        if("数据库用户".equals(ppmPwdShebeiDto.getUsertype())
                || "数据库查询用户".equals(ppmPwdShebeiDto.getUsertype())) {
            PpmPwdShebeiConfigureDto dto = ppmPwdShebeiConfigureService.getSheBei(ppmPwdShebeiDto);
            if(null != dto && StringUtil.isNotEmpty(dto.getGroupname())) {
                key = dto.getGroupname();
            }
        }
        //
        if(StringUtil.isNotEmpty(ppmPwdShebeiDto.getEndtime())) {
            log.info("设备id为【{}】密码已获取，无法再次获取", ppmPwdShebeiDto.getId());
            long dateTime2 = ppmPwdShebeiDto.getEndtime().getTime();
            if(date.getTime() >= dateTime2) {
                ppmPwdShebeiDto.setPwd("");
                ppmPwdShebeiService.updatePwdShebei(ppmPwdShebeiDto);
            }
            return ;
        }
        // 获取密码
        List<EosDictEntry> eosList = eosDictEntryService.getTypeidForList("PWD_LOGIN_USER");
        String loginUser ;
        String password ;
        String status ;
        boolean isCheckout = false;
        for (int i = 0; i < 9; i++) {
            loginUser = eosList.get(i).getDictid();
            password = eosList.get(i).getDictname();
            AuthResultTO result = UCMGetPasswordClient.login(loginUser,password);
            status = UCMGetPasswordClient.getStatus(result,key);
            log.info("设备id为【{}】,密钥：{}，密码状态:{}", ppmPwdShebeiDto.getId(),key, status);
            if("AVAILABLE_FOR_CHECKOUT".equals(status)
                    || "CHECKED_OUT".equals(status)) {
                password = uCMGetPasswordClient.getPassword(result, key);
                String state =  uCMGetPasswordClient.checkout(result,key,Integer.valueOf(ppmPwdShebeiDto.getUsehour()));
                log.info("设备id为【{}】检出结果:{}", ppmPwdShebeiDto.getId(), state);
                if(!"success".equals(state)) {
                    break;
                }
                String[] pwds = password.split(",");
                StringJoiner joiner = new StringJoiner(",");
                int length = pwds.length -1;
                for (int i1 = 0; i1 < length ; i1++) {
                    joiner.add(pwds[i1]);
                }

                String newPwd = CryptoUtil.base64Encode(joiner.toString().getBytes());
                log.info("设备id为【{}】,密钥：{}，加密密码：{}<<<<<<",ppmPwdShebeiDto.getId(),key,newPwd);
                ppmPwdShebeiDto.setPwd(newPwd);
                ppmPwdShebeiDto.setLoginuser(loginUser);
                ppmPwdShebeiDto.setEndtime(endtime);
                ppmPwdShebeiDto.setStarttime(date);
                ppmPwdShebeiDto.setApplyer(ppmPwdShebeiDto.getUserid());
                ppmPwdShebeiService.updatePwdShebei(ppmPwdShebeiDto);
                isCheckout = true;
                PpmPwdShebeiConfigureDto dto = ppmPwdShebeiConfigureService.getSheBei(ppmPwdShebeiDto);
                if(null != dto) {
                    dto.setApplyer(ppmPwdShebeiDto.getUserid());
                    dto.setApplytime(date);
                    dto.setSpuser(spuser);
                    dto.setCheckindate(endtime);
                    ppmPwdShebeiConfigureService.updateSheBei(dto);
                }
            }
            if(isCheckout) {
                break;
            }
            //当所有账号都已使用者，取到期时间最接近的
            if(i != 8) {
               continue;
            }
            ppmPwdShebeiDto.setGroupName(groupName);
            PpmPwdShebeiDto shebeiDto =ppmPwdShebeiService.querySheBeiEndTime(ppmPwdShebeiDto);
            if(null != shebeiDto) {
                long dateTime3 = shebeiDto.getEndtime().getTime();
                long dateTime4 = endtime.getTime();
                ppmPwdShebeiDto.setPwd(shebeiDto.getPwd());
                ppmPwdShebeiDto.setLoginuser(shebeiDto.getLoginuser());
                // //到期时间大于密码到期时间
                if(dateTime4 >= dateTime3) {
                    ppmPwdShebeiDto.setEndtime(shebeiDto.getEndtime());
                    ppmPwdShebeiDto.setStarttime(date);
                    ppmPwdShebeiDto.setApplyer(ppmPwdShebeiDto.getUserid());
                } else {
                    ppmPwdShebeiDto.setEndtime(endtime);
                    ppmPwdShebeiDto.setStarttime(date);
                    ppmPwdShebeiDto.setApplyer(ppmPwdShebeiDto.getUserid());
                }
                ppmPwdShebeiService.updatePwdShebei(ppmPwdShebeiDto);
                log.info("设备id为【{}】,密钥：{}，加密密码：{}!!!!!",ppmPwdShebeiDto.getId(),key,shebeiDto.getPwd());
            }
        }

    }


    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        // 不删除业务数据
    }

    @Override
    public PpmFhyyxtbgApplyDto queryMobileDetail(PpmFhyyxtbgApplyDto ppmFhyyxtbgApplyDto) {
        PpmFhyyxtbgApplyDto ppmFhyyxtbgApply = this.queryDetail(ppmFhyyxtbgApplyDto);
        List<PpmDocumentDto> documentList = ppmFhyyxtbgApply.getDocList();
        if (documentList != null && documentList.size() > 0) {
            String mobilePath = ppmFhyyxtbgApplyDto.getDir()
                    + ppmFhyyxtbgApplyDto.getInstanceid()
                    + ppmFhyyxtbgApplyDto.getSeparator();

            documentList.stream().forEach(ii -> {
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
        }

        ppmFhyyxtbgApply.setDocList(documentList);

        return ppmFhyyxtbgApply;
    }
}
