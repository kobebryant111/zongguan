package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmManpowerdemandApplyInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 描述
 * @date 2020-09-08
 *
 */
@Data
public class PpmManpowerdemandApplyInfoInfo extends PpmManpowerdemandApplyInfo {
	/**
	 * 需求进度
	 */
	@ApiModelProperty(value = "需求进度", name = "nums")
	private String nums;
	/**
	 * 申请人
	 */
	@ApiModelProperty(value = "申请人", name = "userid")
	private String userid;
	/**
	 * 申请人姓名
	 */
	@ApiModelProperty(value = "申请人姓名", name = "username")
	private String username;
	/**
	 * 部门
	 */
	@ApiModelProperty(value = "部门", name = "dept")
	private String dept;
	/**
	 * 部门编号
	 */
	@ApiModelProperty(value = "部门编号", name = "deptno")
	private String deptno;
	/**
	 * 提供时间
	 */
	@ApiModelProperty(value = "提供时间", name = "supplytime")
	private String supplytime;
	/**
	 * 流程状态
	 */
	@ApiModelProperty(value = "流程状态", name = "mstatus")
	private String mstatus;
	/**
	 * 流程状态
	 */
	@ApiModelProperty(value = "流程编号", name = "processId")
	private String processId;
}
