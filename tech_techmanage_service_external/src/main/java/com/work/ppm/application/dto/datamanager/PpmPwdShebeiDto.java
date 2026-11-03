package com.work.ppm.application.dto.datamanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;
import java.util.Date;



 /**
  * 密管设备
  */
@Data
@ExcelTarget("ppmPwdShebeiDto")
public class PpmPwdShebeiDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	系统
	**/
	@Excel(name = "系统名称", orderNum = "1")
	private String systemname;
	/**
	*	用户类型
	**/
	@Excel(name = "用户类型", orderNum = "3")
	private String usertype;
	/**
	*	设备ip
	**/
	@Excel(name = "设备ip", orderNum = "2")
	private String ip;
	/**
	*	用户名
	**/
	@Excel(name = "用户名", orderNum = "4")
	private String username;
	/**
	*	密码
	**/
	@Excel(name = "密码", orderNum = "6")
	private String pwd;
	/**
	*	密管登陆用户
	**/
	private String loginuser;
	/**
	*	到期时间
	**/
	@Excel(name = "到期时间", orderNum = "8", exportFormat = "yyyy-MM-dd")
	private Date endtime;
	/**
	*	字段名
	**/
	private String status;
	/**
	*	申请人
	**/
	@Excel(name = "申请人", orderNum = "5")
	private String applyer;
	/**
	*	应用名称
	**/
	private String servename;
	/**
	*	字段名
	**/
	private String dealcode;
	/**
	*	开始使用时间
	**/
	private Date starttime;
	/**
	*	归还日期
	**/
	private Date checkindate;
	/**
	*	字段名
	**/
	private String hascheckin;
	/**
	*	通知状态
	**/
	private String remindstatus;
	 /**
	  用户
	  */
	private String userid;
	 /**
	  系统名
	  */
	private String systemid;
	 /**
	  分组名称
	  */
	private String groupName;
	 /**
	  使用时长
	  */
	private String usehour;
	 /**
	   是否显示密码
	  */
	private Boolean showPwd = false;
	 /**
	  * 密码状态
	  */
	 @Excel(name = "状态", orderNum = "7")
	private String pwdStatus;
}
