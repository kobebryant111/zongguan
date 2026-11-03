package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.ImPermissionsDto;
import com.work.ppm.application.model.systemmanager.ImPermissions;

import java.util.List;

/**
 * 角色菜单关联接口
 */
public interface IImPermissionsService {

    /**
     * 查询集合
     * @param rid 角色id
     * @return List<ImPermissions>
     */
    List<ImPermissions> queryRidForList(String rid) ;

    /**
     * 更新
     * @param imPermissions 对象
     * @return Integer
     */
    Integer addImPermissions(ImPermissionsDto imPermissions) ;

    /**
     * 删除
     * @param rid 角色id
     * @return Integer
     */
    Integer deleteImPermissions(String rid) ;

    /**
     * 删除
     * @param mid 菜单编号
     * @return Integer
     */
    Integer deleteImPermissionsForMid(String mid) ;
    /**
     * 菜单是否已经被分配
     * @param mid 菜单id
     * @return boolean
     */
    boolean getAuthPathRelForMid(String mid) ;


    /**
     * 添加操作按钮权限
     * @param imPermissions 对象
     * @return Integer
     */
    Integer addOperaPermissions(ImPermissionsDto imPermissions) ;


    /**
     * 查询相关角色下那些按钮具有权限
     * @param rid 角色id
     * @return List<ImPermissions>
     */
    List<ImPermissions> queryOperaList(String rid) ;

    /**
     * 删除
     * @param rid 角色id
     * @return Integer
     */
    Integer deleteOperaPermissions(String rid) ;


}
