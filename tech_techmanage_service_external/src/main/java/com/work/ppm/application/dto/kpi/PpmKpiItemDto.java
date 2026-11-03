package com.work.ppm.application.dto.kpi;

import lombok.Data;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmKpiItemDto {
	/**
	*	主键
	**/
	private Long id;
	/**
	*	指标项
	**/
	private String item;
	/**
	*	使用角色（1：员工，2：经理）
	**/
	private String userole;
	/**
	*	中心
	**/
	private String centerid;
	/**
	*	指标类型
	**/
	private Long itype;
	/**
	*	更新人
	**/
	private String uptuserid;
	/**
	*	更新时间
	**/
	private Date uptdate;
	/**
	*	是否删除
	**/
	private Long isdel;
	/**
	*	权重
	**/
	private Double weight;
	/**
	*	主体
	**/
	private String mainpart;
	/**
	*	使用角色名称便于查询方便（1：员工，2：经理）
	**/
	private String userolename;
}
