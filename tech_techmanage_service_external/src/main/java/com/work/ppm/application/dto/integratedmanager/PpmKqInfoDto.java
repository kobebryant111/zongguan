package com.work.ppm.application.dto.integratedmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 *  考勤信息表 传输对象
 */
@Data
public class PpmKqInfoDto {
    /**
    *	唯一编号 ID
    **/
    @ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
    private Long id;
    /**
    *	工号 JOB_ID
    **/
    @ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
    private String jobId;
    /**
    *	证件号 CERTIFICATE_NUMBER
    **/
    private String certificateNumber;
    /**
    *	部门编号 DEPT_ID
    **/
    private String deptId;
    /**
    *	部门名称 DEPT_NAME
    **/
    private String deptName;
    /**
     *	姓名 USER_NAME
     **/
    @ApiModelProperty(name = "userName", value = "姓名", dataType = "String")
    private String userName;
    /**
    *	供应商编号 SUPPLIER_ID
    **/
    private String supplierId;
    /**
    *	供应商名称 SUPPLIER_NAME
    **/
    private String supplierName;

    /**
    *	考勤年月 ATTENDANCE_MONTH
    **/
    @ApiModelProperty(name = "attendanceMonth", value = "考勤年月", dataType = "String")
    private String attendanceMonth;
    /**
     *	外包方式
     **/
    @ApiModelProperty(name = "outsourceType", value = "外包方式", dataType = "String")
    private String outsourceType;
    /**
    *	项目经理编号 PM_ID
    **/
    private String pmId;
    /**
    *	项目经理名称 PM_NAME
    **/
    private String pmName;

    /**
     * 异常次数
     */
    @ApiModelProperty(name = "exceptionNumber", value = "异常次数", dataType = "Long")
    private Long exceptionNumber;

    /**
     * 忘记打卡次数
     */
    @ApiModelProperty(name = "forgetClockInNumber", value = "忘记打卡次数", dataType = "Long")
    private Long forgetClockInNumber;

    /**
     * 迟到次数
     */
    @ApiModelProperty(name = "belateNumber", value = "迟到次数", dataType = "Long")
    private Long belateNumber;
    /**
     * 早退次数
     */
    @ApiModelProperty(name = "leaveEarlyNumber", value = "早退次数", dataType = "Long")
    private Long leaveEarlyNumber;
    /**
     * 旷工次数
     */
    @ApiModelProperty(name = "absentNumber", value = "旷工次数", dataType = "Long")
    private Long absentNumber;
    /**
     * 请假次数
     */
    @ApiModelProperty(name = "leaveNumber", value = "请假次数", dataType = "Long")
    private Long leaveNumber;
    /**
     * 当月应出勤天数
     */
    @ApiModelProperty(name = "requiredAttendanceDays", value = "当月应出勤天数", dataType = "Double")
    private Double requiredAttendanceDays;
    /**
     * 实际出勤天数
     */
    @ApiModelProperty(name = "actualAttendanceDays", value = "实际出勤天数", dataType = "Double")
    private Double actualAttendanceDays;
    /**
     * 加班时长
     */
    @ApiModelProperty(name = "overtimeHours", value = "加班时长", dataType = "Double")
    private Double overtimeHours;
    /**
     * 考勤记录是否更新 0需更新1无需更新 WHETHER_UPDATE
     */
    @ApiModelProperty(name = "whetherUpdate", value = "考勤记录是否更新 0需更新1无需更新", dataType = "String")
    private String whetherUpdate;
    /**
     * 第五个工作日 FIFTH_WORKING_DATE
     */
    @ApiModelProperty(name = "fifthWorkingDate", value = "考勤记录是否更新 0需更新1无需更新", dataType = "Date")
    private Date fifthWorkingDate;
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
     *	状态
     **/
    private String state;
    /**
     *	状态名称
     **/
    private String stateName;
    /**
    *	入场时间
    **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    /**
    *	离场时间
    **/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leavingTime;
    /**
     * 系统名称/项目名称
    **/
    private String systemProjectName;
    /**
     * 系统名称
     **/
    private String systemName;
    /**
     * 项目名称
     **/
    private String projectName;
    /**
     * 外包方式名称
     */
    private String outsourceTypeName;
    /**
     * 对应考勤记录信息
     */
    private List<PpmKqRecordDto> kqRecordDtoList;

    /**
     * 项目经理 参数
     */
    private String pmValue;

    /**
     * 部门 参数
     */
    private String deptValue;
    /**
     * 去年年假天数
     */
    private Double annualVacationSurplus;
    /**
     * 可增加年假起始日期
     */
    private Date annualVacationDate;
    /**
     * 今年年假天数
     */
    private Double annualVacationDays;
    /**
     * 现有剩余年假
     */
    private Double annualVacationNumber;
    /**
     * 豁免天数 EXEMPTION_DAYS
     */
    private Long exemptionNumber;

    /**
     * 是否通宵
     */
    private String whetherOvernight;
    /**
     * 考勤日期
     */
    private String attendanceDate;
    /**
     * 上班时间
     */
    private String workingTime;
    /**
     * 下班时间
     */
    private String closingTime;
    /**
     * 加班开始时间
     */
    private String overtimeStartTime;
    /**
     * 是否节假日
     */
    private String isHoliday;
    /**
     * 加班时长(分钟)
     */
    private String overtimeMinute;
    /**
     * 上午是否异常
     */
    private String amWhetherAbnormal;
    /**
     * 上午异常原因
     */
    private String amAbnormalReason;
    /**
     * 上午异常备注
     */
    private String amRemark;
    /**
     * 下午是否异常
     */
    private String pmWhetherAbnormal;
    /**
     * 下午异常原因
     */
    private String pmAbnormalReason;
    /**
     * 下午异常备注
     */
    private String pmRemark;

    /**
     * 时间类型
     */
    private String timeType;

    /**
     * 其他导出时间
     */
    private String otherExportTime;

    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 筛选时间开启
     */
    private String attendanceDateSt;

    /**
     * 筛选时间结束
     */
    private String attendanceDateEnd;

    /**
     * 每月合理异常天数
     */
    private Long monthlyReasonableAbnormalDays;

}
