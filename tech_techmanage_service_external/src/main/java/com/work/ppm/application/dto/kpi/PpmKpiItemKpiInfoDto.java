package com.work.ppm.application.dto.kpi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
@ExcelTarget("ppmKpiItemKpiInfoDto")
public class PpmKpiItemKpiInfoDto  {

   /**
    *	主键
    **/
   @Id
   @Excel(name = "主键", orderNum = "1")
   private Long id;
   /**
    *	指标项
    **/
   @Excel(name = "指标项", orderNum = "2")
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
    * 更新时间
    **/
   @Excel(name = "添加时间", orderNum = "5")
   private String uptdateStr;
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
   @Excel(name = "使用角色", orderNum = "3")
   private String userolename;
   /**
    * 中心名称
    **/
   @Excel(name = "中心名称", orderNum = "4")
   private String centername;

   /**
    * 指标类型
    **/
   private String itypename;

   /**
    * id已,分割 用于删除
    **/
   private String ids;

}
