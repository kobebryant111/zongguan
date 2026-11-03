package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiItem;
import lombok.Data;


/**
 * 描述
 */
@Data
public class PpmKpiItemInfo extends PpmKpiItem {
   /**
    * 中心名称
    **/
   private String centername;

   /**
    * 指标类型
    **/
   private String itypename;

   /**
    * 主体中文
    **/
   private String mainpartname;
}
