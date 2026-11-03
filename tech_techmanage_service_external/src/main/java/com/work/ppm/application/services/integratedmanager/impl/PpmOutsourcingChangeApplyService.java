package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmEmpBaseInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqProfesstionDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingChangeApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingChangeInfosDto;
import com.work.ppm.application.dto.integratedmanager.PpmRlwbhtInfoDto;
import com.work.ppm.application.dto.othermanager.PpmWorkKjlCghtDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.usermanager.PpmDaTeamGroupDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeApply;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingExt;
import com.work.ppm.application.model.integratedmanager.ext.PpmEmpBaseInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.projectmanager.PpmProjectBuildSoft;
import com.work.ppm.application.model.projectmanager.ext.AapSystemInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.usermanager.PpmDaTeamUser;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.integratedmanager.IPpmIntervieweeInfoService;
import com.work.ppm.application.services.integratedmanager.IPpmKqProfesstionService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingChangeApplyService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingChangeInfosService;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingExtService;
import com.work.ppm.application.services.integratedmanager.IPpmRlwbhtInfoService;
import com.work.ppm.application.services.othermanager.IPpmWorkKjlCghtService;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import com.work.ppm.application.services.projectmanager.IPpmProjectBuildSoftService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.usermanager.ICommOutsourcingPersonSshtService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamUserService;
import com.work.ppm.application.services.usermanager.IPpmRlwbsshtInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import com.work.ppm.application.quartz.interfaces.IPpmOutsourcingChangeApplyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外包基本信息变更
 *
 */
@Service("ppmOutsourcingChangeAppplyService")
public class PpmOutsourcingChangeApplyService extends BaseDaoSupper implements IPpmOutsourcingChangeApplyService, IPpmOutsourcingChangeApplyJobService, ICommonDeleteFlowService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGCHANGEAPPLY = "com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeApply";
    /**
     * 流程名称
     */
    private final static String FLOW_NAME = "com.work.ppm.bps.newwbryxxbg";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 外包基础信息
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 外包信息变更详情
     */
    @Autowired
    private IPpmOutsourcingChangeInfosService ppmOutsourcingChangeInfosService;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 外包面试信息
     */
    @Autowired
    private IPpmIntervieweeInfoService ppmIntervieweeInfoService;
    /**
     * 文员信息
     */
    @Autowired
    private IPpmKqProfesstionService ppmKqProfesstionService;
    /**
     * 部门信息
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 小组信息
     */
    @Autowired
    private IPpmDaTeamGroupService ppmDaTeamGroupService;
    /**
     * 包商合同接口
     */
    @Autowired
    private IPpmWorkKjlCghtService ppmWorkKjlCghtService;
    /**
     * 人力合同接口
     */
    @Autowired
    private IPpmRlwbhtInfoService ppmRlwbhtInfoService;

    /**
     * 小组信息
     */
    @Autowired
    private IPpmDaTeamUserService ppmDaTeamUserService;
    /**
     * 外包人员信息
     */
    @Autowired
    private IPpmOutsourcingBasicService PpmOutsourcingBasicService;
    /**
     * 外包人员信息拓展表
     */
    @Autowired
    private IPpmOutsourcingExtService ppmOutsourcingExtService;
    /**
     * 系统表
     */
    @Autowired
    private IAapSystemService aapSystemService;
    /**
     * 合同关联表
     */
    @Autowired
    private IPpmRlwbsshtInfoService ppmRlwbsshtInfoService;

    /**
     * 合同关联表
     */
    @Autowired
    private ICommOutsourcingPersonSshtService commOutsourcingPersonSshtService;
    /**
     * 项目信息
     */
    @Autowired
    private IPpmProjectBuildSoftService ppmProjectBuildSoftService;



    @Override
    public void queryPager(PpmOutsourcingChangeApply ppmOutsourcingChangeAppply, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmOutsourcingChangeAppply, PpmOutsourcingChangeApply.class);
        myBatisDao.selectPager(PPMOUTSOURCINGCHANGEAPPLY, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmOutsourcingChangeApply(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) {
        Long id = getNextSequence(PpmOutsourcingChangeApply.class);
        // 如果需要请set 主键id
        //ppmOutsourcingChangeAppply.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEAPPLY, ppmOutsourcingChangeApply, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmOutsourcingChangeApply queryDetail(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) {
        return myBatisDao.selectClass(PPMOUTSOURCINGCHANGEAPPLY, ppmOutsourcingChangeApply, "queryDetail");
    }

    @Override
    public Integer updatePpmOutsourcingChangeApply(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEAPPLY, ppmOutsourcingChangeApply, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deletePpmOutsourcingChangeApply(PpmOutsourcingChangeApply ppmOutsourcingChangeApply) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEAPPLY, ppmOutsourcingChangeApply, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public PpmEmpBaseInfo getEmpBaseInfo(PpmEmpBaseInfo info) {
        String flowid = CodeGenerater.generate("XXBG", 14, "yyyyMMdd");
        PpmEmpBaseInfo baseInfo = myBatisDao.selectClass(PPMOUTSOURCINGCHANGEAPPLY, info, "getEmpBaseInfo");
        baseInfo.setFlowid(flowid.toUpperCase());
        PpmKqProfesstionDto dto = new PpmKqProfesstionDto();
        dto.setJobid(baseInfo.getBadge());
        List<PpmKqProfesstionDto> professions = ppmKqProfesstionService.getPpmKqProfesstions(dto);
        if (null != professions && professions.size() > 0) {
            baseInfo.setFlag("true");
            String deps = "";
            for (int i = 0; i < professions.size(); i++) {
                if (i == 0) {
                    deps = professions.get(i).getDeptno();
                } else {
                    deps = deps + "," + professions.get(i).getDeptno();
                }
            }
            baseInfo.setDeps(deps);
        }

        return baseInfo;
    }

    @Override
    public PpmOutsourcingChangeInfos getOutBaseInfo(String jobid) {
        PpmOutsourcingBasicInfo base = ppmOutsourcingBasicService.getBaseInfo(jobid);
        PpmOutsourcingChangeInfos info = new PpmOutsourcingChangeInfos();
        if (base != null) {
            info.setJobid(base.getJobId());
            info.setUsername(base.getUserName());
            info.setCertnumber(base.getCertificateNumber());
            info.setCompanyid(base.getOutsourceNo());
            info.setCompanyname(base.getOutsourcname());
            info.setOldDepid(base.getDeptId());
            info.setOldDepname(base.getDepname());
            info.setNewDepid(base.getDeptId());
            info.setNewDepname(base.getDepname());
            info.setOldOrganizers(base.getPersonInCharge());
            info.setOldOrganizersname(base.getPersonInChargename());
            info.setNewOrganizers(base.getPersonInCharge());
            info.setNewOrganizersname(base.getPersonInChargename());
            info.setOldGroupid(base.getGroupId());
            info.setOldGroupname(base.getGroupname());
            info.setNewGroupid(base.getGroupId());
            info.setNewGroupname(base.getGroupname());

            info.setOldProjectno(base.getProjectNumber());
            info.setOldProjectname(base.getProjectname());

            info.setNewProjectno(base.getProjectNumber());
            info.setNewProjectname(base.getProjectname());

            info.setOldSysno(base.getSystemNo());
            info.setOldSyaname(base.getSystemnames());

            info.setNewSysno(base.getSystemNo());
            info.setNewSysname(base.getSystemnames());

            info.setOldOutsourcingtype(base.getOutsourceType());
            info.setOldOutsourcingtypename(base.getOutsourceTypename());
            info.setNewOutsourcingtype(base.getOutsourceType());
            info.setNewOutsourcingtypename(base.getOutsourceTypename());

            info.setOldUsetype(base.getUserType());
            info.setOldUsetypename(base.getUserTypename());
            info.setNewUsetype(base.getUserType());
            info.setNewUsetypename(base.getUserTypename());

            info.setOldPosition(base.getPostId());
            info.setOldPositionname(base.getPostname());
            info.setNewPosition(base.getPostId());
            info.setNewPositionname(base.getPostname());
//            info.setOldPrice();
//            info.setNewPrice();
            info.setOldContractid(base.getContractId());
            info.setOldContractname(base.getHtmc());

            info.setNewContractid(base.getContractId());
            info.setNewContractname(base.getHtmc());
            //查询年限
            //面试时工作年限
            double peryears = 0;
            PpmIntervieweeInfo intervieweeInfo = ppmIntervieweeInfoService.queryPpmIntervieweeInfoByIdcard(base.getCertificateNumber());
            String workyears = "";
            if (intervieweeInfo != null) {
                workyears = intervieweeInfo.getWorkyears();
            }
            if (workyears != null && !"".equals(workyears)) {
                peryears = Double.parseDouble(workyears);
            }
            //当前工作年限
            double lastyears = 0;
            Date entrytime = base.getEntryTime();
            if (entrytime != null) {
//                Calendar cal=Calendar.getInstance();
//                cal.setTime(entrytime);
//                Date starttime=cal.getTime();
//                cal.setTime(new Date());
//                Date endtime=cal.getTime();
                lastyears = (new Date().getTime() - entrytime.getTime()) / 86400000 / 365;

            }
            info.setYears(new Double((peryears + lastyears)).longValue());
            //查询等级前台校验
            // 在场状态
            info.setProattrbutes(base.getProattrbutes());
        }
        return info;
    }

    @Override
    public int addPpmOutsourcingChangeApplyDto(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply) {
        PpmOutsourcingChangeApply apply = JsonUtil.jsonToClass(ppmOutsourcingChangeApply, PpmOutsourcingChangeApply.class);
        if (null == apply.getId()) {
            // 如果需要请set 主键id
            Long id = getNextSequence(PpmOutsourcingChangeApply.class);
            apply.setId(id);
            myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEAPPLY, apply, IMyBatisDao.SQL.INSTER, "addPojo");
        } else {
            myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEAPPLY, apply, IMyBatisDao.SQL.UPDATE, "updatePojo");
        }

        return 1;
    }

    @Override
    public PpmOutsourcingChangeApplyDto save(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, CommOperatorDoLoginDto user) {
        //判断流程instanceid是否为空
        String instanceid = ppmOutsourcingChangeApply.getInstanceid();
        String changeName = "";
        if (infos.size() >= 0) {
            changeName = infos.get(0).getUsername();
        }
        String processName = changeName + "等人的信息变更";

        if (!StringUtil.isNotBlank(instanceid)) {
            BpsWorkFlowDto bps = new BpsWorkFlowDto();
            String xid = "";
            try {
                // =================== 流程信息 ===========
                xid = BpsUtil.transactionBegin(user.getUserid());
                bps.setProcessDefName(FLOW_NAME);
                bps.setProcessInstName(processName);
                bps.setProcessInstDesc("申请发起");
                bps.setUserId(user.getUserid());
                Map<String, String> relaData = new HashMap<String, String>();

                bps.setRelaData(relaData);
                // 日志参数
                bps.setSuggest("保存");
                bps.setConclusion("01");
                bps.setFlowUrl("newwbryxxbg");
                bps.setApplydesc(bps.getProcessInstDesc());
                String processInstId = BpsUtil.createProcessInstance(bps, xid);   //发起流程,processInstID为返回的流程id 唯一
                if (processInstId != null && !"-1".equals(processInstId) && !"".equals(processInstId)) {
                    ppmOutsourcingChangeApply.setInstanceid(processInstId);
                    ppmOutsourcingChangeApply.setLastmodtime(new Date());
                    ppmOutsourcingChangeApply.setLastmoduserid(bps.getUserId());
                    this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
                    List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
                    ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, processInstId);
                    // 返回对象
                    ppmOutsourcingChangeApply.setId(ppmOutsourcingChangeApply.getId());
                    ppmOutsourcingChangeApply.setInstanceid(processInstId);
                } else {
                    ppmOutsourcingChangeApply = null;
                }
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            } catch (Exception e) {
                BpsUtil.transactionRollback(bps.getUserId(), xid);
                throw new BizException(e);
            }
        } else {
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
            ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, ppmOutsourcingChangeApply.getInstanceid());

        }
        return ppmOutsourcingChangeApply;
    }

    /**
     * 判断字符串是否重复
     *
     * @param oldStr
     * @param newStr
     * @return newStr
     */
    private String isRepeat(String oldStr, String newStr) {
        String ret = "";
        if (oldStr.contains(newStr)) {
            ret = oldStr;
        } else {
            ret = oldStr + "," + newStr;
        }
        return ret;
    }

    @Transactional("defaultTx")
    @Override
    public String launch(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {
        //判断流程instanceid是否为空
        String instanceid = ppmOutsourcingChangeApply.getInstanceid();
        String processInstId = instanceid;//流程id
        String processInfo = "";//流程信息
        String changeName = "";
        String xid = "";

        String approveUserId = "";
        boolean flag = false;
        String person_in_charges = "";
        String deptno = "";
        try {
            if (null == infos || infos.isEmpty()) {
                return processInfo;

            }
            changeName = infos.get(0).getUsername();

            String processName = changeName + "等人的信息变更";
            //获得新部门的二级部
            deptno = infos.get(0).getNewDepid().replace("dep", "");
            for (int i = 0; i < infos.size(); i++) {
                if (null != infos.get(i).getNewDepid() && infos.get(i).getNewDepid().contains("dep")) {
                    infos.get(i).setNewDepid(infos.get(i).getNewDepid().replace("dep", ""));
                }
                if ((!infos.get(i).getOldOutsourcingtype().equals(infos.get(i).getNewOutsourcingtype()) && "1".equals(infos.get(i).getNewOutsourcingtype()) ||
                        !infos.get(i).getOldUsetype().equals(infos.get(i).getNewUsetype()) && "0".equals(infos.get(i).getNewUsetype()))) {
                    //判断是否走面试流程
                    flag = true;
                    //面试行内负责人的人员
                    person_in_charges = "".equals(person_in_charges) ? infos.get(i).getNewOrganizers() : isRepeat(person_in_charges, infos.get(i).getNewOrganizers());
                }
            }
            Map<String, String> relaData = new HashMap<String, String>();
            if (flag) {
                approveUserId = person_in_charges;
                relaData.put("users", approveUserId);
                relaData.put("status", "-1");
                ppmOutsourcingChangeApply.setIsinterview("1");
            } else {
                //继续流转到主持工作的二级部经理
                CommEmployeeDto dto = UsersUtil.getTwoWorkManager(deptno);
                approveUserId = dto.getBadge();
                relaData.put("users", approveUserId);
                relaData.put("status", "02");
                ppmOutsourcingChangeApply.setIsinterview("0");
            }

            if (!StringUtil.isNotBlank(ppmOutsourcingChangeApply.getInstanceid())) {
                // 日志参数
                bps.setConclusion("01");
                bps.setApplydesc("申请发起");
                bps.setRelaData(relaData);
                bps.setFlowUrl("newwbryxxbg");
                bps.setProcessDefName(FLOW_NAME);
                bps.setProcessInstName(processName);
                bps.setProcessInstDesc("申请发起");
                bps.setSuggest("提交");
                processInstId = BpsUtil.createProcessAndStart(bps, xid);
                if (!StringUtil.isNotBlank(processInstId) || "-1".equals(processInstId)) {
                    return "流程启动失败";
                }
                // ================= 业务部分 ==============
                ppmOutsourcingChangeApply.setInstanceid(processInstId);
                ppmOutsourcingChangeApply.setLastmodtime(new Date());
                ppmOutsourcingChangeApply.setLastmoduserid(bps.getUserId());
            } else {
                bps.setApplydesc(bps.getProcessInstDesc());
                bps.setRelaData(relaData);
                bps.setFlowUrl("newwbryxxbg");
                bps.setUserId(bps.getUserId());
                bps.setConclusion("01");
                bps.setSuggest("同意");
                bps.setApplydesc("申请发起");
                BpsUtil.finshWorkItem(bps, xid);
            }
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
            ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, processInstId);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        processInfo = BpsUtil.queryProcessInfo(bps);
        return processInfo;
    }

    @Transactional("defaultTx")
    @Override
    public String interviewscore(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {

        Map<String, String> map = new HashMap<>();
        String info = "";
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                map.put("status", "01");
                map.put("users", ppmOutsourcingChangeApply.getApplyid());
                bps.setApplydesc("面试打分");
                bps.setRelaData(map);

                this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
                List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
                ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, ppmOutsourcingChangeApply.getInstanceid());
                bps.setDestActDefID("manualActivity");
                BpsUtil.backActivity(bps);
                // 同意
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                EosDictEntry eos = eosDictEntryService.getTypeidForDetail("RLXQQRR", "userid");
                String approveUserId = eos.getDictname();
                map.put("status", "-1");
                map.put("users", approveUserId);
                bps.setRelaData(map);
                bps.setApplydesc("面试打分");
                this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
                List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
                ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, ppmOutsourcingChangeApply.getInstanceid());

                BpsUtil.finshWorkItem(bps, xid);
            }
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);

        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        info = BpsUtil.queryProcessInfo(bps);
        return info;
    }

    @Transactional("defaultTx")
    @Override
    public String resultinput(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {

        Map<String, String> map = new HashMap<>();
        String info = "";
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                map.put("status", "01");
                map.put("users", ppmOutsourcingChangeApply.getApplyid());
                // 同意
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                String deptno = infos.get(0).getNewDepid();
                //继续流转到主持工作的二级部经理
                CommEmployeeDto dto = UsersUtil.getTwoWorkManager(deptno);
                map.put("status", "-1");
                map.put("users", dto.getBadge());
            }
            bps.setRelaData(map);
            bps.setApplydesc("录入结果");
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
            ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, ppmOutsourcingChangeApply.getInstanceid());

            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(),xid);
            throw new BizException(e);
        }
        info = BpsUtil.queryProcessInfo(bps);
        return info;
    }

    @Transactional("defaultTx")
    @Override
    public String twoManager(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {

        Map<String, String> map = new HashMap<>();
        String info = "";
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                map.put("status", "01");
                map.put("users", ppmOutsourcingChangeApply.getApplyid());
                // 同意
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                EosDictEntry eos = eosDictEntryService.getTypeidForDetail("RLXQQRR", "userid");
                String approveUserId = eos.getDictname();
                map.put("status", "-1");
                map.put("users", approveUserId);
            }
            bps.setRelaData(map);
            bps.setApplydesc("二级部经理审批");
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
            ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, ppmOutsourcingChangeApply.getInstanceid());

            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e){
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        info = BpsUtil.queryProcessInfo(bps);
        return info;
    }

    @Transactional("defaultTx")
    @Override
    public String confirm(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {

        Map<String, String> map = new HashMap<>();
        String info = "";
        String xid = "";
        // 流程状态 02 退回， -1：继续流转
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            if ("02".equals(bps.getConclusion())) {
                map.put("status", "01");
                map.put("users", ppmOutsourcingChangeApply.getApplyid());
                // 同意
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                // 新增 当涉及外包方式 结算方式变更，去掉分管总和总经理审批节点 2021-09-07
                ppmOutsourcingChangeApply.setStatus("1");
                map.put("status", "02");
            }
            bps.setRelaData(map);
//            bps.setApplydesc("研发管理部确认");
            bps.setApplydesc("金融科技部总经理室确认");
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            List<PpmOutsourcingChangeInfos> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfos.class);
            ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(dtoList, ppmOutsourcingChangeApply.getInstanceid());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        info = BpsUtil.queryProcessInfo(bps);
        return info;
    }

    @Transactional("defaultTx")
    @Override
    public String deputyDirectorGeneral(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {

        Map<String, String> map = new HashMap<>();
        String info = "";
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());

            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                map.put("status", "01");
                map.put("users", ppmOutsourcingChangeApply.getApplyid());
                // 同意
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                EosDictEntry eos = eosDictEntryService.getTypeidForDetail("JSRLSPR", "1");
                String approveUserId = eos.getDictname();
                map.put("status", "-1");
                map.put("users", approveUserId);
            }
            bps.setRelaData(map);
            bps.setApplydesc("分管总经理审批");
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            //ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(infos,ppmOutsourcingChangeApply.getInstanceid());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e){
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        info = BpsUtil.queryProcessInfo(bps);
        return info;
    }

    @Transactional("defaultTx")
    @Override
    public String manager(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply, List<PpmOutsourcingChangeInfosDto> infos, BpsWorkFlowDto bps) {

        Map<String, String> map = new HashMap<>();
        String info = "";
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                map.put("status", "01");
                map.put("users", ppmOutsourcingChangeApply.getApplyid());
                // 同意
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                ppmOutsourcingChangeApply.setStatus("1");
                map.put("status", "-1");
                //map.put("users", approveUserId);
            }
            bps.setRelaData(map);
            bps.setApplydesc("总经理审批");
            this.addPpmOutsourcingChangeApplyDto(ppmOutsourcingChangeApply);
            //ppmOutsourcingChangeInfosService.addPpmOutsourcingChangeInfos(infos,ppmOutsourcingChangeApply.getInstanceid());
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        info = BpsUtil.queryProcessInfo(bps);
        return info;
    }

    @Override
    public PpmOutsourcingChangeApplyDto getFLowInfo(PpmOutsourcingChangeApplyDto ppmOutsourcingChangeApply) {
        PpmOutsourcingChangeApplyDto dto = new PpmOutsourcingChangeApplyDto();
        PpmWorkKjlCghtDto ht = new PpmWorkKjlCghtDto();
        PpmRlwbhtInfoDto rl = new PpmRlwbhtInfoDto();
        PpmOutsourcingChangeApply apply = new PpmOutsourcingChangeApply();
        apply.setInstanceid(ppmOutsourcingChangeApply.getInstanceid());
        //查询流程数据
        apply = this.queryDetail(apply);
        dto = JsonUtil.jsonToClass(apply, PpmOutsourcingChangeApplyDto.class);
        //查询子流程数据
        List<PpmOutsourcingChangeInfos> infos = ppmOutsourcingChangeInfosService.queryLists(ppmOutsourcingChangeApply.getInstanceid());
        List<PpmOutsourcingChangeInfosDto> dtoList = JsonUtil.jsonToList(infos, PpmOutsourcingChangeInfosDto.class);
        dto.setInfos(dtoList);
        for (int i = 0; i < dtoList.size(); i++) {
            PpmOutsourcingChangeInfosDto infosDto = dtoList.get(i);
            //行内负责人下拉框（我不想这么做）
            List<CommTreeDto> orginizers = commDepartmentService.queryAuth(infosDto.getNewDepid().replace("dep", ""), "", "");
            if (null != orginizers && orginizers.size() > 0) {
                infosDto.setOrganizers(orginizers);
            }
            //小组（我不想这么做）
            List<PpmDaTeamGroupDto> teams = ppmDaTeamGroupService.queryGroup(infosDto.getNewDepid().replace("dep", ""));
            if (null != teams && teams.size() > 0) {
                infosDto.setTeams(teams);
            }
            ht.setDealdeptid(infosDto.getNewDepid().replace("dep", ""));
            //项目合同（我不想这么做）
            List<PpmWorkKjlCghtDto> projectContracts = ppmWorkKjlCghtService.queryContracts(ht);
            if (null != projectContracts && projectContracts.size() > 0) {
                infosDto.setProjectContracts(projectContracts);
            }
            rl.setOrgcode(infosDto.getCompanyid());
            //人力合同（我不想这么做）
            List<PpmRlwbhtInfoDto> personnelContracts = ppmRlwbhtInfoService.queryOrInsertPpmRlwbhtInfo(rl);
            if (null != personnelContracts && personnelContracts.size() > 0) {
                infosDto.setPersonnelContracts(personnelContracts);
            }
        }

        return dto;
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmOutsourcingChangeApply ppmOutsourcingChangeApply = new PpmOutsourcingChangeApply();
        ppmOutsourcingChangeApply.setInstanceid(bps.getProcessInstId());
        PpmOutsourcingChangeInfos info = new PpmOutsourcingChangeInfos();
        info.setInstanceid(bps.getProcessInstId());
        int i = myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEAPPLY, ppmOutsourcingChangeApply, IMyBatisDao.SQL.DELETE, "deleteByInstanceid");
        if (i == 1) {
            ppmOutsourcingChangeInfosService.deleteByInstanceid(info);
        }
    }

    @Override
    public List<CommTreeDto> queryCenterDepTree(PpmEmpBaseInfoDto info) {
        Map<String, Object> map = new HashMap<String, Object>();
        String depnos = "";
        if (null != info.getFlag() && "true".equals(info.getFlag())) {
            String[] deps = info.getDeps().split(",");
            for (int i = 0; i < deps.length; i++) {
                if (i == 0) {
                    depnos = "'" + deps[i] + "'";
                } else {
                    depnos = depnos + "," + "'" + deps[i] + "'";
                }
            }
            map.put("deps", depnos);
        } else if (null != info) {
            map.put("deps", "'" + info.getDepid() + "'");
        }
        List<CommTree> list = myBatisDao.selectListT(PPMOUTSOURCINGCHANGEAPPLY, map, "queryCenterDepTree");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        // 递归 组织树
        List<CommTreeDto> newTreeList = CommTreeDto.recursiveListToTree(dtoList);
        return newTreeList;
    }

    @Override
    public List<PpmOutsourcingBasicExcelDto> queryOutBaseInfos(PpmOutsourcingBasicExcelDto info) {
//        Map param=new HashMap();
//        param.put("deptId",info.getDeptId());
//        param.put("state","0");
        info.setState("0");
        PpmOutsourcingBasicInfo apply = JsonUtil.jsonToClass(info, PpmOutsourcingBasicInfo.class);
        List<PpmOutsourcingBasicInfo> outs = ppmOutsourcingBasicService.queryPpmOutsourcingBasicInfo(apply);
        List<PpmOutsourcingBasicExcelDto> dtoList = JsonUtil.jsonToList(outs, PpmOutsourcingBasicExcelDto.class);
        return dtoList;
    }

    @Override
    public List<PpmOutsourcingBasic> queryDetailByIdAndName(PpmOutsourcingBasic info) {
        info.setState("0");
        List<PpmOutsourcingBasic> detail = ppmOutsourcingBasicService.queryDetailByIdAndName(info);
        return detail;
    }

    /**
     * 更新外包基础表数据
     */
    private void updatePpmOutsourcingBasic(String jobid, PpmOutsourcingChangeInfos item, String sysdate) {
        PpmOutsourcingBasic basic = new PpmOutsourcingBasic();
        basic.setJobId(jobid);
        basic = PpmOutsourcingBasicService.queryDetail(basic);
        if (null == basic) {
            return;
        }
        if (!"".equals(item.getNewDepid()) && !item.getNewDepid().equals(item.getOldDepid())) {
            basic.setDeptId(item.getNewDepid());
        }

        if (!"".equals(item.getNewOutsourcingtype()) && !item.getNewOutsourcingtype().equals(item.getOldOutsourcingtype())) {
            basic.setOutsourceType(item.getNewOutsourcingtype());
        }

        if (!"".equals(item.getNewUsetype()) && !item.getNewUsetype().equals(item.getOldUsetype())) {
            basic.setUserType(item.getNewUsetype());
        }

        if (!"".equals(item.getNewOrganizers()) && !item.getNewOrganizers().equals(item.getOldOrganizers())) {
            basic.setPersonInCharge(item.getNewOrganizers());
        }

        if (!"".equals(item.getNewPosition()) && !item.getNewPosition().equals(item.getOldPosition())) {
            basic.setPostId(item.getNewPosition());
        }

        if (!"".equals(item.getNewGroupid()) && !item.getNewGroupid().equals(item.getOldGroupid())) {
            basic.setGroupId(item.getNewGroupid());
        }
        if (!"".equals(item.getNewSysno()) && !item.getNewSysno().equals(item.getOldSysno())) {
            basic.setSystemNo(item.getNewSysno());
            AapSystemInfo aapsystem = aapSystemService.queryPasBySystems(item.getNewSysno());
            if (aapsystem != null) {
                basic.setSystemPrincipal(aapsystem.getUserids());
            }
        }
        if (!"".equals(item.getNewProjectno()) && !item.getNewProjectno().equals(item.getOldProjectno())) {
            List<PpmProjectBuildSoftDto> list = ppmProjectBuildSoftService.queryMAS(item.getNewProjectno());
            if (null != list&&!list.isEmpty()) {
                basic.setSystemNo(list.get(0).getSysno());
                basic.setSystemPrincipal(list.get(0).getManauserid());
            }
            PpmProjectBuildSoft soft = new PpmProjectBuildSoft();
            soft.setProjectno(item.getNewProjectno());
            soft = ppmProjectBuildSoftService.queryDetail(soft);
            if(StringUtil.isNotEmpty(soft)){
                basic.setProjectManager(soft.getManauserid());
                basic.setProjectNumber(item.getNewProjectno());
            }
        }
//        if (!"".equals(item.getNewContractid()) && !item.getNewContractid().equals(item.getOldContractid())) {
//            if ("1".equals(item.getOldOutsourcingtype())) {
//                if (null!=item.getOldContractid()&&!"".equals(item.getOldContractid())) {
//                    PpmRlwbsshtInfo info = new PpmRlwbsshtInfo();
//                    info.setJobid(item.getJobid());
//                    info.setHtid(item.getOldContractid());
//                    PpmRlwbsshtInfo infos = ppmRlwbsshtInfoService.queryDetail(info);
//                    if (infos != null) {
//                        infos.setEndtime(sysdate);
//                        ppmRlwbsshtInfoService.updatePpmRlwbsshtInfo(infos);
//                    }
//                }
//                if ("1".equals(item.getNewOutsourcingtype())) {
//                    PpmRlwbsshtInfo newinfo = new PpmRlwbsshtInfo();
//                    newinfo.setJobid(item.getJobid());
//                    newinfo.setHtid(item.getNewContractid());
//                    newinfo.setStarttime(sysdate);
//                    ppmRlwbsshtInfoService.addPpmRlwbsshtInfo(newinfo);
//                } else if (!"0000".equals(item.getNewContractid())) {
//                    CommOutsourcingPersonSsht per = new CommOutsourcingPersonSsht();
//                    per.setJobid(jobid);
//                    per.setStarttime(sysdate);
//                    per.setSshtid(item.getNewContractid());
//                    per.setState("0");                         //合同中
//                    commOutsourcingPersonSshtService.addCommOutsourcingPersonSsht(per);
//
//                }
//            } else {
//                CommOutsourcingPersonSsht per = new CommOutsourcingPersonSsht();
//                per.setJobid(jobid);
//                per.setSshtid(item.getOldContractid());
//                per.setState("0");                         //合同中
//                per = commOutsourcingPersonSshtService.queryDetail(per);
//                if (per != null) {
//                    per.setState("1");
//                    per.setEndtime(sysdate);
//                    commOutsourcingPersonSshtService.updateCommOutsourcingPersonSsht(per);
//                }
//                if ("1".equals(item.getNewOutsourcingtype())) {
//                    PpmRlwbsshtInfo newinfo = new PpmRlwbsshtInfo();
//                    newinfo.setJobid(item.getJobid());
//                    newinfo.setHtid(item.getNewContractid());
//                    newinfo.setStarttime(sysdate);
//                    ppmRlwbsshtInfoService.addPpmRlwbsshtInfo(newinfo);
//                } else if (!"0000".equals(item.getNewContractid())) {
//                    CommOutsourcingPersonSsht newper = new CommOutsourcingPersonSsht();
//                    newper.setJobid(jobid);
//                    newper.setStarttime(sysdate);
//                    newper.setSshtid(item.getNewContractid());
//                    newper.setState("0");                         //合同中
//                    commOutsourcingPersonSshtService.addCommOutsourcingPersonSsht(newper);
//                }
//
//            }
//
//
//        }
        // 新增 外包变更时增加在场状态 5.留场旧项目运维，6.留场新项目开发 k6991 2021-05-28
        // 同一个项目时为留场旧项目运维，反之为留场新项目开发
        if("0".equals(item.getNewOutsourcingtype())
            && "1".equals(item.getNewUsetype())) {
            if(item.getOldProjectno().equals(item.getNewProjectno())) {
                basic.setPresenceState("5");
            } else {
                // 根据选择的项目，判断若关联新的正式项目，则“在场情况”更新为正常；
                // 若关联新的临时项目， 则“在场情况”更新为留场新项目开发
                String projectAttr = ppmProjectBuildSoftService.queryProjectNoForProattrbutes(item.getNewProjectno());
                // 01 为正常 02 为临时项目
                if("01".equals(projectAttr)) {
                    basic.setPresenceState("1");
                }else {
                    basic.setPresenceState("6");
                }
            }
        } else {
            basic.setPresenceState("1");
        }
//        if (!"".equals(item.getNewContractid()) && !item.getNewContractid().equals(item.getOldContractid())) {
//            if ("1".equals(item.getOldOutsourcingtype())) {
//                if (null!=item.getOldContractid()&&!"".equals(item.getOldContractid())) {
//                    PpmRlwbsshtInfo info = new PpmRlwbsshtInfo();
//                    info.setJobid(item.getJobid());
//                    info.setHtid(item.getOldContractid());
//                    PpmRlwbsshtInfo infos = ppmRlwbsshtInfoService.queryDetail(info);
//                    if (infos != null) {
//                        infos.setEndtime(sysdate);
//                        ppmRlwbsshtInfoService.updatePpmRlwbsshtInfo(infos);
//                    }
//                }
//                if ("1".equals(item.getNewOutsourcingtype())) {
//                    PpmRlwbsshtInfo newinfo = new PpmRlwbsshtInfo();
//                    newinfo.setJobid(item.getJobid());
//                    newinfo.setHtid(item.getNewContractid());
//                    newinfo.setStarttime(sysdate);
//                    ppmRlwbsshtInfoService.addPpmRlwbsshtInfo(newinfo);
//                } else if (!"0000".equals(item.getNewContractid())) {
//                    CommOutsourcingPersonSsht per = new CommOutsourcingPersonSsht();
//                    per.setJobid(jobid);
//                    per.setStarttime(sysdate);
//                    per.setSshtid(item.getNewContractid());
//                    per.setState("0");                         //合同中
//                    commOutsourcingPersonSshtService.addCommOutsourcingPersonSsht(per);
//
//                }
//            } else {
//                CommOutsourcingPersonSsht per = new CommOutsourcingPersonSsht();
//                per.setJobid(jobid);
//                per.setSshtid(item.getOldContractid());
//                per.setState("0");                         //合同中
//                per = commOutsourcingPersonSshtService.queryDetail(per);
//                if (per != null) {
//                    per.setState("1");
//                    per.setEndtime(sysdate);
//                    commOutsourcingPersonSshtService.updateCommOutsourcingPersonSsht(per);
//                }
//                if ("1".equals(item.getNewOutsourcingtype())) {
//                    PpmRlwbsshtInfo newinfo = new PpmRlwbsshtInfo();
//                    newinfo.setJobid(item.getJobid());
//                    newinfo.setHtid(item.getNewContractid());
//                    newinfo.setStarttime(sysdate);
//                    ppmRlwbsshtInfoService.addPpmRlwbsshtInfo(newinfo);
//                } else if (!"0000".equals(item.getNewContractid())) {
//                    CommOutsourcingPersonSsht newper = new CommOutsourcingPersonSsht();
//                    newper.setJobid(jobid);
//                    newper.setStarttime(sysdate);
//                    newper.setSshtid(item.getNewContractid());
//                    newper.setState("0");                         //合同中
//                    commOutsourcingPersonSshtService.addCommOutsourcingPersonSsht(newper);
//                }
//
//            }
//
//
//        }

        PpmOutsourcingBasicService.updatePpmOutsourcingBasic(basic);
    }

    @Override
    @Transactional("defaultTx")
    public void outInfoChange() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sysdate = dtf.format(now);
        Map param = new HashMap();
        param.put("sysdate", sysdate);
        List<PpmOutsourcingChangeInfos> list = myBatisDao.selectListT(PPMOUTSOURCINGCHANGEAPPLY, param, "queryOutChanageInfos");
        PpmOutsourcingChangeInfos item = null;
        PpmOutsourcingChangeApply apply = new PpmOutsourcingChangeApply();
        PpmOutsourcingExt ext = new PpmOutsourcingExt();
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            nullConverNullString(item);
            String jobid = item.getJobid();
            PpmDaTeamUser user = new PpmDaTeamUser();
            user.setUserid(jobid);
            user = ppmDaTeamUserService.queryDetailByUserid(user);
            if (null != user) {
                if (!"".equals(item.getNewGroupid()) && !item.getNewGroupid().equals(item.getOldGroupid())) {
                    user.setTeamgrpupid(item.getNewGroupid());
                }
                if (!"".equals(item.getNewPosition()) && !item.getNewPosition().equals(item.getOldPosition())) {
                    user.setGw(item.getNewPosition());
                }
                if (!"".equals(item.getNewUsetype()) && !item.getNewUsetype().equals(item.getOldUsetype())) {
                    if ("0".equals(item.getNewUsetype())) {       //人包结算
                        user.setState(2);
                    } else if ("1".equals(item.getNewUsetype())) {  //项目结算
                        user.setState(3);
                    }
                }
                ppmDaTeamUserService.updatePpmDaTeamUser(user);

            }
            //外包主表更新
            updatePpmOutsourcingBasic(jobid, item, sysdate);
            //外包拓展表更新
            ext.setUserId(jobid);
            ext = ppmOutsourcingExtService.queryDetail(ext);
            if (null != ext) {
                if (null!=item.getRecommendedPrice()&&!"".equals(item.getRecommendedPrice())
                        &&item.getRecommendedPrice()!=0) {
                    ext.setPrice(item.getRecommendedPrice()+"");
                }
                if (!"".equals(item.getNewContractid())
                        && !item.getNewContractid().equals(item.getOldContractid())) {
                    ext.setContractId(item.getNewContractid());
                }
                ppmOutsourcingExtService.updatePpmOutsourcingExt(ext);
            }
            //更新流程表信息
            apply.setInstanceid(item.getInstanceid());
            apply = queryDetail(apply);
            apply.setStatus("2");
            updatePpmOutsourcingChangeApply(apply);


        }
    }

    /**
     * nullConverNullString
     * null转化为空字符串
     *
     * @param obj
     */
    private void nullConverNullString(Object obj) {
        try {
            if (null != obj) {
                Class classz = obj.getClass();
                //获取对象所有属性值
                Field[] fields = classz.getDeclaredFields();
                for (Field field : fields) {
                    Type t = field.getGenericType();
                    if (t.getTypeName().equals("java.lang.String")) {
                        Method m = null;

                        m = classz.getMethod("get" + change(field.getName()));

                        Object name = m.invoke(obj);
                        if (name == null) {
                            Method mtd = classz.getMethod("set" + change(field.getName()), String.class);
                            mtd.invoke(obj, "");
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw new BizException(e);
        }

    }

    /**
     * 将第一个字母转大写
     *
     * @param str
     * @return String
     */
    private String change(String str) {
        if (null != str) {
            StringBuffer sb = new StringBuffer(str);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return "";
        }
    }

}
