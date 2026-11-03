package com.work.ppm.application.model.bankmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 行员考勤记录表 实体对象
 *
 */
@Data
public class PpmEmpKqRecord {
    /**
     * 唯一编号 ID
     **/
    @Id
    private Long id;
    /**
     * 自增编号 INFO_ID
     **/
    private Long infoId;
    /**
     * 工号 JOB_ID
     **/
    private String jobId;
    /**
     * 姓名 USER_NAME
     **/
    private String userName;
    /**
     * 考勤日期 ATTENDANCE_DATE
     **/
    private Date attendanceDate;
    /**
     * 上班时间 WORKING_TIME
     **/
    private Date workingTime;
    /**
     * 下班时间 CLOSING_TIME
     **/
    private Date closingTime;
    /**
     * 加班时长(min) OVERTIME_HOURS
     **/
    private Long overtimeHours;
    /**
     * 工作时长(min) WORKTIME_HOURS
     **/
    private Long worktimeHours;
    /**
     * 是否通宵加班 WHETHER_OVERNIGHT
     **/
    private String whetherOvernight;
    /**
     * 加班开始时间 OVERTIME_START_TIME
     **/
    private String overtimeStartTime;
    /**
     * 数据录入类别（导入import/同步sync） DATA_ENTRY_TYPE
     **/
    private String dataEntryType;
    /**
     * 是否节假日 IS_HOLIDAY
     **/
    private String isHoliday;
    /**
     * 加班时长是否已计算 IS_CAL_OVERTIME
     **/
    private String isCalOvertime;
    /**
     * 创建时间 CREATED_TIME
     **/
    private Date createdTime;
    /**
     * 创建人 CREATED_BY
     **/
    private String createdBy;
    /**
     * 修改时间 MODIFIED_TIME
     **/
    private Date modifiedTime;
    /**
     * 修改人 MPDIFIED_BY
     **/
    private String modifiedBy;
    /**
     * 是否可调休 WHETHER_DAYS_OFF
     */
    private String whetherDaysOff;
    /**
     * 是否异常 WHETHER_ABNORMAL
     */
    private String whetherAbnormal;
}
