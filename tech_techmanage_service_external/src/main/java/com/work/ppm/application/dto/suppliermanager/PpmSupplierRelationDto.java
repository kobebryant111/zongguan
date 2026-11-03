package com.work.ppm.application.dto.suppliermanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;


/**
  *  供应商考核信息表 传输对象
  */
@Data
public class PpmSupplierRelationDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	*	键值 TYPE_***
	**/
	@ApiModelProperty(name = "key", value = "键值 TYPE_***", dataType = "String")
	private String key;
	/**
	*	考核内容
	**/
	@ApiModelProperty(name = "code", value = "考核内容", dataType = "String")
	private String code;
	/**
	*	考核标准
	**/
	@ApiModelProperty(name = "name", value = "考核标准", dataType = "String")
	private String name;
	/**
	*	考核类型 RL** 人力 XM** 项目 PG** 风险评估
	**/
	@ApiModelProperty(name = "type", value = "考核类型 RL** 人力 XM** 项目 PG** 风险评估", dataType = "String")
	private String type;
	/**
	*	考核分数上限
	**/
	@ApiModelProperty(name = "score", value = "考核分数上限", dataType = "String")
	private String score;
	/**
	*	选项集合 用 | 分割 
	**/
	@ApiModelProperty(name = "arrays", value = "选项集合 用 | 分割 ", dataType = "String")
	private String arrays;
	/**
	*	备注
	**/
	@ApiModelProperty(name = "remark", value = "备注", dataType = "String")
	private String remark;
	/**
	*	序号
	**/
	@ApiModelProperty(name = "displaySeq", value = "序号", dataType = "String")
	private String displaySeq;
	/**
	*	备用字段1
	**/
	@ApiModelProperty(name = "spare01", value = "备用字段1", dataType = "String")
	private String spare01;
	/**
	*	备用字段2
	**/
	@ApiModelProperty(name = "spare02", value = "备用字段2", dataType = "String")
	private String spare02;
	/**
	*	备用字段3
	**/
	@ApiModelProperty(name = "spare03", value = "备用字段3", dataType = "String")
	private String spare03;

	@JsonInclude
	private String value;

 	@JsonInclude
 	private List<Map<String,Object>> arrayMap;

	@JsonInclude
	private String rowspan;
}
