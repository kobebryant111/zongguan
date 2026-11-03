package com.work.ppm.application.model.otherdeptmanager.ext;

import com.work.ppm.application.model.otherdeptmanager.PpmKqRecordOther;
import lombok.Data;


/**
 * 考勤信息表-其他部门 实体对象
 */
@Data
public class PpmKqRecordOtherExt extends PpmKqRecordOther {

   /**
    * 部门查询
    */
   private String deptVal;
   /**
    * 项目经理查询
    */
   private String pmValue;
   /**
    * 项目经理id
    */
   private String proPerson;
   /**
    * 项目经理名称
    */
   private String proPersonName;
   /**
    * 考勤日期开始
    */
   private String attendanceStart;
   /**
    * 考勤日期结束
    */
   private String attendanceEnd;
   /**
    * 部门名称
    */
   private String deptName;
   /**
    * 用户
    */
   private String userName;
   /**
    * 考勤日期
    */
   private String attendanceDateValue;
   /**
    * 父部门id
    */
   private Integer adminId;
}
