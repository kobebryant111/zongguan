package com.work.ppm.application.services.usermanager.impl;

import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.application.dto.usermanager.PpmDaTeamGroupDto;
import com.work.ppm.application.model.usermanager.PpmDaTeamGroup;
import com.work.ppm.application.services.usermanager.IPpmDaTeamGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 团队接口
 */
@Slf4j
@Service("ppmDaTeamGroupService")
public class PpmDaTeamGroupService implements IPpmDaTeamGroupService {
    /**
     * 实体类路径
     */
    private static String PPMDATEAMGROUP = "com.work.ppm.application.model.usermanager.PpmDaTeamGroup";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public int addPpmDaTeamGroup(PpmDaTeamGroup ppmDaTeamGroup) {
        myBatisDao.executionPojo(PPMDATEAMGROUP, ppmDaTeamGroup, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDaTeamGroup queryDetail(String id) {
        PpmDaTeamGroup ppmDaTeamGroup = new PpmDaTeamGroup();
        ppmDaTeamGroup.setId(id);
        return myBatisDao.selectClass(PPMDATEAMGROUP, ppmDaTeamGroup,"queryDetail");
    }

    @Override
    public Integer updatePpmDaTeamGroup(PpmDaTeamGroup ppmDaTeamGroup) {
        return myBatisDao.executionPojo(PPMDATEAMGROUP, ppmDaTeamGroup, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public List<PpmDaTeamGroupDto> queryGroup(String deptNo) {
        PpmDaTeamGroup group = new PpmDaTeamGroup();
        group.setDepid(deptNo);
        group.setIsdel("0");
        List<PpmDaTeamGroup> list = myBatisDao.selectListT(PPMDATEAMGROUP, group, "getList");
        List<PpmDaTeamGroupDto> dtos = JsonUtil.jsonToList(list,PpmDaTeamGroupDto.class);
        return dtos;
    }

    @Override
    public List<PpmDaTeamGroupDto> queryGroups(PpmDaTeamGroupDto ppmDaTeamGroup) {
        Map map= JsonUtil.jsonToClass(ppmDaTeamGroup,Map.class);
        List<PpmDaTeamGroup> list = myBatisDao.selectListT(PPMDATEAMGROUP, map, "getList");
        List<PpmDaTeamGroupDto> dtos = JsonUtil.jsonToList(list,PpmDaTeamGroupDto.class);
        return dtos;
    }

    @Override
    public String queryGroupManager(String userid) {
        Map<String,Object> map = new HashMap<>();
        map.put("userid",userid);
        log.info("userid:"+userid);
        List<PpmDaTeamGroup> items = myBatisDao.selectListT(PPMDATEAMGROUP, map, "queryGroupManager");
        if(items.size()>0){
            return items.get(0).getTeammanager();
        }else{
            return "";
        }

    }
}
