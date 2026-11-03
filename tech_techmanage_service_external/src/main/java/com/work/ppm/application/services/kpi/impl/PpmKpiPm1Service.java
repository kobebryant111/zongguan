package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.CheckEvalItemsSummary;
import com.work.ppm.application.dto.kpi.JudgeKpiStatusDto;

import com.work.ppm.application.dto.kpi.MyKpiDataDto;

import com.work.ppm.application.dto.kpi.PpmKpiPmBalanceSummaryDto;

import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoTotalDto;
import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleInfoDto;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.kpi.PpmKpiPm;
import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import com.work.ppm.application.model.kpi.PpmKpiRole;
import com.work.ppm.application.model.kpi.ext.PpmKpiPmBalanceSummary;
import com.work.ppm.application.model.kpi.ext.PpmKpiPmInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.quartz.interfaces.IPpmKpiPmJobService;
import com.work.ppm.application.services.kpi.IPpmKpiDepRoleService;
import com.work.ppm.application.services.kpi.IPpmKpiItemService;
import com.work.ppm.application.services.kpi.IPpmKpiItemSwitchService;
import com.work.ppm.application.services.kpi.IPpmKpiLevelPersonService;
import com.work.ppm.application.services.kpi.IPpmKpiPm1Service;
import com.work.ppm.application.services.kpi.IPpmKpiPm2Service;
import com.work.ppm.application.services.kpi.IPpmKpiPmDetailService;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.application.services.kpi.IPpmKpiPostevaluationService;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
  * 描述
  */
@Slf4j
@Service("ppmKpiPm1Service")
public class PpmKpiPm1Service extends BaseDaoSupper implements IPpmKpiPm1Service, IPpmKpiPmJobService {
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
    private IPpmKpiPmService ppmKpiPmService;


    /**
     * 2接口
     */
    @Autowired
    private IPpmKpiPm2Service ppmKpiPm2Service;

    @Transactional("defaultTx")
    @Override
    public int approveKpiDetail(PpmKpiPmDto ppmKpiPmDto,String userid){
        int retCode=0;
        //判断状态是否是草稿
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        //判断分数是否是100分
        Map paramSumValue=new HashMap();
        paramSumValue.put("pid",pm.getId());
        paramSumValue.put("kpitype","1");
        PpmKpiPmDetail detail= ppmKpiPmDetailService.querySumValue(paramSumValue);
        if(detail.getValue()!=100){
            return 4;
        }
        //判断kpi条数和知识能力提升
        //kpi最大条数
        EosDictEntry kpiMax=eosDictEntryService.getTypeidForDetail("KPI_NUMBERS","kpi_max_numbers");
        long kpiMaxnumbers=Long.parseLong(kpiMax.getDictname());
        //kpi最小条数
        EosDictEntry kpiMin=eosDictEntryService.getTypeidForDetail("KPI_NUMBERS","kpi_min_numbers");
        long kpiMinnumbers=Long.parseLong(kpiMin.getDictname());
        //知识能力提升最小条数
        EosDictEntry abilityMin=eosDictEntryService.getTypeidForDetail("KPI_NUMBERS","ability_min_numbers");
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

        if(pm.getStatus()==1){
          pm.setStatus(2L);
          pm.setFirstcheck(userid);
          pm.setFirstchecktime(new Date());
          int i=ppmKpiPmService.updatePpmKpiPm(pm);
          if(i>0){
              retCode=1;
          }

        }else if(pm.getStatus()==2){
            //查询子状态如果有自评初评终评的数据的把著状态设为评价中没有设为生效中
            Map paramDetail=new HashMap();
            paramDetail.put("pid",ppmKpiPmDto.getId());
            paramDetail.put("kpitypes","'1','2','3'");
            paramDetail.put("itemstatuss", "'3','4','5','6'");
            List<PpmKpiPmDetail> list = ppmKpiPmDetailService.queryPpmKpiPmDetails(paramDetail);
            if(list.size()>0){
                pm.setStatus(4L);
            }else{
                pm.setStatus(3L);
            }
            pm.setSecondcheck(userid);
            pm.setSecondchecktime(new Date());
            //修改子状态为生效中
            Map param=new HashMap();
            param.put("pid",ppmKpiPmDto.getId());
            param.put("kpitypes","'1','5'");
            param.put("itemstatus","2");
            param.put("itemstatuss","1");
            int j=ppmKpiPmDetailService.updateItemStatus(param);
            if(j<=0){
                return 0;
            }
            int i=ppmKpiPmService.updatePpmKpiPm(pm);
            if(i>0){
                retCode=1;
            }
        }else{
            retCode=2;
        }
        return retCode;
    }
    @Override
    public int rollbackKpiDetail(PpmKpiPmDto ppmKpiPmDto,String userid){
        int retCode=0;
        //判断状态是否是草稿
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        if(pm.getStatus()==1){
            pm.setStatus(0L);
            pm.setFirstcheck(userid);
            pm.setFirstchecktime(new Date());
            pm.setRollbacksuggest(ppmKpiPmDto.getRollbacksuggest());
            //修改子状态为草稿中
            Map param=new HashMap();
            param.put("pid",ppmKpiPmDto.getId());
            param.put("kpitypes","'1','5'");
            param.put("itemstatus","0");
            param.put("itemstatuss","1");
            int j=ppmKpiPmDetailService.updateItemStatus(param);
            if(j<=0){
                return 0;
            }
            int i=ppmKpiPmService.updatePpmKpiPm(pm);
            if(i>0){
                retCode=1;
            }

        }else if(pm.getStatus()==2){
            pm.setStatus(0L);
            pm.setSecondcheck(userid);
            pm.setSecondchecktime(new Date());
            pm.setRollbacksuggest(ppmKpiPmDto.getRollbacksuggest());
            //修改子状态为草稿中
            Map param=new HashMap();
            param.put("pid",ppmKpiPmDto.getId());
            param.put("kpitypes","'1','5'");
            param.put("itemstatus","0");
            param.put("itemstatuss","1");
            int j=ppmKpiPmDetailService.updateItemStatus(param);
            if(j<=0){
                return 0;
            }
            int i=ppmKpiPmService.updatePpmKpiPm(pm);
            if(i>0){
                retCode=1;
            }
        }else{
            retCode=2;
        }
        return retCode;
    }
    @Override
    public void launchPostEvaluation(PpmKpiPmDetail detail,PpmKpiPmDto ppmKpiPmDto){
        //KPI_MAIN 1:上级 2:下级 3:科技部
        if("1".equals(detail.getMainpart())){
            addSupersiorPostEvaluation(detail,ppmKpiPmDto);
        }else if("2".equals(detail.getMainpart())){
            detail.setItemstatus(6L);
            addSubordinateEvaluation(detail,ppmKpiPmDto);
        }else{
            addTecEvaluation(detail,ppmKpiPmDto);
        }
    }

    /**
     * 添加科技部对经理的评价
     * @param detail 对象
     * @param ppmKpiPmDto 对象
     */
    private void addTecEvaluation(PpmKpiPmDetail detail,PpmKpiPmDto ppmKpiPmDto){
        log.info(ppmKpiPmDto.getId()+"");
        //获得科技部管理员审批人
        //KPI_EMPCONFIG
        String admin=eosDictEntryService.getTypeidForDetail("KPI_EMPCONFIG","ADMIN").getDictname();
        detail.setItemstatus(6L);
        detail.setSecondeval(admin);
    }
    /**
     * 添加下级对经理评价数据
     * @param detail 对象
     * @param ppmKpiPmDto 对象
     */
    private void addSubordinateEvaluation(PpmKpiPmDetail detail,PpmKpiPmDto ppmKpiPmDto){
        PpmKpiPostevaluationDto eval=new PpmKpiPostevaluationDto();
        if("DEP_MANAGER".equals(ppmKpiPmDto.getRole())){
            //查询部门下面的人员包括兼职部门从考核组织树那边取
            //List<CommDepartment> deps= commDepartmentService.queryDirectDeps(detail.getEid());
            PpmKpiRoleDto ppmKpiRoleDto =new PpmKpiRoleDto();
            ppmKpiRoleDto.setBadge(detail.getEid());
            //查询下级人员数据
            List<PpmKpiRoleDto> roles=ppmKpiRoleService.getPpmKpiRoleListByDirector(ppmKpiRoleDto);
            roles=roles.stream().filter(e->!e.getBadge().equals(detail.getEid())).collect(Collectors.toList());
            if(roles.size()>0){
                for (int i=0;i<roles.size();i++){
                    String badge=roles.get(i).getBadge();
                    setPpmKpiPostevaluation(detail,eval);
                    eval.setItemstatus(6L);
                    eval.setSecondeval(badge);
                    ppmKpiPostevaluationService.addPpmKpiPostevaluation(eval);
                }

            }else{
                detail.setItemstatus(7L);
                detail.setSecondevalscore(100.00);
                detail.setTscore(100.00);

            }
        }else{
            PpmKpiRoleInfoDto ppmKpiRoleInfoDto=new PpmKpiRoleInfoDto();
            ppmKpiRoleInfoDto.setFirstcheck(detail.getEid());
            //部门经理层，初评人是经理层的人员
            List<PpmKpiRoleInfoDto> roles=ppmKpiRoleService.getPpmKpiRoleView(ppmKpiRoleInfoDto);
            if(roles.size()>0){
                for (int i=0;i<roles.size();i++){
                    String badge=roles.get(i).getBadge();
                    setPpmKpiPostevaluation(detail,eval);
                    eval.setItemstatus(6L);
                    eval.setSecondeval(badge);
                    ppmKpiPostevaluationService.addPpmKpiPostevaluation(eval);
                }

            }else{
                detail.setItemstatus(7L);
                detail.setSecondevalscore(100.00);
                detail.setTscore(100.00);
            }
        }
    }
    /**
     * 添加上级对经理评价数据
     * @param detail 对象
     * @param ppmKpiPmDto 对象
     */
    private void addSupersiorPostEvaluation(PpmKpiPmDetail detail,PpmKpiPmDto ppmKpiPmDto){
        if("DEP_MANAGER".equals(ppmKpiPmDto.getRole())){
            String director=UsersUtil.getDeputyDirectorGeneral(ppmKpiPmDto.getDepid());
            detail.setItemstatus(6L);
            detail.setSecondeval(director);
        }else{
            // 部门分管总经理
            String director=UsersUtil.getDeputyDirectorGeneral(ppmKpiPmDto.getDepid());
            //部门主持工作经理
            CommEmployeeDto emp=UsersUtil.getTwoWorkManager(ppmKpiPmDto.getDepid());
            if(director.equals(emp.getBadge())){
                detail.setItemstatus(6L);
                detail.setSecondeval(director);
            }else{
                detail.setItemstatus(5L);
                detail.setSecondeval(director);
                detail.setFirsteval(emp.getBadge());
            }
        }
    }

    /**
     * 给履职评估赋值数据
     * @param detail 对象
     * @param eval 对象
     */
    private void setPpmKpiPostevaluation(PpmKpiPmDetail detail,PpmKpiPostevaluationDto eval){
        eval.setEid(detail.getEid());
        eval.setPid(detail.getId());
        eval.setItem(detail.getItem());
        eval.setItemdescribe(detail.getItemdescribe());
        eval.setPlan(detail.getPlan());
        eval.setPlandescribe(detail.getPlandescribe());
        eval.setValue(detail.getValue());
        eval.setFinishdate(detail.getFinishdate());
        eval.setWorkremark(detail.getWorkremark());
        eval.setWeight(detail.getWeight());
        eval.setMainpart(detail.getMainpart());
        eval.setEid(detail.getEid());
        eval.setTerm(detail.getTerm());
        eval.setTyear(detail.getTyear());
    }


    @Override
    @Transactional("defaultTx")
    public void autoLaunchSelfEval() {
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sysdate = dtf.format(now);
        Map param = new HashMap();
        param.put("itemstatus", "2");
        param.put("statuss", "'3','4'");
        param.put("finishdate", sysdate);
        param.put("tyear", tyear);
       List<PpmKpiPmDetail> list = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        PpmKpiPmDetail item=null;
        PpmKpiPmDto dto =new PpmKpiPmDto();//掺入参数
        PpmKpiPmDto ppmKpiPmDto =new PpmKpiPmDto();//返回数据
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            //更新主表为评价中
            long pid=item.getPid();
            dto.setId(pid);
            ppmKpiPmDto=ppmKpiPmService.queryDetail(dto);
            if(StringUtil.isNotEmpty(ppmKpiPmDto)){
                ppmKpiPmDto.setStatus(4L);
                ppmKpiPmService.updatePpmKpiPm(ppmKpiPmDto);
            }
            //更新子表为自评，初评状态
            if(item.getKpitype()==1){
                item.setItemstatus(4L);
            }else if(item.getKpitype()==2||item.getKpitype()==6){
                PpmKpiRole role=ppmKpiRoleService.getPpmKpiRoleByBadge(item.getEid());
                if(StringUtil.isNotBlank(role.getFirstcheck())){
                    //提交未初评
                    item.setItemstatus(5L);
                }else{
                    //提交待终评
                    item.setItemstatus(6L);
                }
            }else if(item.getKpitype()==3){
                //履职评估
                launchPostEvaluation(item,ppmKpiPmDto);
            }else{
                item.setItemstatus(4L);
            }
            ppmKpiPmDetailService.updatePpmKpiPmDetail(item);
        }
    }
    @Transactional("defaultTx")
    @Override
    public int submitSelfEval(PpmKpiPmDto ppmKpiPmDto){
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        Map param = new HashMap();
        param.put("pid", pm.getId());
        param.put("itemstatus", "4");
        List<PpmKpiPmDetail> list = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        //检测数据中自评是否为空
        for (int i = 0; i < list.size(); i++) {
            PpmKpiPmDetail detail=list.get(i);
            if(!StringUtil.isNotEmpty(detail.getEvalself())&&detail.getKpitype()==1){
                return 0;
            }else if(!StringUtil.isNotEmpty(detail.getEvalself())&&detail.getKpitype()==5){
                return -1;
            }else{
                continue;
            }
        }
        //修改为为评价状态
        for (int i = 0; i < list.size(); i++) {
            PpmKpiPmDetail detail=list.get(i);
            if(detail.getKpitype()==1){
                PpmKpiRole role=ppmKpiRoleService.getPpmKpiRoleByBadge(detail.getEid());
                if(StringUtil.isNotBlank(role.getFirstcheck())){
                    //提交未初评
                    detail.setItemstatus(5L);
                }else if(role.getFirstcheck()!=null&&role.getFirstcheck().equals(role.getSecondcheck())){
                    //提交待终评
                    detail.setItemstatus(6L);
                } else{
                    //提交待终评
                    detail.setItemstatus(6L);
                }
            }else{
                detail.setItemstatus(7L);
            }
            ppmKpiPmDetailService.updatePpmKpiPmDetail(detail);
        }
        return 1;
    }

    @Override
    public void getPpmKpiPmInfoSummaryPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager,String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        //查询改人员角色
        String role=ppmKpiRoleService.retRole(userid);
        //boolean flag=imUserRoleRelService.isRoleUser("KPIADMIN",userid);
        if("DEP_MANAGER".equals(role)||"DEP_MANAGERS".equals(role)){
             map.put("check",userid);
        }
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getPpmKpiPmInfoSummary");
    }
    @Override
    public void autoLaunchHalfYearSelfEval(){
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();
        //查询当前年份经理年终自评开关
//        PpmKpiItemSwitchDto ppmKpiItemSwitchDto=new  PpmKpiItemSwitchDto();
//        ppmKpiItemSwitchDto.setTyear(tyear);
//        ppmKpiItemSwitchDto.setItemtype("7");
//        PpmKpiItemSwitchDto switchs =ppmKpiItemSwitchService.queryDetailByCondition(ppmKpiItemSwitchDto);
//        //如果开着进行状态修改
//        if(StringUtil.isNotEmpty(switchs)&&switchs.getIsswitch()==1){
//            String finsihtime=switchs.getFinishtime();
//            Date finsihdate= DateUtil.strToDate(finsihtime,"yyyy-MM-dd");
//            Date now=new Date();
//            if(now.compareTo(finsihdate)==1||now.compareTo(finsihdate)==0){
                //发起经理半年度自评
                launchSelfEval(tyear);
 //           }
        //}
    }
    @Transactional("defaultTx")
    @Override
    public int launchSelfEval(String tyear){
        //查询需要修改的数据
        Map param = new HashMap();
        param.put("itemstatus", "2");
        param.put("statuss", "'3','4'");
        param.put("tyear", tyear);
        param.put("roles", "'DEP_MANAGERS'");
        param.put("kpitypes", "'1','5'");
        List<PpmKpiPmDetail> list = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        for (int i = 0; i < list.size(); i++) {
            PpmKpiPmDetail detail=list.get(i);
            detail.setItemstatus(3L);
            ppmKpiPmDetailService.updatePpmKpiPmDetail(detail);
        }
        return 1;
    }
    @Transactional("defaultTx")
    @Override
    public int submitSelfEvalHalfSelf(PpmKpiPmDto ppmKpiPmDto){
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        Map param = new HashMap();
        param.put("pid", pm.getId());
        param.put("itemstatus", "3");
        List<PpmKpiPmDetail> list = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        //检测数据中自评是否为空
        for (int i = 0; i < list.size(); i++) {
            PpmKpiPmDetail detail=list.get(i);
            if(!StringUtil.isNotEmpty(detail.getHalfyearevalself())){
                return 0;
            }
        }
        //修改为为评价状态
        for (int i = 0; i < list.size(); i++) {
            PpmKpiPmDetail detail=list.get(i);
            detail.setItemstatus(2L);
            //把半年度自评赋值给自评
            detail.setEvalself(detail.getHalfyearevalself());
            ppmKpiPmDetailService.updatePpmKpiPmDetail(detail);
        }
        return 1;
    }

    @Override
    public MyKpiDataDto getMyKpi(MyKpiDataDto myKpiDataDto){
        //返回数据
        MyKpiDataDto retData=new MyKpiDataDto();
        //查询角色数据
        PpmKpiRoleInfoDto ppmKpiRoleInfoDto=new PpmKpiRoleInfoDto();
        ppmKpiRoleInfoDto.setBadge(myKpiDataDto.getEid());
        PpmKpiRoleInfoDto info =ppmKpiRoleService.getPpmKpiRoleInfo(ppmKpiRoleInfoDto);
        retData.setPpmKpiRoleInfoDto(info);
        //查询主记录参数
        PpmKpiPmInfoDto dto=new PpmKpiPmInfoDto();
        if("DEP_MANAGER".equals(info.getRole())||"DEP_MANAGERS".equals(info.getRole())){
            dto.setTyear(myKpiDataDto.getTyear());
            dto.setEid(myKpiDataDto.getEid());
            dto.setTerm("全年度");
            PpmKpiPmInfoDto ppmKpiPmInfoDto=ppmKpiPmService.queryKpiPmByCondition(dto);
            retData.setManager_kpis(ppmKpiPmInfoDto);
        }else{
            //查询上半年数据
            dto.setTyear(myKpiDataDto.getTyear());
            dto.setTerm("上半年");
            dto.setEid(myKpiDataDto.getEid());
            PpmKpiPmInfoDto ppmKpiPmInfoDtoFirst=ppmKpiPmService.queryKpiPmByCondition(dto);
            retData.setEmp_kpis_first_half(ppmKpiPmInfoDtoFirst);

            //查询下半年数据
            dto.setTyear(myKpiDataDto.getTyear());
            dto.setTerm("下半年");
            dto.setEid(myKpiDataDto.getEid());
            PpmKpiPmInfoDto ppmKpiPmInfoDtoLast=ppmKpiPmService.queryKpiPmByCondition(dto);
            retData.setEmp_kpis_last_half(ppmKpiPmInfoDtoLast);
        }
        dto.setTyear(myKpiDataDto.getTyear());
        dto.setTerm("年度");


        dto.setEid(myKpiDataDto.getEid());
        PpmKpiPmInfoDto ppmKpiPmInfoDtoYear=ppmKpiPmService.queryKpiPmByCondition(dto);
        retData.setMy_kpis_year_eval(ppmKpiPmInfoDtoYear);
        return retData;

    }
    @Override
    public PpmKpiPmInfoTotalDto getPpmKpiPmInfoTotalDto(PpmKpiPmInfoTotalDto ppmKpiPmInfoTotalDto){
        PpmKpiPmInfoTotalDto retData=new PpmKpiPmInfoTotalDto();
        PpmKpiPmInfoDto ppmKpiPmInfoDto = JsonUtil.jsonToClass(ppmKpiPmInfoTotalDto, PpmKpiPmInfoDto.class);
        PpmKpiPmInfoDto dto=ppmKpiPmService.queryKpiPmByCondition(ppmKpiPmInfoDto);
        if(StringUtil.isNotEmpty(dto)){
            retData= JsonUtil.jsonToClass(dto, PpmKpiPmInfoTotalDto.class);
            //kpi考核项
            Map param = new HashMap();
            param.put("pid", retData.getId());
            param.put("kpitype", "1");
            List<PpmKpiPmDetail> list = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
            retData.setKpsItems(list.size());

            //已完成考核项
            Map param1 = new HashMap();
            param1.put("pid", retData.getId());
            param1.put("kpitypes", "'1'");
            param1.put("itemstatus", "7");
            List<PpmKpiPmDetail> list1 = ppmKpiPmDetailService.queryPpmKpiPmDetails(param1);
            retData.setFinishItems(list1.size());

            //评价中
            Map param2 = new HashMap();
            param2.put("pid", retData.getId());
            param2.put("kpitypes", "'1'");
            param2.put("itemstatuss", "'4','5','6'");
            List<PpmKpiPmDetail> list2 = ppmKpiPmDetailService.queryPpmKpiPmDetails(param2);
            retData.setEvalItems(list2.size());

            //能力提升项
            Map param3 = new HashMap();
            param3.put("pid", retData.getId());
            param3.put("kpitypes", "5");
            List<PpmKpiPmDetail> list3 = ppmKpiPmDetailService.queryPpmKpiPmDetails(param3);
            retData.setAbilityItems(list3.size());

            //配置中
            Map param4 = new HashMap();
            param4.put("pid", retData.getId());
            param4.put("kpitypes", "'1'");
            param4.put("itemstatus", "0");
            List<PpmKpiPmDetail> list4 = ppmKpiPmDetailService.queryPpmKpiPmDetails(param4);
            retData.setConfigureItems(list4.size());


            //审批中
            Map param5 = new HashMap();
            param5.put("pid", retData.getId());
            param5.put("kpitypes", "'1'");
            param5.put("itemstatus", "1");
            List<PpmKpiPmDetail> list5 = ppmKpiPmDetailService.queryPpmKpiPmDetails(param5);
            retData.setApproveItems(list5.size());


        }
        return retData;

    }

    @Override
    public List<PpmKpiPmInfoDto> getPpmKpiPmInfoSummaryList(PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        //查询改人员角色
        String role=ppmKpiRoleService.retRole(userid);
        //boolean flag=imUserRoleRelService.isRoleUser("KPIADMIN",userid);
        if("DEP_MANAGER".equals(role)||"DEP_MANAGERS".equals(role)){
            map.put("check",userid);
        }
        List<PpmKpiPmInfo> list=myBatisDao.selectListT(PPMKPIPM, map,"getPpmKpiPmInfoSummary");
        List<PpmKpiPmInfoDto> item = JsonUtil.jsonToList(list, PpmKpiPmInfoDto.class);
        return item;
    }

    @Override
    public List<PpmKpiPmDto> getPpmKpiPmTyears(PpmKpiPmDto ppmKpiPmDto) {
        PpmKpiPm ppmKpiPm = JsonUtil.jsonToClass(ppmKpiPmDto, PpmKpiPm.class);
        List<PpmKpiPm> list = myBatisDao.selectListT(PPMKPIPM, ppmKpiPm, "getPpmKpiPmTyears");
        List<PpmKpiPmDto> item = JsonUtil.jsonToList(list, PpmKpiPmDto.class);
        return item;
    }


    @Override
    public List<PpmKpiPmInfoDto> getPpmKpiPmEvalInfo(Map param) {
        List<PpmKpiPmInfo> list=myBatisDao.selectListT(PPMKPIPM, param,"getPpmKpiPmEvalInfo");
        List<PpmKpiPmInfoDto> item = JsonUtil.jsonToList(list, PpmKpiPmInfoDto.class);
        return item;
    }

    @Override
    public void getPpmKpiPmEvalInfoPage(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("check",userid);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getPpmKpiPmEvalInfo");
        if(StringUtil.isNotEmpty(pager.getList())){
            List<PpmKpiPmInfoDto> item = JsonUtil.jsonToList(pager.getList(), PpmKpiPmInfoDto.class);
            for (int i=0;i<item.size();i++){
                //double expertscore=getExpectScore(item.get(i));
                //item.get(i).setExpectScore(expertscore);
                int ret=retNoFillItem(item.get(i),userid);
                if(ret==1){
                    item.get(i).setNofillitem(0L);
                }else{
                    item.get(i).setNofillitem(1L);
                }
            }
            pager.setList(item);
        }
    }

    /**
     *  检查该评价人员是否有未填写的评价项
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return int
     */
    private int retNoFillItem(PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid){
        int retCode=1;
        JudgeKpiStatusDto jd=new JudgeKpiStatusDto();
        jd.setEid(ppmKpiPmInfoDto.getEid());
        jd.setCheck(userid);
        jd.setFlag("pj");
        int itemstatus=ppmKpiRoleService.judgeStatus(jd);
        if(itemstatus==0){
            return 0;//评价关系不对
        }
        Map param = new HashMap();
        param.put("pid", ppmKpiPmInfoDto.getId());
        param.put("kpitypes", "'1','2','3','6'");
        param.put("mainparts", "1");
        param.put("itemstatus", itemstatus);
        List<PpmKpiPmDetail> details = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        if(details.size()>0){
            //校验提交的数据的合法性
            retCode =judgeNull(details);
        }
        return retCode;
    }
    @Override
    public int empUpdateRole(PpmKpiPmDto ppmKpiPmDto,PpmKpiRoleDto ppmKpiRoleDto, String userid){
        //更新考核表中数据
        PpmKpiPmDto dto =ppmKpiPmService.queryDetail(ppmKpiPmDto);
        if(StringUtil.isNotBlank(ppmKpiRoleDto.getDepid())){
            dto.setDepid(ppmKpiRoleDto.getDepid());
        }
        if(StringUtil.isNotBlank(ppmKpiRoleDto.getCenterid())){
            dto.setCenterid(ppmKpiRoleDto.getCenterid());
        }
        if(StringUtil.isNotBlank(ppmKpiRoleDto.getRole())){
            dto.setRole(ppmKpiRoleDto.getRole());
        }
        ppmKpiPmService.updatePpmKpiPm(dto);
        //更新角色表中数据
        ppmKpiRoleService.updatePpmKpiRoleDtos(ppmKpiRoleDto,userid);
        return 1;
    }

    /**
     * 判断评分是否为空
     * @param details 对象
     * @return int 对象
     */
    private int judgeNull(List<PpmKpiPmDetail> details ){
        for (int i =0;i<details.size();i++){
            PpmKpiPmDetail detail=details.get(i);
            if(detail.getItemstatus()==5&&detail.getKpitype()!=6){
                if(detail.getFirstevalscore()==null){
                    return 3;//评分为空
                }
            }else if(detail.getItemstatus()==6&&detail.getKpitype()!=6){
                if(detail.getSecondevalscore()==null){
                    return 3;//评分为空
                }
            }else if(detail.getItemstatus()==5&&detail.getKpitype()==6){
                if(detail.getFirstevalcomment()==null){
                    return 3;//评价为空
                }
            }else if(detail.getItemstatus()==6&&detail.getKpitype()==6){
                if(detail.getSecondevalcomment()==null){
                    return 3;//评价为空
                }
            } else{
                return 4;//状态不对
            }

        }
        return 1;
    }
    @Override
    public int changeStatus(List<PpmKpiPmDetail> details,String userid){
        for (int i =0;i<details.size();i++){
            PpmKpiPmDetail detail=details.get(i);
            if(detail.getItemstatus()==5){
                detail.setItemstatus(6L);
                detail.setFirsteval(userid);
                detail.setFirstevaltime(new Date());
            }else if(detail.getItemstatus()==6){
                detail.setItemstatus(7L);
                detail.setSecondeval(userid);
                detail.setSecondevaltime(new Date());
            }else{
                detail.setItemstatus(4L);
            }
            ppmKpiPmDetailService.updatePpmKpiPmDetail(detail);
        }
        return 1;
    }

    @Override
    public int  submitKpiEval (PpmKpiPmDto ppmKpiPmDto,String userid){
        int retCode=1;
        //根据当前评价人和被评价人查询改执行的状态
        JudgeKpiStatusDto dto=new JudgeKpiStatusDto();
        dto.setEid(ppmKpiPmDto.getEid());
        dto.setCheck(userid);
        dto.setFlag("pj");
        int itemstatus=ppmKpiRoleService.judgeStatus(dto);
        if(itemstatus==0){
            return 0;//评价关系不对
        }
        Map param = new HashMap();
        param.put("pid", ppmKpiPmDto.getId());
        param.put("kpitype", "1");
        param.put("itemstatus", itemstatus);
        List<PpmKpiPmDetail> details = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        if(details.size()>0){
            //校验提交的数据的合法性
            retCode =judgeNull(details);
        }else{
            retCode= 2;//没有可提交的数据
        }
        if(retCode==1){//如果判断成功提交数据
            changeStatus(details,userid);
            //计算分数
            ppmKpiPm2Service.summaryScore(ppmKpiPmDto.getId());
            //判断主记录状态
            updateMainStatusByItemstatus(ppmKpiPmDto.getId());
        }
        return retCode;//提交成功
    }

    @Override
    public void getBalanceSummaryInfoPage(PpmKpiPmBalanceSummaryDto ppmKpiPmBalanceSummaryDto,
                                          Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiPmBalanceSummaryDto,PpmKpiPmBalanceSummaryDto.class);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getBalanceSummaryInfo");
    }

    @Override
    public List<PpmKpiPmBalanceSummaryDto> getBalanceSummaryInfoPage(
            PpmKpiPmBalanceSummaryDto ppmKpiPmBalanceSummaryDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(
                ppmKpiPmBalanceSummaryDto,PpmKpiPmBalanceSummaryDto.class);
        List<PpmKpiPmBalanceSummary> list=myBatisDao.selectListT(
                PPMKPIPM, map,"getBalanceSummaryInfo");
        List<PpmKpiPmBalanceSummaryDto> item = JsonUtil.jsonToList(
                list, PpmKpiPmBalanceSummaryDto.class);
        return item;
    }
    @Override
    public CheckEvalItemsSummary getCheckEvalItemsSummary(CheckEvalItemsSummary checkEvalItemsSummary){
        //根据当前评价人和被评价人查询改执行的状态
        JudgeKpiStatusDto dto=new JudgeKpiStatusDto();
        dto.setEid(checkEvalItemsSummary.getEid());
        dto.setCheck(checkEvalItemsSummary.getCheck());
        dto.setFlag("pj");
        int itemstatus=ppmKpiRoleService.judgeStatus(dto);
        if(itemstatus>0){
            checkEvalItemsSummary.setItemstatus(itemstatus+"");
            //获得kpi的评价项
            Map kpiparam = new HashMap();
            kpiparam.put("pid", checkEvalItemsSummary.getPid());
            kpiparam.put("kpitype", "1");
            kpiparam.put("itemstatus", itemstatus);
            List<PpmKpiPmDetail> kpidDtails = ppmKpiPmDetailService.queryPpmKpiPmDetails(kpiparam);

            checkEvalItemsSummary.setKpi_items(kpidDtails.size());


            //获得综合能力的评价项
            Map comprehensiveAbilityparam = new HashMap();
            comprehensiveAbilityparam.put("pid", checkEvalItemsSummary.getPid());
            comprehensiveAbilityparam.put("kpitype", "2");
            comprehensiveAbilityparam.put("itemstatus", itemstatus);
            List<PpmKpiPmDetail> comprehensiveAbilityDetails = ppmKpiPmDetailService.
                    queryPpmKpiPmDetails(comprehensiveAbilityparam);

            checkEvalItemsSummary.setComprehensiveAbility_items(comprehensiveAbilityDetails.size());

            //上半年评语
            Map upCommentParam = new HashMap();
            upCommentParam.put("pid", checkEvalItemsSummary.getPid());
            upCommentParam.put("kpitype", "6");
            upCommentParam.put("term", "上半年");
            upCommentParam.put("itemstatus", itemstatus);
            List<PpmKpiPmDetail> upCommentDetails = ppmKpiPmDetailService.
                    queryPpmKpiPmDetails(upCommentParam);

            checkEvalItemsSummary.setFirst_half_year_comment_items(upCommentDetails.size());

            //下半年评语

            Map downCommentParam = new HashMap();
            downCommentParam.put("pid", checkEvalItemsSummary.getPid());
            downCommentParam.put("kpitype", "6");
            downCommentParam.put("term", "下半年");
            downCommentParam.put("itemstatus", itemstatus);
            List<PpmKpiPmDetail> downCommentDetails = ppmKpiPmDetailService.
                    queryPpmKpiPmDetails(downCommentParam);

            checkEvalItemsSummary.setLast_half_year_comment_items(downCommentDetails.size());

            //履职评估指标项
            Map postEvaluationItems=new HashMap();
            postEvaluationItems.put("pid", checkEvalItemsSummary.getPid());
            postEvaluationItems.put("kpitype", "3");
            postEvaluationItems.put("check",checkEvalItemsSummary.getCheck());
            List<PpmKpiPmDetail> postEvaluationList=ppmKpiPmDetailService.
                    queryPpmKpiPmDetails(postEvaluationItems);
            checkEvalItemsSummary.setPostEvaluation_items(postEvaluationList.size());
        }
        return checkEvalItemsSummary;
    }
    @Override
    public  int updateMainStatusByItemstatus(long pid){
        int retCode=0;
        //获得未完成的评价项
        Map param = new HashMap();
        param.put("pid", pid);
        param.put("notFinish", "7");
        List<PpmKpiPmDetail> kpidDtails = ppmKpiPmDetailService.queryPpmKpiPmDetails(param);
        if(kpidDtails.size()<=0){
            //更新主状态
            PpmKpiPmDto ppmKpiPmDto=new PpmKpiPmDto();
            ppmKpiPmDto.setId(pid);
            ppmKpiPmDto.setStatus(8L);
            retCode= ppmKpiPmService.updatePpmKpiPm(ppmKpiPmDto);
        }
        return retCode;
    }

    @Override
    public double getExpectScore(PpmKpiPmInfoDto ppmKpiPmInfo){
        double retCode=0;
        PpmKpiPmInfo data =getPpmKpiPmInfoSummaryScore(ppmKpiPmInfo);
        if(StringUtil.isNotEmpty(data)&&data.getTscore()!=null){
            retCode=data.getTscore();
        }
        return retCode;
    }

    /**
     *  根据参数查询考核人员汇总数据
     * @param ppmKpiPmInfo 对象
     * @return PpmKpiPmInfo
     */
    private PpmKpiPmInfo getPpmKpiPmInfoSummaryScore(PpmKpiPmInfoDto ppmKpiPmInfo){
        Map param=new HashMap();
        param.put("tyear", ppmKpiPmInfo.getTyear());
        param.put("eid",ppmKpiPmInfo.getEid());
        PpmKpiPmInfo data = myBatisDao.selectClass(PPMKPIPM, param, "getEmpKpiData");
        return data;
    }
}
