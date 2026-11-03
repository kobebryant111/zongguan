package com.work.ppm.application.services.rdmanager;

import com.work.ppm.application.dto.bankmamager.PpmEmpKqInfoDto;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.integratedmanager.PpmAttendanceInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmRdEmployeeInfoExportDto;
import com.work.ppm.application.dto.integratedmanager.PpmRdHrProjectInfoExportDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.commonmanager.CommRdDepartment;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import com.work.ppm.application.model.rdmanager.*;
import com.work.ppm.commons.pager.Pager;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IHrResourcesService {
    /**
     * 分页
     * @param commRdEmployeeDto 对象
     * @param pager 分页对象
     */
    void queryPager(CommRdEmployeeDto commRdEmployeeDto, Pager pager);


    /**
     * 信息查询
     * @param rdQueryEmployee 对象
     * @param user 对象
     */
    List<CommRdEmployeeDto> queryList(RdQueryEmployee rdQueryEmployee , CommOperatorDoLoginDto user) ;

    /**
     * 工作管理信息查询
     * @param commRdEmployeeDto 对象
     */
    List<CommRdEmployeeDto> queryAdministrationList(CommRdEmployeeDto commRdEmployeeDto) ;
    /**
     * 研发人员表导出
     * @param commRdEmployeeDto 对象
     * @param
     */
    List<PpmRdEmployeeInfoExportDto> queryPagerInfo(CommRdEmployeeDto commRdEmployeeDto) ;

    /**
     * 导出当前考勤信息
     * @param rdManagementProject
     * @return
     */
    List<List<HrProject>> attendanceAuditInfo(RdManagementProject rdManagementProject);
    /**
     * 分页
     * @param commEmployeeDto 对象
     * @param pager 分页对象
     */
    void emqueryPager(CommEmployeeDto commEmployeeDto, Pager pager) ;

    /**
     * 添加
     * @param  commArrivarEmployDto
     */
    void add(List<CommRdEmployeeDto> commArrivarEmployDto) ;
    /**
     * 删除
     * @param  commRdEmployeeDto
     */
    Integer delete(CommRdEmployeeDto commRdEmployeeDto) ;

    /**
     * 部门组织树-2
     *
     * @param commEmployeeDto
     * @return List<CommTreeDto>
     */
    List<CommTreeDto> queryDeptTree(CommEmployeeDto commEmployeeDto);

    /**
     * 获取部门
     * @param commEmployeeDto
     * @return
     */
    List<CommRdDepartment>queryDepartment(CommEmployeeDto commEmployeeDto);
     /**
     * 修改
     *
     * @param commRdEmployeeDto
     * @return
     */
    void edit(CommRdEmployeeDto commRdEmployeeDto);
    /**
     * 项目查询
     *
     * @param commRdEmployeeDto
     * @return List<HrProject>
     */
    List<HrProject> rdProject(CommRdEmployeeDto commRdEmployeeDto);
    /**
     * 添加项目
     *
     * @param commRdEmployeeProjectDto
     * @return
     */
    void addProject(CommRdEmployeeProjectDto commRdEmployeeProjectDto);

    /**
     * 删除项目
     *
     * @param  hrProject, commRdEmployeeDto
     * @return
     */
    void prDelete(HrProject hrProject, CommRdEmployeeDto commRdEmployeeDto);


    /**
     * 查询研发人员项目
     *
     * @param rdQueryDeptType
     * @return List<CommTreeDto>
     */
    List<CommRdEmployeeDto> rdQueryDeptTree(RdQueryDeptType rdQueryDeptType, CommOperatorDoLoginDto user);

    /**
     * 项目查询
     *
     * @param commRdEmployeeDto
     * @return List<HrProject>
     */
    List<HrProject> rdQueryProject(CommRdEmployeeDto commRdEmployeeDto);

    /**
     * 全员项目分配表
     * @param commRdEmployeeDto
     * @return
     */
    List<PpmAttendanceInfoExportDto> rdQueryProjectInfo(CommRdEmployeeDto commRdEmployeeDto,Pager pager);
    /**
     * 定时任务
     * @param ppmKqCalendarDto
     */

    void atManagement(PpmKqCalendarDto ppmKqCalendarDto);

    /**
     * 考勤期间管理
     * @param ppmKqRdCalendar
     */

    void atDateManagement(PpmKqRdCalendar ppmKqRdCalendar,Pager pager);

    /**
     * 查询考勤填报考勤信息
     * @param rdManagementProject
     */
    List<HrProject> rdManagementProject(RdManagementProject rdManagementProject);

    /**
     * 考勤查询
     * @param rdManagementProject
     */

    List<HrProject> queryAttendanceTabulation(RdManagementProject rdManagementProject);

    /**
     * 查询考勤请假信息
     * @param rdManagementProject
     * @return
     */
    List<HrProject> attendanceRecord(RdManagementProject rdManagementProject);

    /**
     * 查询员工考勤信息
     * @param rdManagementProject
     * @return
     */
    List<HrProject> qyeryEmployeeAttendance(RdManagementProject rdManagementProject);
    /**
     * 填报考勤信息
     * @param hrProjects
     */

//    Integer keep(List<HrProject> hrProjects);

    /**
     * 填报考勤信息
     * @param rdProjectAttendance
     * @return
     */

    Integer atEditManagement(RdProjectAttendance rdProjectAttendance);

    /**
     * 考勤期间管理
     * @param hrProjects
     */
    void submit(List<HrProject> hrProjects);

    /**
     * 驳回提交考勤填报考勤信息
     * @param rdManagementProject
     */
    void withdraw(RdManagementProject rdManagementProject);
    /**
     * 审核考勤填报考勤信息
     * @param rdManagementProject
     */
    List<HrProject> attendanceAudit(RdManagementProject rdManagementProject);

    /**
     * 审核通过
     * @param rdManagementProject
     * @param userid
     */
    void submitAudit(RdManagementHrProject rdManagementProject, String userid);
    /**
     * 勤填报提交造册
     * @param  rdProjectAttendance
     */
    void submitTabulation(RdProjectAttendance rdProjectAttendance);

    /**
     * 导出当前考勤信息
     * @param rdManagementProject
     * @return
     */
    List<PpmRdHrProjectInfoExportDto> outputCurrent(RdManagementProject rdManagementProject);

    /**
     * 驳回提交考勤填报考勤信息
     * @param rdManagementProject
     */
//    void reject(RdManagementProject rdManagementProject);

}
