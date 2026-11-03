package com.work.ppm.application.services.testmanager;

import com.work.ppm.application.dto.testmanagement.PpmTestTeamDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolExportDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 测试人员池
 */
public interface ITestEmployeePoolService {
    /**
     * 分页查询
     * @param testEmployeePoolDto
     * @param pager
     */
    void queryPager(TestEmployeePoolDto testEmployeePoolDto, Pager pager);

    /**
     * 获取所有的公司内负责人
     * @return
     */
    List<CommEmployee> getHeadOfCompanyList();

    /**
     * 测试人员池数据导出
     * @param testEmployeePoolDto
     * @return
     */
    List<TestEmployeePoolExportDto> exportTestEmpPoolExcel(TestEmployeePoolDto testEmployeePoolDto);

    /**
     * 获取所属公司信息
     * @return
     */
    List<PpmOutsourcingInfo> getAffilCompanyList();

    /**
     * 获取所属测试团队
     * @return
     */
    List<PpmTestTeamDto> getOwnedTestTeamList();

    /**
     * 获取所属测试小组
     * @return
     */
    List<TestGroupDto> getOwnedTestGroupList();

    /**
     * 获取所属测试人员信息
     * @return
     */
    List<TestEmployeePoolDto> getTestGroupEmpList();

    /**
     * 测试人员出池操作
     * @param testEmployeePoolDto
     * @return
     */
    ApiResult dialogAddSubmit(TestEmployeePoolDto testEmployeePoolDto);

    /**
     * @param teamId:
     * @return List < TestGroup>
     * @date 2024/9/13
     * @version JDK 8
     * @description 查询测试团队下所有的小组信息
     */
    List<TestGroup> selectTestGroupInfoMsg(String teamId);
}
