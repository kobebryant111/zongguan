package com.work.ppm.application.dto.systemmanager;

import lombok.Data;
import java.util.Date;



 /**
  * 中心
  */
@Data
public class PpmDaCenterDto {
	/**
	*	部门类型 01.管理中心 02.研发中心 03.数据中心 04.测试中心
	**/
	private String centertype;
	/**
	*	主键
	**/
	private Integer id;
	/**
	*	中心名称
	**/
	private String centername;
	/**
	*	更新人
	**/
	private String regusrid;
	/**
	*	更新时间
	**/
	private Date regtime;
	/**
	*	负责人
	**/
	private String resuserid;
	/**
	*	负责人姓名
	**/
	private String resname;
	/**
	*	是否删除
	**/
	private Integer isdel;
	/**
	*	排序列
	**/
	private Integer ord;
	/**
	*	部门类型1.管理中心 2.研发中心 3.数据中心 4.测试中心
	**/
	private String type;
	/**
	*	中心架构师人员id
	**/
	private String zxjgsusrid;
	/**
	*	中心架构师人员name
	**/
	private String zxjgsusername;
	 /**
	  * 部门id
	  */
	 private String depId;
	 /**
	  * 部门名称
	  */
	 private String depName;
}
