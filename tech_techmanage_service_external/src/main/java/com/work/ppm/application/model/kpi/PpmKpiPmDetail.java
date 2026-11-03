package com.work.ppm.application.model.kpi;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiPmDetail {
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
	private String eid;
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
	private String itemdescribe;
	/**
	*	考核标准
	**/
	private String plan;
	/**
	*	考核标准描述
	**/
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
	  *	录入时间
	  **/
	 private Date evalselftime;
}
