package com.work.ppm.application.services.onlinemanager.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.onlinemanager.OnlineInfoDto;
import com.work.ppm.application.dto.onlinemanager.OnlineSubProjectInfoDto;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineGndDto;
import com.work.ppm.application.dto.onlinemanager.PpmOnlineOrgareaDto;
import com.work.ppm.application.dto.onlinemanager.PpmProjectNewonlineDto;
import com.work.ppm.application.dto.onlinemanager.PpmProjectOnlinefjhDto;
import com.work.ppm.application.dto.onlinemanager.PpmTaskplanDto;
import com.work.ppm.application.dto.projectmanager.AapSystemDto;
import com.work.ppm.application.dto.projectmanager.DemandBackDto;
import com.work.ppm.application.dto.projectmanager.DemandFileDto;
import com.work.ppm.application.dto.projectmanager.DemandFlowLogDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandApplyDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandnewinfoDto;
import com.work.ppm.application.dto.projectmanager.PpmFhspbmpzDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftTreeDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectFileDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectOnlineversionDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.model.onlinemanager.PpmProjectNewonline;
import com.work.ppm.application.model.projectmanager.ext.PpmProjectBuildSoftInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.onlinemanager.IPpmOnlineGndService;
import com.work.ppm.application.services.onlinemanager.IPpmOnlineOrgareaService;
import com.work.ppm.application.services.onlinemanager.IPpmProductionDayService;
import com.work.ppm.application.services.onlinemanager.IPpmProjectNewonlineService;
import com.work.ppm.application.services.onlinemanager.IPpmProjectOnlinefjhService;
import com.work.ppm.application.services.onlinemanager.IPpmTaskplanService;
import com.work.ppm.application.services.onlinemanager.IPpmTeamqaFileCheckService;
import com.work.ppm.application.services.othermanager.IPpmDocumentCatalogService;
import com.work.ppm.application.services.othermanager.IPpmTecShenpiConfigService;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import com.work.ppm.application.services.projectmanager.IPpmDemandApplyService;
import com.work.ppm.application.services.projectmanager.IPpmDemandnewinfoService;
import com.work.ppm.application.services.projectmanager.IPpmFhspbmpzService;
import com.work.ppm.application.services.projectmanager.IPpmProjectBuildSoftService;
import com.work.ppm.application.services.projectmanager.IPpmProjectFileService;
import com.work.ppm.application.services.projectmanager.IPpmProjectOnlineversionService;
import com.work.ppm.application.services.projectmanager.IPpmProjectService;
import com.work.ppm.application.services.systemmanager.IAapSystemResponsibilityService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmSystParameterService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.lang.other.CodeGenerater;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 业务意向上线服务类
  */
@Service("ppmProjectNewonlineService")
@Slf4j
public class PpmProjectNewonlineService extends BaseDaoSupper implements IPpmProjectNewonlineService {
    /**
      * 实体类路径
      */
    protected static String PPMPROJECTNEWONLINE = "com.work.ppm.application.model.onlinemanager.PpmProjectNewonline";
    /**
     * 分行业务意向上线
     */
    protected final static String FLOW_NAME = "com.work.ppm.bps.newfhxmsxFJH";
    /**
     * 数据接口
     */
    @Autowired
    protected IMyBatisDao myBatisDao;
    /**
     * 机构
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;

    /**
     * 项目管理 接口
     */
    @Autowired
    private IPpmProjectBuildSoftService ppmProjectBuildSoftService;
    /**
     * 系统系数配置表 接口
     */
    @Autowired
    private IPpmSystParameterService ppmSystParameterService;
    /**
     * 项目信息 接口
     */
    @Autowired
    private IPpmProjectService ppmProjectService;
    /**
     * 项目信息 接口
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 上线日期 接口
     */
    @Autowired
    private IPpmProductionDayService ppmProductionDayService;
    /**
     * 奋进号上线 接口
     */
    @Autowired
    private IPpmProjectOnlinefjhService ppmProjectOnlinefjhService;
    /**
     * 上线范围 接口
     */
    @Autowired
    private IPpmOnlineOrgareaService ppmOnlineOrgareaService;
    /**
     *  人员表
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 日志服务 接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;
    /**
     * 上线版本服务 接口
     */
    @Autowired
    private IPpmProjectOnlineversionService ppmProjectOnlineversionService;
    /**
     * 系统关系 接口
     */
    @Autowired
    private IAapSystemResponsibilityService aapSystemResponsibilityService;
    /**
     * 业务意向发起 接口
     */
    @Autowired
    private IPpmDemandApplyService ppmDemandApplyService;
    /**
     * 团队QA文档审查 接口
     */
    @Autowired
    private IPpmTeamqaFileCheckService ppmTeamqaFileCheckService;
    /**
     * 文档日志保存类
     */
    @Autowired
    private IPpmDocumentCatalogService ppmDocumentCatalogService;
    /**
     * 审批配置
     */
    @Autowired
    private IPpmTecShenpiConfigService ppmTecShenpiConfigService;
    /**
     * 需求
     */
    @Autowired
    private IPpmDemandnewinfoService ppmDemandnewinfoService;
    /**
     * 分行审批配置
     */
    @Autowired
    private IPpmFhspbmpzService ppmFhspbmpzService;
    /**
     * 项目文件服务类
     */
    @Autowired
    private IPpmProjectFileService ppmProjectFileService;
    /**
     * 上线服务类
     */
    @Autowired
    private IPpmOnlineGndService ppmOnlineGndService;

    /**
     * 流程服务类
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;
    /**
     * 部门服务类
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 系统服务类
     */
    @Autowired
    private IAapSystemService aapSystemService;
    /**
     * 上线版本类
     */
    @Autowired
    private IPpmTaskplanService ppmTaskplanService;

    @Override
    public void queryOnlinePager(PpmProjectNewonlineDto ppmProjectNewonlineDto, Pager pager) {
        CommOrganizationDto orgitem = commOrganizationService
                .queryFHIdforUserId(ppmProjectNewonlineDto.getUserid());
        ppmProjectNewonlineDto.setCompid(orgitem.getCompid() + "");
        String date = DateUtil.dateToStr(ppmProjectNewonlineDto.getPlanonline(),"yyyy-MM-dd");
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProjectNewonlineDto,PpmProjectNewonlineDto.class);
        map.put("planonline",date);
        myBatisDao.selectPager(PPMPROJECTNEWONLINE, map,pager,"queryOnlinePager");
    }

    @Override
    public void queryPager(PpmProjectNewonlineDto ppmProjectNewonlineDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmProjectNewonlineDto,PpmProjectNewonlineDto.class);
        myBatisDao.selectPager(PPMPROJECTNEWONLINE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmProjectNewonline(PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        PpmProjectNewonline ppmProjectNewonline = JsonUtil.jsonToClass(ppmProjectNewonlineDto,
                PpmProjectNewonline.class);
        ppmProjectNewonline.setId(getNextSequence(PpmProjectNewonline.class));
        myBatisDao.executionPojo(PPMPROJECTNEWONLINE, ppmProjectNewonline, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmProjectNewonlineDto queryDetail(PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        PpmProjectNewonline ppmProjectNewonline = JsonUtil.jsonToClass(ppmProjectNewonlineDto,
                PpmProjectNewonline.class);
        PpmProjectNewonline data = myBatisDao.selectClass(PPMPROJECTNEWONLINE, ppmProjectNewonline,
                "queryDetail");
        PpmProjectNewonlineDto item = JsonUtil.jsonToClass(data, PpmProjectNewonlineDto
                .class);
        return item;
    }

    @Override
    public int updatePpmProjectNewonline(PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        PpmProjectNewonline ppmProjectNewonline = JsonUtil.jsonToClass(ppmProjectNewonlineDto,
                PpmProjectNewonline.class);
        return myBatisDao.executionPojo(PPMPROJECTNEWONLINE, ppmProjectNewonline, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public List<PpmProjectBuildSoftTreeDto> getPpmProjectBuildSoft(PpmProjectNewonlineDto ppmProjectNewonlineDto) {
        List<PpmProjectBuildSoftTreeDto> projectList = new ArrayList<>();
        projectList = ppmProjectBuildSoftService.queryProjectTree();
        return projectList;
    }

    @Transactional("defaultTx")
    @Override
    public Map<String, Object> addPpmProjectNewonlineFlow(OnlineInfoDto onlineInfo) {
        Map<String, Object> result = new HashMap<>();

        List<OnlineSubProjectInfoDto> onlineSubProjectInfo = onlineInfo.getOnlineSubProjectInfo();
        List<DemandFlowLogDto> demandFlowLogList = onlineInfo.getDemandFlowLogList();
        List<DemandFileDto> demandFileList = onlineInfo.getDemandFileList();

        PpmProjectOnlinefjhDto pinfo = new PpmProjectOnlinefjhDto();
        PpmProjectBuildSoftInfo obj = ppmProjectBuildSoftService.
                getProjectBuildSoftByDenamdID(onlineInfo.getProjectno());

        List<PpmProjectDto> projectView;
        if (obj != null && !"".equals(obj)) {
            String onlineno = CodeGenerater.generate("OFFH", 20, "yyyyMMdd");
            projectView = ppmProjectService.getPpmSubProjectView(obj.getProjectno(), null);
            PpmOnlineOrgareaDto[] ppmOnlineOrgareas = new PpmOnlineOrgareaDto[projectView.size()];
            PpmProjectOnlineversionDto[] ppmProjectOnlineversions = new PpmProjectOnlineversionDto[projectView.size()];
            String flag = "00";//判断是否都是本次不上线
            //String flagA = "00";//判断上线中是否存在A类系统
            //String isonlyOtherSys = "00";//判断是否只有特殊系统
            //String sysids = ppmSystParameterService.getValueByKey("OTHER_SYSTEM_ID");

            for (int i = 0; i < projectView.size(); i++) {
                String orgarea = "03";
                //String coupledsys = projectView.get(i).getCoupledsys();
                //String safetytesttype = "";
                for (int j=0; j < onlineSubProjectInfo.size(); j++) {
                    boolean isSH = false;
                    if ((projectView.get(i).getSysname().equals(onlineSubProjectInfo.get(j).getSysName())
                            || projectView.get(i).getSysno().equals(onlineSubProjectInfo.get(j).getSysNo()))
                            && "否".equals(projectView.get(i).getCoupledsys())) {
                        orgarea = "01";
                        flag = "01";
                        isSH = true;
                    }
                    //互金主系统仍为计划
                    if (("A+类".equals(projectView.get(i).getVel()) || "A类".equals(projectView.get(i).getVel()))
                            && !checkOnLineASysnoDeal(projectView.get(0).getSysno()) && isSH) {
                        pinfo.setIsdfxfjh("01");
                    }

                    //if (("A+类".equals(projectView.get(i).getVel())||"A类".equals(projectView.get(i).getVel()))
                    //        && checkOnLineASysnoDeal(projectView.get(i).getSysno()) && isSH) {
                        //flagA = "01";
                    //}

                    //if (!sysids.contains(projectView.get(i).getSysno()) && isSH) {
                        //isonlyOtherSys = "01";
                    //}
                }

                ppmOnlineOrgareas[i] = this.addPpmOnlineOrgarea(projectView.get(i),
                        onlineInfo, orgarea, onlineno, onlineSubProjectInfo);
                ppmProjectOnlineversions[i] = this.addPpmOnlineVersion(projectView.get(i), orgarea, onlineno);
                projectView.get(i).setOrg(orgarea);
            }

            if("00".equals(flag)){
                result.put("status", "0000");
                result.put("msg", "所有系统都是本次不上线！请检查");
                return  result;
            }

            if ("01".equals(obj.getProjectscale()) ||
                    "02".equals(obj.getProjectscale()) ||
                    "03".equals(obj.getProjectscale())) {
                String reviewno = onlineInfo.getReviewno();
                if (reviewno == null || "".equals(reviewno)) {
                    result.put("status", "0000");
                    result.put("msg", "大中型项目需要上线评审！请检查");
                    return  result;
                } else {
                    List<PpmProjectNewonline> onlineList = isExitReviewByid(reviewno);
                    if (onlineList != null && onlineList.size() > 0) {
                        result.put("status", "0000");
                        result.put("msg", "上线评审已经被其他上线关联，请重新选择！");
                        return  result;
                    } else {
                        pinfo.setReviewid(reviewno);
                        pinfo.setReviewstatus("1");
                    }
                }
            }

            pinfo.setApply(onlineInfo.getApply());//申请日期
            pinfo.setUserid(onlineInfo.getUserid());//申请人
            pinfo.setProjectno(obj.getProjectno());//项目编号
            pinfo.setOnlineno(onlineno);//编号
            pinfo.setProjecttype(obj.getProjecttype());//项目类型
            pinfo.setApply(onlineInfo.getApply());//上线时间
            pinfo.setProjectscale(obj.getProjectscale());//项目规模
            pinfo.setSysno(obj.getSysno());//关联主系统
            pinfo.setBusideptno(obj.getBusideptno());//牵头业务部门
            pinfo.setTestcontent(onlineInfo.getTestcontent());
            pinfo.setTeststatus(onlineInfo.getTeststatus());
            String operateType = "01";//投产类型,分行特殊处理，只有计划上线
            pinfo.setContent(onlineInfo.getContent());//上线内容概述(两句话简述)
            if ("全部上线".equals(onlineInfo.getOrgarea())) {
                pinfo.setOrgarea("02");//上线范围
            }else{
                pinfo.setOrgarea("01");//上线范围
            }

            pinfo.setOperatetype(operateType);//上线类型
            pinfo.setPlanonline(onlineInfo.getPlanonline());//申请上线日期
            pinfo.setIsdel(0);
            pinfo.setCreatetime(new Date());
            pinfo.setCreators(onlineInfo.getUserid());
            pinfo.setFjhreason(onlineInfo.getFjhreason());//非计划原因
            pinfo.setBfsxreason(onlineInfo.getBfsxreason());

            HashMap<String, String> relaData = new HashMap<String, String>();
            String kjuserid = this.getKeji(pinfo);
            String bmfzr = ppmProjectBuildSoftService.getBmfzrByProjectno(pinfo.getProjectno());
            String ywuserid =  "";
            if("".equals(bmfzr) || bmfzr == null ){
                ywuserid = "";
            }else{
                ywuserid = bmfzr;
            }
            pinfo.setYsuserid(ywuserid);
            if (kjuserid.equals(ywuserid)) {
                relaData.put("users", kjuserid);
            } else {
                relaData.put("users", kjuserid + "," + ywuserid);
            }
            relaData.put("status", "-1");

            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
            String processName = "";
            if ("01".equals(pinfo.getOrgarea())) {
                processName = obj.getProjectname() + ft.format(pinfo.getPlanonline()) + "部分上线";
            } else {
                processName = obj.getProjectname() + ft.format(pinfo.getPlanonline()) + "全部上线";
            }
            String userId = onlineInfo.getUserid();
            BpsWorkFlowDto bps = null;
            String instanceid = "";
            String xid = "";
            try {
                bps = new BpsWorkFlowDto();
                // 开启bps事务
                bps.setFlowUrl("newfhxmsxFJH");
                bps.setProcessDefName(FLOW_NAME);
                bps.setProcessInstName(processName);
                bps.setProcessInstDesc("分行业务意向上线发起");
                bps.setUserId(onlineInfo.getUserid());
                bps.setRelaData(relaData);
                bps.setConclusion("01");
                bps.setApplydesc(bps.getProcessInstDesc());
                xid = BpsUtil.transactionBegin(pinfo.getUserid());
                instanceid = BpsUtil.createProcessAndFinishFirst(bps, xid);   //发起流程,processInstID为返回的流程id 唯一

                // 更新流程编号
                pinfo.setInstanceid(instanceid);

                ppmProjectOnlinefjhService.addPpmProjectOnlinefjh(pinfo);
                ppmOnlineOrgareaService.addPpmOnlineOrgareaBatch(ppmOnlineOrgareas);
                ppmProjectOnlineversionService.addPpmProjectOnlineversionBatch(ppmProjectOnlineversions);

                for(int i=0;i<projectView.size();i++){
                    ppmProjectService.updatePpmProjectByUserid(projectView.get(i), userId);
                }

                //创建非计划窗口
                this.createTaskPlanAction(projectView, pinfo, ppmOnlineOrgareas);

                // 提交事务
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);

                this.saveDocument(demandFileList, instanceid, userId);
                this.addDemandFlowLog(demandFlowLogList, instanceid, onlineInfo);

                result.put("onlineNum", pinfo.getOnlineno());
                result.put("status", "1111");
                return  result;
            } catch (Exception e) {
                if(bps != null){
                    BpsUtil.transactionRollback(userId, xid);
                }

                log.error(e.getMessage(), e);
                result.put("msg", "流程创建失败！请检查数据");
                result.put("status", "0000");
                return  result;
            }
        }

        return result;
    }

    @Transactional("defaultTx")
    @Override
    public String recallPpmProjectNewonlineFlow(DemandBackDto demandBackDto) {
        String msg = "0000";
        try {
            List<PpmProjectOnlinefjhDto> fjhDtos = ppmProjectOnlinefjhService.
                    queryNewFjhOnileFlow(demandBackDto.getId());

            if(fjhDtos != null && fjhDtos.size() > 0){
                PpmProjectOnlinefjhDto fjhDto = new PpmProjectOnlinefjhDto();
                fjhDto.setInstanceid(fjhDtos.get(0).getInstanceid());
                fjhDto.setIsdel(0);
                PpmProjectOnlinefjhDto fjhEntity = ppmProjectOnlinefjhService.queryDetailByInstanceid(fjhDto);

                //查询之前的日志记录，看看有没有已经审批过
                PpmFlowLog flowlog = new PpmFlowLog();
                flowlog.setFlowcaseno(fjhDtos.get(0).getInstanceid());
                flowlog.setApplydesc("分行科技二级部经理审批");
                flowlog.setConclusion("01");
                List<PpmFlowLogDto> flowlogList = ppmFlowLogService.queryOnlineSplogs(flowlog);
                if(fjhEntity != null && flowlogList.size() < 1){
                    BpsWorkFlowDto dto = new BpsWorkFlowDto();
                    dto.setProcessInstId(fjhDtos.get(0).getInstanceid());
                    dto.setUserId(demandBackDto.getUserid());
                    BpsUtil.terminateProcessInstance(dto);  //删除流程及待办
                    fjhEntity.setIsdel(1);
                    ppmProjectOnlinefjhService.updatePpmProjectOnlinefjh(fjhEntity);

                    //删除团队QA,如果退回,则删除团队QA文档审查流程   begin
                    if("05".equals(fjhEntity.getProjectscale()) || "04".equals(fjhEntity.getProjectscale())){
                        log.info("1111");
                        //delTeamQAFileCheckFlow(fjhEntity.getInstanceid(), demandBackDto.getUserid());
                    }
                    msg = "1111";
                }else{
                    msg = "3333";
                }
            }else{
                msg = "2222";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            msg = "0000";
        }

        return msg;
    }

    @Transactional("defaultTx")
    @Override
    public String approveFlowByEJBManagers(PpmProjectOnlinefjhDto onlinefjh, BpsWorkFlowDto bps) {
        Map<String, String> map = new HashMap<>();
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                PpmDemandnewinfoDto demandnewinfoDto = ppmDemandnewinfoService.getdemandByProjectno(
                        onlinefjh.getProjectno());
                map.put("status", "2");
                map.put("users", demandnewinfoDto.getDemanduserid());
                //删除流程
                backflow(bps.getProcessInstId(), bps.getUserId(),
                        bps.getSuggest(), onlinefjh.getIssueid(), onlinefjh.getOnlineno());
                this.httpClientDemand("failed", onlinefjh.getOnlineno(),
                        eosDictEntryService.getTypeidForDetail("NEWDEMAND", "03").getDictname());
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                PpmDemandnewinfoDto demandnewinfoDto = ppmDemandnewinfoService.getdemandByProjectno(
                        onlinefjh.getProjectno());
                map.put("status", "-1");
                map.put("users", demandnewinfoDto.getYwzjl());
                bps.setRelaData(map);
                bps.setApplydesc("分行科技二级部经理审批");
                BpsUtil.finshWorkItem(bps, xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            }
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }

        return BpsUtil.queryProcessInfo(bps);
    }

    @Transactional("defaultTx")
    @Override
    public String approveFlowByBusZJL(PpmProjectOnlinefjhDto onlinefjh, BpsWorkFlowDto bps) {
        Map<String, String> map = new HashMap<>();
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                PpmDemandnewinfoDto demandnewinfoDto = ppmDemandnewinfoService.getdemandByProjectno(
                        onlinefjh.getProjectno());
                map.put("status", "2");
                map.put("users", demandnewinfoDto.getDemanduserid());
                //删除流程
                backflow(bps.getProcessInstId(), bps.getUserId(),
                        bps.getSuggest(), onlinefjh.getIssueid(), onlinefjh.getOnlineno());
                this.httpClientDemand("failed", onlinefjh.getOnlineno(),
                        eosDictEntryService.getTypeidForDetail("NEWDEMAND", "03").getDictname());
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                PpmDemandnewinfoDto demandnewinfoDto = ppmDemandnewinfoService.getdemandByProjectno(
                        onlinefjh.getProjectno());
                map.put("status", "-1");
                map.put("users", demandnewinfoDto.getFhKjzjl());
                bps.setRelaData(map);
                bps.setApplydesc("分行业务总经理审批");
                BpsUtil.finshWorkItem(bps, xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            }
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }

        return BpsUtil.queryProcessInfo(bps);
    }

    @Transactional("defaultTx")
    @Override
    public String approveFlowByTechZJL(PpmProjectOnlinefjhDto onlinefjh, BpsWorkFlowDto bps) {
        Map<String, String> map = new HashMap<>();
        String xid = "";
        try {
            xid = BpsUtil.transactionBegin(bps.getUserId());
            // 流程状态 02 退回， -1：继续流转
            if ("02".equals(bps.getConclusion())) {
                PpmDemandnewinfoDto demandnewinfoDto = ppmDemandnewinfoService.getdemandByProjectno(
                        onlinefjh.getProjectno());
                map.put("status", "2");
                map.put("users", demandnewinfoDto.getDemanduserid());
                // 同意
                backflow(bps.getProcessInstId(), bps.getUserId(),
                        bps.getSuggest(), onlinefjh.getIssueid(), onlinefjh.getOnlineno());
                this.httpClientDemand("failed", onlinefjh.getOnlineno(),
                        eosDictEntryService.getTypeidForDetail("NEWDEMAND", "03").getDictname());
            } else if ("01".equals(bps.getConclusion()) || "00".equals(bps.getConclusion())) {
                map.put("status", "-1");
                map.put("users", bps.getUserId());

                PpmProjectOnlinefjhDto onlinefjhDto = new PpmProjectOnlinefjhDto();
                onlinefjhDto.setInstanceid(bps.getProcessInstId());
                PpmProjectOnlinefjhDto onlinefjhEntity = ppmProjectOnlinefjhService.queryDetail(onlinefjhDto);
                this.oversave(onlinefjhEntity, bps.getUserId());
                this.overupdatePpmProjectsoft(onlinefjhEntity, bps.getUserId());
                bps.setRelaData(map);
                bps.setApplydesc("分行科技总经理审批");
                BpsUtil.finshWorkItem(bps, xid);
                BpsUtil.transactionCommit(bps.getUserId(), xid);
                BpsUtil.flowLog(bps);
            }
        } catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }

        return BpsUtil.queryProcessInfo(bps);
    }


    /**
     * 判断互金系统是否在业务字典中配置过
     * @return boolean
     */
    public Boolean checkOnLineASysnoDeal(String sysno){
        Boolean  isSpeciSysno = true;
        EosDictEntry entry = eosDictEntryService.getTypeidForDetail("PPM_ONLINE_SYSNO", sysno);
        if (entry != null) {
            isSpeciSysno = false;
        }

        return isSpeciSysno;
    }

    /**
     * 查询是否已关联其他上线流程
     *
     * @param reviewno
     * @return List
     */
    private List<PpmProjectNewonline> isExitReviewByid(String reviewno) {
        Map<String,Object> map = new HashMap<>();
        map.put("reviewno", reviewno);
        List<PpmProjectNewonline> list = myBatisDao.selectListT(PPMPROJECTNEWONLINE, map, "isExitReviewByid");
        return list;
    }

    /**
     * 创建非计划窗口
     * @param ppmProjectOnlinefjhDto
     * @param ppmprojectDto
     */
    private void createTaskPlan(PpmProjectOnlinefjhDto ppmProjectOnlinefjhDto, PpmProjectDto ppmprojectDto) {
        log.info("创建非计划窗口"+ppmProjectOnlinefjhDto.getProjectno() + ppmprojectDto.getProjectno());
        try {
            DateFormat dataformat2 = new SimpleDateFormat("yyyyMMdd");
            // 查询树所有系统
            AapSystemDto aapSystemDto = aapSystemService.queryPlansystem(ppmprojectDto.getSysno());
            if (StringUtil.isNotEmpty(aapSystemDto)) {
                log.info("系统存在"+ppmProjectOnlinefjhDto.getInstanceid() + ppmprojectDto.getSysno());
                String ghnum = "GH_" + aapSystemDto.getSystemId() + "_"
                        + dataformat2.format(ppmProjectOnlinefjhDto.getPlanonline());
                List<PpmTaskplanDto> ppmTaskplanDtos = ppmTaskplanService.queryTaskPlan(ghnum);

                if (ppmTaskplanDtos.size() <= 0) {
                    log.info("版本不存在插入开始" + ppmProjectOnlinefjhDto.getInstanceid() + ppmprojectDto.getSysno());
                    PpmTaskplanDto plan = new PpmTaskplanDto();
                    plan.setPlannoPpm(ghnum);
                    plan.setPlanname(aapSystemDto.getSysName() + "_"
                            + dataformat2.format(ppmProjectOnlinefjhDto.getPlanonline()));
                    plan.setPlanuser(aapSystemDto.getPrincipala());
                    plan.setCreateuser(aapSystemDto.getPrincipala());
                    plan.setCreatetime(new Date());
                    plan.setPlantype("02");// 类型01：固定规划
                    plan.setState("03");
                    plan.setSysno(ppmprojectDto.getSysno());
                    plan.setPlandate(ppmProjectOnlinefjhDto.getPlanonline());
                    ppmTaskplanService.addPpmTaskplan(plan);
                    log.info("版本不存在插入结束" + ppmProjectOnlinefjhDto.getInstanceid() + ppmprojectDto.getSysno());
                } else {
                    log.info("版本存在" + ppmProjectOnlinefjhDto.getInstanceid() + ppmprojectDto.getSysno());
                    if ("01".equals(ppmProjectOnlinefjhDto.getIsdfxfjh())
                            && ("A+类".equals(ppmprojectDto.getVel()) || "A类"
                            .equals(ppmprojectDto.getVel())) && checkOnLineASysnoDeal(ppmprojectDto.getSysno())) {
                        log.info("版本存在并且不满足"+ppmProjectOnlinefjhDto.getInstanceid()+ppmprojectDto.getSysno());
                        PpmTaskplanDto ppmTaskplanDto = new PpmTaskplanDto();
                        ppmTaskplanDto.setState("02");
                        ppmTaskplanDto.setPlantype("01");
                        ppmTaskplanDto.setCreatetime(new Date());
                        ppmTaskplanDto.setPlannoPpm(ghnum);
                        ppmTaskplanService.updatePpmTaskplan(ppmTaskplanDto);
                    }
                }
            }
        } catch (Exception err) {
            log.error("[ERROR]：-" + err.getMessage(), err);
        }
    }

    /**
     * 添加业务意向日志
     * @param demandFlowLogList
     * @param instanceid
     * @param onlineInfo
     */
    private void addDemandFlowLog(List<DemandFlowLogDto> demandFlowLogList, String instanceid,
                                  OnlineInfoDto onlineInfo) {
        if (demandFlowLogList!=null && !"".equals(demandFlowLogList)) {
            for (int i = 0; i < demandFlowLogList.size(); i++) {
                DemandFlowLogDto demandFlowLog = demandFlowLogList.get(i);
                PpmFlowLog log = new PpmFlowLog();
                log.setCreatetime(demandFlowLog.getCreatetime());
                log.setUserid(demandFlowLog.getUserid());
                log.setCreators(demandFlowLog.getUserid());
                log.setDeptno(commEmployeeService.getDeptNo(demandFlowLog.getUserid()));
                if ("0".equals(demandFlowLog.getConclusion())
                        || "通过".equals(demandFlowLog.getConclusion())
                        || "同意".equals(demandFlowLog.getConclusion())) {
                    log.setConclusion("01");
                } else if ("提交".equals(demandFlowLog.getConclusion())) {
                    log.setConclusion("00");
                } else {
                    log.setConclusion("02");
                }

                if (StringUtils.isNotEmpty(demandFlowLog.getSuggest())) {
                    log.setSuggest(demandFlowLog.getSuggest() + "【奋进号】");
                } else if ("上线发起".equals(demandFlowLog.getActivename())) {
                    log.setSuggest("提交申请【奋进号】");
                    log.setConclusion("00");
                } else if ("科技上线审核".equals(demandFlowLog.getActivename())) {
                    log.setSuggest("同意【奋进号】");
                }

                log.setFlowcaseno(instanceid);
                log.setExamine(demandFlowLog.getCreatetime());
                log.setApplydesc(demandFlowLog.getActivename());

                if ("1".equals(onlineInfo.getOnlineType())) {//分行上线
                    log.setFlowdraftno(FLOW_NAME);
                }

                log.setIsdel(0);
                ppmFlowLogService.addPpmFlowLog(log);
            }
        }
    }

    /**
     * 保存文档
     * @param demandFileList
     * @param instanceid
     * @param userID
     */
    private void saveDocument(List<DemandFileDto> demandFileList, String instanceid, String userID) {
        if(demandFileList!=null&&!"".equals(demandFileList)){
            for(int t=0;t<demandFileList.size();t++){
                DemandFileDto demandFile = demandFileList.get(t);
                ppmDocumentCatalogService.saveDoucment(demandFile, instanceid, userID);
            }
        }
    }

    /**
     * 组装上线相关信息
     * @param ppmProjectDto 项目实体
     * @param onlineInfo 上线信息
     * @param orgarea 上线范围
     * @param onlineno 上线编号
     * @return PpmOnlineOrgareaDto
     */
    private PpmOnlineOrgareaDto addPpmOnlineOrgarea(PpmProjectDto ppmProjectDto,
                                                    OnlineInfoDto onlineInfo,
                                                    String orgarea, String onlineno,
                                                    List<OnlineSubProjectInfoDto> onlineSubProjectInfo
    ) {
        PpmOnlineOrgareaDto ppmOnlineOrgarea = new PpmOnlineOrgareaDto();
        ppmOnlineOrgarea.setProjectno(ppmProjectDto.getSubprojectno());
        ppmOnlineOrgarea.setPlanonline(onlineInfo.getPlanonline());
        ppmOnlineOrgarea.setOrgarea(orgarea);
        ppmOnlineOrgarea.setIsdel(0);
        ppmOnlineOrgarea.setCreators(onlineInfo.getUserid());
        ppmOnlineOrgarea.setCreatetime(new Date());
        ppmOnlineOrgarea.setOnlineno(onlineno);
        ppmOnlineOrgarea.setIsonline("01");
        for (int i = 0; i < onlineSubProjectInfo.size(); i++) {
            OnlineSubProjectInfoDto onlineSubProjectInfoDto = onlineSubProjectInfo.get(i);
            if (ppmProjectDto.getSysno().equals(onlineSubProjectInfoDto.getSysNo())) {
                if(onlineSubProjectInfoDto.isAssortTest()) {
                    ppmOnlineOrgarea.setCoupledsys("是");
                } else {
                    ppmOnlineOrgarea.setCoupledsys("否");
                }

                ppmOnlineOrgarea.setSafetytesttype(
                        strInternetChangesScenes(
                                onlineSubProjectInfoDto.getInternetChangesScenes()));

                ppmOnlineOrgarea.setSafetytestname(
                        StringUtils.join(
                                onlineSubProjectInfoDto.getInternetChangesScenes(), ","));

            }
        }

        return ppmOnlineOrgarea;
    }

    /**
     * 组装上线版本信息
     * @param ppmProjectDto 项目实体
     * @param orgarea 上线范围
     * @param onlineno 上线编号
     * @return PpmProjectOnlineversionDto
     */
    private PpmProjectOnlineversionDto addPpmOnlineVersion(PpmProjectDto ppmProjectDto,
                                                           String orgarea, String onlineno) {
        PpmProjectOnlineversionDto projectOnlineversionDto = new PpmProjectOnlineversionDto();
        projectOnlineversionDto.setProjectno(ppmProjectDto.getProjectno());
        projectOnlineversionDto.setOnlineno(onlineno);
        projectOnlineversionDto.setSubprojectno(ppmProjectDto.getSubprojectno());
        projectOnlineversionDto.setVersiontype(orgarea);
        projectOnlineversionDto.setIsdel("0");
        return projectOnlineversionDto;
    }

    /**
     * 创建非计划窗口
     * @param projectView 项目视图
     * @param pinfo 奋进号信息
     */
    private void createTaskPlanAction(List<PpmProjectDto> projectView,
                                      PpmProjectOnlinefjhDto pinfo, PpmOnlineOrgareaDto[] ppmOnlineOrgareas) {
        for(int i=0;i<projectView.size();i++){
            PpmProjectDto ppmProjectDto = projectView.get(i);
            String sysno = ppmProjectDto.getSysno();
            AapSystemDto aapSystemDto = aapSystemService.querySystemIsZH(sysno);
            if (StringUtil.isNotEmpty(aapSystemDto)) {
                for (int j=0; j<ppmOnlineOrgareas.length; j++) {
                    PpmOnlineOrgareaDto ppmOnlineOrgareaDto = ppmOnlineOrgareas[j];
                    checkZHSysnoISCreatePlanTask(ppmOnlineOrgareaDto, sysno, ppmProjectDto, pinfo);
                }
            }
        }
    }

    /**
     * 检查是否总行是否生成版本
     * @param ppmOnlineOrgareaDto
     * @param sysno
     * @param ppmProjectDto
     * @param pinfo
     */
    private void checkZHSysnoISCreatePlanTask(PpmOnlineOrgareaDto ppmOnlineOrgareaDto,
                                              String sysno, PpmProjectDto ppmProjectDto,
                                              PpmProjectOnlinefjhDto pinfo){
        log.info("系统编号:" + sysno);
        if (ppmOnlineOrgareaDto.getProjectno().equals(ppmProjectDto.getSubprojectno())
                && "否".equals(ppmOnlineOrgareaDto.getCoupledsys())) {
            if ("02".equals(pinfo.getOperatetype())) { //非计划上线
                // 保存未生效非计划窗口
                createTaskPlan(pinfo, ppmProjectDto);
            } else if ("01".equals(pinfo.getOperatetype()) &&
                    ("A+类".equals(ppmProjectDto.getVel())
                            || "A类".equals(ppmProjectDto.getVel())) &&
                    "01".equals(pinfo.getIsdfxfjh()) &&
                    checkOnLineASysnoDeal(ppmProjectDto.getSysno()) &&
                    ppmProjectDto.getSubprojectno().indexOf("_001") == -1) { //计划上线非主项目
                // 保存未生效非计划窗口
                createTaskPlan(pinfo, ppmProjectDto);
            }
        }
    }

    /**
     * 获取二级部科技审批人员
     * @param pinfo
     * @return String
     */
    private String getKeji(PpmProjectOnlinefjhDto pinfo) {
        String kjuserid = "";
        try {
            PpmDemandApplyDto demandApplyEntity = ppmDemandApplyService.getdemandByProjectno(pinfo.getProjectno());
            if (demandApplyEntity != null) {
                CommOrganizationDto orgitem = commOrganizationService.queryFHIdforUserId(
                        demandApplyEntity.getDemanduserid());

                if (null == orgitem) {
                    throw new BizException(ApiCode.FAIL, "分行研发二级部经理未配置");
                }

                PpmFhspbmpzDto rditem = ppmFhspbmpzService.queryDetail(orgitem.getCompid() + "");
                kjuserid = rditem.getFhyfjl();
            }
        } catch (Exception ee1) {
            log.error(ee1.toString(), ee1);
        }

        return kjuserid;
    }

    /**
     * 结束保存归档
     *
     * @param onlineInfo 上线信息
     * @param userID userID
     */
    public void oversave(PpmProjectOnlinefjhDto onlineInfo, String userID) {

        PpmProjectOnlinefjhDto hs = ppmProjectOnlinefjhService.getOnlineInfo(onlineInfo);

        PpmProjectFileDto ppmProjectFile = new PpmProjectFileDto();
        ppmProjectFile.setProjectno(hs.getProjectno());
        ppmProjectFile.setUserid(hs.getUserid());
        ppmProjectFile.setApply(hs.getApply());
        ppmProjectFile.setDeptno(hs.getDeptno());
        ppmProjectFile.setReviewtime(Calendar.getInstance().getTime());
        ppmProjectFile.setOperatetype(hs.getOperatetype());
        ppmProjectFile.setPlanonline(hs.getPlanonline());
        ppmProjectFile.setProjectname(hs.getProjectname());
        ppmProjectFile.setOnlineno(hs.getOnlineno());
        ppmProjectFile.setSysno(hs.getSysno());
        ppmProjectFile.setBusiuserid(hs.getBusiuserid());
        ppmProjectFile.setBusideptno(hs.getBusideptno());
        ppmProjectFile.setTectdeptno(hs.getTecdeptno());
        ppmProjectFile.setTecuserid(hs.getManauserid());
        ppmProjectFile.setIsdel(0);
        ppmProjectFile.setCreators(userID);
        ppmProjectFile.setCreatetime(Calendar.getInstance().getTime());
        ppmProjectFileService.addPpmProjectFile(ppmProjectFile);

        PpmProjectNewonlineDto online = new PpmProjectNewonlineDto();
        online.setOnlineno(onlineInfo.getOnlineno());
        online.setProjectno(onlineInfo.getProjectno());
        online.setUserid(onlineInfo.getUserid());
        online.setApply(onlineInfo.getApply());
        online.setDeptno(onlineInfo.getDeptno());
        online.setProjecttype(onlineInfo.getProjecttype());
        online.setProjectscale(onlineInfo.getProjectscale());
        online.setSysno(onlineInfo.getSysno());
        online.setBusideptno(onlineInfo.getBusideptno());
        online.setOperatetype(onlineInfo.getOperatetype());
        online.setPlanonline(onlineInfo.getPlanonline());
        online.setOrgarea(onlineInfo.getOrgarea());
        online.setInstanceid(onlineInfo.getInstanceid());
        online.setIsdel(0);
        online.setIsdfxfjh(onlineInfo.getIsdfxfjh());
        online.setCreators(onlineInfo.getUserid());
        online.setCreatetime(Calendar.getInstance().getTime());
        online.setBfsxreason(onlineInfo.getBfsxreason());
        online.setContent(onlineInfo.getContent());
        online.setTeststatus(onlineInfo.getTeststatus()); //测试结论
        online.setTestcontent(onlineInfo.getTestcontent()); //测试内容
        online.setFjhreason(onlineInfo.getFjhreason());
        CommDepartmentDto commDepartmentDto = commDepartmentService.queryParentDeptId(
                commEmployeeService.getDeptNo(onlineInfo.getUserid()));
        online.setCompid(String.valueOf(commDepartmentDto.getCompid()));
        this.addPpmProjectNewonline(online);

        //创建QA文档审查流程
        //xmDocumentCheck(online);
        // 短信通知流程结束
        //sendMsgForXMSXFinish(onlineInfo.getUserid(), onlineInfo.getProjectno(), "02");
    }


    /***
     * 结束更新项目信息
     *
     * @param online 上线信息
     * @param userID userID
     */
    private void overupdatePpmProjectsoft(PpmProjectOnlinefjhDto online,
                                          String userID) {
        List<PpmProjectDto> projectLists = ppmProjectService.queryProjectListByProjectno(online.getProjectno());
        for (int i = 0; i < projectLists.size(); i++) {
            PpmProjectDto ppmProjectDto = projectLists.get(i);
            if (!"1".equals(ppmProjectDto.getIsproduction())) {
                if ("01".equals(ppmProjectDto.getOrgarea())
                        || "02".equals(ppmProjectDto.getOrgarea())) {
                    ppmProjectDto.setIsonline("等待上线");
                } else if ("03".equals(ppmProjectDto.getOrgarea())) {
                    ppmProjectDto.setIsonline("不上线");
                } else {
                    ppmProjectDto.setIsonline("无需上线");
                }
                ppmProjectDto.setLastmoduser(userID);
                ppmProjectDto.setLastmodtime(Calendar.getInstance().getTime());

                if("1".equals(online.getIsproduct())){     //是，无需投产
                    ppmProjectDto.setActualdate(online.getPlanonline());  //计划上线日期取计划上线日期
                    ppmProjectDto.setStatus("已关闭");  //独立项目更新项目状态
                }

                ppmProjectService.updatePpmProject(ppmProjectDto);
            }
        }
        // 根系子项目上线范围为等待上线
        DateFormat dataformat2 = new SimpleDateFormat("yyyy-MM-dd");
        PpmProjectDto ppmProjectDtoQuery = new PpmProjectDto();
        ppmProjectDtoQuery.setProjectno(online.getProjectno());
        ppmProjectDtoQuery.setOnlineno(online.getOnlineno());
        ppmProjectDtoQuery.setPlanonline(dataformat2.format(online.getPlanonline()));

        List<PpmProjectDto> ppmProjectDtoListOnline = ppmProjectService.queryPpmProjectList(ppmProjectDtoQuery);
        for (int i = 0; i < ppmProjectDtoListOnline.size(); i++) {
            PpmOnlineOrgareaDto orgareaDto = new PpmOnlineOrgareaDto();
            orgareaDto.setIsonline("02");
            orgareaDto.setOnlineno(online.getOnlineno());
            orgareaDto.setProjectno(ppmProjectDtoListOnline.get(i).getProjectno());
            // 上线情况置为等待上线
            ppmOnlineOrgareaService.updateOrgareaByOnlineno(orgareaDto);
        }

        for (int j = 0; j < projectLists.size(); j++) {
            String users = "";
            double totalPoint = 0.0;
            double totalWork = 0.0;
            PpmOnlineGndDto gnd = new PpmOnlineGndDto();
            gnd.setOnlineno(online.getOnlineno());
            gnd.setProjectno(online.getProjectno());
            gnd.setPlaneonline(online.getPlanonline());
            gnd.setSubprojectno(projectLists.get(j).getSubprojectno());
            gnd.setUserid(users);
            gnd.setWkgnd((long) totalPoint);
            gnd.setWorkload(String.valueOf(totalWork));
            ppmOnlineGndService.addPpmOnlineGnd(gnd);
        }
        try {
            this.httpClientDemand("passed", online.getOnlineno(),
                    eosDictEntryService.getTypeidForDetail("NEWDEMAND", "03").getDictname());
        } catch (Exception ee) {
            log.error(ee.getMessage(), ee);
        }
    }

    /**
     * 退回删除流程
     * @param processInstID
     * @param userID
     * @param suggest
     * @param issueid
     * @param onlineNum
     */
    @Transactional("defaultTx")
    public void backflow(String processInstID,String userID,String suggest,String issueid,String onlineNum){
        try {
            BpsWorkFlowDto dto = new BpsWorkFlowDto();
            dto.setProcessInstId(processInstID);
            dto.setUserId(userID);
            BpsUtil.terminateProcessInstance(dto);

            PpmProjectFlow ppmProjectFlow = new PpmProjectFlow();
            ppmProjectFlow.setInstanceid(processInstID);
            ppmProjectFlow.setLastmodtime(new Date());
            ppmProjectFlow.setFlowstatus("已删除");
            ppmProjectFlow.setClr("");
            ppmProjectFlowService.updatePpmProjectFlow(ppmProjectFlow);

            PpmFlowLog flowlog = new PpmFlowLog();
            flowlog.setFlowcaseno(processInstID);
            flowlog.setIsfalse("01");
            ppmFlowLogService.deletePpmFlowLog(flowlog);

            PpmFlowLog log1 = new PpmFlowLog();
            log1.setCreatetime(new Date());
            log1.setUserid(userID);
            log1.setCreators(userID);
            log1.setConclusion("90");
            log1.setFlowcaseno(processInstID);
            log1.setIsdel(0);
            log1.setSuggest(suggest);
            ppmFlowLogService.addPpmFlowLog(log1);

            //返回奋进号数据
            this.httpClientDemand("failed", onlineNum,
                    eosDictEntryService.getTypeidForDetail("NEWDEMAND", "03").getDictname());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    /**
     * 处理结束回调奋进号
     * @param result
     * @param procNum
     * @param demandurl
     */
    private void httpClientDemand(String result, String procNum, String demandurl) {
        log.info("接口入参:result" + result + ":procNum" + procNum + ":demandurl" + demandurl);
        InputStream inStrm = null;
        OutputStream outStrm = null;
        byte[] byteIn = null;
        try{
            Map<String, Object> relaData = new HashMap<>();
            relaData.put("result", result);
            relaData.put("procNum", procNum);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
            String jsonstr = gson.toJson(relaData);
            //获取接口地址      业务字典获取
            log.info("============流程结束调用奋进号开始=============");

            URL url = new URL(demandurl);
            HttpURLConnection httpConnect = (HttpURLConnection) url.openConnection();
            httpConnect.setConnectTimeout(30000);
            httpConnect.setReadTimeout(30000);
            httpConnect.setRequestProperty("Content-type", "application/json");
            httpConnect.setDoOutput(true);
            httpConnect.setDoInput(true);
            httpConnect.setRequestMethod("POST");
            httpConnect.connect();

            outStrm = httpConnect.getOutputStream();
            outStrm.write(jsonstr.getBytes(StandardCharsets.UTF_8));

            inStrm = httpConnect.getInputStream();
            byteIn = new byte[inStrm.available()];
            inStrm.read(byteIn);
            String msg = new String(byteIn, StandardCharsets.UTF_8);
            log.info("============流程结束调用奋进号结束============="+msg);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }finally{
            try{
                if (inStrm != null){
                    inStrm.close();
                }
                if(outStrm!=null){
                    outStrm.flush();
                    outStrm.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 互联网端功能变更涉及场景 转换
     * @param str
     * @return String
     */
    public String strInternetChangesScenes(String[] str) {
        String scenes = "";
        if (str == null || str.length <= 0) {
            return scenes;
        }

        List<String> internetChangesScenes = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            if ("其他（含非互联网端变更功能）".equals(str[i])) {
                internetChangesScenes.add("01");
            } else if ("注册/登录".equals(str[i])) {
                internetChangesScenes.add("02");
            } else if ("密码重置".equals(str[i])) {
                internetChangesScenes.add("03");
            } else if ("验证码（短信、图形）".equals(str[i])) {
                internetChangesScenes.add("04");
            } else if ("文件上传/下载".equals(str[i])) {
                internetChangesScenes.add("05");
            } else if ("敏感信息增删查改操作".equals(str[i])) {
                internetChangesScenes.add("06");
            } else if ("资金交易".equals(str[i])) {
                internetChangesScenes.add("07");
            } else if ("无需安全测试".equals(str[i])) {
                internetChangesScenes.add("08");
            } else {
                log.info("无");
            }
        }
        if (internetChangesScenes.size() > 0) {
            scenes = StringUtils.join(internetChangesScenes.toArray(), ",");
        }

        return scenes;
    }

}
