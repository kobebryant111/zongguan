package com.work.ppm.application.dto.kpi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@ExcelTarget("ppmKpiPmInfoDto")
@Data
public class PpmKpiPmInfoDto  {
   /**
    *	中心
    **/
   private String centerid;
   /**
    *	角色
    **/
   private String role;
   /**
    *	主键
    **/
   @Id
   private Long id;
   /**
    *	年
    **/
   @Excel(name = "年份", orderNum = "1")
   private String tyear;
   /**
    *	周期
    **/
   @Excel(name = "周期", orderNum = "2")
   private String term;
   /**
    *	工号
    **/
   @Excel(name = "工号", orderNum = "3")
   private String eid;
   /**
    *	部门
    **/
   private String depid;
   /**
    *	岗位
    **/
   private String jobid;
   /**
    *	所属机构
    **/
   private String compid;
   /**
    *	状态
    **/
   private Long status;
   /**
    *	审批人1
    **/
   private String firstcheck;
   /**
    *	审批人2
    **/
   private String secondcheck;
   /**
    *	审批时间1
    **/
   private Date firstchecktime;
   /**
    *	审批时间2
    **/
   private Date secondchecktime;
   /**
    *	评价人1
    **/
   private String firsteval;
   /**
    *	评价人2
    **/
   private String secondeval;
   /**
    *	评价时间1
    **/
   private Date firstevaltime;
   /**
    *	评价时间2
    **/
   private Date secondevaltime;
   /**
    *	创建人
    **/
   private String createuser;
   /**
    *	创建时间
    **/
   private Date createtime;
   /**
    *	回退意见
    **/
   private String rollbacksuggest;
   /**
    *	是否可编辑
    **/
   private Long isedit;
   /**
    *	初评人评语
    **/
   private String firstcomment;
   /**
    *	终评人评语
    **/
   private String secondcomment;
   /**
    *	初评人评语时间
    **/
   private Date firstcommenttime;
   /**
    *	终评人评语时间
    **/
   private Date secondcommenttime;
   /**
    *	总分
    **/
   @Excel(name = "总分", orderNum = "13")
   private Double tscore;
   /**
    *	总等级
    **/
   @Excel(name = "评级", orderNum = "14")
   private String tgrade;
   /**
    *	初评人评分
    **/
   private Double evalby1score;
   /**
    *	终评人评分
    **/
   private Double evalby2score;
   /**
    *	职位
    **/
   private String headship;
   /**
    *	初次排名
    **/
   private Long firstorder;
   /**
    *	终次排名
    **/
   private Long secondorder;
   /**
    *	kpi分数
    **/
   @Excel(name = "kpi考核", orderNum = "8")
   private Double kpiscore;
   /**
    *	综合分数
    **/
   @Excel(name = "综合能力", orderNum = "9")
   private Double comprehensivescore;
   /**
    *	履职评估分数
    **/
   @Excel(name = "履职评分", orderNum = "10")
   private Double performanceevaluationscore;
   /**
    *	平衡计分分数
    **/
   @Excel(name = "平衡计分", orderNum = "12")
   private Double balancescore;
   /**
    *	能力提升分数
    **/
   private Double abilityimporvescore;
   /**
    *	调整等级
    **/
   private String adjustlevel;
   /**
    *	调整原因
    **/
   private String adjustreason;
   /**
    *	团队排名
    **/
   private Long teamorder;
   /**
    *	团队占比
    **/
   private String teampercentage;
   /**
   *	审批人1
   **/
   private String firstcheckname;
   /**
   *	审批人2
   **/
   private String secondcheckname;

   /**
    *	姓名
    **/
   @Excel(name = "姓名", orderNum = "4")
   private String eidname;

   /**
    *	审核人
    **/
   private String check;

   /**
    *	部门名称
    **/
   @Excel(name = "部门", orderNum = "6")
   private String depname;

   /**
    *	中心名称
    **/
   @Excel(name = "中心", orderNum = "5")
   private String centername;

   /**
    *	初评人
    **/
   private String firstevalname;

   /**
    *	终评人
    **/
   private String secondevalname;


   /**
    *	状态名称
    **/
   @Excel(name = "状态", orderNum = "15")
   private String statusname;


   /**
    *	角色名称
    **/
   @Excel(name = "角色", orderNum = "7")
   private String rolename;

   /**
    *	二级部分数
    **/
   @Excel(name = "二级部评分", orderNum = "11")
   private Double depscore;

   /**
    *	待评价项数
    **/
   private Long evalitems;

   /**
    *	子项状态
    **/
   private Long itemstatus;
    /**
     *	子状态名称
     **/
    @Excel(name = "子状态名称", orderNum = "16")
    private String itemstatusname;


   /**
    *	多个角色
    **/
   private String roles;


   /**
    *	二级部总分
    **/
   private Double deptscore;
   /**
    *	二级部等级
    **/
   private String deptlevel;
   /**
    *	二级部排序
    **/
   private Long deporder;
   /**
    *	部门备注
    **/
   private String depremark;
   /**
    *	中心等级
    **/
   private String centertlevel;
   /**
    *	中心排序
    **/
   private Long centerorder;
   /**
    *	中心备注
    **/
   private String centerremark;
   /**
    *	初始分数
    **/
   private Double initialtscore;
   /**
    *	初始等级
    **/
   private String initialtlevel;
   /**
    *	初始排名
    **/
   private Long initialorder;

   /**
    *	添加分数
    **/
   private Double addscore;

   /**
    *	预计总分
    **/
   private Double expectscore;
   
   /**
    * 未填写项
    **/
   private Long nofillitem;

}
