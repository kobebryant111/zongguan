package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.CommOrganizationDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.ext.CommDepartmentInfo;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOrganizationService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.AssertUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.UserSessionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门
 *
 */
@Service("commDepartmentService")
public class CommDepartmentService implements ICommDepartmentService {
    /**
     * 实体类路径
     */
    private static String COMMDEPARTMENT = "com.work.ppm.application.model.systemmanager.CommDepartment";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 机构
     */
    @Autowired
    private ICommOrganizationService commOrganizationService;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 人员接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Override
    public void queryPager(CommDepartment commDepartment, Pager pager) {
        Map<String, Object> map = StringUtil.objectTOMap(commDepartment, CommDepartment.class);
        myBatisDao.selectPager(COMMDEPARTMENT, map, pager, "queryPagerList");
    }

    @Override
    public CommDepartment queryDetail(CommDepartment commDepartment) {
        return myBatisDao.selectClass(COMMDEPARTMENT, commDepartment, "queryDetail");
    }

    @Override
    public Integer addCommDepartment(CommDepartment commDepartment) {
        return myBatisDao.executionPojo(COMMDEPARTMENT, commDepartment, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer updateCommDepartment(CommDepartment commDepartment) {
        return myBatisDao.executionPojo(COMMDEPARTMENT, commDepartment, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public Integer deleteCommDepartment(CommDepartment commDepartment) {
        return myBatisDao.executionPojo(COMMDEPARTMENT, commDepartment, IMyBatisDao.SQL.DELETE, "deletePojo");
    }

    @Override
    public List<CommDepartmentDto> queryListByCompId(String compId) {
        CommDepartment entity = new CommDepartment();
        entity.setCompid(Long.parseLong(compId));
        List<CommDepartment> list = myBatisDao.selectListT(COMMDEPARTMENT, entity, "getList");
        List<CommDepartmentDto> newList = JsonUtil.jsonToList(list, CommDepartmentDto.class);
        return newList;
    }

    @Override
    public List<CommTreeDto> queryDeptTree(String code, String grade) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("grade", grade);
        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryDeptTree");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        // 递归 组织树
        List<CommTreeDto> newTreeList = CommTreeDto.recursiveListToTree(dtoList);
        return newTreeList;
    }

    @Override
    public List<CommTreeDto> queryDeptTreeQt(String code,String deptId,String grade){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("pidVal", "'dep" + deptId + "'");
        map.put("grade", grade);
        map.put("depIdVal", deptId);
        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryDeptTreeQt");
        list.removeIf(item->item.getId().equals("dep10905"));
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        // 递归 组织树
        List<CommTreeDto> newTreeList = CommTreeDto.recursiveListToTree(dtoList);
        return newTreeList;
    }

    @Override
    public CommDepartment queryDepgradeByUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return myBatisDao.selectClass(COMMDEPARTMENT, map, "queryDepgradeByUserId");
    }

    @Override
    public List<CommTreeDto> queryAuth(String deptNo, String userData, String users) {
        Map<String, Object> map = new HashMap<>();
        map.put("badge", users);
        map.put("userData", userData);
        map.put("deptNo", deptNo);

        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryResourceStaff");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        List<CommTreeDto> treeList = CommTreeDto.recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<CommTreeDto> queryAuth(int compid, String userData, String users) {
        Map<String, Object> map = new HashMap<>();
        map.put("badge", users);
        map.put("userData", userData);
        map.put("adminId", compid);

        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryResourceStaff");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        List<CommTreeDto> treeList = CommTreeDto.recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<CommTreeDto> queryAuth(String adminId) {
        Map<String, Object> map = new HashMap<>();
        map.put("adminId", adminId);
        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryResourceStaff");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        List<CommTreeDto> treeList = CommTreeDto.recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<CommTreeDto> queryResourceDept(CommOperatorDoLoginDto user, CommDepartmentDto commDepartmentDto) {
        //将写死的一级部门放开
        CommDepartment commDepartment = queryFirstDept(user.getUserid());
        if(commDepartment != null){
//            commDepartmentDto.setObjectcode(commDepartment.getDepcode());
            //将一级部门写死
            commDepartmentDto.setObjectcode("3540001900");
        }

        Map<String, Object> map = JsonUtil.objectTOMap(commDepartmentDto, CommDepartmentDto.class);
        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryResourceDept");
//        list.removeIf(item->item.getId().equals("dep10905"));
        // 是否需要鉴定权限
        if (CollectionUtils.isNotEmpty(list) && commDepartmentDto.isAuth()) {
            List<ImRoleDoLoginDto> roles = user.getRoles();
            boolean admin = roles.stream().anyMatch(i -> "00000001".equals(i.getRoleId()));

            if (StringUtils.isNotBlank(commDepartmentDto.getAllowDept())) {
                String[] allowDeptIds = commDepartmentDto.getAllowDept().split(",");
                boolean allowDept = Arrays.stream(allowDeptIds).anyMatch(i -> user.getDeptId().equals(i));
                if (!admin && !allowDept) {
                    list = list.stream().filter(i -> i.getId().contains(user.getDeptId())).collect(Collectors.toList());
                }
            }

        }
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        List<CommTreeDto> treeList = CommTreeDto.recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<CommTreeDto> getDeptByCompId(String type, String id, String next) {
        return this.getDeptByCompId(id, next);
    }

    @Override
    public List<CommTreeDto> getDeptByCompId(String type, String id, boolean isAdmin) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("id", id);
        map.put("admin", isAdmin);
        List<CommTree> trees = myBatisDao.selectListT(COMMDEPARTMENT, map, "getDeptByCompId");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(trees, CommTreeDto.class);
        return treeDtos;
    }

    @Override
    public int hasDepCount(String adminid, String compid) {
        Map<String, Object> map = new HashMap<>();
        map.put("adminid", adminid);
        map.put("compid", compid);
        Long count = myBatisDao.selectCount(COMMDEPARTMENT, map, "hasDepCount");
        return null != count ? count.intValue() : 0;
    }

    @Override
    public String getDetpIdForDeptName(String deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("deptId", deptId);
        CommDepartment item = myBatisDao.selectClass(COMMDEPARTMENT, map, "getDetpIdForDeptName");
        return item.getLocation();
    }

    /**
     * 部门树
     *
     * @param id   id
     * @param next 标识
     * @return List<CommTreeDto>
     */
    private List<CommTreeDto> getDeptByCompId(String id, String next) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("next", next);
        map.put("admin", false);
        List<CommTree> trees = myBatisDao.selectListT(COMMDEPARTMENT, map, "getDeptByCompId");
        List<CommTreeDto> treeDtos = JsonUtil.jsonToList(trees, CommTreeDto.class);
        return treeDtos;
    }

    @Override
    public List<CommDepartment> queryHeadOffice() {
        Map<String, Object> map = new HashMap<>();
        List<CommDepartment> trees = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryHeadOffice");
        return trees;
    }

    @Override
    public List<CommDepartment> queryDirectDeps(String badge) {
        Map<String, Object> map = new HashMap<>();
        map.put("badge", badge);
        List<CommDepartment> trees = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryDirectDeps");
        return trees;
    }

    @Override
    public List<CommDepartment> queryList(CommDepartment commDepartment) {
        List<CommDepartment> deps = myBatisDao.selectListT(COMMDEPARTMENT, commDepartment, "getList");
        return deps;
    }

    @Override
    public String getDepNameForId(String depName) {
        CommDepartment department = new CommDepartment();
        department.setLocation(depName);
        CommDepartment item = myBatisDao.selectClass(COMMDEPARTMENT, department, "getDepNameForId");
        if (null == item) {
            return null;
        }
        return item.getDepid() + "";
    }

    @Override
    public String queryDeptIdForDeptName(String depName) {
        CommDepartment department = new CommDepartment();
        department.setLocation(depName);
        CommDepartment item = this.queryDetail(department);
        return null == item ? null : item.getDepid() + "";
    }

    @Override
    public CommDepartmentDto queryDeptIdForCompId(String compid) {
        CommDepartment department = new CommDepartment();
        department.setCompid(Long.parseLong(compid));
        department.setLocation("行长室");
        CommDepartment item = myBatisDao.selectClass(COMMDEPARTMENT, department, "queryDetail");
        CommDepartmentDto dto = JsonUtil.jsonToClass(item, CommDepartmentDto.class);
        return dto;
    }

    @Override
    public CommDepartmentDto queryParentDeptId(String deptId) {
        CommDepartment dep = new CommDepartment();
        dep.setDepid(Long.parseLong(deptId));
        CommDepartment item = this.queryDetail(dep);
        String admin = "";
        if (null == item) {
            throw new BizException(ApiCode.FAIL, "部门信息异常");
        }
        if (StringUtil.isNotEmpty(item.getAdminid())) {
            admin = item.getAdminid() + "";
        }
        int index = 0;
        // 父级部门id
        while (StringUtil.isNotBlank(admin)) {
            item = this.queryDetail(dep);
            if (!StringUtil.isNotEmpty(item)) {
                admin = item.getAdminid() + "";
            } else {
                admin = "";
            }
            index++;
            if (index >= 20) {
                break;
            }
        }
        CommDepartmentDto ditem = JsonUtil.jsonToClass(item, CommDepartmentDto.class);
        return ditem;
    }

    @Override
    public CommDepartmentDto queryManagerByDepid(String deptId) {
        AssertUtil.notNull(deptId, "[deptId] 不能为空");
        CommDepartment department = new CommDepartment();
        department.setDepid(Long.parseLong(deptId));

        CommDepartmentInfo item = myBatisDao.selectClass(COMMDEPARTMENT, department, "queryManagerByDepid");
        // //主持经理
        if (!StringUtil.isNotBlank(item.getBadge())) {
            CommEmployeeDto dto = commEmployeeService.getFgManageByDepid(deptId, 1);
            item.setBadge(dto.getBadge());
            item.setEmpname(dto.getEmpname());
        }
        //分管总经理
        if (!StringUtil.isNotBlank(item.getUserid())) {
            CommEmployeeDto dto = commEmployeeService.getFgManageByDepid(deptId, 2);
            item.setUserid(dto.getBadge());
            item.setUsername(dto.getEmpname());
        }
        CommDepartmentDto dto = JsonUtil.jsonToClass(item, CommDepartmentDto.class);
        return dto;
    }

    @Override
    public String getFhITManager(String compcode) {
        String orgs = eosDictEntryService.getTypeidForDetail("FHORG", "org").getDictname();
        CommOrganizationDto departmentDto = commOrganizationService.queryDateilForCompCode(compcode);
        if (!StringUtil.isNotEmpty(departmentDto.getCompid())) {
            return "";
        }

        boolean flag = orgs.contains(compcode);
        Map<String, Object> map = new HashMap<>();
        if (flag) {
            // 分行科技部, 分行金融科技部
            map.put("compid", departmentDto.getCompid());
            map.put("status", 1);
        } else {
            // 分行运营部
            map.put("compid", departmentDto.getCompid());
            map.put("status", 2);
        }
        CommDepartment cd = myBatisDao.selectClass(COMMDEPARTMENT, map, "getFhITManager");
        if (null == cd) {
            // 运营部
            map.put("compid", departmentDto.getCompid());
            map.put("status", 3);
            cd = myBatisDao.selectClass(COMMDEPARTMENT, map, "getFhITManager");
        }
        return cd.getDepid() + "";
    }

    @Override
    public String getFhYybManager(String compcode) {
        String orgs = eosDictEntryService.getTypeidForDetail
                ("FHORG", "org").getDictname();
        CommOrganizationDto orgDto = commOrganizationService
                .queryDateilForCompCode(compcode);
        if (!StringUtil.isNotEmpty(orgDto.getCompid())) {
            return "";
        }

        boolean flag = orgs.contains(compcode);
        Map<String, Object> map = new HashMap<>();
        if (flag) {
            // 分行科技部-总经理室 分行金融科技部-总经理室
            map.put("compid", orgDto.getCompid());
            map.put("status", 4);
        } else {
            // 运营部-总经理室
            map.put("compid", orgDto.getCompid());
            map.put("status", 5);
        }
        CommDepartment cd = myBatisDao.selectClass(COMMDEPARTMENT, map, "getFhITManager");
        return cd.getDepid() + "";
    }

    @Override
    public CommDepartmentDto getCompcodeForDepId(String compcode) {
        Map<String, Object> map = new HashMap<>();
        map.put("compCode", compcode);
        CommDepartment item = myBatisDao.selectClass(COMMDEPARTMENT, map, "getCompcodeForDepId");
        CommDepartmentDto dto = JsonUtil.jsonToClass(item, CommDepartmentDto.class);
        return dto;
    }

    @Override
    public CommDepartmentInfo getManagerByDepid(String depno) {
        CommDepartment department = new CommDepartment();
        department.setLocation(depno);
        CommDepartmentInfo item = myBatisDao.selectClass(COMMDEPARTMENT, department, "getDepNameForId");
        if (null == item) {
            return null;
        }
        return item;
    }

    @Override
    public CommDepartmentDto queryFhKjbManager(int compid) {
        Map<String, Object> map = new HashMap<>();
        map.put("compid", compid);
        CommDepartment item = myBatisDao.selectClass(COMMDEPARTMENT, map, "queryFhKjbManager");
        CommDepartmentDto dto = JsonUtil.jsonToClass(item, CommDepartmentDto.class);
        return dto;
    }

    @Override
    public List<CommDepartment> query2ndBy1stDept(String deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("depId", deptId);
        List<CommDepartment> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "query2ndBy1stDept");
        return list;
    }

    @Override
    public CommDepartment queryFirstDept(String userId) {
        CommEmployee commEmployee = commEmployeeService.getCommEmployeeInfo(userId);
        if(!UserSessionUtil.isQueryMgr()){
            Map<String, Object> map = new HashMap<>();
            map.put("depid", commEmployee.getDepid());
            CommDepartment commDepartment2 = myBatisDao.selectClass(COMMDEPARTMENT, map, "queryByDeptId");
            commEmployee.setDepid(commDepartment2.getAdminid());
        }else{
            commEmployee.setDepid(10684L);
        }
        if(commEmployee != null){
            CommDepartment commDepartment = new CommDepartment();
            commDepartment.setDepid(commEmployee.getDepid());
            commDepartment = queryDetail(commDepartment);
            if(commDepartment != null){
                CommDepartment commDepartment1 = new CommDepartment();
                commDepartment1.setDepid(commDepartment.getDepid());
                return queryDetail(commDepartment1);
            }
        }

        return null;
    }

    @Override
    public List<CommTreeDto> queryAuthList(String deptNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("deptNo", deptNo);

        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryEmpList");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        List<CommTreeDto> treeList = CommTreeDto.recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<CommTreeDto> queryMsgList() {
        Map<String, Object> map = new HashMap<>();
        map.put("urrRoleid", "InterviewExpertperson");
        List<CommTree> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryMsgList");
        List<CommTreeDto> dtoList = JsonUtil.jsonToList(list, CommTreeDto.class);
        List<CommTreeDto> treeList = CommTreeDto.recursiveListToTree(dtoList);
        return treeList;
    }

    @Override
    public List<CommDepartment> queryDep(String empId) {
        Map<String, Object> map = new HashMap<>();
        map.put("badge", empId);
        List<CommDepartment> list = myBatisDao.selectListT(COMMDEPARTMENT, map, "queryDepByBadge");
        return list;
    }
}
