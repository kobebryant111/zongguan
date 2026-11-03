package com.work.ppm.application.model.usermanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

/**
 * 组织树部门和分管总经理之间关系表
 */
@Data
public class PpmDaDeptManager {
	/**
	 * id
	 */
	@Id
	private String id;
	/**
	 *	工号
	 */
	private String userid;
	/**
	 * 部门id
	 */
	private String deptid;
	/**
	 *	状态
	 */
	private String status;
	/**
	 *	 中心id
	 */
	private String centerid;
}
