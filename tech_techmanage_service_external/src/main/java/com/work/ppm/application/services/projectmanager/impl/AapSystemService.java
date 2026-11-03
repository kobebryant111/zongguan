package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.AapSystemDto;
import com.work.ppm.application.model.projectmanager.ext.AapSystemInfo;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.projectmanager.AapSystem;
import com.work.ppm.application.services.projectmanager.IAapSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 系统管理
 */
@Service("aapSystemService")
public class AapSystemService extends BaseDaoSupper implements IAapSystemService {
    /**
     * 实体类路径
     */
    private static String AAPSYSTEM = "com.work.ppm.application.model.projectmanager.AapSystem";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(AapSystem aapSystem, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(aapSystem,AapSystem.class);
        myBatisDao.selectPager(AAPSYSTEM, map,pager,"queryPagerList");
    }

    @Override
    public int addAapSystem(AapSystem aapSystem) {
        //Long key = this.getNextSequence(AapSystem.class);
        // 如果需要请set 主键id
        //aapSystem.setId(key);
        myBatisDao.executionPojo(AAPSYSTEM, aapSystem, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public AapSystemDto queryDetail(AapSystemDto aapSystemDto) {
        AapSystem aapSystem = JsonUtil.jsonToClass(aapSystemDto, AapSystem.class);
        AapSystem item = myBatisDao.selectClass(AAPSYSTEM, aapSystem,"queryDetail");
        AapSystemDto dto = JsonUtil.jsonToClass(item,AapSystemDto.class);
        return dto;
    }

    @Override
    public Integer updateAapSystem(AapSystem aapSystem) {
        return myBatisDao.executionPojo(AAPSYSTEM, aapSystem, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteAapSystem(AapSystem aapSystem) {
        return myBatisDao.executionPojo(AAPSYSTEM, aapSystem, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public List<CommTreeDto> querySystemTree(String objectCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("objectCode", objectCode);
        List<CommTree> trees = myBatisDao.selectListT(AAPSYSTEM, map, "querySystemTree");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(trees, CommTreeDto.class);
        List<CommTreeDto> treeDtoList = CommTreeDto.recursiveListToTree(treeDtos);
        return treeDtoList;
    }

    @Override
    public AapSystemInfo queryPasBySystems(String systemids) {
        Map param=new HashMap();
        String[] s=null;
        String str="";
        if(systemids.contains(",")){
            s=systemids.split(",");
            for(int i=0;i<s.length;i++){
                if(i==0){
                    str="'"+s[i]+"'";
                }else{
                str=str+","+"'"+s[i]+"'";
            }
            }
        }else{
            str="'"+systemids+"'";
        }
        param.put("systemid", str);
        AapSystemInfo systeminfo=myBatisDao.selectClass(AAPSYSTEM, param,"queryPasBySystems");
        return systeminfo;
    }

    @Override
    public AapSystemDto queryDetail(String systemName) {
        AapSystemDto system = new AapSystemDto();
        system.setSysName(systemName);
        return this.queryDetail(system);
    }

    @Override
    public String getFZRSysno(String userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        List<AapSystem> list = myBatisDao.selectListT(AAPSYSTEM,map, "getFZRSysno");
        if(null != list && !list.isEmpty()) {
            StringJoiner joiner = new StringJoiner(",");
            list.stream().forEach(item -> {
                joiner.add(item.getSystemId());
            });
            return joiner.toString();
        }
        return null;
    }

    @Override
    public List<AapSystemDto> queryDetailsForSysId(String sysId) {
        AapSystem system = new AapSystem();
        system.setSystemId(sysId);
        List<AapSystem> list = myBatisDao.selectListT(AAPSYSTEM, system, "getList");
        List<AapSystemDto> dtos = JsonUtil.jsonToList(list, AapSystemDto.class);
        return dtos;
    }

    @Override
    public AapSystemDto queryPlansystem(String sysno) {
        Map<String,Object> map = new HashMap<>();
        map.put("sysno", sysno);
        AapSystemInfo aapSystemInfo = myBatisDao.selectClass(AAPSYSTEM, map, "queryPlansystem");
        return JsonUtil.jsonToClass(aapSystemInfo, AapSystemDto.class);
    }

    @Override
    public AapSystemDto querySystemIsZH(String sysno) {
        Map<String,Object> map = new HashMap<>();
        map.put("sysno", sysno);
        AapSystem aapSystem = myBatisDao.selectClass(AAPSYSTEM, map, "querySystemIsZH");
        return JsonUtil.jsonToClass(aapSystem, AapSystemDto.class);
    }
}
