package com.work.ppm.application.model.projectmanager;

import lombok.Data;
import com.work.ppm.commons.annotaion.Id;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 奋进号业务意向
 */
@Data
public class PpmDemandnewinfo {
	/**
	*	外包采购类型
	**/
	private String cgtype;
	/**
	*	是否涉及外包采购需求
	**/
	private String isxmzcg;
	/**
	*	科技人力预算
	**/
	private BigDecimal tbudget;
	/**
	*	业务人力预算
	**/
	private BigDecimal bbudget;
	/**
	*	总工作量
	**/
	private BigDecimal sumworkload;
	/**
	*	总工作量单位
	**/
	private String sumworkunit;
	/**
	*	项目规模 GLOBAL_PROJECT_SCALE
	**/
	private String proscale;
	/**
	*	是否大中型 02是  01否
	**/
	private String islarge;
	/**
	*	是否涉及外包采购需求
	**/
	private String issjwbcgxq;
	/**
	*	是否涉及包含电子印章
	**/
	private String issjbhdzyz;
	/**
	*	审批处理意见
	**/
	private String suggest;
	/**
	*	业务 分管总经理
	**/
	private String ywfgzjl;
	/**
	*	业务 分管行长
	**/
	private String ywfghz;
	/**
	*	流程id
	**/
	private String instanceid;
	/**
	*	删除标志
	**/
	private String isdel;
	/**
	*	合并是否业务总经理
	**/
	private String isywkjfzjl;
	/**
	*	合并是否业务分管总经理
	**/
	private String isftywjl;
	/**
	*	业务总经理
	**/
	private String ywzjl;
	/**
	*	总预算
	**/
	private BigDecimal sumbudget;
	/**
	*	奋进号业务意向id
	**/
	private String issueid;
	/**
	*	业务二级部经理
	**/
	private String ywejbjl;
	/**
	*	科技二级部经理
	**/
	private String kjejbjl;
	/**
	*	是否业务分管总经理
	**/
	private String isywfazjl;
	/**
	*	业务分摊总经理
	**/
	private String ywfzzjl;
	/**
	*	是否绩效考核项目
	**/
	private String isjxkh;
	/**
	*	可行性分析执行人
	**/
	private String researchuserid;
	/**
	*	是否可行性分析
	**/
	private String isresearch;
	/**
	*	字段名
	**/
	@Id
	private Long id;
	/**
	*	意向编号
	**/
	private String demandno;
	/**
	*	意向名称
	**/
	private String demandname;
	/**
	*	需求发起人
	**/
	private String demanduserid;
	/**
	*	提出时间
	**/
	private Date bringupdate;
	/**
	*	业务项目经理
	**/
	private String busiuserid;
	/**
	*	产品经理
	**/
	private String prodeuctmanageruserid;
	/**
	*	所属产品
	**/
	private String prodeuct;
	/**
	*	所属场景
	**/
	private String scenes;
	/**
	*	应用类型
	**/
	private String apptypes;
	/**
	*	级联-具体应用
	**/
	private String apps;
	/**
	*	意向分类
	**/
	private String types;
	/**
	*	状态
	**/
	private String status;
	/**
	*	需求来源
	**/
	private String demandsource;
	/**
	*	牵头业务部门
	**/
	private String demanddeptno;
	/**
	*	优先类型  GLOBAL_PROJECT_3PRIORITY
	**/
	private String priority;
	/**
	*	是否新系统
	**/
	private String isnew;
	/**
	*	适用用户
	**/
	private String forusers;
	/**
	*	收益类型
	**/
	private String incometype;
	/**
	*	级联-具体收益
	**/
	private String mainincomes;
	/**
	*	是否重点项目
	**/
	private String majorproject;
	/**
	*	级联-紧急/重点原因
	**/
	private String majorprojectreason;
	/**
	*	意向描述
	**/
	private String demanddesc;
	/**
	*	系统期望上线日期
	**/
	private Date busiexpect;
	/**
	*	主系统编号
	**/
	private String mSysno;
	/**
	*	科技二级部经理
	**/
	private String tecmanageruserid;
	/**
	*	主系统研发负责人
	**/
	private String tecuserid;
	/**
	*	系统业务主管部门
	**/
	private String busideptno;
	/**
	*	系统科技主管部门
	**/
	private String tectdeptno;
	/**
	*	采购预算
	**/
	private BigDecimal cbudget;
	/**
	*	业务团队工作量单位
	**/
	private String sworkunit;
	/**
	*	 科技团队工作量单位
	**/
	private String tworkunit;
	/**
	*	科技团队工作量
	**/
	private BigDecimal tecworkload;
	/**
	*	业务团队工作量
	**/
	private BigDecimal busiworkload;
	/**
	 *	分行科技总经理
	 **/
	private String fhKjzjl;
	/**
	 *	是否涉及三新(1:是，0：否)
	 **/
	private String sfThreenew;
	/**
	 *	是否涉及新架构(1:是，0：否)
	 **/
	private String sfXjg;
	/**
	 *	是否涉及新技术(1:是，0：否)
	 **/
	private String sfXjs;

	/**
	 * 预审材料
	 */
	private String pretMaterial;
}
