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
@ExcelTarget("ppmKpiPmDetailInfoDto")
public class PpmKpiPmDetailInfoDto  {

   /**
    *	半年度自评
    **/
   private String halfyearevalself;
   /**
    *	初评人
    **/
   private String firsteval;
   /**
    *	终评人
    **/
   private String secondeval;
   /**
    *	初评时间
    **/
   private Date firstevaltime;
   /**
    *	终评时间
    **/
   private Date secondevaltime;
   /**
    *	详细项状态
    **/
   private Long itemstatus;
   /**
    *	上下级或者科技部评价人工号（履职评估用）
    **/
   private String evaluserid;
   /**
    *	权重
    **/
   private Double weight;
   /**
    *	考核主体
    **/
   private String mainpart;
   /**
    *	主键
    **/
   @Id
   @Excel(name = "主键", orderNum = "1")
   private Long id;
   /**
    *	主表主键
    **/
   private Long pid;
   /**
    *	年
    **/
   private String tyear;
   /**
    *	周期
    **/
   private String term;
   /**
    *	工号
    **/
   @Excel(name = "工号", orderNum = "5")
   private String eid;

   /**
    * 姓名
    */
   @Excel(name = "评价人", orderNum = "6")
   private String eidname;
   /**
    *	指标类型
    **/
   private Long kpitype;
   /**
    *	考核项
    **/
   private String item;
   /**
    *	考核项描述
    **/
   @Excel(name = "考核要项", orderNum = "7")
   private String itemdescribe;
   /**
    *	考核标准
    **/
   private String plan;
   /**
    *	考核标准描述
    **/
   @Excel(name = "行为描述", orderNum = "8")
   private String plandescribe;
   /**
    *	分值
    **/
   private Double value;
   /**
    *	完成时间
    **/
   private Date finishdate;
   /**
    *	录入人
    **/
   private String regby;
   /**
    *	录入时间
    **/
   private Date regtime;
   /**
    *	是否可编辑
    **/
   private Long isedit;
   /**
    *	完成情况自评
    **/
   private String evalself;
   /**
    *	工作内容
    **/
   private String workremark;
   /**
    *	初评分
    **/
   private Double firstevalscore;
   /**
    *	初评比重
    **/
   private Double firstevalgoal;
   /**
    *	初评评语
    **/
   private String firstevalcomment;
   /**
    *	终评分
    **/
   @Excel(name = "评分", orderNum = "10")
   private Double secondevalscore;
   /**
    *	终评比重
    **/
   private Double secondevalgoal;
   /**
    *	终评评语
    **/
   private String secondevalcomment;
   /**
    *	备注内容1
    **/
   private String remark1;
   /**
    *	备注内容2
    **/
   private String remark2;
   /**
    *	最终得分
    **/
   private Double tscore;
   /**
    * 考核项状态名称
    */
   private String itemstatusname;
   /**
    * 初评人姓名
    */
   private String firstevalname;
   /**
    * 终评人姓名
    */
   private String secondevalname;

   /**
    *	初评平均分
    **/
   private Double firstevalavgscore;

   /**
    *	终评平均分
    **/
   private Double secondevalavgscore;

   /**
    * 主考核项状态
    */
   private String status;

   /**
    * 主考核项状态名称
    */
   @Excel(name = "状态", orderNum = "9")
   private String statusname;


   /**
    * 中心id
    */
   private String centerid;

   /**
    * 中心名称
    */
   @Excel(name = "中心", orderNum = "2")
   private String centername;

   /**
    * 部门编号
    */
   private String depid;

   /**
    * 部门名称
    */
   @Excel(name = "部门", orderNum = "3")
   private String depname;



   /**
    * 角色编号
    */
   private String role;


   /**
    * 角色名称
    */
   private String rolename;


   /**
    *	待评价项数
    **/
   private long evalitems;


   /**
    *	录入时间
    **/
   private Date evalselftime;


}
