package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingChangeInfosService;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工信息变更详细
 */
@Service("ppmOutsourcingChangeInfosService")
public class PpmOutsourcingChangeInfosService extends BaseDaoSupper implements IPpmOutsourcingChangeInfosService {
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGCHANGEINFOS = "" +
            "com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos, Pager pager) {
        Map<String,Object> map = JsonUtil.objectTOMap(ppmOutsourcingChangeInfos,PpmOutsourcingChangeInfos.class);
        myBatisDao.selectPager(PPMOUTSOURCINGCHANGEINFOS, map,pager,"queryPagerList");
    }

    @Override
    public int addPpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {
        Long id = getNextSequence(PpmOutsourcingChangeInfos.class);
        // 如果需要请set 主键id
        //ppmOutsourcingChangeInfos.setId(id.intValue());
        myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEINFOS, ppmOutsourcingChangeInfos, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmOutsourcingChangeInfos queryDetail(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {
        return myBatisDao.selectClass(PPMOUTSOURCINGCHANGEINFOS, ppmOutsourcingChangeInfos,"queryDetail");
    }

    @Override
    public Integer updatePpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {

        return myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEINFOS, ppmOutsourcingChangeInfos, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) {
        return myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEINFOS, ppmOutsourcingChangeInfos, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public int addPpmOutsourcingChangeInfos(List<PpmOutsourcingChangeInfos> eeList, String instanceid) {
        if(null == eeList || eeList.isEmpty()) {
            return -1;
        }
        eeList.stream().forEach(item -> {
            if(null == item.getId()) {
                Long id = getNextSequence(PpmOutsourcingChangeInfos.class);
                // 如果需要请set 主键id
                item.setId(id);
                item.setInstanceid(instanceid);
                myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEINFOS, item, IMyBatisDao.SQL.INSTER, "addPojo");
            } else {
                double total=0L;
                double professionscore=0L;
                double comprehensivescore=0L;
                boolean flag=true;
                if(null!=item.getProfessionscore()){//专业
                    professionscore=item.getProfessionscore()*0.3;
                }
                if(null!=item.getComprehensivescore()){//综合
                    comprehensivescore=item.getComprehensivescore()*0.7;
                }
                if(null!=item.getValuescore()){//价值观小于60不通过
                    if(item.getValuescore()<60){
                        flag=false;
                    }
                }
                if(null!=item.getImagescore()){//形象分小于60不通过
                    if(item.getImagescore()<60){
                        flag=false;
                    }
                }
                total=professionscore+comprehensivescore;
                if(total<60){
                    flag=false;
                }
                item.setResult(flag?"1":"0");
                item.setTotalscore(total);
                this.updatePpmOutsourcingChangeInfos(item);
            }
        });
        return 1;
    }
    @Override
    public List<PpmOutsourcingChangeInfos> queryLists(String instanceid) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("instanceid",instanceid);
        return myBatisDao.selectListT(PPMOUTSOURCINGCHANGEINFOS, map,"queryPagerList");
    }

    @Override
    public int deleteByInstanceid(PpmOutsourcingChangeInfos info) {
        int i=myBatisDao.executionPojo(PPMOUTSOURCINGCHANGEINFOS, info, IMyBatisDao.SQL.DELETE, "deleteByInstanceid");
        return i;
    }

}
