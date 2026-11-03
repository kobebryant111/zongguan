package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;

/**
 * 考勤记录表 实体对象
 */
@Data
public class PpmKqRecord {
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
   *	姓名 USER_NAME
   **/
   private String userName;
   /**
   *	考勤日期 ATTENDANCE_DATE
   **/
   private Date attendanceDate;
   /**
    *	外包方式 OUTSOURCE_TYPE
    **/
   private String outsourceType;
   /**
    *	上班时间 WORKING_TIME
    **/
   private Date workingTime;
   /**
    *	下班时间 CLOSING_TIME
    **/
   private Date closingTime;
   /**
    *	加班时长(min) OVERTIME_HOURS
    **/
   private Long overtimeHours;
   /**
    *	是否通宵加班 WHETHER_OVERNIGHT
    **/
   private String whetherOvernight;
   /**
    *	加班开始时间 OVERTIME_START_TIME
    **/
   private String overtimeStartTime;
   /**
    *	上午是否异常 AM_WHETHER_ABNORMAL
    **/
   private String amWhetherAbnormal;
   /**
    *	下午是否异常 PM_WHETHER_ABNORMAL
    **/
   private String pmWhetherAbnormal;
   /**
    *	上午异常原因 ABNORMAL_REASON
    **/
   private String amAbnormalReason;
   /**
    *	下午异常原因 ABNORMAL_REASON
    **/
   private String pmAbnormalReason;
   /**
    *	数据录入类别（导入import/同步sync） DATA_ENTRY_TYPE
    **/
   private String dataEntryType;
   /**
    *	上午是否处理（0未处理1已处理） AM_WHETHER_HANDLE
    **/
   private String amWhetherHandle;
   /**
    *	下午是否处理（0未处理1已处理） PM_WHETHER_HANDLE
    **/
   private String pmWhetherHandle;
   /**
    * 单价
    */
   private String prices;
   /**
    *	是否节假日 IS_HOLIDAY
    **/
   private String isHoliday;
   /**
    *	加班时长是否已计算 IS_CAL_OVERTIME
    **/
   private String isCalOvertime;
   /**
    *	创建时间 CREATED_TIME
    **/
   private Date createdTime;
   /**
    *	创建人 CREATED_BY
    **/
   private String createdBy;
   /**
    *	修改时间 MODIFIED_TIME
    **/
   private Date modifiedTime;
   /**
    *	修改人 MPDIFIED_BY
    **/
   private String modifiedBy;
   /**
    *   上午备注 AM_REMARK
    **/
   private String amRemark;
   /**
    *   下午备注 PM_REMARK
    **/
   private String pmRemark;
   /**
    * 上午计算异常原因
    **/
   private String autoAmAbnormalReason;
   /**
    * 下午计算异常原因
    **/
   private String autoPmAbnormalReason;
   /**
    * 单价 WHETHER_DAYS_OFF
    */
   private String whetherDaysOff;
   /**
    * 豁免状态 EXEMPTION_STATE
    */
   private Long exemptionState;
}
