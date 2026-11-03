package com.work.ppm.application.dto.kpi;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 查询考核指标库数据用于考核指标项插入使用
 */
@Data
public class PpmKpiItemInsertInfoDto {

   /**
    *	主键
    **/
   @Id
   private Long id;
   /**
    *	指标项
    **/
   private String item;
   /**
    *	使用角色（1：员工，2：经理）
    **/
   private String userole;
   /**
    *	中心
    **/
   private String centerid;
   /**
    *	指标类型
    **/
   private Long itype;
   /**
    *	更新人
    **/
   private String uptuserid;
   /**
    *	更新时间
    **/
   private Date uptdate;
   /**
    *	是否删除
    **/
   private Long isdel;
   /**
    *	权重
    **/
   private Double weight;
   /**
    *	主体
    **/
   private String mainpart;
   /**
    *	使用角色名称便于查询方便（1：员工，2：经理）
    **/
   private String userolename;
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
