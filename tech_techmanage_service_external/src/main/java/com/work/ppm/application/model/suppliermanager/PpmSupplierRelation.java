package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 供应商考核信息表 实体对象
  */
@Data
public class PpmSupplierRelation {
	/**
	*	唯一编号
	**/
	@Id
	private String id;
	/**
	*	键值 TYPE_***
	**/
	private String key;
	/**
	*	考核内容
	**/
	private String code;
	/**
	*	考核标准
	**/
	private String name;
	/**
	*	考核类型 RL** 人力 XM** 项目 PG** 风险评估
	**/
	private String type;
	/**
	*	考核分数上限
	**/
	private String score;
	/**
	*	选项集合 用 | 分割 
	**/
	private String arrays;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	序号
	**/
	private String displaySeq;
	/**
	*	备用字段1
	**/
	private String spare01;
	/**
	*	备用字段2
	**/
	private String spare02;
	/**
	*	备用字段3
	**/
	private String spare03;
}
