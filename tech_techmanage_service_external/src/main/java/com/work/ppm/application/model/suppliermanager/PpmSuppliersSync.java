package com.work.ppm.application.model.suppliermanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



 /**
  * 同步采购数据 - 项目供应商信息表 实体对象
  */
@Data
public class PpmSuppliersSync {
	/**
	*	唯一编号
	**/
	@Id
	private Long id;
	/**
	*	项目编号
	**/
	private String xmbh;
	/**
	*	合同id
	**/
	private String htid;
	/**
	*	合同编号
	**/
	private String htbh;
	/**
	*	合同名称
	**/
	private String htmc;
	/**
	*	项目名称
	**/
	private String xmmc;
	/**
	*	合同类别
	**/
	private String htlb;
	/**
	*	采购内容
	**/
	private String cgnr;
	/**
	*	合同金额
	**/
	private Double htje;
	/**
	*	交付日期
	**/
	private String jfrq;
	/**
	*	工期
	**/
	private String gq;
	/**
	*	邮政编码(乙方)
	**/
	private String yzbm;
	/**
	*	开户行(乙方)
	**/
	private String khh;
	/**
	*	帐号(乙方)
	**/
	private String zh;
	/**
	*	合同终止日期
	**/
	private String htzzrq;
	/**
	*	第三方收款单位名称
	**/
	private String skdwmc;
	/**
	*	第三方开户行
	**/
	private String dsfkhh;
	/**
	*	第三方账号
	**/
	private String dsfzh;
	/**
	*	对象存储ID(以‘|’为分割符)
	**/
	private String dxccid;
	/**
	*	供应商编号
	**/
	private String gysbh;
	/**
	*	供应商名称
	**/
	private String gsmc;
	/**
	*	供应商简称
	**/
	private String gsjc;
	/**
	*	是否中国境外(0否1是)
	**/
	private String sfzgjw;
	/**
	*	国别/地区
	**/
	private String gbdq;
	/**
	*	成立年份
	**/
	private String clnf;
	/**
	*	注册资金
	**/
	private Double zczj;
	/**
	*	同步时间
	**/
	private Date syncTime;
}
