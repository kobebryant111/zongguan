package com.work.ppm.application.services.systemmanager.impl;

import com.alibaba.fastjson.JSON;
import com.work.ppm.application.dto.integratedmanager.PpmKqProfesstionDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.commonmanager.CommTree;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;
import com.work.ppm.application.quartz.interfaces.IImUserRoleRelJobService;
import com.work.ppm.application.services.integratedmanager.IPpmKqProfesstionService;
import com.work.ppm.application.services.systemmanager.IImUserRoleRelService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户角色关联接口
 *
 */
@Slf4j
@Service("imUserRoleRelService")
public class ImUserRoleRelService implements IImUserRoleRelService, IImUserRoleRelJobService {
    /**
     * 实体类路径
     */
    private static String IMUSERROLEREL = "com.work.ppm.application.model.systemmanager.ImUserRoleRel";
    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 考勤专员接口
     */
    @Autowired
    private IPpmKqProfesstionService ppmKqProfesstionService;


    @Override
    public Integer addImUserRoleRel(ImUserRoleRel imUserRoleRel) {
        imUserRoleRel.setSysType(1);
        return myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public Integer syncCommRole(ImUserRoleRel imUserRoleRel) {
        myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.DELETE, "deleteCommRole");
        return myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.INSTER, "syncCommRole");
    }

    @Override
    public Integer addImUserRoleRel(List<ImUserRoleRel> imUserRoleRel) {
        imUserRoleRel.stream().forEach(ii -> {
            ii.setSysType(1);
        });
        myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.INSTER, "batchAdd");
        return 1;
    }

    @Override
    public Integer deleteImUserRoleRel(String userId, String roleId) {
        ImUserRoleRel imUserRoleRel = new ImUserRoleRel();
        imUserRoleRel.setUrrUserid(userId);
        imUserRoleRel.setUrrRoleid(roleId);
        imUserRoleRel.setSysType(1);
        return myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public Integer deleteImUserRoleRel(List<ImUserRoleRel> list) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        list.stream().forEach(ii -> ii.setSysType(1));
        return myBatisDao.executionPojo(IMUSERROLEREL, list, IMyBatisDao.SQL.DELETE, "batchDeleteList");
    }

    @Override
    public List<ImUserRoleRel> queryUserIdForRoleId(String roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("urrRoleid", roleId);
        map.put("sysType", 1);
        return myBatisDao.selectListT(IMUSERROLEREL, map, "getList");
    }

    @Override
    public List<ImUserRoleRel> queryResignOutsource() {
        return myBatisDao.selectListT(IMUSERROLEREL, null, "queryResignOutsource");
    }

    @Override
    public int addSpecialUser(String role, List<String> users) {
        if (users.isEmpty()) {
            return 0;
        }
        // 1.根据角色删除
        List<ImUserRoleRel> rels = new ArrayList<>();
        ImUserRoleRel item;
        for (int i = users.size() - 1; i >= 0; i--) {
            item = new ImUserRoleRel();
            item.setUrrUserid(users.get(i));
            item.setUrrRoleid(role);
            rels.add(item);
        }
        this.deleteImUserRoleRel(role);
        // 2. 添加
        this.addImUserRoleRel(rels);
        return 1;
    }

    @Override
    public Integer deleteImUserRoleRel(String roleId) {
        ImUserRoleRel rel = new ImUserRoleRel();
        rel.setUrrRoleid(roleId);
        int i = myBatisDao.executionPojo(IMUSERROLEREL, rel, IMyBatisDao.SQL.DELETE, "delByRoleId");
        return i;
    }


    @Transactional("defaultTx")
    @Override
    public int clerkTimer() {
        List<String> kqJobids = null;
        List<String> roles = null;
        List<PpmKqProfesstionDto> list = ppmKqProfesstionService.getPpmKqProfesstions(null);
        if (null == list || list.isEmpty()) {
            log.warn("文员角色未查询到 考勤专员数据， 定时器执行结束");
            return -1;
        }
        // 考勤工号
        kqJobids = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            kqJobids.add(list.get(i).getJobid());
        }
        // 过滤重复数据
        kqJobids = kqJobids.stream().distinct().collect(Collectors.toList());
        // 查询文员角色
        List<ImUserRoleRel> roleRels = this.queryUserIdForRoleId("WY00010");
        int roleSize = 0;
        if (null != roleRels && !roleRels.isEmpty()) {
            roleSize = roleRels.size();
            roles = new ArrayList<>();
            for (int i = roleRels.size() - 1; i >= 0; i--) {
                roles.add(roleRels.get(i).getUrrUserid());
            }
        } else {
            roles = new ArrayList<>();
        }

        int kqSize = kqJobids.size();
        List<String> delList = new ArrayList<>();
        List<ImUserRoleRel> addRoleList = new ArrayList<>();

        // 1 删除多余文员数据 -- 角色数据存在，但是考勤专员已删除
        ImUserRoleRel temp1;
        for (int i = kqSize - 1; i >= 0; i--) {
            String jobid = kqJobids.get(i);
            if (!roles.contains(jobid)) {
                temp1 = new ImUserRoleRel();
                temp1.setUrrRoleid("WY00010");
                temp1.setSysType(1);
                temp1.setUrrUserid(jobid);
                addRoleList.add(temp1);
//                delList.add(jobid);
            }
        }
        // 2. 添加文员角色数据 -- 角色数据不存在，考勤专员存在
        for (int i = roleSize - 1; i >= 0; i--) {
            ImUserRoleRel temp2 = roleRels.get(i);
            if (!kqJobids.contains(temp2.getUrrUserid())) {
                delList.add(temp2.getUrrUserid());
            }
        }
        // 批量添加
        if (null != addRoleList && !addRoleList.isEmpty()) {

            myBatisDao.executionPojo(IMUSERROLEREL, addRoleList, IMyBatisDao.SQL.INSTER, "batchAdd");
        }
        // 批量删除
        if (null != delList && !delList.isEmpty()) {
            List<ImUserRoleRel> temp = new ArrayList<>();
            ImUserRoleRel rel;
            for (int i = delList.size() - 1; i >= 0; i--) {
                rel = new ImUserRoleRel();
                rel.setSysType(1);
                rel.setUrrUserid(delList.get(i));
                rel.setUrrRoleid("WY00010");
                temp.add(rel);
            }
            myBatisDao.executionPojo(IMUSERROLEREL, temp, IMyBatisDao.SQL.DELETE, "batchDeleteList");
        }
        return 1;
    }

    @Override
    public boolean isRoleUser(String roleid, String userid) {
        List<ImUserRoleRel> list = queryUserIdForRoleId(roleid);
        for (int i = list.size() - 1; i >= 0; i--) {
            if (userid.equals(list.get(i).getUrrUserid())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer addOperaUserRoleRel(ImUserRoleRel imUserRoleRel) {
        imUserRoleRel.setSysType(1);
        return myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.INSTER, "addOperaUserRoleRel");
    }

    @Override
    public Integer deleteOperaUserRoleRel(String userId, String roleId) {
        ImUserRoleRel imUserRoleRel = new ImUserRoleRel();
        imUserRoleRel.setUrrUserid(userId);
        imUserRoleRel.setUrrRoleid(roleId);
        imUserRoleRel.setSysType(1);
        return myBatisDao.executionPojo(IMUSERROLEREL, imUserRoleRel, IMyBatisDao.SQL.DELETE, "deleteByUserId");
    }

    @Override
    public void insertResearchManagerEmployee(ImUserRoleRel imUserRoleRel) {
        String urrRoleid = imUserRoleRel.getUrrRoleid();
        String urrUserid = imUserRoleRel.getUrrUserid();
        try{
            if (urrRoleid.contains("RESEARCH")) {
                Map<String, Object> map = new HashMap<>();
                map.put("badge", urrUserid);
                List<CommEmployee> commEmployeeList = myBatisDao.selectListT(COMMEMPLOYEE, map, "queryByBadge");
                for (CommEmployee commEmployee : commEmployeeList) {
                    Integer addResearchManagerEmployee = myBatisDao.executionPojo(COMMEMPLOYEE, commEmployee, IMyBatisDao.SQL.INSTER, "addResearchManagerEmployee");
                    if (addResearchManagerEmployee > 0) {
                        log.info("研发综合管理用户添加成功！用户ID为：" + urrUserid);
                    }
                }
            }
        }catch (Exception e){
            log.error("研发综合管理用户添加失败！添加失败的用户ID为：{"+ urrUserid +"}。错误信息为：{}" , e);
            e.printStackTrace();
        }

    }

    @Override
    public Integer deleteOperaUserRoleRelByIds(String userId, String roleId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        if(userId != null && userId.length() != 0 && userId.contains(",")){
            String[] split = userId.split(",");
            for (int i = 0; i < split.length; i++) {
                stringBuilder.append("'").append(split[i]).append("'");
                if (i < split.length - 1) {
                    stringBuilder.append(",");
                } else{
                    stringBuilder.append(")");
                }
            }
        }else if(userId != null && userId.length() != 0 && !userId.contains(",")){
            stringBuilder.append("'").append(userId).append("'").append(")");
        }
        Map map = new HashMap<>();
        map.put("ids" , stringBuilder.toString());
        map.put("roleId" , roleId);
        return myBatisDao.executionPojo(IMUSERROLEREL, map, IMyBatisDao.SQL.DELETE, "deleteByUserIds");
    }


    @Override
    public Integer deleteImUserRoleRelByIds(String userId, String roleId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        if(userId != null && userId.length() != 0 && userId.contains(",")){
            String[] split = userId.split(",");
            for (int i = 0; i < split.length; i++) {
                stringBuilder.append("'").append(split[i]).append("'");
                if (i < split.length - 1) {
                    stringBuilder.append(",");
                } else{
                    stringBuilder.append(")");
                }
            }
        }else if(userId != null && userId.length() != 0 && !userId.contains(",")){
            stringBuilder.append("'").append(userId).append("'").append(")");
        }
        Map map = new HashMap<>();
        map.put("ids" , stringBuilder.toString());
        map.put("roleId" , roleId);
        return myBatisDao.executionPojo(IMUSERROLEREL, map, IMyBatisDao.SQL.DELETE, "deleteUserRoleByUserIds");
    }


    @Override
    public int addOperaUserRoleBatch(String ids, String roleId) {
        int i = 0;
        if (ids.contains(",")) {
            String[] split = ids.split(",");
            for (String everyBadge : split) {
                Map<String , Object> map = new HashMap<>();
                map.put("urrRoleid" , roleId);
                map.put("sysType" , 1);
                map.put("urrUserid" , everyBadge);
                i += myBatisDao.executionPojo(IMUSERROLEREL, map, IMyBatisDao.SQL.INSTER, "addOperaUserRoleBatch");
            }
        }else {
            Map<String , Object> map = new HashMap<>();
            map.put("urrRoleid" , roleId);
            map.put("sysType" , 1);
            map.put("urrUserid" , ids);
            i += myBatisDao.executionPojo(IMUSERROLEREL, map, IMyBatisDao.SQL.INSTER, "addOperaUserRoleBatch");
        }
        return i;
    }

    @Override
    public int addOperaUserRoleAll(String empname , String badge , String compid , String depid,String roleId ) {
        int j = 0;
        List<String> userIdList = myBatisDao.selectListT(IMUSERROLEREL, null, "getUserRole");
        if (userIdList == null && userIdList.size() == 0) {
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userIdList.size(); i++) {
            if ("findpt".equals(userIdList.get(i))) {
                continue;
            }
            stringBuilder.append(userIdList.get(i));
            if (i < userIdList.size() - 1) {
                stringBuilder.append(",");
            }
        }
        if(StringUtil.isStringNotEmpty(stringBuilder.toString()) && StringUtil.isStringNotEmpty(roleId)) {
            j += addOperaUserRoleBatch(stringBuilder.toString(), roleId);
        }
        return j ;
    }
}
