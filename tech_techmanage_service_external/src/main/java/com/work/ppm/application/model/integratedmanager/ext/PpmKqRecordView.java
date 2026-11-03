package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmKqRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 考勤信息表 实体对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PpmKqRecordView extends PpmKqRecord {

   /**
   *	部门编号 DEPT_ID
   **/
   private String deptId;
   /**
   *	部门名称 DEPT_NAME
   **/
   private String deptName;
   /**
    *	项目经理 PM_ID
    **/
   private String pmId;
   /**
    *	项目经理名称 PM_NAME
    **/
   private String pmName;
   /**
    * where 条件参数1
    **/
   private String whetherKeyOne;
   /**
    * where 条件参数2
    **/
   private String whetherKeyTwo;
   /**
    * 打卡时间 punchTime
    **/
   private Date punchTime;
   /**
    * 是否异常 whetherAbnormal
    **/
   private String whetherAbnormal;
   /**
    * 是否处理 whetherHandle
    **/
   private String whetherHandle;
   /**
    * 异常原因 abnormalReason
    **/
   private String abnormalReason;
   /**
    * 数据类型 dataType
    **/
   private String dataType;
   /**
    * where条件 attendanceDateValue
    **/
   private String attendanceDateValue;
   /**
    * 考勤时间 范围查询 开始
    */
   private String attendanceDateSt;
   /**
    * 考勤时间 范围查询 结束
    **/
   private String attendanceDateEnd;
   /**
    * 系统计算异常原因
    */
   private String autoAbnormalReason;
   /**
    * 备注
    */
   private String remark;
   /**
    * 今年年假天数 ANNUAL_VACATION_DAYS
    */
   private String annualVacationDays;

   /**
    * 去年年假天数 ANNUAL_VACATION_SURPLUS
    */
   private String annualVacationSurplus;
   /**
    * 可增加年假起始日期 ANNUAL_VACATION_DATE
    */
   private String annualVacationDate;
}
