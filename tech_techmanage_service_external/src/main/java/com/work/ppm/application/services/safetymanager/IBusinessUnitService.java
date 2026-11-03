package com.work.ppm.application.services.safetymanager;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.EmployeeDepartMsg;
import com.work.ppm.application.model.safetymanager.UpdateEmployeeInfo;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * packageName com.work.ppm.application.services.safetymanager
 *
 * @version JDK 8
 * @className IBusinessUnitService
 * @date 2025/1/3
 * @description TODO
 */
public interface IBusinessUnitService {
    /**
     * 分页查询
     * @param businessUnit
     * @param pager
     */
    void queryPager(BusinessUnit businessUnit, Pager pager);

    /**
     * @param businessUnit:
     * @return ApiResult
     * @date 2025/1/7
     * @version JDK 8
     * @description 修改部门安全专员清单内容
     */
    ApiResult updateMsg(BusinessUnit businessUnit);

    /**
     * @param businessUnit:
     * @return ApiResult
     * @date 2025/6/3
     * @version JDK 8
     * @description 修改业务部安全专员信息
     */
    ApiResult updateEmployeeMsg(BusinessUnit businessUnit);

}
