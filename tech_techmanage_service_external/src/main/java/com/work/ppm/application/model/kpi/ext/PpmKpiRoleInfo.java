package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiRole;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmKpiRoleInfo extends PpmKpiRole {
   /**
    *	角色名称
    **/
   private String rolename;
   /**
    *	岗位
    **/
   private String jobname;
   /**
    *	部门编号
    **/
   private String depname;
   /**
    *	中心名称
    **/
   private String centername;
   /**
    *	一级审核评价人
    **/
   private String firstcheckname;
   /**
    *	二级审核评价人
    **/
   private String secondcheckname;
}
