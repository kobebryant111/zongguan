package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 同步采购数据 - 项目供应商信息附件表 实体对象
  */
@Data
public class PpmSuppliersSyncFile {
	/**
	*	唯一编号
	**/
	@Id
	private Long id;
	/**
	*	项目id
	**/
	private String xmid;
	/**
	*	项目编号
	**/
	private String xmbh;
	/**
	*	对象存储id
	**/
	private String objectId;
	/**
	*	文件编号
	**/
	private String fileNo;
	/**
	*	文件名称
	**/
	private String fileName;
	/**
	*	是否删除
	**/
	private String isDel;
	/**
	*	创建时间
	**/
	private Date createdTime;
	/**
	*	修改时间
	**/
	private Date modifiedTime;
}
