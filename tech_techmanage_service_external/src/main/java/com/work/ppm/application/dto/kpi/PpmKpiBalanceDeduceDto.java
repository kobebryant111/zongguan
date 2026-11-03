package com.work.ppm.application.dto.kpi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
 @ExcelTarget("ppmKpiBalanceDeduceDto")
 @Data
public class PpmKpiBalanceDeduceDto {
	/**
	*	主键
	**/
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
	@Excel(name = "考核项", orderNum = "1")
	private String itemdescribe;
	/**
	*	考核标准描述
	**/
	@Excel(name = "考核内容", orderNum = "2")
	private String plandescribe;
	/**
	*	扣分原因
	**/
	@Excel(name = "扣分理由", orderNum = "5")
	private String deducescorereason;
	/**
	*	分值
	**/
	@Excel(name = "扣分", orderNum = "4")
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
	  *	录入时间
	  **/
	 @Excel(name = "扣分时间", orderNum = "6")
	 private String regtimeStr;


	 /**
	  *	扣分标准
	  **/
	 @Excel(name = "扣分标准", orderNum = "3")
	 private String workremark;
}
