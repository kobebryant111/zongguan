package com.work.ppm.application.services.safetymanager.impl;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.SafetyOfficerList;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.quartz.safetymanager.ISafetyCommissionerEmployeeSyncService;
import com.work.ppm.commons.dao.IMyBatisDao;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName com.work.ppm.application.services.safetymanager.impl
 *
 * @version JDK 8
 * @className SyncEmployeeMsgService
 * @date 2025/6/5
 * @description 将人力资源信息同步到安全专员
 */
@Service("syncEmployeeMsgService")
@Slf4j
public class SyncEmployeeMsgService implements ISafetyCommissionerEmployeeSyncService {
    /**
     * 实体类路径
     */
    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";
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

    @Override
    public void syncEmployeeMsg() {
        Map<String, Object> map = new HashMap<>();
        // 1.查询综管人员信息
        List<CommEmployee> empList = myBatisDao.selectListT(COMMEMPLOYEE, map, "getEmployeeAndDepInfo");
        int i = 0;
        int j = 0;
        int k = 0;

        for (CommEmployee commEmployee : empList) {
            // 2.循环人员信息同步到总部安全专员
            Map<String, Object> safetyOfficerMap = new HashMap<>();
            safetyOfficerMap.put("badge", commEmployee.getBadge());
            safetyOfficerMap.put("safetySpecialist", commEmployee.getEmpname());
            safetyOfficerMap.put("phoneNum", commEmployee.getMobiletelphone());
            safetyOfficerMap.put("safetyStatus" , "N");
            // 查询总部安全专员信息, 判断是否有调岗的情况.
            List<SafetyOfficerList> safetyOfficerLists = myBatisDao.selectListT(SAFETYOFFICERLIST, safetyOfficerMap, "querySafetyOfficerInfo");
            if (safetyOfficerLists != null && safetyOfficerLists.size() > 0) {
                SafetyOfficerList safetyOfficer = safetyOfficerLists.get(0);
                // 进行判断是否有调岗的情况 ,
                final Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("depcode", safetyOfficer.getDepcode());
                List depcodes = myBatisDao.selectListT(SAFETYOFFICERLIST, hashMap, "queryDepcode");
                if (!depcodes.contains(commEmployee.getDepcode()) || "Loff".equals(commEmployee.getEmpstatus())) {
                    safetyOfficerMap.put("safetyStatus" , "Y");
                }
                Integer ii = myBatisDao.executionPojo(SAFETYOFFICERLIST, safetyOfficerMap, IMyBatisDao.SQL.UPDATE, "updateSafetyOfficer");
                if (ii > 0) {
                    i += ii;
                }
            }

            // 3.循环人员信息同步到业务部安全专员
            // 3.1 修改以一级部安全专员信息
            Map<String, Object> businessUnitMap = new HashMap<>();
            businessUnitMap.put("oldBadge", commEmployee.getBadge());
            businessUnitMap.put("badge", commEmployee.getBadge());
            businessUnitMap.put("safetySpecialist", commEmployee.getEmpname());
            businessUnitMap.put("phoneNum", commEmployee.getMobiletelphone());
            businessUnitMap.put("safetyStatus" , "N");
            // 查询业务部一级部门安全专员信息, 判断是否有调岗的情况.
            List<BusinessUnit> businessUnitList = myBatisDao.selectListT(BUSINESSUNIT, businessUnitMap, "queryBusinessunitInfo");
            if (businessUnitList != null && businessUnitList.size() > 0) {
                BusinessUnit businessUnit = businessUnitList.get(0);
                // 进行判断是否有调岗的情况 ,
                final Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("depcode", businessUnit.getDepcode());
                List depcodes = myBatisDao.selectListT(SAFETYOFFICERLIST, hashMap, "queryDepcode");
                if (!depcodes.contains(commEmployee.getDepcode()) || "Loff".equals(commEmployee.getEmpstatus())) {
                    businessUnitMap.put("safetyStatus" , "Y");
                }
                Integer jj = myBatisDao.executionPojo(BUSINESSUNIT, businessUnitMap, IMyBatisDao.SQL.UPDATE, "updateEmployeeMsg");
                if (jj > 0) {
                    j += jj;
                }
            }

            // 3.2 修改二级部安全专员信息
            Map<String, Object> areaBusinessUnitMap = new HashMap<>();
            areaBusinessUnitMap.put("oldAreaBadge", commEmployee.getBadge());
            areaBusinessUnitMap.put("areaBadge", commEmployee.getBadge());
            areaBusinessUnitMap.put("areaSafetySpecialist", commEmployee.getEmpname());
            areaBusinessUnitMap.put("areaPhoneNum", commEmployee.getMobiletelphone());
            areaBusinessUnitMap.put("areaSafetyStatus" , "N");
            // 查询业务部二级部门安全专员信息, 判断是否有调岗的情况.
            List<BusinessUnit> businessUnitAreaList = myBatisDao.selectListT(BUSINESSUNIT, areaBusinessUnitMap, "queryBusinessunitAreaInfo");
            if (businessUnitAreaList != null && businessUnitAreaList.size() > 0) {
                BusinessUnit businessUnitArea = businessUnitAreaList.get(0);
                // 进行判断是否有调岗的情况
                final Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("depcode", businessUnitArea.getBusinessUnitDepcode());
                List depcodes = myBatisDao.selectListT(SAFETYOFFICERLIST, hashMap, "queryDepcode");
                if (!depcodes.contains(commEmployee.getDepcode()) || "Loff".equals(commEmployee.getEmpstatus())) {
                    areaBusinessUnitMap.put("areaSafetyStatus" , "Y");
                }
                Integer kk = myBatisDao.executionPojo(BUSINESSUNIT, areaBusinessUnitMap, IMyBatisDao.SQL.UPDATE, "updateEmployeeMsg");
                if (kk > 0) {
                    k += kk;
                }
            }
        }
        log.info("安全专员总部更新" + i + "条数据, 业务部一级部门更新" + j + "条数据, 业务部二级部门更新" + k + "条数据。");
    }
}



















