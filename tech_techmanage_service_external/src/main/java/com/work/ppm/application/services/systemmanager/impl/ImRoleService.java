package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDto;
import com.work.ppm.application.dto.systemmanager.PpmSpecialRoleDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBasic;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.services.integratedmanager.IPpmOutsourcingBasicService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.application.services.systemmanager.IPpmSpecialRoleService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleTreeDto;
import com.work.ppm.application.model.systemmanager.ImRole;
import com.work.ppm.application.services.systemmanager.IImPermissionsService;
import com.work.ppm.application.services.systemmanager.IImRoleService;
import com.work.ppm.application.quartz.interfaces.IImRoleJobService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色接口
 *
 */
@Service("imRoleService")
public class ImRoleService implements IImRoleService, IImRoleJobService {
    /**
     * 实体类路径
     */
    private static String IMROLE = "com.work.ppm.application.model.systemmanager.ImRole";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 角色菜单关联接口
     */
    @Autowired
    private IImPermissionsService imPermissionsService;
    /**
     * 用户角色接口
     */
    @Autowired
    private IImUserRoleRelService imUserRoleRelService;
    /**
     * 员工
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 外包人员基础接口
     */
    @Autowired
    private IPpmOutsourcingBasicService ppmOutsourcingBasicService;
    /**
     * 特殊角色菜单关联
     */
    @Autowired
    private IPpmSpecialRoleService ppmSpecialRoleService;

    @Override
    public void queryPager(ImRole imRole, Pager pager) {
        imRole.setSysType(1);
        Map<String, Object> map = StringUtil.objectTOMap(imRole, ImRole.class);
        myBatisDao.selectPager(IMROLE, map, pager, "queryPagerList");
    }

    @Override
    public ImRole queryDetail(ImRole imRole) {
        imRole.setSysType(1);
        return myBatisDao.selectClass(IMROLE, imRole, "queryDetail");
    }

    @Override
    public int updateImRole(ImRoleDto imRole) {
        imRole.setSysType(1);
        if ("add".equals(imRole.getSqlStatus())) {
            imRole.setRolCreateTime(new Date());
            imRole.setRolGroupId("");
            // 查询记录是否有重复
            ImRole role = new ImRole();
            role.setRolId(imRole.getRolId());
            role.setSysType(1);
            ImRole item = this.queryDetail(role);
            if (null != item) {
                throw new BizException(ApiCode.FAIL, "角色编号已存在，请重新输入");
            }
            // 添加
            myBatisDao.executionPojo(IMROLE, imRole, IMyBatisDao.SQL.INSTER, "addPojo");
            return 1;
        }
        // 更新
        myBatisDao.executionPojo(IMROLE, imRole, IMyBatisDao.SQL.UPDATE, "updatePojo");
        if(0 == imRole.getFalgSpecialRole().intValue()) {
            PpmSpecialRoleDto roleDto = new PpmSpecialRoleDto();
            roleDto.setRoleId(imRole.getRolId());
            ppmSpecialRoleService.addPpmSpecialRole(roleDto);
        } else {
            PpmSpecialRoleDto roleDto = new PpmSpecialRoleDto();
            roleDto.setRoleId(imRole.getRolId());
            ppmSpecialRoleService.deletePpmSpecialRole(roleDto);
        }
        return 2;
    }

    @Override
    public Integer deleteImRole(ImRole imRole) {
        imRole.setSysType(1);
        int i = myBatisDao.executionPojo(IMROLE, imRole, IMyBatisDao.SQL.DELETE, "deleteById");
        if(i > 0) {
            PpmSpecialRoleDto dto = new PpmSpecialRoleDto();
            dto.setRoleId(imRole.getRolId());
            ppmSpecialRoleService.deletePpmSpecialRole(dto);
        }

        return i;
    }

    @Override
    public List<ImRoleDoLoginDto> getImRole(String userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userid);
        map.put("sysType", 1);
        List<ImRole> list = myBatisDao.selectListT(IMROLE, map, "getImRoleForUserid");
        List<ImRoleDoLoginDto> roleListDto = new ArrayList<>();
        if (null != list && !list.isEmpty()) {
            ImRoleDoLoginDto roleDto = null;
            for (ImRole item : list) {
                if (null == item) {
                    continue;
                }
                roleDto = new ImRoleDoLoginDto();
                roleDto.setRoleId(item.getRolId());
                roleDto.setRoleName(item.getRolName());
                roleDto.setFalgSpecialRole(item.getFalgSpecialRole());
                roleListDto.add(roleDto);
            }
        }
        return roleListDto;
    }

    @Override
    public List<ImRoleTreeDto> queryRoleTree() {
        Map<String, Object> map = new HashedMap();
        map.put("sysType", 1);
        List<ImRole> roleList = myBatisDao.selectListT(IMROLE, map, "getList");
        List<ImRoleTreeDto> dtoList = JsonUtil.jsonToList(roleList, ImRoleTreeDto.class);
        List<ImRoleTreeDto> treeList = recursiveListTree(dtoList);
        return treeList;
    }

//  ------ 递归角色树 begin -----

    /**
     * 递归树
     *
     * @param treeDtos 节点
     * @return List<ImRoleTreeDto>
     */
    private static List<ImRoleTreeDto> recursiveListTree(List<ImRoleTreeDto> treeDtos) {
        boolean existRootNode = false;
        List<ImRoleTreeDto> treeDtos1 = new ArrayList<>();
        for (ImRoleTreeDto item : treeDtos) {
            if (isRootNode(item, treeDtos)) {
                treeDtos1.add(findChildren(item, treeDtos));
                existRootNode = true;
            }
        }
        if (!existRootNode) {
            return treeDtos;
        }
        return treeDtos1;
    }

    /**
     * 递归查找子节点
     *
     * @param parentNode 节点
     * @param treeNodes 所有节点
     * @return  ImRoleTreeDto
     */
    private static ImRoleTreeDto findChildren(ImRoleTreeDto parentNode, List<ImRoleTreeDto> treeNodes) {
        List<ImRoleTreeDto> chilren = new ArrayList<>();
        for (ImRoleTreeDto it : treeNodes) {
            // 找子节点，判断 parentNode是不是有儿子
            if (parentNode.getRolId().equals(it.getRolParentId())) {
                chilren.add(findChildren(it, treeNodes));
                parentNode.setChildren(chilren);
            }
        }
        return parentNode;
    }

    /**
     * 判断节点是否有根节点
     *
     * @param item 节点
     * @param treeDtos 所有节点
     * @return  boolean
     */
    private static boolean isRootNode(ImRoleTreeDto item, List<ImRoleTreeDto> treeDtos) {
        for (ImRoleTreeDto dto : treeDtos) {
            // 判断item是不是有父级
            if (dto.getRolId().equals(item.getRolParentId())) {
                return false;
            }
        }
        return true;
    }

    @Transactional("defaultTx")
    @Override
    public void updateEmployForRole() {

        // 科技部行员角色更新
        List<ImUserRoleRel> kjList = this.updateTechnology();
        // 业务部门员工角色更新
        List<ImUserRoleRel> ywList = this.updateBusDepUserRole();
        kjList.addAll(ywList);
        // 离职员工
        List<CommEmployeeDto> roleRels = commEmployeeService.queryResignUser();
        //3. 不存在添加，存在如果该行员离职 则删除记录
        roleRels.stream().forEach(item -> {
            List<ImUserRoleRel> temps = kjList.stream().filter(t -> item.getBadge().contains(t.getUrrUserid())).collect(Collectors.toList());
            if (null != temps && !temps.isEmpty()) {
                imUserRoleRelService.deleteImUserRoleRel(item.getBadge(), temps.get(0).getUrrRoleid());
            }
        });

        // 外包员工
        updateOutsourceRole();
        //二级部经理层
        updateDepmanagers();
        //二级部主持工作经理
        updateDepmanager();
        //总经理室
        updateManagers();
        // 分行自研项目应用 角色
        upateFhZyxmyyRole();
    }

    /**
     * 分行科技人员
     */
    private void upateFhZyxmyyRole() {
        String roleid = "FHKJ0001";
        List<String> users = new ArrayList<String>();
        List<CommEmployeeDto> list = commEmployeeService.queryFhKjUsers();
        list.stream().forEach(ii -> {
            users.add(ii.getBadge());
        });
        imUserRoleRelService.addSpecialUser(roleid,users);
    }

    /**
     * 二级部经理层
     * @return  int
     */
    private int updateDepmanagers(){
        String roleid="DEPMANAGERS";
        List<String> users=new ArrayList<String>();
        List<CommEmployeeDto> list=commEmployeeService.getDepManagers();
        list.stream().forEach(ii -> {
            if(StringUtil.isNotBlank(ii.getBadge())) {
                users.add(ii.getBadge());
            }
        });
        imUserRoleRelService.addSpecialUser(roleid,users);
        return 1;
    }
    /**
     * 二级部主持工作经理
     * @return  int
     */
    private int updateDepmanager(){
        String roleid="DEPMANAGER";
        List<String> users=new ArrayList<String>();
        List<CommEmployeeDto> list=commEmployeeService.getDepManager();
        list.stream().forEach(ii -> {
            if(StringUtil.isNotBlank(ii.getBadge())) {
                users.add(ii.getBadge());
            }
        });
        imUserRoleRelService.addSpecialUser(roleid,users);
        return 1;
    }
    /**
     * 总经理室
     * @return  int
     */
    private int updateManagers(){
        String roleid="MANAGER";
        List<String> users=new ArrayList<String>();
        List<CommEmployeeDto> list=commEmployeeService.getManagers();
        list.stream().forEach(ii -> {
            if(StringUtil.isNotBlank(ii.getBadge())) {
                users.add(ii.getBadge());
            }
        });
        imUserRoleRelService.addSpecialUser(roleid,users);
        return 1;
    }


    /**
     * 外包员工
     * @return  int
     */
    private int updateOutsourceRole() {
        Pager pager = new Pager();
        pager.setIndex(1);
        pager.setSize(500);
        ppmOutsourcingBasicService.queryUserIdForState("0", "PTWB0004",pager);
        if (null == pager.getList() || pager.getList().isEmpty()) {
            return -1;
        }
        int total = pager.getPagerCount();
        List<ImUserRoleRel> rels = javaBeanConversion((List<PpmOutsourcingBasic>) pager.getList());
        imUserRoleRelService.addImUserRoleRel(rels);
        for (int i = 1; i <= total; i++) {
            pager.setIndex(1 + i);
            ppmOutsourcingBasicService.queryUserIdForState("0", "PTWB0004",pager);
            rels = javaBeanConversion((List<PpmOutsourcingBasic>) pager.getList());
            if (null == rels || rels.isEmpty()) {
                continue;
            }
            imUserRoleRelService.addImUserRoleRel(rels);
        }
        // 删除已离职的外包员工
        List<ImUserRoleRel> resignList = imUserRoleRelService.queryResignOutsource();
        // 如果为null 说明没有离职 则结束，否则执行删除
        if (null == resignList || resignList.isEmpty()) {
            return -1;
        }
        // 如果存在离职，判断大于50个，分批删除
        int size = 50;
        List<ImUserRoleRel> temps;
        if (resignList.size() > size) {
            for (int i = 1; (i - 1) * size < resignList.size(); i++) {
                temps = resignList.stream().skip((i - 1) * size).limit(size).collect(Collectors.toList());
                imUserRoleRelService.deleteImUserRoleRel(temps);
            }
        } else {
            imUserRoleRelService.deleteImUserRoleRel(resignList);
        }
        return 1;
    }

    /**
     * java类转换
     *
     * @param list 集合对象
     * @return  List<ImUserRoleRel>
     */
    private List<ImUserRoleRel> javaBeanConversion(List<PpmOutsourcingBasic> list) {
        List<ImUserRoleRel> roleRels = new ArrayList<>();
        list.stream().forEach(item -> {
            ImUserRoleRel entity = new ImUserRoleRel();
            entity.setUrrUserid(item.getJobId());
            entity.setUrrRoleid("PTWB0004");
            roleRels.add(entity);
        });
        return roleRels;
    }

    /**
     * 更新科技部行员
     * @return List<ImUserRoleRel>
     */
    private List<ImUserRoleRel> updateTechnology() {
        // 1.查询科技部行员
        List<CommEmployeeDto> employs = commEmployeeService.queryOnTheJobUsers();
        // 2.查询用户角色-科技部员工
        return this.updateUserRole(employs, "PT0001");
    }

    /**
     * 更新业务部门员工
     * @return List<ImUserRoleRel>
     */
    private List<ImUserRoleRel> updateBusDepUserRole() {
        // 1.查询科技部行员
        List<CommEmployeeDto> employs = commEmployeeService.queryBusDepUsers();
        // 2.查询用户角色-科技部员工
        return this.updateUserRole(employs, "BD0100");
    }

    /**
     * 更新员工角色基础方法
     *
     * @param employs 集合对象
     * @param roleCode 角色编号
     * @return  List<ImUserRoleRel>
     */
    private List<ImUserRoleRel> updateUserRole(List<CommEmployeeDto> employs, String roleCode) {
        List<ImUserRoleRel> roles = imUserRoleRelService.queryUserIdForRoleId(roleCode);

        int pageSize = 500;

        // 如果等于null，说明行员没有记录，则批量添加，这里指初始数据，否则只更新，
        if (null == roles || roles.isEmpty()) {
            List<ImUserRoleRel> roleRels = new ArrayList<>();
            List<CommEmployeeDto> emps;
            for (int page = 1; (page - 1) * pageSize < employs.size(); page++) {
                emps = employs.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
                emps.stream().forEach(ii -> {
                    ImUserRoleRel rel = new ImUserRoleRel();
                    rel.setUrrRoleid(roleCode);
                    rel.setUrrUserid(ii.getBadge());
                    roleRels.add(rel);
                });
                imUserRoleRelService.addImUserRoleRel(roleRels);
                roleRels.clear();
            }
        } else {
            // 2.判断用户角色表是否存在信息
            List<ImUserRoleRel> rels = new ArrayList<>();
            employs.stream().forEach(item -> {
                List<ImUserRoleRel> temps = roles.stream().filter(t -> item.getBadge().contains(t.getUrrUserid())).collect(Collectors.toList());
                // 如果为空，说明是新增行员，添加
                if (null == temps || temps.isEmpty()) {
                    ImUserRoleRel rel = new ImUserRoleRel();
                    rel.setUrrRoleid(roleCode);
                    rel.setUrrUserid(item.getBadge());
                    rels.add(rel);
                }
            });
            if (null != rels && !rels.isEmpty()) {
                if (rels.size() > 500) {
                    List<ImUserRoleRel> emps;
                    List<ImUserRoleRel> roleRels = new ArrayList<>();
                    for (int page = 1; (page - 1) * pageSize < rels.size(); page++) {
                        emps = rels.stream().skip((page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
                        emps.stream().forEach(ii -> {
                            roleRels.add(ii);
                        });
                        imUserRoleRelService.addImUserRoleRel(roleRels);
                        roleRels.clear();
                    }
                } else {
                    imUserRoleRelService.addImUserRoleRel(rels);
                }
            }
        }
        return roles;
    }

//  ------ 递归角色树 end -----

//    public static void main(String[] args) {
//        int size = 2;
//        int index = 1;
//        List<Integer> list = new ArrayList<>();
//        List<Integer> result = null;
//        List<Integer> lists = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11);
//        for(int page = 1; (page-1) * size < lists.size(); page++) {
//            result = lists.stream().skip((page - 1) * size)
//                    .limit(size).collect(Collectors.toList());
//
//        }
//
//
//    }

    @Override
    public List<ImRoleTreeDto> queryOperaRoleTree() {
        Map<String, Object> map = new HashedMap();
        map.put("sysType", 1);
        List<ImRole> roleList = myBatisDao.selectListT(IMROLE, map, "queryOperaRoleList");
        List<ImRoleTreeDto> dtoList = JsonUtil.jsonToList(roleList, ImRoleTreeDto.class);
        List<ImRoleTreeDto> treeList = recursiveListTree(dtoList);
        return treeList;
    }
}
