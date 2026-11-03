package com.work.ppm.application.services.safetymanager.impl;

import com.alibaba.fastjson.JSON;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.EmployeeDepartMsg;
import com.work.ppm.application.model.safetymanager.SafetyOfficerList;
import com.work.ppm.application.model.safetymanager.UpdateEmployeeInfo;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.services.safetymanager.ISafetyOfficerListService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName com.work.ppm.application.services.safetymanager.impl
 *
 * @version JDK 8
 * @className safetyOfficerListService
 * @date 2025/1/2
 * @description TODO
 */
@Service("SafetyOfficerListService")
@Slf4j
public class SafetyOfficerListService implements ISafetyOfficerListService {
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 系统角色查询接口
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;
    /**
     * 实体类路径
     */
    private static String SAFETYOFFICERLIST = "com.work.ppm.application.model.safetymanager.SafetyOfficerList";
    /**
     * 实体类路径
     */
    private static String IMUSERROLEREL = "com.work.ppm.application.model.systemmanager.ImUserRoleRel";
    /**
     * 员工实体类路径
     */
    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";


    @Override
    public void queryPager(SafetyOfficerList safetyOfficerList, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(safetyOfficerList, SafetyOfficerList.class);
            myBatisDao.selectPager(SAFETYOFFICERLIST, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("总部安全专员信息查询异常,错误信息为:{}。", e);
        }
    }

    @Override
    public ApiResult updateMsg(SafetyOfficerList safetyOfficerList) {
        log.info("总部安全专员清单内容进行修改信息:{}", JSON.toJSONString(safetyOfficerList));
        ApiResult result = new ApiResult<>();
        if (safetyOfficerList == null) {
            result.setStatus(ApiCode.UPDATEMSGISNULL);
            return result;
        }
        try {
            myBatisDao.executionPojo(SAFETYOFFICERLIST, safetyOfficerList, IMyBatisDao.SQL.UPDATE, "updateMsg");
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("总部安全专员清单内容修改失败!失败信息为:{}", JSON.toJSONString(e.getMessage()));
            result.setStatus(ApiCode.UPATE_FAIL);
            return result;
        }
        return result;
    }

    @Override
    public List<ImUserRoleRel> queryRole(String badge) {
        Map<String, Object> map = new HashMap<>();
        map.put("urrRoleid", "SAFETY_SPECIALIST");
        map.put("urrUserid", badge);
        map.put("sysType", 1);
        return myBatisDao.selectListT(IMUSERROLEREL, map, "getList");
    }

    @Override
    public List<EmployeeDepartMsg> quertBadgeMsg() {
        Map<String, Object> map = new HashMap<>();
        List<EmployeeDepartMsg> list = myBatisDao.selectListT(SAFETYOFFICERLIST, map, "quertBadgeMsg");
        return list;
    }

    @Override
    public void queryEmployeePager(CommEmployeeDto commEmployee, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployee, CommEmployeeDto.class);
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryEmployeePagerList");
    }

    @Override
    public ApiResult updateEmployeeMsg(UpdateEmployeeInfo updateEmployeeInfo) {
        log.info("总部安全专员需修改的信息为:{}", JSON.toJSONString(updateEmployeeInfo));
        ApiResult result = new ApiResult<>();

        Map<String, Object> map = JsonUtil.objectTOMap(updateEmployeeInfo, UpdateEmployeeInfo.class);

        try {
            myBatisDao.executionPojo(SAFETYOFFICERLIST, map, IMyBatisDao.SQL.UPDATE, "updateEmployeeMsg");
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("总部安全专员信息修改失败!失败信息为:{}", JSON.toJSONString(e.getMessage()));
            result.setStatus(ApiCode.UPATE_FAIL);
            return result;
        }
        return result;
    }

}
