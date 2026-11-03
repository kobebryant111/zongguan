package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  考勤月明细导出 传输对象
 */
@Data
public class PpmKqInfoExportDto {

    /**
     *	系统编号 JOB_ID
     **/
    @Excel(name = "系统编号", orderNum = "1")
    private String jobId;
    /**
     *	姓名 USER_NAME
     **/
    @Excel(name = "姓名", orderNum = "2")
    private String userName;
    /**
     *	部门名称 DEPT_NAME
     **/
    @Excel(name = "所属部门", orderNum = "3")
    private String deptName;
    /**
     * 外包方式名称
     */
    @Excel(name = "外包方式", orderNum = "4")
    private String outsourceTypeName;
    /**
     * 系统名称
     **/
    @Excel(name = "系统名称", orderNum = "5")
    private String systemName;
    /**
     * 项目名称
     **/
    @Excel(name = "项目名称", orderNum = "6")
    private String projectName;
    /**
     *	项目经理名称 PM_NAME
     **/
    @Excel(name = "项目经理", orderNum = "7")
    private String pmName;
    /**
     *	状态名称
     **/
    @Excel(name = "状态", orderNum = "8")
    private String stateName;
    /**
     *	入场时间
     **/
    @Excel(name = "入场时间", orderNum = "9",format = "yyyy-MM-dd")
    private Date entryTime;
    /**
     *	离场时间
     **/
    @Excel(name = "离场时间", orderNum = "10",format = "yyyy-MM-dd")
    private Date leavingTime;
    /**
     *	供应商名称 SUPPLIER_NAME
     **/
    @Excel(name = "供应商（全称）", orderNum = "11")
    private String supplierName;
    /**
     *	考勤年月 ATTENDANCE_MONTH
     **/
    @Excel(name = "考勤年月", orderNum = "12")
    private String attendanceMonth;
    /**
     * 当月应出勤天数
     */
    @Excel(name = "应出勤天数", orderNum = "13")
    private Double requiredAttendanceDays;
    /**
     * 实际出勤天数
     */
    @Excel(name = "实际出勤天数", orderNum = "14")
    private Double actualAttendanceDays;
    /**
     * 加班时长
     */
    @Excel(name = "当月加班时长", orderNum = "15")
    private Double overtimeHours;
    /**
     * 忘记打卡次数
     */
    @Excel(name = "忘记打卡次数", orderNum = "16")
    private Long forgetClockInNumber;
    /**
     * 迟到次数
     */
    @Excel(name = "迟到次数", orderNum = "17")
    private Long belateNumber;
    /**
     * 早退次数
     */
    @Excel(name = "早退次数", orderNum = "18")
    private Long leaveEarlyNumber;
    /**
     * 异常次数
     */
    @Excel(name = "异常次数", orderNum = "19")
    private Long exceptionNumber;
    /**
     * 旷工次数
     */
    private Long absentNumber;



}
