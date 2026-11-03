package com.work.ppm.commons.utils;

import com.work.ppm.application.dto.flowmanager.PpmRelateuserDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.model.usermanager.PpmDaDeptManager;
import com.work.ppm.application.model.usermanager.PpmEmpView;
import com.work.ppm.application.services.flowmanager.IPpmRelateuserService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.ICommOperatorService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.application.services.usermanager.IPpmDaDeptManagerService;
import com.work.ppm.application.services.usermanager.IPpmEmpViewService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 所有查询用户相关的方法工具类
 *
 */
@Slf4j
public class UsersUtil {
    /**
     * 员工接口
     */
    private static ICommEmployeeService commEmployeeService = (ICommEmployeeService) SpringUtil.getBean("commEmployeeService");
    /**
     * 部门中心分管经理配置接口
     */
    private static IPpmDaDeptManagerService ppmDaDeptManagerService = (IPpmDaDeptManagerService) SpringUtil.getBean("ppmDaDeptManagerService");
    /**
     * 登录接口
     */
    private static ICommOperatorService commOperatorService = (ICommOperatorService) SpringUtil.getBean("commOperatorService");
    /**
     * 字典表
     */
    @Autowired
    private static IEosDictEntryService eosDictEntryService = (IEosDictEntryService) SpringUtil.getBean("eosDictEntryService");
    /**
     * 根据部门id 查询 二级部工作主持者经理
     *
     * @param deptno 部门编号
     * @return CommEmployeeDto
     */
    @ApiOperation(value = "根据部门id 查询 二级部工作主持者经理")
    public static CommEmployeeDto getTwoWorkManager(String deptno) {
        CommEmployeeDto employee = commEmployeeService.getTwoDeptWorkManager(deptno);
        return employee;
    }

    /**
     * 根据部门id 查询 一级部工作主持者经理
     *
     * @param deptno 部门编号
     * @return CommEmployeeDto
     */
    @ApiOperation(value = "根据部门id 查询一级部工作主持者经理")
    public static CommEmployeeDto getTopWorkManager(String deptno) {
        CommEmployeeDto employee = commEmployeeService.getTopWorkManager(deptno);
        return employee;
    }

    /**
     * 二级部经理层
     *
     * @param deptId 部门编号
     * @return list
     */
    @ApiOperation(value = " 二级部经理层")
    public static List<CommEmployeeDto> queryMinistryManagers(String deptId) {
        List<CommEmployeeDto> list = commEmployeeService.ministryManagers(deptId);
        return list;
    }

    /**
     * 根据badge查询人员信息
     *
     * @param badge 工号
     * @return CommEmployee
     */
    @ApiOperation(value = "根据badge查询人员信息")
    public static CommEmployee getCommEmployeeInfo(String badge) {
        CommEmployee employee = commEmployeeService.getCommEmployeeInfo(badge);
        return employee;
    }

    /**
     * 根据depno查询分管总经理
     *
     * @param depno 部门编号
     * @return String
     */
    @ApiOperation(value = "根据depno查询分管总经理")
    public static String getDeputyDirectorGeneral(String depno) {
        String deputyDirectorGeneral = "";
        PpmDaDeptManager manager = new PpmDaDeptManager();
        manager.setDeptid(depno);
        manager = ppmDaDeptManagerService.queryDetail(manager);
        if (manager != null) {
            deputyDirectorGeneral = manager.getUserid();
        }
        return deputyDirectorGeneral;
    }

    /**
     * 获取分管总经理id
     *
     * @param deptId 部门编号
     * @return String
     */
    public static String queryHousing(String deptId) {
        String userId = ppmDaDeptManagerService.queryBadgeForDetpId(deptId);
        return userId;
    }

    /**
     * 获取下一个节点用户id
     *
     * @param roleId 角色id
     * @return 工号 多个“,” 分割
     */
    public static String getNextRoleUser(String roleId) {
        return commOperatorService.getNextRoleUser(roleId);
    }

    /**
     * 根据工号查询名称
     *
     * @param userId 工号
     * @return 名称 多个“,” 分割
     */
    public static String getUserNameByUserId(String userId) {
        return commOperatorService.getUserNameByUserId(userId);
    }

    /**
     * 根据角色id 查询 名称
     *
     * @param roleId
     * @return 名称 多个“,” 分割
     */
    public static String getUserNameByRoleId(String roleId) {
        return commOperatorService.getUserNameByRoleId(roleId);
    }

    /**
     * 根据用户id查询用户名称和id
     *
     * @param userId 工号
     * @return 名称和工号 工号@名称， 多个“,” 分割
     */
    public static String getUser(String userId) {
        IPpmEmpViewService ppmEmpViewService = (IPpmEmpViewService) SpringUtil.getBean("ppmEmpViewService");
        String[] str = userId.split(",");
        String newUsers = "";
        String relateUser;
        for (int i = 0; i < str.length; i++) {
            if ("workuser".equals(str[i])) {
                newUsers = "workuser@workuser";
                continue;
            }
            relateUser = selectRelateUser(str[i]);
            PpmEmpView emp = new PpmEmpView();
            emp.setBadge(relateUser);
            PpmEmpView item = ppmEmpViewService.queryDetail(emp);
            if (null != item) {
                newUsers = newUsers + relateUser + "@" + item.getEmpname() + ",";
            }
        }
        return newUsers.substring(0, newUsers.length() - 1);
    }

    /**
     * 根据用户编号查询部门编号
     *
     * @param userId 用户编号
     * @return 部门编号
     */
    public static String getDeptId(String userId) {
        return commEmployeeService.getDeptNo(userId);
    }

    /**
     * 查询OA 关联的用户
     *
     * @param userId 工号
     * @return String
     */
    private static String selectRelateUser(String userId) {
        IPpmRelateuserService ppmRelateuserService = (IPpmRelateuserService) SpringUtil.getBean("ppmRelateuserService");
        PpmRelateuserDto param = new PpmRelateuserDto();
        param.setRelateUserno(userId);
        PpmRelateuserDto item = ppmRelateuserService.queryDetail(param);
        if (null != item) {
            return item.getUserno();
        }
        return userId;
    }


    /**
     * 根据条件查询部门数据
     *
     * @param commEmployeeDto 部门参数
     * @return list
     */
    @ApiOperation(value = " 根据条件查询部门数据")
    public static List<CommEmployeeDto> getEmployeeList(CommEmployeeDto commEmployeeDto) {
        List<CommEmployeeDto> list = commEmployeeService.getEmployeeList(commEmployeeDto);
        return list;
    }


    /**
     * 根据条件查询员工数据
     *
     * @param commEmployeeDto 员工参数
     * @return list
     */
    @ApiOperation(value = " 根据条件查询员工数据")
    public static List<CommEmployeeDto> getEmployeeLists(CommEmployeeDto commEmployeeDto) {
        List<CommEmployeeDto> list = commEmployeeService.getEmployeeList(commEmployeeDto);
        return list;
    }

    /**
     * 根据部门id 查询业务字典配置部门审批人员
     *
     * @param deptno 部门编号
     * @return CommEmployeeDto
     */
    @ApiOperation(value = "根据部门id 查询部门审批人员")
    public static EosDictEntry getChargeManager(String deptno) {
        EosDictEntry workManager = eosDictEntryService.
                getTypeidForDetail("CHARGE_APPROVE", deptno);
        // 下一步审批人
        if (workManager == null || StringUtils.isBlank(workManager.getDictname())) {
            log.error("{}未配置部门审批人员", deptno);
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当前用户无总经理室经理");
        }
        return workManager;
    }

    @ApiOperation(value = "根据角色id 金融科技部经办人")
    public static CommEmployee getDepApprove(String rolId) {
        Pager pager = new Pager();
        CommEmployeeDto commEmployee = new CommEmployeeDto();
        commEmployee.setRolId(rolId);
        commEmployeeService.queryRoleUserRelByPage(commEmployee, pager);
        if (pager.getTotal() != 1 ) {
            throw new BizException(ApiCode.BPS_WORKFLOW_FAIL, "当先节点审批人数超过一人");
        }
        return (CommEmployee)pager.getList().get(0);
    }
}
