package com.work.ppm.application.services.testmanager.impl;

import com.work.ppm.application.dto.testmanager.TestEmployeePoolDto;
import com.work.ppm.application.dto.testmanager.TestEmployeePoolExportDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.services.testmanager.IUndistributionPoolService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * packageName com.work.ppm.application.services.testmanager.impl
 *
 * @version JDK 8
 * @className TestEmployeePoolService
 * @date 2024/8/30
 * @description 未分配测试人员池服务
 */
@Service("undistributionPoolService")
@Slf4j
public class UndistributionPoolService implements IUndistributionPoolService {
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
     * @param testEmployeePoolDto: 对象
     * @param pager: 分页参数
     * @return void
     * @date 2024/8/30
     * @version JDK 8
     * @description 分页查询
     */
    @Override
    public void queryPager(TestEmployeePoolDto testEmployeePoolDto, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(testEmployeePoolDto, TestEmployeePoolDto.class);
            myBatisDao.selectPager(TESTEMPLOYEEPOOL, map, pager, "queryUndistributionPagerList");
        } catch (Exception e) {
            log.error("未分配测试人员池信息查询异常,错误信息为:{}。", e);
        }
    }

    /**
     * @param : 
     * @return List < CommEmployee>
     * @date 2024/8/30
     * @version JDK 8
     * @description 获取公司负责人列表
     */
    @Override
    public List<CommEmployee> getHeadOfCompanyList() {
        return myBatisDao.selectListT(COMMEMPLOYEE, null, "getHeadOfCompanyList");
    }

    /**
     * @param testEmployeePoolDto: 对象
     * @return List < TestEmployeePoolExportDto>
     * @date 2024/8/30
     * @version JDK 8
     * @description 导出未分配人员池信息
     */
    @Override
    public List<TestEmployeePoolExportDto> exportUndistriPoolExcel(TestEmployeePoolDto testEmployeePoolDto) {
        List<TestEmployeePoolExportDto> repsJson = new ArrayList<>();
        try{
            Map<String, Object> map = JsonUtil.objectTOMap(testEmployeePoolDto, TestEmployeePoolDto.class);
            List<TestEmployeePool> testEmployeePools = myBatisDao.selectListT(TESTEMPLOYEEPOOL, map, "queryUndistributionPagerList");
            repsJson = JsonUtil.jsonToList(testEmployeePools, TestEmployeePoolExportDto.class);
        } catch (Exception e) {
            log.error("未分配测试人员池信息导出异常,异常信息为:{}" , e);
        }
        return repsJson;
    }

    /**
     * @param testEmployeePoolDto: 对象
     * @return ApiResult
     * @date 2024/8/30
     * @version JDK 8
     * @description 测试人员出池
     */
    @Override
    public ApiResult dialogAddSubmit(TestEmployeePoolDto testEmployeePoolDto) {
        ApiResult result = new ApiResult<>();
        try {
            testEmployeePoolDto.setUpdateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
            testEmployeePoolDto.setEmpAttribute("0");
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
            result.setMessage("测试人员出池成功!");
            result.setStatus(ApiCode.SUCCESS);
        } catch (Exception e) {
            log.error("测试人员出池异常,异常信息为:{},出现异常的数据为:{}", e, testEmployeePoolDto);
            result.setMessage("测试人员出池异常，请联系管理员查看。");
            result.setStatus(ApiCode.FAIL);
        }
        return result;
    }
}
