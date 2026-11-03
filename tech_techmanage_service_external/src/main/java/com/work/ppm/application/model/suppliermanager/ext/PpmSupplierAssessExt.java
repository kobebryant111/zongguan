package com.work.ppm.application.model.suppliermanager.ext;

import com.work.ppm.application.model.suppliermanager.PpmSupplierAssess;
import lombok.Data;


/**
 * 供应商考核信息表 实体对象
 */
@Data
public class PpmSupplierAssessExt extends PpmSupplierAssess {
   /**
    *	项目经理名称
    **/
   private String pmName;
   /**
    *	供应商名称
    **/
   private String supplierName;
   /**
    *	项目名称
    **/
   private String projectName;
   /**
    * 项目经理 查询条件
    */
   private String pmValue;
   /**
    * 部门名称
    */
   private String deptName;
   /**
    * 项目经理对应人数
    */
   private String personCount;
   /**
    * 年份开始时间
    */
   private String startTime;
   /**
    * 年份结束时间
    */
   private String endTime;
   /**
    * 部门编号查询
    */
   private String deptIdQue;
   /**
    * 项目经理类型名称
    */
   private String pmTypeName;
   /**
    *部门名称模糊查询
     */
   private String projectNameVal;

}
