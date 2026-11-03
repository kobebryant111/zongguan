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
@ExcelTarget("ppmKpiItemBalanceInfoDto")
public class PpmKpiItemBalanceInfoDto {


   /**
    *	主键
    **/
   @Id
   private Long id;
   /**
    *	指标项
    **/
   @Excel(name = "考核项目", orderNum = "1")
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
    * 标准
    **/
   @Excel(name = "考核内容", orderNum = "2")
   private String standard;

   /**
    * 工作内容
    **/
   @Excel(name = "评分标准", orderNum = "3")
   private String workcontent;

   /**
    * 分值
    **/
   private Double value;

   /**
    * 完成时间
    **/
   private Date finishdate;

   /**
    * 主体名称
    **/
   private String mainpartname;


   /**
    * 子数据的ID
    **/
   private Long did;
}
