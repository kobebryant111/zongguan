package com.work.ppm.application.services.testmanager.impl;

import com.work.ppm.application.dto.testmanagement.PpmTestTeamDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolExportDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.services.testmanager.ITestEmployeePoolService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * packageName com.work.ppm.application.services.testmanager.impl
 *
 * @version JDK 8
 * @className TestEmployeePoolService
 * @date 2024/8/15
 * @description 测试人员池
 */
@Service("testEmployeePoolService")
@Slf4j
public class TestEmployeePoolService implements ITestEmployeePoolService {
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
     * 实体类路径
     */
    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";
    /**
     * 实体类路径
     */
    private static String PPMOUTSOURCINGINFO = "com.work.ppm.application.model.othermanager.PpmOutsourcingInfo";

    /**
     * @param testEmployeePoolDto: 对象
     * @param pager:               分页参数
     * @return void
     * @date 2024/8/15
     * @version JDK 8
     * @description 分页查询
     */
    @Override
    public void queryPager(TestEmployeePoolDto testEmployeePoolDto, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(testEmployeePoolDto, TestEmployeePoolDto.class);
            myBatisDao.selectPager(TESTEMPLOYEEPOOL, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("测试人员池信息查询异常,错误信息为:{}。", e);
        }
    }

    /**
     * @param :
     * @return List < CommEmployee>
     * @date 2024/8/15
     * @version JDK 8
     * @description 获取公司负责人
     */
    @Override
    public List<CommEmployee> getHeadOfCompanyList() {
        return myBatisDao.selectListT(COMMEMPLOYEE, null, "getHeadOfCompanyList");
    }

    /**
     * @param testEmployeePoolDto:
     * @return List < TestEmployeePoolExportDto>
     * @date 2024/8/15
     * @version JDK 8
     * @description 测试人员池导出
     */
    @Override
    public List<TestEmployeePoolExportDto> exportTestEmpPoolExcel(TestEmployeePoolDto testEmployeePoolDto) {
        List<TestEmployeePoolExportDto> repsJson = new ArrayList<>();
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(testEmployeePoolDto, TestEmployeePoolDto.class);
            List<TestEmployeePool> testEmployeePools = myBatisDao.selectListT(TESTEMPLOYEEPOOL, map, "queryPagerList");
            repsJson = JsonUtil.jsonToList(testEmployeePools, TestEmployeePoolExportDto.class);
        } catch (Exception e) {
            log.error("测试人员池信息导出异常,异常信息为:{}", e);
        }
        return repsJson;
    }

    /**
     * @param :
     * @return List < PpmOutsourcingInfo>
     * @date 2024/8/15
     * @version JDK 8
     * @description 获取测试人员所属公司
     */
    @Override
    public List<PpmOutsourcingInfo> getAffilCompanyList() {
        return myBatisDao.selectListT(PPMOUTSOURCINGINFO, null, "getAffilCompanyList");
    }

    /**
     * @param :
     * @return List < PpmTestTeamDto>
     * @date 2024/8/15
     * @version JDK 8
     * @description 获取所属团队列表
     */
    @Override
    public List<PpmTestTeamDto> getOwnedTestTeamList() {
        return myBatisDao.selectListT(TESTEMPLOYEEPOOL, null, "getOwnedTestTeamList");
    }

    /**
     * @param :
     * @return List < TestGroupDto>
     * @date 2024/8/15
     * @version JDK 8
     * @description 获取所属小组列表
     */
    @Override
    public List<TestGroupDto> getOwnedTestGroupList() {
        return myBatisDao.selectListT(TESTGROUP, null, "getOwnedTestGroupList");
    }

    /**
     * @param :
     * @return List < TestEmployeePoolDto>
     * @date 2024/8/15
     * @version JDK 8
     * @description 获取测试小组人员列表
     */
    @Override
    public List<TestEmployeePoolDto> getTestGroupEmpList() {
        return myBatisDao.selectListT(TESTEMPLOYEEPOOL, null, "getTestGroupEmpList");
    }

    /**
     * @param testEmployeePoolDto: 对象
     * @return ApiResult
     * @date 2024/8/15
     * @version JDK 8
     * @description 测试人员信息修改
     */
    @Override
    public ApiResult dialogAddSubmit(TestEmployeePoolDto testEmployeePoolDto) {
        ApiResult result = new ApiResult<>();
        try {
            testEmployeePoolDto.setUpdateTime(DateUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            myBatisDao.executionPojo(TESTEMPLOYEEPOOL, testEmployeePoolDto, IMyBatisDao.SQL.UPDATE, "dialogAddSubmit");
            //查询小组信息,
            Map map = new HashMap<>();
            map.put("id" , testEmployeePoolDto.getOwnedTestGroup());
            List<TestGroup> testGroupList = myBatisDao.selectListT(TESTGROUP, map, "queryTestGroupName");
            if (testGroupList.size() > 0) {
                TestGroup testGroup = testGroupList.get(0);
                String testGroupMember = testGroup.getTestGroupMember();
                if (!testGroupMember.contains(testEmployeePoolDto.getTestEmpBadge())) {
                    testGroupMember = testGroupMember + "," + testEmployeePoolDto.getTestEmpBadge();
                    Map updateGroupMap =  new HashMap<>();
                    updateGroupMap.put("id" , testEmployeePoolDto.getOwnedTestGroup());
                    updateGroupMap.put("testGroupMember" , testGroupMember);
                    updateGroupMap.put("updateTime" , DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    myBatisDao.executionPojo(TESTGROUP, updateGroupMap, IMyBatisDao.SQL.UPDATE, "updateTestGroup");
                }
            }
            //修改原先小组的成员信息
            Map mapOld = new HashMap<>();
            mapOld.put("id" , testEmployeePoolDto.getOldTestGroup());
            List<TestGroup> oldTestGroupList = myBatisDao.selectListT(TESTGROUP, mapOld, "queryTestGroupName");
            if (oldTestGroupList.size() > 0) {
                TestGroup oldTestGroup = oldTestGroupList.get(0);
                String oldTestGroupMember = oldTestGroup.getTestGroupMember();
                if (oldTestGroupMember.contains(testEmployeePoolDto.getTestEmpBadge())) {
                    if (oldTestGroupMember.contains(testEmployeePoolDto.getTestEmpBadge()+",")) {
                        oldTestGroupMember = oldTestGroupMember.replace(testEmployeePoolDto.getTestEmpBadge()+"," , "");
                    }else if (oldTestGroupMember.contains(testEmployeePoolDto.getTestEmpBadge())) {
                        oldTestGroupMember = oldTestGroupMember.replace(testEmployeePoolDto.getTestEmpBadge(), "");
                    }
                    Map updateGroupMap =  new HashMap<>();
                    updateGroupMap.put("id" , testEmployeePoolDto.getOldTestGroup());
                    updateGroupMap.put("testGroupMember" , oldTestGroupMember);
                    updateGroupMap.put("updateTime" , DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    myBatisDao.executionPojo(TESTGROUP, updateGroupMap, IMyBatisDao.SQL.UPDATE, "updateTestGroup");
                }
            }
            result.setMessage("测试人员信息修改成功!");
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            log.error("测试人员信息修改异常,异常信息为:{},出现异常的数据为:{}", e, testEmployeePoolDto);
            result.setMessage("测试人员信息修改异常，请联系管理员查看。");
            result.setStatus(ApiCode.FAIL);
        }
        return result;
    }

    /**
     * @param teamId: 团队ID
     * @return List < TestGroup>
     * @date 2024/9/13
     * @version JDK 8
     * @description 查询测试团队下所有的小组信息
     */
    @Override
    public List<TestGroup> selectTestGroupInfoMsg(String teamId) {
        Map map = new HashMap<>();
        map.put("ownedTestTeam", teamId);
        List<TestGroup> testGroupList = null;
        try {
            testGroupList = myBatisDao.selectListT(TESTGROUP, map, "queryTestGroupName");
        } catch (Exception e) {
            log.error("人员池编辑功能,获取团队下相关测试小组信息异常,异常信息为:{}" , e);
        }
        return testGroupList;
    }
}
