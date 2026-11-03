package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiDepRole;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmKpiDepRoleInfo extends PpmKpiDepRole {
   /**
    *	类别姓名
    **/
   private String signname;
   /**
    *	部门姓名
    **/
   private String depname;
   /**
    *	中心名称姓名
    **/
   private String centername;
   /**
    *	部门主持工作经理姓名
    **/
   private String directorname;
   /**
    *	部门分管总经理姓名
    **/
   private String depchargename;

   /**
    *	中心负责人姓名
    **/
   private String centerchargename;

}
