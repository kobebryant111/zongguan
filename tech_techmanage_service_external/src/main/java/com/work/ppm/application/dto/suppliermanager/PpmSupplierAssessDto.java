package com.work.ppm.application.dto.suppliermanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;


/**
  *  供应商考核信息表 传输对象
  */
@Data
public class PpmSupplierAssessDto {
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
	*	考核年份
	**/
	@ApiModelProperty(name = "assessYear", value = "考核年份", dataType = "String")
	private String assessYear;
	/**
	*	供应商编号
	**/
	@ApiModelProperty(name = "supplierId", value = "供应商编号", dataType = "String")
	private String supplierId;
	/**
	*	考核类型 RL** 人力 XM** 项目 [PPM_SUPPLIER_RELATION.TYPE]
	**/
	@ApiModelProperty(name = "assessType", value = "考核类型  [PPM_SUPPLIER_RELATION.TYPE]", dataType = "String")
	private String assessType;
	/**
	*	考核分数集合 key1:value1|key2:value2|***  key:PPM_SUPPLIER_RELATION.KEY/value表示分数
	**/
	@ApiModelProperty(name = "assessScores", value = "考核分数集合 ", dataType = "String")
	private String assessScores;
	/**
	*	考核总分
	**/
	@ApiModelProperty(name = "assessScoreSum", value = "考核总分", dataType = "Long")
	private Long assessScoreSum;
	/**
	*	考核等级
	**/
	@ApiModelProperty(name = "assessLevel", value = "考核等级", dataType = "String")
	private String assessLevel;
	/**
	*	项目经理类型 KJ 科技 YW 项目
	**/
	@ApiModelProperty(name = "pmType", value = "项目经理类型 KJ 科技 YW 项目", dataType = "String")
	private String pmType;
	/**
	*	项目经理编号
	**/
	@ApiModelProperty(name = "pmId", value = "项目经理编号", dataType = "String")
	private String pmId;
	/**
	*	对应部门编号
	**/
	@ApiModelProperty(name = "deptId", value = "对应部门编号", dataType = "String")
	private String deptId;
	/**
	*	项目编号
	**/
	@ApiModelProperty(name = "projectNumber", value = "项目编号", dataType = "String")
	private String projectNumber;
	/**
	*	存在问题
	**/
	@ApiModelProperty(name = "existQuestion", value = "存在问题", dataType = "String")
	private String existQuestion;
	/**
	*	是否已同步
	**/
	@ApiModelProperty(name = "isSync", value = "是否已同步", dataType = "String")
	private String isSync;
	/**
	*	流程状态码 -1 已关闭 0 未发起 1已发起
	**/
	@ApiModelProperty(name = "state", value = "流程状态码 -1 已关闭 0 未发起 1已发起", dataType = "String")
	private String state;
	/**
	*	流程状态码名称 -1 已关闭 0未发起 >1 根据实际流程节点展示
	**/
	@ApiModelProperty(name = "stateName", value = "流程状态码名称", dataType = "String")
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
	*	项目经理名称
	**/
	@ApiModelProperty(name = "pmName", value = "项目经理名称", dataType = "String")
	private String pmName;
	/**
	*	供应商名称
	**/
	@ApiModelProperty(name = "supplierName", value = "供应商名称", dataType = "String")
	private String supplierName;
	/**
	*	项目名称
	**/
	@ApiModelProperty(name = "projectName", value = "项目名称", dataType = "String")
	private String projectName;
	/**
	 * 对应部门名称
	 */
	@JsonInclude
	private String deptName;
 	/**
  	* 项目经理 查询条件
  	*/
	private String pmValue;

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
	 * 外包方式
	 */
	private String outSourceType;

	/**
	 * 项目经理对应人数
	 */
	private Long personCount;
	/**
	 * 年份开始时间
	 */
	private String startTime;
	/**
	 * 年份结束时间
	 */
	private String endTime;
	/**
	 * 部门编号查询
	 */
	private String deptIdQue;

	private Double percent;
	/**
	 * 项目id
	 */
	private Long projectId;
	/**
	 * 考核项目模糊查询
	 */
	private String projectNameVal;
	/**
	 * 项目类型
	 */
	private String pmTypeName;



}
