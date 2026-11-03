package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 机构
  */
@Data
public class CommOrganization {
	/**
	*	机构ID
	**/
	@Id
	private Long compid;
	/**
	*	机构编码
	**/
	private String compcode;
	/**
	*	中文名称
	**/
	private String location;
	/**
	*	字段名
	**/
	private String abbreviation;
	/**
	*	所属上级机构
	**/
	private Long adminid;
	/**
	*	字段名
	**/
	private Long compcate;
	/**
	*	机构级别
	**/
	private String compgrade;
	/**
	*	机构负责人
	**/
	private Long compcharge;
	/**
	*	机构分管领导
	**/
	private Long director;
	/**
	*	生效日期
	**/
	private Date effectdate;
	/**
	*	是否失效
	**/
	private Long isdisabled;
	/**
	*	失效日期
	**/
	private Date disableddate;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	0.HR数据  1.本地新增  2.本地编辑
	**/
	private String updateflg;
	/**
	*	最新更新时间
	**/
	private Date lastupdatedate;
	/**
	*	机构ID(HR)
	**/
	private Long compidhr;
	/**
	*	四位码
	**/
	private String displaycode;
	/**
	*	机构序列(排序用)
	**/
	private Long comorgid;
}
