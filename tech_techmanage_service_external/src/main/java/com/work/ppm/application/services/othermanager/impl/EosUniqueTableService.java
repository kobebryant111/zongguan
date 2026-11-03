package com.work.ppm.application.services.othermanager.impl;

import com.work.ppm.application.model.othermanager.EosUniqueTable;
import com.work.ppm.application.services.othermanager.IEosUniqueTableService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 主键生成表
 */
@Service("eosUniqueTableService")
public class EosUniqueTableService implements IEosUniqueTableService {
    /**
     * 实体类路径
     */
    private static String EOSUNIQUETABLE = "com.work.ppm.application.model.othermanager.EosUniqueTable";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public int addEosUniqueTable(EosUniqueTable eosUniqueTable) {

        myBatisDao.executionPojo(EOSUNIQUETABLE, eosUniqueTable, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public EosUniqueTable queryDetail(EosUniqueTable eosUniqueTable) {
        return myBatisDao.selectClass(EOSUNIQUETABLE, eosUniqueTable, "queryDetail");
    }

    @Override
    public Integer updateEosUniqueTable(EosUniqueTable eosUniqueTable) {
        return myBatisDao.executionPojo(EOSUNIQUETABLE, eosUniqueTable, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public long getTableSequence(String tableName,String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName", StringUtil.clazzNameToTableName(tableName));
        map.put("fieldId", id);
        Long primaryId = myBatisDao.selectCount(EOSUNIQUETABLE, map, "getTableSequence");
        return null != primaryId ? primaryId : 0;
    }

    @Override
    public long getCheckedSequence(String tableName, String fieldId, String id) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName", StringUtil.clazzNameToTableName(tableName));
        map.put("fieldId", fieldId);
        map.put("id", id);
        Long primaryId = myBatisDao.selectCount(EOSUNIQUETABLE, map, "getCheckedSequence");
        return primaryId;
    }


}
