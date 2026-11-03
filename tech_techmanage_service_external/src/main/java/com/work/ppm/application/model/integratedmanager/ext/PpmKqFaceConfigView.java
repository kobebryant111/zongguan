package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmKqFaceConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * 考勤信息表 实体对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PpmKqFaceConfigView extends PpmKqFaceConfig {

   /**
   *	部门编号 DEPT_ID
   **/
   private String deptId;
   /**
   *	部门名称 DEPT_NAME
   **/
   private String deptName;
   /**
    *	姓名 PM_ID
    **/
   private String userName;
   /**
    *	在场状态 PRESENCE_STATE
    **/
   private String presenceState;
   /**
    *	在岗状态 STATE
    **/
   private String state;
   /**
    * 父部门id
    */
   private Integer adminId;



}
