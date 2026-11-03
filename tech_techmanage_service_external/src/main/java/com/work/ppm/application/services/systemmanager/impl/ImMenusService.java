package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.dto.systemmanager.ImRoleDoLoginDto;
import com.work.ppm.application.model.systemmanager.ImPermissions;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.dto.systemmanager.ImMenusDto;
import com.work.ppm.application.model.systemmanager.ImMenus;
import com.work.ppm.application.services.systemmanager.IImMenusService;
import com.work.ppm.application.services.systemmanager.IImPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;


/**
 * 菜单
 */
@Service("imMenusService")
public class ImMenusService implements IImMenusService {
    /**
     * 实体类路径
     */
    private static String IMMENUS = "com.work.ppm.application.model.systemmanager.ImMenus";
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
     * 菜单接口
     */
    @Autowired
    private IImMenusService iImMenusService;

    @Override
    public List<ImMenusDto> queryMenusTree(ImMenus imMenus) {
        imMenus.setSysType(1);
        List<ImMenus> menusList = myBatisDao.selectListT(IMMENUS, imMenus, "getList");
        List<ImMenusDto> dtos = JsonUtil.jsonToList(menusList, ImMenusDto.class);
        // 查询菜单是否关联分配权限
        dtos.stream().filter(item -> item.getType().intValue() == 1).forEach(item -> {
            boolean falg = false;
            try {
                falg = imPermissionsService.getAuthPathRelForMid(item.getMid());
            } catch (Exception e) {
               throw new BizException(ApiCode.FAIL,e.getMessage());
            }
            item.setAuthRel(falg);
        });

        List<ImMenusDto> treeList =     recursiveListToTree(dtos);
        return treeList;
    }

    @Override
    public ImMenus queryDetail(ImMenus imMenus) {
        imMenus.setSysType(1);
        return myBatisDao.selectClass(IMMENUS, imMenus,"queryDetail");
    }

    @Override
    public Integer updateImMenus(ImMenus imMenus) {
        imMenus.setSysType(1);
        imMenus.setModifydate(new Date());
        if(!StringUtil.isStringNotEmpty(imMenus.getMid())) {
            imMenus.setIcon("");
            // 添加
            imMenus.setCreatedate(new Date());
            myBatisDao.executionPojo(IMMENUS, imMenus, IMyBatisDao.SQL.INSTER,"addPojo");
            return 1;
        }
        // 更新
        myBatisDao.executionPojo(IMMENUS, imMenus, IMyBatisDao.SQL.UPDATE,"updatePojo");
        return 2;
    }
    @Transactional("defaultTx")
    @Override
    public Integer deleteImMenus(ImMenus imMenus) {
        imMenus.setSysType(1);
        imPermissionsService.deleteImPermissionsForMid(imMenus.getMid());
        return myBatisDao.executionPojo(IMMENUS, imMenus, IMyBatisDao.SQL.DELETE,"deleteById");
    }

    @Override
    public ImMenusDto getLoginMenus(String rid, CommOperatorDoLoginDto user) {
        // 获取根目录
        ImMenus entity = new ImMenus();
        entity.setType(-1);
        entity.setSysType(1);
        ImMenus item = this.queryDetail(entity);
        // 根据角色id 查询菜单
        ImMenusDto itemDto = JsonUtil.jsonToClass(item,ImMenusDto.class);
        // 处理 rid 判断如果多个则进行处理
        StringBuilder sb = new StringBuilder(rid.length());
        if(rid.indexOf(",") != -1) {
            String[] rids = rid.split(",");
            for (int i = 0; i < rids.length; i++) {
                sb.append("'").append(rids[i]).append("',");
            }
            rid = sb.toString().substring(0, sb.toString().lastIndexOf(",") );
        } else {
            sb.append("'").append(rid).append("'");
            rid = sb.toString();
        }

        Map<String,Object> map = new HashMap<>();
        map.put("rid",rid);
        map.put("sysType",1);
        List<ImMenus> list = myBatisDao.selectListT(IMMENUS, map, "LoginMenusList");
        // 数据去重
        List<ImMenus> nwelist = list.stream().distinct().collect(Collectors.toList());
        // 转换
        List<ImMenusDto> listDto = JsonUtil.jsonToList(nwelist,ImMenusDto.class);
        List<ImMenusDto> newDto = recursiveListToTree(listDto);
        boolean falg = newDto.stream().anyMatch( ii -> ii.getType().intValue() == -1);
        if(falg) {
            return  newDto.get(0);
        }
        if(newDto.isEmpty()) {
            throw new BizException(ApiCode.FAIL,"该用户为配置菜单，请联系管理员");
        }
        itemDto.setChildren(newDto);
        return itemDto;
    }

    @Override
    public List<ImMenus> queryMeunsList(List<String> mids) {
        StringJoiner joiner = new StringJoiner("','", "'", "'");
        mids.stream().forEach(ii -> {
             joiner.add(ii);
        });
        Map<String,Object> map = new HashMap<>();
        map.put("mids", joiner.toString());

        return myBatisDao.selectListT(IMMENUS, map, "getMidsForList");
    }

    /**
     * 特殊角色菜单
     * @param list 原菜单集合
     * @param user session
     * @return list
     */
    private List<ImMenus> speListRoleMenu(List<ImMenus> list, CommOperatorDoLoginDto user) {
        // 过滤出特殊角色
        List<ImRoleDoLoginDto> rids =
                user.getRoles().stream().filter(i -> 0 == i.getFalgSpecialRole()).collect(Collectors.toList());
        if(rids.isEmpty()) {
            return new ArrayList<>();
        }
        // 查询当前用户该有的特殊菜单
        List<ImPermissions> mList = new ArrayList<>();
        for (int i = rids.size() - 1; i >= 0; i--) {
            List<ImPermissions> teps = imPermissionsService.queryRidForList(rids.get(i).getRoleId());
            mList.addAll(teps);
        }
        // 先删除特殊角色的菜单
        for (int i = list.size() - 1; i >= 0; i--) {
            ImMenus menus = list.get(i);
            for (int j = mList.size() - 1; j >= 0; j--) {
                // 如果菜单相同 则删除
                if(mList.get(j).getMid().equals(menus.getMid())) {
                    list.remove(list.get(i));
                    continue;
                }
            }
        }
        // 将角色里包含特殊，菜单重新添加到 list
        List<String> temps = new ArrayList<>();
        mList.stream().forEach(ii -> {
            temps.add(ii.getMid());
        });

        List<ImMenus> menus = iImMenusService.queryMeunsList(temps);
        list.addAll(menus);
        return list;
    }

    /**
     * 检查是否存在相同的父类
     * @param children 对象
     * @param tepList 集合
     * @return boolean
     */
    private boolean existParentMenu(ImMenus children, List<ImMenusDto> tepList) {
        if(null == tepList || tepList.isEmpty()) {
            return false;
        }
        for (ImMenusDto item: tepList) {
            if(children.getMid().equals(item.getMid())) {
                return true;
            }
        }
        return false;
    }

    //    递归 菜单树 begin ------

    /**
     * 递归树
     * @param nodes 节点集合
     * @return  List<ImMenusDto>
     */
    private List<ImMenusDto> recursiveListToTree(List<ImMenusDto> nodes) {
        boolean extisRootNode = false;
        List<ImMenusDto> childrenNode = new ArrayList<>();
        for (ImMenusDto item: nodes) {
            // 判断节点是否有根节点
            if(isRootNode(item, nodes)) {
                childrenNode.add(findChildrenNode(item, nodes));
                extisRootNode = true;
            }
        }
        if(!extisRootNode) {
            return nodes;
        }
        return childrenNode;
    }

    /**
     * 递归查找子节点
     * @param parentItem 节点
     * @param nodes 所有节点
     * @return  ImMenusDto
     */
    private ImMenusDto findChildrenNode(ImMenusDto parentItem, List<ImMenusDto> nodes) {
        List<ImMenusDto> childrens = new ArrayList<>();
        for (ImMenusDto it: nodes) {
            if(parentItem.getMid().equals(it.getParentid())) {
                childrens.add(findChildrenNode(it,nodes));
                parentItem.setChildren(childrens);
            }
        }
        return parentItem;
    }

    /**
     * 判断节点是否有根节点
     * @param item 节点
     * @param nodes 所有节点
     * @return boolean
     */
    private boolean isRootNode(ImMenusDto item, List<ImMenusDto> nodes) {
        for (ImMenusDto it: nodes) {
            if(it.getMid().equals(item.getParentid())) {
                return false;
            }
        }
        return true;
    }
//    递归 菜单树 end ------



    @Override
    public List<ImMenusDto> queryOperaMenusTree(ImMenus imMenus) {
        imMenus.setSysType(1);
        List<ImMenus> menusList = myBatisDao.selectListT(IMMENUS, imMenus, "getOperaMenusList");
        List<ImMenusDto> dtos = JsonUtil.jsonToList(menusList, ImMenusDto.class);
        // 查询菜单是否关联分配权限
        dtos.stream().filter(item -> item.getType().intValue() == 1).forEach(item -> {
            boolean falg = false;
            try {
                falg = imPermissionsService.getAuthPathRelForMid(item.getMid());
            } catch (Exception e) {
                throw new BizException(ApiCode.FAIL,e.getMessage());
            }
            item.setAuthRel(falg);
        });

        List<ImMenusDto> treeList = recursiveListToTree(dtos);
        return treeList;
    }

}
