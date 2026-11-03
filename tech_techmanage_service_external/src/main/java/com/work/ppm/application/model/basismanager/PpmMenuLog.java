package com.work.ppm.application.model.basismanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.Data;

import java.util.Date;



/**
 * 菜单日志记录
 */
@Data
public class PpmMenuLog {
	/**
	*	自增编号
	**/
	@Id
	private Long id;
	/**
	*	菜单名称
	**/
	private String name;
	/**
	*	菜单编号
	**/
	private String code;
	/**
	*	工号
	**/
	private String userId;
	/**
	*	名称
	**/

	private String userName;
	/**
	*	时间
	**/

	private Date createTime;
	/**
	*	ip
	**/

	private String ip;
}
