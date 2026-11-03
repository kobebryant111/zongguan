package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmKqInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 考勤信息表 实体对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PpmKqInfoView extends PpmKqInfo {

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
   *	供应商编号 SUPPLIER_ID
   **/
   private String supplierId;
   /**
    *	供应商名称 SUPPLIER_NAME
    **/
   private String supplierName;
   /**
    *	外包方式 OUTSOURCE_TYPE
    **/
   private String outsourceType;
   /**
    *	项目经理 PM_ID
    **/
   private String pmId;
   /**
    *	项目经理名称 PM_NAME
    **/
   private String pmName;
   /**
    *	外包方式名称 OUTSOURCE_TYPE_NAME
   **/
   private String outsourceTypeName;
   /**
    *	状态 STATE
    **/
   private String state;
   /**
    *	状态名称 STATENAME
    **/
   private String statename;
   /**
    *	入场时间 ENTRY_TIME
    **/
   private String entryTime;
   /**
    *	离场时间 LEAVING_TIME
    **/
   private String leavingTime;
   /**
    *	项目名称 PROJECTNAME
    **/
   private String projectName;
   /**
    *	系统名称 SYSTEMNAMES
    **/
   private String systemName;
   /**
    *	系统/项目名称 SYSTEMPROJECTNAME
    **/
   private String systemProjectName;
   /**
    *	项目经理筛选
    **/
   private String pmValue;
   /**
    *	部门筛选
    **/
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
    * 是否通宵
    */
   private String whetherOvernight;

   /**
    * 是否通宵
    */
   private String attendanceDateSt;

   /**
    * 是否通宵
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
