package com.work.ppm.application.dto.systemmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
  *  项目详情表 传输对象
  */
@Data
@JsonInclude
public class PpmProjectUserInfoDto {
	/**
	*	主键
	**/
	@ApiModelProperty(name = "id", value = "主键", dataType = "Long")
	private Long id;

	/**
	*	项目经理工号
	**/
	@ApiModelProperty(name = "badge", value = "项目经理工号", dataType = "String")
	private String badge;
	/**
	*	项目经理名称
	**/
	@ApiModelProperty(name = "empName", value = "项目经理名称", dataType = "String")
	private String empName;
	/**
	  * 业务项目经理
	  */
	@ApiModelProperty(name = "busiBadge", value = "业务项目经理", dataType = "String")
	private String busiBadge;

	/**
	 * 业务项目经理名称
	 */
	@ApiModelProperty(name = "busiBadgeName", value = "业务项目经理名称", dataType = "String")
	private String busiBadgeName;

	/**
	 * 业务项目经理
	 */
	@ApiModelProperty(name = "deptId", value = "项目经理所属部门", dataType = "String")
	private String deptId;

	/**
	 * 业务项目经理名称
	 */
	@ApiModelProperty(name = "deptName", value = "项目经理所属部门名称", dataType = "String")
	private String deptName;

}
