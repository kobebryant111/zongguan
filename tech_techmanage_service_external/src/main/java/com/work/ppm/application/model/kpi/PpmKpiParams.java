package com.work.ppm.application.model.kpi;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiParams {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	参数大类
	**/
	private String ptype;
	/**
	*	参数类型
	**/
	private String dicttype;
	/**
	*	参数id
	**/
	private String dictid;
	/**
	*	参数名
	**/
	private String dictname;
	/**
	*	排序
	**/
	private Long sort;
	/**
	*	参数规则
	**/
	private String prule;
	/**
	*	创建人
	**/
	private String createuserid;
	/**
	*	创建时间
	**/
	private Date createdate;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	备注1
	**/
	private String remark1;
	/**
	*	备注2
	**/
	private String remark2;
}
