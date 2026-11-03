package com.work.ppm.application.services.testmanager;

import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.commons.ApiResult;

import java.util.List;

/**
 * 测试组管理
 */
public interface ITestTeamManagementService {
    /**
     * 创建测试小组
     * @param testGroupDto
     */
    ApiResult creatTestGroup(TestGroupDto testGroupDto);

    /**
     * 查询测试人员
     * @param testGroupId
     * @return
     */
    List<TestEmployeePoolDto> selectTestGroupEmployee(String testGroupId);

    /**
     * 查询当前小组名是否已经存在
     * @param testGroupName
     * @return
     */
    List<TestGroup> selectGroupName(String testGroupName);
}
