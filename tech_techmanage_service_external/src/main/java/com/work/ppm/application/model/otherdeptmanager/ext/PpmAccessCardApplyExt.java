package com.work.ppm.application.model.otherdeptmanager.ext;

import com.work.ppm.application.model.otherdeptmanager.PpmAccessCardApply;
import lombok.Data;


/**
 * 监保部台账领用申请表 实体对象
 */
@Data
public class PpmAccessCardApplyExt extends PpmAccessCardApply {

   /**
   *	申请人编号
   **/
   private String applyerName;
   /**
   *	申请人部门编号
   **/
   private String applyerDeptName;
   
}
