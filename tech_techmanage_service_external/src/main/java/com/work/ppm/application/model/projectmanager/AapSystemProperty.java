package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 系统属性
  */
@Data
public class AapSystemProperty {
	/**
	*	字段名
	**/
	@Id
	private String id;
	/**
	*	系统
	**/
	private String systemid;
	/**
	*	科技负责主管A_20170120_000108
	**/
	private String tecFzzgUser;
	/**
	*	科技责任部门A_20160426_000083
	**/
	private String tecdep;
	/**
	*	系统管理员A岗A_20160426_000086
	**/
	private String admina;
	/**
	*	系统管理员B岗A_20160426_000087
	**/
	private String adminb;
	/**
	*	系统级配置管理员A_20210128_000661
	**/
	private String configuser;
	/**
	*	系统架构组运维人员A_20210119_000651
	**/
	private String jgywuser;
	/**
	*	系统研发负责人A岗A_20200507_000391
	**/
	private String yfmanagera;
	/**
	*	系统研发负责人B岗A_20200507_000392
	**/
	private String yfmanagerb;
	/**
	*	系统业务运维人员A_20210204_000671
	**/
	private String ywywuser;
	/**
	*	系统运维负责人A岗A_20160331_000051
	**/
	private String ywmanagera;
	/**
	*	系统运维负责人B岗A_20160426_000085
	**/
	private String ywmanagerb;
	/**
	*	系统总负责人A_20210118_000641
	**/
	private String zmanager;
	/**
	*	业务联系人A_20160426_000088
	**/
	private String ywlinkman;
	/**
	*	业务主管部门A_20160426_000084
	**/
	private String busdep;
	/**
	*	运维负责人（甲乙类系统）A_20210302_000711
	**/
	private String ywfzruser;
	/**
	*	被上收部门A_20210302_000721
	**/
	private String collectdep;
}
