package com.work.ppm.application.dto.otherdeptmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;


/**
  *  监保部台账领用申请表 传输对象
  */
@Data
public class PpmAccessCardApplyDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
	private String id;
	/**
	 *	继承data的id
	 **/
	@ApiModelProperty(name = "pid", value = "继承data的id", dataType = "String")
	private String pid;
	/**
	*	类型 RECEIVE 领用流程 RETURN 归还流程
	**/
	@ApiModelProperty(name = "type", value = "类型 RECEIVE 领用流程 RETURN 归还流程", dataType = "String")
	private String type;
	/**
	*	流程编号
	**/
	@ApiModelProperty(name = "instanceId", value = "流程编号", dataType = "String")
	private String instanceId;
	/**
	*	申请人编号
	**/
	@ApiModelProperty(name = "applyerId", value = "申请人编号", dataType = "String")
	private String applyerId;

	private String applyerName;
	/**
	*	申请人部门编号
	**/
	@ApiModelProperty(name = "applyerDeptId", value = "申请人部门编号", dataType = "String")
	private String applyerDeptId;

	private String applyerDeptName;
	/**
	 *	流程状态码 -1 已关闭 0 未发起 1已发起
	 **/
	@ApiModelProperty(name = "fileNo", value = "相关附件编号 用,分割", dataType = "String")
	private String fileNo;
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
	 *	是否删除 0否 1是
	 **/
	@ApiModelProperty(name = "isDel", value = "是否删除 0否 1是", dataType = "String")
	private String isDel;
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
	 * bps
	 */
	private BpsWorkFlowDto bps;
	private List<PpmAccessCardApplyInfoDto> accessCardApplyInfoList;

	/**
	 * 流程定义名称
	 **/
	private String flowId;



}
