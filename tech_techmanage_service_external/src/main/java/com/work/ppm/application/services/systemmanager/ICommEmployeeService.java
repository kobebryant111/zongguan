package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingBasicExcelDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 员工
 */
public interface ICommEmployeeService {

    /**
     * 分页
     * @param commEmployee 对象
     * @param pager 分页对象
     */
    void queryPager(CommEmployeeDto commEmployee, Pager pager) ;

    /**
     * 分页，去除离职人员
     * @param commEmployee 对象
     * @param pager 分页对象
     */
    void pageList(CommEmployee commEmployee, Pager pager) ;

    /**
     * 查询详情
     * @param commEmployee 对象
     * @return CommEmployee
     */
    @Deprecated
    CommEmployee queryDetail(CommEmployee commEmployee) ;

    /**
     * ADD
     * @param commEmployee 对象
     * @return Integer
     */
    Integer addCommEmployee(CommEmployee commEmployee) ;
    /**
     * 同步操作权限
     * @param commEmployee 对象
     * @return Integer
     */
    Integer syncOperator(CommEmployee commEmployee) ;
    /**
     * 更新
     * @param commEmployee 对象
     * @return Integer
     */
    Integer updateCommEmployee(CommEmployee commEmployee) ;

    /**
     * 删除
     * @param commEmployee 对象
     * @return Integer
     */
    Integer deleteCommEmployee(CommEmployee commEmployee) ;

    /**
     * 角色用户关联查询
     * @param commEmployee  对象
     * @param pager 分页对象
     */
    void queryRoleUserRelByPage(CommEmployeeDto commEmployee, Pager pager) ;
    /**
     * 获取Token
     * @param badge 用户编号
     * @param ip ip
     * @return String
     */
    String getTokenMd5(String badge,String ip) ;

    /**
     * 行员花名册
     * @param commEmployeeDto 对象
     * @param pager 分页对象
     */
    void queryCrewPageList(CommEmployeeDto commEmployeeDto, Pager pager) ;

    /**
     * 获取部门编号
     * @param userId 工号
     * @return String
     */
    String getDeptNo(String userId) ;

    /**
     * 根据当前用户Userid查找该用户所在二级部指定角色的人员信息
     * @param userId  工号
     * @param roleId 角色id
     * @return  String
     */
    String queryDeptUserInfoByUserId(String userId, String roleId) ;

    /**
     * 根据指定角色的查找对应人员信息
     * @param roleId 角色id
     * @return 参与者,号连接字符串
     */
    String queryUserIdByRole(String roleId) ;

    /**
     * 获取行员列表-组件
     * @param commEmployeeDto 对象
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> getEmployeeList(CommEmployeeDto commEmployeeDto);

    /**
     * 获取用户树
     * @param commTreeDto 对象
     *                    id
     *                    current 当前选中节点
     *                    next 标记
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryEmployeeTree(CommTreeDto commTreeDto);

    /**
     *  获取用户集合
     * @param id 部门编号
     * @param next 子节点类型
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryEmployeeList(String id, String next);

    /**
     * 根据部门id 查询 一级部工作主持者经理
     * @param deptId 部门编号
     * @return CommEmployeeDto
     */
    CommEmployeeDto getTopWorkManager(String deptId);
    /**
     * 根据部门id 查询 二级部工作主持者经理
     * @param deptId 部门编号
     * @return CommEmployeeDto
     */
    CommEmployeeDto getTwoDeptWorkManager(String deptId);
    /**
     * 根据工号查询人员信息
     * @param badge 工号
     * @return CommEmployee
     */
    CommEmployee getCommEmployeeInfo(String badge);

    /**
     * 获取科技部行员
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> queryOnTheJobUsers();

    /**
     * 业务部门员工
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> queryBusDepUsers();
    /**
     * 获取所有离职行员
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> queryResignUser();

    /**
     * 根据用户名称、拼音、工号 查询员工信息
     * @param username 户名称、拼音、工号
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> queryEmployUser(String username);

    /**
     * 外包花名册 - 导出excel
     * @param ppmOutsourcingBasicInfodto 对象
     * @return List<CommEmployeeDto>
     */
    List<PpmOutsourcingBasicExcelDto> exportOutsourcing(PpmOutsourcingBasicExcelDto ppmOutsourcingBasicInfodto,
                                                        CommOperatorDoLoginDto user);
    /**
     * 获得二级部经理层数据
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> getDepManagers();

    /**
     * 获得主持工作经理数据
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> getDepManager();

    /**
     * 获得总经理室角色
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> getManagers();

    /**
     *  根据部门id 查询 二级部经理层
     * @param deptId 部门编号
     * @return list
     */
    List<CommEmployeeDto> ministryManagers(String deptId);

    /**
     * 根据id查询用户信息
     * @param userIds  工号
     * @return 名称(工号) 多个 “,” 分开
     */
    String getBadgeAndName(String userIds);

    /**
     * 分行总经理
     * @param deptId 部门编号
     * @return 总经理编号
     */
    String getFhTechManager(String deptId);

    /**
     * 架构部副总
     * @return String 工号
     */
    String getAreDeptManager();

    /**
     * 获取部门分管总经理/主持经理/部门经理层(二级部经理)
     * @param deptId 部门
     * @param type 类型 1：部门负责人 ，2：部门分管领导
     * @return CommEmployeeDto
     */
    CommEmployeeDto getFgManageByDepid(String deptId, int type);

    /**
     * 根据条件查询数据
     * @param commEmployeeDto
     * @return List<CommEmployeeDto>
     */
    List<CommEmployeeDto> getEmployeeLists(CommEmployeeDto commEmployeeDto);

    /**
     * 查询分行科技人员
     * @return list
     */
    List<CommEmployeeDto> queryFhKjUsers();

    /**
     * 操作权限角色用户关联查询
     * @param commEmployee  对象
     * @param pager 分页对象
     */
    void queryOperaRoleUserRelByPage(CommEmployeeDto commEmployee, Pager pager) ;

    /**
     * 查询所有权限码信息
     * @return list
     */
    List<String>  queryOperaAuthEncoding(String  userId);

    /**
     * 查询当前登录用户的ID
     * @return list
     */
    String getUserOperaAuthEncoding(String userid);

    /**
     * 分页
     * @param commEmployee 对象
     * @param pager 分页对象
     */
    void queryResearchManagerEmployee(CommEmployeeDto commEmployee, Pager pager) ;

}
