package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;


/**
 *  考勤记录导入失败导出 传输对象
 */
@Data
@ExcelTarget("PpmKqRecordExportDto")
public class PpmKqRecordExportDto {

    /**
     *	考勤日期 ATTENDANCE_DATE
     **/
    @Excel(name = "考勤日期", orderNum = "1",format = "yyyy-MM-dd")
    private Date attendanceDate;
    /**
    *	人员编号 JOB_ID
    **/
    @Excel(name = "系统编号", orderNum = "2")
    private String jobId;
    /**
    *	姓名 USER_NAME
    **/
    @Excel(name = "姓名", orderNum = "3")
    private String userName;
    /**
     *	外包方式 OUTSOURCE_TYPE
     **/
    @Excel(name = "外包方式", orderNum = "4",replace = {"人力外包_1","项目外包_0"})
    private String outsourceType;
    /**
     *	上午上班时间 MORNING_WORKING_TIME
     **/
    @Excel(name = "上午上班时间",orderNum = "5",format = "HH:mm:ss")
    private Date morningWorkingTime;
    /**
     *	上午下班时间 MORNING_CLOSING_TIME
     **/
    @Excel(name = "上午下班时间",orderNum = "6",format = "HH:mm:ss")
    private Date morningClosingTime;
    /**
     *	下午上班时间 AFTERNOON_WORKING_TIME
     **/
    @Excel(name = "下午上班时间",orderNum = "7",format = "HH:mm:ss")
    private Date afternoonWorkingTime;
    /**
     *	下午下班时间 AFTERNOON_CLOSING_TIME
     **/
    @Excel(name = "下午下班时间",orderNum = "8",format = "HH:mm:ss")
    private Date afternoonClosingTime;
    /**
     *	加班时间 OVERTIME_TIME
     **/
    @Excel(name = "加班时间",orderNum = "9",format = "HH:mm:ss")
    private Date overtimeTime;

    /**
     *	上午异常原因 AM_ABNORMAL_REASON
     **/
    @Excel(name = "上午异常原因",orderNum = "12")
    private String amAbnormalReason;
    /**
     *	下午异常原因 PM_ABNORMAL_REASON
     **/
    @Excel(name = "下午异常原因",orderNum = "13")
    private String pmAbnormalReason;

    /**
     *	备注
     **/
    @Excel(name = "备注",orderNum = "14")
    private String remark;

}
