package com.work.ppm.application.dto.bankmamager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 行员考勤记录表 传输对象
 *
 */
@Data
public class PpmEmpKqRecordDto {
    /**
     * 自增编号 ID
     **/
    @ApiModelProperty(name = "id", value = "自增编号", dataType = "Long")
    private Long id;
    /**
     * 考勤信息表编号 INFO_ID
     **/
    @ApiModelProperty(name = "infoId", value = "考勤信息表编号", dataType = "Long")
    private Long infoId;
    /**
     * 工号 JOB_ID
     **/
    @ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
    private String jobId;
    /**
     * 部门编号 DEPT_ID
     **/
    private String deptId;
    /**
     * 部门名称 DEPT_NAME
     **/
    private String deptName;
    /**
     * 姓名 USER_NAME
     **/
    @ApiModelProperty(name = "userName", value = "姓名", dataType = "String")
    private String userName;
    /**
     * 考勤日期 ATTENDANCE_DATE
     **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(name = "attendanceDate", value = "考勤日期", dataType = "Date")
    private Date attendanceDate;
    /**
     * 考勤结束日期
     */
    private Date attendanceDateE;

    /**
     * 打卡时间 PUNCH_TIME
     **/
    private Date punchTime;
    /**
     * 上班时间 WORKING_TIME
     **/
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(name = "workingTime", value = "上班时间", dataType = "Date")
    private Date workingTime;
    /**
     * 下班时间 CLOSING_TIME
     **/
    @JsonFormat(pattern = "HH:mm:ss")
    @ApiModelProperty(name = "closingTime", value = "下班时间", dataType = "Date")
    private Date closingTime;
    /**
     * 是否异常 WHETHER_ABNORMAL
     **/
    private String whetherAbnormal;
    /**
     * 加班时长 OVERTIME_HOURS
     **/
    @ApiModelProperty(name = "overtimeHours", value = "加班时长(min)", dataType = "Long")
    private Long overtimeHours;
    /**
     * 工作时长 OVERTIME_HOURS
     **/
    @ApiModelProperty(name = "worktimeHours", value = "工作时长(min)", dataType = "Long")
    private Long worktimeHours;
    /**
     * 是否通宵 WHETHER_OVERNIGHT
     **/
    @ApiModelProperty(name = "whetherOvernight", value = "是否通宵", dataType = "String")
    private String whetherOvernight;
    /**
     * 加班开始时间 OVERTIME_START_TIME
     **/
    @ApiModelProperty(name = "overtimeStartTime", value = "加班开始时间", dataType = "String")
    private String overtimeStartTime;
    /**
     * 项目经理名称 PM_ID
     **/
    private String pmId;
    /**
     * 项目经理名称 PM_NAME
     **/
    private String pmName;

    /**
     * 数据录入类别（导入import/同步sync） DATA_ENTRY_TYPE
     **/
    @ApiModelProperty(name = "dataEntryType", value = "数据录入类别", dataType = "String")
    private String dataEntryType;
    /**
     * 是否节假日 IS_HOLIDAY
     **/
    @ApiModelProperty(name = "isHoliday", value = "是否节假日", dataType = "String")
    private String isHoliday;
    /**
     * 加班时长是否已计算 IS_CAL_OVERTIME
     **/
    @ApiModelProperty(name = "isCalOvertime", value = "加班时长是否已计算", dataType = "String")
    private String isCalOvertime;
    /**
     * 创建时间 CREATED_TIME
     **/
    @ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
    private Date createdTime;
    /**
     * 创建人 CREATED_BY
     **/
    @ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
    private String createdBy;
    /**
     * 修改时间 MODIFTED_TIME
     **/
    @ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
    private Date modifiedTime;
    /**
     * 修改人 MODIFIED_DATE
     **/
    @ApiModelProperty(name = "modifiedBy", value = "修改人", dataType = "String")
    private String modifiedBy;

    /**
     * 单价 WHETHER_DAYS_OFF
     */
    @ApiModelProperty(name = "whetherDaysOff", value = "次日是否可调休（过11点半）", dataType = "String")
    private String whetherDaysOff;

    /**
     * 按钮操作
     **/
    private Boolean actionType;

    /**
     * 是否可修改。
     **/
    private Boolean whetherUpdate;

    /**
     * where 条件参数1
     **/
    private String whetherKeyOne;

    /**
     * where 条件参数2
     **/
    private String whetherKeyTwo;

    /**
     * 数据类型
     **/
    private String dataType;

    /**
     * where条件 attendanceDateValue
     **/
    private String attendanceDateValue;

    /**
     * 上午打卡时间
     */
    private String amPunchTime;
    /**
     * 下午打卡时间
     */
    private String pmPunchTime;

    /**
     * 考勤时间 范围查询 开始
     */
    private String attendanceDateSt;

    /**
     * 考勤时间 范围查询 结束
     **/
    private String attendanceDateEnd;
    /**
     * 可增加年假起始日期 ANNUAL_VACATION_DATE
     **/
    private String annualVacationDate;
    /**
     * 去年年假天数 ANNUAL_VACATION_SURPLUS
     */
    private Double annualVacationSurplus;
    /**
     * 今年年假天数 ANNUAL_VACATION_DAYS
     */
    private Double annualVacationDays;

    private List<Map> reasonSelect;

}
