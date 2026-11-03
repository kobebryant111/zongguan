package com.work.ppm.application.model.integratedmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;


 /**
  * 描述
  */
@Data
public class PpmWorkKjlCghtDetail {
	/**
	*	合同编号
	**/
	private String htbh;
	/**
	*	合同名称
	**/
	private String htmc;
	/**
	*	合同金额
	**/
	private Long htje;
	/**
	*	维保期
	**/
	private Long wbq;
	/**
	*	维保费用
	**/
	private String wbfy;
	/**
	*	维保开始日期
	**/
	private Long wbksrq;
	/**
	*	维保结束日期
	**/
	private Long wbjsrq;
	/**
	*	开始日期
	**/
	private Long ksrq;
	/**
	*	结束日期
	**/
	private Long jsrq;
	/**
	*	采购内容
	**/
	private String cgnr;
	/**
	*	供应商名称
	**/
	private String gysmc;
	/**
	*	合同文本附件
	**/
	private String htwb;
	/**
	*	人力资源立项编号
	**/
	private String rlzylxbh;
	/**
	*	ID
	**/
	@Id
	private Long id;
	/**
	*	处理结果，0未处理，1已删除，2已处理
	**/
	private Long dealstatus;
	/**
	*	处理二级部
	**/
	private String dealdept;
	/**
	*	流程ID
	**/
	private String instanceid;
	/**
	*	处理二级部ID
	**/
	private String dealdeptid;
	/**
	*	字段名
	**/
	private Long htlb;
	/**
	*	是否为人力框架合同
	**/
	private String isrlht;
	/**
	*	项目预计人数
	**/
	private Long xmyjrs;
}
