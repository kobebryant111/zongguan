package com.work.ppm.application.model.otherdeptmanager.ext;

import com.work.ppm.application.model.otherdeptmanager.PpmOutsourcePersonledge;
import lombok.Data;

/**
 * 非科技外包人员台账表 实体对象
 */
@Data
public class PpmOutsourcePersonledgeExt extends PpmOutsourcePersonledge {

   /**
    * 所属部门名称
    **/
   private String deptName;
   /**
    * 供应商名称
    */
   private String supplierName;
   /**
    * 外包方式
    **/
   private String outsourceTypeName;
   /**
    * 项目经理名称
    */
   private String proPersonName;
   /**
    * 所属部门查询
    */
   private String deptVal;
   /**
    * 姓名查询
    */
   private String userNameVal;
   /**
    * 项目经理查询
    */
   private String pmVal;
   /**
    * 父部门id
    */
   private Integer adminId;

}
