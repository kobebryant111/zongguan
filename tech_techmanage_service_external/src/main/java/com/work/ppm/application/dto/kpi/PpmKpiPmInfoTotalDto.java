package com.work.ppm.application.dto.kpi;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;


/**
 * 描述
 */
@Data
public class PpmKpiPmInfoTotalDto {
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
   private String tyear;
   /**
    *	周期
    **/
   private String term;
   /**
    *	工号
    **/
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
   private Double tscore;
   /**
    *	总等级
    **/
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
   private Double kpiscore;
   /**
    *	综合分数
    **/
   private Double comprehensivescore;
   /**
    *	履职评估分数
    **/
   private Double performanceevaluationscore;
   /**
    *	平衡计分分数
    **/
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
   private String eidname;

   /**
    *	审核人
    **/
   private String check;

   /**
    *	部门名称
    **/
   private String depname;

   /**
    *	中心名称
    **/
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
   private String statusname;


   /**
    *	角色名称
    **/
   private String rolename;

   /**
    *	二级部分数
    **/
   private long depscore;

   /**
    *	kpi考核项
    **/
   private long kpsItems;

   /**
    *	已完成考核项
    **/
   private long finishItems;

   /**
    *	进行中考核项
    **/
   private long evalItems;
   /**
    *	能力项提升考核项
    **/
   private long abilityItems;


   /**
    *	配置中考核项
    **/
   private long configureItems;

   /**
    * 审批中考核项
    */
   private long approveItems;



}
