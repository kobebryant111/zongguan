package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



/**
 * 系统负责人
 */
@Data
public class AapSystemResponsibility {
	/**
	*	系统ID
	**/
	@Id
	private String resId;
	/**
	*	科技责任部门
	**/
	private String tecDep;
	/**
	*	字段名
	**/
	private String tecDepName;
	/**
	*	系统责任人A岗
	**/
	private String principala;
	/**
	*	字段名
	**/
	private String principalaName;
	/**
	*	系统责任人B岗
	**/
	private String principalb;
	/**
	*	字段名
	**/
	private String principalbName;
	/**
	*	系统管理员A岗
	**/
	private String admina;
	/**
	*	字段名
	**/
	private String adminaName;
	/**
	*	系统管理员B岗
	**/
	private String adminb;
	/**
	*	字段名
	**/
	private String adminbName;
	/**
	*	业务主管部门
	**/
	private String busDep;
	/**
	*	字段名
	**/
	private String busDepName;
	/**
	*	业务联系人
	**/
	private String busPrin;
	/**
	*	字段名
	**/
	private String busPrinName;
	/**
	*	超级管理员
	**/
	private String superAdmin;
	/**
	*	字段名
	**/
	private String superAdminName;
	/**
	*	创建时间
	**/
	private Date createTime;
	/**
	*	修改时间
	**/
	private Date modifyTime;
	/**
	*	创建人员
	**/
	private String createPerson;
	/**
	*	修改人员
	**/
	private String modifyPerson;
	/**
	*	迁移字段
	**/
	private String transport;
	/**
	*	科技负责主管
	**/
	private String keji;
	/**
	*	字段名
	**/
	private String kejiName;
	/**
	*	是否涉及到测试中心
	**/
	private String istest;
	/**
	*	测试联系人
	**/
	private String testmanager;
	/**
	*	字段名
	**/
	private String testmanagername;
	/**
	 * 系统ID
	 */
	private String systemId;
}
