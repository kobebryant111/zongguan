package com.work.ppm.application.model.otherdeptmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;

 /**
  * 非科技外包人员台账表 实体对象
  */
@Data
public class PpmOutsourcePersonledge {
 	/**
  	*	唯一编号
  	**/
 	@Id
	private String id;
	/**
	*	系统编号
	**/
	private String jobId;
	/**
	*	姓名
	**/
	private String userName;
	/**
	*	所属部门
	**/
	private String deptId;
	/**
	*	供应商编号
	**/
	private String supplierId;
	/**
	*	外包方式
	**/
	private String outsourceType;
	/**
	*	身份证号
	**/
	private String cardId;
	/**
	*	入场时间
	**/
	private Date startingTime;
	/**
	*	离场时间
	**/
	private Date endingTime;
	/**
	*	人员状态 0在岗 1离岗
	**/
	private String state;
	/**
	*	项目经理工号
	**/
	private String proPerson;
	/**
	*	联系方式
	**/
	private String telphone;
	/**
  	* 人脸照片
  	*/
	private String imageNo;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	创建人
	**/
	private String createdBy;
	/**
	*	更新时间
	**/
	private Date modifiedTime;
	/**
	*	更新人
	**/
	private String modifiedBy;

}
