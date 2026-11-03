package com.work.ppm.application.dto.suppliermanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;



 /**
  *  同步采购数据 - 项目供应商信息附件表 传输对象
  */
@Data
public class PpmSuppliersSyncFileDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	项目id
	**/
	@ApiModelProperty(name = "xmid", value = "项目id", dataType = "String")
	private String xmid;
	/**
	*	项目编号
	**/
	@ApiModelProperty(name = "xmbh", value = "项目编号", dataType = "String")
	private String xmbh;
	/**
	*	对象存储id
	**/
	@ApiModelProperty(name = "objectId", value = "对象存储id", dataType = "String")
	private String objectId;
	/**
	*	文件编号
	**/
	@ApiModelProperty(name = "fileNo", value = "文件编号", dataType = "String")
	private String fileNo;
	/**
	*	文件名称
	**/
	@ApiModelProperty(name = "fileName", value = "文件名称", dataType = "String")
	private String fileName;
	/**
	*	是否删除
	**/
	@ApiModelProperty(name = "isDel", value = "是否删除", dataType = "String")
	private String isDel;
	/**
	*	创建时间
	**/
	@ApiModelProperty(name = "createdTime", value = "创建时间", dataType = "Date")
	private Date createdTime;
	/**
	*	修改时间
	**/
	@ApiModelProperty(name = "modifiedTime", value = "修改时间", dataType = "Date")
	private Date modifiedTime;
}
