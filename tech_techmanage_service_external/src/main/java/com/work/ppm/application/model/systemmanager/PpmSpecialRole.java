package com.work.ppm.application.model.systemmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 特殊角色菜单关联
  */
@Data
public class PpmSpecialRole {
	/**
	*	自增编号
	**/
	@Id
	private Integer id;
	/**
	*	角色编号
	**/
	private String roleId;
}
