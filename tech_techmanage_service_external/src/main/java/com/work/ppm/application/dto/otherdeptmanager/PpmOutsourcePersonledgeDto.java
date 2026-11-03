package com.work.ppm.application.dto.otherdeptmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

 /**
  *  非科技外包人员台账表 传输对象
  */
@Data
public class PpmOutsourcePersonledgeDto {
	/**
  	*	唯一编号
  	**/
 	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "String")
 	private String id;
	/**
	*	系统编号
	**/
	@ApiModelProperty(name = "jobId", value = "系统编号", dataType = "String")
	@Excel(name = "系统编号",orderNum = "1")
	private String jobId;
	/**
	*	姓名
	**/
	@ApiModelProperty(name = "userName", value = "姓名", dataType = "String")
	@Excel(name = "姓名",orderNum = "2")
	private String userName;
	/**
	*	所属部门
	**/
	@ApiModelProperty(name = "deptId", value = "所属部门", dataType = "String")
	private String deptId;
	/**
	 *	所属部门名称
	 **/
	@Excel(name = "所属部门",orderNum = "5")
	private String deptName;
	/**
	*	供应商编号
	**/
	@ApiModelProperty(name = "supplierId", value = "供应商编号", dataType = "String")
	private String supplierId;
	/**
	 *  供应商名称
	 */
	private String supplierName;
	/**
	*	外包方式
	**/
	@ApiModelProperty(name = "outsourceType", value = "外包方式", dataType = "String")
	private String outsourceType;
	/**
	 *	外包方式
	 **/
	private String outsourceTypeName;
	/**
	*	身份证号
	**/
	@ApiModelProperty(name = "cardId", value = "身份证号", dataType = "String")
	@Excel(name = "身份证号",orderNum = "3")
	private String cardId;
	/**
	*	入场时间
	**/
	@ApiModelProperty(name = "startingTime", value = "入场时间", dataType = "Date")
	private Date startingTime;
	/**
	*	离场时间
	**/
	@ApiModelProperty(name = "endingTime", value = "离场时间", dataType = "Date")
	private Date endingTime;
	/**
	*	人员状态 0在岗 1离岗
	**/
	@ApiModelProperty(name = "state", value = "人员状态 0在岗 1离岗", dataType = "String")
	@Excel(name = "人员状态" ,orderNum = "7",replace = {"离岗_1","在岗_0"})
	private String state;
	/**
	*	项目经理工号
	**/
	@ApiModelProperty(name = "proPerson", value = "项目经理工号", dataType = "String")
	private String proPerson;
	/**
	 * 项目经理名称
	 */
	@Excel(name = "项目经理",orderNum = "6")
	private String proPersonName;
	/**
	*	联系方式
	**/
	@ApiModelProperty(name = "telphone", value = "联系方式", dataType = "String")
	@Excel(name = "手机号",orderNum = "4")
	private String telphone;
	/**
	*	人脸照片
	**/
	@ApiModelProperty(name = "imageNo", value = "人脸照片", dataType = "String")
	private String imageNo;
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
	*	更新时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "更新时间", dataType = "Date")
	private Date modifiedTime;
	/**
	*	更新人
	**/
	@ApiModelProperty(name = "modifiedBy", value = "更新人", dataType = "String")
	private String modifiedBy;

	/**
	 * 所属部门查询
	 */
	private String deptVal;
	/**
	 * 姓名查询
	 */
	private String userNameVal;
	/**
	 * 项目经理查询
	 */
	private String pmVal;
	/**
	 * 项目经理查询
	 */
	private Integer adminId;
	/**
	 * 是否改变状态
	 */
	private Boolean isChangeState;

}
