package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.integratedmanager.ext.PpmManpowerdemandApplyInfoOrgInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 描述
 * @date 2020-09-08
 *
 */
@Data
public class PpmManpowerdemandApplyOrgDto {


		/**
		 * ID
		 */
		@ApiModelProperty(value = " ID", name = "id")
		private Integer id;
		/**
		 * 用户ID
		 */
		@ApiModelProperty(value = " 用户ID", name = "userid")
		private String userid;
		/**
		 * 用户名
		 */
		@ApiModelProperty(value = " 用户名", name = "username")
		private String username;
		/**
		 * 部门名
		 */
		@ApiModelProperty(value = " 部门名", name = "dept")
		private String dept;
		/**
		 * 部门ID
		 */
		@ApiModelProperty(value = " 部门ID", name = "deptno")
		private String deptno;
		/**
		 * 流程号
		 */
		@ApiModelProperty(value = " 流程号", name = "instanceid")
		private String instanceid;
		/**
		 * 创建时间
		 */
		@ApiModelProperty(value = " 创建时间", name = "createtime")
		private String createtime;
		/**
		 * 创建人
		 */
		@ApiModelProperty(value = " 创建人", name = "createuserid")
		private String createuserid;
		/**
		 * 提至供应商时间
		 */
		@ApiModelProperty(value = " 提至供应商时间", name = "supplytime")
		private Date supplytime;
		/**
		 * 审批的主持工作经理
		 */
		@ApiModelProperty(value = " 审批的主持工作经理", name = "manageruserid")
		private String manageruserid;
		/**
		 * 综合部人员确认
		 */
		@ApiModelProperty(value = " 综合部人员确认", name = "confirmuserid")
		private String confirmuserid;
		/**
		 * 流程状态
		 */
		@ApiModelProperty(value = " 流程状态", name = "status")
		private String status;
		/**
		 * ID
		 */
		@ApiModelProperty(value = " 详细数据", name = "infos")
		private List<PpmManpowerdemandApplyInfoOrgInfo> infos;
		/**
		 * ID
		 */
		@ApiModelProperty(value = " bps", name = "bps")
		private BpsWorkFlowDto bps;


}
