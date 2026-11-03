package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmOutsourcingInfoPrice {
	/**
	*	主键
	**/
	@Id
	private Long id;
	/**
	*	供应商编号
	**/
	private String orgcode;
	/**
	*	岗位
	**/
	private String position;
	/**
	*	1档开始价格
	**/
	private Long firststartprie;
	/**
	*	1档结束价格
	**/
	private Long firstendprie;
	/**
	*	2档开始价格
	**/
	private Long secondstartprie;
	/**
	*	2档结束价格
	**/
	private Long secondendprie;
	/**
	*	3档开始价格
	**/
	private Long thirdstartprie;
	/**
	*	3档结束价格
	**/
	private Long thirdendprie;
	/**
	*	4档开始价格
	**/
	private Long fourthstartprie;
	/**
	*	4档结束价格
	**/
	private Long fourthendprie;
	/**
	*	备注
	**/
	private String remark;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	创建人
	**/
	private String createusrid;
	 /**
	  * 是否删除
	  */
	private String isDel;
}
