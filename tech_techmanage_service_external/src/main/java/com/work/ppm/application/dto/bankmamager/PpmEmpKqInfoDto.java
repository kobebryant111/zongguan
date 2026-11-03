package com.work.ppm.application.dto.bankmamager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 行员考勤信息表 传输对象
 *
 */
@Data
public class PpmEmpKqInfoDto {
    /**
     * 唯一编号 ID
     **/
    @ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
    private Long id;
    /**
     * 工号 JOB_ID
     **/
    @ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
    private String jobId;
    /**
     * 证件号 CERTIFICATE_NUMBER
     **/
    private String certificateNumber;
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
     * 考勤年月 ATTENDANCE_MONTH
     **/
    @ApiModelProperty(name = "attendanceMonth", value = "考勤年月", dataType = "String")
    private String attendanceMonth;
    /**
     * 项目经理编号 PM_ID
     **/
    private String pmId;
    /**
     * 项目经理名称 PM_NAME
     **/
    private String pmName;
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
     * 工作时长
     */
    @ApiModelProperty(name = "worktimeHours", value = "工作时长", dataType = "Double")
    private Double worktimeHours;
    /**
     * 考勤记录是否更新 0需更新1无需更新 WHETHER_UPDATE
     */
    @ApiModelProperty(name = "whetherUpdate", value = "考勤记录是否更新 0需更新1无需更新", dataType = "String")
    private String whetherUpdate;
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
     * 状态
     **/
    private String state;
    /**
     * 状态名称
     **/
    private String stateName;
    /**
     * 对应考勤记录信息
     */
    private List<PpmEmpKqRecordDto> kqRecordDtoList;

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
     * 工作时长(分钟)
     */
    private String worktimeMinute;

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

}
