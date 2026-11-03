package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.systemmanager.ImUserRoleRel;

import java.util.List;

/**
 * 用户角色关联接口
 *
 */
public interface IImUserRoleRelService {

    /**
     * 添加
     *
     * @param imUserRoleRel 对象
     * @return Integer
     */
    Integer addImUserRoleRel(ImUserRoleRel imUserRoleRel);

    /**
     * 给数据中心同步过来的员工指定角色
     *
     * @param imUserRoleRel 对象
     * @return Integer
     */
    Integer syncCommRole(ImUserRoleRel imUserRoleRel);

    /**
     * 批量添加
     *
     * @param imUserRoleRel 对象
     * @return Integer
     */
    Integer addImUserRoleRel(List<ImUserRoleRel> imUserRoleRel);

    /**
     * 删除
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Integer
     */
    Integer deleteImUserRoleRel(String userId, String roleId);

    /**
     * 批量删除
     *
     * @param list 集合
     * @return Integer
     */
    Integer deleteImUserRoleRel(List<ImUserRoleRel> list);

    /**
     * 根据角色编号查询用户编号
     *
     * @param roleId
     * @return List<ImUserRoleRel>
     */
    List<ImUserRoleRel> queryUserIdForRoleId(String roleId);

    /**
     * 查询离职外包员工
     *
     * @return List<ImUserRoleRel>
     */
    List<ImUserRoleRel> queryResignOutsource();

    /**
     * 添加特殊用户
     *
     * @param role  角色编号
     * @param users 工号集合
     * @return int 大于0 说明成功
     */
    int addSpecialUser(String role, List<String> users);

    /**
     * 删除
     *
     * @param roleId 角色编号
     * @return Integer
     */
    Integer deleteImUserRoleRel(String roleId);

    /**
     * 该userid是否是该角色的
     *
     * @param roleid
     * @param userid
     * @return boolean
     */
    boolean isRoleUser(String roleid, String userid);

    /**
     * 添加操作权限角色用户表
     *
     * @param imUserRoleRel 对象
     * @return Integer
     */
    Integer addOperaUserRoleRel(ImUserRoleRel imUserRoleRel);

    /**
     * 删除
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Integer
     */
    Integer deleteOperaUserRoleRel(String userId, String roleId);

    /**
     * 同步添加研发综合管理用户信息
     */
    void insertResearchManagerEmployee(ImUserRoleRel imUserRoleRel);

    /**
     * 批量删除
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Integer
     */
    Integer deleteOperaUserRoleRelByIds(String userId, String roleId);


    /**
     * 批量删除
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Integer
     */
    Integer deleteImUserRoleRelByIds(String userId, String roleId);


    /**
     * 批量添加角色用户
     * @param ids
     * @param roleId
     */
    int addOperaUserRoleBatch(String ids, String roleId);

    /**
     * 添加所有角色用户
     */
    int addOperaUserRoleAll(String empname , String badge , String compid , String depid,String roleId );
}
