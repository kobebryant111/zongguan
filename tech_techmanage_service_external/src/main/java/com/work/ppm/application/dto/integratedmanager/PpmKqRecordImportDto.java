package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 *  考勤记录表 传输对象
 */
@Data
@ExcelTarget("PpmKqRecordImportDto")
public class PpmKqRecordImportDto {

    /**
     *	自增编号 ID
     **/
    @ApiModelProperty(name = "id", value = "自增编号", dataType = "Long")
    private Long id;
    /**
     *	考勤日期 ATTENDANCE_DATE
     **/
    @Excel(name = "考勤日期", orderNum = "1",format = "yyyy-MM-dd")
    private Date attendanceDate;
    /**
    *	工号 JOB_ID
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
    @Excel(name = "上午上班时间",orderNum = "5" ,databaseFormat = "HH:mm:ss",importFormat = "HH:mm:ss")
    private String morningWorkingTime;
    /**
     *	上午下班时间 MORNING_CLOSING_TIME
     **/
    @Excel(name = "上午下班时间",orderNum = "6",format = "HH:mm:ss")
    private String morningClosingTime;
    /**
     *	下午上班时间 AFTERNOON_WORKING_TIME
     **/
    @Excel(name = "下午上班时间",orderNum = "7",format = "HH:mm:ss")
    private String afternoonWorkingTime;
    /**
     *	下午下班时间 AFTERNOON_CLOSING_TIME
     **/
    @Excel(name = "下午下班时间",orderNum = "8",format = "HH:mm:ss")
    private String afternoonClosingTime;
    /**
     *	加班签到时间 OVERTIME_TIME
     **/
    @Excel(name = "加班时间",orderNum = "9",format = "HH:mm:ss")
    private String overtimeTime;

    /**
    *	打卡时间 CLOCK_TIME
    **/
    @ApiModelProperty(name = "clockTime", value = "打卡时间", dataType = "String")
    private String clockTime;
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
     *	导入状态 IMPORT_STATE
     **/
    @ApiModelProperty(name = "importState", value = "导入状态", dataType = "String")
    private String importState;
    /**
     *	模板类型 TEMPLATE_TYPE
     **/
    @ApiModelProperty(name = "templateType", value = "模板类型", dataType = "String")
    private String templateType;
    /**
     *	创建时间 CREATED_TIME
     **/
    @ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
    private Date createdTime;
    /**
     *	创建人 CREATED_BY
     **/
    @ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
    private String createdBy;
    /**
     *	修改时间 MODIFIED_TIME
     **/
    @ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
    private Date modifiedTime;
    /**
     *	创建人 MODIFIED_BY
     **/
    @ApiModelProperty(name = "modifiedBy", value = "创建人", dataType = "String")
    private String modifiedBy;
    /**
     *	是否通宵 WHETHER_OVERNIGHT
     **/
    @ApiModelProperty(name = "whetherOvernight", value = "是否通宵", dataType = "String")
    private String whetherOvernight;

}
