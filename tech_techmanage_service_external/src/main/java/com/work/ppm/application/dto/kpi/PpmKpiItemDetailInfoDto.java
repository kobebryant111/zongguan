package com.work.ppm.application.dto.kpi;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
public class PpmKpiItemDetailInfoDto {
   /**
    *	主键
    **/
   @Id
   private Long id;
   /**
    *	关联主键
    **/
   private Long rid;
   /**
    *	是否删除
    **/
   private Long isdel;
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
   private String uptuserid;
   /**
    *	更新时间
    **/
   private Date uptdate;
   /**
   *	更新人姓名
   **/
   private String uptusername;
}
