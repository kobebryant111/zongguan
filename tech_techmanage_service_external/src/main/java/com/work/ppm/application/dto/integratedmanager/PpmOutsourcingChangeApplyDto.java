package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 描述
 */
@Data
public class PpmOutsourcingChangeApplyDto {
		/**
		 * ID
		 */
		@ApiModelProperty(value = " ID", name = "id")
		private Integer id;
		/**
		 * 申请人姓名
		 */
		@ApiModelProperty(value = " 申请人姓名", name = "applyer")
		private String applyer;
		/**
		 * 流程flowid
		 */
		@ApiModelProperty(value = " 流程flowid", name = "flowid")
		private String flowid;
		/**
		 * 部门编号
		 */
		@ApiModelProperty(value = " 部门编号", name = "deptno")
		private String deptno;
		/**
		 * 申请时间
		 */
		@ApiModelProperty(value = " 申请时间", name = "applytime")
		private Date applytime;
		/**
		 * 申请人ID
		 */
		@ApiModelProperty(value = " 申请人ID", name = "applyid")
		private String applyid;
		/**
		 * 部门名称
		 */
		@ApiModelProperty(value = " 部门名称", name = "deptname")
		private String deptname;
		/**
		 * 流程id
		 */
		@ApiModelProperty(value = " 流程id", name = "instanceid")
		private String instanceid;
		/**
		 * 实际变更时间
		 */
		@ApiModelProperty(value = " 实际变更时间", name = "actChangetime")
		private Date actChangetime;
		/**
		 * 最后修改人
		 */
		@ApiModelProperty(value = " 最后修改人", name = "lastmoduserid")
		private String lastmoduserid;
		/**
		 * 最后修改时间
		 */
		@ApiModelProperty(value = " 最后修改时间", name = "lastmodtime")
		private Date lastmodtime;
		/**
		 * 状态
		 */
		@ApiModelProperty(value = " 状态", name = "status")
		private String status;

		/**
		 * 变更原因
		 */
		@ApiModelProperty(value = " 变更原因", name = "changereason")
		private String changereason;
		/**
		 * 是否符合
		 */
		@ApiModelProperty(value = " 是否符合", name = "isfit")
		private String isfit;
		/**
		 * 是否需要走面试流程
		 */
		@ApiModelProperty(value = " 是否需要走面试流程", name = "isinterview")
		private String isinterview;
		/**
		 * ID
		 */
		@ApiModelProperty(value = " 详细数据", name = "infos")
		private List<PpmOutsourcingChangeInfosDto> infos;
		/**
		 * ID
		 */
		@ApiModelProperty(value = " bps", name = "bps")
		private BpsWorkFlowDto bps;


}
