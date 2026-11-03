package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;
import java.util.Date;



/**
 * 老的业务意向
 */
@Data
public class PpmDemandApply {
	/**
	*	??PPM_DEMAND_APPLY_ID ??EOS?????????
	**/
	@Id
	private Long id;
	/**
	*	字段名
	**/
	private String applyno;
	/**
	*	字段名
	**/
	private String demandno;
	/**
	*	字段名
	**/
	private String demandname;
	/**
	*	字段名
	**/
	private String demanduserid;
	/**
	*	字段名
	**/
	private String demanddeptno;
	/**
	*	????????
	**/
	private String planningno;
	/**
	*	01???
02???????
????????BUSI_DEMAND_APPLY_SOURCE
	**/
	private String source;
	/**
	*	????????BUSI_DEMAND_APPLY_PRIORITY
	**/
	private String priority;
	/**
	*	????????BUSI_DEMAND_APPLY_EMERGENCY
	**/
	private String emergency;
	/**
	*	字段名
	**/
	private String reason;
	/**
	*	字段名
	**/
	private String busiuserid;
	/**
	*	字段名
	**/
	private String mSysno;
	/**
	*	?????????????,???
	**/
	private String sSysno;
	/**
	*	A+??A??B??C??D?
????????
BUSI_SYSTEM_OPERAGRADE
	**/
	private String operagrade;
	/**
	*	字段名
	**/
	private String busideptno;
	/**
	*	????????
	**/
	private String tectdeptno;
	/**
	*	字段名
	**/
	private String teamno;
	/**
	*	字段名
	**/
	private String teamrespid;
	/**
	*	字段名
	**/
	private String manageruserid;
	/**
	*	字段名
	**/
	private Double tbudget;
	/**
	*	字段名
	**/
	private Double cbudget;
	/**
	*	字段名
	**/
	private Double bbudget;
	/**
	*	字段名
	**/
	private Date busiexpect;
	/**
	*	字段名
	**/
	private String target;
	/**
	*	字段名
	**/
	private String demanddesc;
	/**
	*	????????GLOBAL_YESNO
	**/
	private String isresearch;
	/**
	*	字段名
	**/
	private Date tecexpect;
	/**
	*	字段名
	**/
	private String flowid;
	/**
	*	字段名
	**/
	private String instanceid;
	/**
	*	1-????0-???
	**/
	private Integer isdel;
	/**
	*	字段名
	**/
	private Date createtime;
	/**
	*	字段名
	**/
	private String creators;
	/**
	*	字段名
	**/
	private Date lastmodtime;
	/**
	*	字段名
	**/
	private String lastmoduser;
	/**
	*	字段名
	**/
	private String operaresid;
	/**
	*	字段名
	**/
	private Date bringupdate;
	/**
	*	字段名
	**/
	private String isnew;
	/**
	*	字段名
	**/
	private String sworkunit;
	/**
	*	字段名
	**/
	private String tworkunit;
	/**
	*	字段名
	**/
	private Double tecworkload;
	/**
	*	字段名
	**/
	private Double busiworkload;
	/**
	*	字段名
	**/
	private Double obudget;
	/**
	*	字段名
	**/
	private String profit;
	/**
	*	字段名
	**/
	private String researchuserid;
	/**
	*	字段名
	**/
	private Double sumbudget;
	/**
	*	字段名
	**/
	private Double sumworkload;
	/**
	*	字段名
	**/
	private String sumworkunit;
	/**
	*	字段名
	**/
	private String proscale;
	/**
	*	字段名
	**/
	private String managercheckuserid;
	/**
	*	字段名
	**/
	private String otherbudgetdesc;
	/**
	*	字段名
	**/
	private String domain;
	/**
	*	字段名
	**/
	private String satisficing;
	/**
	*	字段名
	**/
	private String dremark;
	/**
	*	字段名
	**/
	private String demandsource;
	/**
	*	网银类型
	**/
	private String wyType;
	/**
	*	科技业务是否同一个人
	**/
	private String istecbus;
	/**
	*	是否新的需求
	**/
	private String newdemand;
	/**
	*	是否行办
	**/
	private String istobank;
	/**
	*	行办日期 
	**/
	private Date banktime;
	/**
	*	是否大中型 02是  01否
	**/
	private String islarge;
	/**
	*	业务副总和分摊确认是否有同一个
	**/
	private String isftywjl;
	/**
	*	是否非功能需求
	**/
	private String isfgn;
	/**
	*	字段名
	**/
	private String majorproject;
	/**
	*	字段名
	**/
	private String pdl;
	/**
	*	字段名
	**/
	private String updown;
	/**
	*	字段名
	**/
	private String ywzjl;
	/**
	*	字段名
	**/
	private String isywkjfzjl;
	/**
	*	研管合并功能需求id
	**/
	private String fileworkitemids;
	/**
	*	需求审核人
	**/
	private String bmfzr;
	/**
	*	是否涉及外包采购需求
	**/
	private String issjwbcgxq;
	/**
	*	牵头业务部门分管行长
	**/
	private String qtywbfghz;
	/**
	*	牵头业务部门分管行长名称
	**/
	private String qtywbfghzname;
	/**
	*	是否提交分管行长
	**/
	private String iskjhzsp;
	/**
	*	是否涉及包含电子印章
	**/
	private String issjbhdzyz;
	/**
	*	反洗钱影响自评
	**/
	private String antimoneySelfdesc;
	/**
	*	字段名
	**/
	private String demandname1;
	/**
	*	是否绩效考核项目
	**/
	private String isjxkh;
}
