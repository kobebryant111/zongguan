package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiPostevaluation;
import lombok.Data;



/**
 * 履职评估表
 */
@Data
public class PpmKpiPostevaluationInfo extends PpmKpiPostevaluation {
   /**
   *	状态名称
   **/
   private String itemstatusname;
   /**
   *	名称
   **/
   private String eidname;

   /**
    *	部门名称
    **/
   private String depname;
   /**
    *	中心名称
    **/
   private String centername;

}
