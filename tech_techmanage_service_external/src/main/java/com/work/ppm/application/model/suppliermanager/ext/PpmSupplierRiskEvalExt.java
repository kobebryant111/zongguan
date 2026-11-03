package com.work.ppm.application.model.suppliermanager.ext;

import com.work.ppm.application.model.suppliermanager.PpmSupplierRiskEval;
import lombok.Data;


/**
 * 供应商考核信息表 实体对象
 */
@Data
public class PpmSupplierRiskEvalExt extends PpmSupplierRiskEval {
   /**
    *	项目经理名称
    **/
   private String pmName;
   /**
    *	供应商名称
    **/
   private String supplierName;
   /**
    * 项目经理 查询条件
    */
   private String pmValue;
   /**
    * 部门名称
    */
   private String deptName;
   /**
    * 部门名称
    */
   private String leaderPmName;
   /**
    * 部门名称
    */
   private String deptIdVal;
   /**
    * 项目经理查询
    */
   private String pmIdVal;
}
