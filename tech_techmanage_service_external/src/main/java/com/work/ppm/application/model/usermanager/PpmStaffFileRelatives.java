package com.work.ppm.application.model.usermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmStaffFileRelatives {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	姓名
	**/
	private String name;
	/**
	*	关系
	**/
	private String relationship;
	/**
	*	性别
	**/
	private String gender;
	/**
	*	身份证号
	**/
	private String certno;
	/**
	*	出生日期
	**/
	private String birthday;
	/**
	*	工作单位
	**/
	private String workunit;
	/**
	*	职务
	**/
	private String post;
	/**
	*	居住地址
	**/
	private String address;
	/**
	*	电话
	**/
	private String phone;
	/**
	*	是否紧急联系人
	**/
	private String flag;
	/**
	*	工号
	**/
	private String badge;
	/**
	*	关联主键
	**/
	private String rid;
	/**
	*	排序列
	**/
	private Long ord;
	/**
	*	更新人工号
	**/
	private String updateuserid;
	/**
	*	更新人时间
	**/
	private Date updatedate;
}
