package com.work.ppm.application.model.othermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 描述
  */
@Data
public class PpmWorkKjlCght {
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
	*	同步日期
	**/
	private Long time;
	/**
	*	ID
	**/
	@Id
	private Long id;
	/**
	*	处理二级部
	**/
	private String dealdept;
	/**
	*	创建者
	**/
	private String creator;
	/**
	*	创建时间
	**/
	private Date createtime;
	/**
	*	数据来源流程ID
	**/
	private String instanceid;
	/**
	*	处理二级部ID
	**/
	private String dealdeptid;
	/**
	*	0为未发送，1为已发送
	**/
	private Long issendemail;
	/**
	*	0为未删除，1为已删除
	**/
	private Long isdel;
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
