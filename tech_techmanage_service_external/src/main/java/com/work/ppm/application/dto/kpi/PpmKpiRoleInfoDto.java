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
@ExcelTarget("ppmKpiRoleInfoDto")
public class PpmKpiRoleInfoDto {
   /**
   *	主键
   **/
   @Id
   @Excel(name = "主键", orderNum = "1")
   private Long id;
   /**
   *	工号
   **/
   @Excel(name = "工号", orderNum = "2")
   private String badge;
   /**
   *	姓名
   **/
   @Excel(name = "姓名", orderNum = "3")
   private String empname;
   /**
   *	角色
   **/
   private String role;

   /**
    *	角色名称
    **/
   @Excel(name = "角色名称", orderNum = "4")
   private String rolename;
   /**
   *	岗位
   **/
   private String jobid;

   /**
    *	岗位
    **/
   @Excel(name = "岗位", orderNum = "5")
   private String jobname;
   /**
   *	部门编号
   **/
   private String depid;
   /**
    *	部门名称
    **/
   @Excel(name = "部门名称", orderNum = "6")
   private String depname;

   /**
   *	中心编号
   **/
   private String centerid;

   /**
    *	中心名称
    **/
   @Excel(name = "中心名称", orderNum = "7")
   private String centername;
   /**
   *	头衔
   **/
   private String headship;
   /**
   *	一级审核评价人
   **/
   @Excel(name = "一级审核评价人", orderNum = "8")
   private String firstcheck;
   /**
    *	一级审核评价人
    **/
   @Excel(name = "一级审核评价人姓名", orderNum = "9")
   private String firstcheckname;
   /**
   *	二级审核评价人
   **/
   @Excel(name = "二级审核评价人", orderNum = "10")
   private String secondcheck;
   /**
    *	二级审核评价人
    **/
   @Excel(name = "二级审核评价人姓名", orderNum = "11")
   private String secondcheckname;
   /**
   *	是否删除
   **/
   private Long isdel;
   /**
   *	年份
   **/
   private String tyear;
   /**
   *	最后修改人
   **/
   private String lastmoduserid;
   /**
   *	最后修改时间
   **/
   private Date lastmodtime;


}
