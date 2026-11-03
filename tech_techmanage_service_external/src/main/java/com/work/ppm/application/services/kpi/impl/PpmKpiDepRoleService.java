package com.work.ppm.application.services.kpi.impl;

import com.work.ppm.application.dto.kpi.PpmKpiDepRoleInfoDto;
import com.work.ppm.application.model.kpi.ext.PpmKpiDepRoleInfo;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.kpi.IPpmKpiRoleService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.systemmanager.IPpmDaCenterService;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.kpi.PpmKpiDepRole;
import com.work.ppm.application.dto.kpi.PpmKpiDepRoleDto;
import com.work.ppm.application.services.kpi.IPpmKpiDepRoleService;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
  * 描述
  */
@Service("ppmKpiDepRoleService")
public class PpmKpiDepRoleService extends BaseDaoSupper implements IPpmKpiDepRoleService {
    /**
      * 实体类路径
      */
    private static String PPMKPIDEPROLE = "com.work.ppm.application.model.kpi.PpmKpiDepRole";
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
     *  人员角色
     */
    @Autowired
    private IPpmKpiRoleService ppmKpiRoleService;

    @Override
    public void queryPager(PpmKpiDepRoleDto ppmKpiDepRoleDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiDepRoleDto,PpmKpiDepRoleDto.class);
        myBatisDao.selectPager(PPMKPIDEPROLE, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        Long id = getNextSequence(PpmKpiDepRole.class);

        // 如果需要请set 主键id
        ppmKpiDepRole.setId(id);
        myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmKpiDepRoleDto queryDetail(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        PpmKpiDepRole data = myBatisDao.selectClass(PPMKPIDEPROLE, ppmKpiDepRole,
        "queryDetail");
        PpmKpiDepRoleDto item = JsonUtil.jsonToClass(data, PpmKpiDepRoleDto
        .class);
        return item;
    }

    @Override
    public int updatePpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        return myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public int deletePpmKpiDepRole(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        return myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    /**
     * 保存数据
     * @param ppmKpiDepRole
     * @param userid
     */
    private void insertPpmKpiDepRole(PpmKpiDepRole ppmKpiDepRole,String userid){
        ppmKpiDepRole.setLastmodtime(new Date());
        ppmKpiDepRole.setLastmoduserid(userid);
        Long id = getNextSequence(PpmKpiDepRole.class);
        // 如果需要请set 主键id
        ppmKpiDepRole.setId(id);
        myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    /**
     * 更新数据
     * @param ppmKpiDepRole
     */
    private void updatePpmKpiDepRole(PpmKpiDepRole ppmKpiDepRole,String userid) {
        ppmKpiDepRole.setLastmodtime(new Date());
        ppmKpiDepRole.setLastmoduserid(userid);
         myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }


    @Override
    @Transactional("defaultTx")
    public int insertPpmKpiDepRoles(PpmKpiDepRoleDto ppmKpiDepRoleDto,String userid) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        //先删除表中数据
        myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.DELETE, "deleteAll");
        //在进行插入操作
        List<PpmKpiDepRole> dtos = myBatisDao.selectListT(PPMKPIDEPROLE, ppmKpiDepRole,"getDepCenList");
        for(int i=0;i<dtos.size();i++){
            PpmKpiDepRole item =dtos.get(i);
            insertPpmKpiDepRole(item,userid);
        }
        return 0;
    }

    @Override
    public List<PpmKpiDepRoleInfoDto> getPpmKpiDepRoleView(PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiDepRoleInfoDto,PpmKpiDepRoleInfoDto.class);
        if(StringUtil.isNotBlank(ppmKpiDepRoleInfoDto.getDepid())){
            String depids="";
            String [] depArr=ppmKpiDepRoleInfoDto.getDepid().split(",");
            for (int i=0;i<depArr.length;i++){
                if(i==0){
                    depids="'"+depArr[i]+"'";
                }else{
                    depids=depids+",'"+depArr[i]+"'";
                }
            }
            map.remove("depid");
            map.put("depids", depids);
        }

        if(StringUtil.isNotBlank(ppmKpiDepRoleInfoDto.getCenterid())){
            String centerids="";
            String [] centeridArr=ppmKpiDepRoleInfoDto.getCenterid().split(",");
            for (int i=0;i<centeridArr.length;i++){
                if(i==0){
                    centerids="'"+centeridArr[i]+"'";
                }else{
                    centerids=centerids+",'"+centeridArr[i]+"'";
                }
            }
            map.remove("centerid");
            map.put("centerids", centerids);
        }
        List<PpmKpiDepRoleInfo> datas = myBatisDao.selectListT(PPMKPIDEPROLE, map, "getKpiDepRoleList");
        List<PpmKpiDepRoleInfoDto> item = JsonUtil.jsonToList(datas, PpmKpiDepRoleInfoDto.class);
        return item;
    }

    @Override
    public void queryPagerList(PpmKpiDepRoleInfoDto ppmKpiDepRoleInfoDto, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmKpiDepRoleInfoDto,PpmKpiDepRoleInfoDto.class);
        if(StringUtil.isNotBlank(ppmKpiDepRoleInfoDto.getDepid())){
            String depids="";
            String [] depArr=ppmKpiDepRoleInfoDto.getDepid().split(",");
            for (int i=0;i<depArr.length;i++){
               if(i==0){
                   depids="'"+depArr[i]+"'";
               }else{
                   depids=depids+",'"+depArr[i]+"'";
               }
            }
            map.remove("depid");
            map.put("depids", depids);
        }

        if(StringUtil.isNotBlank(ppmKpiDepRoleInfoDto.getCenterid())){
            String [] centeridArr=ppmKpiDepRoleInfoDto.getCenterid().split(",");
            String centerids="";
            for (int i=0;i<centeridArr.length;i++){
                if(i==0){
                    centerids="'"+centeridArr[i]+"'";
                }else{
                    centerids=centerids+",'"+centeridArr[i]+"'";
                }
            }
            map.remove("centerid");
            map.put("centerids", centerids);
        }
        if("depid".equals(pager.getSortField())){
            pager.setSortField("r.depid");
        }else if("centername".equals(pager.getSortField())){
            pager.setSortField("r.centerid");
        }else if("depname".equals(pager.getSortField())){
            pager.setSortField("r.depid");
        }else if("director".equals(pager.getSortField())){
            pager.setSortField("r.director");
        }else if("depcharge".equals(pager.getSortField())){
            pager.setSortField("r.depcharge");
        }else if("centercharge".equals(pager.getSortField())){
            pager.setSortField("r.centercharge");
        }else {
            pager.setSortField("r.depid");
        }
        myBatisDao.selectPager(PPMKPIDEPROLE, map,pager,"getKpiDepRoleList");
    }


    @Override
    @Transactional("defaultTx")
    public int deletePpmKpiDepRoleList(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmKpiDepRole ppmKpiDepRole = new PpmKpiDepRole();
        for (int i = idList.length - 1; i >= 0; i--) {
            ppmKpiDepRole.setId(Long.parseLong(idList[i]));
            myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.DELETE,"deleteById");
        }
        return 1;
    }

    /**
     *  根据id获得数据
     * @param id
     * @return ppmKpiDepRole
     */
    private PpmKpiDepRole getPpmKpiDepRoleById(Long id){
        PpmKpiDepRole ppmKpiDepRole=new PpmKpiDepRole();
        ppmKpiDepRole.setId(id);
        PpmKpiDepRole data = myBatisDao.selectClass(PPMKPIDEPROLE, ppmKpiDepRole, "queryDetailById");
        return data;
    }
    @Override
    @Transactional("defaultTx")
    public boolean importExcel(MultipartFile file, String userid) {
        // 业务字典
        List<EosDictEntry> eos = eosDictEntryService.getTypeidForList("KHBMJSBZ");
        // excel导入数据
        List<PpmKpiDepRoleInfoDto> list = ExcelUtil.importExcel(file, PpmKpiDepRoleInfoDto.class);
        for (int i=0;i<list.size();i++){
            PpmKpiDepRoleInfoDto ii=list.get(i);
            Long id=ii.getId();
            PpmKpiDepRole role=getPpmKpiDepRoleById(id);
            //查询角色id
            String sign =eos.stream().filter(iii-> ii.getSignname().
                    equals(iii.getDictname())).findFirst().get().getDictid();
            //查询部门id
            String depid= ppmKpiRoleService.retDepid(ii.getDepname());
            //查询中心编号
            String centerid=ppmKpiRoleService.retCenterid(ii.getCentername());
            //部门主持工作经理
            String director =ii.getDirector()==null?"":ii.getDirector();
            //部门分管总经理
            String depcharge =ii.getDepcharge()==null?"":ii.getDepcharge();
            //中心分管总经理
            String centercharge =ii.getCentercharge()==null?"":ii.getCentercharge();
            if(sign!=null&&!"".equals(sign)){
                role.setSign(Long.parseLong(sign));
            }
            if(depid!=null&&!"".equals(depid)){
                role.setDepid(depid);
            }
            if(centerid!=null&&!"".equals(centerid)){
                role.setCenterid(centerid);
            }
            if(director!=null&&!"".equals(director)){
                role.setDirector(director);
            }
            if(depcharge!=null&&!"".equals(depcharge)){
                role.setDepcharge(depcharge);
            }
            if(centercharge!=null&&!"".equals(centercharge)){
                role.setCentercharge(centercharge);
            }
            updatePpmKpiDepRole(role,userid);
        }
        return true;
    }


    @Override
    public int updatePpmKpiDepRoleDtos(PpmKpiDepRoleDto ppmKpiDepRoleDtos, String userid) {
        String ids=ppmKpiDepRoleDtos.getIds();
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        for (int i = idList.length - 1; i >= 0; i--) {
            String id=idList[i];
            PpmKpiDepRole dto=this.getPpmKpiDepRoleById(Long.parseLong(id));
            if(StringUtil.isNotBlank(ppmKpiDepRoleDtos.getDepid())){
                dto.setDepid(ppmKpiDepRoleDtos.getDepid());
            }
            if(StringUtil.isNotBlank(ppmKpiDepRoleDtos.getCenterid())){
                dto.setCenterid(ppmKpiDepRoleDtos.getCenterid());
            }
            if(StringUtil.isNotBlank(ppmKpiDepRoleDtos.getDirector())){
                dto.setDirector(ppmKpiDepRoleDtos.getDirector());
            }

            if(StringUtil.isNotBlank(ppmKpiDepRoleDtos.getDepcharge())&&!"".equals(ppmKpiDepRoleDtos.getDepcharge())){
                dto.setDepcharge(ppmKpiDepRoleDtos.getDepcharge());
            }
            if("".equals(ppmKpiDepRoleDtos.getDepcharge())){
                dto.setDepcharge("");
            }

            if(StringUtil.isNotBlank(ppmKpiDepRoleDtos.getCentercharge())
                    &&!"".equals(ppmKpiDepRoleDtos.getCentercharge())){
                dto.setCentercharge(ppmKpiDepRoleDtos.getCentercharge());
            }
            myBatisDao.executionPojo(PPMKPIDEPROLE, dto, IMyBatisDao.SQL.UPDATE,"updatePojo");

        }
        return 1;
    }


    @Override
    public int updatePpmKpiDepRoleDto(PpmKpiDepRoleDto ppmKpiDepRoleDto,String userid) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        ppmKpiDepRole.setLastmodtime(new Date());
        ppmKpiDepRole.setLastmoduserid(userid);
        return myBatisDao.executionPojo(PPMKPIDEPROLE, ppmKpiDepRole, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }


    @Override
    public List<PpmKpiDepRoleDto> getPpmKpiDepRoleList(PpmKpiDepRoleDto ppmKpiDepRoleDto) {
        PpmKpiDepRole ppmKpiDepRole = JsonUtil.jsonToClass(ppmKpiDepRoleDto, PpmKpiDepRole.class);
        List<PpmKpiDepRole> datas = myBatisDao.selectListT(PPMKPIDEPROLE, ppmKpiDepRole, "getList");
        List<PpmKpiDepRoleDto> item = JsonUtil.jsonToList(datas, PpmKpiDepRoleDto.class);
        return item;
    }
}
