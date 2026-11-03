package com.work.ppm.application.services.testmanager.impl;

import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.services.testmanager.ITestTeamManagementService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName com.work.ppm.application.services.testmanager.impl
 *
 * @version JDK 8
 * @className TestEmployeePoolService
 * @date 2024/8/29
 * @description 测试组管理
 */
@Service("testTeamManagementService")
@Slf4j
public class TestTeamManagementService implements ITestTeamManagementService {
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 实体类路径
     */
    private static String TESTGROUP = "com.work.ppm.application.model.testmanager.TestGroup";
    /**
     * 实体类路径
     */
    private static String TESTEMPLOYEEPOOL = "com.work.ppm.application.model.testmanager.TestEmployeePool";

    /**
     * @param testGroupDto: 
     * @return ApiResult
     * @date 2024/8/29
     * @version JDK 8
     * @description 创建小组
     */
    @Override
    public ApiResult creatTestGroup(TestGroupDto testGroupDto) {
        ApiResult result = new ApiResult<>();
        testGroupDto.setId(UUIDUtils.generateUUID());
        try {
            //将小组成员添加进池中信息.
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            String testGroupLeader = StringUtil.transStr(testGroupDto.getTestGroupLeader());
            String testGroupMember = StringUtil.transStr(testGroupDto.getTestGroupMember());

            String member = testGroupLeader + "," + testGroupMember ;
            testGroupDto.setTestGroupMember(member.replaceAll("'" , ""));
            myBatisDao.executionPojo(TESTGROUP, testGroupDto, IMyBatisDao.SQL.INSTER, "creatTestGroup");

            sb.append(testGroupLeader).append(",");
            sb.append(testGroupMember).append(")");

            Map map = new HashMap<>();
            map.put("ownedTestGroup", testGroupDto.getId());
            map.put("testEmpBadge", sb.toString());
            map.put("headOfCompany", testGroupDto.getTestGroupComemployee());
            myBatisDao.executionPojo(TESTEMPLOYEEPOOL, map, IMyBatisDao.SQL.UPDATE, "updateTestGroup");
            //修改测试人员属性
            TestEmployeePool testEmployeePool = new TestEmployeePool();
            testEmployeePool.setTestEmpBadge(StringUtil.strToSql(testGroupMember));
            testEmployeePool.setEmpAttribute("0");
            myBatisDao.executionPojo(TESTEMPLOYEEPOOL, testEmployeePool, IMyBatisDao.SQL.UPDATE, "updateEmpAttState");
            testEmployeePool.setTestEmpBadge(StringUtil.strToSql(testGroupLeader));
            testEmployeePool.setEmpAttribute("1");
            myBatisDao.executionPojo(TESTEMPLOYEEPOOL, testEmployeePool, IMyBatisDao.SQL.UPDATE, "updateEmpAttState");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加小组异常,异常信息为:{},出现异常的数据为:{}", e, testGroupDto);
            result.setMessage("添加小组异常，请联系管理员查看。");
            result.setStatus(ApiCode.FAIL);
        }
        result.setMessage("小组信息添加成功!");
        result.setStatus(ApiCode.SUCCESS);
        return result;
    }

    /**
     * @param teamId: 团队ID
     * @return List < TestEmployeePoolDto>
     * @date 2024/8/29
     * @version JDK 8
     * @description 查询测试人员信息列表
     */
    @Override
    public List<TestEmployeePoolDto> selectTestGroupEmployee(String teamId) {
        Map map = new HashMap<>();
        map.put("ownedTestTeam" , teamId);
        List<TestEmployeePoolDto> selectTestGroupEmployeeList = myBatisDao.selectListT(TESTEMPLOYEEPOOL, map, "queryUndistributionPagerList");
        return selectTestGroupEmployeeList;
    }

    /**
     * @param testGroupName: 小组名
     * @return List < TestGroup>
     * @date 2024/8/29
     * @version JDK 8
     * @description 查询小组名称
     */
    @Override
    public List<TestGroup> selectGroupName(String testGroupName) {
        Map map = new HashMap<>();
        map.put("testGroupName" , testGroupName);
        List<TestGroup> list = myBatisDao.selectListT(TESTGROUP, map, "queryTestGroupName");
        return list;
    }
}
