package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 入职部门分配报表
  */
@Data
public class PpmDeptdistributionReport {
	/**
	*	自增编号
	**/
	@Id
	private Long id;
	/**
	*	部门编号
	**/
	private String depId;
	/**
	*	各部门总编制数
	**/
	private Double depNum;
	/**
	*	研发
	**/
	private Double researchNum;
	/**
	*	产品经理
	**/
	private Double productNum;
	/**
	*	应用支持
	**/
	private Double appSuportNum;
	/**
	*	系统运维
	**/
	private Double systemNum;
	/**
	*	测试
	**/
	private Double testNum;
	/**
	*	职能管理
	**/
	private Double functionsifNum;
	/**
	*	批次
	**/
	private String batch;
}
