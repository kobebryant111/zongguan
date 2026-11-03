package com.work.ppm.application.services.safetymanager.impl;

import com.alibaba.fastjson.JSON;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.EmployeeDepartMsg;
import com.work.ppm.application.model.safetymanager.SafetyOfficerList;
import com.work.ppm.application.model.safetymanager.UpdateEmployeeInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.safetymanager.IBusinessUnitService;
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
 * @className BusinessUnitService
 * @date 2025/1/3
 * @description TODO
 */
@Service("businessUnitService")
@Slf4j
public class BusinessUnitService implements IBusinessUnitService {
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 实体类路径
     */
    private static String BUSINESSUNIT = "com.work.ppm.application.model.safetymanager.BusinessUnit";

    @Override
    public void queryPager(BusinessUnit businessUnit, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(businessUnit, BusinessUnit.class);
            myBatisDao.selectPager(BUSINESSUNIT, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("业务部门安全专员信息查询异常,错误信息为:{}。", e);
        }
    }

    @Override
    public ApiResult updateMsg(BusinessUnit businessUnit) {
        log.info("部门安全专员清单内容进行修改信息:{}", JSON.toJSONString(businessUnit));
        ApiResult result = new ApiResult<>();
        if (businessUnit == null) {
            result.setStatus(ApiCode.UPDATEMSGISNULL);
            return result;
        }
        try {
            // 修改公共数据
            myBatisDao.executionPojo(BUSINESSUNIT, businessUnit, IMyBatisDao.SQL.UPDATE, "updateAll");
            // 修改单条数据
            myBatisDao.executionPojo(BUSINESSUNIT, businessUnit, IMyBatisDao.SQL.UPDATE, "updateMsg");
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("部门安全专员清单内容修改失败!失败信息为:{}", JSON.toJSONString(e.getMessage()));
            result.setStatus(ApiCode.UPATE_FAIL);
            return result;
        }
        return result;
    }

    @Override
    public ApiResult updateEmployeeMsg(BusinessUnit businessUnit) {
        log.info("总部安全专员需修改的信息为:{}", JSON.toJSONString(businessUnit));
        ApiResult result = new ApiResult<>();

        Map<String, Object> map = JsonUtil.objectTOMap(businessUnit, BusinessUnit.class);

        try {
            myBatisDao.executionPojo(BUSINESSUNIT, map, IMyBatisDao.SQL.UPDATE, "updateEmployeeMsg");
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
