package com.work.ppm.application.model.bankmanager.ext;

import com.work.ppm.application.model.bankmanager.PpmEmpKqInfo;
import com.work.ppm.application.model.integratedmanager.PpmKqInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 考勤信息表 实体对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PpmEmpKqInfoView extends PpmEmpKqInfo {

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
    *	项目经理 PM_ID
    **/
   private String pmId;
   /**
    *	项目经理名称 PM_NAME
    **/
   private String pmName;
   /**
    *	状态 STATE
    **/
   private String state;
   /**
    *	状态名称 STATENAME
    **/
   private String statename;
   /**
    *	项目经理筛选
    **/
   private String pmValue;
   /**
    *	部门筛选
    **/
   private String deptValue;
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
    * 是否通宵
    */
   private String whetherOvernight;

   /**
    * 考勤时间 范围查询 开始
    */
   private String attendanceDateSt;

   /**
    * 考勤时间 范围查询 结束
    */
   private String attendanceDateEnd;

   /**
    * 数据类型
    */
   private String dataType;
   /**
    * 是否已处理
    */
   private String whetherHandle;





}
