package com.work.ppm.application.dto.suppliermanager;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;


/**
  *  同步采购数据 - 项目供应商信息表 传输对象
  */
@Data
public class PpmSuppliersSyncDto {
	/**
	*	唯一编号
	**/
	@ApiModelProperty(name = "id", value = "唯一编号", dataType = "Long")
	private Long id;
	/**
	*	项目编号
	**/
	@ApiModelProperty(name = "xmbh", value = "项目编号", dataType = "String")
	private String xmbh;
	/**
	*	合同id
	**/
	@ApiModelProperty(name = "htid", value = "合同id", dataType = "String")
	private String htid;
	/**
	*	合同编号
	**/
	@ApiModelProperty(name = "htbh", value = "合同编号", dataType = "String")
	private String htbh;
	/**
	*	合同名称
	**/
	@ApiModelProperty(name = "htmc", value = "合同名称", dataType = "String")
	private String htmc;
	/**
	*	项目名称
	**/
	@ApiModelProperty(name = "xmmc", value = "项目名称", dataType = "String")
	private String xmmc;
	/**
	*	合同类别
	**/
	@ApiModelProperty(name = "htlb", value = "合同类别", dataType = "String")
	private String htlb;
	/**
	*	采购内容
	**/
	@ApiModelProperty(name = "cgnr", value = "采购内容", dataType = "String")
	private String cgnr;
	/**
	*	合同金额
	**/
	@ApiModelProperty(name = "htje", value = "合同金额", dataType = "Double")
	private Double htje;
	/**
	*	交付日期
	**/
	@ApiModelProperty(name = "jfrq", value = "交付日期", dataType = "String")
	private String jfrq;
	/**
	*	工期
	**/
	@ApiModelProperty(name = "gq", value = "工期", dataType = "String")
	private String gq;
	/**
	*	邮政编码(乙方)
	**/
	@ApiModelProperty(name = "yzbm", value = "邮政编码(乙方)", dataType = "String")
	private String yzbm;
	/**
	*	开户行(乙方)
	**/
	@ApiModelProperty(name = "khh", value = "开户行(乙方)", dataType = "String")
	private String khh;
	/**
	*	帐号(乙方)
	**/
	@ApiModelProperty(name = "zh", value = "帐号(乙方)", dataType = "String")
	private String zh;
	/**
	*	合同终止日期
	**/
	@ApiModelProperty(name = "htzzrq", value = "合同终止日期", dataType = "String")
	private String htzzrq;
	/**
	*	第三方收款单位名称
	**/
	@ApiModelProperty(name = "skdwmc", value = "第三方收款单位名称", dataType = "String")
	private String skdwmc;
	/**
	*	第三方开户行
	**/
	@ApiModelProperty(name = "dsfkhh", value = "第三方开户行", dataType = "String")
	private String dsfkhh;
	/**
	*	第三方账号
	**/
	@ApiModelProperty(name = "dsfzh", value = "第三方账号", dataType = "String")
	private String dsfzh;
	/**
	*	对象存储ID(以‘|’为分割符)
	**/
	@ApiModelProperty(name = "dxccid", value = "对象存储ID(以‘|’为分割符)", dataType = "String")
	private String dxccid;
	/**
	*	供应商编号
	**/
	@ApiModelProperty(name = "gysbh", value = "供应商编号", dataType = "String")
	private String gysbh;
	/**
	*	供应商名称
	**/
	@ApiModelProperty(name = "gsmc", value = "供应商名称", dataType = "String")
	private String gsmc;
	/**
	*	供应商简称
	**/
	@ApiModelProperty(name = "gsjc", value = "供应商简称", dataType = "String")
	private String gsjc;
	/**
	*	是否中国境外(0否1是)
	**/
	@ApiModelProperty(name = "sfzgjw", value = "是否中国境外(0否1是)", dataType = "String")
	private String sfzgjw;
	/**
	*	国别/地区
	**/
	@ApiModelProperty(name = "gbdq", value = "国别/地区", dataType = "String")
	private String gbdq;
	/**
	*	成立年份
	**/
	@ApiModelProperty(name = "clnf", value = "成立年份", dataType = "String")
	private String clnf;
	/**
	*	注册资金
	**/
	@ApiModelProperty(name = "zczj", value = "注册资金", dataType = "Double")
	private Double zczj;
	/**
	*	同步时间
	**/
	@ApiModelProperty(name = "syncTime", value = "同步时间", dataType = "Date")
	private Date syncTime;

	 /**
	  * 项目来源
	  */
	private String projectSource;

	 /**
	  * 关联附件浏览
	  */
	 private List<String> supplierSyncFiles;
}
