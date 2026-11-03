package com.work.ppm.application.dto.systemmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;


/**
  *  项目详情表 传输对象
  */
@Data
@JsonInclude
public class PpmProjectInfoDto {
	/**
	*	主键
	**/
	@ApiModelProperty(name = "id", value = "主键", dataType = "Long")
	private Long id;
	/**
	*	项目名称
	**/
	@ApiModelProperty(name = "name", value = "项目名称", dataType = "String")
	private String name;
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
	*	归属部门ID
	**/
	@ApiModelProperty(name = "depId", value = "归属部门ID", dataType = "Long")
	private Long depId;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createBy", value = "创建人", dataType = "String")
	private String createBy;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createDate", value = "创建时间", dataType = "Date")
	private Date createDate;
	/**
	*	修改人
	**/
	@ApiModelProperty(name = "updateBy", value = "修改人", dataType = "String")
	private String updateBy;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "updateDate", value = "修改时间", dataType = "Date")
	private Date updateDate;
	/**
	*	备注
	**/
	@ApiModelProperty(name = "remark", value = "备注", dataType = "String")
	private String remark;
	 /**
	  * 项目编号
	  */
	@ApiModelProperty(name = "projectNumber", value = "项目编号", dataType = "String")
	private String projectNumber;
	 /**
	  * 项目来源
	  */
	@ApiModelProperty(name = "projectSource", value = "项目来源", dataType = "String")
	private String projectSource;
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
	 * 测试项目经理
	 */
	@ApiModelProperty(name = "testBadge", value = "测试项目经理", dataType = "String")
	private String testBadge;
	/**
	 * 测试项目经理名称
	 */
	@ApiModelProperty(name = "testBadgeName", value = "测试项目经理名称", dataType = "String")
	private String testBadgeName;
	/**
	 * 测试项目经理所属部门
	 */
	@ApiModelProperty(name = "testBadgeDept", value = "测试项目经理所属部门", dataType = "String")
	private String testBadgeDept;
	/**
	 * 测试项目经理所属部门名称
	 */
	@ApiModelProperty(name = "testBadgeDeptName", value = "测试项目经理所属部门名称", dataType = "String")
	private String testBadgeDeptName;
	/**
	 * 项目结束时间
	 */
	@ApiModelProperty(name = "projectEndTime", value = "项目结束时间", dataType = "Date")
	private Date projectEndTime;
	/**
	 * 项目结束时间
	 */
	private String projectEndTimeVal;
	 /**
	  * 关联附件浏览
	  */
	private List<String> supplierSyncFiles;

	/**
	 * 工号
	 */
	private String badgeVal;

	/**
	 * 系统负责人信息
	 */
	private List<PpmProjectUserInfoDto> userInfoList;
	/**
	 * 部门名称
	 */
	private String depName;
}
