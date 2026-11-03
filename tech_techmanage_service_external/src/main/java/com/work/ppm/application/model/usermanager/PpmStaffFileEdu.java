package com.work.ppm.application.model.usermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmStaffFileEdu {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	工号
	**/
	private String badge;
	/**
	*	开始时间
	**/
	private Date starttime;
	/**
	*	结束时间
	**/
	private Date endtime;
	/**
	*	教育类别
	**/
	private String edutype;
	/**
	*	学校
	**/
	private String school;
	/**
	*	专业
	**/
	private String major;
	/**
	*	学习形式
	**/
	private String learnmodality;
	/**
	*	学历
	**/
	private String education;
	/**
	*	学位
	**/
	private String degree;
	/**
	*	关联ID
	**/
	private String rid;
	/**
	*	排序
	**/
	private Long ord;
	/**
	*	更新人工号
	**/
	private String updateuserid;
	/**
	*	更新时间
	**/
	private Date updatedate;
}
