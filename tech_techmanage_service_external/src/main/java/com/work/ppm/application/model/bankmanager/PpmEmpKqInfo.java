package com.work.ppm.application.model.bankmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 考勤信息表 实体对象
 *
 */
@Data
public class PpmEmpKqInfo {
    /**
     * 唯一编号 ID
     **/
    @Id
    private Long id;
    /**
     * 工号 JOB_ID
     **/
    private String jobId;
    /**
     * 姓名 USER_NAME
     **/
    private String userName;
    /**
     * 考勤年月 ATTENDANCE_MONTH
     **/
    private String attendanceMonth;
    /**
     * 加班时长 /h OVERTIME_HOURS
     **/
    private Double overtimeHours;
    /**
     * 工作时长 /h WORKTIME_HOURS
     **/
    private Double worktimeHours;
    /**
     * 忘记打卡次数 FORGET_CLOCK_IN_NUMBER
     **/
    private Long forgetClockInNumber;
    /**
     * 迟到次数 BELATE_NUMBER
     **/
    private Long belateNumber;
    /**
     * 早退次数 LEAVE_EARLY_NUMBER
     **/
    private Long leaveEarlyNumber;
    /**
     * 旷工次数 ABSENT_NUMBER
     **/
    private Long absentNumber;
    /**
     * 请假次数 leaveNumber
     **/
    private Long leaveNumber;
    /**
     * 当月应出勤天数 REQUIRED_ATTENDANCE_DAYS
     **/
    private Double requiredAttendanceDays;
    /**
     * 实际出勤天数 ACTUAL_ATTENDANCE_DAYS
     **/
    private Double actualAttendanceDays;
    /**
     * 考勤记录是否更新 0需更新1无需更新 WHETHER_UPDATE
     **/
    private String whetherUpdate;
    /**
     * 创建时间 CREATED_TIME
     **/
    private Date createdTime;
    /**
     * 创建人 CREATED_BY
     **/
    private String createdBy;

}
