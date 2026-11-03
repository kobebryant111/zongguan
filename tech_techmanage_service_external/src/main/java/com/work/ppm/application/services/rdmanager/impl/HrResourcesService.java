package com.work.ppm.application.services.rdmanager.impl;


import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmAttendanceInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmRdEmployeeInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmRdHrProjectInfoExportDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.commonmanager.CommRdDepartment;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.*;
import com.work.ppm.application.services.administration.ResourcesAllocationServiceImpl;
import com.work.ppm.application.services.rdmanager.IHrResourcesService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 研发项目管理
 *
 */
@Service
@Slf4j
public class HrResourcesService implements IHrResourcesService {
    /**
     * 实体类路径
     */
    private static String PPMKQCALENDAR = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";

    /**
     * 实体类路径
     */
    private static String COMMRDEMPLOYEE = "com.work.ppm.application.model.rdmanager.CommRdEmployeeDto";

    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";
    @Autowired
    private IMyBatisDao myBatisDao;

    @Autowired
    private HrNewRequiresService hrNewRequiresService;

    @Override
    public void queryPager(CommRdEmployeeDto commRdEmployeeDto, Pager pager) {
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
        myBatisDao.selectPager(COMMRDEMPLOYEE, commRdEmployeeMap, pager, "queryPagerList");
    }

    @Override
    public List<CommRdEmployeeDto> queryList(RdQueryEmployee rdQueryDeptType, CommOperatorDoLoginDto user) {
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(rdQueryDeptType, RdQueryEmployee.class);
        List<CommRdEmployeeDto> queryPagerList = new ArrayList<>();
        boolean isShow = false;
        List<String> roleIdList = queryOperaAuthEncoding(user.getUserid());
        String attendanceDate = rdQueryDeptType.getAttendanceDate();
        List<ImRoleDoLoginDto> roles = user.getRoles();
        for (ImRoleDoLoginDto role : roles) {
            String roleId = role.getRoleId();
            if ("DEP_SPECIALIST".equals(roleId)) {
                isShow = true;
            }
        }
        HashMap<String, Object> map1 = new HashMap<>();
        String firstDay;
        String lastDay;
        if (StringUtils.isNotEmpty(attendanceDate)) {
            final YearMonth parse = YearMonth.parse(attendanceDate);
            firstDay = parse.atDay(1).toString();
            lastDay = parse.atEndOfMonth().toString();
        } else {
            firstDay = DateUtil.getFirstDay();
            lastDay = DateUtil.getLastDay();
        }
        map1.put("fistDay", firstDay);
        map1.put("lastDay", lastDay);
        map1.put("badge", user.getUserid());
        Integer depid = myBatisDao.selectClass(COMMRDEMPLOYEE, map1, "querDepidByBadge");
        if (roleIdList.isEmpty()) {
            map1.put("depid", depid);
        }
        if (isShow) {
            map1.put("badge", "");
        }

        if (!ObjectUtils.isEmpty(rdQueryDeptType.getAttendanceDate())) {
            Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdQueryDeptType, RdQueryEmployee.class);

            List<RdProjectAttendance> list = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atDateManagement");
            if ("2".equals(list.get(0).getAttendanceStatus())) {
                if (roleIdList.isEmpty()) {
                    rdQueryDeptTypemap.put("depid", depid);
                }
                if (isShow) {
                    rdQueryDeptTypemap.put("badge", "");
                } else {
                    rdQueryDeptTypemap.put("badge", user.getUserid());
                }
                List<HrProject> hrProjects = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atAuditManagementKqcx");
                List<List<HrProject>> collect = hrProjects.
                        stream().collect(Collectors.groupingBy(HrProject::getEid)).values().
                        stream().collect(Collectors.toList());
                for (java.util.List<HrProject> projects : collect) {
                    queryPagerList.add(CommRdEmployeeDto.builder()
                            .eid(projects.get(0).getEid())
                            .badge(projects.get(0).getBadge())
                            .empname(projects.get(0).getUsername())
                            .nature(projects.get(0).getNature())
                            .depid(projects.get(0).getDepid())
                            .chargeName(projects.get(0).getChargeName())
                            .build());
                }
                return queryPagerList;
            }
        }
        map1.putAll(commRdEmployeeMap);
        List<CommRdEmployeeDto> commRdEmployeeDtoList = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "rdQueryDeptTree");
        if (!ObjectUtils.isEmpty(rdQueryDeptType.getRdQueryType())) {
            switch (rdQueryDeptType.getRdQueryType()) {
                case "0": {
                    queryPagerList.addAll(commRdEmployeeDtoList);
                    break;
                }
                case "1":
                case "2": {
                    if (ObjectUtils.isEmpty(rdQueryDeptType.getAttendanceDate())) {
                        return new ArrayList<>();
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("attendanceDate", rdQueryDeptType.getAttendanceDate());
                    map.put("attendanceStatus", "2");
                    if (roleIdList.isEmpty()) {
                        map.put("depid", depid);
                    }
                    if (isShow) {
                        map.put("badge", "");
                    } else {
                        map.put("badge", user.getUserid());
                    }
                    map.put("fistDay", firstDay);
                    map.put("lastDay", lastDay);
                    map.putAll(commRdEmployeeMap);
                    List<CommRdEmployeeDto> collect = commRdEmployeeDtoList.stream()
                            .filter(item -> item.getIsProject() != 1).collect(Collectors.toList());
                    List<CommRdEmployeeDto> queryPagerList1 =
                            myBatisDao.selectListT(COMMRDEMPLOYEE, map, "rdAttendanceTreeNew");
                    queryPagerList.addAll(collect);
                    queryPagerList.addAll(queryPagerList1);
                    break;
                }
                default:
            }
        }
        return queryPagerList;
    }

    @Override
    public List<CommRdEmployeeDto> queryAdministrationList(CommRdEmployeeDto commRdEmployeeDto) {
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
        List<CommRdEmployeeDto> commRdEmployeeDtoList = myBatisDao.selectListT(COMMRDEMPLOYEE, commRdEmployeeMap, "queryAdministrationList");
        return commRdEmployeeDtoList;
    }

    @Override
    public List<PpmRdEmployeeInfoExportDto> queryPagerInfo(CommRdEmployeeDto commRdEmployeeDto) {
        Pager pager = new Pager();
        pager.setIndex(1);
        pager.setSize(5000);
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
        myBatisDao.selectPager(COMMRDEMPLOYEE, commRdEmployeeMap, pager, "queryPagerList");
        List<PpmRdEmployeeInfoExportDto> ppmRdEmployeeInfoExportDtos = JsonUtil.jsonToList(pager.getList(), PpmRdEmployeeInfoExportDto.class);
        return ppmRdEmployeeInfoExportDtos;
    }


    @Override
    public void emqueryPager(CommEmployeeDto commEmployeeDto, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        myBatisDao.selectPager(COMMRDEMPLOYEE, map, pager, "emqueryPagerList");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<CommRdEmployeeDto> commRdEmployeeDtoList) {
        Map<String, Object> map = new HashMap<>();
        List<CommRdDepartment> list = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "departmentByAll");
        if (!commRdEmployeeDtoList.isEmpty()) {
            for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
                CommRdDepartment commRdDepartment = CommRdDepartment.recursiveListToTree(list, commRdEmployeeDto.getDepid().toString());
                try {
                    myBatisDao.executionPojo(COMMRDEMPLOYEE, commRdDepartment, IMyBatisDao.SQL.INSTER, "addDepartment");
                } catch (Exception e) {
                    log.info("该部门已添加");
                }
                commRdEmployeeDto.setDepid(Integer.valueOf(commRdDepartment.getDepid()));
                myBatisDao.executionPojo(COMMRDEMPLOYEE, commRdEmployeeDto, IMyBatisDao.SQL.INSTER, "addPojo");
            }
        }
    }

    @Override
    public Integer delete(CommRdEmployeeDto commRdEmployeeDto) {
        if (commRdEmployeeDto != null) {
            Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
            Integer deletePojo = myBatisDao.executionMap(COMMRDEMPLOYEE, commRdEmployeeMap, IMyBatisDao.SQL.DELETE, "deletePojo");
            return deletePojo;
        }
        return null;
    }

    @Override
    public List<CommTreeDto> queryDeptTree(CommEmployeeDto commEmployeeDto) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        List<CommTree> list = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "queryResourceDept");
        List<CommTree> collect = list.stream().map(item -> {
            String[] split = item.getText().split("-");
            String s1 = split[split.length - 1];
            item.setText(s1);
            return item;
        }).collect(Collectors.toList());
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(collect, CommTreeDto.class);
        // 递归 组织树
        List<CommTreeDto> newTreeList = CommTreeDto.recursiveListToTree(dtoList);
        return newTreeList;
    }

    @Override
    public List<CommRdDepartment> queryDepartment(CommEmployeeDto commEmployeeDto) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        List<CommRdDepartment> list = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "queryDepartment");
        return list;
    }

    @Autowired
    private ResourcesAllocationServiceImpl resourcesAllocationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(CommRdEmployeeDto commRdEmployeeDto) {
        try {
            if (StringUtil.isNotEmpty(commRdEmployeeDto.getBadge())) {
                if ("0".equals(commRdEmployeeDto.getEmpstatus())) {
                    commRdEmployeeDto.setLeaveTime(null);
                }
                Map<String, Object> map = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
                myBatisDao.executionMap(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "editDepartment");
                myBatisDao.executionMap(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "editPojo");
                resourcesAllocationService.editPojo(map);
            }
        } catch (Exception e) {
            log.error("编辑异常", e);
            throw new BizException(ApiCode.ATTENDANCE_ERROR, e.getMessage());
        }
    }

    @Override
    public List<HrProject> rdProject(CommRdEmployeeDto commRdEmployeeDto) {
        if (ObjectUtils.isEmpty(commRdEmployeeDto.getEid()) || ObjectUtils.isEmpty(commRdEmployeeDto.getDepid())) {
            return null;
        }
        Map<String, Object> map = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
        map.put("depid", map.get("depid") + "");
        List<HrProject> dataList = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "queryPagerproject");
        return dataList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProject(CommRdEmployeeProjectDto commRdEmployeeProjectDto) {
        try {
            List<HrProject> hrProjectList = commRdEmployeeProjectDto.getHrProjectList();
            CommRdEmployeeDto commRdEmployeeDto = commRdEmployeeProjectDto.getCommRdEmployeeDto();
            Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
            CommRdEmployeeDto rdEmployeeDto = myBatisDao.selectClass(COMMRDEMPLOYEE, commRdEmployeeMap, "queryPagerList");
            //该员工不是在岗状态则不能添加项目
//            if (!("0".equals(rdEmployeeDto.getEmpstatus()))) {
//                log.error("该员工不是在岗状态");
//                throw new BizException(ApiCode.ATTENDANCE_ERROR, "该员工不是在岗状态");
//            }
            PpmKqCalendarDto ppmKqCalendarDto = new PpmKqCalendarDto();
            ppmKqCalendarDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
            ppmKqCalendarDto.setEntryEndDate(DateUtil.getLasttDayByMonth());
            Map<String, Object> map1 = JsonUtil.objectTOMap(ppmKqCalendarDto, PpmKqCalendarDto.class);
            //获取当月空白考勤
            List<PpmKqCalendar> queryPagerList = myBatisDao.selectListT(PPMKQCALENDAR, map1, "queryPagerList");
            List<PpmKqRdCalendar> ppmKqRdCalendarList = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (hrProjectList.isEmpty() || commRdEmployeeDto.getEid() == null) {
                return;
            }
            for (HrProject hrProject : hrProjectList) {
                Map<String, Object> map = new HashMap<>();
                map.put("eidCode", commRdEmployeeDto.getEid() + "_" + hrProject.getProjectCode());
                map.put("eid", commRdEmployeeDto.getEid());
                map.put("projectCode", hrProject.getProjectCode());
                map.put("entryStartDate", DateUtil.getFirstDayByMonth());
                map.put("entryEndDate", DateUtil.getLasttDayByMonth());
                List<RdProjectEmployee> rdProjectEmployee = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "rdDisabledEmployee");
                //判断该项目是否为逻辑删除状态 是：修改状态 否：添加项目
                if (!ObjectUtils.isEmpty(rdProjectEmployee)) {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("eid", commRdEmployeeDto.getEid());
                    map2.put("projectCode", hrProject.getProjectCode());
                    map2.put("isDisabled", "0");
                    // 是：修改状态
                    myBatisDao.executionMap(COMMRDEMPLOYEE, map2, IMyBatisDao.SQL.UPDATE, "prDelete");
                } else {
                    // 否：添加项目
                    myBatisDao.executionMap(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.INSTER, "addproject");
                }
                List<PpmKqRdCalendar> ppmKqRdCalendars = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "rdDisabledProject");
                if (!ObjectUtils.isEmpty(ppmKqRdCalendars)) {
                    continue;
                }
                extracted(rdEmployeeDto, queryPagerList, ppmKqRdCalendarList, simpleDateFormat, hrProject);
            }
            // 添加当月空白考勤
            if (!ObjectUtils.isEmpty(ppmKqRdCalendarList)) {
                myBatisDao.executionPojo(COMMRDEMPLOYEE, ppmKqRdCalendarList, IMyBatisDao.SQL.INSTER, "atManagement");
            }
        } catch (Exception e) {
            log.error("项目分配异常", e);
            throw new BizException(ApiCode.ATTENDANCE_ERROR, e.getMessage());
        }

    }

    private void extracted(CommRdEmployeeDto commRdEmployeeDto, List<PpmKqCalendar> queryPagerList,
                           List<PpmKqRdCalendar> ppmKqRdCalendarList,
                           SimpleDateFormat simpleDateFormat, HrProject hrProject) throws ParseException {
        if (ObjectUtils.isEmpty(commRdEmployeeDto.getArrivalTime())) {
            throw new BizException(ApiCode.ATTENDANCE_ERROR, "该员工缺失到岗日期");
        }
        boolean leaveBool = false;
        final Date arrivalParse = simpleDateFormat.parse(commRdEmployeeDto.getArrivalTime());
        Date leaveParse = new Date();
        if (!ObjectUtils.isEmpty(commRdEmployeeDto.getLeaveTime())) {
            leaveBool = true;
            leaveParse = simpleDateFormat.parse(commRdEmployeeDto.getLeaveTime());
        }
        for (PpmKqCalendar ppmKqCalendar : queryPagerList) {
            PpmKqRdCalendar ppmKqRdCalendar = new PpmKqRdCalendar();
            final Date calendarDate = ppmKqCalendar.getCalendarDate();
            final int i = calendarDate.compareTo(arrivalParse);
            int l = -1;
            if (leaveBool) {
                l = calendarDate.compareTo(leaveParse);
            }
            if (i >= 0 && l < 0 && "N".equals(ppmKqCalendar.getIsHoliday())) {
                ppmKqRdCalendar.setAttendanceOrno("1");
            } else if ((i < 0 || l >= 0) && "N".equals(ppmKqCalendar.getIsHoliday())) {
                ppmKqRdCalendar.setAttendanceOrno("0");
            } else {
                ppmKqRdCalendar.setAttendanceOrno("2");
            }
            ppmKqRdCalendar.setEidCode(commRdEmployeeDto.getEid() + "_" + hrProject.getProjectCode());
            ppmKqRdCalendar.setId(commRdEmployeeDto.getEid() + "_" + hrProject.getProjectCode() + "_"
                    + simpleDateFormat.format(ppmKqCalendar.getCalendarDate()));
            BeanUtils.copyProperties(ppmKqCalendar, ppmKqRdCalendar);
            ppmKqRdCalendarList.add(ppmKqRdCalendar);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void prDelete(HrProject hrProject, CommRdEmployeeDto commRdEmployeeDto) {
        if ((!ObjectUtils.isEmpty(hrProject.getProjectCode())) && (!ObjectUtils.isEmpty(commRdEmployeeDto.getEid()))) {
            Map<String, Object> map = new HashMap<>();
            map.put("eid", commRdEmployeeDto.getEid());
            map.put("projectCode", hrProject.getProjectCode());
            map.put("isDisabled", "1");
            myBatisDao.executionMap(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "prDelete");
            //得到上月的日期
            LocalDate localDate = LocalDate.now();
//            LocalDate localDate = currentDate.minusMonths(1);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            String attendanceDate = localDate.format(dateTimeFormatter);
            map.put("attendanceDate", attendanceDate);
            map.put("eidCode", commRdEmployeeDto.getEid() + "_" + hrProject.getProjectCode());
            myBatisDao.executionMap(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.DELETE, "prDisabledDelete");
        }
    }

    @Override
    public List<CommRdEmployeeDto> rdQueryDeptTree(RdQueryDeptType rdQueryDeptType, CommOperatorDoLoginDto user) {
        List<CommRdEmployeeDto> queryPagerList = new ArrayList<>();
        boolean isShow = false;
        List<String> roleIdList = queryOperaAuthEncoding(user.getUserid());
        String attendanceDate = rdQueryDeptType.getAttendanceDate();

        List<ImRoleDoLoginDto> roles = user.getRoles();
        for (ImRoleDoLoginDto role : roles) {
            String roleId = role.getRoleId();
            if ("DEP_SPECIALIST".equals(roleId)) {
                isShow = true;
            }
        }
        HashMap<String, Object> map1 = new HashMap<>();
        String firstDay;
        String lastDay;
        if (StringUtils.isNotEmpty(attendanceDate)) {
            final YearMonth parse = YearMonth.parse(attendanceDate);
            firstDay = parse.atDay(1).toString();
            lastDay = parse.atEndOfMonth().toString();
        } else {
            firstDay = DateUtil.getFirstDay();
            lastDay = DateUtil.getLastDay();
        }
        map1.put("fistDay", firstDay);
        map1.put("lastDay", lastDay);
        String deptId = null;
        map1.put("badge", user.getUserid());
        Integer depid = myBatisDao.selectClass(COMMRDEMPLOYEE, map1, "querDepidByBadge");
        if (roleIdList.isEmpty()) {
            deptId = user.getDeptId();
            map1.put("depid", depid);
        }
        if (isShow) {
            map1.put("badge", "");
        }
        if (!ObjectUtils.isEmpty(rdQueryDeptType.getAttendanceDate())) {
            Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdQueryDeptType, RdQueryDeptType.class);

            List<RdProjectAttendance> List = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atDateManagement");
            // 考勤查询已提交造册节点查询
            if ("2".equals(List.get(0).getAttendanceStatus())) {
                if (roleIdList.isEmpty()) {
                    deptId = user.getDeptId();
                    rdQueryDeptTypemap.put("depid", depid);
                }
                if (isShow) {
                    rdQueryDeptTypemap.put("badge", "");
                } else {
                    rdQueryDeptTypemap.put("badge", user.getUserid());
                }
                List<HrProject> hrProjects = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atAuditManagement");
                List<List<HrProject>> collect = hrProjects.
                        stream().collect(Collectors.groupingBy(HrProject::getEid)).values().
                        stream().collect(Collectors.toList());
                for (java.util.List<HrProject> projects : collect) {
                    queryPagerList.add(CommRdEmployeeDto.builder()
                            .eid(projects.get(0).getEid())
                            .badge(projects.get(0).getBadge())
                            .empname(projects.get(0).getUsername())
                            .nature(projects.get(0).getNature())
                            .depid(projects.get(0).getDepid())
                            .chargeName(projects.get(0).getChargeName())
                            .build());
                }
                return queryPagerList;
            }
        }

        List<CommRdEmployeeDto> commRdEmployeeDtoList = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "rdQueryDeptTree");
        if (!ObjectUtils.isEmpty(rdQueryDeptType.getRdQueryType())) {
            switch (rdQueryDeptType.getRdQueryType()) {
                // 研发项目分配节点查询
                case "0": {
                    queryPagerList.addAll(commRdEmployeeDtoList);
                    break;
                }
                // 考勤填报、考勤审核节点查询
                case "1":
                case "2": {
                    if (ObjectUtils.isEmpty(rdQueryDeptType.getAttendanceDate())) {
                        return new ArrayList<>();
                    }
                    Map<String, Object> map = new HashMap<>();
                    map.put("attendanceDate", rdQueryDeptType.getAttendanceDate());
                    map.put("attendanceStatus", "2");
                    map.put("fistDay", firstDay);
                    map.put("lastDay", lastDay);
                    if (roleIdList.isEmpty()) {
                        map.put("depid", depid);
                    }
                    if (isShow) {
                        map.put("badge", "");
                    } else {
                        map.put("badge", user.getUserid());
                    }
                    List<CommRdEmployeeDto> collect = commRdEmployeeDtoList.stream().filter(item -> item.getIsProject() != 1).collect(Collectors.toList());
                    List<CommRdEmployeeDto> queryPagerList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "rdAttendanceTree");
                    queryPagerList.addAll(collect);
                    queryPagerList.addAll(queryPagerList1);
                    break;
                }
                default:
            }
        }
        return queryPagerList;
    }

    public List<String> queryOperaAuthEncoding(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<String> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryRoleId");
        return list;
    }

    @Override
    public List<HrProject> rdQueryProject(CommRdEmployeeDto commRdEmployeeDto) {
        if (commRdEmployeeDto.getEid() == null) {
            return null;
        }
        Map<String, Object> map = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
        List<HrProject> dataList = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "rdQueryProject");
        return dataList;
    }

    @Override
    public List<PpmAttendanceInfoExportDto> rdQueryProjectInfo(CommRdEmployeeDto commRdEmployeeDto, Pager pager) {

        Map<String, Object> map = StringUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
        myBatisDao.selectPager(COMMRDEMPLOYEE, map, pager, "rdQueryProjectInfo");
        List<PpmAttendanceInfoExportDto> ppmRdHrProjectInfoExportDtos = JsonUtil.jsonToList(pager.getList(), PpmAttendanceInfoExportDto.class);
        return ppmRdHrProjectInfoExportDtos;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void atManagement(PpmKqCalendarDto ppmKqCalendarDto) {

        if (!ObjectUtils.isEmpty(ppmKqCalendarDto.getEntryTime())) {
            String[] split = ppmKqCalendarDto.getEntryTime()[0].split("-");
            YearMonth month = YearMonth.of(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            LocalDate localDate = month.atDay(1);
            LocalDate localDate1 = month.atEndOfMonth();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date firstDayOfMonth = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            Date lastDayOfMonth = Date.from(localDate1.atStartOfDay(defaultZoneId).toInstant());
            ppmKqCalendarDto.setEntryStartDate(firstDayOfMonth);
            ppmKqCalendarDto.setEntryEndDate(DateUtil.addOrSubHour(lastDayOfMonth, 23));
        } else {
            ppmKqCalendarDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
            ppmKqCalendarDto.setEntryEndDate(DateUtil.getLasttDayByMonth());
        }

        Map<String, Object> map = JsonUtil.objectTOMap(ppmKqCalendarDto, PpmKqCalendarDto.class);
        List<PpmKqCalendar> queryPagerList = myBatisDao.selectListT(PPMKQCALENDAR, map, "queryPagerList");
        HashMap<Object, Object> hashMap = new HashMap<>();
        List<RdProjectEmployee> dataList = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "rdProjectEmployee");

        if (dataList.isEmpty()) {
            return;
        }
        List<PpmKqRdCalendar> ppmKqRdCalendarList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (RdProjectEmployee hrProject : dataList) {
            for (PpmKqCalendar ppmKqCalendar : queryPagerList) {
                PpmKqRdCalendar ppmKqRdCalendar = new PpmKqRdCalendar();
                ppmKqRdCalendar.setEidCode(hrProject.getEidCode());
                ppmKqRdCalendar.setId(hrProject.getEidCode() + "_" + simpleDateFormat.format(ppmKqCalendar.getCalendarDate()));
                BeanUtils.copyProperties(ppmKqCalendar, ppmKqRdCalendar);
                ppmKqRdCalendarList.add(ppmKqRdCalendar);
            }
        }
        myBatisDao.executionPojo(COMMRDEMPLOYEE, ppmKqRdCalendarList, IMyBatisDao.SQL.INSTER, "atManagement");
    }

    @Override
    public void atDateManagement(PpmKqRdCalendar ppmKqRdCalendar, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmKqRdCalendar, PpmKqRdCalendar.class);
        myBatisDao.selectPager(COMMRDEMPLOYEE, map, pager, "atDateManagement");

//        return queryPagerList;
    }

    @Override
    public Integer atEditManagement(RdProjectAttendance rdProjectAttendance) {
        Integer atEditManagement = 0;
        if (ObjectUtils.isEmpty(rdProjectAttendance.getAttendanceDate()) || ObjectUtils.isEmpty(rdProjectAttendance.getAttendanceStatus())) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "请求参数错误");
        }
        Map<String, Object> map = JsonUtil.objectTOMap(rdProjectAttendance, RdProjectAttendance.class);
        atEditManagement = myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "atEditManagement");
//        myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "atAttendanceManagement");
        return atEditManagement;
    }

    public List<HrProject> currentSelectInfo(RdManagementProject rdManagementProject) {
        List<HrProject> dataList = new ArrayList<>();
        String attendanceStatus = rdManagementProject.getPpmKqRdCalendar().getAttendanceStatus();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();

        Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdManagementProject.getPpmKqRdCalendar(), PpmKqRdCalendar.class);
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        if (commRdEmployeeDtoList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("('");
            for (int i = 0; i < commRdEmployeeDtoList.size(); i++) {
                sb.append(commRdEmployeeDtoList.get(i).getBadge()).append("'");
                if (i < commRdEmployeeDtoList.size() - 1) {
                    sb.append(",'");
                }
            }
            sb.append(")");
            rdQueryDeptTypemap.put("badges", sb.toString());
        }

        switch (attendanceStatus) {
            case "2": {
                List<HrProject> commRdEmployeeDtos = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "queryAttendance");
                dataList.addAll(commRdEmployeeDtos);
                break;
            }
            default:
        }
        for (HrProject hrProject : dataList) {
            Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
            map1.put("attendanceDate", attendanceDate);
            List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
            hrProject.setRdCalendarList(dataList1);
        }
        return dataList;
    }

    public List<HrProject> attendanceSelectInfo(RdManagementProject rdManagementProject) {
        List<HrProject> dataList = new ArrayList<>();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        final YearMonth parse = YearMonth.parse(attendanceDate);
        String firstDay = parse.atDay(1).toString();
        String lastDay = parse.atEndOfMonth().toString();
        Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdManagementProject.getPpmKqRdCalendar(), PpmKqRdCalendar.class);
        rdQueryDeptTypemap.put("firstDay", firstDay);
        rdQueryDeptTypemap.put("lastDay", lastDay);
        RdProjectAttendance rdProjectAttendances = myBatisDao.selectClass(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atDateManagement");
        final String attendanceStatus = rdProjectAttendances.getAttendanceStatus();
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        if (commRdEmployeeDtoList != null && commRdEmployeeDtoList.size() > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < commRdEmployeeDtoList.size(); i++) {
                list.add(commRdEmployeeDtoList.get(i).getBadge());
            }
            rdQueryDeptTypemap.put("badge", list);
        }
        switch (attendanceStatus) {
            case "0": {
                throw new BizException(ApiCode.PARAMETER_ERROR, "当月考勤未启动");
            }
            case "1": {
                List<HrProject> list = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "rdManagementProjectExcel");
                dataList.addAll(list);
                break;
            }
            case "2": {
                List<HrProject> commRdEmployeeDtos = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "queryAttendance");
                dataList.addAll(commRdEmployeeDtos);
                break;
            }
        }
        for (HrProject hrProject : dataList) {
            Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
            map1.put("attendanceDate", attendanceDate);
            List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
            hrProject.setRdCalendarList(dataList1);
        }
        return dataList;
    }

    @Override
    public List<HrProject> rdManagementProject(RdManagementProject rdManagementProject) {
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdManagementProject.getPpmKqRdCalendar(), PpmKqRdCalendar.class);

        List<RdProjectAttendance> rdProjectAttendances = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atDateManagement");

        List<HrProject> dataList = new ArrayList<>();
        if (!commRdEmployeeDtoList.isEmpty()) {
            for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
                if ("2".equals(rdProjectAttendances.get(0).getAttendanceStatus())) {
                    List<HrProject> commRdEmployeeDtos = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atAuditManagement");
                    dataList.addAll(commRdEmployeeDtos);
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("eid", commRdEmployeeDto.getEid());
                    hashMap.put("attendanceDate", attendanceDate);
                    List<HrProject> list = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "rdManagementProject");
                    dataList.addAll(list);
                }

            }
            for (HrProject hrProject : dataList) {
                Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
                map1.put("attendanceDate", attendanceDate);
                map1.put("eidCode", hrProject.getEidCode());
                List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
                hrProject.setRdCalendarList(dataList1);
            }
            return dataList.stream().filter(item -> !ObjectUtils.isEmpty(item.getRdCalendarList())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<HrProject> queryAttendanceTabulation(RdManagementProject rdManagementProject) {
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdManagementProject.getPpmKqRdCalendar(), PpmKqRdCalendar.class);

        List<RdProjectAttendance> rdProjectAttendances = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atDateManagement");

        List<HrProject> dataList = new ArrayList<>();
        if (!commRdEmployeeDtoList.isEmpty()) {
            for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
                if ("2".equals(rdProjectAttendances.get(0).getAttendanceStatus())) {
                    Map<String, Object> rdEmp = JsonUtil.objectTOMap(commRdEmployeeDto, CommRdEmployeeDto.class);
                    rdEmp.put("attendanceDate", attendanceDate);
                    List<HrProject> commRdEmployeeDtos = myBatisDao.selectListT(COMMRDEMPLOYEE, rdEmp, "attendanceManagement");
                    dataList.addAll(commRdEmployeeDtos);
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("eid", commRdEmployeeDto.getEid());
                    hashMap.put("attendanceDate", attendanceDate);
                    List<HrProject> list = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "rdManagementProject");
                    dataList.addAll(list);
                }
            }
            for (HrProject hrProject : dataList) {
                Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
                map1.put("attendanceDate", attendanceDate);
                map1.put("eidCode", hrProject.getEidCode());
                List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
                hrProject.setRdCalendarList(dataList1);
            }
            return dataList.stream().filter(item -> !ObjectUtils.isEmpty(item.getRdCalendarList())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<HrProject> attendanceRecord(RdManagementProject rdManagementProject) {
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        List<HrProject> dataList = new ArrayList<>();
        if (!commRdEmployeeDtoList.isEmpty()) {
            for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("eid", commRdEmployeeDto.getEid());
                hashMap.put("attendanceDate", attendanceDate);
                List<HrProject> list = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "rdManagementProject");
                if (ObjectUtils.isEmpty(list)) {
                    return null;
                }
                List<HrProject> collect = list.stream().filter(item -> "0".equals(item.getIsdisabled())).collect(Collectors.toList());
                if (ObjectUtils.isEmpty(collect)) {
                    return null;
                }
                dataList.addAll(collect);
            }
            for (HrProject hrProject : dataList) {
                Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
                map1.put("attendanceDate", attendanceDate);
                List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
                if (ObjectUtils.isEmpty(dataList1)) {
                    continue;
                }
                hrProject.setRdCalendarList(dataList1);
            }
            List<HrProject> simpleList = new ArrayList<>();
            for (HrProject hrProject : dataList) {
                HrProject hrProject1 = new HrProject();
                hrProject1.setRdCalendarList(hrProject.getRdCalendarList());
                hrProject1.setUsername(hrProject.getUsername());
                hrProject1.setBadge(hrProject.getBadge());
                hrProject1.setNature(hrProject.getNature());
                hrProject1.setArrivalTime(hrProject.getArrivalTime());
                hrProject1.setLeaveTime(hrProject.getLeaveTime());
                hrProject1.setEmpstatus(hrProject.getEmpstatus());
                simpleList.add(hrProject1);
            }
            List<HrProject> collect1 = simpleList.stream().filter(item
                    -> !ObjectUtils.isEmpty(item.getRdCalendarList())).collect(Collectors.toList());

            List<List<HrProject>> collect2 = collect1.
                    stream().collect(Collectors.groupingBy(HrProject::getBadge)).values().
                    stream().collect(Collectors.toList());

            List<HrProject> simpleList1 = new ArrayList<>();
            for (List<HrProject> hrProjects : collect2) {
                simpleList1.add(hrProjects.get(0));
            }
            return simpleList1;
        }
        return null;
    }

    @Override
    public List<HrProject> qyeryEmployeeAttendance(RdManagementProject rdManagementProject) {
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        Map<String, Object> rdQueryDeptTypemap = JsonUtil.objectTOMap(rdManagementProject.getPpmKqRdCalendar(), PpmKqRdCalendar.class);
        List<RdProjectAttendance> rdProjectAttendances = myBatisDao.selectListT(COMMRDEMPLOYEE, rdQueryDeptTypemap, "atDateManagement");
        List<HrProject> dataList = new ArrayList<>();
        if (!commRdEmployeeDtoList.isEmpty()) {
            for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("eid", commRdEmployeeDto.getEid());
                hashMap.put("attendanceDate", attendanceDate);
                List<HrProject> collect;
                if ("2".equals(rdProjectAttendances.get(0).getAttendanceStatus())) {
                    collect = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "qyeryEmployeeAttendance");
                } else {
                    List<HrProject> list = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "rdManagementProject");
                    if (ObjectUtils.isEmpty(list)) {
                        return null;
                    }
                    collect = list.stream().filter(item -> "0".equals(item.getIsdisabled())).collect(Collectors.toList());
                    if (ObjectUtils.isEmpty(list)) {
                        return null;
                    }
                }

                dataList.addAll(collect);
            }
            for (HrProject hrProject : dataList) {
                Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
                map1.put("attendanceDate", attendanceDate);
                List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
                if (ObjectUtils.isEmpty(dataList1)) {
                    continue;
                }
                hrProject.setRdCalendarList(dataList1);
            }
            List<HrProject> simpleList = new ArrayList<>();
            for (HrProject hrProject : dataList) {
                HrProject hrProject1 = new HrProject();
                hrProject1.setRdCalendarList(hrProject.getRdCalendarList());
                hrProject1.setUsername(hrProject.getUsername());
                hrProject1.setBadge(hrProject.getBadge());
                hrProject1.setNature(hrProject.getNature());
                hrProject1.setArrivalTime(hrProject.getArrivalTime());
                hrProject1.setLeaveTime(hrProject.getLeaveTime());
                hrProject1.setEmpstatus(hrProject.getEmpstatus());
                simpleList.add(hrProject1);
            }
            List<HrProject> collect1 = simpleList.stream().filter(item
                    -> !ObjectUtils.isEmpty(item.getRdCalendarList())).collect(Collectors.toList());

            List<List<HrProject>> collect2 = collect1.
                    stream().collect(Collectors.groupingBy(HrProject::getBadge)).values().
                    stream().collect(Collectors.toList());

            List<HrProject> simpleList1 = new ArrayList<>();
            for (List<HrProject> hrProjects : collect2) {
                simpleList1.add(hrProjects.get(0));
            }
            return simpleList1;
        }
        return null;
    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Integer keep(List<HrProject> hrProjects) {
//        Integer attendanceRecord = 0;
//        if (ObjectUtils.isEmpty(hrProjects)) {
//            return attendanceRecord;
//        }
//        List<PpmKqRdCalendar> ppmKqRdCalendars = new ArrayList<>();
//        for (HrProject hrProject : hrProjects) {
//            String eidCode = hrProject.getEidCode();
//            String username = hrProject.getUsername();
//            String badge = hrProject.getBadge();
//            for (PpmKqRdCalendar ppmKqRdCalendar : hrProject.getRdCalendarList()) {
//                ppmKqRdCalendar.setEidCode(eidCode);
//                ppmKqRdCalendar.setUsername(username);
//                ppmKqRdCalendar.setBadge(badge);
//                if ("N".equals(ppmKqRdCalendar.getIsHoliday())) {
//                    ppmKqRdCalendars.add(ppmKqRdCalendar);
//                }
//            }
//        }
//        for (PpmKqRdCalendar ppmKqRdCalendar : ppmKqRdCalendars) {
//            BigDecimal attendanceTime = ppmKqRdCalendar.getAttendanceTime();
//            if (!ObjectUtils.isEmpty(attendanceTime)) {
//                //attendanceTime需在【0,1】
//                if (attendanceTime.compareTo(BigDecimal.ZERO) < 0 || attendanceTime.compareTo(BigDecimal.ONE) > 0) {
//                    throw new BizException(ApiCode.ATTENDANCE_ERROR,ppmKqRdCalendar.getUsername()+"("+ppmKqRdCalendar.getBadge()+")"+
//                            ppmKqRdCalendar.getDayOfMonth() + "号：考勤时长不在【0~1】");
//                }
//                int scale = attendanceTime.scale();
//                if (attendanceTime.scale() > 1) {
//                    throw new BizException(ApiCode.ATTENDANCE_ERROR,ppmKqRdCalendar.getUsername()+"("+ppmKqRdCalendar.getBadge()+")"+
//                            ppmKqRdCalendar.getDayOfMonth() + "号：考勤时长数值小数应精确至小数点后一位");
//                }
//            }
//        }
//        // 将时间查询条件转换为日期对象"2024-04-17"
//        attendanceRecord = myBatisDao.executionPojo(COMMRDEMPLOYEE, ppmKqRdCalendars, IMyBatisDao.SQL.UPDATE, "attendanceRecord");
//        return attendanceRecord;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(List<HrProject> hrProjectss) {
        //保存考勤
        hrNewRequiresService.keep(hrProjectss);
        if (ObjectUtils.isEmpty(hrProjectss)) {
            throw new BizException(ApiCode.ATTENDANCE_ERROR, "考勤为空");
        }
        List<List<HrProject>> collect2 = hrProjectss.
                stream().collect(Collectors.groupingBy(HrProject::getEid)).values().
                stream().collect(Collectors.toList());
        RdManagementProject rdManagementProject = new RdManagementProject();
        List<CommRdEmployeeDto> commRdEmployeeDtoList = new ArrayList<>();
        //得到研发人员信息
        for (List<HrProject> hrProjects : collect2) {
            CommRdEmployeeDto commRdEmployeeDto = new CommRdEmployeeDto();
            commRdEmployeeDto.setEid(hrProjects.get(0).getEid());
            commRdEmployeeDtoList.add(commRdEmployeeDto);
        }
        //取得考勤日期
        Date calendarDate = hrProjectss.get(0).getRdCalendarList().get(0).getCalendarDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String format = simpleDateFormat.format(calendarDate);
        rdManagementProject.setCommRdEmployeeDtoList(commRdEmployeeDtoList);
        PpmKqRdCalendar ppmKqRdCalendar = new PpmKqRdCalendar();
        ppmKqRdCalendar.setAttendanceDate(simpleDateFormat.format(calendarDate));
        rdManagementProject.setPpmKqRdCalendar(ppmKqRdCalendar);
        //取得库中考勤信息
        List<HrProject> hrProjects = this.rdManagementProject(rdManagementProject);
        for (HrProject projectss : hrProjects) {
            BigDecimal days = new BigDecimal("0");
            BigDecimal time = new BigDecimal("0");
            for (PpmKqRdCalendar calendar : projectss.getRdCalendarList()) {
                if (!"Y".equals(calendar.getIsHoliday()) && "1".equals(calendar.getAttendanceOrno())) {
                    days = days.add(BigDecimal.ONE);
                    time = time.add(calendar.getAttendanceTime());
                }
            }
            if (time.compareTo(new BigDecimal("0")) == 0) {
                throw new BizException(ApiCode.ATTENDANCE_ERROR, projectss.getUsername() + projectss.getProjectName() + "考勤为空");
            }
            BigDecimal proportion = time.divide(days, 4, RoundingMode.HALF_UP);
            projectss.setProportion(proportion.doubleValue());
        }
        //对hrProjects以eid分组 得到新list
        List<List<HrProject>> collect = hrProjects.
                stream().collect(Collectors.groupingBy(HrProject::getEid)).values().
                stream().collect(Collectors.toList());
        for (List<HrProject> projects : collect) {
            BigDecimal allProportion = BigDecimal.ZERO;
            //校验每人每天考勤总时长<=1.0
            for (int i = 0; i < projects.get(0).getRdCalendarList().size(); i++) {
                BigDecimal allattendanceTime = BigDecimal.ZERO;
                String isHoliday = projects.get(0).getRdCalendarList().get(i).getIsHoliday();
                if ("N".equals(isHoliday)) {
                    for (HrProject project : projects) {

                        BigDecimal attendanceTime = project.getRdCalendarList().get(i).getAttendanceTime();
                        if (attendanceTime != null) {
                            allattendanceTime = allattendanceTime.add(attendanceTime);
//                        } else {
//                            throw new BizException(ApiCode.ATTENDANCE_ERROR, "工号：" + projects.get(0).getBadge() + " " +
//                                    "日期：" + project.getRdCalendarList().get(i).getDayOfMonth() + "号考勤工时校验异常");
                        }
                    }
                    if (allattendanceTime.compareTo(BigDecimal.ONE) > 0) {
                        throw new BizException(ApiCode.ATTENDANCE_ERROR, projects.get(0).getUsername() + "(" + projects.get(0).getBadge() + ")" +
                                "日期：" + projects.get(0).getRdCalendarList().get(i).getDayOfMonth() + "号考勤工时超过范围");
                    }
                }
            }
            //校验考勤人员性质   考勤占比
            //全职研发人员     100%
            //兼职研发人员1    [85%,100%)
            //兼职研发人员2    [70%-85%)
            //兼职研发人员3    [50%-70%]
            for (HrProject project : projects) {
                allProportion = allProportion.add(BigDecimal.valueOf(project.getProportion()));
                String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
                project.setAttendanceDate(attendanceDate);
                project.setId(project.getEidCode() + "_" + attendanceDate);
            }
            double lowerBound;
            double upperBound;
            boolean flot;
            switch (projects.get(0).getNature()) {
                case "0": {
                    upperBound = 1.0;
                    lowerBound = 1.0;
                    flot = allProportion.compareTo(BigDecimal.valueOf(lowerBound)) == 0;
                    break;
                }
                case "1": {
                    lowerBound = 0.85;
                    upperBound = 1.0;
                    flot = allProportion.compareTo(BigDecimal.valueOf(lowerBound)) >= 0 && allProportion.compareTo(BigDecimal.valueOf(upperBound)) < 0;
                    break;
                }
                case "2": {
                    lowerBound = 0.70;
                    upperBound = 0.85;
                    flot = allProportion.compareTo(BigDecimal.valueOf(lowerBound)) >= 0 && allProportion.compareTo(BigDecimal.valueOf(upperBound)) < 0;
                    break;
                }
                case "3": {
                    lowerBound = 0.50;
                    upperBound = 0.70;
                    flot = allProportion.compareTo(BigDecimal.valueOf(lowerBound)) >= 0 && allProportion.compareTo(BigDecimal.valueOf(upperBound)) <= 0;
                    break;
                }
                default:
                    flot = false;
                    break;
            }
            //校验通过
            if (flot) {
                HrProject hrProject = projects.get(0);

                CommRdAttendanceAudit department = CommRdAttendanceAudit.builder()
                        .id(hrProject.getEid() + "_" + format)
                        .eid(hrProject.getEid())
                        .badge(hrProject.getBadge())
                        .username(hrProject.getUsername())
                        .attendanceStatus("2")
                        .auditEmployee("")
                        .attendanceDate(format)
                        .build();
                Map<String, Object> map = JsonUtil.objectTOMap(department, CommRdAttendanceAudit.class);
                //是审核调用时 跳过插入程序
                if ("1".equals(hrProject.getAuditStatus())) {
                    myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.INSTER, "submitManagement");
                } else if ("3".equals(hrProject.getAuditStatus())) {
                    myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "submitAudit");
                }
            } else {
                throw new BizException(ApiCode.ATTENDANCE_ERROR, projects.get(0).getUsername() + "(" + projects.get(0).getBadge() + ")" + "考勤占比校验不通过");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitAudit(RdManagementHrProject rdManagementProject, String userid) {
        List<HrProject> hrProjects = rdManagementProject.getCommRdEmployeeDtoList();
        PpmKqRdCalendar ppmKqRdCalendar = rdManagementProject.getPpmKqRdCalendar();
        List<List<HrProject>> collect2 = hrProjects.
                stream().collect(Collectors.groupingBy(HrProject::getEid)).values().
                stream().collect(Collectors.toList());
//        List<CommRdAttendanceAudit> commRdEmployeeDtoList = new ArrayList<>();
//        Date calendarDate = hrProjects.get(0).getRdCalendarList().get(0).getCalendarDate();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
//        String format = simpleDateFormat.format(calendarDate);

        for (List<HrProject> hrProject : collect2) {
            CommRdAttendanceAudit commRdAttendanceAudit = CommRdAttendanceAudit.builder()
                    .eid(hrProject.get(0).getEid())
                    .attendanceStatus("4")
                    .attendanceDate(ppmKqRdCalendar.getAttendanceDate())
                    .auditEmployee(userid)
                    .build();
            Map<String, Object> map = JsonUtil.objectTOMap(commRdAttendanceAudit, CommRdAttendanceAudit.class);
            myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "submitAudit");
        }
    }

    @Override
    public void withdraw(RdManagementProject rdManagementProject) {
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("eid", commRdEmployeeDto.getEid());
            map.put("attendanceDate", attendanceDate);
            map.put("attendanceStatus", "3");
            myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "submitAudit");
        }
    }


    @Override
    public List<PpmRdHrProjectInfoExportDto> outputCurrent(RdManagementProject rdManagementProject) {
//        List<HrProject> hrProjects = this.currentSelectInfo(rdManagementProject);
        this.currentSelectInfo(rdManagementProject);

        return null;
    }

    @Override
    public List<List<HrProject>> attendanceAuditInfo(RdManagementProject rdManagementProject) {
        List<HrProject> hrProjects = this.attendanceSelectInfo(rdManagementProject);
        return new ArrayList<>(hrProjects.stream().collect(Collectors.groupingBy(
                HrProject::getBadge,
                LinkedHashMap::new,
                Collectors.toList()
        )).values());
//        return new ArrayList<>(hrProjects.stream().collect(Collectors.groupingBy(HrProject::getBadge)).values());
//        List<PpmRdHrProjectInfoExportDto> ppmRdHrProjectInfoExportDtos = new ArrayList<>();
//        for (HrProject hrProject : hrProjects) {
//            PpmRdHrProjectInfoExportDto ppmExportDto = new PpmRdHrProjectInfoExportDto();
//            ppmExportDto.setUsername(hrProject.getUsername());
//            ppmExportDto.setBadge(hrProject.getBadge());
//            ppmExportDto.setNature(hrProject.getNature());
//            ppmExportDto.setProjectCode(hrProject.getProjectCode());
//            ppmExportDto.setContractAmount(hrProject.getContractAmount());
//            ppmExportDto.setProjectName(hrProject.getProjectName());
//            ppmExportDto.setProjectState(hrProject.getProjectState());
//            ppmExportDto.setProportion(hrProject.getProportion());
//            ppmExportDto.setAttendanceDate(rdManagementProject.getPpmKqRdCalendar().getAttendanceDate());
//            for (PpmKqRdCalendar ppmKqRdCalendar : hrProject.getRdCalendarList()) {
//                int dayOfMonth = ppmKqRdCalendar.getDayOfMonth().intValue();
//                switch (dayOfMonth) {
//                    case 1: {
//                        ppmExportDto.setFirstOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 2: {
//                        ppmExportDto.setTwoOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 3: {
//                        ppmExportDto.setThreeOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 4: {
//                        ppmExportDto.setFourOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 5: {
//                        ppmExportDto.setFiveOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 6: {
//                        ppmExportDto.setSixOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 7: {
//                        ppmExportDto.setSevenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 8: {
//                        ppmExportDto.setEightOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 9: {
//                        ppmExportDto.setNineOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 10: {
//                        ppmExportDto.setTenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 11: {
//                        ppmExportDto.setElevenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 12: {
//                        ppmExportDto.setTwelveOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 13: {
//                        ppmExportDto.setThirteenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 14: {
//                        ppmExportDto.setFourteenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 15: {
//                        ppmExportDto.setFifteenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 16: {
//                        ppmExportDto.setSixteenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 17: {
//                        ppmExportDto.setSeventeenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 18: {
//                        ppmExportDto.setEighteenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 19: {
//                        ppmExportDto.setNineteenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 20: {
//                        ppmExportDto.setTwentyOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 21: {
//                        ppmExportDto.setTwentyOneOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 22: {
//                        ppmExportDto.setTwentyTwoOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 23: {
//                        ppmExportDto.setTwentyThreeOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 24: {
//                        ppmExportDto.setTwentyFourOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 25: {
//                        ppmExportDto.setTwentyFiveOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 26: {
//                        ppmExportDto.setTwentySixOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 27: {
//                        ppmExportDto.setTwentySevenOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 28: {
//                        ppmExportDto.setTwentyEightOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 29: {
//                        ppmExportDto.setTwentyNineOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 30: {
//                        ppmExportDto.setThirtyOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//                    case 31: {
//                        ppmExportDto.setThirtyOneOfMontn(ppmKqRdCalendar.getAttendanceTime());
//                        break;
//                    }
//
//                    default:
//                        break;
//                }
//            }
//            ppmRdHrProjectInfoExportDtos.add(ppmExportDto);
//        }
//
//        return JsonUtil.jsonToList(ppmRdHrProjectInfoExportDtos, PpmRdHrProjectInfoExportDto.class);
    }

    @Override
    public List<HrProject> attendanceAudit(RdManagementProject rdManagementProject) {
        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
        List<HrProject> dataList = new ArrayList<>();
        if (!commRdEmployeeDtoList.isEmpty()) {
            for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("eid", commRdEmployeeDto.getEid());
                hashMap.put("attendanceDate", attendanceDate);
                List<HrProject> list = myBatisDao.selectListT(COMMRDEMPLOYEE, hashMap, "attendanceAudit");
                java.util.List<HrProject> collect = list.stream().filter(item -> "0".equals(item.getIsdisabled())).collect(Collectors.toList());
                dataList.addAll(collect);
            }
            for (HrProject hrProject : dataList) {
                Map<String, Object> map1 = JsonUtil.objectTOMap(hrProject, HrProject.class);
                map1.put("attendanceDate", attendanceDate);
                List<PpmKqRdCalendar> dataList1 = myBatisDao.selectListT(COMMRDEMPLOYEE, map1, "ManagementProject");
                hrProject.setRdCalendarList(dataList1);
            }
            return dataList.stream().filter(item -> !ObjectUtils.isEmpty(item.getRdCalendarList())).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitTabulation(RdProjectAttendance rdProjectAttendance) {
        if (ObjectUtils.isEmpty(rdProjectAttendance.getAttendanceDate()) || ObjectUtils.isEmpty(rdProjectAttendance.getAttendanceStatus())) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "请求参数错误");
        }
        Map<String, Object> map = JsonUtil.objectTOMap(rdProjectAttendance, RdProjectAttendance.class);
        String attendanceDate = rdProjectAttendance.getAttendanceDate();
        final YearMonth parse = YearMonth.parse(attendanceDate);
        String firstDay = parse.atDay(1).toString();
        String lastDay = parse.atEndOfMonth().toString();
        map.put("firstDay", firstDay);
        map.put("lastDay", lastDay);
        List<HrProject> submitTabulation = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "submitTabulation");
        if (!ObjectUtils.isEmpty(submitTabulation)) {
            throw new BizException(ApiCode.ATTENDANCE_ERROR, "尚有员工未完成考勤或考勤为审核通过");
        }
        myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "atEditManagement");
        List<HrProject> hrProjects = myBatisDao.selectListT(COMMRDEMPLOYEE, map, "submitTabulationPlass");
        for (HrProject hrProject : hrProjects) {
            hrProject.setId(hrProject.getEidCode() + "_" + attendanceDate);
            hrProject.setAttendanceDate(attendanceDate);
        }
        try {
            myBatisDao.executionPojo(COMMRDEMPLOYEE, hrProjects, IMyBatisDao.SQL.INSTER, "submit");
        } catch (Exception e) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "该考勤已提交");
        }

    }

//    @Override
//    public void reject(RdManagementProject rdManagementProject) {
//        List<CommRdEmployeeDto> commRdEmployeeDtoList = rdManagementProject.getCommRdEmployeeDtoList();
//        String attendanceDate = rdManagementProject.getPpmKqRdCalendar().getAttendanceDate();
//        for (CommRdEmployeeDto commRdEmployeeDto : commRdEmployeeDtoList) {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("eid", commRdEmployeeDto.getEid());
//            map.put("attendanceDate", attendanceDate);
//            map.put("attendanceStatus", "2");
//            myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.UPDATE, "withdrawManagement");
//            myBatisDao.executionPojo(COMMRDEMPLOYEE, map, IMyBatisDao.SQL.DELETE, "withdraw");
//        }
//    }

}
