package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;


/**
  *  信息变更申请信息表 传输对象
  */
@Data
public class PpmOutsourcUpdateDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	流程ID
	**/
	@ApiModelProperty(name = "instanceid", value = "流程ID", dataType = "String")
	private String instanceid;
	/**
	*	申请人
	**/
	@ApiModelProperty(name = "applyer", value = "申请人", dataType = "String")
	private String applyer;
	/**
	*	申请人部门
	**/
	@ApiModelProperty(name = "applyerDept", value = "申请人部门", dataType = "String")
	private String applyerDept;
	/**
	 *	变更原因 UPDATE_REASON
	 **/
	@ApiModelProperty(name = "updateReason", value = "变更原因", dataType = "String")
	private String updateReason;
	/**
	*	申请时间
	**/
	@ApiModelProperty(name = "applyerTime", value = "申请时间", dataType = "String")
	private String applyerTime;
	/**
	 *	流程状态码 -1 已关闭 0 未发起 1已发起
	 **/
	@ApiModelProperty(name = "state", value = "流程状态码 -1 已关闭 0 未发起 1已发起", dataType = "String")
	private String state;
	/**
	 *	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示
	 **/
	@ApiModelProperty(name = "stateName", value = "流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示", dataType = "String")
	private String stateName;
	/**
	 *	当前处理人编号
	 **/
	@ApiModelProperty(name = "currentId", value = "当前处理人编号", dataType = "String")
	private String currentId;
	/**
	 *	当前处理人名称
	 **/
	@ApiModelProperty(name = "currentName", value = "当前处理人名称", dataType = "String")
	private String currentName;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	创建人
	**/
	@ApiModelProperty(name = "createdBy", value = "创建人", dataType = "String")
	private String createdBy;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;
	/**
	*	修改人
	**/
	@ApiModelProperty(name = "modifiedBy", value = "修改人", dataType = "String")
	private String modifiedBy;
	/**
	 * 信息
	 */
	private List<PpmOutsourcUpdateInfoDto> updateInfoDtos;

	/**
	 * bps
	 */
	private BpsWorkFlowDto bps;

	/**
	 * 流程定义名称
	 **/
	private String flowId;

	/**
	 *	申请人
	 **/
	private String applyerName;

	/**
	 *	申请人
	 **/
	private String applyerDeptName;
}
