package com.work.ppm.application.model.integratedmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 导入考勤记录表 实体对象
 */
@Data
public class PpmKqRecordImport {
   /**
   * 自增编号 ID
   **/
   @Id
   private Long id;
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
   *	上午上班时间 MORNING_WORKING_TIME
   **/
   private String morningWorkingTime;
   /**
   *	上午下班时间 MORNING_CLOSING_TIME
   **/
   private String morningClosingTime;
   /**
   *	下午上班时间 AFTERNOON_WORKING_TIME
   **/
   private String afternoonWorkingTime;
   /**
   *	下午下班时间 AFTERNOON_CLOSING_TIME
   **/
   private String afternoonClosingTime;
   /**
    *	加班时间 OVERTIME_TIME
    **/
   private String overtimeTime;
   /**
    *	打卡时间 CLOCK_TIME
    **/
   private String clockTime;
   /**
    *	上午异常原因 AM_ABNORMAL_REASON
    **/
   private String amAbnormalReason;
   /**
    *	下午异常原因 PM_ABNORMAL_REASON
    **/
   private String pmAbnormalReason;
   /**
    *	导入状态 IMPORT_STATE
    **/
   private String importState;
   /**
    *	模板类型 TEMPLATE_TYPE
    **/
   private String templateType;
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
    *	修改人 MODIFIED_BY
    **/
   private String modifiedBy;
}
