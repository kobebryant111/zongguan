package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.systemmanager.EosDictType;
import com.work.ppm.application.services.systemmanager.IEosDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 业务字典主表
 */
@Service("eosDictTypeService")
public class EosDictTypeService implements IEosDictTypeService {
    /**
     * 实体类路径
     */
    private static String EOSDICTTYPE = "com.work.ppm.application.model.systemmanager.EosDictType";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void queryPager(EosDictType eosDictType, Pager pager) {
        Map<String,Object> map = StringUtil.objectTOMap(eosDictType,EosDictType.class);
        myBatisDao.selectPager(EOSDICTTYPE, map,pager,"queryPagerList");
    }

    @Override
    public EosDictType queryDetail(EosDictType eosDictType) {
        return myBatisDao.selectClass(EOSDICTTYPE, eosDictType,"queryDetail");
    }

    @Override
    public Integer updateEosDictType(EosDictType eosDictType) {
        return myBatisDao.executionPojo(EOSDICTTYPE, eosDictType, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteEosDictType(EosDictType eosDictType) {
        return myBatisDao.executionPojo(EOSDICTTYPE, eosDictType, IMyBatisDao.SQL.DELETE,"deleteById");
    }
}
