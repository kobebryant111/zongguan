package com.work.ppm.application.dto.projectmanager;

import lombok.Data;
import java.util.Date;



/**
 * 分行审批部门配置
 */
@Data
public class PpmFhspbmpzDto {
	/**
	*	分行研发经理
	**/
	private String fhyfjl;
	/**
	*	分行研发经理名称
	**/
	private String fhyfjlName;
	/**
	*	主键
	**/
	private String id;
	/**
	*	部门
	**/
	private String deptno;
	/**
	*	部门名称
	**/
	private String deptname;
	/**
	*	合规部总经理
	**/
	private String hgbzjl;
	/**
	*	合规部总经理名称
	**/
	private String hgbzjlname;
	/**
	*	人力资源部总经理
	**/
	private String rlzybzjl;
	/**
	*	人力资源部总经理名称
	**/
	private String rlzybzjlname;
	/**
	*	是否删除   0:否    1:是
	**/
	private String isdel;
	/**
	*	字段名
	**/
	private String createuserid;
	/**
	*	字段名
	**/
	private String createusername;
	/**
	*	字段名
	**/
	private Date createtime;

}
