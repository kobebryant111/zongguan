package com.work.ppm.application.dto.depkpi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
@ExcelTarget("ppmDepkpiDepRelInfoDto")
public class PpmDepkpiDepRelInfoDto {
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
   private String director;
   /**
   *	部门分管领导//分管总经理
   **/
   private String depcharge;
   /**
   *	中心总经理
   **/
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
   *	1:部门 2：中心 3：一级部
   **/
   private Long sign;
   /**
   *	初审/初评人
   **/
   @Excel(name = "初审/初评人", orderNum = "6")
   private String firstcheck;
   /**
   *	终审/终评人
   **/
   @Excel(name = "终审/终评人", orderNum = "8")
   private String secondcheck;
   /**
   *	考核制定人
   **/
   @Excel(name = "考核制定人", orderNum = "4")
   private String marker;
   /**
    *	部门名称
    **/
   @Excel(name = "部门", orderNum = "3")
   private String depname;
   /**
    *	主持工作经理名称
    **/
   private String directorname;
   /**
    *	部门分管领导//分管总经理名称
    **/
   private String depchargename;
   /**
    *	中心总经理名称
    **/
   private String centerchargename;
   /**
    *	中心名称
    **/
   @Excel(name = "中心", orderNum = "2")
   private String centername;

   /**
    *	初审/初评人名称
    **/
   @Excel(name = "初审/初评人名称", orderNum = "7")
   private String firstcheckname;
   /**
    *	终审/终评人名称
    **/
   @Excel(name = "初审/初评人名称", orderNum = "9")
   private String secondcheckname;
   /**
    *	考核制定人名称
    **/
   @Excel(name = "考核制定人名称", orderNum = "5")
   private String markername;

   /**
    *	标志类型名称
    **/
   @Excel(name = "类别", orderNum = "10")
   private String signname;
}
