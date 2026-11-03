package com.work.ppm.application.dto.integratedmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
  *  信息变更申请信息表 传输对象
  */
@Data
public class PpmOutsourcUpdateInfoDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	父编号
	**/
	@ApiModelProperty(name = "pid", value = "父编号", dataType = "Long")
	private Long pid;
	/**
	*	工号
	**/
	@ApiModelProperty(name = "jobId", value = "工号", dataType = "String")
	private String jobId;
	/**
	*	姓名
	**/
	@ApiModelProperty(name = "userName", value = "姓名", dataType = "String")
	private String userName;
	/**
	*	流程ID
	**/
	@ApiModelProperty(name = "instanceid", value = "流程ID", dataType = "String")
	private String instanceid;
	/**
	*	前岗位
	**/
	@ApiModelProperty(name = "positionBe", value = "前岗位", dataType = "String")
	private String positionBe;
	/**
	*	现岗位
	**/
	@ApiModelProperty(name = "positionAf", value = "现岗位", dataType = "String")
	private String positionAf;
	/**
	*	前外包方式
	**/
	@ApiModelProperty(name = "outsourceTypeBe", value = "前外包方式", dataType = "String")
	private String outsourceTypeBe;
	/**
	*	现外包方式
	**/
	@ApiModelProperty(name = "outsourceTypeAf", value = "现外包方式", dataType = "String")
	private String outsourceTypeAf;
	/**
	*	前结算方式
	**/
	@ApiModelProperty(name = "userTypeBe", value = "前结算方式", dataType = "String")
	private String userTypeBe;
	/**
	*	现结算方式
	**/
	@ApiModelProperty(name = "userTypeAf", value = "现结算方式", dataType = "String")
	private String userTypeAf;
	/**
	*	前虚拟机账号
	**/
	@ApiModelProperty(name = "vmAccountBe", value = "前虚拟机账号", dataType = "String")
	private String vmAccountBe;
	/**
	*	现虚拟机账号
	**/
	@ApiModelProperty(name = "vmAccountAf", value = "现虚拟机账号", dataType = "String")
	private String vmAccountAf;
	/**
	*	前门禁卡编号
	**/
	@ApiModelProperty(name = "entranceCardBe", value = "前门禁卡编号", dataType = "String")
	private String entranceCardBe;
	/**
	*	现门禁卡编号
	**/
	@ApiModelProperty(name = "entranceCardAf", value = "现门禁卡编号", dataType = "String")
	private String entranceCardAf;
	/**
	*	前所属合同
	**/
	@ApiModelProperty(name = "newContractIdBe", value = "前所属合同", dataType = "Long")
	private Long newContractIdBe;
	/**
	*	现所属合同
	**/
	@ApiModelProperty(name = "newContractIdAf", value = "现所属合同", dataType = "Long")
	@JsonInclude
	private Long newContractIdAf;
	/**
	*	前总办议案 修改时需改变人数
	**/
	@ApiModelProperty(name = "ceneralOfficeMotionBe", value = "前总办议案 修改时需改变人数", dataType = "Long")
	private Long ceneralOfficeMotionBe;
	/**
	*	现总办议案
	**/
	@ApiModelProperty(name = "ceneralOfficeMotionAf", value = "现总办议案", dataType = "Long")
	private Long ceneralOfficeMotionAf;
	/**
	*	前征信报告
	**/
	@ApiModelProperty(name = "creditReportBe", value = "前征信报告", dataType = "String")
	private String creditReportBe;
	/**
	*	现征信报告
	**/
	@ApiModelProperty(name = "creditReportAf", value = "现征信报告", dataType = "String")
	private String creditReportAf;
	/**
	*	前社保缴纳证明/合同
	**/
	@ApiModelProperty(name = "ssPaymentProveBe", value = "前社保缴纳证明/合同", dataType = "String")
	private String ssPaymentProveBe;
	/**
	*	现社保缴纳证明/合同
	**/
	@ApiModelProperty(name = "ssPaymentProveAf", value = "现社保缴纳证明/合同", dataType = "String")
	private String ssPaymentProveAf;
	/**
	*	前保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表
	**/
	@ApiModelProperty(name = "confidentCommitmentBe",
			value = "前保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表", dataType = "String")
	private String confidentCommitmentBe;
	/**
	*	现保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表
	**/
	@ApiModelProperty(name = "confidentCommitmentAf",
			value = "现保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表", dataType = "String")
	private String confidentCommitmentAf;

	 /**
	  *	前尽调报告
	  **/
	 @ApiModelProperty(name = "dueDiligenceReportBe",
			 value = "前尽调报告", dataType = "String")
	 private String dueDiligenceReportBe;
	 /**
	  *	现尽调报告
	  **/
	 @ApiModelProperty(name = "dueDiligenceReportAf",
			 value = "现尽调报告", dataType = "String")
	 private String dueDiligenceReportAf;
	 /**
	  *	前其他附件
	  **/
	 @ApiModelProperty(name = "qtFileBe",
			 value = "前其他附件", dataType = "String")
	 private String qtFileBe;
	 /**
	  *	现其他附件
	  **/
	 @ApiModelProperty(name = "qtFileAf",
			 value = "现其他附件", dataType = "String")
	 private String qtFileAf;
	/**
	*	前身份证反正面
	**/
	@ApiModelProperty(name = "otherFileBe", value = "前身份证反正面", dataType = "String")
	private String otherFileBe;
	/**
	*	现身份证反正面
	**/
	@ApiModelProperty(name = "otherFileAf", value = "现身份证反正面", dataType = "String")
	private String otherFileAf;
	/**
	*	前人脸照片
	**/
	@ApiModelProperty(name = "faceimageBe", value = "前人脸照片", dataType = "String")
	private String faceimageBe;
	/**
	*	现人脸照片
	**/
	@ApiModelProperty(name = "faceimageAf", value = "现人脸照片", dataType = "String")
	private String faceimageAf;
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
	  * 申请人id
	  */
	 private String applyer;
	 /**
	  * 申请人名称
	  */
	 private String applyerName;
	 /**
	  * 部门编号
	  */
	 private String applyerDept;
	 /**
	  * 部门名称
	  */
	 private String applyerDeptName;
	 /**
	  * 状态名称
	  */
	 private String state;
	 /**
	  * 状态名称
	  */
	 private String stateName;
	 /**
	  * 当前处理人
	  */
	 private String currentId;
	 /**
	  * 当前处理人名称
	  */
	 private String currentName;
	 /**
	  * 状态
	  */
	 private String presenceState;
	 /**
	  * 负责人
	  */
	 private String personInCharge;

	 /**
	  * 征信报告数组
	  */
	 private String[] creditReports;
	 /**
	 * 社保缴纳证明/合同数组
	 */
	 private String[] ssPaymentProves;
	 /**
	  * 保密承诺书/信息查询授权书/案防承诺书/行为规范承诺书/信息备案表数组
	  */
	 private String[] confidentCommitments;
	 /**
	  * 尽调报告数组
	  */
	 private String[] dueDiligenceReports;
	 /**
	  * 其他附件数组
	  */
	 private String[] qtFiles;
	 /**
	  * 身份证正反面数组
	  */
	 private String[] otherFiles;

	 /**
	  * 前合同编号编号
	  */
	 private String newContractIdBeNo;
	 /**
	  * 前合同编号名称
	  */
	 private String newContractIdBeName;
	 /**
	  * 前合同编号 下拉名称
	  */
	 private String newContractIdBeLabel;
	 /**
	  * 后合同编号编号
	  */
	 private String newContractIdAfNo;
	 /**
	  * 后合同编号名称
	  */
	 private String newContractIdAfName;
	 /**
	  * 后合同编号 下拉名称
	  */
	 private String newContractIdAfLabel;
	 /**
	  * 前总办议案编号
	  */
	 private String ceneralOfficeMotionBeNo;
	 /**
	  * 前总办议案名称
	  */
	 private String ceneralOfficeMotionBeName;
	 /**
	  * 前总办议案 下拉名称
	  */
	 private String ceneralOfficeMotionBeLabel;
	 /**
	  * 后总办议案编号
	  */
	 private String ceneralOfficeMotionAfNo;
	 /**
	  * 后总办议案名称
	  */
	 private String ceneralOfficeMotionAfName;
	 /**
	  * 后总办议案 下拉名称
	  */
	 private String ceneralOfficeMotionAfLabel;
	 /**
	  * 费用结算 过滤
	  */
	 private String payConfirmId;

	 /**
	  * 支付确认 过滤
	  */
	 private String payConfirmNo;
	 /**
	  * 部门编号
	  */
	 private String deptIdBe;
	 /**
	  * 变更后的部门编号
	  */
	 private String deptIdAf;
	 /**
	  * 系统编号
	  */
	 private String systemNoBe;
	 /**
	  * 变更后的系统编号
	  */
	 private String systemNoAf;
	 /**
	  * 系统负责人
	  */
	 private String personInChargeBe;
	 /**
	  * 变更后的系统负责人
	  */
	 private String personInChargeAf;
	 /**
	  * 定级级别
	  * junior-初级
	  * intermediate-中级
	  * senior-高级
	  * expert-资深
	  */
	 private String evaluationLevelBe;
	 /**
	  * 定级理由，200字以内
	  */
	 private String levelReasonBe;
	 /**
	  * 定级人员
	  */
	 private String evaluatorIdBe;
	 /**
	  * 变更前定级时间
	  */
	 private String evaluationTimeBe;
	 /**
	  * 定级时间
	  */
	 private Date evaluationTime;
	 /**
	  * 变更后的定级级别
	  * junior-初级
	  * intermediate-中级
	  * senior-高级
	  * expert-资深
	  */
	 private String evaluationLevelAf;
	 /**
	  * 变更后的定级理由，200字以内
	  */
	 private String levelReasonAf;
	 /**
	  * 变更后的定级人员
	  */
	 private String evaluatorIdAf;
	 /**
	  * 定级日期
	  */
	 private String evaluationDate;
	 /**
	  * 单价
	  */
	 private String price;
	 /**
	  * 上下班时间
	  */
	 private String workTime;

 }
