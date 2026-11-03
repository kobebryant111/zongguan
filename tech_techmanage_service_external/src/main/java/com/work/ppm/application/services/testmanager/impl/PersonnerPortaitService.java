package com.work.ppm.application.services.testmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmRdEmployeeInfoExportDto;
import com.work.ppm.application.dto.testmanagement.PersonnerPortaitsDto;
import com.work.ppm.application.dto.testmanagement.PpmTeamGroup;
import com.work.ppm.application.dto.testmanagement.PpmTestGroupDto;
import com.work.ppm.application.dto.testmanagement.PpmTestGroupInfo;
import com.work.ppm.application.dto.testmanagement.PpmTestTeamDto;
import com.work.ppm.application.dto.testmanager.TestGroupDto;
import com.work.ppm.application.model.rdmanager.RdProjectExcel;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.dto.testmanagement.PpmTestTeamInfo;
import com.work.ppm.application.model.testmanager.TestEmployeePool;
import com.work.ppm.application.model.testmanager.TestGroup;
import com.work.ppm.application.model.testmanager.TestGroupExcel;
import com.work.ppm.application.model.testmanager.TestGroupInfo;
import com.work.ppm.application.model.testmanager.TestTeatDto;
import com.work.ppm.application.model.testmanager.TestTeatDtoExcel;
import com.work.ppm.application.services.testmanager.IPersonnerPortaitService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtilNew;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonnerPortaitService implements IPersonnerPortaitService {

    @Autowired
    private IMyBatisDao myBatisDao;

    private static String TESTAPECTACULARS = "com.work.ppm.application.model.testmanager.PersonnerPortait";
    /**
     * 实体类路径
     */
    private static String TESTGROUP = "com.work.ppm.application.model.testmanager.TestGroup";

    @Override
    public void queryPager(PersonnerPortaitsDto personnerPortaitsDto, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(personnerPortaitsDto, PersonnerPortaitsDto.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("分页查询失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }


    public void queryPagerInfo(PersonnerPortaitsDto personnerPortaitsDto, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(personnerPortaitsDto, PersonnerPortaitsDto.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("分页查询失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<CommEmployeeInfo> selectTeamMansgerList() {
        try {
            HashMap<Object, Object> map = new HashMap<>();
            List<CommEmployeeInfo> commEmployees = myBatisDao.selectListT(TESTAPECTACULARS, map, "selectTeamMansgerList");
            return commEmployees;
        } catch (Exception e) {
            log.error("查询项目经理列表失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<TestEmployeePool> selectTeamMemberList() {
        try {
            HashMap<Object, Object> map = new HashMap<>();
            List<TestEmployeePool> testEmployeePools = myBatisDao.selectListT(TESTAPECTACULARS, map, "selectTeamMemberList");
            return testEmployeePools;
        } catch (Exception e) {
            log.error("查询团队成员列表失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public ApiResult<List> creatOfTeam(PpmTestTeamDto ppmTestTeamDto, String userid) {
        ApiResult<List> result = new ApiResult<>();
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamDto, PpmTestTeamDto.class);
            map.put("teamId", StringUtilNew.getPrimarykey());
            map.put("teamCreateUser", userid);
            map.put("teamMansger", String.join(",", ppmTestTeamDto.getTeamMansger()));
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.INSTER, "creatOfTeam");
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "updateTestEmployeePool");
            result.setStatus(ApiCode.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("测试团队创建失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);

        }

    }

    public void teamNameListInfo(PpmTestTeamInfo ppmTestTeamInfo, Pager pager) {

        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamInfo, PpmTestTeamInfo.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map, pager, "teamNameListInfo");
        } catch (Exception e) {
            log.error("查询测试团队列表失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<TestEmployeePool> teamMemberListinfo() {
        try {
            HashMap<Object, Object> map = new HashMap<>();
            List<TestEmployeePool> testEmployeePools =
                    myBatisDao.selectListT(TESTAPECTACULARS, map, "teamMemberListinfo");
            return testEmployeePools;
        } catch (Exception e) {
            log.error("查询团队成员做下拉框失败,错误信息为: {}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public void edit(PpmTestTeamDto ppmTestTeamDto, String userid) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamDto, PpmTestTeamDto.class);
            map.put("teamCreateUser", userid);
            map.put("teamMansger", String.join(",", ppmTestTeamDto.getTeamMansger()));
            myBatisDao.executionMap(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "editTestTeam");
        } catch (Exception e) {
            log.error("编辑测试团队失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<TestEmployeePool> editTemMeb(PpmTestTeamDto ppmTestTeamDto, String userid) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamDto, PpmTestTeamDto.class);
            List<TestEmployeePool> testEmployeePools =
                    myBatisDao.selectListT(TESTAPECTACULARS, map, "editTemMeb");
            return testEmployeePools;
        } catch (Exception e) {
            log.error("修改团队成员失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<TestEmployeePool> queryTemMeb(PpmTestTeamDto ppmTestTeamDto) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamDto, PpmTestTeamDto.class);
            List<TestEmployeePool> testEmployeePools =
                    myBatisDao.selectListT(TESTAPECTACULARS, map, "queryTemMeb");
            return testEmployeePools;
        } catch (Exception e) {
            log.error("查询无团队的成员和当前团队所属成员失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTemMeb(PpmTestTeamDto ppmTestTeamDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamDto, PpmTestTeamDto.class);
        try {
            List<TestEmployeePool> testEmployeePools =
                    myBatisDao.selectListT(TESTAPECTACULARS, map, "editTemMeb");
            // 该团队原有的成员
            List<String> oldMembers =
                    testEmployeePools.stream().map(TestEmployeePool::getTestEmpBadge).collect(Collectors.toList());
            // 修改为的团队成员
            List<String> newMembers = ppmTestTeamDto.getTeamMember();
            // 比较两个集合差
            // 创建一个oldMembers的副本
            List<String> diff1 = new ArrayList<>(oldMembers);
            diff1.removeAll(newMembers);
            // 创建一个newMembers的副本
            List<String> diff2 = new ArrayList<>(newMembers);
            diff2.removeAll(oldMembers);
            HashMap<Object, Object> mapDiff = new HashMap<>();
            if (diff1.size() > 0) {
                mapDiff.put("teamId", ppmTestTeamDto.getTeamId());
                mapDiff.put("teamMember", diff1);
                myBatisDao.executionPojo(TESTAPECTACULARS, mapDiff, IMyBatisDao.SQL.UPDATE, "delTeEmpPool");
            }
            if (diff2.size() > 0) {
                mapDiff.put("teamId", ppmTestTeamDto.getTeamId());
                mapDiff.put("teamMember", diff2);
                myBatisDao.executionPojo(TESTAPECTACULARS, mapDiff, IMyBatisDao.SQL.UPDATE, "updateTestEmployeePool");
            }
        } catch (Exception e) {
            log.error("修改团队成员失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTeam(PpmTestTeamDto ppmTestTeamDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamDto, PpmTestTeamDto.class);
        try {
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.DELETE, "deleteTeam");
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.DELETE, "deleteGroup");
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "upadteTeEmplPol");
        } catch (Exception e) {
            log.error("删除测试团队失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }

    }

    public List<TestGroup> temDetail(PpmTestTeamDto ppmTestTeamDto) {
        try {
            Map map = new HashMap<>();
            map.put("ownedTestTeam", ppmTestTeamDto.getTeamId());
            List<TestGroup> list = myBatisDao.selectListT(TESTGROUP, map, "queryTestGroupName");
            return list;
        } catch (Exception e) {
            log.error("查询测试团队详情失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<TestTeatDtoExcel> excelInfo(PpmTestTeamInfo ppmTestTeamInfo, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestTeamInfo, PpmTestTeamInfo.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map, pager, "teamNameListInfo");
            List<TestTeatDto> testTeatDtos = JsonUtil.jsonToList(pager.getList(), TestTeatDto.class);
            for (TestTeatDto testTeatDto : testTeatDtos) {
                Map<String, Object> map1 = new HashMap<>();
                List<String> split = Arrays.asList(testTeatDto.getTeamMansger().split(","));
                map1.put("teamMansger", split);
                String teamMansger = myBatisDao.selectClass(TESTAPECTACULARS, map1, "empnamesInfo");
                testTeatDto.setTeamMansger(teamMansger);
            }
            List<TestTeatDtoExcel> repsJson = JsonUtil.jsonToList(testTeatDtos, TestTeatDtoExcel.class);
            return repsJson;
        } catch (Exception e) {
            log.error("下载测试团队列表情失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public List<TestGroup> secltGroup() {
        try {
            Map<Object, Object> map = new HashMap<>();
            List<TestGroup> testGroups = myBatisDao.selectListT(TESTAPECTACULARS, map, "secltGroup");
            return testGroups;
        } catch (Exception e) {
            log.error("测试小组下拉框查询失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    public void queryPagerGrop(PpmTeamGroup ppmTeamGroup, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTeamGroup, PpmTeamGroup.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map, pager, "queryPagerGrop");
        } catch (Exception e) {
            log.error("查询测试小组列表失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }

    }

    public List<TestGroupExcel> secltGroupExcel(PpmTeamGroup ppmTeamGroup, Pager pager) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTeamGroup, PpmTeamGroup.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map, pager, "queryPagerGrop");
            List<TestGroupInfo> testGroupInfos = JsonUtil.jsonToList(pager.getList(), TestGroupInfo.class);
            for (TestGroupInfo testGroupInfo : testGroupInfos) {
                Map<String, Object> map1 = new HashMap<>();
                List<String> split = Arrays.asList(testGroupInfo.getTeamMansger().split(","));
                map1.put("teamMansger", split);
                String teamMansger = myBatisDao.selectClass(TESTAPECTACULARS, map1, "empnamesInfo");
                testGroupInfo.setTeamMansger(teamMansger);
            }
            List<TestGroupExcel> repsJson = JsonUtil.jsonToList(testGroupInfos, TestGroupExcel.class);
            return repsJson;
        } catch (Exception e) {
            log.error("下载测试小组列表失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteGroupInfo(PpmTestGroupDto ppmTestGroupDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmTestGroupDto, PpmTestGroupDto.class);
        try {
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.DELETE, "deleteGroupInfo");
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "updateGroupInfo");
        } catch (Exception e) {
            log.error("删除测试小组列表失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }

    }

    public List<TestEmployeePool> groupDateInfo(PpmTestGroupDto ppmTestGroupDto) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestGroupDto, PpmTestGroupDto.class);
            List<TestEmployeePool> testEmployeePools =
                    myBatisDao.selectListT(TESTAPECTACULARS, map, "groupDateInfo");
            return testEmployeePools;
        } catch (Exception e) {
            log.error("测试小组成员下拉框失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTestGroup(PpmTestGroupInfo ppmTestGroupInfo) {
        try {
            Map<String, Object> map = JsonUtil.objectTOMap(ppmTestGroupInfo, PpmTestGroupInfo.class);
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "udadelTestGroup");
            map.put("testGroupMembers", String.join(",", ppmTestGroupInfo.getParamGroup()));
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "updateTestGroup");
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "updatemptepool");
            myBatisDao.executionPojo(TESTAPECTACULARS, map, IMyBatisDao.SQL.UPDATE, "updatempAttribute");
        } catch (Exception e) {
            log.error("编辑测试小组确认失败,错误信息为:{}。", e.getMessage(), e);
            throw new BizException(e);
        }


    }
}
