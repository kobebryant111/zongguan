package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.KpiLevelPersons;
import com.work.ppm.application.dto.kpi.PpmKpiDepRoleDto;
import com.work.ppm.application.dto.kpi.PpmKpiDepRoleInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonDto;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleInfoDto;
import com.work.ppm.application.dto.systemmanager.PpmDaCenterDto;
import com.work.ppm.application.model.kpi.PpmKpiPm;
import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import com.work.ppm.application.model.kpi.PpmKpiRole;
import com.work.ppm.application.model.kpi.ext.PpmKpiPmInfo;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
  * 描述
  */
@Service("ppmKpiPm2Service")
public class PpmKpiPm2Service extends BaseDaoSupper implements IPpmKpiPm2Service {
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
    private IPpmKpiPm1Service ppmKpiPm1Service;
    @Override
    public void summaryScore(long pid){
        PpmKpiPmDto ppmKpiPmDto=new PpmKpiPmDto();
        ppmKpiPmDto.setId(pid);
        ppmKpiPmDto=ppmKpiPmService.queryDetail(ppmKpiPmDto);
        if(StringUtil.isNotEmpty(ppmKpiPmDto)){
            Map param =new HashMap();
            param.put("pid",pid);
            //计算kpi考核
            param.put("kpitype","1");
            double kpi=ppmKpiPmDetailService.getKpiOrBalanceSumScore(param);
            ppmKpiPmDto.setKpiscore(kpi);
            //计算平衡计分
            Map param1 =new HashMap();
            param1.put("tyear",ppmKpiPmDto.getTyear());
            param1.put("eid",ppmKpiPmDto.getEid());
            param1.put("kpitype","4");
            double balance=ppmKpiPmDetailService.getKpiOrBalanceSumScore(param1);
            ppmKpiPmDto.setBalancescore(balance);
            //计算综合能力评估
            param.put("kpitype","2");
            double comprehensiveAbility=ppmKpiPmDetailService.getComprehensiveAbilityOrPostEvaluationSumScore(param);
            ppmKpiPmDto.setComprehensivescore(comprehensiveAbility);
            //计算履职评估
            param.put("kpitype","3");
            double postEvaluation=ppmKpiPmDetailService.getComprehensiveAbilityOrPostEvaluationSumScore(param);
            ppmKpiPmDto.setPerformanceevaluationscore(postEvaluation);
            //计算二级部得分
            double depscore=ppmKpiPmDto.getDeptscore()==null?0:ppmKpiPmDto.getDeptscore();
            if("COMM_USER".equals(ppmKpiPmDto.getRole())){
                if("上半年".equals(ppmKpiPmDto.getTerm())){
                    ppmKpiPmDto.setTscore((kpi*0.7+comprehensiveAbility*0.3)*0.4);
                }else{
                    ppmKpiPmDto.setTscore((kpi*0.7+comprehensiveAbility*0.3)*0.6+balance);
                }

            }else if("DEP_MANAGER".equals(ppmKpiPmDto.getRole())){
                ppmKpiPmDto.setTscore(postEvaluation*0.3+depscore*0.7+balance);
            }else{
                //DEP_MANAGERS
                ppmKpiPmDto.setTscore(kpi*0.4+postEvaluation*0.3+depscore*0.3+balance);
            }
            ppmKpiPmService.updatePpmKpiPm(ppmKpiPmDto);
        }
    }
    @Override
    public void getEmpKpiDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager,String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        //查询改人员角色
        String role=ppmKpiRoleService.retRole(userid);
        //boolean flag=imUserRoleRelService.isRoleUser("KPIADMIN",userid);
        if("DEP_MANAGER".equals(role)||"DEP_MANAGERS".equals(role)){
            map.put("check",userid);
        }
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getEmpKpiData");
    }
    @Override
    public List<PpmKpiPmInfoDto> getEmpKpiDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        //查询改人员角色
        String role=ppmKpiRoleService.retRole(userid);
        //boolean flag=imUserRoleRelService.isRoleUser("KPIADMIN",userid);
        if("DEP_MANAGER".equals(role)||"DEP_MANAGERS".equals(role)){
            map.put("check",userid);
        }
        List<PpmKpiPmInfo> list = myBatisDao.selectListT(PPMKPIPM, map, "getEmpKpiData");
        List<PpmKpiPmInfoDto> item = JsonUtil.jsonToList(list, PpmKpiPmInfoDto.class);
        return item;
    }
    @Override
    public void queryKpiPmByConditionPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"queryKpiPmByCondition");
    }
    @Override
    public List<PpmKpiPmInfoDto> queryKpiPmByConditionList(PpmKpiPmInfoDto ppmKpiPmInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        List<PpmKpiPmInfo> lists = myBatisDao.selectListT(PPMKPIPM, map,"queryKpiPmByCondition");
        List<PpmKpiPmInfoDto> item = JsonUtil.jsonToList(lists, PpmKpiPmInfoDto.class);
        return item;
    }
    @Override
    public void getPostEvalInfoSummaryPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("check",userid);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getPostEvalInfo");
    }
    @Override
    public List<PpmKpiPmInfoDto> getPostEvalInfoSummaryList(Map param) {
        List<PpmKpiPmInfo> list=myBatisDao.selectListT(PPMKPIPM, param,"getPostEvalInfo");
        List<PpmKpiPmInfoDto> item = JsonUtil.jsonToList(list, PpmKpiPmInfoDto.class);
        return item;
    }
    @Override
    @Transactional("defaultTx")
    public int launchComprehensive(PpmKpiPmDto launchPpmKpiPmDto) {
        //获取当前考核年份
        String tyear=launchPpmKpiPmDto.getTyear();
        String term=launchPpmKpiPmDto.getTerm();
        if(!StringUtil.isNotBlank(tyear)&&!StringUtil.isNotBlank(term)){
            return 0;
        }
        Map param = new HashMap();
        param.put("itemstatus", "2");
        param.put("statuss", "'3','4'");
        param.put("kpitype", "2");
        param.put("tyear", tyear);
        param.put("term", term);
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
            PpmKpiRole role=ppmKpiRoleService.getPpmKpiRoleByBadge(item.getEid());
            if(StringUtil.isNotBlank(role.getFirstcheck())){
                //提交未初评
                item.setItemstatus(5L);
            }else{
                //提交待终评
                item.setItemstatus(6L);
            }
            ppmKpiPmDetailService.updatePpmKpiPmDetail(item);
        }
        return 1;
    }
    @Override
    @Transactional("defaultTx")
    public int launchPostEvaluationEval(PpmKpiPmDto launchPpmKpiPmDto) {
        //获取当前考核年份
        //EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        //String tyear=entryTyear.getDictname();
        String tyear=launchPpmKpiPmDto.getTyear();
        if(!StringUtil.isNotBlank(tyear)){
            return 0;
        }
        Map param = new HashMap();
        param.put("itemstatus", "2");
        param.put("statuss", "'3','4'");
        param.put("kpitype", "3");
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
            ppmKpiPm1Service.launchPostEvaluation(item,ppmKpiPmDto);
            ppmKpiPmDetailService.updatePpmKpiPmDetail(item);
        }
        return 1;
    }
    @Override
    public int launchYearData(PpmKpiPmDto ppmKpiPmDto,String userid){
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();
        //获取当前需要考核的人
        PpmKpiRoleInfoDto ppmKpiRoleInfoDto=new PpmKpiRoleInfoDto();
        List<PpmKpiRoleInfoDto> persons=ppmKpiRoleService.getPpmKpiRoleView(ppmKpiRoleInfoDto);
        if(persons.size()>0){
            //插入数据，计算总分
            for(int i=0;i<persons.size();i++){
                //插入员工考核数据
                insertEmpLoyeeYearData(persons.get(i),tyear,userid);
            }
        }
        //部门比例开始分配
        //查询所有总行科技部部门
        List<CommDepartment> deps=commDepartmentService.queryHeadOffice();
        if(StringUtil.isNotEmpty(deps)&&deps.size()>0){
            for (int i=0;i<deps.size();i++){
                setDepTgradeAndOrder(deps.get(i),tyear);
            }
        }
        //中心按比例开始分配
        PpmDaCenterDto ppmDaCenterDto=new PpmDaCenterDto();
        List<PpmDaCenterDto> centers= ppmDaCenterService.queryLists(ppmDaCenterDto);
        if(StringUtil.isNotEmpty(centers)&&centers.size()>0){
            for (int i=0;i<centers.size();i++){
                setCenTgradeAndOrder(centers.get(i),tyear);
            }
        }
        return 1;
    }
    /**
     * 设置中心比例和排序
     * @param ppmDaCenter 对象
     * @return int 对象
     */
    private int setCenTgradeAndOrder(PpmDaCenterDto ppmDaCenter,String tyear){
        //查询等级人数业务字典
        List<EosDictEntry> eos=eosDictEntryService.getTypeidForList("KPI_LEVEL");
        //获得当前部门下面的人员
        Map param=new HashMap();
        param.put("centerid",ppmDaCenter.getId());
        param.put("roles","'DEP_MANAGERS','DEP_MANAGER'");
        param.put("status","6");
        param.put("tyear",tyear);
        param.put("term","年度");
        param.put("order","p.tscore desc,p.eid asc");
        List<PpmKpiPm> persons=getPpmKpiPmListByOrder(param);
        PpmKpiLevelPersonDto ppmKpiLevelPersonDto=new PpmKpiLevelPersonDto();
        ppmKpiLevelPersonDto.setCenterid(ppmDaCenter.getId()+"");
        ppmKpiLevelPersonDto.setOtype("2");
        List<PpmKpiLevelPersonDto> lps=getLevelPersons(eos,ppmKpiLevelPersonDto,persons);
        if(lps.size()>0){
            int start=0;
            for (int i=0;i<lps.size();i++){
                PpmKpiLevelPersonDto lp=lps.get(i);
                //long nums=lp.getNums();
                start=setTgradeAndOrder(persons,lp,start);
            }
            updatePpmKpiLevelPersonDtos(persons);
        }
        return 1;
    }
    /**
     * 设置部门比例和排序
     * @param department 对象
     * @return int 对象
     */
    private int setDepTgradeAndOrder(CommDepartment department,String tyear){
        //查询等级人数业务字典
        List<EosDictEntry> eos=eosDictEntryService.getTypeidForList("KPI_LEVEL");
        //获得当前部门下面的人员
        Map param=new HashMap();
        param.put("depid",department.getDepid());
        param.put("role","COMM_USER");
        param.put("status","5");
        param.put("tyear",tyear);
        param.put("term","年度");
        param.put("order","p.tscore desc,p.eid asc");
        List<PpmKpiPm> persons=getPpmKpiPmListByOrder(param);
        PpmKpiLevelPersonDto ppmKpiLevelPersonDto=new PpmKpiLevelPersonDto();
        ppmKpiLevelPersonDto.setDepid(department.getDepid()+"");
        ppmKpiLevelPersonDto.setOtype("1");
        List<PpmKpiLevelPersonDto> lps=getLevelPersons(eos,ppmKpiLevelPersonDto,persons);
        if(lps.size()>0){
            int start=0;
            for (int i=0;i<lps.size();i++){
                PpmKpiLevelPersonDto lp=lps.get(i);
                //long nums=lp.getNums();
                start=setTgradeAndOrder(persons,lp,start);
            }
            updatePpmKpiLevelPersonDtos(persons);
        }
        return 1;
    }
    /**
     * List集合更新
     * @param persons 对象
     * @return int 对象
     */
    @Transactional("defaultTx")
    private int updatePpmKpiLevelPersonDtos(List<PpmKpiPm> persons){
        for(int i=0;i<persons.size();i++){
            updatePpmKpiPm(persons.get(i));
        }
        return 1;
    }
    /**
     * 设置等级和人数
     * @param persons 对象
     * @param lp 对象
     * @param start 对象
     * @return int 对象
     */
    private int setTgradeAndOrder(List<PpmKpiPm> persons,PpmKpiLevelPersonDto lp,int start){
        String tlevel=lp.getTlevel();
        int nums=lp.getNums().intValue();
        if(start+nums<=persons.size()){
           for(int i=start;i<start+nums;i++){
               persons.get(i).setTgrade(tlevel);
               persons.get(i).setTeamorder((long)i+1);
               persons.get(i).setInitialtlevel(tlevel);
               persons.get(i).setInitialorder((long)i+1);
               persons.get(i).setDeptlevel(tlevel);
               persons.get(i).setDeporder((long)i+1);
               persons.get(i).setCenterorder((long)i+1);
               persons.get(i).setCentertlevel(tlevel);
           }
           start=start+nums;
        }else{
            for(int i=start;i<persons.size();i++){
                persons.get(i).setTgrade(tlevel);
                persons.get(i).setTeamorder((long)i+1);
                persons.get(i).setInitialtlevel(tlevel);
                persons.get(i).setInitialorder((long)i+1);
                persons.get(i).setDeptlevel(tlevel);
                persons.get(i).setDeporder((long)i+1);
                persons.get(i).setCenterorder((long)i+1);
                persons.get(i).setCentertlevel(tlevel);
            }
            start=persons.size();
        }
        return start;
    }
    /**
     * 返回经过计算的人员比例数据（非要不配全，让你计算，我TM简直了）
     * @param eos 对象
     * @param ppmKpiLevelPersonDto 对象
     * @param persons 对象
     * @return  List<PpmKpiLevelPersonDto> 对象
     */
    private List<PpmKpiLevelPersonDto> getLevelPersons(List<EosDictEntry> eos,
                                                       PpmKpiLevelPersonDto ppmKpiLevelPersonDto,
                                                       List<PpmKpiPm> persons){
        long avg=0;
        //查询部门配置的总人数
        Long sumLevelPersons=getSumLevelPersons(ppmKpiLevelPersonDto);
        //查询当前没有配置的等级
        List<PpmKpiLevelPersonDto> notLevel= ppmKpiLevelPersonService.getNoSetLevel(ppmKpiLevelPersonDto);
        if(persons.size()>0&&persons.size()>=sumLevelPersons&&notLevel.size()>0){
            avg=Math.round((persons.size()-sumLevelPersons)/notLevel.size());
        }
        List<PpmKpiLevelPersonDto> lps=new ArrayList<PpmKpiLevelPersonDto>();
        for(int i=0;i<eos.size();i++){
            if(i==eos.size()-1){
                avg=persons.size()-(avg*i);
            }
            PpmKpiLevelPersonDto dto=new PpmKpiLevelPersonDto();
            dto.setTlevel(eos.get(i).getDictid());
            if("1".equals(ppmKpiLevelPersonDto.getOtype())){
                dto.setOtype("1");
                dto.setDepid(ppmKpiLevelPersonDto.getDepid()+"");
                dto.setRole("COMM_USER");
            }else{
                dto.setOtype("2");
                dto.setCenterid(ppmKpiLevelPersonDto.getCenterid());
                dto.setRole("DEP_MANAGERS");
            }
            List<PpmKpiLevelPersonDto> dtos= ppmKpiLevelPersonService.getPpmKpiLevelPersons(dto);
            if(dtos.size()>0){
                if(dtos.get(0).getNums()!=0){
                    dto.setNums(dtos.get(0).getNums());
                }else{
                    dto.setNums(avg);
                }
            }else{
                dto.setNums(avg);
            }
            lps.add(dto);
        }
        return lps;
    }
    /**
     * 根据部门或者中心查询比例配置中的总人数
     * @param ppmKpiLevelPersonDto 对象
     * @return long 对象
     */
    private Long getSumLevelPersons(PpmKpiLevelPersonDto ppmKpiLevelPersonDto){
        Long sum=0L;
       List<PpmKpiLevelPersonDto> lps= ppmKpiLevelPersonService.getPpmKpiLevelPersons(ppmKpiLevelPersonDto);
       if(lps.size()>0){
           sum=lps.stream().collect(Collectors.summarizingLong(PpmKpiLevelPersonDto::getNums)).getSum();
       }
       return sum;
    }
    /**
     * 添加员工年度数据
     * @param ppmKpiRoleInfoDto 对象
     * @param tyear 对象
     * @param userid 对象
     * @return int 对象
     */
    @Transactional("defaultTx")
    private int insertEmpLoyeeYearData(PpmKpiRoleInfoDto ppmKpiRoleInfoDto,String tyear,String userid){
        //根据当前人获取当前人年度总分
        //员工：（KPI上半年评分x70%+上半年综合能力评估x30%）x40%+
        // （KPI下半年评分x70%+下半年综合能力评估x30%）x60%+平衡积分=考核总分
        //分管经理：年度KPI总分x40%+履职评分x30%+二级部绩效30%+平衡积分=考核总分
        //主持经理：二级部绩效70%+履职评分x30+平衡积分=考核总分
        //取值方法上半年tscore加上下半年tscore
        PpmKpiPm ppmKpiPm=new PpmKpiPm();
        ppmKpiPm.setTyear(tyear);
        ppmKpiPm.setEid(ppmKpiRoleInfoDto.getBadge());
        PpmKpiPm sumScore=getSumScore(ppmKpiPm);
        if(!StringUtil.isNotEmpty(sumScore)){
            sumScore=new PpmKpiPm();
            sumScore.setTscore(0.00);
        }
        //判断是否存在该条年度数据
        ppmKpiPm.setTerm("年度");
        PpmKpiPm data=getPpmKpiPm(ppmKpiPm);
        if(StringUtil.isNotEmpty(data)){
            //更新数据
            data.setTscore(sumScore.getTscore());
            updatePpmKpiPm(data);
        }else{
            PpmKpiPm newPpmKpiPm=new PpmKpiPm();
            newPpmKpiPm.setTyear(tyear);
            newPpmKpiPm.setTerm("年度");
            newPpmKpiPm.setEid(ppmKpiRoleInfoDto.getBadge());
            newPpmKpiPm.setDepid(ppmKpiRoleInfoDto.getDepid());
            newPpmKpiPm.setJobid(ppmKpiRoleInfoDto.getJobid());
            if("COMM_USER".equals(ppmKpiRoleInfoDto.getRole())){
                newPpmKpiPm.setStatus(5L);
            }else {
                newPpmKpiPm.setStatus(6L);
            }
            newPpmKpiPm.setCreatetime(new Date());
            newPpmKpiPm.setCreateuser(userid);
            newPpmKpiPm.setIsedit(0L);
            //设置分数
            newPpmKpiPm.setTscore(sumScore.getTscore());
            newPpmKpiPm.setInitialtscore(sumScore.getTscore());
            newPpmKpiPm.setDeptscore(sumScore.getTscore());
            newPpmKpiPm.setHeadship(ppmKpiRoleInfoDto.getHeadship());
            newPpmKpiPm.setKpiscore(sumScore.getKpiscore());
            newPpmKpiPm.setComprehensivescore(sumScore.getComprehensivescore());
            newPpmKpiPm.setPerformanceevaluationscore(sumScore.getPerformanceevaluationscore());
            newPpmKpiPm.setBalancescore(sumScore.getBalancescore());
            newPpmKpiPm.setCenterid(ppmKpiRoleInfoDto.getCenterid());
            newPpmKpiPm.setRole(ppmKpiRoleInfoDto.getRole());
            addPpmKpiPm(newPpmKpiPm);
        }
        return 1;
    }
    /**
     * 根据工号和年份获取总分数据
     * @param ppmKpiPm 对象
     * @return PpmKpiPm 对象
     */
    private PpmKpiPm getSumScore(PpmKpiPm ppmKpiPm){
        PpmKpiPm data = myBatisDao.selectClass(PPMKPIPM, ppmKpiPm, "querySumScore");
        if(StringUtil.isNotEmpty(data)){
          return data;
        }
        return null;
    }
    /**
     * 判断数据是否存在
     * @param ppmKpiPm 对象
     * @return boolean 对象
     */
    private boolean isExist(PpmKpiPm ppmKpiPm){
        PpmKpiPm data = myBatisDao.selectClass(PPMKPIPM, ppmKpiPm,
                "queryDetailByCondition");
        if(StringUtil.isNotEmpty(data)){
            return true;
        }
        return false;
    }
    /**
     * 根据条件查询数据
     * @param ppmKpiPm 对象
     * @return PpmKpiPm 对象
     */
    private PpmKpiPm getPpmKpiPm(PpmKpiPm ppmKpiPm){
        PpmKpiPm data = myBatisDao.selectClass(PPMKPIPM, ppmKpiPm,
                "queryDetailByCondition");
        if(StringUtil.isNotEmpty(data)){
            return data;
        }
        return null;
    }
    /**
     * 更新数据
     * @param ppmKpiPm 对象
     * @return int 对象
     */
    private int updatePpmKpiPm(PpmKpiPm ppmKpiPm) {
        return myBatisDao.executionPojo(PPMKPIPM, ppmKpiPm, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }
    /**
     *  添加数据
     * @param ppmKpiPm 对象
     * @return int 对象
     */
    private int addPpmKpiPm(PpmKpiPm ppmKpiPm) {
        Long id = getNextSequence(PpmKpiPm.class);
        // 如果需要请set 主键id
        ppmKpiPm.setId(id);
        myBatisDao.executionPojo(PPMKPIPM, ppmKpiPm, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    /**
     * 通过条件和排序查询数据
     * @param param 对象
     * @return List<PpmKpiPm> 对象
     */
    private List<PpmKpiPm> getPpmKpiPmListByOrder(Map param) {
        List<PpmKpiPm> list=myBatisDao.selectListT(PPMKPIPM, param,"getPpmKpiPmListByOrder");
        return list;
    }
    @Override
    public void getDepYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("check",userid);
        map.put("status","5");
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getDepYearData");
    }
    @Override
    public List<PpmKpiPmInfoDto> getDepYearDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("check",userid);
        map.put("status","5");
        List<PpmKpiPmInfoDto> list=myBatisDao.selectListT(PPMKPIPM, map,"getDepYearData");
        return list;
    }
    @Override
    public int addScore(PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid){
        //更新修改的分数
        PpmKpiPmDto dto=new PpmKpiPmDto();
        dto.setId(ppmKpiPmInfoDto.getId());
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(dto);
        updateDepAddScore(ppmKpiPmInfoDto,pm);
        //重新计算排序列
        setDepTgradeAndOrderConfirm(pm.getDepid(),pm.getTyear());
        return 1;
    }
    /**
     * 更新部门年度考核数据
     * @param ppmKpiPmInfoDto 对象
     * @return  int 对象
     */
    @Transactional("defaultTx")
    private int updateDepAddScore(PpmKpiPmInfoDto ppmKpiPmInfoDto,PpmKpiPmDto pm){
        if(StringUtil.isNotEmpty(pm)){
            pm.setAddscore(ppmKpiPmInfoDto.getAddscore());
            double score=pm.getInitialtscore()+ppmKpiPmInfoDto.getAddscore();
            pm.setTscore(score);
            pm.setDeptscore(score);
            pm.setDepremark(ppmKpiPmInfoDto.getDepremark());
            ppmKpiPmService.updatePpmKpiPm(pm);
        }
        return 1;
    }
    /**
     * 设置部门比例和排序_确认时
     * @param depid 对象
     * @param tyear 对象
     * @return int 对象
     */
    private int setDepTgradeAndOrderConfirm(String depid,String tyear){
        //查询等级人数业务字典
        List<EosDictEntry> eos=eosDictEntryService.getTypeidForList("KPI_LEVEL");
        //获得当前部门下面的人员
        Map param=new HashMap();
        param.put("depid",depid);
        param.put("role","COMM_USER");
        param.put("status","5");
        param.put("tyear",tyear);
        param.put("term","年度");
        param.put("order","p.tscore desc,p.eid asc");
        List<PpmKpiPm> persons=getPpmKpiPmListByOrder(param);
        PpmKpiLevelPersonDto ppmKpiLevelPersonDto=new PpmKpiLevelPersonDto();
        ppmKpiLevelPersonDto.setDepid(depid);
        ppmKpiLevelPersonDto.setOtype("1");
        List<PpmKpiLevelPersonDto> lps=getLevelPersons(eos,ppmKpiLevelPersonDto,persons);
        if(lps.size()>0){
            int start=0;
            for (int i=0;i<lps.size();i++){
                PpmKpiLevelPersonDto lp=lps.get(i);
                //long nums=lp.getNums();
                start=setTgradeAndOrderConfirm(persons,lp,start);
            }
            updatePpmKpiLevelPersonDtos(persons);
        }
        return 1;
    }
    /**
     * 设置等级和人数
     * @param persons 对象
     * @param lp 对象
     * @param start 对象
     * @return int 对象
     */
    private int setTgradeAndOrderConfirm(List<PpmKpiPm> persons,PpmKpiLevelPersonDto lp,int start){
        String tlevel=lp.getTlevel();
        int nums=lp.getNums().intValue();
        if(start+nums<=persons.size()){
            for(int i=start;i<start+nums;i++){
                persons.get(i).setTgrade(tlevel);
                persons.get(i).setTeamorder((long)i+1);
                persons.get(i).setDeptlevel(tlevel);
                persons.get(i).setDeporder((long)i+1);
            }
            start=start+nums;
        }else{
            for(int i=start;i<persons.size();i++){
                persons.get(i).setTgrade(tlevel);
                persons.get(i).setTeamorder((long)i+1);
                persons.get(i).setDeptlevel(tlevel);
                persons.get(i).setDeporder((long)i+1);
            }
            start=persons.size();
        }
        return start;
    }
    @Override
    public int submitDepYearConfirm(String userid){
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();
        Map param=new HashMap();
        param.put("tyear",tyear);
        param.put("check",userid);
        return myBatisDao.executionPojo(PPMKPIPM, param, IMyBatisDao.SQL.UPDATE, "submitDepYearData");
    }
    @Override
    public List<PpmKpiPmInfoDto> getCenterYearDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("check",userid);
        map.put("status","6");
        if("DEP_MANAGERS".equals(ppmKpiPmInfoDto.getRole())){
            map.put("roles","'DEP_MANAGERS','DEP_MANAGER'");
            map.remove("role");
        }else{
            map.put("role",ppmKpiPmInfoDto.getRole());
        }
        List<PpmKpiPmInfoDto> list=myBatisDao.selectListT(PPMKPIPM, map,"getCenterYearData");
        return list;
    }
    @Override
    public void getCenterYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("check",userid);
        map.put("status","6");
        if("DEP_MANAGERS".equals(ppmKpiPmInfoDto.getRole())){
            map.put("roles","'DEP_MANAGERS','DEP_MANAGER'");
            map.remove("role");
        }else{
            map.put("role",ppmKpiPmInfoDto.getRole());
        }
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getCenterYearData");
    }
    @Transactional("defaultTx")
    @Override
    public int updateCenterTlevel(PpmKpiPmInfoDto ppmKpiPmInfoDto){
        //更新修改的等级
        PpmKpiPmDto dto=new PpmKpiPmDto();
        dto.setId(ppmKpiPmInfoDto.getId());
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(dto);
        pm.setCentertlevel(ppmKpiPmInfoDto.getCentertlevel());
        pm.setTgrade(ppmKpiPmInfoDto.getCentertlevel());
        pm.setCenterremark(ppmKpiPmInfoDto.getCenterremark());
        ppmKpiPmService.updatePpmKpiPm(pm);
        return 1;
    }
    @Override
    public int submitCenterYearEval(PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid){
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
         String tyear=entryTyear.getDictname();
        //获得当前人所分管中心
        PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto =new PpmKpiDepRoleInfoDto();
        ppmKpiDepRoleInfoDto.setCentercharge(userid);
        ppmKpiDepRoleInfoDto.setSign(2L);
        List<PpmKpiDepRoleInfoDto> centers=ppmKpiDepRoleService.getPpmKpiDepRoleView(ppmKpiDepRoleInfoDto);
        if(centers.size()>0){
            PpmKpiPmInfoDto param=new PpmKpiPmInfoDto();
            param.setTyear(tyear);
            param.setTerm("年度");
            if("DEP_MANAGERS".equals(ppmKpiPmInfoDto.getRole())){
                param.setRoles("'DEP_MANAGERS','DEP_MANAGER'");
            }else{
                param.setRole(ppmKpiPmInfoDto.getRole());
            }
            for (int i=0;i<centers.size();i++){
                String centerid=centers.get(i).getCenterid();
                param.setCenterid(centerid);
                //校验数据是否符合标准
                int retCode=checkCenterYearData(param);
                if(retCode!=2){
                    return retCode;
                }
            }
        }
        //提交当前中心数据
        List<PpmKpiPmInfoDto> list=getCenterYearDataList(ppmKpiPmInfoDto,userid);
        if(StringUtil.isNotEmpty(list)){
            for (int i=0;i<list.size();i++){
                PpmKpiPmDto ppmKpiPmDto = JsonUtil.jsonToClass(list.get(i), PpmKpiPmDto.class);
                ppmKpiPmDto.setCenterorder((long)i+1);
                ppmKpiPmDto.setStatus(7L);
                ppmKpiPmService.updatePpmKpiPm(ppmKpiPmDto);
            }
        }
        return 2;
    }
    /**
     * 校验数据是否正确
     * @param param 对象
     * @return int 对象
     */
    private int checkCenterYearData(PpmKpiPmInfoDto param){
        //第一步判断数据是否为空,根据部门查询和角色查询当前中心下人员
        List<PpmKpiPmInfoDto> list= queryKpiPmByConditionList(param);
        if(list.size()>0){
            for (int i=0;i<list.size();i++){
                PpmKpiPmInfoDto dto=list.get(i);
                if(dto.getCentertlevel()==null||dto.getTgrade()==null){
                    return 0;
                }
                if(dto.getStatus()!=6){
                    return 0;
                }
            }
        }
        //校验人数是否符合当前比例人数
        String centerid=param.getCenterid();
        PpmKpiLevelPersonDto lp=new PpmKpiLevelPersonDto();
        if(StringUtil.isNotBlank(param.getRoles())){
            lp.setRole("DEP_MANAGERS");
        }
        if(StringUtil.isNotBlank(param.getRole())){
            lp.setRole(param.getRole());
        }
        lp.setCenterid(centerid);
        lp.setOtype("2");
        List<PpmKpiLevelPersonDto> lps=ppmKpiLevelPersonService.getPpmKpiLevelPersons(lp);
        if(lps.size()>0){
            for(int i=0;i<lps.size();i++){
                String tlevel=lps.get(i).getTlevel();
                long nums=lps.get(i).getNums();
                Map param1=new HashMap();
                param1.put("tyear",param.getTyear());
                param1.put("term","年度");
                param1.put("role",param.getRole());
                if(StringUtil.isNotBlank(param.getRoles())){
                    param1.put("roles","'DEP_MANAGERS','DEP_MANAGER'");
                }
                if(StringUtil.isNotBlank(param.getRole())){
                    param1.put("role",param.getRole());
                }
                param1.put("centerid",param.getCenterid());
                param1.put("tgrade",tlevel);
                List<PpmKpiPm> persons=getPpmKpiPmListByOrder(param1);
                if(persons.size()>nums){
                    return 1;
                }
            }
        }
        return 2;
    }
    @Override
    public List<KpiLevelPersons> getCenterLevelPersonTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,
                                                         String userid){
        List<KpiLevelPersons> klps=new ArrayList<KpiLevelPersons>();
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.
                getTypeidForDetail("KPI_TIME","tyear");
        PpmKpiDepRoleDto ppmKpiDepRoleDto=new PpmKpiDepRoleDto();
        ppmKpiDepRoleDto.setCentercharge(userid);
        ppmKpiDepRoleDto.setSign(2L);
        List<PpmKpiDepRoleDto> centers=ppmKpiDepRoleService.getPpmKpiDepRoleList(ppmKpiDepRoleDto);
        if(!StringUtil.isNotEmpty(centers)) {
            return klps;
        }
        PpmKpiLevelPersonInfoDto dto=new PpmKpiLevelPersonInfoDto();
        dto.setOtype("2");
        dto.setRole(ppmKpiLevelPersonInfoDto.getRole());
        for(int i=0;i<centers.size();i++){
            String centerid=centers.get(i).getCenterid();
            dto.setCenterid(centerid);
            List<PpmKpiLevelPersonInfoDto> lps=ppmKpiLevelPersonService.
                    getPpmKpiLevelPersonsByCondition(dto);
            if(!StringUtil.isNotEmpty(lps)) {
                continue;
            }
            for(int j=0;j<lps.size();j++){
                KpiLevelPersons kpl=new KpiLevelPersons();
                kpl.setCenterid(lps.get(j).getCenterid());
                kpl.setCenterName(lps.get(j).getCentername());
                kpl.setTlevel(lps.get(j).getTlevel());
                kpl.setSetPersons(lps.get(j).getNums());
                kpl.setOrder(lps.get(j).getSortno());
                //查询当前人数
                PpmKpiPmInfoDto info=new PpmKpiPmInfoDto();
                if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
                    info.setRole(lps.get(j).getRole());
                    info.setCenterid(lps.get(j).getCenterid());
                    info.setTgrade(lps.get(j).getTlevel());
                    info.setTyear(entryTyear.getDictname());
                    info.setTerm("年度");
                }else{
                    info.setTgrade(lps.get(j).getTlevel());
                    info.setTyear(entryTyear.getDictname());
                    info.setTerm("年度");
                }
                if("DEP_MANAGERS".equals(ppmKpiLevelPersonInfoDto.getRole())){
                    info.setRoles("'DEP_MANAGERS','DEP_MANAGER'");
                    info.setRole("");
                }else{
                    info.setRole(ppmKpiLevelPersonInfoDto.getRole());
                }
                List<PpmKpiPmInfoDto> infos= queryKpiPmByConditionList(info);
                kpl.setCurrentPersons((long) (infos==null?0:infos.size()));
                klps.add(kpl);
            }
        }
        return klps;
    }
    @Override
    public List<KpiLevelPersons> getLevelPersonTip(
            PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,String userid){
        List<KpiLevelPersons> klps=new ArrayList<KpiLevelPersons>();
        //获取当前考核年份
        EosDictEntry entryTyear=eosDictEntryService.
                getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();
        PpmKpiLevelPersonInfoDto dto=new PpmKpiLevelPersonInfoDto();
        if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
            dto.setCenterid(ppmKpiLevelPersonInfoDto.getCenterid());
            dto.setOtype("2");
            dto.setRole(ppmKpiLevelPersonInfoDto.getRole());
        }else{
            dto.setOtype("3");
        }
        List<PpmKpiLevelPersonInfoDto> lps=ppmKpiLevelPersonService.
                getPpmKpiLevelPersonsByCondition(dto);
        if(StringUtil.isNotEmpty(lps)){
            for(int i=0;i<lps.size();i++){
                KpiLevelPersons kpl=new KpiLevelPersons();
                kpl.setCenterid(lps.get(i).getCenterid());
                kpl.setCenterName(lps.get(i).getCentername());
                kpl.setTlevel(lps.get(i).getTlevel());
                kpl.setSetPersons(lps.get(i).getNums());
                kpl.setOrder(lps.get(i).getSortno());
                //查询当前人数
                PpmKpiPmInfoDto info=new PpmKpiPmInfoDto();

                if("2".equals(ppmKpiLevelPersonInfoDto.getOtype())){
                    info.setRole(lps.get(i).getCenterid());
                    info.setCenterid(lps.get(i).getCenterid());
                    info.setTgrade(lps.get(i).getTlevel());
                    info.setTyear(tyear);
                    info.setTerm("年度");
                }else{
                    info.setTgrade(lps.get(i).getTlevel());
                    info.setTyear(tyear);
                    info.setTerm("年度");
                }
                List<PpmKpiPmInfoDto> infos= queryKpiPmByConditionList(info);
                kpl.setCurrentPersons((long) (infos==null?0:infos.size()));
                klps.add(kpl);
            }
        }
        return klps;
    }
    @Override
    public List<PpmKpiPmInfoDto> getTecYearDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("status","7");
        List<PpmKpiPmInfoDto> list=myBatisDao.selectListT(PPMKPIPM, map,"getTecYearData");
        return list;
    }
    @Override
    public void getTecYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmInfoDto,PpmKpiPmInfoDto.class);
        map.put("status","7");
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getTecYearData");
    }
    /**
     * 更新科技部年度考核数据
     * @param ppmKpiPmInfoDto 对象
     * @return  int 对象
     */
    @Transactional("defaultTx")
    @Override
    public int updateTecTlevel(PpmKpiPmInfoDto ppmKpiPmInfoDto){
        //更新修改的等级
        PpmKpiPmDto dto=new PpmKpiPmDto();
        dto.setId(ppmKpiPmInfoDto.getId());
        PpmKpiPmDto pm=ppmKpiPmService.queryDetail(dto);
        pm.setTgrade(ppmKpiPmInfoDto.getTgrade());
        ppmKpiPmService.updatePpmKpiPm(pm);
        return 1;
    }
    @Override
    public int submitTecYearEval(PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid){
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();//获取当前考核年份
        PpmKpiPmInfoDto param=new PpmKpiPmInfoDto();
        param.setTyear(tyear);
        param.setTerm("年度");
        int retCode=checkTecYearData(param);//校验数据是否符合标准
        if(retCode!=2){
            return retCode;
        }
        //提交当前中心数据
        List<PpmKpiPmInfoDto> list=getTecYearDataList(ppmKpiPmInfoDto,userid);
        if(StringUtil.isNotEmpty(list)){
            for (int i=0;i<list.size();i++){
                PpmKpiPmDto ppmKpiPmDto = JsonUtil.jsonToClass(list.get(i), PpmKpiPmDto.class);
                ppmKpiPmDto.setStatus(8L);
                ppmKpiPmService.updatePpmKpiPm(ppmKpiPmDto);
            }
        }
        return 2;
    }
    /**
     * 校验数据是否正确
     * @param param 对象
     * @return int 对象
     */
    private int checkTecYearData(PpmKpiPmInfoDto param){
        //第一步判断数据是否为空,根据部门查询和角色查询当前中心下人员
        List<PpmKpiPmInfoDto> list= queryKpiPmByConditionList(param);
        if(list.size()>0){
            for (int i=0;i<list.size();i++){
                PpmKpiPmInfoDto dto=list.get(i);
                if(dto.getTgrade()==null){
                    return 0;
                }
                if(dto.getStatus()!=7){
                    return 0;
                }
            }
        }
        //校验人数是否符合当前比例人数
        PpmKpiLevelPersonDto lp=new PpmKpiLevelPersonDto();
        lp.setOtype("3");
        List<PpmKpiLevelPersonDto> lps=ppmKpiLevelPersonService.getPpmKpiLevelPersons(lp);
        if(lps.size()>0){
            for(int i=0;i<lps.size();i++){
                String tlevel=lps.get(i).getTlevel();
                long nums=lps.get(i).getNums();
                Map param1=new HashMap();
                param1.put("tyear",param.getTyear());
                param1.put("term","年度");
                param1.put("tgrade",tlevel);
                List<PpmKpiPm> persons=getPpmKpiPmListByOrder(param1);
                if(persons.size()>nums){
                    return 1;
                }
            }
        }
        return 2;
    }
    @Override
    public void getDepStatusPager(PpmKpiPmDto ppmKpiPmDto, Pager pager){
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmDto,PpmKpiPmDto.class);
        myBatisDao.selectPager(PPMKPIPM, map,pager,"getDepStatus");
    }
    @Override
    public int updateDepStatus(PpmKpiPmDto ppmKpiPmDto){
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiPmDto,PpmKpiPmDto.class);
        return myBatisDao.executionPojo(PPMKPIPM, map, IMyBatisDao.SQL.UPDATE,"updateDepStatus");
    }
    @Override
    @Transactional("defaultTx")
    public int launchComment(PpmKpiPmDto launchPpmKpiPmDto) {
        //获取当前考核年份
        String tyear=launchPpmKpiPmDto.getTyear();
        String term=launchPpmKpiPmDto.getTerm();
        if(!StringUtil.isNotBlank(tyear)&&!StringUtil.isNotBlank(term)){
            return 0;
        }
        Map param = new HashMap();
        param.put("itemstatus", "2");
        param.put("statuss", "'3','4'");
        param.put("kpitype", "6");
        param.put("tyear", tyear);
        param.put("term", term);
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
            PpmKpiRole role=ppmKpiRoleService.getPpmKpiRoleByBadge(item.getEid());
            if(StringUtil.isNotBlank(role.getFirstcheck())){
                //提交未初评
                item.setItemstatus(5L);
            }else{
                //提交待终评
                item.setItemstatus(6L);
            }
            ppmKpiPmDetailService.updatePpmKpiPmDetail(item);
        }
        return 1;
    }
    @Override
    @Transactional("defaultTx")
    public int launchDepManagersSelfEval(PpmKpiPmDto launchPpmKpiPmDto) {
        //获取当前考核年份
        String tyear = launchPpmKpiPmDto.getTyear();
        if (!StringUtil.isNotBlank(tyear)) {
            return 0;
        }
        //发起经理半年度自评
        ppmKpiPm1Service.launchSelfEval(tyear);
        return 1;
    }
    @Override
    public KpiLevelPersons getNoSubmitCenterTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,
                                                         String userid){
        KpiLevelPersons klps=new KpiLevelPersons();
        PpmKpiDepRoleDto ppmKpiDepRoleDto=new PpmKpiDepRoleDto();
        ppmKpiDepRoleDto.setSign(2L);
        List<PpmKpiDepRoleDto> centers=ppmKpiDepRoleService.getPpmKpiDepRoleList(ppmKpiDepRoleDto);
        if(!StringUtil.isNotEmpty(centers)) {
            return klps;
        }
        String centernames="";
        //一共多少中心
        klps.setSetCens((long) (centers==null?0:centers.size()));
        //计算未提交的中心数
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();//获取当前考核年份
        Map map = new HashMap();
        map.put("tyear",tyear);
        List<PpmKpiPmInfo> list=myBatisDao.selectListT(PPMKPIPM, map,"getNoSubmitCenters");
        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                centernames=list.get(i).getCentername();
            }else{
                centernames=centernames+","+list.get(i).getCentername();
            }
        }
        klps.setCurrentCens((long) (list==null?0:list.size()));
        klps.setNoSubmitCenterName(centernames);
        return klps;
    }
    @Override
    public KpiLevelPersons getNoSubmitDepTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,
                                                String userid){
        KpiLevelPersons klps=new KpiLevelPersons();
        PpmKpiDepRoleDto ppmKpiDepRoleDto=new PpmKpiDepRoleDto();
        ppmKpiDepRoleDto.setCentercharge(userid);
        ppmKpiDepRoleDto.setSign(1L);
        List<PpmKpiDepRoleDto> deps=ppmKpiDepRoleService.getPpmKpiDepRoleList(ppmKpiDepRoleDto);
        if(!StringUtil.isNotEmpty(deps)) {
            return klps;
        }
        String depnames="";
        klps.setSetDeps((long) (deps==null?0:deps.size()));//一共多少中心
        //计算未提交的中心数
        EosDictEntry entryTyear=eosDictEntryService.getTypeidForDetail("KPI_TIME","tyear");
        String tyear=entryTyear.getDictname();//获取当前考核年份
        Map map = new HashMap();
        map.put("tyear",tyear);
        map.put("centercharge",userid);
        List<PpmKpiPmInfo> list=myBatisDao.selectListT(PPMKPIPM, map,"getNoSubmitDeps");
        for (int i = 0; i < list.size(); i++) {
            if(i==0){
                depnames=list.get(i).getDepname();
            }else{
                depnames=depnames+","+list.get(i).getDepname();
            }
        }
        klps.setCurrentDeps((long) (list==null?0:list.size()));
        klps.setNoSubmitDepName(depnames);
        return klps;
    }

    @Override
    public void summaryScoreForBalance( PpmKpiPm ppmKpiPm){
        List<PpmKpiPm> list=myBatisDao.selectListT(PPMKPIPM, ppmKpiPm,"getList");
        if(StringUtil.isNotEmpty(list)){
            for(int i=0;i<list.size();i++){
                PpmKpiPm pm=list.get(i);
                if("上半年".equals(pm.getTerm())||"年度".equals(pm.getTerm())){
                    continue;
                }
                Map param =new HashMap();
                param.put("pid",pm.getId());
                //计算kpi考核
                param.put("kpitype","1");
                double kpi=ppmKpiPmDetailService.getKpiOrBalanceSumScore(param);
                pm.setKpiscore(kpi);
                //计算平衡计分
                Map param1 =new HashMap();
                param1.put("tyear",pm.getTyear());
                param1.put("eid",pm.getEid());
                param1.put("kpitype","4");
                double balance=ppmKpiPmDetailService.getKpiOrBalanceSumScore(param1);
                pm.setBalancescore(balance);
                //计算综合能力评估
                param.put("kpitype","2");
                double comprehensiveAbility=ppmKpiPmDetailService.
                        getComprehensiveAbilityOrPostEvaluationSumScore(param);
                pm.setComprehensivescore(comprehensiveAbility);
                //计算履职评估
                param.put("kpitype","3");
                double postEvaluation=ppmKpiPmDetailService.
                        getComprehensiveAbilityOrPostEvaluationSumScore(param);
                pm.setPerformanceevaluationscore(postEvaluation);
                //计算二级部得分
                double depscore=pm.getDepscore()==null?0:pm.getDepscore();
                if("COMM_USER".equals(pm.getRole())){
                    if("上半年".equals(pm.getTerm())){
                        pm.setTscore((kpi*0.7+comprehensiveAbility*0.3)*0.4);
                    }else{
                        pm.setTscore((kpi*0.7+comprehensiveAbility*0.3)*0.6+balance);
                    }

                }else if("DEP_MANAGER".equals(pm.getRole())){
                    pm.setTscore(postEvaluation*0.3+depscore*0.7+balance);
                }else{
                    //DEP_MANAGERS
                    pm.setTscore(kpi*0.4+postEvaluation*0.3+depscore*0.3+balance);
                }
                myBatisDao.executionPojo(PPMKPIPM, pm, IMyBatisDao.SQL.UPDATE,"updatePojo");
            }

        }
    }
}