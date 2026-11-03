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
@ExcelTarget("ppmKpiDepRoleInfoDto")
public class PpmKpiDepRoleInfoDto  {

   /**
    *	主键
    **/
   @Id
   @Excel(name = "主键", orderNum = "1")
   private Long id;
   /**
    *	部门编号
    **/
   private String depid;
   /**
    *	主持工作经理
    **/
   @Excel(name = "主持工作经理", orderNum = "4")
   private String director;
   /**
    *	部门分管领导//分管总经理
    **/
   @Excel(name = "部门分管经理", orderNum = "6")
   private String depcharge;
   /**
    *	中心总经理
    **/
   @Excel(name = "中心分管经理", orderNum = "8")
   private String centercharge;
   /**
    *	中心id
    **/
   private String centerid;
   /**
    *	年份
    **/
   private String tyear;
   /**
    *	是否删除
    **/
   private Long isdel;
   /**
    *	最后修改人
    **/
   private String lastmoduserid;
   /**
    *	最后修改时间
    **/
   private Date lastmodtime;
   /**
    *	1:部门 2：中心
    **/
   private Long sign;
   /**
    *	类别姓名
    **/
   @Excel(name = "类别", orderNum = "10")
   private String signname;
   /**
    *	部门姓名
    **/
   @Excel(name = "部门", orderNum = "3")
   private String depname;
   /**
    *	中心名称
    **/
   @Excel(name = "中心", orderNum = "2")
   private String centername;
   /**
    *	部门主持工作经理姓名
    **/
   @Excel(name = "主持工作经理姓名", orderNum = "5")
   private String directorname;
   /**
    *	部门分管总经理姓名
    **/
   @Excel(name = "部门分管经理姓名", orderNum = "7")
   private String depchargename;

   /**
    *	中心负责人姓名
    **/
   @Excel(name = "中心分管经理姓名", orderNum = "9")
   private String centerchargename;



}
