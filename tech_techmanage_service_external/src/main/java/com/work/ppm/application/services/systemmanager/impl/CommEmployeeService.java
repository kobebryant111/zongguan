package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.model.systemmanager.ext.CommEmployeeInfo;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.application.services.systemmanager.ICommParttimejobService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.CryptoUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 员工
 *
 */
@Slf4j
@Service("commEmployeeService")
public class CommEmployeeService implements ICommEmployeeService {
    /**
     * 实体类路径
     */
    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";
    private static String IMUSERROLEREL = "com.work.ppm.application.model.systemmanager.ImUserRoleRel";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;
    /**
     * 机构接口
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;
    /**
     * 行员兼职
     */
    @Autowired
    private ICommParttimejobService commParttimejobService;
    /**
     * 外包基本信息
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;

    @Override
    public void queryPager(CommEmployeeDto commEmployee, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployee, CommEmployeeDto.class);
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryPagerList");
    }

    @Override
    public void pageList(CommEmployee commEmployee, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployee, CommEmployee.class);
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryPagerList2");
    }

    @Override
    public CommEmployee queryDetail(CommEmployee commEmployee) {
        return myBatisDao.selectClass(COMMEMPLOYEE, commEmployee, "queryDetail");
    }

    @Override
    public Integer addCommEmployee(CommEmployee commEmployee) {
        return myBatisDao.executionPojo(COMMEMPLOYEE, commEmployee, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer syncOperator(CommEmployee commEmployee) {
        myBatisDao.executionPojo(COMMEMPLOYEE, commEmployee, IMyBatisDao.SQL.UPDATE, "updateOperator");
        return myBatisDao.executionPojo(COMMEMPLOYEE, commEmployee, IMyBatisDao.SQL.INSTER, "syncOperator");
    }

    @Override
    public Integer updateCommEmployee(CommEmployee commEmployee) {
        return myBatisDao.executionPojo(COMMEMPLOYEE, commEmployee, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteCommEmployee(CommEmployee commEmployee) {
        return myBatisDao.executionPojo(COMMEMPLOYEE, commEmployee, IMyBatisDao.SQL.DELETE, "deletePojo");
    }

    @Override
    public void queryRoleUserRelByPage(CommEmployeeDto commEmployee, Pager pager) {
        commEmployee.setSysType(2);
        Map<String, Object> map = StringUtil.objectTOMap(commEmployee, CommEmployeeDto.class);
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryRoleUserRel");
    }

    @Override
    public String getTokenMd5(String badge, String ip) {
        if (!StringUtil.isStringNotEmpty(badge)) {
            throw new BizException(ApiCode.PARAMETER_ERROR, "[用户编号]不能为空");
        }
        String md5 = CryptoUtil.digestByMD5(badge + ":" + ip + ":" + DateUtil.dateToNoBar(new Date()));
        log.debug("md5::" + md5);
        return md5;
    }

    @Override
    public void queryCrewPageList(CommEmployeeDto commEmployeeDto, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        // 业务处理
        String dep = null == commEmployeeDto.getDep() ? "" : commEmployeeDto.getDep();
        if (dep.contains("GRP") || dep.contains("TEAM")) {
            map.put("gro", commEmployeeDto.getDep().replaceAll("gro", ""));
            map.put("depid", "");
        } else if (dep.contains("dep")) {
            map.put("depid", commEmployeeDto.getDep().replaceAll("dep", ""));
        }
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryCrewPageList");
        List<CommEmployeeInfo> list = (List<CommEmployeeInfo>) pager.getList();
        list.stream().forEach(item -> {
            item.setEmpName(item.getEmpName() + "(" + item.getPersonInCharge() + ")");
        });
    }

    @Override
    public String getDeptNo(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        String deptid = myBatisDao.selectClass(COMMEMPLOYEE, map, "getDeptNo");
        return deptid;
    }

    @Override
    public String queryDeptUserInfoByUserId(String userId, String roleId) {
        Map<String, Object> map = new HashMap<>();
//        if("k".contains(userId.toLowerCase()) || "l".equals(userId.toLowerCase())) {
//            map.put("flag", "waibao");
//        } else {
//            map.put("flag", "hangyuan");
//        }
        CommDepartment dept = commDepartmentService.queryDepgradeByUserId(userId);
        if (null == dept) {
            return null;
        }
        List<CommEmployee> empList = null;
        if ("IT0010".equals(roleId)) {
            map.put("userId", userId);
            if ("001".equals(dept.getDepgrade())) {
                empList = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryUseridByDepgrade");
            } else {
                empList = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryUseridByDepgrade1");
            }
        }
        StringBuilder sb = new StringBuilder(empList.size());
        if (null != empList && !empList.isEmpty()) {
            for (CommEmployee item : empList) {
                sb.append(item.getBadge()).append(",");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    @Override
    public String queryUserIdByRole(String roleId) {
        String users = null;
        Map<String, Object> map = new HashMap<>();
        map.put("rolId", roleId);
        List<CommEmployee> emList = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryRoleUserRel");

        if (null != emList && !emList.isEmpty()) {
            StringBuilder sb = new StringBuilder(emList.size());
            for (CommEmployee item : emList) {
                sb.append(item.getBadge()).append(",");
            }
            users = sb.toString().substring(0, sb.toString().length() - 1);
        }
        return users;
    }

    @Override
    public List<CommEmployeeDto> getEmployeeList(CommEmployeeDto commEmployeeDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "getEmployeeComponent");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommTreeDto> queryEmployeeTree(CommTreeDto commTreeDto) {
        List<CommTreeDto> treeDtos = null;
        // 搜索
        if (StringUtil.isNotBlank(commTreeDto.getSearchUser())) {
            Map<String, Object> map = new HashMap<>();
            map.put("searchUser", commTreeDto.getSearchUser());
            List<CommTree> trees = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryBankUser");
            if (null == trees || trees.isEmpty()) {
                return null;
            }
            List<CommTreeDto> treeDtoList = JsonUtil.jsonToList(trees, CommTreeDto.class);
            return treeDtoList;
        }

        // 第一次加载，机构
        if (null == commTreeDto.getId()) {
            treeDtos = commOrganizationService.queryOrgTreeComponents();
        } else if ("org".equals(commTreeDto.getCurrent())) {  // 机构
            if ("org".equals(commTreeDto.getNext())) {
                treeDtos = commOrganizationService.queryOrgTree(commTreeDto.getId());
            } else {
                treeDtos = commDepartmentService.getDeptByCompId(commTreeDto.getCurrent(), commTreeDto.getId(),
                        commTreeDto.getNext());
            }
        } else if ("dep".equals(commTreeDto.getCurrent())) {  // 部门
            if ("dep".equals(commTreeDto.getNext())) {
                treeDtos = commDepartmentService.getDeptByCompId(commTreeDto.getCurrent(), commTreeDto.getId(), true);
            } else {
                treeDtos = this.queryEmployeeList(commTreeDto.getId(), commTreeDto.getNext());
            }
        }
        recursion(treeDtos);
        dataProcess(treeDtos);
        return treeDtos;
    }

    @Override
    public List<CommTreeDto> queryEmployeeList(String id, String next) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("next", next);
        List<CommTree> trees = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryEmployeeList");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(trees, CommTreeDto.class);
        return treeDtos;
    }

    @Override
    public CommEmployeeDto getTopWorkManager(String deptId) {
        CommDepartment commDepartment = new CommDepartment();
        commDepartment.setDepid(Long.parseLong(deptId));
        CommDepartment department = commDepartmentService.queryDetail(commDepartment);
        if ("004".equals(department.getDepgrade())) {
            deptId = String.valueOf(department.getAdminid());
        }
        return getTwoDeptWorkManager(deptId);
    }

    @Override
    public CommEmployeeDto getTwoDeptWorkManager(String deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("deptId", deptId);
        CommEmployeeInfo item = myBatisDao.selectClass(COMMEMPLOYEE, map, "getDeptWorkManager");
        if (null != item) {
            // 如果有个为空 则取username和userid
            if (!StringUtil.isNotBlank(item.getEmpname()) || !StringUtil.isNotBlank(item.getBadge())) {
                item.setEmpname(item.getUsername());
                item.setBadge(item.getUserid());
            }
        }
        CommEmployeeDto dto = JsonUtil.jsonToClass(item, CommEmployeeDto.class);
        return dto;
    }

    /**
     * 根据id查询信息
     *
     * @param id 编号
     * @return int
     */
    private int hasEmp(String id) {
        Map<String, Object> hashEmpMap = new HashMap<>();
        hashEmpMap.put("id", id);
        Long count = myBatisDao.selectCount(COMMEMPLOYEE, hashEmpMap, "hasEmp");
        return null != count ? count.intValue() : 0;
    }

    /**
     * 递归处理数据
     *
     * @param root
     */
    private void recursion(List<CommTreeDto> root) {
        if (null == root) {
            return;
        }

        for (int i = root.size() - 1; i >= 0; i--) {
            recursion(dataProcess(root.get(i).getChildren()));
            if (null == root.get(i).getChildren()) {
                root.get(i).setChildren(new ArrayList<>());
            }
        }
    }

    /**
     * 数据处理
     *
     * @param treeDtos
     * @return List<CommTreeDto>
     */
    private List<CommTreeDto> dataProcess(List<CommTreeDto> treeDtos) {
        if (null == treeDtos) {
            return null;
        }
        treeDtos.stream().forEach(item -> {
            if ("org".equals(item.getAttr() + "")) {
                int count = commOrganizationService.hasOrg(item.getId());
                if (count == 0) {
                    int retCount = commDepartmentService.hasDepCount(null, item.getId());
                    if (retCount == 0) {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-none"));
                    } else {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-dep"));
                    }
                } else {
                    int retCount = commDepartmentService.hasDepCount(null, item.getId());
                    if (retCount > 0) {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-org,dep"));
                    } else {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-org"));
                    }
                }
            } else if ("dep".equals(item.getAttr() + "")) {
                int retCount = commDepartmentService.hasDepCount(item.getId(), null);
                if (retCount == 0) {
                    int count = this.hasEmp(item.getId());
                    if (count == 0) {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-none"));
                    } else {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-emp"));
                    }
                } else {
                    int count = this.hasEmp(item.getId());
                    if (count > 0) {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-dep,emp"));
                    } else {
                        item.setAttr(item.getId().concat("-").concat(item.getAttr()).concat("-dep"));
                    }
                }
            }
        });
        return treeDtos;
    }

    @Override
    public CommEmployee getCommEmployeeInfo(String badge) {
        Map<String, Object> map = new HashMap<>();
        map.put("badge", badge);
        return myBatisDao.selectClass(COMMEMPLOYEE, map, "queryCommemployeeInfoByBadge");

    }

    @Override
    public List<CommEmployeeDto> queryOnTheJobUsers() {
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "queryOnTheJobUsers");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> queryBusDepUsers() {
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "queryBusDepUsers");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> queryResignUser() {
        List<CommEmployeeInfo> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "queryResignUser");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> queryEmployUser(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", username);
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryEmployUser");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<PpmOutsourcingBasicExcelDto> exportOutsourcing(PpmOutsourcingBasicExcelDto ppmOutsourcingBasicInfodto,
                                                               CommOperatorDoLoginDto user) {

        List<PpmOutsourcingBasicExcelDto> list =
                ppmOutsourcingBasicService.exportOutsourcing(ppmOutsourcingBasicInfodto, user);
        return list;
    }

    @Override
    public List<CommEmployeeDto> getDepManagers() {
        List<CommEmployeeInfo> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "getDepManagers");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> getDepManager() {
        List<CommEmployeeInfo> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "getDepManager");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> getManagers() {
        List<CommEmployeeInfo> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "getManagers");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> ministryManagers(String deptId) {
        CommEmployee employee = new CommEmployee();
        employee.setDepid(Long.valueOf(deptId));
        List<CommEmployeeInfo> list = myBatisDao.selectListT(COMMEMPLOYEE, employee, "queryMinistryManagers");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public String getBadgeAndName(String userIds) {
        if (!StringUtil.isNotBlank(userIds)) {
            return null;
        }
        String[] idStr = userIds.split(",");
        StringJoiner joiner = new StringJoiner("','", "'", "'");
        for (int i = idStr.length - 1; i >= 0; i--) {
            joiner.add(idStr[i]);
        }
        CommEmployee employee = new CommEmployee();
        employee.setBadge(joiner.toString());
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, employee, "getBadgeAndName");
        if (null != list && !list.isEmpty()) {
            StringJoiner joiner1 = new StringJoiner(",");
            list.stream().forEach(ii -> {
                joiner1.add(ii.getEmpname() + "(" + ii.getBadge() + ")");
            });
            return joiner1.toString();
        }
        return null;
    }

    @Override
    public String getFhTechManager(String deptId) {
        CommEmployeeInfo employee = new CommEmployeeInfo();
        employee.setAdminid(deptId);
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, employee, "getFhTechManager");
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(0).getBadge();
    }

    @Override
    public String getAreDeptManager() {
        CommEmployee item = myBatisDao.selectClass(COMMEMPLOYEE, null, "getAreDeptManager");
        return null == item ? null : item.getBadge();
    }

    @Override
    public CommEmployeeDto getFgManageByDepid(String deptId, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("depid", deptId);
        map.put("state", type);
        String uniqueName = "getFgManageByDepid";
        CommEmployee item = myBatisDao.selectClass(COMMEMPLOYEE, map, uniqueName);
        CommEmployeeDto dto = JsonUtil.jsonToClass(item, CommEmployeeDto.class);
        return dto;
    }

    @Override
    public List<CommEmployeeDto> getEmployeeLists(CommEmployeeDto commEmployeeDto) {
        Map<String, Object> map = JsonUtil.objectTOMap(commEmployeeDto, CommEmployeeDto.class);
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "getList");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public List<CommEmployeeDto> queryFhKjUsers() {
        List<CommEmployee> list = myBatisDao.selectListT(COMMEMPLOYEE, null, "queryFhKjUsers");
        List<CommEmployeeDto> dtos = JsonUtil.jsonToList(list, CommEmployeeDto.class);
        return dtos;
    }

    @Override
    public void queryOperaRoleUserRelByPage(CommEmployeeDto commEmployee, Pager pager) {
        commEmployee.setSysType(2);
        Map<String, Object> map = StringUtil.objectTOMap(commEmployee, CommEmployeeDto.class);
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryOperaRoleUserRel");
    }

    @Override
    public List<String> queryOperaAuthEncoding(String  userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<String> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryOperaAuthEncoding");
        return list;
    }

    @Override
    public String getUserOperaAuthEncoding(String userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        List<String> list = myBatisDao.selectListT(COMMEMPLOYEE, map, "getUserOperaAuthEncoding");
        StringBuilder sb = new StringBuilder();
        for (String everyCoding : list) {
            sb.append(everyCoding).append(",");
        }
        return sb.toString();
    }

    @Override
    public void queryResearchManagerEmployee(CommEmployeeDto commEmployee, Pager pager) {
        List<String> userIdList = myBatisDao.selectListT(IMUSERROLEREL, null, "getUserRole");
        if (userIdList == null && userIdList.size() == 0) {
            return ;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < userIdList.size(); i++) {
            stringBuilder.append("'").append(userIdList.get(i)).append("'");
            if (i < userIdList.size() - 1) {
                stringBuilder.append(",");
            } else{
                stringBuilder.append(")");
            }
        }
        Map<String, Object> map = StringUtil.objectTOMap(commEmployee, CommEmployeeDto.class);
        if (commEmployee.getBadge() != null && StringUtil.isStringNotEmpty(commEmployee.getBadge())) {
            StringBuilder sb = new StringBuilder();
            sb.append("(").append("'").append(commEmployee.getBadge()).append("'").append(")");
            map.put("badges" , sb.toString());
        }else{
            map.put("badges" , stringBuilder.toString());
        }
        myBatisDao.selectPager(COMMEMPLOYEE, map, pager, "queryResearchManagerEmployeeList");
    }
}
