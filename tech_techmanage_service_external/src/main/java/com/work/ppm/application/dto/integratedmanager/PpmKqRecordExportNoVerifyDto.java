package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 *  考勤记录未确认异常考勤导出 传输对象
 */
@Data
public class PpmKqRecordExportNoVerifyDto {

    /**
    *	工号 JOB_ID
    **/
    @Excel(name = "系统编号", orderNum = "1", width = 12)
    private String jobId;
    /**
     *	姓名 USER_NAME
     **/
    @Excel(name = "姓名", orderNum = "2")
    private String userName;
    /**
     *	供应商名称
     **/
    @Excel(name = "供应商名称", orderNum = "3" , width = 30)
    private String supplierName;
    /**
    *	部门名称 DEPT_NAME
    **/
    @Excel(name = "部门名称", orderNum = "4", width = 14)
    private String deptName;

    /**
     *	项目经理名称 PM_NAME
     **/
    @Excel(name = "项目经理", orderNum = "5")
    private String pmName;

    /**
    *	考勤日期 ATTENDANCE_DATE
    **/
    @Excel(name = "考勤日期", orderNum = "6", format = "yyyy-MM-dd")
    private Date attendanceDate;

    /**
     *	外包方式
     **/
    @Excel(name = "考勤日期外包方式", orderNum = "7")
    private String outsourceTypeName;

    /**
     *	上午异常原因 AM_ABNORMAL_REASON
     **/
    @Excel(name = "异常类型", orderNum = "8",replace = {"上午_AM","下午_PM"})
    private String dataType;

    /**
    *	上班时间 WORKING_TIME
    **/
    @Excel(name = "上班时间", orderNum = "9",exportFormat = "HH:mm:ss",width = 12)
    private Date workingTime;
    /**
    *	下班时间 CLOSING_TIME
    **/
    @Excel(name = "下班时间", orderNum = "10",exportFormat = "HH:mm:ss",width = 12)
    private Date closingTime;

    /**
     *	是否处理 WHETHER_HANDLE
     **/
    @Excel(name = "是否处理", orderNum = "11",replace = {"未处理_1","自动处理_4"})
    private String whetherHandle;

}
