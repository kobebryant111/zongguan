package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.PpmFhkjDeptRecord;
import com.work.ppm.application.services.systemmanager.IPpmFhkjDeptRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
/**
 * 分行科技配置管理
 */
@Service("ppmFhkjDeptRecordService")
public class PpmFhkjDeptRecordService implements IPpmFhkjDeptRecordService {
    /**
     * 实体类路径
     */
    private static String PPMFHKJDEPTRECORD = "com.work.ppm.application.model.systemmanager.PpmFhkjDeptRecord";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmFhkjDeptRecord ppmFhkjDeptRecord, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(ppmFhkjDeptRecord,PpmFhkjDeptRecord.class);
        myBatisDao.selectPager(PPMFHKJDEPTRECORD, map,pager,"queryPagerList");
    }

    @Override
    public PpmFhkjDeptRecord queryDetail(PpmFhkjDeptRecord ppmFhkjDeptRecord) {
        return myBatisDao.selectClass(PPMFHKJDEPTRECORD, ppmFhkjDeptRecord,"queryDetail");
    }

    @Override
    public Integer updatePpmFhkjDeptRecord(PpmFhkjDeptRecord ppmFhkjDeptRecord) {

        if(StringUtil.isStringNotEmpty(ppmFhkjDeptRecord.getId())){
             myBatisDao.executionPojo(PPMFHKJDEPTRECORD, ppmFhkjDeptRecord, IMyBatisDao.SQL.UPDATE,"updatePojo");
        }else{
            String uuid = UUID.randomUUID().toString();
            ppmFhkjDeptRecord.setId(uuid);
            myBatisDao.executionPojo(PPMFHKJDEPTRECORD, ppmFhkjDeptRecord, IMyBatisDao.SQL.INSTER,"addPojo");
        }
        return 1;
    }

    @Override
    public Integer deletePpmFhkjDeptRecord(PpmFhkjDeptRecord ppmFhkjDeptRecord) {
        String id = ppmFhkjDeptRecord.getId();
        if(id.indexOf(",")>-1){
            String[] str = id.split(",");
            for(int i=0;i<str.length;i++){
                if(StringUtil.isStringNotEmpty(str[i])){
                    ppmFhkjDeptRecord.setId(str[i]);
                    myBatisDao.executionPojo(PPMFHKJDEPTRECORD, ppmFhkjDeptRecord, IMyBatisDao.SQL.DELETE,"deleteById");
                }
            }
        }else{
            myBatisDao.executionPojo(PPMFHKJDEPTRECORD, ppmFhkjDeptRecord, IMyBatisDao.SQL.DELETE,"deleteById");
        }
        return 1;
    }
}
