package com.work.ppm.application.model.kpi.ext;

import com.work.ppm.application.model.kpi.PpmKpiItem;
import lombok.Data;

import java.util.Date;


/**
 * 查询考核指标库数据用于考核指标项插入使用
 */
@Data
public class PpmKpiItemInsertInfo extends PpmKpiItem {
   /**
    *	子表主键
    **/
   private Long did;
   /**
    *	关联主键
    **/
   private Long rid;
   /**
    *	是否删除
    **/
   private Long disdel;
   /**
    *	分值
    **/
   private Double value;
   /**
    *	完成时间
    **/
   private Date finishdate;
   /**
    *	标准内容
    **/
   private String standard;
   /**
    *	考核内容
    **/
   private String workcontent;
   /**
    *	更新人
    **/
   private String duptuserid;
   /**
    *	更新时间
    **/
   private Date duptdate;
}
