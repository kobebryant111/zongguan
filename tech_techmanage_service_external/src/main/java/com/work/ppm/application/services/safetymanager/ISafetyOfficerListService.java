package com.work.ppm.application.services.safetymanager;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.safetymanager.BusinessUnit;
import com.work.ppm.application.model.safetymanager.EmployeeDepartMsg;
import com.work.ppm.application.model.safetymanager.SafetyOfficerList;
import com.work.ppm.application.model.safetymanager.UpdateEmployeeInfo;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * packageName com.work.ppm.application.services.safetymanager
 *
 * @version JDK 8
 * @className safetyOfficerListService
 * @date 2025/1/2
 * @description TODO
 */
public interface ISafetyOfficerListService {
    /**
     * 分页查询
     * @param safetyOfficerList
     * @param pager
     */
    void queryPager(SafetyOfficerList safetyOfficerList, Pager pager);

    /**
     * @param safetyOfficerList :
     * @return void
     * @date 2025/1/7
     * @version JDK 8
     * @description 修改为清单内容
     */
    ApiResult updateMsg(SafetyOfficerList safetyOfficerList);

    /**
     * @param badge: 
     * @return ImUserRoleRel
     * @date 2025/1/7
     * @version JDK 8
     * @description TODO
     */
    List<ImUserRoleRel> queryRole(String badge);

    /**
     * 查询工号等相关基本信息
     *
     * @param :
     * @return List < BusinessUnit>
     * @date 2025/5/27
     * @version JDK 8
     * @description TODO
     */
    List<EmployeeDepartMsg> quertBadgeMsg();

    /**
     * 分页
     * @param commEmployee 对象
     * @param pager 分页对象
     */
    void queryEmployeePager(CommEmployeeDto commEmployee, Pager pager) ;

    /**
     * @param updateEmployeeInfo:
     * @return ApiResult
     * @date 2025/5/30
     * @version JDK 8
     * @description 修改安全专员信息
     */
    ApiResult updateEmployeeMsg(UpdateEmployeeInfo updateEmployeeInfo);

}
