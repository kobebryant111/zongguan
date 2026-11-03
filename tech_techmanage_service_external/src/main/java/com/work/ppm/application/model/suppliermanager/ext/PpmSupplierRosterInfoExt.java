package com.work.ppm.application.model.suppliermanager.ext;

import com.work.ppm.application.model.suppliermanager.PpmSupplierRosterInfo;
import lombok.Data;


/**
 * 供应商花名册表 实体对象
 */
@Data
public class PpmSupplierRosterInfoExt extends PpmSupplierRosterInfo {

   /**
    * 删除变量
    */
   private String delVal;
   /**
    * 部门名称
    */
   private String deptName;
   /**
    * 系统名称
    */
   private String systemName;
   /**
    * 项目名称
    */
   private String projectName;

   /**
    * 是否合作
    */
   private String isFeat;
}
