package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.JudgeKpiStatusDto;
import com.work.ppm.application.dto.kpi.PpmKpiRoleInfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.PpmDaCenterDto;
import com.work.ppm.application.model.kpi.ext.PpmKpiRoleInfo;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiPmService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiRole;
import com.work.ppm.application.dto.kpi.PpmKpiRoleDto;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UsersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
  * 描述
  */
@Service("ppmKpiRoleService")
public class PpmKpiRoleService extends BaseDaoSupper implements IPpmKpiRoleService {
    /**
      * 实体类路径
      */
    private static String PPMKPIROLE = "com.work.ppm.application.model.kpi.PpmKpiRole";
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
     * 部门分管接口
     */
    @Autowired
    private IPpmDaDeptManagerService ppmDaDeptManagerService;

    /**
     *  业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;


    /**
     *  团队表
     */
    @Autowired
    private IPpmDaTeamGroupService ppmDaTeamGroupService;


    /**
     *  中心表
     */
    @Autowired
    private IPpmDaCenterService PpmDaCenterService;

    /**
     *  人员表
     */
    @Autowired
    private ICommEmployeeService CommEmployeeService;

    /**
     *  考核主表
     */
    @Autowired
    private IPpmKpiPmService ppmKpiPmService;


    @Override
    public void queryPager(PpmKpiRoleDto ppmKpiRoleDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiRoleDto,PpmKpiRoleDto.class);
        myBatisDao.selectPager(PPMKPIROLE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto,String userid) {
        int retCode=0;
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        String username=UsersUtil.getUserNameByUserId(ppmKpiRole.getBadge());
        ppmKpiRole.setEmpname(username);
        Long id = getNextSequence(PpmKpiRole.class);
        // 如果需要请set 主键id
        ppmKpiRole.setId(id);
        ppmKpiRole.setLastmodtime(new Date());
        ppmKpiRole.setLastmoduserid(userid);
        retCode=myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.INSTER, "addPojo");
        return retCode;
    }
    @Override
    public PpmKpiRoleDto queryDetail(PpmKpiRoleDto ppmKpiRoleDto) {
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        PpmKpiRole data = myBatisDao.selectClass(PPMKPIROLE, ppmKpiRole,
        "queryDetail");
        PpmKpiRoleDto item = JsonUtil.jsonToClass(data, PpmKpiRoleDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto) {
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        return myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiRole(PpmKpiRoleDto ppmKpiRoleDto) {
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        return myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    /**
     *  根据id获得数据
     * @param id
     * @return PpmKpiRole
     */
    private PpmKpiRole getPpmKpiRoleById(Long id){
        PpmKpiRole ppmKpiRole=new PpmKpiRole();
        ppmKpiRole.setId(id);
        PpmKpiRole data = myBatisDao.selectClass(PPMKPIROLE, ppmKpiRole, "queryDetail");
        return data;
    }
    @Override
    public PpmKpiRole getPpmKpiRoleByBadge(String badge){
        PpmKpiRole ppmKpiRole=new PpmKpiRole();
        ppmKpiRole.setBadge(badge);
        PpmKpiRole data = myBatisDao.selectClass(PPMKPIROLE, ppmKpiRole, "queryDetailByBadge");
        return data;
    }
    @Override
    public PpmKpiRoleDto getPpmKpiRoleDtoByBadge(PpmKpiRoleDto ppmKpiRoleDto){
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        PpmKpiRole data = myBatisDao.selectClass(PPMKPIROLE, ppmKpiRole, "queryDetailByBadge");
        PpmKpiRoleDto item = JsonUtil.jsonToClass(data, PpmKpiRoleDto.class);
        return item;
    }
    /**
     * 返回权限
     * @param item PpmKpiRole
     * @return String
     */
    private String retRole(PpmKpiRole item){
        String depid=CommEmployeeService.getDeptNo(item.getBadge());
        if("131".equals(depid)){
            return "MANAGER";
        }
        List<CommDepartment> deps= commDepartmentService.queryDirectDeps(item.getBadge());
        if(deps.size()>0){
            return "DEP_MANAGER";
        }
        if("009".equals(item.getHeadship())||"010".equals(item.getHeadship())
                ||"030".equals(item.getHeadship())
                ||"037".equals(item.getHeadship())){
            return "DEP_MANAGERS";
        }
        return "COMM_USER";
    }
    @Override
    public String retRole(String badge){
        CommEmployee emp=CommEmployeeService.getCommEmployeeInfo(badge);
        if(emp.getDepid()==131){
            return "MANAGER";
        }
        List<CommDepartment> deps= commDepartmentService.queryDirectDeps(badge);
        if(deps.size()>0){
            return "DEP_MANAGER";
        }
        if("009".equals(emp.getHeadship())||"010".equals(emp.getHeadship())
                ||"030".equals(emp.getHeadship())||"037".equals(emp.getHeadship())){
            return "DEP_MANAGERS";
        }
        return "COMM_USER";
    }

    /**
     * retCheck
     * @param item
     * @return Map
     */
    private Map retCheck(PpmKpiRole item){
        Map<String,String> retMap=new HashMap<String,String>();
        List<CommDepartment> deps= commDepartmentService.queryDirectDeps(item.getBadge());
        if(deps.size()>0){
            String firstCheck= UsersUtil.getDeputyDirectorGeneral(item.getDepid());
            //EosDictEntry eos = eosDictEntryService.getTypeidForDetail("KHNDSPR", "SPR");
            //String secondCheck = eos.getDictname();
//            if(firstCheck.equals(secondCheck)){
//               // retMap.put("firstCheck",firstCheck);
//                retMap.put("
//                retMap.put("firstCheck",firstCheck);
                retMap.put("secondCheck",firstCheck);
//            }
        }else if("009".equals(item.getHeadship())||"010".equals(item.getHeadship())
                ||"030".equals(item.getHeadship())||"037".equals(item.getHeadship())){
            CommEmployeeDto employee= UsersUtil.getTwoWorkManager(item.getDepid());
            String firstCheck=employee.getBadge();
            String secondCheck= UsersUtil.getDeputyDirectorGeneral(item.getDepid());
            if(firstCheck.equals(secondCheck)){
                retMap.put("secondCheck",secondCheck);
            }else{
                retMap.put("firstCheck",firstCheck);
                retMap.put("secondCheck",secondCheck);
            }
        }else{
            String firstCheck=ppmDaTeamGroupService.queryGroupManager(item.getBadge());
            CommEmployeeDto employee= UsersUtil.getTwoWorkManager(item.getDepid());
            String secondCheck=employee.getBadge();
            if(firstCheck.equals(secondCheck)){
                retMap.put("secondCheck",secondCheck);
            }else{
                retMap.put("firstCheck",firstCheck);
                retMap.put("secondCheck",secondCheck);
            }
        }
        return retMap;
    }

    /**
     * 插入和更新权限角色表
     * @param badge
     * @param item
     * @param userid
     */
    private void insertOrUpdatePpmKpiRole(String badge,PpmKpiRole item,String userid){
        PpmKpiRole ppmKpiRole=getPpmKpiRoleByBadge(badge);
        if(ppmKpiRole!=null){
            String role=retRole(item);
            ppmKpiRole.setRole(role);
            ppmKpiRole.setJobid(item.getJobid());
            ppmKpiRole.setDepid(item.getDepid());
            ppmKpiRole.setCenterid(item.getCenterid());
            ppmKpiRole.setHeadship(item.getHeadship());
            Map param=retCheck(item);
            if(null!=param.get("firstCheck")){
                ppmKpiRole.setFirstcheck(param.get("firstCheck").toString());
            }
            if(null!=param.get("secondCheck")){
                ppmKpiRole.setSecondcheck(param.get("secondCheck").toString());
            }
            ppmKpiRole.setLastmodtime(new Date());
            ppmKpiRole.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
        }else{
            String role=retRole(item);
            item.setRole(role);
            Long id = getNextSequence(PpmKpiRole.class);
            item.setId(id);
            Map param=retCheck(item);
            if(null!=param.get("firstCheck")){
                item.setFirstcheck(param.get("firstCheck").toString());
            }
            if(null!=param.get("secondCheck")){
                item.setSecondcheck(param.get("secondCheck").toString());
            }
            item.setLastmodtime(new Date());

            item.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMKPIROLE, item, IMyBatisDao.SQL.INSTER, "addPojo");
        }
    }

    @Override
    @Transactional("defaultTx")
    public int insertPpmKpiRoles(PpmKpiRoleDto ppmKpiRoleDto,String userid) {
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        //先删除表中数据
        myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.DELETE, "deleteAll");
        //在进行插入操作
        List<PpmKpiRole> dtos = myBatisDao.selectListT(PPMKPIROLE, ppmKpiRole,"getEmpList");
        dtos.stream().forEach(item -> {
            String badge=item.getBadge();
            //String empname=item.getEmpname();
            //String jobid=item.getJobid();
            //String depid=item.getDepid();
            //String centerid=item.getCenterid();
            //String headship=item.getHeadship();
            insertOrUpdatePpmKpiRole(badge,item,userid);
        });
        return 1;
    }

    @Override
    public int updatePpmKpiRoles(List<PpmKpiRoleDto> ppmKpiRoleDtos, String userid) {
        ppmKpiRoleDtos.forEach(item->{
            PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(item, PpmKpiRole.class);
            ppmKpiRole.setLastmodtime(new Date());
            ppmKpiRole.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole,
                    IMyBatisDao.SQL.UPDATE,"updatePojo");
        });
        return 1;
    }


    @Override
    public List<PpmKpiRoleInfoDto> getPpmKpiRoleView(PpmKpiRoleInfoDto ppmKpiRoleInfoDto) {
        PpmKpiRoleInfo ppmKpiRoleInfo = JsonUtil.jsonToClass(
                ppmKpiRoleInfoDto, com.work.ppm.application.model.kpi.ext.PpmKpiRoleInfo.class);
        List<PpmKpiRoleInfo> datas = myBatisDao.selectListT(
                PPMKPIROLE, ppmKpiRoleInfo, "getPpmKpiRoleView");
        List<PpmKpiRoleInfoDto> item = JsonUtil.jsonToList(datas, PpmKpiRoleInfoDto.class);
        return item;
    }

    @Override
    public String retDepid(String depname){
        //参数
        CommDepartment department=new CommDepartment();
        department.setLocation(depname);
        department.setAdminid(130L);
        List<CommDepartment> deps=commDepartmentService.queryList(department);
        if(deps.size()>0){
            return deps.get(0).getDepid()+"";
        }else{
            return "";
        }
    }

    @Override
    public String retCenterid(String centername){
        //参数
        PpmDaCenterDto ppmDaCenter=new PpmDaCenterDto();
        ppmDaCenter.setCentername(centername);
        List<PpmDaCenterDto> dtos=PpmDaCenterService.queryLists(ppmDaCenter);
        if(dtos.size()>0){
            return dtos.get(0).getId()+"";
        }else{
            return "";
        }
    }

    /**
     *  匹配括号内工号
     * @param empname
     * @return String
     */
    private String getbadge(String empname){
        String reg="(?<=\\()[^\\)]+";
        Pattern p=Pattern.compile(reg);
        Matcher m=p.matcher(empname);
        while (m.find()){
            return m.group();
        }
        return "";
    }
    @Override
    @Transactional
    public boolean importExcel(MultipartFile file,String userid) {
        // 业务字典
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KPI_ROLE");

        // excel导入数据
        List<PpmKpiRoleInfoDto> list = ExcelUtil.importExcel(file, PpmKpiRoleInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiRoleInfoDto ii=list.get(i);
            Long id=ii.getId();
            PpmKpiRole role=getPpmKpiRoleById(id);
            //查询角色id
            String roleid =eos.stream().filter(iii->
                    ii.getRolename().equals(iii.getDictname())).findFirst().get().getDictid();
            //查询部门id
            String depid= retDepid(ii.getDepname());
            //查询中心编号
            String centerid=retCenterid(ii.getCentername());
            role.setRole(roleid);
            role.setDepid(depid);
            role.setCenterid(centerid);
            String firstcheck=ii.getFirstcheck();
            role.setFirstcheck(firstcheck);
            String secondcheck=ii.getSecondcheck();
            role.setSecondcheck(secondcheck);
            insertOrUpdatePpmKpiRole(role,userid);
        }


        return true;
    }
    /**
     * 插入和更新权限角色表
     * @param ppmKpiRole
     * @param userid
     */
    private void insertOrUpdatePpmKpiRole(PpmKpiRole ppmKpiRole,String userid){
        //PpmKpiRole ppmKpiRole= JsonUtil.jsonToClass(Item, PpmKpiRole.class);
        if(null!=ppmKpiRole.getId()&&!"".equals(ppmKpiRole.getId())){
            ppmKpiRole.setLastmodtime(new Date());
            ppmKpiRole.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
        }else{
            Long id = getNextSequence(PpmKpiRole.class);
            ppmKpiRole.setId(id);
            ppmKpiRole.setLastmodtime(new Date());
            ppmKpiRole.setLastmoduserid(userid);
            myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.INSTER, "addPojo");
        }
    }
    @Override
    public void queryPagerList(PpmKpiRoleInfoDto ppmKpiRoleInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiRoleInfoDto,PpmKpiRoleInfoDto.class);
        if("depid".equals(pager.getSortField())){
            pager.setSortField("r.depid");
        }else if("centername".equals(pager.getSortField())){
            pager.setSortField("r.centerid");
        }else if("depname".equals(pager.getSortField())){
            pager.setSortField("r.depid");
        }else if("badge".equals(pager.getSortField())){
            pager.setSortField("r.badge");
        }else if("empname".equals(pager.getSortField())){
            pager.setSortField("r.empname");
        }else if("firstcheck".equals(pager.getSortField())){
            pager.setSortField("r.firstcheck");
        }else if("secondcheck".equals(pager.getSortField())){
            pager.setSortField("r.secondcheck");
        }else if("rolename".equals(pager.getSortField())){
            pager.setSortField("r.role");
        }else {
            pager.setSortField("r.depid");
        }
        pager.setSortRequirdField("r.id");
        myBatisDao.selectPager(PPMKPIROLE, map,pager,"getPpmKpiRoleView");
    }

    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiRoleList(List<PpmKpiRoleInfoDto> ppmKpiRoleInfoDtos) {
        if(ppmKpiRoleInfoDtos.size()>0){
            for(int i=0;i<ppmKpiRoleInfoDtos.size();i++){
                PpmKpiRoleInfo ppmKpiRoleInfo = JsonUtil.jsonToClass(ppmKpiRoleInfoDtos.get(i), PpmKpiRoleInfo.class);
                PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleInfo, PpmKpiRole.class);
                myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.DELETE,"deleteById");
            }
        }
        return 1;
    }

    @Override
    @Transactional("defaultTx")
    public int updatePpmKpiRoleList(PpmKpiRoleInfoDto ppmKpiRoleInfoDto) {
        String badge=ppmKpiRoleInfoDto.getBadge();
        String role="";
        String depid="";
        String centerid="";
        String firstcheck="";
        String secondcheck="";
        if(null!=ppmKpiRoleInfoDto.getRole()){
            role=ppmKpiRoleInfoDto.getRole();
        }
        if(null!=ppmKpiRoleInfoDto.getDepid()){
            depid=ppmKpiRoleInfoDto.getDepid();
        }
        if(null!=ppmKpiRoleInfoDto.getCenterid()){
            centerid=ppmKpiRoleInfoDto.getCenterid();
        }
        if(null!=ppmKpiRoleInfoDto.getFirstcheck()){
            firstcheck=ppmKpiRoleInfoDto.getFirstcheck();
        }
        if(null!=ppmKpiRoleInfoDto.getSecondcheck()){
            secondcheck=ppmKpiRoleInfoDto.getSecondcheck();
        }
        if(badge.contains(",")){
            String []userids=badge.split(",");
            for(int i=0;i<userids.length;i++){
                String userid=userids[i];
                PpmKpiRole kpiRole=getPpmKpiRoleByBadge(userid);
                if(!"".equals(role)){
                    kpiRole.setRole(role);
                }
                if(!"".equals(depid)){
                    kpiRole.setDepid(depid);
                }
                if(!"".equals(centerid)){
                    kpiRole.setCenterid(centerid);
                }
                if(!"".equals(firstcheck)){
                    kpiRole.setFirstcheck(firstcheck);
                }
                if(!"".equals(secondcheck)){
                    kpiRole.setSecondcheck(secondcheck);
                }
                myBatisDao.executionPojo(PPMKPIROLE, kpiRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
            }
        }
        return 1;
    }

    @Override
    public int deletePpmKpiRoleList(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmKpiRoleDto ppmKpiRoleDto = new PpmKpiRoleDto();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmKpiRoleDto.setId(Long.parseLong(idList[i]));
            this.deletePpmKpiRole(ppmKpiRoleDto);
        }
        return 1;
    }

    @Override
    public int updatePpmKpiRoleDto(PpmKpiRoleDto ppmKpiRoleDto,String userid) {
        PpmKpiRole ppmKpiRole = JsonUtil.jsonToClass(ppmKpiRoleDto, PpmKpiRole.class);
        ppmKpiRole.setLastmodtime(new Date());
        ppmKpiRole.setLastmoduserid(userid);
        return myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int updatePpmKpiRoleDtos(PpmKpiRoleDto ppmKpiRoleDtos, String userid) {
        int retCode=0;
        String ids=ppmKpiRoleDtos.getIds();
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        for (int i = idList.length - 1; i >= 0; i--) {
            String id=idList[i];
            PpmKpiRole dto=this.getPpmKpiRoleById(Long.parseLong(id));
            if(StringUtil.isNotBlank(ppmKpiRoleDtos.getDepid())){
                dto.setDepid(ppmKpiRoleDtos.getDepid());
            }
            if(StringUtil.isNotBlank(ppmKpiRoleDtos.getCenterid())){
                dto.setCenterid(ppmKpiRoleDtos.getCenterid());
            }
            if(StringUtil.isNotBlank(ppmKpiRoleDtos.getRole())){
                dto.setRole(ppmKpiRoleDtos.getRole());
            }

            if(StringUtil.isNotBlank(ppmKpiRoleDtos.getFirstcheck())&&!"".equals(ppmKpiRoleDtos.getFirstcheck())){
                dto.setFirstcheck(ppmKpiRoleDtos.getFirstcheck());
            }
            if("".equals(ppmKpiRoleDtos.getFirstcheck())){
                dto.setFirstcheck("");
            }

            if(StringUtil.isNotBlank(ppmKpiRoleDtos.getSecondcheck())&&!"".equals(ppmKpiRoleDtos.getSecondcheck())){
                dto.setSecondcheck(ppmKpiRoleDtos.getSecondcheck());
            }
            if("".equals(ppmKpiRoleDtos.getSecondcheck())){
                dto.setSecondcheck("");
            }
            retCode= myBatisDao.executionPojo(PPMKPIROLE, dto, IMyBatisDao.SQL.UPDATE,"updatePojo");

        }
        return retCode;
    }


    @Transactional("defaultTx")
    @Override
    public int updatePpmKpiPm(PpmKpiRoleDto ppmKpiRoleDto) {
        //同步给考核表权限数据
        int i= myBatisDao.executionPojo(PPMKPIROLE, ppmKpiRoleDto, IMyBatisDao.SQL.UPDATE,"updatePpmKpiPm");
        return i;
    }

    @Override
    public PpmKpiRoleInfoDto getPpmKpiRoleInfo(PpmKpiRoleInfoDto ppmKpiRoleInfoDto) {
        PpmKpiRoleInfo ppmKpiRoleInfo = JsonUtil.jsonToClass(
                ppmKpiRoleInfoDto, com.work.ppm.application.model.kpi.ext.PpmKpiRoleInfo.class);
        PpmKpiRoleInfo info = myBatisDao.selectClass(PPMKPIROLE,
                ppmKpiRoleInfo, "getPpmKpiRoleView");
        PpmKpiRoleInfoDto item = JsonUtil.jsonToClass(info, PpmKpiRoleInfoDto.class);
        return item;
    }

    @Override
    public int judgeStatus(JudgeKpiStatusDto dto ){
            PpmKpiRole firstRole=new PpmKpiRole();
            firstRole.setBadge(dto.getEid());
            firstRole.setFirstcheck(dto.getCheck());
            List<PpmKpiRole> fdtos=myBatisDao.selectListT(
                    PPMKPIROLE,firstRole,"getList");
            if(fdtos.size()>0){
                if("SP".equals(dto.getFlag())){
                    return 2;
                }else{
                    return 5;
                }
            }
            PpmKpiRole secondRole=new PpmKpiRole();
            secondRole.setBadge(dto.getEid());
            secondRole.setSecondcheck(dto.getCheck());
            List<PpmKpiRole> sdtos=myBatisDao.selectListT(PPMKPIROLE,secondRole,"getList");
            if(sdtos.size()>0){
                if("SP".equals(dto.getFlag())){
                    return 3;
                }else{
                    return 6;
                }
            }
        return 0;
    }

    @Override
    public List<PpmKpiRoleDto> getPpmKpiRoleListByDirector(PpmKpiRoleDto ppmKpiRoleDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiRoleDto,PpmKpiRoleDto.class);
        List<PpmKpiRole> datas = myBatisDao.selectListT(PPMKPIROLE, map, "getPpmKpiRoleListByDirector");
        List<PpmKpiRoleDto> item = JsonUtil.jsonToList(datas, PpmKpiRoleDto.class);
        return item;
    }

}
