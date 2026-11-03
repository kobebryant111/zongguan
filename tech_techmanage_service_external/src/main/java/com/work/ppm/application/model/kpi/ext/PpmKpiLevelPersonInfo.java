package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiLevelPerson;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmKpiLevelPersonInfo extends PpmKpiLevelPerson {
   /**
    *	部门名称
    **/
   private String depname;
   /**
    *	中心名称
    **/
   private String centername;
   /**
    *	排序列
    **/
   private Long sortno;
}
