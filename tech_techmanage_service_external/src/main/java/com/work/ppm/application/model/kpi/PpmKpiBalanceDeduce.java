package com.work.ppm.application.model.kpi;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiBalanceDeduce {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	年份
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
	*	考核项
	**/
	private String item;
	/**
	*	考核标准
	**/
	private String plan;
	/**
	*	考核项描述
	**/
	private String itemdescribe;
	/**
	*	考核标准描述
	**/
	private String plandescribe;
	/**
	*	扣分原因
	**/
	private String deducescorereason;
	/**
	*	分值
	**/
	private Double value;
	/**
	*	录入人
	**/
	private String regby;
	/**
	*	录入时间
	**/
	private Date regtime;

	 /**
	  *	扣分标准
	  **/
	 private String workremark;
}
