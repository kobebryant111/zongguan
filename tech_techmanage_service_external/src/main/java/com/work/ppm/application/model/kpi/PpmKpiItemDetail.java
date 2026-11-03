package com.work.ppm.application.model.kpi;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiItemDetail {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	关联主键
	**/
	private Long rid;
	/**
	*	是否删除
	**/
	private Long isdel;
	/**
	*	分值
	**/
	private Double value;
	/**
	*	完成时间
	**/
	private Date finishdate;
	/**
	*	标准内容
	**/
	private String standard;
	/**
	*	考核内容
	**/
	private String workcontent;
	/**
	*	更新人
	**/
	private String uptuserid;
	/**
	*	更新时间
	**/
	private Date uptdate;
}
