package com.work.ppm.application.services.systemmanager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.systemmanager.CommDepartment;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 部门
 *
 */
public interface ICommDepartmentService {

    /**
     * 分页
     *
     * @param commDepartment 对象
     * @param pager          分页对象
     */
    void queryPager(CommDepartment commDepartment, Pager pager);

    /**
     * 查询详情
     *
     * @param commDepartment 对象
     * @return CommDepartment
     */
    CommDepartment queryDetail(CommDepartment commDepartment);

    /**
     * 更新
     *
     * @param commDepartment 对象
     * @return Integer
     */
    Integer addCommDepartment(CommDepartment commDepartment);
    /**
     * 更新
     *
     * @param commDepartment 对象
     * @return Integer
     */
    Integer updateCommDepartment(CommDepartment commDepartment);

    /**
     * 删除
     *
     * @param commDepartment 对象
     * @return Integer
     */
    Integer deleteCommDepartment(CommDepartment commDepartment);

    /**
     * 根据机构id 查询部门列表
     *
     * @param compId 编号
     * @return List<CommDepartmentDto>
     */
    List<CommDepartmentDto> queryListByCompId(String compId);

    /**
     * 组织树
     *
     * @param code  编号
     * @param grade 编号
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryDeptTree(String code, String grade);

    /**
     * 组织树
     *
     * @param code  编号
     * @param grade 编号
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryDeptTreeQt(String code,String deptId,String grade);

    /**
     * 根据userid查询 部门的depgrade
     *
     * @param userId 工号
     * @return CommDepartment
     */
    CommDepartment queryDepgradeByUserId(String userId);

    /**
     * 授权列表
     *
     * @param deptNo   部门编号
     * @param userData 搜索条件
     * @param users    用户编号 多个用 , 分开
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryAuth(String deptNo, String userData, String users);

    /**
     * 授权列表
     *
     * @param compid   机构编号
     * @param userData 搜索条件
     * @param users    用户编号 多个用 , 分开
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryAuth(int compid, String userData, String users);

    /**
     * 授权列表
     *
     * @param adminId 机构编号
     * @return list
     */
    List<CommTreeDto> queryAuth(String adminId);

    /**
     * 部门组织树-2
     *
     * @param commDepartmentDto
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryResourceDept(CommOperatorDoLoginDto user,
                                        CommDepartmentDto commDepartmentDto);

    /**
     * 根据条件查询部门
     *
     * @param type 子节点类型 dep，emp，org
     * @param id   父级编号
     * @param next 下个节点类型
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> getDeptByCompId(String type, String id, String next);

    /**
     * 根据条件查询部门
     *
     * @param type    子节点类型 dep，emp，org
     * @param id      父级编号
     * @param isAdmin 是否admin
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> getDeptByCompId(String type, String id, boolean isAdmin);

    /**
     * 根据编号返回记录数
     *
     * @param adminid 上级部门id
     * @param compid  机构id
     * @return int
     */
    int hasDepCount(String adminid, String compid);

    /**
     * 根据部门编号查询部门名称
     *
     * @param deptId 部门id
     * @return String
     */
    String getDetpIdForDeptName(String deptId);

    /**
     * 查询总行部门
     *
     * @return List<CommDepartment>
     */
    List<CommDepartment> queryHeadOffice();

    /**
     * 根据工号查询分管部门
     *
     * @return List<CommDepartment>
     */
    List<CommDepartment> queryDirectDeps(String badge);


    /**
     * 根据条件查询列表
     *
     * @param commDepartment 对象
     * @return List<CommDepartment>
     */
    List<CommDepartment> queryList(CommDepartment commDepartment);

    /**
     * 根据部门名称 模糊查询 部门id
     *
     * @param depName 部门名称
     * @return 部门id
     */
    String getDepNameForId(String depName);

    /**
     * 根据部门名称查询部门id
     *
     * @param depName 部门名称
     * @return 部门编号
     */
    String queryDeptIdForDeptName(String depName);

    /**
     * 根据机构编号查询行长室部门id
     *
     * @param compid 机构id
     * @return CommDepartmentDto 对象
     */
    CommDepartmentDto queryDeptIdForCompId(String compid);

    /**
     * 根据部门编号查询父级信息
     *
     * @param deptId 部门编号
     * @return CommDepartmentDto
     */
    CommDepartmentDto queryParentDeptId(String deptId);

    /**
     * 获取部门分管总经理/主持经理/部门经理层(二级部经理)
     *
     * @param deptId 部门id
     * @return CommDepartmentDto
     */
    CommDepartmentDto queryManagerByDepid(String deptId);

    /**
     * 获取分行IT支持部或者软件研发部
     *
     * @param compcode 机构编号
     * @return 部门编号
     */
    String getFhITManager(String compcode);

    /**
     * 获取分行运营部总经理
     *
     * @param compcode 机构编号
     * @return 部门编号
     */
    String getFhYybManager(String compcode);

    /**
     * 根据机构编码查询部门编号
     *
     * @param compcode 机构编码
     * @return 部门编号
     */
    CommDepartmentDto getCompcodeForDepId(String compcode);

    /**
     * 获取fg总经理
     *
     * @param depno
     * @return CommDepartment
     */
    CommDepartment getManagerByDepid(String depno);

    /**
     * 查询分行科技
     *
     * @param compid 机构编号
     * @return CommDepartmentDto
     */
    CommDepartmentDto queryFhKjbManager(int compid);

    /**
     * 查询分行科技
     *
     * @param deptId 机构编号
     * @return CommDepartmentDto
     */
    List<CommDepartment> query2ndBy1stDept(String deptId);
    /**
     * 查询一级部门信息
     *
     * @param deptId 机构编号
     * @return CommDepartmentDto
     */
    CommDepartment queryFirstDept(String deptId);

    /**
     * 授权列表
     *
     * @param deptNo   部门编号
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryAuthList(String deptNo);

    /**
     * 根据角色进行查询面试官信息
     * @return
     */
    List<CommTreeDto> queryMsgList();

    /**
     * 根据面试官信息进行查询部门信息
     * @param badge
     * @return
     */
    List<CommDepartment> queryDep(String badge);

}
