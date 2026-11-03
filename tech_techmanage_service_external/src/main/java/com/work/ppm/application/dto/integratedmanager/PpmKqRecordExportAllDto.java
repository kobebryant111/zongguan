package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;


/**
 *  考勤日明细导出 传输对象
 */
@Data
@ExcelTarget("PpmKqRecordExportAllDto")
public class PpmKqRecordExportAllDto {

    /**
    *	部门名称 DEPT_NAME
    **/
    @Excel(name = "部门名称", orderNum = "1")
    private String deptName;
    /**
    *	姓名 USER_NAME
    **/
    @Excel(name = "姓名", orderNum = "2")
    private String userName;

    /**
     *	外包方式 OUTSOURCE_TYPE
     **/
    @Excel(name = "供应商", orderNum = "3")
    private String supplierName;

    /**
    *	考勤日期 ATTENDANCE_DATE
    **/
    @Excel(name = "考勤日期", orderNum = "4", exportFormat = "yyyy-MM-dd")
    private Date attendanceDate;

    /**
     *	外包方式 OUTSOURCE_TYPE
     **/
    @Excel(name = "人员性质", orderNum = "5")
    private String outsourceTypeName;

    /**
     *	是否节假日 IS_HOLIDAY
     **/
    @Excel(name = "是否节假日",orderNum = "6",replace = {"否_0","是_1"})
    private String isHoliday;

    /**
    *	上班时间 WORKING_TIME
    **/
    @Excel(name = "上班时间", orderNum = "7")
    private String workingTime;
    /**
    *	下班时间 CLOSING_TIME
    **/
    @Excel(name = "下班时间", orderNum = "8")
    private String  closingTime;

    /**
     *	加班开始时间 OVERTIME_START_TIME
     **/
    @Excel(name = "加班开始时间", orderNum = "9")
    private String overtimeStartTime;
    /**
    *	加班时长 OVERTIME_HOURS
    **/
    @Excel(name = "加班时长", orderNum = "10")
    private String overtimeMinute;

    /**
     *	是否通宵 WHETHER_OVERNIGHT
     **/
    private String whetherOvernight;

    /**
     *	项目经理名称 PM_ID
     **/
    private String pmId;

    /**
    *	项目经理名称 PM_NAME
    **/
    @Excel(name = "项目经理", orderNum = "11")
    private String pmName;

    /**
     *	上午是否异常 AM_WHETHER_ABNORMAL
     **/
    @Excel(name = "上午是否异常", orderNum = "12",replace = {"否_0","是_1"})
    private String amWhetherAbnormal;

    /**
     *	上午异常原因 AM_ABNORMAL_REASON
     **/
    @Excel(name = "上午异常原因", orderNum = "13",
            replace = {"忘记打卡_0","迟到_1","早退_2","年假半天_3","事假半天_5","旷工半天_7",
                    "调休_11","忘打卡_12","迟到_13","早退_14","事假半天_15","其他_16"})
    private String amAbnormalReason;

    /**
     * 上午异常备注
     **/
    @Excel(name = "上午异常备注", orderNum = "14")
    private String amRemark;
    /**
     *	下午是否异常 PM_WHETHER_ABNORMAL
     **/
    @Excel(name = "下午是否异常", orderNum = "15",replace = {"否_0","是_1"})
    private String pmWhetherAbnormal;

    /**
     *	下午异常原因 PM_ABNORMAL_REASON
     **/
    @Excel(name = "下午异常原因", orderNum = "16",
            replace = {"忘记打卡_0","迟到_1","早退_2","年假半天_3","事假半天_5","旷工半天_7",
                    "调休_11","忘打卡_12","迟到_13","早退_14","请假_15"})
    private String pmAbnormalReason;

    /**
     * 下午异常备注
     **/
    @Excel(name = "下午异常备注", orderNum = "17")
    private String pmRemark;
}
