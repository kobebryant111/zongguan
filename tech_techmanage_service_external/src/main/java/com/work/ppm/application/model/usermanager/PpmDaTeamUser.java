package com.work.ppm.application.model.usermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmDaTeamUser {
	/**
	*	字段名
	**/
	@Id
	private String id;
	/**
	*	字段名
	**/
	private String teamnum;
	/**
	*	字段名
	**/
	private String userid;
	/**
	*	1:行员 2.人力外包3.项目外包
	**/
	private Integer state;
	/**
	*	小组编号
	**/
	private String teamgrpupid;
	/**
	*	岗位
	**/
	private String gw;
}
