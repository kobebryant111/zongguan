package com.work.ppm.application.services.kpi.impl;


import com.work.ppm.application.dto.kpi.JudgeKpiStatusDto;
import com.work.ppm.application.dto.kpi.MyKpiDbDto;

import com.work.ppm.application.dto.kpi.PpmKpiItemDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemInsertInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiItemSwitchDto;

import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;

import com.work.ppm.application.dto.kpi.PpmKpiRoleInfoDto;

import com.work.ppm.application.model.kpi.PpmKpiPmDetail;

import com.work.ppm.application.model.kpi.PpmKpiRole;

import com.work.ppm.application.model.kpi.ext.PpmKpiPmInfo;


import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiDepRoleService;
import com.work.ppm.application.services.kpi.IPpmKpiItemService;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchService;
import com.work.ppm.application.services.kpi.IPpmKpiLevelPersonService;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
import com.work.ppm.application.services.kpi.IPpmKpiPostevaluationService;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;

import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiPm;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.commons.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiPmService")
public class PpmKpiPmService extends BaseDaoSupper implements IPpmKpiPmService {
    /**
      * 实体类路径
      */
    private static String PPMKPIPM = "com.work.ppm.application.model.kpi.PpmKpiPm";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 考核人员角色接口
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;

    /**
     * 考核指标表接口
     */
    @Autowired
    private IPpmKpiPmDetailService ppmKpiPmDetailService;
    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;

    /**
     *  开关接口
     */
    @Autowired
    private IPpmKpiItemSwitchService ppmKpiItemSwitchService;
    /**
     * 系统角色查询接口
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;

    /**
     * 指标库接口
     */
    @Autowired
    private IPpmKpiItemService ppmKpiItemService;

    /**
     * 履职评估接口
     */
    @Autowired
    private IPpmKpiPostevaluationService ppmKpiPostevaluationService;

    /**
     * 部门组织树接口
     */
    @Autowired
    private IPpmKpiDepRoleService ppmKpiDepRoleService;

    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    /**
     * 比例人数接口
     */
    @Autowired
    private IPpmKpiLevelPersonService ppmKpiLevelPersonService;

    /**
     * 中心接口
     */
    @Autowired
    private IPpmDaCenterService ppmDaCenterService;


    /**
     * 1接口
     */
    @Autowired
    private IPpmKpiPm1Service ppmKpiPm1Service;

    /**
     * 2接口
     */
    @Autowired
    private IPpmKpiPm2Service ppmKpiPm2Service;

    @Override
    public void queryPager(PpmKpiPmDto ppmKpiPmDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmDto,PpmKpiPmDto.class);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiPm(PpmKpiPmDto ppmKpiPmDto) {
        PpmKpiPm ppmKpiPm = JsonUtil.jsonToClass(ppmKpiPmDto, PpmKpiPm.class);
        Long id = getNextSequence(PpmKpiPm.class);
        // 如果需要请set 主键id
        ppmKpiPm.setId(id);
        myBatisDao.executionPojo(PPMKPIPM, ppmKpiPm, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiPmDto queryDetail(PpmKpiPmDto ppmKpiPmDto) {
        PpmKpiPm ppmKpiPm = JsonUtil.jsonToClass(ppmKpiPmDto, PpmKpiPm.class);
        PpmKpiPm data = myBatisDao.selectClass(PPMKPIPM, ppmKpiPm,
        "queryDetail");
        PpmKpiPmDto item = JsonUtil.jsonToClass(data, PpmKpiPmDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiPm(PpmKpiPmDto ppmKpiPmDto) {
        PpmKpiPm ppmKpiPm = JsonUtil.jsonToClass(ppmKpiPmDto, PpmKpiPm.class);
        return myBatisDao.executionPojo(PPMKPIPM, ppmKpiPm, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiPm(PpmKpiPmDto ppmKpiPmDto) {
        //删除主数据
        PpmKpiPm ppmKpiPm = JsonUtil.jsonToClass(ppmKpiPmDto, PpmKpiPm.class);
        myBatisDao.executionPojo(PPMKPIPM, ppmKpiPm, IMyBatisDao.SQL.DELETE,"deleteById");
        //删除子数据
        PpmKpiPmDetailDto detail=new PpmKpiPmDetailDto();
        detail.setPid(ppmKpiPmDto.getId());
        ppmKpiPmDetailService.deletePpmKpiPmDetailByPid(detail);
        return 1;
    }

    @Override
    public PpmKpiPmDto queryDetailByCondition(PpmKpiPmDto ppmKpiPmDto) {
        PpmKpiPm ppmKpiPm = JsonUtil.jsonToClass(ppmKpiPmDto, PpmKpiPm.class);
        PpmKpiPm data = myBatisDao.selectClass(PPMKPIPM, ppmKpiPm,
                "queryDetailByCondition");
        PpmKpiPmDto item = JsonUtil.jsonToClass(data, PpmKpiPmDto
                .class);
        return item;
    }

    @Override
    public int launchEmployee(PpmKpiPmDto ppmKpiPmDto,String userid){
        //判断指标库是否有数据
        PpmKpiItemDto param=new PpmKpiItemDto();
        List<PpmKpiItemInsertInfoDto> infos= ppmKpiItemService.querykpiItemAll(param);
        if(infos.size()<=0){
            return 0;
        }


        //发起周期上半年或者下半年
        String term=ppmKpiPmDto.getTerm();
        //发起年份
        String tyear=ppmKpiPmDto.getTyear();
        //查询考核组织树相关人员
        PpmKpiRoleInfoDto ppmKpiRoleInfoDto=new PpmKpiRoleInfoDto();
        ppmKpiRoleInfoDto.setRole("COMM_USER");
        ppmKpiRoleInfoDto.setIsdel(0L);
        List<PpmKpiRoleInfoDto> emps= ppmKpiRoleService.getPpmKpiRoleView(ppmKpiRoleInfoDto);
        if(emps.size()>0){
            for (int i=0;i<emps.size();i++) {
                PpmKpiRoleInfoDto emp = emps.get(i);
                ppmKpiPmDto.setEid(emp.getBadge());
                PpmKpiPmDto dto = queryDetailByCondition(ppmKpiPmDto);
                if (!StringUtil.isNotEmpty(dto)) {
                    insertEmpKpiInfo(emp, tyear, term,userid);
                }
            }

        }
        EosDictEntry yearEos =new EosDictEntry();
        yearEos.setDicttypeid("KPI_TIME");
        yearEos.setDictid("tyear");
        yearEos.setDictname(tyear);
        eosDictEntryService.updateEosDictEntry(yearEos);

        EosDictEntry termEos =new EosDictEntry();
        termEos.setDicttypeid("KPI_TIME");
        termEos.setDictid("term");
        termEos.setDictname(term);
        eosDictEntryService.updateEosDictEntry(termEos);
        return 1;
    }

    @Override
    public int launchManagers(PpmKpiPmDto ppmKpiPmDto,String userid){
        //判断指标库是否有数据
        PpmKpiItemDto param=new PpmKpiItemDto();
        List<PpmKpiItemInsertInfoDto> infos= ppmKpiItemService.querykpiItemAll(param);
        if(infos.size()<=0){
            return 0;
        }
        //发起周期上半年或者下半年
        String term="全年度";
        //发起年份
        String tyear=ppmKpiPmDto.getTyear();
        //查询考核组织树相关人员
        PpmKpiRoleInfoDto ppmKpiRoleInfoDto=new PpmKpiRoleInfoDto();
        ppmKpiRoleInfoDto.setRole("DEP_MANAGERS");
        ppmKpiRoleInfoDto.setIsdel(0L);
        List<PpmKpiRoleInfoDto> emps= ppmKpiRoleService.getPpmKpiRoleView(ppmKpiRoleInfoDto);
        if(emps.size()>0){
            for (int i=0;i<emps.size();i++) {
                PpmKpiRoleInfoDto emp = emps.get(i);
                ppmKpiPmDto.setEid(emp.getBadge());
                ppmKpiPmDto.setTerm("全年度");
                PpmKpiPmDto dto = queryDetailByCondition(ppmKpiPmDto);
                if (!StringUtil.isNotEmpty(dto)) {
                    insertManagersKpiInfo(emp, tyear, term,userid);
                }
            }

        }
        EosDictEntry yearEos =new EosDictEntry();
        yearEos.setDicttypeid("KPI_TIME");
        yearEos.setDictid("tyear");
        yearEos.setDictname(tyear);
        eosDictEntryService.updateEosDictEntry(yearEos);
        return 1;
    }
    @Override
    public int launchWorkManager(PpmKpiPmDto ppmKpiPmDto,String userid){

        //判断指标库是否有数据
        PpmKpiItemDto param=new PpmKpiItemDto();
        List<PpmKpiItemInsertInfoDto> infos= ppmKpiItemService.querykpiItemAll(param);
        if(infos.size()<=0){
            return 0;
        }
        //发起周期上半年或者下半年
        String term="全年度";
        //发起年份
        String tyear=ppmKpiPmDto.getTyear();
        //查询考核组织树相关人员
        PpmKpiRoleInfoDto ppmKpiRoleInfoDto=new PpmKpiRoleInfoDto();
        ppmKpiRoleInfoDto.setRole("DEP_MANAGER");
        ppmKpiRoleInfoDto.setIsdel(0L);
        List<PpmKpiRoleInfoDto> emps= ppmKpiRoleService.getPpmKpiRoleView(ppmKpiRoleInfoDto);
        if(emps.size()>0){
            for (int i=0;i<emps.size();i++) {
                PpmKpiRoleInfoDto emp = emps.get(i);
                ppmKpiPmDto.setEid(emp.getBadge());
                ppmKpiPmDto.setTerm("全年度");
                PpmKpiPmDto dto = queryDetailByCondition(ppmKpiPmDto);
                if (!StringUtil.isNotEmpty(dto)) {
                     insertWorkManagerKpiInfo(emp, tyear, term,userid);
                }
            }

        }
        EosDictEntry yearEos =new EosDictEntry();
        yearEos.setDicttypeid("KPI_TIME");
        yearEos.setDictid("tyear");
        yearEos.setDictname(tyear);
        eosDictEntryService.updateEosDictEntry(yearEos);
        return 1;
    }
    /**
     * 插入主表数据
     * @param ppmKpiRoleInfoDto 对象
     * @param tyear 对象
     * @param term 对象
     * @param userid 对象
     * @return long 对象
     */
    private long insertPpmKpiPm(PpmKpiRoleInfoDto ppmKpiRoleInfoDto,String tyear,String term,String userid){
        PpmKpiPm pm=new PpmKpiPm();
        pm.setTyear(tyear);
        pm.setTerm(term);
        pm.setEid(ppmKpiRoleInfoDto.getBadge());
        pm.setDepid(ppmKpiRoleInfoDto.getDepid());
        pm.setJobid(ppmKpiRoleInfoDto.getJobid());
        pm.setCenterid(ppmKpiRoleInfoDto.getCenterid());
        pm.setRole(ppmKpiRoleInfoDto.getRole());
        if("DEP_MANAGER".equals(ppmKpiRoleInfoDto.getRole())){
            pm.setStatus(3L);
        }else {
            pm.setStatus(0L);
        }
        pm.setCreatetime(new Date());
        pm.setCreateuser(userid);
        pm.setIsedit(0L);
        pm.setHeadship(ppmKpiRoleInfoDto.getHeadship());
        Long id = getNextSequence(PpmKpiPm.class);
        // 如果需要请set 主键id
        pm.setId(id);
        myBatisDao.executionPojo(PPMKPIPM, pm, IMyBatisDao.SQL.INSTER, "addPojo");
        return id;
    }

    /**
     * 插入员工考核数据
     * @param ppmKpiRoleInfoDto 对象
     * @param tyear 对象
     * @param term 对象
     * @param userid 对象
     * @return int 对象
     */
    @Transactional("defaultTx")
    private int insertEmpKpiInfo(PpmKpiRoleInfoDto ppmKpiRoleInfoDto,String tyear,String term,String userid){
        //插入主表信息
        long id=insertPpmKpiPm( ppmKpiRoleInfoDto, tyear, term, userid);
        //插入详细表
        PpmKpiPmDetailDto detail=new PpmKpiPmDetailDto();
        detail.setTyear(tyear);
        detail.setTerm(term);
        detail.setPid(id);
        detail.setEid(ppmKpiRoleInfoDto.getBadge());
        detail.setRegby(userid);
        detail.setRegtime(new Date());
        detail.setIsedit(0L);
        detail.setItemstatus(0L);
        ppmKpiPmDetailService.insertPpmKpiPmDetailEmp(detail);
        //插入评语
        PpmKpiPmDetailDto detailEvalUp=new PpmKpiPmDetailDto();
        detailEvalUp.setTyear(tyear);
        detailEvalUp.setTerm(term);
        detailEvalUp.setPid(id);
        detailEvalUp.setEid(ppmKpiRoleInfoDto.getBadge());
        detailEvalUp.setRegby(userid);
        detailEvalUp.setRegtime(new Date());
        detailEvalUp.setIsedit(0L);
        detailEvalUp.setItemstatus(2L);
        detailEvalUp.setKpitype(6L);
        if("上半年".equals(term)){
            detailEvalUp.setFinishdate(returnFinishDate(tyear,term,"9"));
        }else{
            detailEvalUp.setFinishdate(returnFinishDate(tyear,term,"10"));
        }
        ppmKpiPmDetailService.addPpmKpiPmDetail(detailEvalUp);
        return 1;
    }

    /**
     * 返回配置日期
     * @param tyear 对象
     * @param term 对象
     * @param itemtype 对象
     * @return Date 对象
     */
    private Date returnFinishDate(String tyear,String term,String itemtype){
        Date date=new Date();
        PpmKpiItemSwitchDto ppmKpiItemSwitchDto=new PpmKpiItemSwitchDto();
        ppmKpiItemSwitchDto.setTyear(tyear);
        ppmKpiItemSwitchDto.setTerm(term);
        ppmKpiItemSwitchDto.setIsswitch(1L);
        ppmKpiItemSwitchDto.setItype("1");
        ppmKpiItemSwitchDto.setItemtype(itemtype);
        PpmKpiItemSwitchDto dto=ppmKpiItemSwitchService.queryDetailByCondition(ppmKpiItemSwitchDto);
        if(StringUtil.isNotEmpty(dto)){
            date= DateUtil.strToDate(dto.getFinishtime(),"yyyy-MM-dd");
        }
        return date;
    }
    /**
     * 插入经理层考核数据
     * @param ppmKpiRoleInfoDto 对象
     * @param tyear 对象
     * @param term 对象
     * @param userid 对象
     * @return int 对象
     */
    @Transactional("defaultTx")
    private int insertManagersKpiInfo(PpmKpiRoleInfoDto ppmKpiRoleInfoDto,String tyear,String term,String userid){
        //插入主表信息
        long id=insertPpmKpiPm( ppmKpiRoleInfoDto, tyear, term, userid);
        //插入详细表
        PpmKpiPmDetailDto detail=new PpmKpiPmDetailDto();
        detail.setTyear(tyear);
        detail.setTerm(term);
        detail.setPid(id);
        detail.setEid(ppmKpiRoleInfoDto.getBadge());
        detail.setRegby(userid);
        detail.setRegtime(new Date());
        detail.setIsedit(0L);
        detail.setItemstatus(0L);
        ppmKpiPmDetailService.insertPpmKpiPmDetailManagers(detail);
        //插入评语
        //插入上半年评语
        PpmKpiPmDetailDto detailEvalUp=new PpmKpiPmDetailDto();
        detailEvalUp.setTyear(tyear);
        detailEvalUp.setTerm("上半年");
        detailEvalUp.setPid(id);
        detailEvalUp.setEid(ppmKpiRoleInfoDto.getBadge());
        detailEvalUp.setRegby(userid);
        detailEvalUp.setRegtime(new Date());
        detailEvalUp.setIsedit(0L);
        detailEvalUp.setItemstatus(2L);
        detailEvalUp.setKpitype(6L);

        detailEvalUp.setFinishdate(returnFinishDate(tyear,"上半年","9"));

        ppmKpiPmDetailService.addPpmKpiPmDetail(detailEvalUp);
        //插入下半年评语
        PpmKpiPmDetailDto detailEvalDown=new PpmKpiPmDetailDto();
        detailEvalDown.setTyear(tyear);
        detailEvalDown.setTerm("下半年");
        detailEvalDown.setPid(id);
        detailEvalDown.setEid(ppmKpiRoleInfoDto.getBadge());
        detailEvalDown.setRegby(userid);
        detailEvalDown.setRegtime(new Date());
        detailEvalDown.setIsedit(0L);
        detailEvalDown.setItemstatus(2L);
        detailEvalDown.setKpitype(6L);

        detailEvalDown.setFinishdate(returnFinishDate(tyear,"下半年","10"));
        ppmKpiPmDetailService.addPpmKpiPmDetail(detailEvalDown);
        return 1;
    }


    /**
     * 插入主持工作经理考核数据
     * @param ppmKpiRoleInfoDto 对象
     * @param tyear 对象
     * @param term 对象
     * @param userid 对象
     * @return int 对象
     */
    @Transactional("defaultTx")
    private int insertWorkManagerKpiInfo(PpmKpiRoleInfoDto ppmKpiRoleInfoDto,String tyear,String term,String userid){
        //插入主表信息
        long id=insertPpmKpiPm( ppmKpiRoleInfoDto, tyear, term, userid);
        //插入详细表
        PpmKpiPmDetailDto detail=new PpmKpiPmDetailDto();
        detail.setTyear(tyear);
        detail.setTerm(term);
        detail.setPid(id);
        detail.setEid(ppmKpiRoleInfoDto.getBadge());
        detail.setRegby(userid);
        detail.setRegtime(new Date());
        detail.setIsedit(0L);
        detail.setItemstatus(3L);
        ppmKpiPmDetailService.insertPpmKpiPmDetailManagers(detail);
        //插入评语
        //插入上半年评语
        PpmKpiPmDetailDto detailEvalUp=new PpmKpiPmDetailDto();
        detailEvalUp.setTyear(tyear);
        detailEvalUp.setTerm("上半年");
        detailEvalUp.setPid(id);
        detailEvalUp.setEid(ppmKpiRoleInfoDto.getBadge());
        detailEvalUp.setRegby(userid);
        detailEvalUp.setRegtime(new Date());
        detailEvalUp.setIsedit(0L);
        detailEvalUp.setItemstatus(2L);
        detailEvalUp.setKpitype(6L);
        detailEvalUp.setFinishdate(returnFinishDate(tyear,"上半年","9"));
        ppmKpiPmDetailService.addPpmKpiPmDetail(detailEvalUp);
        //插入下半年评语
        PpmKpiPmDetailDto detailEvalDown=new PpmKpiPmDetailDto();
        detailEvalDown.setTyear(tyear);
        detailEvalDown.setTerm("下半年");
        detailEvalDown.setPid(id);
        detailEvalDown.setEid(ppmKpiRoleInfoDto.getBadge());
        detailEvalDown.setRegby(userid);
        detailEvalDown.setRegtime(new Date());
        detailEvalDown.setIsedit(0L);
        detailEvalDown.setItemstatus(2L);
        detailEvalDown.setKpitype(6L);
        detailEvalDown.setFinishdate(returnFinishDate(tyear,"下半年","10"));
        ppmKpiPmDetailService.addPpmKpiPmDetail(detailEvalDown);
        return 1;
    }
    @Override
    public MyKpiDbDto getMyDb(MyKpiDbDto myDbDto, String userid){
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();
        EosDictEntry entryTerm=eosDictEntryService.getTypeidForDetail("KPI_TIME","term");
        String term=entryTerm.getDictname();

        EosDictEntry eos = eosDictEntryService.getTypeidForDetail("KHNDSPR", "SPR");
        String finalCheck = eos.getDictname();


        if(tyear==null||"".equals(tyear)){
            return myDbDto;
        }
        //基本信息设置
        myDbDto.setTyear(tyear);
        myDbDto.setTerm(term);
        myDbDto.setEid(userid);
        String role=ppmKpiRoleService.retRole(userid);
        myDbDto.setRole(role);
        if("COMM_USER".equals(role)){
            //员工考核指标制定
            //上半年考核制定
            PpmKpiPmDto upppmKpiPmDto=new PpmKpiPmDto();
            upppmKpiPmDto.setEid(userid);
            upppmKpiPmDto.setTyear(tyear);
            upppmKpiPmDto.setTerm("上半年");
            PpmKpiPmDto updto= queryDetailByCondition(upppmKpiPmDto);
            if(updto!=null&&updto.getStatus()==0){
                Map upparam=new HashMap();
                upparam.put("tyear",tyear);
                upparam.put("term","上半年");
                upparam.put("kpitype","1");
                upparam.put("eid",userid);
                upparam.put("status","'0'");
                long upcount=ppmKpiPmDetailService.queryKpiPmDetails(upparam);
                myDbDto.setEmp_formulate_kpis_first_half(upcount);
            }else{
                myDbDto.setEmp_formulate_kpis_first_half(-1);
            }
            PpmKpiPmDto downppmKpiPmDto=new PpmKpiPmDto();
            downppmKpiPmDto.setEid(userid);
            downppmKpiPmDto.setTyear(tyear);
            downppmKpiPmDto.setTerm("下半年");
            PpmKpiPmDto downdto= queryDetailByCondition(downppmKpiPmDto);
            //下半年考核制定
            if(downdto!=null&&downdto.getStatus()==0) {
                Map downparam = new HashMap();
                downparam.put("tyear", tyear);
                downparam.put("term", "下半年");
                downparam.put("kpitype", "1");
                downparam.put("eid", userid);
                downparam.put("status", "'0'");
                long downcount = ppmKpiPmDetailService.queryKpiPmDetails(downparam);
                myDbDto.setEmp_formulate_kpis_last_half(downcount);
            }else{
                myDbDto.setEmp_formulate_kpis_last_half(-1);
            }



            //上半年自评
            Map upparamSelfeval=new HashMap();
            upparamSelfeval.put("tyear",tyear);
            upparamSelfeval.put("term","上半年");
            upparamSelfeval.put("kpitypes","'1','5'");
            upparamSelfeval.put("eid",userid);
            upparamSelfeval.put("status","'4'");
            upparamSelfeval.put("itemstatus","4");
            long upcountSelfeval=ppmKpiPmDetailService.queryKpiPmDetails(upparamSelfeval);
            myDbDto.setEmp_self_eval_kpis_first_half(upcountSelfeval);
            //下半年自评
            Map downparamSelfeval=new HashMap();
            downparamSelfeval.put("tyear",tyear);
            downparamSelfeval.put("term","下半年");
            downparamSelfeval.put("kpitypes","'1','5'");
            downparamSelfeval.put("eid",userid);
            downparamSelfeval.put("status","'4'");
            downparamSelfeval.put("itemstatus","4");
            long downcountSelfeval=ppmKpiPmDetailService.queryKpiPmDetails(downparamSelfeval);
            myDbDto.setEmp_self_eval_kpis_last_half(downcountSelfeval);


            //下级对上级的评价
            Map subordinateEvaluationParam=new HashMap();
            subordinateEvaluationParam.put("tyear",tyear);
            subordinateEvaluationParam.put("check",userid);
            subordinateEvaluationParam.put("itemstatus","6");
            List<PpmKpiPmInfoDto> subordinateEvaluations=ppmKpiPm2Service.
                    getPostEvalInfoSummaryList(subordinateEvaluationParam);
            long countSubordinateEvaluations=subordinateEvaluations==null?0:
                    subordinateEvaluations.size();
            myDbDto.setSubordinate_evaluation(countSubordinateEvaluations);

        }else{
            //经理指标制定
            PpmKpiPmDto ppmKpiPmDto=new PpmKpiPmDto();
            ppmKpiPmDto.setEid(userid);
            ppmKpiPmDto.setTyear(tyear);
            ppmKpiPmDto.setTerm("全年度");
            PpmKpiPmDto dto= queryDetailByCondition(ppmKpiPmDto);
            if(dto!=null&&dto.getStatus()==0){
                Map param=new HashMap();
                param.put("tyear",tyear);
                param.put("term","全年度");
                param.put("kpitype","1");
                param.put("eid",userid);
                param.put("status","'0'");
                long count=ppmKpiPmDetailService.queryKpiPmDetails(param);
                myDbDto.setManagers_formulate_kpis(count);
            }else{
                myDbDto.setManagers_formulate_kpis(-1);
            }
            //经理待自评
            Map paramSelfeval=new HashMap();
            paramSelfeval.put("tyear",tyear);
            paramSelfeval.put("term","全年度");
            paramSelfeval.put("kpitypes","'1','5'");
            paramSelfeval.put("eid",userid);
            paramSelfeval.put("status","'4'");
            paramSelfeval.put("itemstatus","4");
            long countSelfeval=ppmKpiPmDetailService.queryKpiPmDetails(paramSelfeval);
            myDbDto.setManagers_self_eval_kpis(countSelfeval);

            //经理年中自评
            Map paramSelfevalHalfYear=new HashMap();
            paramSelfevalHalfYear.put("tyear",tyear);
            paramSelfevalHalfYear.put("kpitype","1");
            paramSelfevalHalfYear.put("eid",userid);
            paramSelfevalHalfYear.put("status","'3','4'");
            paramSelfevalHalfYear.put("itemstatus","3");
            paramSelfevalHalfYear.put("term","全年度");
            long countSelfevalHalfYear=ppmKpiPmDetailService.
                    queryKpiPmDetails(paramSelfevalHalfYear);
            myDbDto.setManagers_self_eval_kpis_half_year(countSelfevalHalfYear);

            //上半年审批数据
            Map upparamApprove=new HashMap();
            upparamApprove.put("tyear",tyear);
            upparamApprove.put("term","上半年");
            upparamApprove.put("check",userid);
            upparamApprove.put("role","COMM_USER");

            long countUpApprove=getKpiPmApproveCount(upparamApprove);
            myDbDto.setEmp_kpis_approve_first_half(countUpApprove);
            //下半年审批数据
            Map downparamApprove=new HashMap();
            downparamApprove.put("tyear",tyear);
            downparamApprove.put("term","下半年");
            downparamApprove.put("check",userid);
            //downparamApprove.put("role","COMM_USER");
            long countDownApprove=getKpiPmApproveCount(downparamApprove);
            myDbDto.setEmp_kpis_approve_last_half(countDownApprove);

            //经理kpi审批数据
            Map downparamManagerApprove=new HashMap();
            downparamManagerApprove.put("tyear",tyear);
            downparamManagerApprove.put("check",userid);
            downparamManagerApprove.put("role","DEP_MANAGERS");
            downparamManagerApprove.put("term","全年度");
            long countDownManagerApprove=getKpiPmApproveCount(downparamManagerApprove);
            myDbDto.setManagers_kpis_approve(countDownManagerApprove);

            //员工上半年评价
            Map upparamEval=new HashMap();
            upparamEval.put("tyear",tyear);
            upparamEval.put("term","上半年");
            upparamEval.put("check",userid);
            upparamEval.put("role","COMM_USER");

            upparamEval.put("kpitypes","'1','2','6'");
            List<PpmKpiPmInfoDto> upEvals=ppmKpiPm1Service.getPpmKpiPmEvalInfo(upparamEval);
            long countUpEval=upEvals==null?0:upEvals.size();
            myDbDto.setEmp_eval_first_half(countUpEval);

            //员工下半年评价
            Map downparamEval=new HashMap();
            downparamEval.put("tyear",tyear);
            downparamEval.put("term","下半年");
            downparamEval.put("check",userid);
            downparamEval.put("role","COMM_USER");

            downparamEval.put("kpitypes","'1','2','6'");
            List<PpmKpiPmInfoDto> downEvals=ppmKpiPm1Service.getPpmKpiPmEvalInfo(downparamEval);
            long countDownEval=downEvals==null?0:downEvals.size();
            myDbDto.setEmp_eval_last_half(countDownEval);

            //经理全年评价
            Map paramManagersEval=new HashMap();
            paramManagersEval.put("tyear",tyear);
            paramManagersEval.put("check",userid);
            paramManagersEval.put("roles","'DEP_MANAGERS','DEP_MANAGER'");
            //paramManagersEval.put("kpitypes","'1','3','6'");
            paramManagersEval.put("term","全年度");
            List<PpmKpiPmInfoDto> managersEvals=ppmKpiPm1Service.getPpmKpiPmEvalInfo(paramManagersEval);
            long countManagersEval=managersEvals==null?0:managersEvals.size();
            myDbDto.setManagers_eval(countManagersEval);


            //下级对上级的评价_经理层
            Map subordinateEvaluationParam=new HashMap();
            subordinateEvaluationParam.put("tyear",tyear);
            subordinateEvaluationParam.put("check",userid);
            subordinateEvaluationParam.put("itemstatus","6");
            List<PpmKpiPmInfoDto> subordinateEvaluations=ppmKpiPm2Service.
                    getPostEvalInfoSummaryList(subordinateEvaluationParam);
            long countSubordinateEvaluations=subordinateEvaluations==null
                    ?0:subordinateEvaluations.size();
            myDbDto.setSubordinate_evaluation(countSubordinateEvaluations);

            //经理全年评价
//            Map param_manager_eval=new HashMap();
//            param_manager_eval.put("tyear",tyear);
//            param_manager_eval.put("check",userid);
//            param_manager_eval.put("role","DEP_MANAGER");
//            param_manager_eval.put("kpitypes","'3','6'");
//            List<PpmKpiPmInfoDto> managerEvals=getPpmKpiPmEvalInfo(param_manager_eval);
//            long count_manager_eval=managerEvals==null?0:managerEvals.size();
//            myDbDto.setManager_eval(count_manager_eval);
            //考核年度数据确认
            PpmKpiPmInfoDto ppmKpiPmInfoDto=new PpmKpiPmInfoDto();
            ppmKpiPmInfoDto.setTyear(tyear);
            ppmKpiPmInfoDto.setTerm("年度");
            ppmKpiPmInfoDto.setRole("COMM_USER");
            List<PpmKpiPmInfoDto> depYearDataConfirms=ppmKpiPm2Service.
                    getDepYearDataList( ppmKpiPmInfoDto,  userid);
            long countDepYearDataConfirms=depYearDataConfirms==null?0:depYearDataConfirms.size();
            myDbDto.setDepYearData_confirm(countDepYearDataConfirms);


            //考核年度数据初评
            PpmKpiPmInfoDto centerYearParam=new PpmKpiPmInfoDto();
            ppmKpiPmInfoDto.setTyear(tyear);
            ppmKpiPmInfoDto.setTerm("年度");
            List<PpmKpiPmInfoDto> centerYearDataEvals=ppmKpiPm2Service.
                    getCenterYearDataList( centerYearParam,  userid);
            long countCenterYearDataEvals=centerYearDataEvals==null?0:centerYearDataEvals.size();
            myDbDto.setCenterYearData_first_eval(countCenterYearDataEvals);

            //年度终评
            if(finalCheck.equals(userid)){
                PpmKpiPmInfoDto yearParam=new PpmKpiPmInfoDto();
                yearParam.setTyear(tyear);
                yearParam.setTerm("年度");
                List<PpmKpiPmInfoDto> yearDataEvals=ppmKpiPm2Service.
                        getTecYearDataList( yearParam,  userid);
                long countYearDataEvals=yearDataEvals==null?0:yearDataEvals.size();
                myDbDto.setTecYearData_second_eval(countYearDataEvals);
            }


        }
        return myDbDto;
    }


    /**
     * 根据条件获得待审批的考核数据
     * @param param Map 对象
     * @return long 对象
     */
    private long getKpiPmApproveCount(Map param){
        long count=0L;
        List<PpmKpiPm> data = myBatisDao.selectListT(
                PPMKPIPM, param, "queryKpiPmByRoleAndCondition");
        List<PpmKpiPmDto> item = JsonUtil.jsonToList(data, PpmKpiPmDto.class);
        if(item.size()>0){
            count=item.size();
        }
        return count;
    }
    @Transactional("defaultTx")
    @Override
    public int submitKpiDetail(PpmKpiPmDto ppmKpiPmDto){
        //判断状态是否是草稿
        PpmKpiPmDto pm=queryDetail(ppmKpiPmDto);
        if(pm.getStatus()!=0){
            return 3;
        }
        Map paramSumValue=new HashMap();
        paramSumValue.put("pid",pm.getId());
        paramSumValue.put("kpitype","1");
        PpmKpiPmDetail detail= ppmKpiPmDetailService.querySumValue(paramSumValue);
        if(detail.getValue()!=100){
            return 4;
        }
        //判断kpi条数和知识能力提升
        //kpi最大条数
        EosDictEntry kpiMax=eosDictEntryService.
                getTypeidForDetail("KPI_NUMBERS","kpi_max_numbers");
        long kpiMaxnumbers=Long.parseLong(kpiMax.getDictname());
        //kpi最小条数
        EosDictEntry kpiMin=eosDictEntryService.
                getTypeidForDetail("KPI_NUMBERS","kpi_min_numbers");
        long kpiMinnumbers=Long.parseLong(kpiMin.getDictname());
        //知识能力提升最小条数
        EosDictEntry abilityMin=eosDictEntryService.
                getTypeidForDetail("KPI_NUMBERS","ability_min_numbers");
        long abilityMinnumbers=Long.parseLong(abilityMin.getDictname());

        //kpi条数
        Map paramKpi=new HashMap();
        paramKpi.put("pid",pm.getId());
        paramKpi.put("kpitype","1");
        long kpis=ppmKpiPmDetailService.queryKpiPmDetails(paramKpi);
        if(kpis<kpiMinnumbers||kpis>kpiMaxnumbers){
            return 5;
        }

        //知识能力提升条数
        Map paramAbilitys=new HashMap();
        paramAbilitys.put("pid",pm.getId());
        paramAbilitys.put("kpitype","5");
        long abilitys=ppmKpiPmDetailService.queryKpiPmDetails(paramAbilitys);
        if(abilitys<abilityMinnumbers){
            return 6;
        }

        //查询该人员下面指标项
        PpmKpiPmDetailInfoDto info=new PpmKpiPmDetailInfoDto();
        info.setPid(ppmKpiPmDto.getId());
        info.setKpitype(1L);
        List<PpmKpiPmDetailInfoDto> details= ppmKpiPmDetailService.getPpmKpiPmDetails(info);
        if(details.size()<0){
            return 1;
        }
        PpmKpiRole role=ppmKpiRoleService.getPpmKpiRoleByBadge(pm.getEid());
        if(StringUtil.isNotBlank(role.getFirstcheck())){
            //提交未初评
            ppmKpiPmDto.setStatus(1L);
        }else{
            //提交待终评
            ppmKpiPmDto.setStatus(2L);
        }
        //修改子状态为审批中
        Map param=new HashMap();
        param.put("pid",ppmKpiPmDto.getId());
        param.put("kpitypes","'1','5'");
        param.put("itemstatus","1");
        param.put("itemstatuss","0");
        int j=ppmKpiPmDetailService.updateItemStatus(param);
        if(j<=0){
            return 0;
        }
        //修改主记录
        //把回退意见修改为空
        ppmKpiPmDto.setRollbacksuggest("");
        int i=updatePpmKpiPm(ppmKpiPmDto);
        if(i>0){
            return 2;
        }
        return 0;
    }

    @Override
    public PpmKpiPmInfoDto queryKpiPmByCondition(PpmKpiPmInfoDto ppmKpiPmInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        PpmKpiPmInfo data = myBatisDao.selectClass(PPMKPIPM, map,
                "queryKpiPmByCondition");
        PpmKpiPmInfoDto item = JsonUtil.jsonToClass(data, PpmKpiPmInfoDto
                .class);
        return item;
    }


    @Override
    public void queryApprovePager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"queryKpiPmByCondition");
    }
    @Transactional("defaultTx")
    @Override
    public int totalSubmit (PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid){
        //获取需要评价的人
        Map<String,Object> param = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        param.put("check",userid);
        List<PpmKpiPmInfoDto> persons=ppmKpiPm1Service.getPpmKpiPmEvalInfo(param);
        if(StringUtil.isNotEmpty(persons)){
            for(int i=0;i<persons.size();i++){
                PpmKpiPmInfoDto dto=persons.get(i);
                int retCode=dealOnePersonSubmitEval(dto,userid);
                if(retCode!=1){
                    if(retCode==0){
                        throw new BizException(ApiCode.KPI_RELATION_FAIL);
                    }else if(retCode==2){
                        throw new BizException(ApiCode.KPI_NO_SUBMIT);
                    }else if(retCode==3){
                        throw new BizException(ApiCode.KPI_SCORE_NULL);
                    }else if(retCode==4){
                        throw new BizException(ApiCode.KPI_STATUS_ERROR);
                    }
                }
            }
        }
        return 1;

    }
    /**
     * 处理一个人的全部评价提交
     * @param dto 对象
     * @return int 对象
     */
    private int  dealOnePersonSubmitEval(PpmKpiPmInfoDto dto,String userid){
        int retCode=1;

        JudgeKpiStatusDto jd=new JudgeKpiStatusDto();
        jd.setEid(dto.getEid());
        jd.setCheck(userid);
        jd.setFlag("pj");
        int itemstatus=ppmKpiRoleService.judgeStatus(jd);
        if(itemstatus==0){
            return 0;//评价关系不对
        }
        Map param = new HashMap();
        param.put("pid", dto.getId());
        param.put("kpitypes", "'1','2','3','6'");
        param.put("mainparts", "1");
        param.put("itemstatus", itemstatus);
        List<PpmKpiPmDetail> details = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        if(details.size()>0){
            //校验提交的数据的合法性
            retCode =judgeNull(details);
        }else{
            retCode= 2;//没有可提交的数据
        }
        if(retCode==1){//如果判断成功提交数据
            ppmKpiPm1Service.changeStatus(details,userid);
            //计算分数
            ppmKpiPm2Service.summaryScore(dto.getId());
            //判断主记录状态
            ppmKpiPm1Service.updateMainStatusByItemstatus(dto.getId());
        }
        return retCode;//提交成功
    }

    /**
     * 判断评分是否为空
     * @param details 对象
     * @return int 对象
     */
    private int judgeNull(List<PpmKpiPmDetail> details ){
        for (int i =0;i<details.size();i++){
            PpmKpiPmDetail detail=details.get(i);
            if(detail.getKpitype()==1){
                if(detail.getItemstatus()==5){
                    if(detail.getFirstevalscore()==null){
                        return 3;//评分为空
                    }
                }else if(detail.getItemstatus()==6){
                    if(detail.getSecondevalscore()==null){
                        return 3;//评分为空
                    }
                }else{
                    return 4;//状态不对
                }
            }else if(detail.getKpitype()==2){
                if(detail.getItemstatus()==5){
                    if(detail.getFirstevalscore()==null){
                        return 3;//评分为空
                    }
                }else if(detail.getItemstatus()==6){
                    if(detail.getSecondevalscore()==null){
                        return 3;//评分为空
                    }
                }else{
                    return 4;//状态不对
                }
            }else if(detail.getKpitype()==3){
                if(detail.getItemstatus()==5){
                    if(detail.getFirstevalscore()==null){
                        return 3;//评分为空
                    }
                }else if(detail.getItemstatus()==6){
                    if(detail.getSecondevalscore()==null){
                        return 3;//评分为空
                    }
                }else{
                    return 4;//状态不对
                }
            }else if(detail.getKpitype()==6){
                if(detail.getItemstatus()==5){
                    if(detail.getFirstevalcomment()==null){
                        return 3;//评分为空
                    }
                }else if(detail.getItemstatus()==6){
                    if(detail.getSecondevalcomment()==null){
                        return 3;//评分为空
                    }
                }else{
                    return 4;//状态不对
                }
            }


        }
        return 1;
    }

}
