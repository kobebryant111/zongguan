package com.work.ppm.application.dto.datamanager;

import lombok.Data;
import java.util.Date;



/**
 * 密管设备配置
 */
@Data
public class PpmPwdShebeiConfigureDto {
	/**
	*	字段名
	**/
	private Long id;
	/**
	*	部门
	**/
	private String depid;
	/**
	*	系统
	**/
	private String systemid;
	/**
	*	用户类型
	**/
	private String usertype;
	/**
	*	设备ip
	**/
	private String ip;
	/**
	*	用户id
	**/
	private String userid;
	/**
	*	上一次审批人
	**/
	private String spuser;
	/**
	*	上一次归还时间
	**/
	private Date checkindate;
	/**
	*	上一次申请时间
	**/
	private Date applytime;
	/**
	*	上一次使用者
	**/
	private String applyer;
	/**
	*	状态
	**/
	private String status;
	/**
	*	是否白名单
	**/
	private String iswhite;
	/**
	*	系统属性
	**/
	private String operatortype;
	/**
	*	登录属性
	**/
	private String logintype;
	/**
	*	组名
	**/
	private String groupname;
	/**
	*	服务器名称
	**/
	private String servername;
	/**
	*	协议
	**/
	private String networkprotocol;
	/**
	*	另外可申请部门
	**/
	private String otherdept;
	/**
	*	密码
	**/
	private String pwd;
}
