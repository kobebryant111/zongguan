package com.work.ppm.application.dto.suppliermanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;


/**
  *  风险评估表 传输对象
  */
@Data
public class PpmSupplierRiskEvalDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	流程编号
	**/
	@ApiModelProperty(name = "instanceid", value = "流程编号", dataType = "String")
	private String instanceid;
	/**
	*	评估类别 PG01 [PPM_SUPPLIER_RELATION.TYPE]
	**/
	@ApiModelProperty(name = "assessType", value = "评估类别 PG01 [PPM_SUPPLIER_RELATION.TYPE]", dataType = "String")
	private String assessType;
	/**
	*	评估年份
	**/
	@ApiModelProperty(name = "assessYear", value = "评估年份", dataType = "String")
	private String assessYear;
	/**
	*	外包业务名称
	**/
	@ApiModelProperty(name = "busiName", value = "外包业务名称", dataType = "String")
	private String busiName;
	/**
	*	外包业务类型 研发咨询类/系统运行维护类/业务外包中的信息科技活动
	**/
	@ApiModelProperty(name = "busiType",
			value = "外包业务类型 研发咨询类/系统运行维护类/业务外包中的信息科技活动", dataType = "String")
	private String busiType;
	/**
	*	供应商id
	**/
	@ApiModelProperty(name = "supplierId", value = "供应商id", dataType = "String")
	private String supplierId;
	/**
	*	主要内容
	**/
	@ApiModelProperty(name = "impInfo", value = "主要内容", dataType = "String")
	private String impInfo;
	/**
	*	实施方式 驻场/非驻场
	**/
	@ApiModelProperty(name = "executionMode", value = "实施方式 驻场/非驻场", dataType = "String")
	private String executionMode;
	/**
	*	影响的业务类型
	**/
	@ApiModelProperty(name = "affectBusiType", value = "影响的业务类型", dataType = "String")
	private String affectBusiType;
	/**
	*	考核分数集合  key1:value1|key2:value2|***  key:PPM_SUPPLIER_RELATION.KEY/value表示结果
	**/
	@ApiModelProperty(name = "assessScores", value = "考核分数集合  key1:value1|key2:value2|***  " +
			"key:PPM_SUPPLIER_RELATION.KEY/value表示结果", dataType = "String")
	private String assessScores;
	/**
	*	评估结果
	**/
	@ApiModelProperty(name = "assessResult", value = "评估结果", dataType = "String")
	private String assessResult;
	/**
	*	项目经理id
	**/
	@ApiModelProperty(name = "pmId", value = "项目经理id", dataType = "String")
	private String pmId;
	/**
	*	部门id
	**/
	@ApiModelProperty(name = "deptId", value = "部门id", dataType = "String")
	private String deptId;
	/**
	*	二级部经理编号
	**/
	@ApiModelProperty(name = "leaderPmId", value = "二级部经理编号", dataType = "String")
	private String leaderPmId;
	/**
	*	流程状态码 -1 已关闭 0 未发起 1已发起
	**/
	@ApiModelProperty(name = "state", value = "流程状态码 -1 已关闭 0 未发起 1已发起", dataType = "String")
	private String state;
	/**
	*	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示
	**/
	@ApiModelProperty(name = "stateName",
			value = "流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示", dataType = "String")
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
	 * 评分集合
	 */
	private Map<String,Object> assessScoreMap;

	/**
	 * bps
	 */
	private BpsWorkFlowDto bps;
	/**
	 * 流程键值
	 */
	private String flowId;

	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
	 * 项目经理名称
	 */
	private String pmName;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 部门负责人名称
	 */
	private String leaderPmName;

	/**
	 * 影响的业务类型数组
	 */
	private String[] affectBusiTypeArray;

	/**
	 * 部门名称
	 */
	private String deptIdVal;

	/**
	 * 项目经理查询
	 */
	private String pmIdVal;

	/**
	 * 项目经理工号+姓名查询
	 */
	private String pmValue;
}
