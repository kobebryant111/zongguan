package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.SafetyOfficerList;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.services.systemmanager.IAqzyDepartService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("AqzyDepartService")
public class AqzyDepartService implements IAqzyDepartService {
    /**
     * 实体类路径
     */
    private static String SAFETYOFFICERLIST = "com.work.ppm.application.model.safetymanager.SafetyOfficerList";
    /**
     * 实体类路径
     */
    private static String BUSINESSUNIT = "com.work.ppm.application.model.safetymanager.BusinessUnit";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 总部部门同步
     */
    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void headSafetyspecialist() {
        try {
            log.info("线程：" + Thread.currentThread().getName());
            final Map map = new HashMap<>();
            final List<SafetyOfficerList> safetyOfficerLists = myBatisDao.selectListT(SAFETYOFFICERLIST, map, "queryByNotXfjr");
            if (!CollectionUtils.isEmpty(safetyOfficerLists)) {
                for (SafetyOfficerList safetyOfficerList : safetyOfficerLists) {
                    safetyOfficerList.setLocation(safetyOfficerList.getLocation().substring(4));
                }
            }
            if (!CollectionUtils.isEmpty(safetyOfficerLists)) {
                final Integer upsert = myBatisDao.executionPojo(SAFETYOFFICERLIST, safetyOfficerLists, IMyBatisDao.SQL.INSTER, "upsert");
                log.info("更新插入条数：" + upsert);
                final Integer deletert = myBatisDao.executionPojo(SAFETYOFFICERLIST, safetyOfficerLists, IMyBatisDao.SQL.DELETE, "deletert");
                log.info("删除条数：" + deletert);
            }
        } catch (Exception e) {
            log.info("安全总部部门同步异常");
            throw new BizException(e);
        }

    }

    @Async
    @Override
    public void businessSafetyspecialist() {
        try {
            log.info("线程：" + Thread.currentThread().getName());
            final Map map = new HashMap<>();
            final List<BusinessUnit> businessUnits = myBatisDao.selectListT(BUSINESSUNIT, map, "queryByewub");
            for (BusinessUnit businessUnit : businessUnits) {
                final String substring = businessUnit.getLocation().substring(4);
                businessUnit.setLocation(substring);
                final String busine = businessUnit.getBusinessUnit();
                final int index = busine.lastIndexOf("-");
                businessUnit.setBusinessUnit(busine.substring(index+1));
                if (StringUtils.isEmpty(businessUnit.getAreaManager())) {
                    businessUnit.setAreaManager(businessUnit.getFirstLevelManager());
                    businessUnit.setBusinessBadge(businessUnit.getSafetyBadge());
                }
            }
            if (!CollectionUtils.isEmpty(businessUnits)) {
                Integer upsert = myBatisDao.executionPojo(BUSINESSUNIT, businessUnits, IMyBatisDao.SQL.INSTER, "upsert");
                log.info("更新插入条数：" + upsert);
                final Integer deletert = myBatisDao.executionPojo(BUSINESSUNIT, businessUnits, IMyBatisDao.SQL.DELETE, "deletert");
                log.info("删除条数：" + deletert);
            }
        } catch (Exception e) {
            log.info("安全业务部门同步异常");
            throw new BizException(e);
        }
    }
}
